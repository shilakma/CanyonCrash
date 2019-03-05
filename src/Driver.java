import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author R Travis Pierce
 * @version 2019_03_04
 *
 */

public class Driver {

	public static void main(String[] args) throws IOException, InterruptedException
	{
		int numRacers = 0;
		int startMines = 0;
		ArrayList<Pilot> pilots = new ArrayList<Pilot>();
		Scanner keyboard = new Scanner(System.in);
	
		// Get name of track file name from user input
		System.out.println("Which track will we be racing today?");
		String trackName = keyboard.nextLine();
		Track raceTrack = new Track(trackName);
		
		// Get number of racers from user input
		System.out.println("How many racers?");
		numRacers = Integer.parseInt(keyboard.nextLine());
		
		// Get pilot file names from user input
		String pilotName = "";
		for (int index = 0; index < numRacers; ++index)
		{
			System.out.println("Who is Pilot No. " + (index + 1) + "?");
			pilotName = keyboard.nextLine();
			pilots.add(new Pilot(pilotName));
		}
		
		//for (Pilot item : pilots) System.out.println("\t" + item.getWillPower());			// TEST
		
		// Get ship file names from user input
		String shipName = "";
		for (int index = 0; index < numRacers; ++index)
		{
			System.out.println("Which ship will Pilot No. " + (index + 1) + " fly?");
			shipName = keyboard.nextLine();
			pilots.get(index).readShip(shipName);
		}
		
		// Get number of mines from user input
		System.out.println("How many mines will each racer start with?");
		startMines = keyboard.nextInt();
		for (int index = 0; index < numRacers; ++index)
		{
			pilots.get(index).setMines(startMines);
		}
		
		// Start race
		Race race1 = new Race(raceTrack, pilots);
		race1.runRace();
		
		// WINNER WINNER CHICKEN DINNER
		System.out.println("ANNOUNCER: WOW!!! What a race!!!!");
		TimeUnit.SECONDS.sleep(2);
		System.out.println("ANNOUNCER: And the winner is...");
		TimeUnit.SECONDS.sleep(2);
		System.out.println("ANNOUNCER: " + race1.placing.get(0).getPilotName() + "!!!!!");
		TimeUnit.SECONDS.sleep(2);
		System.out.println("+++++++++++++++++++++++++++++++++++++++++");
		System.out.println("THANK YOU FOR PLAYING CANYON CRASH");
		System.out.println("©2019 PIERCE INTERNATIONAL, UNLIMITED");
		System.out.println("Program by: R Travis Pierce");
	}
}
