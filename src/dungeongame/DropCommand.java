package dungeongame;

import java.util.ArrayList;

class DropCommand extends Command {

	private String itemName;

	DropCommand(String itemName) {
		this.itemName = itemName;
		}

	String execute() {
		try {
			ArrayList<String> inventory = GameState.instance().getInventoryNames();
			ArrayList<String> queuedItems = new ArrayList<String>();
			Item item = null;
			String output = "";

			if (this.itemName.equals("all") == false) {
				item = GameState.instance().getDungeon().getItem(itemName);
				}

			if (this.itemName.equals("all")) {
				if (inventory.size() > 0) {
					for (String s: inventory) {
						queuedItems.add(s);
						}
					for (String s: queuedItems) {
						GameState.instance().removeFromInventory(GameState.instance().getDungeon().getItem(s));
						output += s + " dropped!\n";
						}
					return output;
					}
				else {
					return "You don't have anything.";
					}
				}
			GameState.instance().removeFromInventory(item);
			}
		catch (Exception e) {
			return "You don't have a " + itemName + ".";
			}
		return itemName + " dropped!";
		}
	}

