import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class World extends Thing {
	public World(final Scanner sc) {
		super(sc);
		ports = new ArrayList<>();
	}

	private final String s = ">>>>> The world:\n";
	private ArrayList<SeaPort> ports;
	private PortTime time;
	
    public void validateSeaPortJobs(){
        ports.forEach(SeaPort::setWhetherJobCanBeDoneOrNotAccordingToAvailableTalent);
    }
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(">>>>> The world:\n");

        for(SeaPort port : ports) {
            sb.append(port + "\n");
        }

        return sb.toString();
    }

	private void addDockToPort(final Dock dock, final SeaPort port) {
		port.returnDockList().add(dock);
	}

	private void addPersonToPort(final Person person, final SeaPort port) {
		port.returnPersonList().add(person);
	}

	private void addPortToPortList(final SeaPort p) {
		ports.add(p);
	}

	private void addShipToPort(final Ship s) {
		returnSeaPort(s.getParent()).returnShipList().add(s);
	}

	private void addShipToQueue(final Ship ship) {
		returnSeaPort(ship.getParent()).returnQueue().add(ship);
	}

	private StringBuilder buildString() {
		final StringBuilder stBuild = new StringBuilder();
		stBuild.append(s);
		return stBuild;
	}

	private boolean dockIsChild(final Dock d, final SeaPort p) {
		return p.returnIndex() == d.getParent();
	}

	private boolean portIsChild(final Person p, final SeaPort port) {
		return port.returnIndex() == p.getParent();
	}

	private Dock returnDock(final Ship s) {
		return returnDock(s.getParent());
	}

	public void addDock(final Dock d) {
		for (final Iterator<SeaPort> iterator = ports.iterator(); iterator.hasNext();) {
			final SeaPort p = iterator.next();
			if (dockIsChild(d, p)) {
				addDockToPort(d, p);
			}
		}
	}

	public void addPort(final SeaPort p) {
		if ((p == null) == false) {
			addPortToPortList(p);
		}
	}

	public void addPerson(final Person p) {
		for (final Iterator<SeaPort> iterator = ports.iterator(); iterator.hasNext();) {
			final SeaPort port = iterator.next();
			if (portIsChild(p, port)) {
				addPersonToPort(p, port);
			}
		}
	}

	public void addShip(final Ship s) {
		final Dock d = returnDock(s);
		if (!((d == null) != true)) {
			addShipToPort(s);
			addShipToQueue(s);
			return;
		}
		d.setShip(s);
		returnSeaPort(d.getParent()).returnShipList().add(s);
	}

    public void assignJob(Job j){
        for(SeaPort sp:ports){
            for(Ship ship:sp.returnShipList()){
                System.out.println();
                if(ship.returnIndex() == j.getParent()){
                    ship.addJob(j);
                }
            }
        }
    }

	public ArrayList<SeaPort> returnPortList() {
		return ports;
	}

	public PortTime returnTime() {
		return time;
	}

	public Dock returnDock(final int i) {
		for (final Iterator<SeaPort> iterator = ports.iterator(); iterator.hasNext();) {
			final SeaPort p = iterator.next();
			for (final Iterator<Dock> iterator2 = p.returnDockList().iterator(); iterator2.hasNext();) {
				final Dock dock = iterator2.next();
				if (indexMatchesDock(i, dock))
					return dock;
			}
		}
		return null;
	}

	private boolean indexMatchesDock(final int i, final Dock d) {
		return d.returnIndex() == i;
	}

	public Ship returnShip(final int i) {
		for (final Iterator<SeaPort> iterator = ports.iterator(); iterator.hasNext();) {
			final SeaPort p = iterator.next();
			for (final Iterator<Ship> iterator2 = p.returnShipList().iterator(); iterator2.hasNext();) {
				final Ship s = iterator2.next();
				if (indexMatchesShip(i, s))
					return s;
			}
		}
		return null;
	}

	private boolean indexMatchesShip(final int i, final Ship s) {
		return s.returnIndex() == i;
	}

	public SeaPort returnSeaPort(final int i) {
		for (final Iterator<SeaPort> iterator = ports.iterator(); iterator.hasNext();) {
			final SeaPort p = iterator.next();
			if (indexMatchesPort(i, p))
				return p;
		}
		return null;
	}

	private boolean indexMatchesPort(final int i, final SeaPort port) {
		return port.returnIndex() == i;
	}

	public void setPortList(final ArrayList<SeaPort> p) {
		this.ports = p;
	}

	public void setTime(final PortTime t) {
		this.time = t;
	}
}
