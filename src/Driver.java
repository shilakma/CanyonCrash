import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
		boolean trackSelected = false;
		String trackSelection = "";
		
		System.out.println("CHOOSE YOUR TRACK");
		System.out.println("[Crash Canyon] || [Crag City] || [Big Rock Island] || [Outpost Pass]");
		
		while (!trackSelected)
		{
			String trackName = keyboard.nextLine();
			if (trackName.equalsIgnoreCase("Crash Canyon"))
			{
				trackSelection = "CrashCanyon";
				trackSelected = true;
				break;
			} else if (trackName.equalsIgnoreCase("Crag City"))
			{
				trackSelection = "CragCity";
				trackSelected = true;
				break;
			} else if (trackName.equalsIgnoreCase("Big Rock Island"))
			{
				trackSelection = "BigRockIsland";
				trackSelected = true;
				break;
			} else if (trackName.equalsIgnoreCase("Outpost Pass"))
			{
				trackSelection = "OutpostPass";
				trackSelected = true;
				break;
			}
			
			System.out.println("CHOOSE YOUR TRACK");
			System.out.println("[Crash Canyon] || [Crag City] || [Big Rock Island] || [Outpost Pass]");
			
		}
		Track raceTrack = new Track(trackSelection);
		
		// Get number of racers from user input
		while(numRacers < 2 || numRacers > 8)
		{
			System.out.println("CHOOSE NUMBER of RACERS [2 - 8]");
			numRacers = Integer.parseInt(keyboard.nextLine());
		}
		
		// Get pilot file names from user input
		ArrayList<String> remainingPilots = new ArrayList<String>(
				Arrays.asList("Captain Phil", "Zanton", "Hazzel Frax",
						"Bingstar Megadung", "Glim Sarwo", "Himmawon",
						"Klarbshtabbel Vanderhoosht", "Yuteil Vin"));
				
		String pilotSelection = "";
		String pilotName = "";
		for (int index = 0; index < numRacers; ++index)
		{
			boolean pilotSelected = false;
			System.out.println("PLAYER " + (index + 1) + ": CHOOSE YOUR PILOT");
			remainingPilots.forEach(p -> System.out.println("| [" + p + "] |"));
			
			while(!pilotSelected)
			{
				pilotName = keyboard.nextLine();
				if (pilotName.equalsIgnoreCase("Captain Phil"))
				{
					pilotSelection = "CaptainPhil";
					pilotSelected = true;
					remainingPilots.remove("Captain Phil");
					break;
				} else if (pilotName.equalsIgnoreCase("Zanton"))
				{
					pilotSelection = "Zanton";
					pilotSelected = true;
					remainingPilots.remove("Zanton");
					break;
				} else if (pilotName.equalsIgnoreCase("Hazzel Frax"))
				{
					pilotSelection = "HazzelFrax";
					pilotSelected = true;
					remainingPilots.remove("Hazzel Frax");
					break;
				} else if (pilotName.equalsIgnoreCase("Bingstar Megadung"))
				{
					pilotSelection = "BingstarMegadung";
					pilotSelected = true;
					remainingPilots.remove("Bingstar Megadung");
					break;
				} else if (pilotName.equalsIgnoreCase("Glim Sarwo"))
				{
					pilotSelection = "GlimSarwo";
					pilotSelected = true;
					remainingPilots.remove("Glim Sarwo");
					break;
				} else if (pilotName.equalsIgnoreCase("Himmawon"))
				{
					pilotSelection = "Himmawon";
					pilotSelected = true;
					remainingPilots.remove("Himmawon");
					break;
				} else if (pilotName.equalsIgnoreCase("Klarbshtabbel Vanderhoosht"))
				{
					pilotSelection = "KlarbshtabbelVanderhoosht";
					pilotSelected = true;
					remainingPilots.remove("Klarbshtabbel Vanderhoosht");
					break;
				} else if (pilotName.equalsIgnoreCase("Yuteil Vin"))
				{
					pilotSelection = "YuteilVin";
					pilotSelected = true;
					remainingPilots.remove("Yuteil Vin");
					break;
				}
				
				System.out.println("PLAYER " + (index + 1) + ": CHOOSE YOUR PILOT");
				remainingPilots.forEach(p -> System.out.println("| [" + p + "] |"));
				
			}
			pilots.add(new Pilot(pilotSelection));
			
		}
		
		//for (Pilot item : pilots) System.out.println("\t" + item.getWillPower());			// TEST
		
		// Get ship file names from user input
		ArrayList<String> remainingShips = new ArrayList<String>(
				Arrays.asList("SS Bhutan", "XZ32", "Space Hornet 7",
						"Far Flyer", "Clipfang", "KG780",
						"Shilber", "Dogfood"));
				
		String shipSelection = "";
		String shipName = "";
		for (int index = 0; index < numRacers; ++index)
		{
			boolean shipSelected = false;
			System.out.println("PLAYER " + (index + 1) + ": CHOOSE YOUR SHIP");
			remainingShips.forEach(p -> System.out.println("| [" + p + "] |"));
			
			while (!shipSelected)
			{
				shipName = keyboard.nextLine();
				
				if (shipName.equalsIgnoreCase("SS Bhutan"))
				{
					shipSelection = "SSBhutan";
					shipSelected = true;
					remainingShips.remove("SS Bhutan");
					break;
				} else if (shipName.equalsIgnoreCase("XZ32"))
				{
					shipSelection = "XZ32";
					shipSelected = true;
					remainingShips.remove("XZ32");
					break;
				} else if (shipName.equalsIgnoreCase("Space Hornet 7"))
				{
					shipSelection = "SpaceHornet7";
					shipSelected = true;
					remainingShips.remove("Space Hornet 7");
					break;
				} else if (shipName.equalsIgnoreCase("Far Flyer"))
				{
					shipSelection = "FarFlyer";
					shipSelected = true;
					remainingShips.remove("Far Flyer");
					break;
				} else if (shipName.equalsIgnoreCase("Clipfang"))
				{
					shipSelection = "Clipfang";
					shipSelected = true;
					remainingShips.remove("Clipfang");
					break;
				} else if (shipName.equalsIgnoreCase("KG780"))
				{
					shipSelection = "KG780";
					shipSelected = true;
					remainingShips.remove("KG780");
					break;
				} else if (shipName.equalsIgnoreCase("Shilber"))
				{
					shipSelection = "Shilber";
					shipSelected = true;
					remainingShips.remove("Shilber");
					break;
				} else if (shipName.equalsIgnoreCase("Dogfood"))
				{
					shipSelection = "Dogfood";
					shipSelected = true;
					remainingShips.remove("Dogfood");
					break;
				}
			}
			pilots.get(index).readShip(shipSelection);
		}
		
		// Get number of mines from user input
		System.out.println("ENTER NUMBER of MINES");
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
		TimeUnit.SECONDS.sleep(3);
		System.out.println("# # # # # # # # # # # # # # # # # # # # #");
		System.out.println(" # # # # # # # # # # # # # # # # # # # # ");
		System.out.println("# # # # # # # # # # # # # # # # # # # # #");
		System.out.println(" # # # # # # # # # # # # # # # # # # # # ");
		System.out.println("# # # # # # # # # # # # # # # # # # # # #");
		System.out.println("THANK YOU FOR PLAYING CANYON CRASH");
		System.out.println("©2019 PIERCE INTERNATIONAL, UNLIMITED");
		System.out.println("Program by: R Travis Pierce");
	}
}
