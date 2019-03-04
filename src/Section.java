
/**
 * 
 * @author R Travis Pierce
 * @version 2019_03_04
 *
 */

public class Section {

	private boolean turn = false;
	private boolean jump = false;
	private boolean event = false;
	private int mines = 0;
	
	
	public Section (String statString)
	{
		int stats = Integer.parseInt(statString);
		if (stats > 999)
		{
			this.turn = true;
			mines = stats - 1000;
		} else if (stats > 99) {
			this.jump = true;
			mines = stats - 100;
		} else if (stats > 9) {
			this.event = true;
			mines = stats - 10;
		}
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
