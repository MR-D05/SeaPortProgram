public class Skill {
	String skill;

	public Skill(final String skill) {
		this.skill = skill;
	}

	public String gS() {
		return skill;
	}

	public void sS(final String skill) {
		this.skill = skill;
	}

	@Override
	public String toString() {
		return skill;
	}
}