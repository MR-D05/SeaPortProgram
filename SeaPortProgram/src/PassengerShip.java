import java.util.Scanner;

public class PassengerShip extends Ship {
	private final String formattedstring1 = "Passenger ship: ";
	private int numberOfOccupiedRooms;
	private int numberOfPassengers;
	private int numberOfRooms;

	public PassengerShip(final Scanner sc) {
		super(sc);
		if (sc.hasNextInt())
			numberOfPassengers = sc.nextInt();
		if (sc.hasNextInt())
			numberOfRooms = sc.nextInt();
		if (sc.hasNextInt())
			numberOfOccupiedRooms = sc.nextInt();
	}

	public int returnNumberOfOccupiedRooms() {
		return numberOfOccupiedRooms;
	}

	public void setNumberOfOccupiedRooms(final int r) {
		this.numberOfOccupiedRooms = r;
	}

	public int returnNumberOfPassengers() {
		return numberOfPassengers;
	}

	public void setNumberOfPassengers(final int p) {
		this.numberOfPassengers = p;
	}

	public int returnNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(final int r) {
		this.numberOfRooms = r;
	}

	@Override
	public String toString() {
		return (formattedstring1 + super.toString());
	}
}
