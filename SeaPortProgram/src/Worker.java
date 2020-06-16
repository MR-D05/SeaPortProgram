import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class Worker extends SwingWorker<Void, Integer> {
	private final Progress progressBar;
	private final Panel jPanelRepresentingJob;
	private final JPanelRepresentationOfJobThreads jPanelRepresentationOfJobThreads;
	private final SeaPort port;
	JPanel primaryJPanel;
	Dock dock;

	public Worker(final Panel jPanelRepresentingJobThread, final JPanel primaryJPanel, final Dock dock, final SeaPort port,
			final JPanelRepresentationOfJobThreads jPanelRepresentationOfJobThreads) {
		this.jPanelRepresentingJob = jPanelRepresentingJobThread;
		this.progressBar = jPanelRepresentingJobThread.returnProgressBar();
		this.primaryJPanel = primaryJPanel;
		this.dock = dock;
		this.port = port;
		this.jPanelRepresentationOfJobThreads = jPanelRepresentationOfJobThreads;
	}

	@Override
	protected Void doInBackground() throws Exception {
		final List<Ship> listShips = dock.returnShipList();
		for (int i = 0; i < listShips.size(); i++) {
			jPanelRepresentingJob.reviseShipList(listShips.get(i).getName());
			final List<Job> jobs = populateJobsArray(listShips, i);
			int numbJobs = 1;
			for (final Iterator<Job> iterator = jobs.iterator(); iterator.hasNext();) {
				final Job job = iterator.next();
				if ((job.isAnApplicableJob() == true) == false) {
					continue;
				}
				jPanelRepresentingJob.reviseTextOnJLabel(numbJobs, numbJobsInArray(jobs));
				synchronized (port.pool) {
					job.setAsReadyAndWaiting(true);
					job.thread.run();
					for (final Iterator<Skill> iterator2 = job.returnRequirements().iterator(); iterator2.hasNext();) {
						final Skill skill = iterator2.next();
						final Integer jobsThatNeedToGetDoneOnEachShip = port.pool.get(skill);
						if (((jobsThatNeedToGetDoneOnEachShip == null) == false) && i >= 1) {
							continue;
						}
						job.setAsReadyAndWaiting(false);
					}
					while (job.isReadyAndWaiting() == false) {
						port.pool.wait();
					}
					job.setAsReadyAndWaiting(false);
					remove(job);
				}

				final int duration = (int) job.returnDuration();
				long time = System.currentTimeMillis();
				final long started = time;
				final long stopped = (time + 1000 * duration);
				final double thisDuration = (stopped - time);

				while (time < stopped && (progressBar.returnSigKill() == false)) {
					timeOut();
					if (progressBar.returnSigPerm()) {
						time += 100;
						progressBar.setValue((int) (((time - started) / thisDuration) * 100));
					}

				}

				synchronized (port.pool) {
					add(job);
					job.setAsReadyAndWaiting(false);
					port.pool.notifyAll();
				}
				numbJobs += 1;
			}
		}
		return null;
	}

	private void timeOut() throws InterruptedException {
		Thread.sleep(100);
	}

	private int numbJobsInArray(final List<Job> jobs) {
		return jobs.size();
	}

	private List<Job> populateJobsArray(final List<Ship> listShips, final int i) {
		final List<Job> jobs = listShips.get(i).returnJobList();
		return jobs;
	}

	@Override
	protected void done() {
		try {
			get();
			primaryJPanel.remove(jPanelRepresentingJob);
			repaintAndRevalidate();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	private void repaintAndRevalidate() {
		primaryJPanel.revalidate();
		primaryJPanel.repaint();
	}

	boolean checkVacancy(final Job job) {
		synchronized (port.pool) {
			for (final Skill skill : job.returnRequirements()) {
				final Integer i = port.pool.get(skill);
				if (i < 1 || i.equals(null))
					return false;
			}
			return true;
		}
	}

	void remove(final Job job) {
		synchronized (port.pool) {
			for (final Iterator<Skill> iterator = job.returnRequirements().iterator(); iterator.hasNext();) {
				final Skill skill = iterator.next();
				jPanelRepresentationOfJobThreads.lessenResourcePoolByOne(skill);
				populateSeaPortHashMap(skill);
				skill.notifyAll();
			}
		}
	}

	private void populateSeaPortHashMap(final Skill sk) {
		port.pool.put(sk, port.pool.get(sk) - 1);
	}

	void add(final Job job) {
		synchronized (port.pool) {
			for (final Iterator<Skill> iterator = job.returnRequirements().iterator(); iterator.hasNext();) {
				final Skill skill = iterator.next();
				jPanelRepresentationOfJobThreads.add(skill);
				skill.notifyAll();
			}
		}
	}
}