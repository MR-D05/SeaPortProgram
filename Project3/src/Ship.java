import java.util.ArrayList;
import java.util.Scanner;

public class Ship extends Thing {
	private PortTime arrivalTime;
	private PortTime dockTime;
	private double draft;
	private double length;
	private double weight;
	private double width;
	
	ArrayList<Job> jobs;

	public Ship(final Scanner sc) {
		super(sc);
		if (sc.hasNextDouble())
			weight = sc.nextDouble();
		if (sc.hasNextDouble())
			length = sc.nextDouble();
		if (sc.hasNextDouble())
			width = sc.nextDouble();
		if (sc.hasNextDouble())
			draft = sc.nextDouble();
		jobs = new ArrayList<>();
	}

	public PortTime gAT() {
		return arrivalTime;
	}

	public void sAT(final PortTime t) {
		this.arrivalTime = t;
	}

	public PortTime gDT() {
		return dockTime;
	}

	public void sDT(final PortTime t) {
		this.dockTime = t;
	}

	public double gD() {
		return draft;
	}

	public void sD(final double d) {
		if (d == 0 || d > 0)
			this.draft = d;
	}

	public double gL() {
		return length;
	}

	public void sL(final double l) {
		if (l >= l)
			this.length = l;
	}

	public double gW() {
		return weight;
	}

	public void setWeight(final double w) {
		if (w == 0.0 || w > 0.0)
			this.weight = w;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(final double w) {
		if (w == 0.0 || w > 0.0)
			this.weight = w;
	}

	public ArrayList<Job> gJ() {
		return jobs;
	}

	public void sJ(final ArrayList<Job> j) {
		this.jobs = j;
	}
	
    public void addJob(Job job){
        this.jobs.add(job);
    }

	@Override
	public String toString() {
		return super.toString();
	}
}
