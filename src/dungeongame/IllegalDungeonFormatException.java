package dungeongame;

public class IllegalDungeonFormatException extends Exception {

	private String warning;

	public IllegalDungeonFormatException() {
		this.warning = "Dungeon format corrupt.";
	}

	public IllegalDungeonFormatException(String warning) {
		this.warning = warning;
	}

	public String getWarning() {
		return this.warning;
	}
}
