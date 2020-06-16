import java.util.Scanner;

public class Person extends Thing {
	private final String formattedstring1 = "Person: ";
	private final String formattedstring2 = " ";
	private String skill;

	public Person(final Scanner sc) {
		super(sc);
		if (sc.hasNext())
			skill = sc.next();
	}

	public String returnSkill() {
		return skill;
	}

	public void setSkill(final String sk) {
		this.skill = sk;
	}

	@Override
	public String toString() {
		return (formattedstring1 + super.toString() + formattedstring2 + returnSkill());
	}
}
