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

public class Track 
{
	private String fileName;
	private String trackName;
	private ArrayList<Section> sections = new ArrayList<Section>();
	
	public Track(String fileName) 
	{
		this.fileName = (fileName + ".txt");
		
		//System.out.println(this.fileName);			//TEST
		
		try
		{
			read(this.fileName);
		}
		catch (IOException e)
		{
			System.out.println("Error reading from file\n");
			e.printStackTrace();
		}
		
		//for (Section item : sections) System.out.println("\t" + item.isEvent());			// TEST
	}
	
	public void read(String fileName) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String readLine = br.readLine();		// Read in track name
		trackName = readLine;
		
		//System.out.println(trackName);			// TEST
		readLine = br.readLine();				// PRIMER
		while (readLine != null)
		{
			//readLine = br.readLine();
			sections.add(new Section(readLine));
			readLine = br.readLine();
		}
		
		br.close();
		
	}
	
	public String getTrackName()
	{
		return trackName;
	}
	
	public void setTrackName(String trackName)
	{
		this.trackName = trackName;
	}
	
	public ArrayList<Section> getSections()
	{
		return sections;
	}

}