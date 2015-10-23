package dungeongame;

import java.util.ArrayList;

class InventoryCommand extends Command {

	InventoryCommand() {
		//
		}

	String execute() {
		ArrayList<String> inventoryNames = GameState.instance().getInventoryNames();
		if (inventoryNames.size() == 0) {
			return "You don't have anything.";
			}
		String output = "Inventory:\n";
		
		for (String s : inventoryNames) {
			output += s + "\n";
			}

		return output; 
		}
	}
