/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dungeongame;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Hashtable; 
import java.io.File;				
import java.util.Scanner;	

/* This is the main() class that directs operations. For now, it should 
create a new hardcoded Dungeon, initialize the GameState with it, and repeatedly
prompt the user for input. Each time the user inputs a command, it should use 
the CommandFactory to instantiate a new Command object and execute it. If the 
user enters "q", it terminates the program.

*/
class DungeonGame {

 private static GameState state;
 private static CommandFactory commandFactory;	
	
    public static void main(String[] args) {
	
	// Command Line Arguments
				String filename = " " ;
				String fileExt = " ";
				
				try {
					filename = args[0];
					GameState.instance().setSaveFilename(args[0]);
					String[] filenameSplit = filename.split("\\.");
					fileExt = filenameSplit[1];
					if ((fileExt.equals("bork") == false) && (fileExt.equals("sav") == false)) {
						throw new Exception();
						}
					} 
				catch (Exception e) {
						System.out.println("Usage: Interpreter borkFile.bork | saveFile.sav");
						System.exit(0);
					}

				// Dungeon and GameState Initialization
				String input = " ";
				BufferedReader commandLine = new BufferedReader(new InputStreamReader(System.in));				
				Dungeon dungeon;

				try {

					// Initiate Dungeon from .sav File
					if (fileExt.equals("sav") == true) {
						GameState.instance().restore(filename);
						dungeon = GameState.instance().getDungeon();
						state = state.instance();
						commandFactory = commandFactory.instance();
						welcome(dungeon);
						} 
					
					// Initiate Dungeon from .bork File
					else if (fileExt.equals("bork") == true) {
						dungeon = new Dungeon(filename);
						state = state.instance();
						state.initialize(dungeon);
						commandFactory = commandFactory.instance();
						welcome(dungeon);
						}
					}
				catch (IllegalDungeonFormatException df) {
					System.out.println(df.getWarning());
					df.printStackTrace();
					System.exit(1);
					}
				catch (IllegalSaveFormatException sf) {
					System.out.println(sf.getWarning());
					sf.printStackTrace();
					System.exit(2);
					} 
				catch (java.io.FileNotFoundException fnf) {
					System.out.println("File not found!");
					fnf.printStackTrace();
					System.exit(3);
					}
				catch (Exception e) {
					e.printStackTrace();
					System.exit(4);
					}
				
				// Command Parsing
				while (input.equals("q") == false) {
					input = promptUser(commandLine);
					if (input.equals("q") == true) {
						break;
						}
					System.out.println(commandFactory.parse(input).execute());
					}
				System.out.println("Thanks for playing!");
		}
		
		// promptUser
		private static String promptUser (BufferedReader commandLine) {
			System.out.print("> ");
			try {
				return commandLine.readLine();
				} 
			catch (Exception e) {  
				e.printStackTrace();
				}
			return null;
		}


		// Welcome to Bork
		static void welcome(Dungeon dungeon) {
				// ANSI Colors from: https://gist.github.com/dainkaplan/4651352
				// ANSI Art generated at: http://patorjk.com/software/taag/#p=display&f=ANSI%20Shadow&t=Bork
				System.out.print("\n\n" + Ansi.GREEN);
				System.out.println("   ██████╗    ██████╗   ██████╗   ██╗  ██╗");
				System.out.println("   ██╔══██╗  ██╔═══██╗  ██╔══██╗  ██║ ██╔╝");
				System.out.println("   ██████╔╝  ██║   ██║  ██████╔╝  █████╔╝ ");
				System.out.println("   ██╔══██╗  ██║   ██║  ██╔══██╗  ██╔═██╗ ");
				System.out.println("   ██████╔╝  ╚██████╔╝  ██║  ██║  ██║  ██╗" + Ansi.SANE + "     Version 3.0" + Ansi.GREEN);
				System.out.println("   ╚═════╝    ╚═════╝   ╚═╝  ╚═╝  ╚═╝  ╚═╝" + Ansi.SANE + "     Christopher Shifflett \n\n");
				System.out.println(GameState.instance().getAdventurersCurrentRoom().describe());
		}
}
