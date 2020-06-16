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
        ports.forEach(SeaPort::verify);
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

	private void addDock(final Dock d, final SeaPort p) {
		p.gD().add(d);
	}

	private void addPersonToPort(final Person p, final SeaPort port) {
		port.gP().add(p);
	}

	private void addPort(final SeaPort p) {
		ports.add(p);
	}

	private void addShipToPort(final Ship s) {
		rSP(s.getParent()).gS().add(s);
	}

	private void addShipToQueue(final Ship s) {
		rSP(s.getParent()).gQ().add(s);
	}

	private StringBuilder buildString() {
		final StringBuilder stBuild = new StringBuilder();
		stBuild.append(s);
		return stBuild;
	}

	private boolean dIsChild(final Dock d, final SeaPort p) {
		return p.rI() == d.getParent();
	}

	private boolean pIsChild(final Person p, final SeaPort port) {
		return port.rI() == p.getParent();
	}

	private Dock returnDock(final Ship s) {
		return rD(s.getParent());
	}

	public void aD(final Dock d) {
		for (final Iterator<SeaPort> iterator = ports.iterator(); iterator.hasNext();) {
			final SeaPort p = iterator.next();
			if (dIsChild(d, p)) {
				addDock(d, p);
			}
		}
	}

	public void aP(final SeaPort p) {
		if ((p == null) == false) {
			addPort(p);
		}
	}

	public void aPer(final Person p) {
		for (final Iterator<SeaPort> iterator = ports.iterator(); iterator.hasNext();) {
			final SeaPort port = iterator.next();
			if (pIsChild(p, port)) {
				addPersonToPort(p, port);
			}
		}
	}

	public void aS(final Ship s) {
		final Dock d = returnDock(s);
		if (!((d == null) != true)) {
			addShipToPort(s);
			addShipToQueue(s);
			return;
		}
		d.setShip(s);
		rSP(d.getParent()).gS().add(s);
	}

    public void assignJob(Job j){
        //for aSPae
        for(SeaPort sp:ports){
            for(Ship ship:sp.gS()){
                System.out.println();
                if(ship.rI() == j.getParent()){
                    ship.addJob(j);
                }
            }
        }
    }

	public ArrayList<SeaPort> gP() {
		return ports;
	}

	public PortTime gT() {
		return time;
	}

	public Dock rD(final int i) {
		for (final Iterator<SeaPort> iterator = ports.iterator(); iterator.hasNext();) {
			final SeaPort p = iterator.next();
			for (final Iterator<Dock> iterator2 = p.gD().iterator(); iterator2.hasNext();) {
				final Dock d = iterator2.next();
				if (indicesIDMatch(i, d))
					return d;
			}
		}
		return null;
	}

	private boolean indicesIDMatch(final int i, final Dock d) {
		return d.rI() == i;
	}

	public Ship rS(final int i) {
		for (final Iterator<SeaPort> iterator = ports.iterator(); iterator.hasNext();) {
			final SeaPort p = iterator.next();
			for (final Iterator<Ship> iterator2 = p.gS().iterator(); iterator2.hasNext();) {
				final Ship s = iterator2.next();
				if (indicesISMatch(i, s))
					return s;
			}
		}
		return null;
	}

	private boolean indicesISMatch(final int i, final Ship s) {
		return s.rI() == i;
	}

	public SeaPort rSP(final int i) {
		for (final Iterator<SeaPort> iterator = ports.iterator(); iterator.hasNext();) {
			final SeaPort p = iterator.next();
			if (indicesIPMatch(i, p))
				return p;
		}
		return null;
	}

	private boolean indicesIPMatch(final int i, final SeaPort p) {
		return p.rI() == i;
	}

	public void sP(final ArrayList<SeaPort> p) {
		this.ports = p;
	}

	public void sT(final PortTime t) {
		this.time = t;
	}
}
