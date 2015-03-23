import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Board {
	public List<List<Cell>> board;
	public Board parent;
	public List<Board> children;
	public Color player;
	public int size;
	public int expansions = 0;
	public Board(String filename) throws IOException{
		board = new ArrayList<List<Cell>>();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
		while ((line = br.readLine()) != null){
			String[] elems = line.split("\t");
			Arrays.asList(elems);
			List<Cell> row = new ArrayList<Cell>();
			for (String elem: elems){
				Cell currCell = new Cell(Integer.parseInt(elem));
				row.add(currCell);
			}
			board.add(row);
			
		}
		parent = null;
		children = new ArrayList<Board>();
		player = Color.BLANK;
		size = board.size();
		br.close();
	}
	
	
	public Board(Board root){
		List<List<Cell>> myboard = new ArrayList<List<Cell>>();
		for (int i = 0;i < root.board.size();i++){
			List<Cell> row = new ArrayList<Cell>();
			for (int j = 0;j < root.board.size();j++){
				Cell currCell = new Cell(root.board.get(i).get(j));
				row.add(currCell);
					
			}
			myboard.add(row);
		}
		this.board = myboard;
		this.parent = null;
		this.children = new ArrayList<Board>();
		this.player = Color.BLANK;
		this.size = root.size;
	}
	
	public boolean isGameOver(){
		for (int i = 0;i < this.size;i++){
			for (int j = 0;j < this.size;j++){
				if (this.board.get(i).get(j).color == Color.BLANK)
					return false;
			}
		}
		return true;
		
	}
	
	public int[] getScore(){
		int[] player_scores = new int[2];
		for (int i = 0;i < this.size;i++){
			for (int j = 0;j < this.size;j++){
				if (this.board.get(i).get(j).color == Color.BLUE)
					player_scores[0] += this.board.get(i).get(j).value;
				else if (this.board.get(i).get(j).color == Color.GREEN){
					player_scores[1] += this.board.get(i).get(j).value;
				}
			}
		}
		return player_scores;
	}
	
	public int getUtility(Color col){
		int blueUtil = 0;
		int greenUtil = 0;
		for (int i = 0;i < this.size;i++){
			for (int j = 0;j < this.size;j++){
				if (this.board.get(i).get(j).color == Color.BLUE)
					blueUtil += this.board.get(i).get(j).value;
				else if (this.board.get(i).get(j).color == Color.GREEN){
					greenUtil += this.board.get(i).get(j).value;
				}
			}
		}
		if (this.player == Color.BLUE){
			return (blueUtil - greenUtil);
		}else{
			return (greenUtil - blueUtil);
		}
		
	}
	
	public List<Board> expandTree(Color childColor){
		List<Board> children = new ArrayList<Board>();
		
		for (int i = 0;i < this.size;i++){
			for (int j = 0;j < this.size;j++){
				if (this.board.get(i).get(j).color == Color.BLANK){
					this.expansions++;
					Board newBoard = new Board(this);
					newBoard.turn(childColor,i,j);
					newBoard.parent = this;
					newBoard.player = childColor;
					children.add(newBoard);
				}
			}
		}
		return children;
	}
	
	public void turn(Color col, int i, int j){
		this.player = col;
		this.board.get(i).get(j).color = col;
		
		if (isOnBoard(i+1,j)){
			if (this.board.get(i+1).get(j).color == col){
				deathBlitz(col,i,j);
			}
		}
		
		if (isOnBoard(i-1,j)){
			if (this.board.get(i-1).get(j).color == col){
				deathBlitz(col,i,j);
			}
		}
		
		if (isOnBoard(i,j+1)){
			if (this.board.get(i).get(j+1).color == col){
				deathBlitz(col,i,j);
			}
		}
		
		if (isOnBoard(i,j-1)){
			if (this.board.get(i).get(j-1).color == col){
				deathBlitz(col,i,j);
			}
		}
		
	}
	
	public void deathBlitz(Color myCol, int x, int y){
		Color enemy;
		if (myCol == Color.BLUE)
			enemy = Color.GREEN;
		else
			enemy = Color.BLUE;
		
		this.board.get(x).get(y).color = myCol;
		
		if (isOnBoard(x+1,y)){
			if (this.board.get(x+1).get(y).color == enemy)
				this.board.get(x+1).get(y).color = myCol;
		}
		if (isOnBoard(x-1,y)){
			if (this.board.get(x-1).get(y).color == enemy)
				this.board.get(x-1).get(y).color = myCol;
		}
		if (isOnBoard(x,y+1)){
			if (this.board.get(x).get(y+1).color == enemy)
				this.board.get(x).get(y+1).color = myCol;
		}
		if (isOnBoard(x,y-1)){
			if (this.board.get(x).get(y-1).color == enemy)
				this.board.get(x).get(y-1).color = myCol;
		}
		
	}
	
	public boolean isOnBoard(int x, int y){
		if (x < 0 || x >= this.size || y < 0 || y >= this.size)
			return false;
		return true;
	}
	
	public void printBoard(){
		for (int i = 0;i < board.size();i++){
			
			for (int j = 0;j < board.get(i).size();j++){
				if (board.get(i).get(j).color == Color.BLUE){
					System.out.print("   (" + board.get(i).get(j).value + ", " + board.get(i).get(j).color + ") ");
				}else{
					System.out.print("   (" + board.get(i).get(j).value + ", " + board.get(i).get(j).color + ")");
				}
			}
			System.out.println();
		}
	}
}
