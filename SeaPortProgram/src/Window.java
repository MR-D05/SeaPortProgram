import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	World world;
	JScrollPane newJScrollPane;
	int nD;
	List<Worker> listOfWorkers;
	HashMap<SeaPort, JScrollPane> hashMapContainingJScrollPanesForEachSeaPort;
	ConcurrentHashMap<SeaPort, JPanelRepresentationOfJobThreads> concurrentHashMapContainingJPanelsRepresentingJobThreadsForEachSeaPort;

	public Window(final World world) {
		this.world = world;
		hashMapContainingJScrollPanesForEachSeaPort = new HashMap<>();
		concurrentHashMapContainingJPanelsRepresentingJobThreadsForEachSeaPort = new ConcurrentHashMap<>();
		listOfWorkers = new ArrayList<>();
		for (final Iterator<SeaPort> iterator = world.returnPortList().iterator(); iterator.hasNext();) {
			final SeaPort seaPort = iterator.next();
			nD += seaPort.returnDockList().size();
		}
		generateTitle();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		generateJobView();
	}

	private void generateTitle() {
		setTitle("Jobs");
	}

	private void generateJobView() {

		final JPanel primaryJPanel = returnJPanel();
		newJScrollPane.setViewportView(primaryJPanel);
		setAppropriateDimension();
		setLayout1(primaryJPanel);
		for (final Iterator<SeaPort> iterator = world.returnPortList().iterator(); iterator.hasNext();) {
			final SeaPort port = iterator.next();
			final JPanel jPanel = new JPanel();
			setThisLayout(jPanel);
			addJPanelsToConcurrentHashMapContainingJPanelsRepresentingJobThreadsForEachSeaPort(port);
			jPanel.add(concurrentHashMapContainingJPanelsRepresentingJobThreadsForEachSeaPort.get(port));
			for (final Iterator<Dock> iterator2 = port.returnDockList().iterator(); iterator2.hasNext();) {
				final Dock dock = iterator2.next();
				addAllPanels(port, dock, jPanel, concurrentHashMapContainingJPanelsRepresentingJobThreadsForEachSeaPort.get(port));
			}
			primaryJPanel.add(pP(port.getName(), jPanel));
		}
		fireUp(listOfWorkers);
		add(newJScrollPane);
		this.pack();
	}

	private void addJPanelsToConcurrentHashMapContainingJPanelsRepresentingJobThreadsForEachSeaPort(final SeaPort port) {
		concurrentHashMapContainingJPanelsRepresentingJobThreadsForEachSeaPort.put(port, new JPanelRepresentationOfJobThreads(port.pool));
	}

	private JPanel returnJPanel() {
		newJScrollPane = new JScrollPane();
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
		p.setLayout(new GridLayout(0, world.returnPortList().size()));
	}

	private void setAppropriateDimension() {
		newJScrollPane.setPreferredSize(new Dimension(2000, 1000));
	}

	private void addAllPanels(final SeaPort port, final Dock dock, final JPanel topLevelJPanel, final JPanelRepresentationOfJobThreads jPanelRepresentationOfJobThreads) {
		final Panel dockJPanel = newPanelForEachDock(dock);
		new ArrayList<Object>().add(dockJPanel);
		topLevelJPanel.add(dockJPanel);
//		for (final Iterator<Ship> iterator3 = dock.returnShipList().iterator(); iterator3.hasNext();) {
//			final Ship ship = iterator3.next();
//			for (final Iterator<Job> iterator4 = ship.returnJobList().iterator(); iterator4.hasNext();) {
				final Worker worker = new Worker(dockJPanel, topLevelJPanel, dock, port, jPanelRepresentationOfJobThreads);
				listOfWorkers.add(worker);
//			}
//		}
		repaintAndRevalidate(topLevelJPanel);
		this.pack();
	}

	private void repaintAndRevalidate(final JPanel jPanel) {
		jPanel.repaint();
		jPanel.revalidate();
	}

	private Panel newPanelForEachDock(final Dock dock) {
		final Panel index = new Panel(dock.getName(), 0, 0);
		return index;
	}

	public void fireUp(final List<Worker> workers) {
		final ExecutorService eS = newFixedThreadPoolForAllJobs();
		for (final Iterator<Worker> iterator = workers.iterator(); iterator.hasNext();) {
			final Worker worker = iterator.next();
			eS.execute(worker);
		}
	}

	private ExecutorService newFixedThreadPoolForAllJobs() {
		return Executors.newFixedThreadPool(listOfWorkers.size());
	}

	public JScrollPane pP(final String pN, final JPanel nested) {
		final JScrollPane panel = new JScrollPane(nested);
		return panel;
	}

}
