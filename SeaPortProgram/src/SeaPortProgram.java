import javax.swing.text.DefaultCaret;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;

public class SeaPortProgram extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(final String[] args) {
		new SeaPortProgram();
	}

	private final JTextArea output = new JTextArea(50, 50);
	private final JComboBox<String> c;
	private final JTextField fArea;
	private JFileChooser fChooser;
	private File file;
	private final JTextField sArea;
	private final JComboBox<String> sCB1;
	private final JComboBox<String> sCB2;
	JTree t;
	JScrollPane trP;
	JButton ex;
	JButton col;
	JPanel trB;
	JPanel tP;
	JPanel prP;
	JScrollPane scPr;
	JTable jTable;

	private World world;

	public SeaPortProgram() {

		setTitle("SeaPort Program");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JPanel m1 = new JPanel();
		final JPanel m2 = new JPanel();
		final JPanel s = new JPanel();
		final JPanel s1 = new JPanel();
		final JPanel s2 = new JPanel();
		final JPanel f = new JPanel();
		final JPanel f1 = new JPanel();
		final JPanel f2 = new JPanel();
		final JButton dB = new JButton("Display");
		final JButton rB = new JButton("Reset");
		final JButton sB = new JButton("Search");
		final JButton oB = new JButton("Open File");
		final JButton srtB = new JButton("Sort");
		final JPanel sP1 = new JPanel();
		final JPanel sP2 = new JPanel();
		final JPanel srtP = new JPanel();
		final JScrollPane sP = new JScrollPane(output);
		final JPanel mM = new JPanel();
		final JPanel mM1 = new JPanel();
		c = new JComboBox<String>();
		sCB1 = new JComboBox<String>();
		sCB2 = new JComboBox<String>();
		f.setLayout(new GridLayout(0, 1));
		s.setLayout(new GridLayout(0, 1));
		m1.setLayout(new GridLayout(0, 1));
		mM1.setLayout(new GridLayout(1, 0));
		srtP.setLayout(new GridLayout(0, 1));
		sArea = new JTextField(15);
		fArea = new JTextField(15);
		c.addItem("Name");
		c.addItem("Index");
		c.addItem("Skill");
		sCB1.addItem("Weight");
		sCB1.addItem("Length");
		sCB1.addItem("Width");
		sCB1.addItem("Draft");
		sCB2.addItem("Ascending");
		sCB2.addItem("Descending");
		f1.add(fArea);
		s1.add(sArea);
		f1.add(oB);
		f2.add(dB);
		f2.add(rB);
		m1.add(f);
		f.add(f1);
		f.add(f2);
		s.add(s1);
		s.add(s2);
		s.setMaximumSize(s.getPreferredSize());
		f.setMaximumSize(f.getPreferredSize());
		s1.add(c);
		s2.add(sB);
		m1.add(s);
		m2.add(m1);
		sCB1.setEditable(true);
		sCB2.setEditable(true);
		sP2.add(sCB1);
		sP2.add(sCB2);
		sP2.add(srtB);
		srtP.add(sP1);
		srtP.add(sP2);
		srtP.setMaximumSize(srtP.getPreferredSize());
		mM1.add(f);
		mM1.add(s);
		mM1.add(srtP);
		mM.add(mM1);
		add(sP, BorderLayout.CENTER);
		add(mM, BorderLayout.NORTH);
		tP = new JPanel(new BorderLayout());
		tP.setMaximumSize(tP.getPreferredSize());
		add(tP, BorderLayout.WEST);
		pack();
		final DefaultCaret caret = (DefaultCaret) output.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		setVisible(true);

		oB.addActionListener(e -> chooser());

		dB.addActionListener(e -> {
			brandNewWorld();
			initialize();
			if ((file == null) == false) {
				r(file);
				world.validateSeaPortJobs();
				gT();
				new Window(world);
				repaint();
				revalidate();
			} else
				selectFileMessage();
		});

		rB.addActionListener(e -> output.setText(""));

		sB.addActionListener(e -> s((String) (c.getSelectedItem()), sArea.getText()));

		srtB.addActionListener(e -> {
			output.setText("");
			sort(sCB1.getItemAt(sCB1.getSelectedIndex()), sCB2.getItemAt(sCB2.getSelectedIndex()));
		});
	}

	private void gT() {
		final DefaultMutableTreeNode head = gTN("World");
		t = new JTree(head);
		trP = new JScrollPane(t);
		ex = new JButton("Expand");
		col = new JButton("Collapse");
		trB = new JPanel();
		trB.add(ex);
		trB.add(col);
		tP.add(trB, BorderLayout.SOUTH);
		tP.add(trP, BorderLayout.CENTER);

		ex.addActionListener(e -> {
			for (int i = 0; i < t.getRowCount(); i++) {
				t.expandRow(i);
			}
		});

		col.addActionListener(e -> {
			for (int i = 0; i < t.getRowCount(); i++) {
				t.collapseRow(i);
			}
		});
	}

	private DefaultMutableTreeNode gTN(final String world) {
		final DefaultMutableTreeNode head = new DefaultMutableTreeNode(world);
		DefaultMutableTreeNode port;
		for (final SeaPort s : this.world.returnPortList()) {
			port = new DefaultMutableTreeNode("SeaPort: " + s.getName());
			head.add(port);
			port.add(bN(s.returnDockList(), "Docks"));
			port.add(cN(s.returnQueue(), "Queue"));
			port.add(cN(s.returnShipList(), "Ships"));
			port.add(cN(s.returnPersonList(), "Persons"));
		}
		return head;
	}

	private MutableTreeNode bN(final ArrayList<Dock> a, final String s1) {
		final DefaultMutableTreeNode head = new DefaultMutableTreeNode(s1);
		for (final Iterator<Dock> iterator = a.iterator(); iterator.hasNext();) {
			final Dock d = iterator.next();
			String temp;
			temp = d.returnShipList().get(0) != null ? "Ship: " + d.returnShipList().get(0).getName() : "(Empty)";
			attach(head, temp);
		}
		return head;
	}

	private void attach(final DefaultMutableTreeNode head, final String temp) {
		head.add(new DefaultMutableTreeNode(temp));
	}

	private <T extends Thing> DefaultMutableTreeNode cN(final ArrayList<T> a, final String s2) {
		final DefaultMutableTreeNode head = new DefaultMutableTreeNode(s2);
		for (final Iterator<T> iterator = a.iterator(); iterator.hasNext();) {
			final T index = iterator.next();
			final String s = index.getName();
			attach(head, s);
		}
		return head;
	}

	private void brandNewWorld() {
		world = new World(null);
	}

	private void chooser() {
		try {
			establishChooserProperties();
			fChooser.showOpenDialog(null);
			file = fChooser.getSelectedFile();
			fArea.setText(file.toString());
		} catch (final Exception e) {
			selectFileMessage();
			e.printStackTrace();
		}
	}

	private void errorMessage() {
		JOptionPane.showMessageDialog(null, "Error.");
	}

	private void establishChooserProperties() {
		fChooser = new JFileChooser(".");
		fChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fChooser.setDialogTitle("File Select");
	}

	private boolean failedToFind(final StringBuilder r) {
		return r.toString().isEmpty() || r.toString() == null;
	}

	private void failedToFindMessage() {
		JOptionPane.showMessageDialog(null, "Search failed.");
	}

	private <T> void fDI(final int i, final ArrayList<Dock> d, final StringBuilder r) {
		for (final Iterator<Dock> iterator = d.iterator(); iterator.hasNext();) {
			final Dock index = iterator.next();
			if (((Dock) index).returnIndex() == i) {
				r.append(index.toString());
				r.append("\n");
			}
		}
	}

	private <T> void fDN(final String n, final ArrayList<Dock> d, final StringBuilder r) {
		for (final Iterator<Dock> iterator = d.iterator(); iterator.hasNext();) {
			final Dock index = iterator.next();
			if (((Dock) index).getName().equalsIgnoreCase(n)) {
				r.append(index.toString());
				r.append("\n");
			}
		}
	}

	private <T> void fPI(final int i, final ArrayList<Person> p, final StringBuilder r) {
		for (final Iterator<Person> iterator = p.iterator(); iterator.hasNext();) {
			final Person index = iterator.next();
			if (((Person) index).returnIndex() == i) {
				r.append(index.toString());
				r.append("\n");
			}
		}
	}

	private <T> void fPN(final String n, final ArrayList<Person> p, final StringBuilder r) {
		for (final Iterator<Person> iterator = p.iterator(); iterator.hasNext();) {
			final Person index = iterator.next();
			if (((Person) index).getName().equalsIgnoreCase(n)) {
				r.append(index.toString());
				r.append("\n");
			}
		}
	}

	private <T> void fSI(final int i, final ArrayList<Ship> s, final StringBuilder r) {
		for (final Iterator<Ship> iterator = s.iterator(); iterator.hasNext();) {
			final Ship index = iterator.next();
			if (((Ship) index).returnIndex() == i) {
				r.append(index.toString());
				r.append("\n");
			}
		}
	}

	private <T> void fSN(final String n, final ArrayList<Ship> s, final StringBuilder r) {
		for (final Iterator<Ship> iterator = s.iterator(); iterator.hasNext();) {
			final Ship index = iterator.next();
			if (((Ship) index).getName().equalsIgnoreCase(n)) {
				r.append(index.toString());
				r.append("\n");
			}
		}
	}

	private void initialize() {
		output.setText("");
	}

	private void invalidEntryMessage() {
		JOptionPane.showMessageDialog(null, "Invalid entry.");
	}

	private boolean lineIsNotCommented(final String line) {
		return (line.startsWith("//") == false);
	}

	private void parse(final String line) {
		final Scanner sc = new Scanner(line);
		while (sc.hasNext()) {
			final String next = sc.next();
			if ("port".equals(next)) {
				world.addPort(new SeaPort(sc));
			} else if ("dock".equals(next)) {
				world.addDock(new Dock(sc));
			} else if ("ship".equals(next)) {
				world.addShip(new Ship(sc));
			} else if ("pship".equals(next)) {
				world.addShip(new PassengerShip(sc));
			} else if ("cship".equals(next)) {
				world.addShip(new CargoShip(sc));
			} else if ("person".equals(next)) {
				world.addPerson(new Person(sc));
			}
		}
	}

	private void parse(final String line, final HashMap<Integer, Ship> sMap, final HashMap<Integer, Person> pMap,
			final HashMap<Integer, Dock> dMap) {
		final Scanner sc = new Scanner(line);
		if (sc.hasNext() == false) {
			return;
		}
		final String next = sc.next();
		if (next.equals("port")) {
			world.addPort(new SeaPort(sc));
		} else if (next.equals("dock")) {
			final Dock dk = new Dock(sc);
			dMap.put(dk.returnIndex(), dk);
			world.addDock(dk);
		} else if (next.equals("ship")) {
			final Ship sh = new Ship(sc);
			sMap.put(sh.returnIndex(), sh);
			world.addShip(sh);
		} else if (next.equals("pship")) {
			final Ship psh = new PassengerShip(sc);
			sMap.put(psh.returnIndex(), psh);
			world.addShip(psh);
		} else if (next.equals("cship")) {
			final Ship csh = new CargoShip(sc);
			sMap.put(csh.returnIndex(), csh);
			world.addShip(csh);
		} else if (next.equals("person")) {
			final Person p = new Person(sc);
			pMap.put(p.returnIndex(), p);
			world.addPerson(p);
		} else if (next.equals("job")) {
			final Job job = new Job(sc);
			world.assignJob(job);
		} else {
		}
	}

	private void r(final File f) {
		try {
			final BufferedReader bR = Reader(f);
			String temp;
			final HashMap<Integer, Ship> sMap = new HashMap<>();
			final HashMap<Integer, Person> pMap = new HashMap<>();
			final HashMap<Integer, Dock> dMap = new HashMap<>();
			while ((((temp = bR.readLine()) == null) == false)) {
				if (lineIsNotCommented(temp)) {
					parse(temp, sMap, pMap, dMap);
				}
			}
			output.append(world.toString() + "\n");
		} catch (final Exception e) {
			errorMessage();
			e.printStackTrace();
		}
	}

	private BufferedReader Reader(final File f) throws FileNotFoundException {
		final FileReader fR = new FileReader(f);
		final BufferedReader bR = new BufferedReader(fR);
		return bR;
	}

	private void s(final String t, final String choice) {
		final StringBuilder r = new StringBuilder();
		if (output.getText().isEmpty()) {
			selectFileMessage();
		} else {
			final ArrayList<SeaPort> ports = world.returnPortList();
			if ("Name".equals(t)) {
				try {
					final String n = sArea.getText();
					initialize();
					storePortDataByName(n, ports, r);
					if (failedToFind(r)) {
						failedToFindMessage();
					} else {
						output.setText(r.toString());
					}
				} catch (final Exception e) {
					invalidEntryMessage();
					e.printStackTrace();
				}
			} else if ("Index".equals(t)) {
				try {
					final int i = Integer.parseInt(sArea.getText());
					initialize();
					storePortDataByIndex(i, ports, r);
					if (failedToFind(r)) {
						failedToFindMessage();
					} else {
						output.setText(r.toString());
					}
				} catch (final Exception e) {
					invalidEntryMessage();
					e.printStackTrace();
				}
			} else if ("Skill".equals(t)) {
				try {
					final String skill = sArea.getText();
					initialize();
					for (final Iterator<SeaPort> iterator2 = ports.iterator(); iterator2.hasNext();) {
						final SeaPort port = iterator2.next();
						final ArrayList<Person> people = port.returnPersonList();
						for (final Iterator<Person> iterator = people.iterator(); iterator.hasNext();) {
							final Person p = iterator.next();
							if (p.returnSkill().equalsIgnoreCase(skill)) {
								r.append(p.toString() + "\n");
							}
						}
					}
					if (failedToFind(r)) {
						failedToFindMessage();
					} else {
						output.setText(r.toString());
					}
				} catch (final Exception e) {
					invalidEntryMessage();
					e.printStackTrace();
				}
			} else {
			}
		}

	}

	private void selectFileMessage() {
		JOptionPane.showMessageDialog(null, "Select File.");
	}

	private void sort(final String selection, final String choice) {
		for (final Iterator<SeaPort> iterator2 = world.returnPortList().iterator(); iterator2.hasNext();) {
			final SeaPort port = iterator2.next();
			comparator(port);
			port.returnQueue().sort(new QueueComparator(selection, choice));
			output.setText(world.toString());
		}
	}

	private void comparator(final SeaPort port) {
		port.returnDockList().sort(new NameComparator());
		port.returnPersonList().sort(new NameComparator());
		port.returnShipList().sort(new NameComparator());
	}

	private void storePortDataByIndex(final int i, final ArrayList<SeaPort> ports, final StringBuilder r) {
		for (final Iterator<SeaPort> iterator = ports.iterator(); iterator.hasNext();) {
			final SeaPort port = iterator.next();
			final ArrayList<Dock> d = port.returnDockList();
			final ArrayList<Ship> s = port.returnShipList();
			final ArrayList<Ship> q = port.returnQueue();
			final ArrayList<Person> p = port.returnPersonList();
			fDI(i, d, r);
			fSI(i, s, r);
			fSI(i, q, r);
			fPI(i, p, r);
		}
	}

	private void storePortDataByName(final String n, final ArrayList<SeaPort> ports, final StringBuilder r) {
		for (final Iterator<SeaPort> iterator = ports.iterator(); iterator.hasNext();) {
			final SeaPort port = iterator.next();
			final ArrayList<Dock> d = port.returnDockList();
			final ArrayList<Ship> s = port.returnShipList();
			final ArrayList<Ship> q = port.returnQueue();
			final ArrayList<Person> p = port.returnPersonList();
			fDN(n, d, r);
			fSN(n, s, r);
			fSN(n, q, r);
			fPN(n, p, r);
		}
	}
}