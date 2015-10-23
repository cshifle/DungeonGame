package dungeongame;

import java.util.ArrayList;
import java.util.Hashtable;

class ItemSpecificCommand extends Command {

	private String noun;
	private String verb;

	ItemSpecificCommand(String verb, String noun) {
		this.verb = verb;
		this.noun = noun;
		}

	String execute() {
		Room room = GameState.instance().getAdventurersCurrentRoom();
		ArrayList<String> contents = room.getContents();
		ArrayList<String> inventory = GameState.instance().getInventoryNames();
		Hashtable<String,Item> items = room.getItems();
		String message = "";


		if (items.containsKey(this.noun) || inventory.contains(this.noun)) {
			Item item = GameState.instance().getDungeon().getItem(noun);
			message = item.getMessageForVerb(this.verb);
			if (message != null) {
				return message;
				}
			else {
				return "I don't quite understand what you want to do with " + noun + ".";
				}
			}
		else {
			return "There is no " + noun + " here.";
			}
		}
	}
