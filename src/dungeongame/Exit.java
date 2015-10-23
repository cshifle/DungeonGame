package dungeongame;

import java.util.Scanner;

public class Exit {
	
	static class NoExitException extends Exception {}

	private String dir;
	private Room dest;
	private Room src;

	// Construct from file
	public Exit(Scanner s, Dungeon dungeon)		throws	IllegalDungeonFormatException, 
														NoExitException {
		String nextSrc = s.nextLine();

		if (nextSrc.equals("===") == true) {
			throw new NoExitException();
			}
		
		String nextDir = s.nextLine();
		String nextDest = s.nextLine();
		String delimiter = s.nextLine();
		if (delimiter.equals("---") == false) {
			throw new IllegalDungeonFormatException("Error adding exit: Incorrect exit format.");
			}

		this.src = dungeon.getRoom(nextSrc);
		this.dir = nextDir;
		this.dest = dungeon.getRoom(nextDest);
		this.src.addExit(this);
		}
		
	// Hardcoded Construct
	Exit(String dir, Room src, Room dest) {
		this.dir = dir;
		this.src = src;
		this.dest = dest;
		src.addExit(this);
		}

	// Init()
	void init() {
		// Empty; initialization done in constructor
		}

	// Describe - toString implemented as shortcut
	String describe() {
		return ("You can go " + Ansi.YELLOW + CommandFactory.directions.get(dir) + Ansi.SANE + " to the " + Ansi.GREEN + dest.getTitle() + Ansi.SANE +  ".");
		}
	
	// Get Direction
	public String getDir() {
		return this.dir;
		}
	
	// Get Location
	public String getLoc() {
		return this.dest.getTitle();
		}

	// Get Source
	public Room getSrc() {
		return this.src;
		}

	// Get Destination
	public Room getDest() {
		return this.dest;
		}
	
	// To String	
	public String toString() {
		return ("You can go " + dir + " to the " + dest.getTitle() + ".");
		}
	}
