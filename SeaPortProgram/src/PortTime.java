public class PortTime {
	private int time;

	public PortTime() {
		this.time = 0;
	}

	public int returnTime() {
		return time;
	}

	public void setTime(final int t) {
		if (t == 0 || t > 0)
			this.time = t;
	}
}
