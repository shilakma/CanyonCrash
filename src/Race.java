import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
/**
 * 
 * @author R Travis Pierce
 * @version 2019_03_04
 *
 */

public class Race {
	
	Track track;
	ArrayList<Pilot> pilots;
	ArrayList<Pilot> placing = new ArrayList<Pilot>();
	int laps = 3;
	Scanner keyboard = new Scanner(System.in);
	Random rand = new Random();
	final int PAUSE = 2;
	

	// Need to get Track, Pilot/Ship stats
	
	public Race (Track track, ArrayList<Pilot> pilots)
	{
		this.track = track;
		this.pilots = pilots;
	}
	
	// Commence race by starting, then going through a sequence of Sections - allowing players to perform actions
	// pertaining to the Section style and available attacks on available opponents, in order of current placing -
	// a number of times equal to the number of laps specified.
	public void runRace() throws InterruptedException {
		System.out.println("ON YOUR MARK...");
		TimeUnit.SECONDS.sleep(1);
		System.out.println("GET SET...");
		TimeUnit.SECONDS.sleep(1);
		System.out.println("GO!!!");
		TimeUnit.SECONDS.sleep(PAUSE);
		placing.addAll(pilots);			// Copy all pilots to placing
		int currLap = 0;
		int currSection = 0;
		
		determineInitialPlacing();
		
		// Announce the initial placing
		System.out.println("ANNOUNCER: Straight out of the gate, it's " + placing.get(0).getPilotName() + "!!!");
		for (int index = 1; index < placing.size(); ++index)
		{
			System.out.println("followed by " + placing.get(index).getPilotName() + "!!!");
		}
		System.out.println("!!!!!!!!!!");
		TimeUnit.SECONDS.sleep(PAUSE*2);
		System.out.println(); 				// BLANK SPACE
		
		// MAIN LOOP
		while (currLap < laps)
		{
			System.out.println("LAP " + (currLap + 1));
			currSection = 0;
			while (currSection < (track.getSections().size()))
			{	
				// Determine what type of Section this is based on what's present
				int sectionType = track.getSections().get(currSection).getSectionType();
				// Play it out
				// if statement based on returned int value from determineSectionType
				//	0 = TURN | 1 = JUMP | 2 = EVENT | 3 = STRAIGHTAWAY
				
				if (sectionType == 0) {					// TURN
					System.out.println("ANNOUNCER: Looks like they're coming up on a turn!");
					TimeUnit.SECONDS.sleep(PAUSE);
					// Handle the Turn and PilotAction by order in placing
					System.out.println("ANNOUNCER: In first place... it's... "
							+ placing.get(0).getPilotName() + "!!!");
					TimeUnit.SECONDS.sleep(PAUSE);
					pilotAction(placing.get(0), currSection);
					for (int index = 1; index < placing.size(); ++index)
					{
						System.out.println("ANNOUNCER: Next up... it's... "
								+ placing.get(index).getPilotName() + "!!!");
						TimeUnit.SECONDS.sleep(PAUSE);
						pilotAction(placing.get(index), currSection);
					}
					// Everyone takes the Turn
					takeTurn();
					
					
				} else if (sectionType == 1) {			// JUMP
					System.out.println("ANNOUNCER: Looks like they're coming up on a jump!");
					TimeUnit.SECONDS.sleep(PAUSE);
					// Handle the Jump and PilotAction by order in placing
					
				} else if (sectionType == 2) {			// EVENT
					System.out.println("ANNOUNCER: I've never seen anything like this before!!");
					TimeUnit.SECONDS.sleep(PAUSE);
					// Handle the Event and PilotAction by order in placing
					
				} else if (sectionType == 3) {			// STRAIGHTAWAY
					System.out.println("ANNOUNCER: Comin' up on a straightaway! "
							+ "Should be smooth sailing from here...");
					TimeUnit.SECONDS.sleep(PAUSE);
					// Handle PilotAction by order in placing
					
				}
				++currSection;
			}
			++currLap;
		}
	}
	
	// Determine initial placing based on ship Acceleration stats
	public void determineInitialPlacing()
	{
		Collections.sort(placing);
	}
	
	public void pilotAction(Pilot pilot, int currSection) throws InterruptedException
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("What action will " + pilot.getPilotName() + " take??");
		System.out.println("RAM OPPONENT | TAUNT OPPONENT | DROP MINE | FOCUS");
		String userInput = keyboard.nextLine();
		boolean validInput = false;
		while (!(validInput))
		{
			if (userInput.equalsIgnoreCase("RAM OPPONENT"))
			{
				ramOpponent(pilot, userInput);
				validInput = true;
				break;
			} else if (userInput.equalsIgnoreCase("TAUNT OPPONENT")) {
				tauntOpponent(pilot, userInput);
				validInput = true;
				break;
			} else if (userInput.equalsIgnoreCase("DROP MINE")) {
				dropMine(pilot, currSection);
				validInput = true;
				break;
			}
			System.out.println("RAM OPPONENT | TAUNT OPPONENT | DROP MINE | FOCUS");
			userInput = keyboard.nextLine();
		}
	}
	
	public void takeTurn() throws InterruptedException
	{
		ArrayList<Pilot> tempPlacing = new ArrayList<Pilot>();
		tempPlacing.addAll(placing);
		
		// TAKE THE TURN
		int rando = rand.nextInt(50);
		// FIRST PLACE
		System.out.println("ANNOUNCER: First around the bend is "
				+ placing.get(0).getPilotName() + "!!!");
		TimeUnit.SECONDS.sleep(PAUSE);
		if (((placing.get(0).getShip().get(3)) + rando) >= 16)
		{
			System.out.println("ANNOUNCER: " + placing.get(0).getPilotName() + " made it"
					+ " around the turn!");
			TimeUnit.SECONDS.sleep(PAUSE);
		} else {
			Pilot tempPilot = placing.get(0);
			tempPlacing.remove(0);
			tempPlacing.add(tempPilot);
			System.out.println("ANNOUNCER: Oooh... That's gonna cost 'em...");
			System.out.println(placing.get(0).getPilotName() + " is now in place"
					+ " " + placing.size() + ".");
			TimeUnit.SECONDS.sleep(PAUSE*2);
		}
		// ALL OTHERS
		for (int index = 1; index < placing.size(); ++index)
		{
			rando = rand.nextInt(50);
			System.out.println("ANNOUNCER: Coming up..." + placing.get(index).getPilotName() + "!!!");
			if (((placing.get(index).getShip().get(3)) + rando) >= 15)
			{
				System.out.println("ANNOUNCER: " + placing.get(index).getPilotName() + " made it"
						+ " around the turn!");
				TimeUnit.SECONDS.sleep(PAUSE);
			} else {
				Pilot tempPilot = placing.get(index);
				int tempPlacingIndex = -1;
				for (int k = 0; k < tempPlacing.size(); ++k)
				{
					if (tempPilot.equals(tempPlacing.get(k)))
					{
						tempPlacingIndex = k;
					}
				}
				tempPlacing.remove(tempPlacingIndex);
				tempPlacing.add(tempPilot);
				System.out.println("ANNOUNCER: Oooh... That's gonna cost 'em...");
				System.out.println(placing.get(index).getPilotName() + " is now in place"
						+ " " + placing.size() + ".");
				TimeUnit.SECONDS.sleep(PAUSE*2);
			}
		}
		// COPY tempPlacing BACK TO placing
		for (int index = 0; index < tempPlacing.size(); ++index)
		{
			placing.set(index, tempPlacing.get(index));
		}
	}
	
	public void ramOpponent(Pilot pilot, String userInput) throws InterruptedException 
	{
		Scanner keyboard = new Scanner(System.in);
		boolean validSecondary = false;
		while (!(validSecondary))
		{
			System.out.println("Which opponent would you like to ram?");
			for (int index = 0; index < placing.size(); ++index)
			{
				if (placing.get(index).equals(pilot)) {
					continue;
				}
				System.out.print("| " + placing.get(index).getPilotName() + " |");
			}
			userInput = keyboard.nextLine();
			for (int index = 0; index < placing.size(); ++index)
			{
				if (placing.get(index).equals(pilot)) {
					if (userInput.equalsIgnoreCase(pilot.getPilotName()))
					{
						System.out.println("Pilots cannot ram themselves.");
						TimeUnit.SECONDS.sleep(PAUSE);
						break;
					}
					continue;
				}
				if (userInput.equalsIgnoreCase(placing.get(index).getPilotName()))
				{
					System.out.println(pilot.getPilotName() + " rammed " + placing.get(index).getPilotName() + "!!!");
					TimeUnit.SECONDS.sleep(PAUSE);
					// Take 10 points from ship's shields
					placing.get(index).getShip().set(4, (placing.get(index).getShip().get(4) - 10));
					System.out.println(placing.get(index).getPilotName() + "'s shields fell to " + placing.get(index).getShip().get(4) + ".");
					TimeUnit.SECONDS.sleep(PAUSE);
					validSecondary = true;
				}
			}
		}
	}
	
	public void tauntOpponent(Pilot pilot, String userInput) throws InterruptedException 
	{
		Scanner keyboard = new Scanner(System.in);
		boolean validSecondary = false;
		while (!(validSecondary))
		{
			System.out.println("Which opponent would you like to taunt?");
			for (int index = 0; index < placing.size(); ++index)
			{
				if (placing.get(index).equals(pilot)) {
					continue;
				}
				System.out.print("| " + placing.get(index).getPilotName() + " |");
			}
			userInput = keyboard.nextLine();
			for (int index = 0; index < placing.size(); ++index)
			{
				if (placing.get(index).equals(pilot)) {
					if (userInput.equalsIgnoreCase(pilot.getPilotName()))
					{
						System.out.println("Pilots cannot taunt themselves.");
						TimeUnit.SECONDS.sleep(PAUSE);
						break;
					}
					continue;
				}
				if (userInput.equalsIgnoreCase(placing.get(index).getPilotName()))
				{
					System.out.println(pilot.getPilotName() + " taunted " + placing.get(index).getPilotName() + "!!!");
					TimeUnit.SECONDS.sleep(PAUSE);
					// Lower opponent's willpower while raising your own
					placing.get(index).setWillPower(placing.get(index).getWillPower() - 1);
					pilot.setWillPower(pilot.getWillPower() - 1);
					System.out.println(placing.get(index).getPilotName() + "'s willpower fell to " + placing.get(index).getWillPower() + ".");
					TimeUnit.SECONDS.sleep(1);
					System.out.println(pilot.getPilotName() + "'s willpower rose to " + pilot.getWillPower() + ".");
					TimeUnit.SECONDS.sleep(PAUSE);
					validSecondary = true;
				}
			}
		}
	}
	
	public void dropMine(Pilot pilot, int currSection) throws InterruptedException 
	{
		if (pilot.getMines() > 0)
		{
			track.getSections().get(currSection).setMines(track.getSections().get(currSection).getMines() + 1);
			pilot.setMines(pilot.getMines() - 1);
			System.out.println("ANNOUNCER: What's this???");
			TimeUnit.SECONDS.sleep(PAUSE);
			System.out.println("ANNOUNCER: Looks like " + pilot.getPilotName() + " dropped a mine on the track!");
			TimeUnit.SECONDS.sleep(PAUSE);
		}
	}
}
