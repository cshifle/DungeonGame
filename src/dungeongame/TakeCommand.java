package dungeongame;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;

class TakeCommand extends Command {

	private String itemName;

	TakeCommand(String itemName) {
		this.itemName = itemName;
	}

	String execute() {
		try {
			int load = GameState.instance().getInventoryWeight();
			int queuedLoad = 0;
			Item item = null;
			if (itemName.equals("all") == false) {
				item = GameState.instance().getDungeon().getItem(this.itemName);
				}

			Room room = GameState.instance().getAdventurersCurrentRoom();
			ArrayList<String> contents		= room.getContents();
			ArrayList<String> queuedItems	= new ArrayList<String>();
			ArrayList<String> inventory		= GameState.instance().getInventoryNames();
			Hashtable<String,Item> items	= room.getItems();
			Enumeration itemKeys = items.keys();
			String output = "";

			// Take all items
			if (itemName.equals("all") && (contents.size() > 0)) {
				
				for (String s: contents) {
					queuedItems.add(s);
					output += Ansi.GREEN + s + Ansi.SANE + " taken!\n";
					}
				queuedLoad = calculateLoad(queuedItems);
				
				// Check Load Weight
				if (canTakeLoad(load,queuedLoad) == false) {
					return "Your load is too heavy to take everything.";
				}
				for (String s: queuedItems) {
					GameState.instance().addToInventory(GameState.instance().getDungeon().getItem(s));
					}
				return output;
				}
			else if (itemName.equals("all") && (contents.size() == 0)) {
				return "There's nothing here.";
				}
			else if (inventory.contains(itemName)) {
				return "You already have it.";
				}
			// Take Single Item
			else {
				while (itemKeys.hasMoreElements()) {
					if (itemKeys.nextElement().equals(this.itemName)) {
						queuedLoad += item.getWeight();
						
						// Check Load Weight
						if (canTakeLoad(load,queuedLoad) == false) {
							return "Your load is too heavy.";
							}
						GameState.instance().addToInventory(item);
						return Ansi.GREEN + item.getPrimaryName() + Ansi.SANE + " taken!";	
						}
					}
				}
			}
		catch (Exception e) {
			e.printStackTrace(); // Debugging Purposes..
			return "Exception: Can't find " + this.itemName + "!"; // To differentiate between "can't find" messages.
			}

		return "Can't find " + this.itemName + "!";
	}

	// Calculate Load
	private int calculateLoad(ArrayList<String> items) {
		int load = 0;
		for (String s:items) {
			load += GameState.instance().getDungeon().getItem(s).getWeight();
			}
		return load;
		}

	// Weight Limit
	private boolean canTakeLoad(int currentLoad, int queuedLoad) {
		if (currentLoad + queuedLoad <= 40) {
			return true;
			}
		else {
			return false;
			}
		}
}
