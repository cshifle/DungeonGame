package dungeongame;

public class IllegalSaveFormatException extends Exception {

	private String warning;

	public IllegalSaveFormatException() {
		this.warning = "Save file corrupt.";
	}

	public IllegalSaveFormatException(String warning) {
		this.warning = warning;
	}

	public String getWarning() {
		return this.warning;
	}

}
