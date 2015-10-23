package dungeongame;

import java.util.Hashtable;
import java.io.File;
import java.util.Scanner;
import java.util.Enumeration;
import java.io.PrintWriter;
import java.util.ArrayList;



public class Dungeon {

	private String name;
	private String filename;
	private Room entry;
	private Hashtable < String , Room > rooms = new Hashtable<String,Room>();
	private Hashtable < String , Item > items = new Hashtable<String,Item>();
	
	// Construct from file
	public Dungeon (String filename)	throws	IllegalDungeonFormatException, 
												java.io.FileNotFoundException {
		this.filename = filename;
		init();
		}

	// Hardcoded construct
	Dungeon (Room entry,  String name) {
		this.name = name;
		this.entry = entry;
		} 
	
	// Initialization	
	private void init()		throws	java.io.FileNotFoundException, 
									IllegalDungeonFormatException {
			
			File file = new File(this.filename);
			try {
				this.filename = file.getCanonicalPath(); 		
				}
			catch (java.io.IOException ioe) {
				// Do nothing.
				}
			String line;
			Scanner s = new Scanner(file);
				
			this.name = s.nextLine();
		
			// Filetype Check
			String[] filenameSplit = GameState.instance().getSaveFilename().split("\\.");
			String fileExt = filenameSplit[1];
			boolean initCheck = true;
			if (fileExt.equals("sav") == true) {
				initCheck = false;
				}			

			// Version Check
			line = s.nextLine();
			if (line.equals("Bork v3.0") == false) {
				throw new IllegalDungeonFormatException("Version not recognized.");
				}
			line = s.nextLine();
			if (line.equals("===") == false) {
				throw new IllegalDungeonFormatException();
				}

			// Add Items
			Item nextItem;
			boolean hasMoreItems = true;
			if (s.nextLine().equals("Items:") == true) {
				while (hasMoreItems == true) {	
					try {
						nextItem = new Item(s);
						if (nextItem == null) {
							hasMoreItems = false;
							}
						this.add(nextItem);
						}
					catch (Item.NoItemException nie) {
						hasMoreItems = false;
						}
					}
				}
			else {
				throw new IllegalDungeonFormatException("No items found in dungeon.");
				}
			String itemCheck = null;
			Enumeration i = items.keys();
			while (i.hasMoreElements()) {
				itemCheck = (String) i.nextElement();
			}


			// Add Entry and Rooms
			line = s.nextLine();
			Room nextRoom;
			boolean hasMoreRooms = true;
			while (hasMoreRooms == true) {
				try {
					nextRoom = new Room(s,this,initCheck);
					if (this.entry == null) {
						this.entry = nextRoom;
						}
					this.add(nextRoom);
					} 
				catch (Room.NoRoomException n) {
					hasMoreRooms = false;
					}
				}

			// Add Exits
			line = s.nextLine();
			boolean hasMoreExits = true;
			Exit nextExit;
			while (hasMoreExits == true) {
				try {
						nextExit = new Exit(s,this);
					} 
				catch (Exit.NoExitException ne) {
					hasMoreExits = false;
					}
				}
		}

	// Store State
	void storeState(PrintWriter p) {
		p.println("Dungeon file: " + this.filename);
		p.println("Room states:");
		Enumeration e = rooms.keys();
		String s = "";
		while (e.hasMoreElements()) {
			s = (String) e.nextElement();
			rooms.get(s).storeState(p);
			}
		p.println("===");
		}

	// Restore State - made this method static to call without first instantiating the dungeon.
	static void restoreState(Scanner r)	throws	IllegalDungeonFormatException,
												java.io.FileNotFoundException {
		String fileLine = r.nextLine();
		String[] s = fileLine.split(": ");
		String filepath = s[1];
		
		Dungeon dungeon = new Dungeon(filepath);
		GameState.instance().initialize(dungeon);
			
		// Restore Room States
		String line = r.nextLine();
		String roomLine = " ";
		boolean hasMoreRooms = true;
		while (hasMoreRooms == true) {
			roomLine = r.nextLine().replace(":","");
			if ((roomLine.equals("===") == true) || (line.equals("===") == true)) {
				hasMoreRooms = false;
				}
			if (roomLine.contains("beenHere")== false &&
				roomLine.contains("===") == false &&
				roomLine.contains("Contents:") == false &&
				roomLine.contains("---") == false ) {
				dungeon.getRoom(roomLine).restoreState(r);
				}
			//line = r.nextLine();	
			if (line.equals("===") == true) {
				hasMoreRooms = false;
				break;
				}
			}
		}	
	
	// Get Entry
	public Room getEntry() {
		return this.entry;
		}

	// Get Name
	public String getName() {
		return this.name;
		}

	// Add Room
	public void add(Room room) {
		rooms.put(room.getTitle(),room);
		}

	// Add Item
	public void add(Item item) {
		items.put(item.getPrimaryName(),item);
		
		ArrayList<String> altNames = item.getAltNames();

		for (String s:altNames) {
			items.put(s,item);
			}

		}

	// Get Room
	public Room getRoom(String roomTitle) {
		return rooms.get(roomTitle);
		}
	
	// Get Item
	public Item getItem(String name) {
		return items.get(name);
		}

	// Get Items
	public Hashtable<String, Item> getItems() {
		return this.items;
		}

	// To String
	public String toString() {
		String string;
		String nextRoom;
		Enumeration e = rooms.keys();
			
		string =  "Dungeon Name: " + this.getName();
		string += "\nEntry: " + this.getEntry().getTitle();
		string += "\nRooms: ";
			
		while (e.hasMoreElements()) {
			nextRoom = (String) e.nextElement();
			string += "\n\n" + getRoom(nextRoom).describe();
			}			
			
		return string;
		}
	}
