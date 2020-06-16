import javax.swing.JProgressBar;

class Progress extends JProgressBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean sigKill;
	private Boolean sigPerm;

	public Progress() {
		this.sigKill = false;
		this.sigPerm = true;
	}

	public boolean hasPermission() {
		synchronized (sigPerm) {
			sigPerm = ((sigPerm == true) == false);
			if ((sigPerm == true) == false)
				setString("Halted");
			else
				setString("Progressing");
		}
		return sigPerm;
	}

	public void kill() {
		sigKill = true;
	}

	public boolean returnSigKill() {
		return sigKill;
	}

	public boolean returnSigPerm() {
		return sigPerm;
	}
}