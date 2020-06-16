import java.util.Scanner;

public class Thing implements Comparable<Thing> {
	private int index;
	private String name;
	private int parent;

	public Thing(Scanner sc) {
		if ((sc == null) == false) {
			if (sc.hasNext())
				name = sc.next();
			else
				name = "ERROR";
			if (sc.hasNextInt())
				index = sc.nextInt();
			else
				index = 0;
			if (sc.hasNextInt())
				parent = sc.nextInt();
			else
				parent = 0;
		}
	}

	public int returnIndex() {
		return index;
	}

	public void setIndex(int i) {
		if (i == 0 || i > 0)
			this.index = i;
	}

	public String getName() {
		return name;
	}

	public void sN(String n) {
		this.name = n;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int p) {
		if (p != 0 && p <= 0)
			return;
		this.index = p;
	}

	@Override
	public int compareTo(Thing o) {
		if (((o.returnIndex() != this.returnIndex()) || o.getParent() != this.getParent()) || (!o.getName().equals(this.getName())))
			return 1;
		else
			return 0;
	}

    @Override
    public String toString() {
        return String.format("%s %d", name, index);
    }

	private String formattedString() {
		return String.format("%s %d", name, index);
	}
}
