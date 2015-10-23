package dungeongame;

import java.util.Hashtable;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

class CommandFactory {

	private static CommandFactory theInstance;

	// Command Types
	public static Hashtable <String,String> directions = new Hashtable <String,String>();
	public static List <String> movementCommands;
	public static List <String> saveCommands;
	public static List <String> inventoryCommands;
	public static List <String> takeCommands;
	public static List <String> dropCommands;
	public static List <String> miscCommands;
	public static ArrayList <String> itemSpecificCommands = new ArrayList <String>();

	// Instance
	public static CommandFactory instance() {
		if (theInstance == null) {
			theInstance = new CommandFactory();
			}
		return theInstance;
		}

	private CommandFactory() {
		
		// Directions
		directions.put("n", "north");
		directions.put("s","south");	
		directions.put("e","east");	
		directions.put("w","west");		
		directions.put("nw", "northwest");
		directions.put("sw", "southwest");
		directions.put("ne", "northeast");
		directions.put("se", "southeast");
		directions.put("u", "up");		
		directions.put("d", "down");
		
		directions.put("north", "n");		
		directions.put("south", "s");		
		directions.put("east", "e");		
		directions.put("west", "w");		
		directions.put("northwest", "nw");		
		directions.put("southwest", "sw");		
		directions.put("northeast", "ne");		
		directions.put("southeast", "se");	
		directions.put("up", "u");		
		directions.put("down", "d");	
	
		// Movement Commands
		this.movementCommands = Arrays.asList(
			"move",		"go",		"strut",	"traverse",
			"crawl",	"walk",		"moonwalk",	"travel",
			"wander",	"meander",	"roam",		"diddybop",
			"run",		"sprint",	"march",	"step",
			"hike",		"stroll",	"skip",		"m"
			);

		// Take Commands
		this.takeCommands = Arrays.asList(
			"take", "pick up", "grab", "acquire", "collect", "snag", "get", "secure",
			"snatch", "seize", "t"
			);

		// Drop Commands
		this.dropCommands = Arrays.asList(
			"drop", "lose", "discard", "ditch", "dump", "throw"
			);
		
		// Save Commands
		this.saveCommands = Arrays.asList(
			"save", "save game", "save to", "save as"
			);

		// Inventory Commands
		this.inventoryCommands = Arrays.asList(
			"view i", "view inv", "view inventory", "inventory", "inv", "i",
			"check i", "check inv", "check inventory", "view items", "items",
			"show inv", "get inv"
			);
	
		// Misc Commands
		this.miscCommands = Arrays.asList(
			"look"
			);		

		// Item Specific Commands

	}

	Command parse (String command) {
		String[] phrase = {" "};
		String altCmd = "";
		String itemName = null;
		Command itemCommand = null;

		if (command.contains(" ")) {
			phrase = command.split(" ");
			altCmd = phrase[0] + " " + phrase[1];
			itemName =  matchItem(phrase);
			itemCommand = matchItemVerb(phrase,itemName);			
			}
		else {
			phrase[0] = command;
			}
		if (itemCommand != null) {
			return itemCommand;
			}
		if (directions.containsKey(phrase[0]) || movementCommands.contains(phrase[0]) || directions.containsValue(phrase[0])) {
			return new MovementCommand(command);
			}
		else if (takeCommands.contains(phrase[0]) || takeCommands.contains(altCmd)) {
			return new TakeCommand(itemName);
			}
		else if (inventoryCommands.contains(phrase[0]) || inventoryCommands.contains(altCmd)) {
			return new InventoryCommand();
			}
		else if (dropCommands.contains(phrase[0]) || dropCommands.contains(altCmd)) {
			return new DropCommand(itemName);
			}
		else if (saveCommands.contains(phrase[0])) {
			return new SaveCommand(phrase[0]);
			}
		else if (miscCommands.contains(phrase[0])) {
			return new MiscellaneousCommand(phrase[0]);
			}
		else {
			return new UnknownCommand(command);
			}
		}

	String matchItem(String[] phrase) {
		Hashtable<String,Item> items = GameState.instance().getDungeon().getItems();
		String s = null;
		for (int i = 0; i < phrase.length; i++) {
			s = "";
			for (int j = i; j < phrase.length; j++) {
				//System.out.println("S: " + s);
				s += phrase[j] + " ";
				}
			s = s.trim();
			//System.out.println("checking... " + s);
			if (s.equals("all")) {
				return s;
				}
			if (items.containsKey(s)) {
				//System.out.println("Item found: " + s);
				return items.get(s).getPrimaryName();
				}
			}
		return "it";
		}

	Command matchItemVerb(String[] phrase, String itemName) {
		String s = null;
		if (itemName.equals("all") || itemName.equals("it")) {
			return null;
			}
		for (int i = 0; i < phrase.length; i++) {
			s = "";
			for (int j = 0; j < phrase.length - i; j++) {
				s += phrase[j] + " ";
				}
			s = s.trim();
			//System.out.println("checking..." + s);			
			if (itemSpecificCommands.contains(s)) {
				//System.out.println("Found verb: " + s);
				return new ItemSpecificCommand(s,itemName);
				}
			}
 
		return null;
		}



}






