import java.awt.GridLayout;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPanelRepresentationOfJobThreads extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ConcurrentHashMap<Skill, Integer> concurrentHashMapRepresentingJobResourcePool;
	private final ConcurrentHashMap<Skill, JLabel> concurrentHashMapContainingJLabelRepresentationsOfEachJob;

	public JPanelRepresentationOfJobThreads(final ConcurrentHashMap<Skill, Integer> pool) {
		setLayout();
		this.concurrentHashMapRepresentingJobResourcePool = pool;
		this.concurrentHashMapContainingJLabelRepresentationsOfEachJob = newConcurrentHashMapContainingJLabelRepresentationsOfEachJob(
				pool);

		for (final Iterator<Entry<Skill, Integer>> iterator = concurrentHashMapRepresentingJobResourcePool.entrySet()
				.iterator(); iterator.hasNext();) {
			final ConcurrentHashMap.Entry<Skill, Integer> entry = iterator.next();
			concurrentHashMapContainingJLabelRepresentationsOfEachJob.put(entry.getKey(),
					newJPanelRepresentingJob(entry.getKey(), entry.getValue()));
		}

		final Iterator<Map.Entry<Skill, JLabel>> iterator = concurrentHashMapContainingJLabelRepresentationsOfEachJob
				.entrySet().iterator();

		while (iterator.hasNext()) {
			final JPanel topLevelJPanel = new JPanel();
			final JLabel temp = iterator.next().getValue();
			final JPanel jPanel = newJPanel();
			jPanel.add(temp);
			topLevelJPanel.add(jPanel);
			if (iterator.hasNext()) {
				final JLabel jLabel = iterator.next().getValue();
				final JPanel jP = newJPanel();
				jP.add(jLabel);
				topLevelJPanel.add(jP);
			}
			this.add(topLevelJPanel);
		}
		reviseThis();
	}

	private void reviseThis() {
		this.repaint();
		this.validate();
	}

	private ConcurrentHashMap<Skill, JLabel> newConcurrentHashMapContainingJLabelRepresentationsOfEachJob(
			final ConcurrentHashMap<Skill, Integer> pool) {
		return new ConcurrentHashMap<>(pool.size());
	}

	private void setLayout() {
		this.setLayout(new GridLayout(0, 1));
	}

	public void add(final Skill sk) {
		augmentResourcePoolByOne(sk);
		makeLabels(sk);
		repaintAndRevalidate();
	}

	private void makeLabels(final Skill sk) {
		concurrentHashMapContainingJLabelRepresentationsOfEachJob.get(sk)
				.setText(setTitle(sk, concurrentHashMapRepresentingJobResourcePool.get(sk)));
	}

	private void augmentResourcePoolByOne(final Skill skill) {
		concurrentHashMapRepresentingJobResourcePool.put(skill, increment(skill));
	}

	private int increment(final Skill skill) {
		return concurrentHashMapRepresentingJobResourcePool.get(skill) + 1;
	}

	public void lessenResourcePoolByOne(final Skill skill) {
		concurrentHashMapRepresentingJobResourcePool.put(skill, decrement(skill));
		makeLabels(skill);
		repaintAndRevalidate();
	}

	private int decrement(final Skill skill) {
		return concurrentHashMapRepresentingJobResourcePool.get(skill) - 1;
	}

	private void repaintAndRevalidate() {
		repaint();
		revalidate();
	}

	private JLabel newJPanelRepresentingJob(final Skill sk, final Integer i) {
		final JLabel jL = new JLabel();
		jL.setText(setTitle(sk, i));
		return jL;
	}

	private String setTitle(final Skill sk, final Integer i) {
		return sk.skill + ": " + i;
	}

	private JPanel newJPanel() {
		final JPanel jPanel = new JPanel();
		return jPanel;
	}
}
