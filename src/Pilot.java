import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author R Travis Pierce
 * @version 2019_03_04
 *
 */

public class Pilot implements Comparable<Pilot>{
	String fileName;
	
	private String pilotName = "";
	private int dexterity = 0;
	private int willPower = 0;
	private String shipName = "";
	private ArrayList<Integer> ship = new ArrayList<Integer>();
	// Max Speed | Acceleration | Braking | Steering | Shields
	private int mines = 0;
	
	public Pilot (String fileName)
	{
		this.fileName = (fileName + ".txt");
		
		//System.out.println(fileName);			//TEST
		
		try
		{
			read(this.fileName);
		}
		catch (IOException e)
		{
			System.out.println("Error reading from file\n");
			e.printStackTrace();
		}
	}
	
	public void read(String fileName) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		pilotName = br.readLine();		// Read in erroneous line
		
		
		this.dexterity = Integer.parseInt(br.readLine());
		this.willPower = Integer.parseInt(br.readLine());
		
		br.close();
	}
	
	public void readShip(String fileName) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(fileName + ".txt"));
		shipName = br.readLine();		// Read in erroneous line
		
		
		ship.add(Integer.parseInt(br.readLine()));	// MAX SPEED		0
		ship.add(Integer.parseInt(br.readLine()));	// ACCELERATION		1
		ship.add(Integer.parseInt(br.readLine()));	// BRAKING			2
		ship.add(Integer.parseInt(br.readLine()));	// STEERING			3
		ship.add(Integer.parseInt(br.readLine()));	// SHIELDS			4
		
		br.close();
	}
	
	// Sort based on ship's Acceleration stat
	@Override
	public int compareTo(Pilot p)
	{
		if (this.getShip().get(1) > p.getShip().get(1))
		{
			return 1;
		} else if (this.getShip().get(1) > p.getShip().get(1))
		{
			return -1;
		} else {
			return 0;
		}
	}

	public String getPilotName() {
		return pilotName;
	}

	public void setPilotName(String pilotName) {
		this.pilotName = pilotName;
	}

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	public int getWillPower() {
		return willPower;
	}

	public void setWillPower(int willPower) {
		this.willPower = willPower;
	}
	
	public ArrayList<Integer> getShip() {
		return ship;
	}
	
	public String getShipName() {
		return pilotName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	
	public int getMines() {
		return mines;
	}
	
	public void setMines(int mines) {
		this.mines = mines;
	}

}
