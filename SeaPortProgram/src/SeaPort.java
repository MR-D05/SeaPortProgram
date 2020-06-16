import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class SeaPort extends Thing {
	final ConcurrentHashMap<Skill, Integer> pool;
	private final String s = "\n\nSeaPort: ";
	private final String s1 = "\n";
	private final String s2 = "\n   > ";
	private final String s3 = "\n\n --- List of all ships:";
	private final String s4 = "\n --- List of all ships in queue:";
	private final String s5 = "\n\n --- List of all persons:";
	private ArrayList<Dock> docks;
	private ArrayList<Ship> queue;
	private ArrayList<Ship> ships;
	private ArrayList<Person> persons;

	public SeaPort(final Scanner sc) {
		super(sc);
		docks = new ArrayList<>();
		queue = new ArrayList<>();
		ships = new ArrayList<>();
		persons = new ArrayList<>();
		pool = new ConcurrentHashMap<>();
	}

	public ArrayList<Dock> returnDockList() {
		return docks;
	}

	public void setDockList(final ArrayList<Dock> d) {
		this.docks = d;
	}

	public ArrayList<Ship> returnQueue() {
		return queue;
	}

	public void setQueue(final ArrayList<Ship> q) {
		this.queue = q;
	}

	public ArrayList<Ship> returnShipList() {
		return ships;
	}

	public void setShipList(final ArrayList<Ship> s) {
		this.ships = s;
	}

	public ArrayList<Person> returnPersonList() {
		return persons;
	}

	public void setPersonList(final ArrayList<Person> persons) {
		this.persons = persons;
	}

	public ConcurrentHashMap<Skill, Integer> getResourcePool() {
		return pool;
	}

	public void setWhetherJobCanBeDoneOrNotAccordingToAvailableTalent() {
		for (final Iterator<Ship> iterator = ships.iterator(); iterator.hasNext();) {
			final Ship ship = iterator.next();
			for (final Iterator<Job> iterator2 = ship.jobs.iterator(); iterator2.hasNext();) {
				final Job job = iterator2.next();
				for (final Iterator<Skill> iterator3 = job.returnRequirements().iterator(); iterator3.hasNext();) {
					final Skill skill = iterator3.next();
					if (pool.containsKey(skill))
						continue;
					job.setAsAnApplicableJob(false);
				}
			}
		}
	}

	public String toString() {
		String string = s + super.toString() + s1;
		for (final Iterator<Dock> iterator = docks.iterator(); iterator.hasNext();) {
			final Dock md = iterator.next();
			string += s1 + md;
		}
		string += s4;
		for (final Iterator<Ship> iterator = queue.iterator(); iterator.hasNext();) {
			final Ship mq = iterator.next();
			string += s2 + mq;
		}
		string += s3;
		for (final Iterator<Ship> iterator = ships.iterator(); iterator.hasNext();) {
			final Ship ms = iterator.next();
			string += s2 + ms;
		}
		string += s5;
		for (final Iterator<Person> iterator = persons.iterator(); iterator.hasNext();) {
			final Person mp = iterator.next();
			string += s2 + mp;
		}
		return string;
	}
}
