package dungeongame;

import java.util.Hashtable;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random; // http://docs.oracle.com/javase/7/docs/api/java/util/Random.html

public class Item {

	static class NoItemException extends Exception {}

	private String primaryName;
	private int weight;
	private Hashtable <String,String> messages = new Hashtable <String,String>();
	private ArrayList <String> altNames = new ArrayList <String>();

	public Item (String primaryName, int weight) {
		this.primaryName = primaryName;
		this.weight = weight;
		}

	public Item (Scanner s)		throws	NoItemException,
										IllegalDungeonFormatException {
		
		// Primary and Alternate Names
		String line = s.nextLine();
		String[] split = line.split(",");
		if ((line.contains("---") == true) || (line.contains("===") == true) || (line.contains(":") == true)) {
			throw new NoItemException();
			}
		else {
			if (line.contains(",")) {
				this.primaryName = split[0];
				for (int x = 1; x < split.length; x++) {
					altNames.add(split[x]);
					}
				}
			else {
				this.primaryName = line;
				}
			}

		// Item Weight
		line = s.nextLine();
		if (line.contains(":")) {
			throw new NoItemException();
			}
		else {
			try {
				this.weight = Integer.parseInt(line); // http://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html#parseInt-java.lang.String-int-
				}
			catch (Exception e) {
				throw new NoItemException();
				}
			}

		// Item Specific Commands
		String[] cmd = {" "};
		String msg = null;
		line = s.nextLine();
		try {
			while (line.contains(":") == true) {
				cmd = line.split(":");
				msg = cmd[1];
				if (cmd[0].contains(",")) {
					cmd = cmd[0].split(",");
					for (int x = 0; x < cmd.length; x++) {
						CommandFactory.itemSpecificCommands.add(cmd[x]);
						messages.put(cmd[x],msg);
						}
					}
				else {
					CommandFactory.itemSpecificCommands.add(cmd[0]);
					messages.put(cmd[0],msg);
					}
				line = s.nextLine();
				}
			}
		catch (Exception e) {

			}
		if (line.contains("---") == false) {
			throw new IllegalDungeonFormatException();
			}		

		}

	boolean goesBy (String name) {
		if (this.altNames.contains(name)) {
			return true;
			}
		else {
			return false;
			}
		}

	public String getPrimaryName() {
		return this.primaryName;
		}

	public int getWeight() {
		return this.weight;
		}

	public String getMessageForVerb(String verb) {
		if (this.messages.containsKey(verb)) {
			String[] randMessage = this.messages.get(verb).split("\\|");
			Random rnd = new Random();
			int randomInt = rnd.nextInt(randMessage.length);
			return randMessage[randomInt];
			}
		else {
			return null;
			}
		}
	
	public ArrayList<String> getAltNames() {
		return this.altNames;
		}

	public String toString() {
		String string = "";
		string += "Name: " + this.primaryName;
		string += "Weight: " + this.weight;		
		return string;
		}
	}
