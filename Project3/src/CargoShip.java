import java.util.Scanner;

public class CargoShip extends Ship {
	private double cargoValue;
	private double cargoVolume;
	private double cargoWeight;

	public CargoShip(final Scanner sc) {
		super(sc);
		if (sc.hasNextDouble())
			cargoValue = sc.nextDouble();
		if (sc.hasNextDouble())
			cargoVolume = sc.nextDouble();
		if (sc.hasNextDouble())
			cargoWeight = sc.nextDouble();
	}

	public double getCargoValue() {
		return cargoValue;
	}

	public void setCargoValue(final double cVal) {
		if (cVal >= 0.0)
			this.cargoValue = cVal;
	}

	public double getCargoVolume() {
		return cargoVolume;
	}

	public void setCargoVolume(final double cVol) {
		if (cVol >= 0.0)
			this.cargoVolume = cVol;
	}

	public double getCargoWeight() {
		return cargoWeight;
	}

	public void setCargoWeight(final double cW) {
		if (cW >= 0.0)
			this.cargoWeight = cW;
	}

	@Override
	public String toString() {
		return "Cargo Ship: " + super.toString();
	}
}
