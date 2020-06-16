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

public class Job extends Thing implements Runnable {
	
	Thread thread;
	double duration;
	private boolean isReadyAndWaiting;
	private boolean isAnApplicableJob;
	ArrayList<Skill> requirements;

	public Job(final Scanner sc) {
		super(sc);
		if (sc.hasNextDouble())
			duration = sc.nextDouble();
		else
			duration = 0.0d;
		requirements = new ArrayList<>();
		isReadyAndWaiting = false;
		isAnApplicableJob = true;
		thread = new Thread();
	}

	public double returnDuration() {
		return duration;
	}

	public void setDuration(final double d) {
		this.duration = d;
	}

	public ArrayList<Skill> returnRequirements() {
		return requirements;
	}

	public void setRequirements(final ArrayList<Skill> requirements) {
		this.requirements = requirements;
	}

	public boolean isAnApplicableJob() {
		return isAnApplicableJob;
	}

	public void setAsAnApplicableJob(final boolean valid) {
		isAnApplicableJob = valid;
	}

	public boolean isReadyAndWaiting() {
		return isReadyAndWaiting;
	}

	public void setAsReadyAndWaiting(final boolean ready) {
		isReadyAndWaiting = ready;
	}
	
	@Override
	public void run() {
		this.thread.start();
	}
}