import java.util.Comparator;

public class QueueComparator implements Comparator<Ship> {
	private final String selection;
	private final String selection1;

	public QueueComparator(final String selection, final String selection1) {
		this.selection = selection;
		this.selection1 = selection1;
	}

	@Override
	public int compare(final Ship ship, final Ship ship1) {
		int resultValue = 0;
		String selection = this.selection;
		if (selection.equals("Weight")) {
			if (firstShipHeavier(ship, ship1))
				resultValue = 1;
			else if (firstShipLighter(ship, ship1))
				resultValue = -1;
			else
				resultValue = 0;
		} else if (selection.equals("Length")) {
			if (firstShipLonger(ship, ship1)) {
				resultValue = 1;
			} else if (firstShipShorter(ship, ship1)) {
				resultValue = -1;
			} else {
				resultValue = 0;
			}
		} else if (selection.equals("Width")) {
			if (firstShipWider(ship, ship1)) {
				resultValue = 1;
			} else if (firstShipNarrower(ship, ship1)) {
				resultValue = -1;
			} else {
				resultValue = 0;
			}
		} else if (selection.equals("Draft")) {
			if (firstShipDraftGreater(ship, ship1)) {
				resultValue = 1;
			} else if (firstShipDraftLesser(ship, ship1)) {
				resultValue = -1;
			} else {
				resultValue = 0;
			}
		}
		switch (selection1) {
		case "Descending":
			return -1 * resultValue;
		default:
			return resultValue;
		}
	}

	private boolean firstShipDraftLesser(final Ship ship, final Ship ship1) {
		return ship.gD() < ship1.gD();
	}

	private boolean firstShipDraftGreater(final Ship ship, final Ship ship1) {
		return ship.gD() > ship1.gD();
	}

	private boolean firstShipNarrower(final Ship ship, final Ship ship1) {
		return ship.getWidth() < ship1.getWidth();
	}

	private boolean firstShipWider(final Ship ship, final Ship ship1) {
		return ship.getWidth() > ship1.getWidth();
	}

	private boolean firstShipShorter(final Ship ship, final Ship ship1) {
		return ship.gL() < ship1.gL();
	}

	private boolean firstShipLonger(final Ship ship, final Ship ship1) {
		return ship.gL() > ship1.gL();
	}

	private boolean firstShipLighter(final Ship ship, final Ship ship1) {
		return ship.gW() < ship1.gW();
	}

	private boolean firstShipHeavier(final Ship ship, final Ship ship1) {
		return ship.gW() > ship1.gW();
	}
}