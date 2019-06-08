import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
/**
 * 
 * @author R Travis Pierce
 * @version 2019_06_03
 *
 */

public class Race {
	
	Track track;
	ArrayList<Pilot> pilots;
	ArrayList<Pilot> placing = new ArrayList<Pilot>();
	ArrayList<Pilot> finalPlacing = new ArrayList<Pilot>();
	int laps = 3;
	Scanner keyboard = new Scanner(System.in);
	Random rand = new Random();
	final int PAUSE = 2;
	int currLap = 0;
	int currSection = 0;
	

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
		System.out.println("");
		System.out.println("ON YOUR MARK...");
		TimeUnit.SECONDS.sleep(1);
		System.out.println("GET SET...");
		TimeUnit.SECONDS.sleep(1);
		System.out.print("GO");
		TimeUnit.MILLISECONDS.sleep(500);
		System.out.print("!");
		TimeUnit.MILLISECONDS.sleep(500);
		System.out.print("!");
		TimeUnit.MILLISECONDS.sleep(500);
		System.out.println("!");
		TimeUnit.SECONDS.sleep(PAUSE);
		placing.addAll(pilots);			// Copy all pilots to placing
		finalPlacing.addAll(placing);
		currLap = 0;
		currSection = 0;
		
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
		
		System.out.println("\nLAP " + (currLap + 1));
		currSection = 0;
		
		// MAIN LOOP
		while (currLap < laps)
		{
			//TODO: remove battered pilots immediately. Check to end race as well!!!
			while (currSection < (track.getSections().size()))
			{	
				// Remove battered contestants
				// TODO: make it order battered contestants in order of their removal from race placing
				for (int index = 0; index < placing.size(); ++index)
				{
					if (placing.get(index).getShip().get(4) < 0)
					{	
						System.out.print("ANNOUNCER: " + placing.get(index).getPilotName() + " is OUT of the race");
						TimeUnit.MILLISECONDS.sleep(500);
						System.out.print(".");
						TimeUnit.MILLISECONDS.sleep(500);
						System.out.print(".");
						TimeUnit.MILLISECONDS.sleep(500);
						System.out.println(".");
						TimeUnit.SECONDS.sleep(PAUSE*2);
						
						Pilot tempPilot = placing.get(index);
						placing.remove(index);
						finalPlacing.addAll(placing);
						finalPlacing.add(tempPilot);
					}
				}
				if (placing.size() == 1)
				{
					break;
				}
				// Determine what type of Section this is based on what's present
				int sectionType = track.getSections().get(currSection).getSectionType();
				// Play it out
				// if statement based on returned int value from determineSectionType
				//	0 = TURN | 1 = JUMP | 2 = EVENT | 3 = STRAIGHTAWAY
				
				if (sectionType == 0) {					// TURN
					System.out.println("ANNOUNCER: Looks like they're coming up on a turn!");
					TimeUnit.SECONDS.sleep(PAUSE);
					preSection(currSection);
					if (currLap >= laps)
					{
						return;
					}
					// Everyone takes the Turn
					takeTurn();
					
					
				} else if (sectionType == 1) {			// JUMP
					System.out.println("ANNOUNCER: Looks like they're coming up on a jump!");
					TimeUnit.SECONDS.sleep(PAUSE);
					preSection(currSection);
					if (currLap >= laps)
					{
						return;
					}
					// Everyone takes the Turn
					takeJump();
					
				} else if (sectionType == 2) {			// EVENT
					System.out.println("ANNOUNCER: I've never seen anything like this before!!");
					TimeUnit.SECONDS.sleep(PAUSE);
					// Handle the Event and PilotAction by order in placing
					//preSection(currSection);
					takeEvent();
					
				} else if (sectionType == 3) {			// STRAIGHTAWAY
					System.out.println("ANNOUNCER: Comin' up on a straightaway! "
							+ "Should be smooth sailing from here...");
					TimeUnit.SECONDS.sleep(PAUSE);
					// Handle PilotAction by order in placing
					preSection(currSection);
					if (currLap >= laps)
					{
						return;
					}
					
				}
				++currSection;
			}
			++currLap;
			
			if (currLap < laps)
			{
			System.out.println("\nLAP " + (currLap + 1));
			}
			currSection = 0;
		}
		// Fill the finalPlacing with the remaining pilots in proper order, maintaining battered contestant list
		for (int index = 0; index < placing.size(); ++index)
		{
			finalPlacing.set(index, placing.get(index));
		}
	}
	
	// Determine initial placing based on ship Acceleration stats
	public void determineInitialPlacing()
	{
		Collections.sort(placing);
	}
	
	public void preSection(int currSection) throws InterruptedException
	{
		// Handle the Turn/Jump/Event/Straightaway and PilotAction by order in placing
		System.out.println("ANNOUNCER: In first place... it's... "
				+ placing.get(0).getPilotName() + "!!!");
		TimeUnit.SECONDS.sleep(PAUSE);
		
		// Check for mines and run the mine dodge/damage
		mineCheck(placing.get(0), currSection);
		// TODO: Check if first place pilot has 0 shields
			if (placing.get(0).getShip().get(4) < 0)
			{
				//currLap = 3;				// WHY WAS I DOING THIS?
				
				// Remove dead pilot from placing
				System.out.print("ANNOUNCER: " + placing.get(0).getPilotName() + " is OUT of the race");
				TimeUnit.MILLISECONDS.sleep(500);
				System.out.print(".");
				TimeUnit.MILLISECONDS.sleep(500);
				System.out.print(".");
				TimeUnit.MILLISECONDS.sleep(500);
				System.out.println(".");
				TimeUnit.SECONDS.sleep(PAUSE*2);
				
				Pilot tempPilot = placing.get(0);
				placing.remove(0);
				finalPlacing.addAll(placing);
				finalPlacing.add(tempPilot);
				
				// TEST THIS ***********			
				// (possibly checks how many racers are still in the race and might end the race)
				if (finalPlacing.size() == 1)
				{
					currLap = 3;
				}
				// TEST THIS ***********
				return;
			}
		// PilotAction
		pilotAction(placing.get(0), currSection);
		for (int index = 1; index < placing.size(); ++index)
		{
			System.out.println("ANNOUNCER: Next up... it's... "
					+ placing.get(index).getPilotName() + "!!!");
			TimeUnit.SECONDS.sleep(PAUSE);
			mineCheck(placing.get(index), currSection);
			// TODO: Check if pilot has 0 shields
						if (placing.get(index).getShip().get(4) < 0)
						{
							//currLap = 3;				// WHY WAS I DOING THIS?
							
							// Remove dead pilot from placing
							System.out.print("ANNOUNCER: " + placing.get(index).getPilotName() + " is OUT of the race");
							TimeUnit.MILLISECONDS.sleep(500);
							System.out.print(".");
							TimeUnit.MILLISECONDS.sleep(500);
							System.out.print(".");
							TimeUnit.MILLISECONDS.sleep(500);
							System.out.println(".");
							TimeUnit.SECONDS.sleep(PAUSE*2);
							
							Pilot tempPilot = placing.get(index);
							placing.remove(index);
							finalPlacing.addAll(placing);
							finalPlacing.add(tempPilot);
							
							// TEST THIS ***********			
							// (possibly checks how many racers are still in the race and might end the race)
							if (finalPlacing.size() == 1)
							{
								currLap = 3;
							}
							// TEST THIS ***********
							return;
						}
			pilotAction(placing.get(index), currSection);
						if (placing.get(index).getShip().get(4) < 0)
						{
							//currLap = 3;				// WHY WAS I DOING THIS?
							
							// Remove dead pilot from placing
							System.out.print("ANNOUNCER: " + placing.get(index).getPilotName() + " is OUT of the race");
							TimeUnit.MILLISECONDS.sleep(500);
							System.out.print(".");
							TimeUnit.MILLISECONDS.sleep(500);
							System.out.print(".");
							TimeUnit.MILLISECONDS.sleep(500);
							System.out.println(".");
							TimeUnit.SECONDS.sleep(PAUSE*2);
							
							Pilot tempPilot = placing.get(index);
							placing.remove(index);
							finalPlacing.addAll(placing);
							finalPlacing.add(tempPilot);
							
							// TEST THIS ***********			
							// (possibly checks how many racers are still in the race and might end the race)
							if (finalPlacing.size() == 1)
							{
								currLap = 3;
							}
							// TEST THIS ***********
							return;
						}
		}
	}
	
	/**
	 * Checks the currently occupied section of track for the presence of mines
	 * then then rolls the pilot's dexterity value added to a randomly generated 
	 * integer as high as 50 against 30. If the pilot fails the roll, 15 points
	 * are deducted from their ship's shields.
	 * 
	 * @param pilot
	 * @param currSection
	 * @throws InterruptedException
	 */
	public void mineCheck(Pilot pilot, int currSection) throws InterruptedException
	{
		if (track.getSections().get(currSection).getMines() > 0)
		{
			// Roll against the mine using pilot's dexterity
			int dieFaceNum = 50;
			int mineDodgeLimit = 30;
			int rando = rand.nextInt(dieFaceNum);
			if ((pilot.getDexterity() + rando) < mineDodgeLimit)
			{
				// Remove offending mine from currSection 
				track.getSections().get(currSection).setMines(track.getSections().get(currSection).getMines() - 1);
				System.out.println("ANNOUNCER: Looks like " + pilot.getPilotName() + " triggered a mine!!");
				TimeUnit.SECONDS.sleep(PAUSE);
				pilot.getShip().set(4, pilot.getShip().get(4) - 15);	// Decrement shields by 15.
				System.out.println(pilot.getPilotName() + "'s shields fell to " + pilot.getShip().get(4));
				TimeUnit.SECONDS.sleep(PAUSE*2);
			}
		}
	}
	
	
	/**
	 * Handles the 'dropdown' list of available player actions presented at each
	 * section of the track.
	 * 
	 * @param pilot
	 * @param currSection
	 * @throws InterruptedException
	 */
	public void pilotAction(Pilot pilot, int currSection) throws InterruptedException
	{
		Scanner keyboard = new Scanner(System.in);
		
		if (pilot.getMines() > 0) {
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
				} else if (userInput.equalsIgnoreCase("FOCUS")) {
					focus(pilot, currSection);
					validInput = true;
					break;
				}
				System.out.println("RAM OPPONENT | TAUNT OPPONENT | DROP MINE | FOCUS");
				userInput = keyboard.nextLine();
			}
		} else {
			System.out.println("What action will " + pilot.getPilotName() + " take??");
			System.out.println("RAM OPPONENT | TAUNT OPPONENT | FOCUS");
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
				} else if (userInput.equalsIgnoreCase("FOCUS")) {
					focus(pilot, currSection);
					validInput = true;
					break;
				}
				System.out.println("RAM OPPONENT | TAUNT OPPONENT | FOCUS");
				userInput = keyboard.nextLine();
			}
		}
	}
	
	
	/**
	 * Handles turns for the peloton, according to each pilot's ship's steering
	 * mixed with a randomly generated integer rolled against the number 15
	 * (16 for first place)
	 * @throws InterruptedException
	 */
	public void takeTurn() throws InterruptedException
	{
		ArrayList<Pilot> tempPlacing = new ArrayList<Pilot>();
		tempPlacing.addAll(placing);
		
		//////////
		if (finalPlacing.size() == 1)
		{
			return;
		}
		//////////
		
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
			//////////
			if (finalPlacing.size() == 1)
			{
				return;
			}
			//////////
			rando = rand.nextInt(50);
			System.out.println("ANNOUNCER: Coming up... " + placing.get(index).getPilotName() + "!!!");
			TimeUnit.SECONDS.sleep(PAUSE);
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
	
	/**
	 * Handles jumps for the peloton, according to each pilot's ship's acceleration
	 * mixed with a randomly generated integer rolled against the number 15
	 * (16 for first place)
	 */
	public void takeJump() throws InterruptedException
	{
		ArrayList<Pilot> tempPlacing = new ArrayList<Pilot>();
		tempPlacing.addAll(placing);
		
		//////////
		if (finalPlacing.size() == 1)
		{
			return;
		}
		//////////
		
		// TAKE THE JUMP
		int rando = rand.nextInt(50);
		// FIRST PLACE
		System.out.println("ANNOUNCER: First up the ramp is "
				+ placing.get(0).getPilotName() + "!!!");
		TimeUnit.SECONDS.sleep(PAUSE);
		if (((placing.get(0).getShip().get(1)) + rando) >= 16)
		{
			System.out.println("ANNOUNCER: " + placing.get(0).getPilotName() + " made the jump!!");
			TimeUnit.SECONDS.sleep(PAUSE);
		} else {
			// TAKE DAMAGE
			placing.get(0).getShip().set(4, (placing.get(0).getShip().get(4) - 10));
			// LOSE PLACE
			Pilot tempPilot = placing.get(0);
			tempPlacing.remove(0);
			tempPlacing.add(tempPilot);
			System.out.println("ANNOUNCER: Oooh... That does NOT look good...");
			System.out.println(placing.get(0).getPilotName() + "'s shields fell to " + placing.get(0).getShip().get(4) + ".");
			TimeUnit.SECONDS.sleep(PAUSE);
			System.out.println(placing.get(0).getPilotName() + " is now in place"
					+ " " + placing.size() + ".");
			TimeUnit.SECONDS.sleep(PAUSE*2);
		}
		// ALL OTHERS
		for (int index = 1; index < placing.size(); ++index)
		{
			
			//////////
			if (finalPlacing.size() == 1)
			{
				return;
			}
			//////////
			
			rando = rand.nextInt(50);
			System.out.println("ANNOUNCER: Next up's... " + placing.get(index).getPilotName() + "!!!");
			TimeUnit.SECONDS.sleep(PAUSE);
			if (((placing.get(index).getShip().get(1)) + rando) >= 15)
			{
				System.out.println("ANNOUNCER: " + placing.get(index).getPilotName() + " made the jump!!");
				TimeUnit.SECONDS.sleep(PAUSE);
			} else {
				// TAKE DAMAGE
				placing.get(index).getShip().set(4, (placing.get(index).getShip().get(4) - 10));
				// LOSE PLACE
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
				System.out.println("ANNOUNCER: Oooh... That does NOT look good...");
				System.out.println(placing.get(index).getPilotName() + "'s shields fell to " + placing.get(index).getShip().get(4) + ".");
				TimeUnit.SECONDS.sleep(PAUSE);
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
	
	
	/**
	 * Randomly affects a randomly-selected pilot from the peloton.
	 */
	public void takeEvent() throws InterruptedException
	{
		// EVENT
		int randPilot = rand.nextInt(finalPlacing.size() - 1);
		int randEffect = rand.nextInt(4);
		
		switch (randEffect)
		{
		case (0) :			// DEXTERITY -1
			{
			placing.get(randPilot).setDexterity(placing.get(randPilot).getDexterity() - 1);
			System.out.println("ANNOUNCER: Something STRANGE has happened to "
					+ placing.get(randPilot).getPilotName() + "!!!");
			TimeUnit.SECONDS.sleep(PAUSE);
			System.out.println(placing.get(randPilot).getPilotName() + "'s dexterity fell to " + placing.get(randPilot).getDexterity() + ".");
			TimeUnit.SECONDS.sleep(PAUSE*2);
			break;
			}
		case (1) :			// MINES +1
			{
			placing.get(randPilot).setMines(placing.get(randPilot).getMines() + 1);;
			System.out.println("ANNOUNCER: Looks like "
					+ placing.get(randPilot).getPilotName() + " is storing an extra mine!!!");
			TimeUnit.SECONDS.sleep(PAUSE*2);
			break;
			}
		case (2) :			// SHIELDS -10
			{
			placing.get(randPilot).getShip().set(4, (placing.get(randPilot).getShip().get(4) - 10));
			System.out.println("ANNOUNCER: WATCH OUT, "
					+ placing.get(randPilot).getPilotName() + "!!!");
			TimeUnit.SECONDS.sleep(PAUSE);
			System.out.println("ANNOUNCER: That MASSIVE creature just took a bite out of the "
					+ placing.get(randPilot).getShipName() + "!!!");
			TimeUnit.SECONDS.sleep(PAUSE);
			System.out.println(placing.get(randPilot).getPilotName() + "'s shields fell to " + placing.get(randPilot).getShip().get(4) + ".");
			TimeUnit.SECONDS.sleep(PAUSE*2);
			break;
			}
		case (3) :			// ACCELERATION +1
			{
			placing.get(randPilot).getShip().set(1, placing.get(randPilot).getShip().get(1) + 1);
			System.out.print("ANNOUNCER: What's this energy source manifesting on the track?");
			TimeUnit.MILLISECONDS.sleep(500);
			System.out.print("?");
			TimeUnit.MILLISECONDS.sleep(500);
			System.out.println("?");
			TimeUnit.SECONDS.sleep(PAUSE);
			System.out.println("ANNOUNCER: Looks like it wants to help out "
					+ placing.get(randPilot).getPilotName() + "!!!");
			TimeUnit.SECONDS.sleep(PAUSE);
			System.out.println(placing.get(randPilot).getPilotName() + "'s acceleration rose to " + placing.get(randPilot).getShip().get(1) + ".");
			TimeUnit.SECONDS.sleep(PAUSE*2);
			break;
			}
		}
		
	}
	
	
	/**
	 * Handles the Ram Opponent pilot action by showing the player a list of available
	 * opponents and allowing them to choose from whom they'll knock off
	 * 10 shield points.
	 * 
	 * @param pilot
	 * @param userInput
	 * @throws InterruptedException
	 */
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
	
	/**
	 * Handles the Taunt Opponent pilot action by showing the player a list of available
	 * opponents and allowing them to choose from whom they'll knock off
	 * a single willpower point.
	 * 
	 * @param pilot
	 * @param userInput
	 * @throws InterruptedException
	 */
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
					// Lower opponent's willpower
					placing.get(index).setWillPower(placing.get(index).getWillPower() - 1);
					pilot.setWillPower(pilot.getWillPower() - 1);
					System.out.println(placing.get(index).getPilotName() + "'s willpower fell to " + placing.get(index).getWillPower() + ".");
					TimeUnit.SECONDS.sleep(1);
					// Raise pilot's willpower
					//System.out.println(pilot.getPilotName() + "'s willpower rose to " + pilot.getWillPower() + ".");
					//TimeUnit.SECONDS.sleep(PAUSE);
					validSecondary = true;
				}
			}
		}
	}
	
	/**
	 * Handles the Drop Mine pilot action by adding a mine to the currently occupied
	 * section of track and reevaluating the pilot's mine supply.
	 * 
	 * @param pilot
	 * @param currSection
	 * @throws InterruptedException
	 */
	public void dropMine(Pilot pilot, int currSection) throws InterruptedException 
	{
		if (pilot.getMines() > 0)
		{
			track.getSections().get(currSection).setMines(track.getSections().get(currSection).getMines() + 1);
			pilot.setMines(pilot.getMines() - 1);
			System.out.println("ANNOUNCER: What's this???");
			TimeUnit.SECONDS.sleep(PAUSE/2);
			System.out.println("ANNOUNCER: Looks like " + pilot.getPilotName() + " dropped a mine on the track!");
			TimeUnit.SECONDS.sleep(PAUSE);
			//System.out.println(track.getSections().get(currSection).getMines()); //TEST
		}
	}
	
	/**
	 * Handles the Focus pilot action by raising the pilot's dexterity and willpower
	 * field values by a single integer value.
	 * 
	 * @param pilot
	 * @param currSection
	 * @throws InterruptedException
	 */
	public void focus(Pilot pilot, int currSection) throws InterruptedException 
	{
		pilot.setDexterity(pilot.getDexterity() + 1);
		pilot.setWillPower(pilot.getWillPower() + 1);
		System.out.println(pilot.getPilotName() + "'s dexterity rose to " + pilot.getDexterity() + ".");
		TimeUnit.SECONDS.sleep(PAUSE);
		System.out.println(pilot.getPilotName() + "'s willpower rose to " + pilot.getWillPower() + ".");
		TimeUnit.SECONDS.sleep(PAUSE);
	}
}
