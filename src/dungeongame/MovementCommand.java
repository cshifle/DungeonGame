package dungeongame;

class MovementCommand extends Command {

	private String dir;

	MovementCommand(String dir) {
		this.dir = dir;
		}

	String execute() {
		Room currentRoom = GameState.instance().getAdventurersCurrentRoom();
		Room nextRoom;

		if (this.dir.contains(" ")) {
			String[] s = dir.split(" ");
			this.dir = s[s.length-1];
			}
		if (dir.length() > 2 && CommandFactory.directions.containsKey(dir)) {
			this.dir = CommandFactory.directions.get(dir);
			}

		nextRoom = GameState.instance().getAdventurersCurrentRoom().leaveBy(this.dir);
	

		if (CommandFactory.directions.containsKey(dir) || CommandFactory.directions.containsValue(dir)) {
			
			if (nextRoom != null) {
				GameState.instance().setAdventurersCurrentRoom(nextRoom);
				return nextRoom.describe();
				}

			else {
				return "You cannot move " + CommandFactory.directions.get(dir) + ".";
				}
			}

		return "Where exactly is " + dir + "?";
		}
	}
