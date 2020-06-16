import java.util.Scanner;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Job extends Thing {

	double duration;
	private boolean primed;
	private boolean vetted;
	ArrayList<Skill> requirements;

	public Job(final Scanner sc) {
		super(sc);
		if (sc.hasNextDouble())
			duration = sc.nextDouble();
		else
			duration = 0.0d;
		requirements = new ArrayList<>();
		primed = false;
		vetted = true;
	}

	public double gD() {
		return duration;
	}

	public void sD(final double d) {
		this.duration = d;
	}

	public ArrayList<Skill> gR() {
		return requirements;
	}

	public void sR(final ArrayList<Skill> requirements) {
		this.requirements = requirements;
	}

	public boolean vetted() {
		return vetted;
	}

	public void setVetted(final boolean valid) {
		vetted = valid;
	}

	public boolean primed() {
		return primed;
	}

	public void setPrimed(final boolean ready) {
		primed = ready;
	}
}

class Progress extends JProgressBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean sigKill;
	private Boolean sigPerm;

	public Progress() {
		this.sigKill = false;
		this.sigPerm = true;
	}

	public boolean hasPermission() {
		synchronized (sigPerm) {
			sigPerm = ((sigPerm == true) == false);
			if ((sigPerm == true) == false)
				setString("Halted");
			else
				setString("Progressing");
		}
		return sigPerm;
	}

	public void kill() {
		sigKill = true;
	}

	public boolean returnSigKill() {
		return sigKill;
	}

	public boolean returnSigPerm() {
		return sigPerm;
	}
}

class Panel extends JPanel {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	JLabel dL;
	JLabel jL;
	JLabel sL;
	JPanel tP;
	JPanel mP;
	JPanel lP;
	JButton progressState;
	JButton killSwitch;
	Progress pB;

	public Panel(final String d, final int tempJob, final int upperLimit) {

		dL = new JLabel(d);
		sL = shipLabel();
		jL = jobLabel(tempJob, upperLimit);
		tP = new JPanel();
		pB = new Progress();
		mP = new JPanel();

		progressState = haltButton();

		progressState.addActionListener(e -> {
			final boolean hasPermission = pB.hasPermission();
			if (hasPermission)
				progressState.setText("Halt");
			else
				progressState.setText("Continue");
		});

		killSwitch = killSwitch();

		killSwitch.addActionListener(e -> pB.kill());

		lP = new JPanel();
		lP.add(progressState);
		lP.add(killSwitch);
		setLayout(new GridLayout(0, 1));
		tP.add(dL);
		tP.add(sL);
		tP.add(jL);
		mP.add(pB);
		add(tP);
		add(mP);
		add(lP);
	}

	private JButton killSwitch() {
		return new JButton("Kill");
	}

	private JButton haltButton() {
		return new JButton("Halt");
	}

	private JLabel jobLabel(final int tempJob, final int upperLimit) {
		return new JLabel(jL(tempJob, upperLimit));
	}

	private JLabel shipLabel() {
		return new JLabel("Ship: ");
	}

	private String jL(final int tempJob, final int upperLimit) {
		return "Job: " + tempJob + "of" + upperLimit;
	}

	public void rJ(final int tempJob, final int upperLimit) {
		jL.setText(genText(tempJob, upperLimit));
		reviseJL();
	}

	private void reviseJL() {
		jL.revalidate();
		jL.repaint();
	}

	public void reviseSL(final String s) {
		sL.setText("Ship: " + s);
		sL.revalidate();
		sL.repaint();
	}

	private String genText(final int a, final int b) {
		return jL(a, b);
	}

	public Progress returnPBar() {
		return pB;
	}
}

class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	World world;
	JScrollPane sP;
	int nD;
	List<Worker> lW;
	HashMap<SeaPort, JScrollPane> pHM;
	ConcurrentHashMap<SeaPort, ThreadPanels> pCM;

	public Window(final World world) {
		this.world = world;
		pHM = new HashMap<>();
		pCM = new ConcurrentHashMap<>();
		lW = new ArrayList<>();
		for (final Iterator<SeaPort> iterator = world.gP().iterator(); iterator.hasNext();) {
			final SeaPort seaPort = iterator.next();
			nD += seaPort.gD().size();
		}
		iT();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		generateThreadView();
	}

	private void iT() {
		setTitle("Jobs");
	}

	private void generateThreadView() {

		final JPanel p = gP();
		sP.setViewportView(p);
		setAppropriateDimension();
		setLayout1(p);
		for (final Iterator<SeaPort> iterator = world.gP().iterator(); iterator.hasNext();) {
			final SeaPort s = iterator.next();
			final JPanel pl = new JPanel();
			setThisLayout(pl);
			addThreadsToMap(s);
			pl.add(pCM.get(s));
			for (final Iterator<Dock> iterator2 = s.gD().iterator(); iterator2.hasNext();) {
				final Dock dock = iterator2.next();
				addAllPanels(s, dock, pl, pCM.get(s));
			}
			p.add(pP(s.getName(), pl));
		}
		fireUp(lW);
		add(sP);
		this.pack();
	}

	private void addThreadsToMap(final SeaPort s) {
		pCM.put(s, new ThreadPanels(s.rP));
	}

	private JPanel gP() {
		sP = new JScrollPane();
		final JPanel p = new JPanel();
		setLayout(p);
		return p;
	}

	private void setThisLayout(final JPanel pl) {
		pl.setLayout(new GridLayout(0, 1));
	}

	private void setLayout1(final JPanel p) {
		p.setLayout(new GridLayout(1, 0));
	}

	private void setLayout(final JPanel p) {
		p.setLayout(new GridLayout(0, world.gP().size()));
	}

	private void setAppropriateDimension() {
		sP.setPreferredSize(new Dimension(2000, 1000));
	}

	private void addAllPanels(final SeaPort s, final Dock d, final JPanel jP, final ThreadPanels rPP) {
		final Panel index = newPanelForEachDock(d);
		new ArrayList<Object>().add(index);
		jP.add(index);
		final Worker w = new Worker(index, jP, d, s, rPP);
		lW.add(w);
		reviseJP(jP);
		this.pack();
	}

	private void reviseJP(final JPanel jP) {
		jP.revalidate();
		jP.repaint();
	}

	private Panel newPanelForEachDock(final Dock d) {
		final Panel index = new Panel(d.getName(), 0, 0);
		return index;
	}

	public void fireUp(final List<Worker> w) {
		final ExecutorService eS = newFixedThreadPoolForEachDock();
		for (final Iterator<Worker> iterator = w.iterator(); iterator.hasNext();) {
			final Worker j = iterator.next();
			eS.execute(j);
		}
	}

	private ExecutorService newFixedThreadPoolForEachDock() {
		return Executors.newFixedThreadPool(nD);
	}

	public JScrollPane pP(final String pN, final JPanel nested) {
		final JScrollPane panel = new JScrollPane(nested);
		return panel;
	}

}

class Worker extends SwingWorker<Void, Integer> {
	private final Progress pB;
	private final Panel jP;
	private final ThreadPanels rPP;
	private final SeaPort s;
	JPanel main;
	Dock d;

	public Worker(final Panel jP, final JPanel m, final Dock d, final SeaPort s, final ThreadPanels rPP) {
		this.jP = jP;
		this.pB = jP.returnPBar();
		this.main = m;
		this.d = d;
		this.s = s;
		this.rPP = rPP;
	}

	@Override
	protected Void doInBackground() throws Exception {
		final List<Ship> listShips = d.returnShip();
		for (int i = 0; i < listShips.size(); i++) {
			jP.reviseSL(listShips.get(i).getName());
			final List<Job> jobs = populateJobsArray(listShips, i);
			final int numbJobs = initJobs();
			for (final Iterator<Job> iterator = jobs.iterator(); iterator.hasNext();) {
				final Job j = iterator.next();
				if ((j.vetted() == true) == false)
					continue;
				jP.rJ(numbJobs, numbJobsInArray(jobs));
				synchronized (s.rP) {
					j.setPrimed(true);
					for (final Iterator<Skill> iterator2 = j.gR().iterator(); iterator2.hasNext();) {
						final Skill sk = iterator2.next();
						final Integer shipTasks = s.rP.get(sk);
						if (((shipTasks == null) == false) && i >= 1)
							continue;
						j.setPrimed(false);
					}
					while (j.primed() == false) {
						s.rP.wait();
					}
					j.setPrimed(false);
					remove(j);
				}

				final int duration = (int) j.gD();
				long time = System.currentTimeMillis();
				final long started = time;
				final long stopped = (time + 1000 * duration);
				final double thisDuration = (stopped - time);

				while (time < stopped && (pB.returnSigKill() == false)) {
					timeOut();
					if (pB.returnSigPerm()) {
						time += 100;
						pB.setValue((int) (((time - started) / thisDuration) * 100));
					}

				}

				synchronized (s.rP) {
					add(j);
					j.setPrimed(false);
					s.rP.notifyAll();
				}
				incrementNumbJobs(numbJobs);
			}
		}
		return null;
	}

	private void incrementNumbJobs(int numbJobs) {
		numbJobs++;
	}

	private void timeOut() throws InterruptedException {
		Thread.sleep(100);
	}

	private int numbJobsInArray(final List<Job> jobs) {
		return jobs.size();
	}

	private List<Job> populateJobsArray(final List<Ship> listShips, final int i) {
		final List<Job> jobs = listShips.get(i).gJ();
		return jobs;
	}

	private int initJobs() {
		final int numbJobs = 1;
		return numbJobs;
	}

	@Override
	protected void done() {
		try {
			get();
			main.remove(jP);
			reviseMain();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	private void reviseMain() {
		main.revalidate();
		main.repaint();
	}

	boolean checkVacancy(final Job j) {
		synchronized (s.rP) {
			for (final Skill sk : j.gR()) {
				final Integer i = s.rP.get(sk);
				if (i < 1 || i.equals(null))
					return false;
			}
			return true;
		}
	}

	void remove(final Job j) {
		synchronized (s.rP) {
			for (final Iterator<Skill> iterator = j.gR().iterator(); iterator.hasNext();) {
				final Skill sk = iterator.next();
				rPP.remove(sk);
				populateSeaPortHashMap(sk);
				sk.notifyAll();
			}
		}
	}

	private void populateSeaPortHashMap(final Skill sk) {
		s.rP.put(sk, s.rP.get(sk) - 1);
	}

	void add(final Job j) {
		synchronized (s.rP) {
			for (final Iterator<Skill> iterator = j.gR().iterator(); iterator.hasNext();) {
				final Skill skill = iterator.next();
				rPP.add(skill);
				skill.notifyAll();
			}
		}
	}
}

class ThreadPanels extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ConcurrentHashMap<Skill, Integer> p;
	private final ConcurrentHashMap<Skill, JLabel> l;

	public ThreadPanels(final ConcurrentHashMap<Skill, Integer> rP) {
		setLayout();
		this.p = rP;
		this.l = newPHashMap(rP);

		for (final Iterator<Entry<Skill, Integer>> iterator = p.entrySet().iterator(); iterator.hasNext();) {
			final ConcurrentHashMap.Entry<Skill, Integer> entry = iterator.next();
			l.put(entry.getKey(), mJL(entry.getKey(), entry.getValue()));
		}

		final Iterator<Map.Entry<Skill, JLabel>> iterator = l.entrySet().iterator();

		while (iterator.hasNext()) {
			final JPanel tP = new JPanel();
			final JLabel temp = iterator.next().getValue();
			final JPanel jPanel = cP();
			jPanel.add(temp);
			tP.add(jPanel);
			if (iterator.hasNext()) {
				final JLabel l = iterator.next().getValue();
				final JPanel jP = cP();
				jP.add(l);
				tP.add(jP);
			}
			this.add(tP);
		}
		reviseThis();
	}

	private void reviseThis() {
		this.repaint();
		this.validate();
	}

	private ConcurrentHashMap<Skill, JLabel> newPHashMap(final ConcurrentHashMap<Skill, Integer> rP) {
		return new ConcurrentHashMap<>(rP.size());
	}

	private void setLayout() {
		this.setLayout(new GridLayout(0, 1));
	}

	public void add(final Skill sk) {
		addSToPool(sk);
		makeLabels(sk);
		reeval();
	}

	private void makeLabels(final Skill sk) {
		l.get(sk).setText(setTitle(sk, p.get(sk)));
	}

	private void addSToPool(final Skill sk) {
		p.put(sk, p.get(sk) + 1);
	}

	public void remove(final Skill sk) {
		p.put(sk, p.get(sk) - 1);
		makeLabels(sk);
		reeval();
	}

	private void reeval() {
		repaint();
		revalidate();
	}

	private JLabel mJL(final Skill sk, final Integer i) {
		final JLabel jL = new JLabel();
		jL.setText(setTitle(sk, i));
		return jL;
	}

	private String setTitle(final Skill sk, final Integer i) {
		return sk.skill + ": " + i;
	}

	private JPanel cP() {
		final JPanel jPanel = new JPanel();
		return jPanel;
	}
}
