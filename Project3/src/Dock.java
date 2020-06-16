import java.util.ArrayList;
import java.util.Scanner;

public class Dock extends Thing {

	ArrayList<Ship> ships;

	public Dock(final Scanner sc) {
		super(sc);
		ships = new ArrayList<>();
	}

	public ArrayList<Ship> returnShip() {
		return ships;
	}

	public void setShip(final Ship ship) {
		this.ships.add(ship);
	}

	@Override
	public String toString() {
		final StringBuffer sB = new StringBuffer();
		final String string = "  Dock: " + super.toString() + "\n";
		sB.append(string);
		for (final Ship ship : ships) {
			final String string1 = "    Ship: " + ship.toString() + "\n";
			sB.append(string1);
		}
		return sB.toString();
	}
}