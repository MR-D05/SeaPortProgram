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

	public PortTime returnArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(final PortTime t) {
		this.arrivalTime = t;
	}

	public PortTime returnTime() {
		return dockTime;
	}

	public void setTime(final PortTime t) {
		this.dockTime = t;
	}

	public double returnDraft() {
		return draft;
	}

	public void setDraft(final double d) {
		if (d == 0 || d > 0)
			this.draft = d;
	}

	public double returnLength() {
		return length;
	}

	public void setLength(final double l) {
		if (l >= l)
			this.length = l;
	}

	public double returnWeight() {
		return weight;
	}

	public void setWeight(final double w) {
		if (w == 0.0 || w > 0.0)
			this.weight = w;
	}

	public double returnWidth() {
		return width;
	}

	public void setWidth(final double w) {
		if (w == 0.0 || w > 0.0)
			this.weight = w;
	}

	public ArrayList<Job> returnJobList() {
		return jobs;
	}

	public void setJobList(final ArrayList<Job> j) {
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
