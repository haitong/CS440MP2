
public class Cell {
	public int value;
	public Color color;
	
	public Cell(int value){
		this.value = value;
		this.color = Color.BLANK;
	}
	
	public Cell(Cell rootCell){
		this.value = rootCell.value;
		this.color = rootCell.color;
	}
}
