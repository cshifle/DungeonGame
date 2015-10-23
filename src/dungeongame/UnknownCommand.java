package dungeongame;

class UnknownCommand extends Command {

	private String bogusCommand;

	UnknownCommand(String bogusCommand) {
		this.bogusCommand = bogusCommand;
		}

	String execute() {
		return "I don't understand the command " + bogusCommand + ".";
		}
	}
