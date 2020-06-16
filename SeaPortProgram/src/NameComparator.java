import java.util.Comparator;

public class NameComparator implements Comparator<Thing> {

	@Override
	public int compare(final Thing thing, final Thing thing1) {
		return itsName(thing).compareToIgnoreCase(itsName(thing1));
	}

	private String itsName(final Thing thing1) {
		return thing1.getName();
	}
}