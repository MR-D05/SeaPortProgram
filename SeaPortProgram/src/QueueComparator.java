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
		return ship.returnDraft() < ship1.returnDraft();
	}

	private boolean firstShipDraftGreater(final Ship ship, final Ship ship1) {
		return ship.returnDraft() > ship1.returnDraft();
	}

	private boolean firstShipNarrower(final Ship ship, final Ship ship1) {
		return ship.returnWidth() < ship1.returnWidth();
	}

	private boolean firstShipWider(final Ship ship, final Ship ship1) {
		return ship.returnWidth() > ship1.returnWidth();
	}

	private boolean firstShipShorter(final Ship ship, final Ship ship1) {
		return ship.returnLength() < ship1.returnLength();
	}

	private boolean firstShipLonger(final Ship ship, final Ship ship1) {
		return ship.returnLength() > ship1.returnLength();
	}

	private boolean firstShipLighter(final Ship ship, final Ship ship1) {
		return ship.returnWeight() < ship1.returnWeight();
	}

	private boolean firstShipHeavier(final Ship ship, final Ship ship1) {
		return ship.returnWeight() > ship1.returnWeight();
	}
}