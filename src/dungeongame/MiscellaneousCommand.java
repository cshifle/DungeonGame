package dungeongame;

class MiscellaneousCommand extends Command {

	private String command;

	MiscellaneousCommand(String command) {
		this.command = command;
		}

	public String execute() {
		
		// Look Command
		if (command.equals("look")) {
			Room room = GameState.instance().getAdventurersCurrentRoom();
			room.reset();
			return room.describe();
			}

		return "Need to add support for " + command + ".";
		}
}
