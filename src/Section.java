
/**
 * 
 * @author R Travis Pierce
 * @version 2019_03_04
 *
 */

public class Section {

	private int sectionType = -1;
	private boolean turn = false;			// 10,000
	private boolean jump = false;			// 1,000
	private boolean event = false;			// 100
	// STRAIGHTAWAY							// 10
	private int mines = 0;
	
	
	public Section (String statString)
	{
		int stats = Integer.parseInt(statString);
		if (stats > 9999)
		{
			this.turn = true;
			mines = stats - 10000;
		} else if (stats > 999) {
			this.jump = true;
			mines = stats - 1000;
		} else if (stats > 99) {
			this.event = true;
			mines = stats - 100;
		} else {
			mines = stats -10;
		}
		
		determineSectionType();
	}
	
	//	Determines the 
	public void determineSectionType()
	{
		if (this.isTurn()) {
			this.sectionType = 0;				// TURN
		} else if (this.isJump()) {
			this.sectionType = 1;				// JUMP
		} else if (this.isEvent()) {
			this.sectionType = 2;				// EVENT
		} else {
			this.sectionType = 3;				// STRAIGHTAWAY
		}
	}
	
	public int getSectionType() {
		return sectionType;
	}


	public boolean isTurn() {
		return turn;
	}


	public void setTurn(boolean turn) {
		this.turn = turn;
	}


	public boolean isJump() {
		return jump;
	}


	public void setJump(boolean jump) {
		this.jump = jump;
	}


	public boolean isEvent() {
		return event;
	}


	public void setEvent(boolean event) {
		this.event = event;
	}


	public int getMines() {
		return mines;
	}


	public void setMines(int mines) {
		this.mines = mines;
	}

}
