package dungeongame;

import java.util.Hashtable;
import java.util.Set;
import java.util.Enumeration;
import java.util.Scanner; 
import java.io.PrintWriter;
import java.util.ArrayList;

public class Room {
	
	static class NoRoomException extends Exception {}

	private String title;
	private String desc;
	private boolean beenHere;
	private boolean itemsAliased;
	private Hashtable < String, Exit > exits;
	private ArrayList<String> contents = new ArrayList<String>();
	private Hashtable <String,Item> items = new Hashtable<String,Item>();	

	// Construct from File
	public Room(Scanner s, Dungeon d, boolean initState)	throws	IllegalDungeonFormatException, 
																	NoRoomException {
		String line = s.nextLine();
		boolean addedContents = false;
		String[] split;
		boolean firstLine = true;

		// Set room title
		if (line.contains("===") == true) {
			throw new NoRoomException();
			}
		this.title = line;

		// Add contents (before description)
		line = s.nextLine();
		if (line.contains("Contents:") == true) {
			split = line.split(": ");
			split = split[1].split(",");	
			for (int x = 0; x < split.length; x++) {
				split[x] = split[x].trim();
				if (initState == true) { // .bork File
					this.contents.add(split[x]);
					}
				}
			addedContents = true;
			}

		// Set Description
		if (addedContents == false) {
			this.desc = line;
			firstLine = false;
			}
		else {
			this.desc = "";
			}

		// Format Check
		line = s.nextLine();
		//if (line.equals("===") == true || line.equals("---") == true) {
		//		throw new IllegalDungeonFormatException("Error creating room: " + this.title + ". No Description.");
		//	}
		
		// Check for Contents and Description Simultaneously
		while (line.equals("---") == false) {
			if (line.contains("Contents:") == true) {
				split = line.split(": ");
				split = split[1].split(",");
				for (int x = 0; x < split.length; x++) {
					split[x] = split[x].trim();
					if (initState == true) {
						this.contents.add(split[x]);
						}
					}
				addedContents = true;
				}
			else if ((line.contains("Contents:") == false) && (firstLine == false)) {
				this.desc += " " + line;
				}
			else if (line.contains("Contents:") == false) {
				this.desc += line;
				}

			line = s.nextLine();

			if (line.equals("===") == true) {
				throw new IllegalDungeonFormatException();	
				} 
			}
			
		// Set Booleans
		this.beenHere = false;
		this.itemsAliased = false;

		// Exits
		exits = new Hashtable<String,Exit>();

	}

	// Store State	
	void storeState(PrintWriter w) {
			w.println(this.title + ":");
			w.println("beenHere=true");
			if (contents.size() != 0) {
				w.print("Contents: ");
				int commaIndex = 0;
				for (int x = 0; x < contents.size(); x++) {
					if (commaIndex > 0) {
						w.print(",");
						}
					w.print(contents.get(x));
					commaIndex += 1;
					}
				w.print("\n");
				}
			w.println("---");
		}

	// Restore State
	void restoreState(Scanner r) {
		String[] line = r.nextLine().split("=");
		String parameter = line[0];
		String value = line[1];
		String[] items;
		this.itemsAliased = true;
		if (parameter.equals("beenHere") == true) {
			if (value.equals("true") == true) {
				this.beenHere = true;
				}
			}	
		line = r.nextLine().split(":");
		if (line[0].equals("---") == false) {
			items = line[1].split(",");
			for (int x = 0; x < items.length; x++) {
				this.add(GameState.instance().getDungeon().getItem(items[x].trim()));
				}
			}
		}

	// Initialization
	public void init() {
		if (this.itemsAliased == false) {
			Item item = null;
			ArrayList<String> altNames = null;
			for (String s:contents) {
				item = GameState.instance().getDungeon().getItem(s);
				altNames = item.getAltNames();
				items.put(item.getPrimaryName(),item);
				for (String z:altNames) {
					items.put(z,item);
					}
				}
			this.itemsAliased = true;
			}		
		}
		
	// GetTitle
	public String getTitle() {
		return title;
		}

	// SetDesc
	public void setDesc(String desc) {
		this.desc = desc;
		}

	// Reset beenHere (for Look command)
	public void reset() {
		this.beenHere = false;
		}

	// Describe
	String describe(){
		init();
		Enumeration e = exits.keys();
		String dir;
		String fullDesc;

		if (beenHere == false) {
			beenHere = true;
			fullDesc = Ansi.GREEN + title + Ansi.SANE +  "\n";
			fullDesc += this.desc;
			} 
		else {
			fullDesc = Ansi.GREEN + title + Ansi.SANE;
			}
		for (String item:contents) {
			fullDesc += "\nThere is a " + Ansi.GREEN + item + Ansi.SANE + " here.";
			}
		while (e.hasMoreElements()) {
			dir = ((String) e.nextElement());
			fullDesc +=("\n" + (exits.get(dir).describe()));
			}
		return fullDesc;
		}

	// LeaveBy
	Room leaveBy(String dir) {
		if (exits.containsKey(dir) == true) {
			return exits.get(dir).getDest();
			} 
		else {
			return null;
			}
		}

	// Add exit
	public void addExit(Exit exit) {
		exits.put(exit.getDir(),exit);
		}

	// Add item
	void add(Item item) {
		this.contents.add(item.getPrimaryName());
		items.put(item.getPrimaryName(),item);
		ArrayList <String> altNames = item.getAltNames();
		for (String s: altNames) {
			items.put(s,item);
			}

		}

	// Remove item
	void remove(Item item) {
		String itemName = item.getPrimaryName();
		for (int x = 0; x < this.contents.size(); x++) {
			if (this.contents.get(x).equals(itemName) == true) {
				this.contents.remove(x);
				break;
				}
			}
		}

	// Get Contents
	public ArrayList<String> getContents() {
		return this.contents;
		}

	// Get Items (Aliases)
	public Hashtable<String,Item> getItems() {
		return this.items;
		}

	}

