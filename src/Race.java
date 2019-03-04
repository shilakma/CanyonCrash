import java.util.ArrayList;
import java.util.Scanner;
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
	

	// Need to get Track, Pilot/Ship stats
	
	public Race (Track track, ArrayList<Pilot> pilots)
	{
		this.track = track;
		this.pilots = pilots;
	}
	
	// Commence race by starting, then going through a sequence of Sections - allowing players to perform actions
	// pertaining to the Section style and available attacks on available opponents, in order of current placing -
	// a number of times equal to the number of laps specified.
	public void runRace() {
		System.out.println("ON YOUR MARKS...");
		System.out.println("GET SET...");
		System.out.println("GO!!!");
		int currLap = 0;
		int currSection = 0;
		
		while (currLap < laps)
		{
			System.out.println("LAP " + (currLap + 1));
			while (currSection < (track.getSections().size()-1))
			{
				
			}
			++currLap;
		}
	}
	// Declare a winner/placing.
}
