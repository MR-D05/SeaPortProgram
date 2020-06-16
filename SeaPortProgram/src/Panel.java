import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel extends JPanel {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	JLabel dockJLabel;
	JLabel jLabel;
	JLabel setJLabelTitle;
	JPanel tP;
	JPanel mP;
	JPanel lP;
	JButton progressState;
	JButton killSwitch;
	Progress progressBar;

public Panel(final String d, final int tempJob, final int upperLimit) {

		dockJLabel = new JLabel(d);
		setJLabelTitle = shipLabel();
		jLabel = jobLabel(tempJob, upperLimit);
		tP = new JPanel();
		progressBar = new Progress();
		mP = new JPanel();

		progressState = haltButton();

		progressState.addActionListener(e -> {
			final boolean hasPermission = progressBar.hasPermission();
			if (hasPermission)
				progressState.setText("Halt");
			else
				progressState.setText("Continue");
		});

		killSwitch = killSwitch();

		killSwitch.addActionListener(e -> progressBar.kill());

		lP = new JPanel();
		lP.add(progressState);
		lP.add(killSwitch);
		setLayout(new GridLayout(0, 1));
		tP.add(dockJLabel);
		tP.add(setJLabelTitle);
		tP.add(jLabel);
		mP.add(progressBar);
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

	public void reviseTextOnJLabel(final int tempJob, final int upperLimit) {
		jLabel.setText(generateText(tempJob, upperLimit));
		reviseJL();
	}

	private void reviseJL() {
		jLabel.revalidate();
		jLabel.repaint();
	}

	public void reviseShipList(final String s) {
		setJLabelTitle.setText("Ship: " + s);
		setJLabelTitle.revalidate();
		setJLabelTitle.repaint();
	}

	private String generateText(final int x, final int y) {
		return jL(x, y);
	}

	public Progress returnProgressBar() {
		return progressBar;
	}
}
