package dungeongame;

class SaveCommand extends Command {

	private String saveFilename;

	SaveCommand(String f) {
		this.saveFilename = "myProgress.sav";
		}

	String execute() {
		try {
			GameState.instance().store(this.saveFilename);
			return "Game saved to: " + this.saveFilename + "!";
			}
		catch (java.io.IOException ioe) {
			return "Error! Save unsuccessful!";
			}
		}

}
