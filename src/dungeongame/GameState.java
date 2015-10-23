package dungeongame;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;


class GameState {

	private static GameState theInstance;
	private Room adventurersCurrentRoom;
	private Dungeon dungeon;
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private String saveFilename;

	public static GameState instance() {
		if (theInstance == null) {
			theInstance = new GameState();
			}
		return theInstance;
		}
		
	private GameState() {
		this.theInstance = theInstance;
		this.dungeon = null;
		}

	// Initialize
	public void initialize(Dungeon dungeon) {
		this.dungeon = dungeon;
		setAdventurersCurrentRoom(dungeon.getEntry());
		}
	
	// Store
	void store(String saveName)		throws	java.io.IOException {
		File file = new File(saveName);
		boolean newFile = file.createNewFile();
		PrintWriter p = new PrintWriter(file);
		int commaIndex = 0;		

		p.println("Bork v3.0 save data");
		this.dungeon.storeState(p);
		p.println("Adventurer:");
		p.println("Current Room: " + this.getAdventurersCurrentRoom().getTitle());
		if (inventory.size() > 0) {
			p.print("Inventory: ");
			for (int x = 0; x < inventory.size(); x++) {
				if (commaIndex > 0) {
					p.print(",");
					}
				commaIndex += 1;
				p.print(inventory.get(x).getPrimaryName());
				}
			}
		p.close();
		}

	// Restore
	void restore(String filename)	throws	java.io.FileNotFoundException,
											IllegalSaveFormatException,
											IllegalDungeonFormatException {
		File file = new File(filename);
		Scanner r = new Scanner(file);
		String line = r.nextLine();
		
		// Compatibility Check
		if (line.equals("Bork v3.0 save data") == false) {
			throw new IllegalSaveFormatException("Unrecognized version.");
			}

		// Build Dungeon
		Dungeon.restoreState(r);
		
		// Restore Adventurer State
		line = r.nextLine();
		if (line.equals("Adventurer:") == false) {
			throw new IllegalSaveFormatException("Error reading Adventurer state.");
			}
		line = r.nextLine();
		String[] s = line.split(": ");
		String currentRoom = s[1];
		this.setAdventurersCurrentRoom(this.dungeon.getRoom(currentRoom));
		
		// Restore Inventory
		try {
			line = r.nextLine();
			s = line.split(": ");
			s = s[1].split(",");
			for (int x = 0; x < s.length; x++) {
				inventory.add(this.dungeon.getItem(s[x]));	
				}
			}
		catch (Exception e) {}
		}

	// Add Item to Inventory
	void addToInventory(Item item) {
		this.inventory.add(item);
		GameState.instance().getAdventurersCurrentRoom().remove(item);
		}

	// Remove Item from Inventory
	void removeFromInventory(Item item) {
		GameState.instance().getAdventurersCurrentRoom().add(item);
		this.inventory.remove(item);
		}

	// Get Adventurer's Current Room
	public Room getAdventurersCurrentRoom() {
		return adventurersCurrentRoom;
		}
	
	// Set Adventurer's Current Room	
	public void setAdventurersCurrentRoom(Room room) {
		adventurersCurrentRoom = room;
		}
	
	// Set Save Filename
	public void setSaveFilename(String s) {
		this.saveFilename = s;
		}	

	// Get Save Filenmae
	public String getSaveFilename() {
		return this.saveFilename;
		}

	// Get Dungeon
	public Dungeon getDungeon() {
		return this.dungeon;
		}

	// Get Inventory Names
	ArrayList<String> getInventoryNames() {
		ArrayList<String> inventoryNames = new ArrayList<String>();
		for (int x = 0; x < this.inventory.size(); x++) {
			inventoryNames.add(this.inventory.get(x).getPrimaryName());
			}
		return inventoryNames;
		}

	// Check Weights
	public int getInventoryWeight() {
		ArrayList<String> inventory = this.getInventoryNames();
		int load = 0;

		if (inventory.size() > 0) {
			for (String s: inventory) {
				load += this.getDungeon().getItem(s).getWeight();
				}
			return load;
			}
		else {
			return 0;
			}
		}

	}

