import java.util.List;
public class Player {
	Color startColor = Color.BLANK;
	Board board = null;
	int nodeExpansions = 0;
	public Player(Color color, Board br){
		this.startColor = color;
		this.board = br;
	}
	
	public Board minimax(Board board, int depth, boolean isBlueTurn){
		if (board.isGameOver() || depth == 0 || board == null){
			return board;
		}
		Color childColor = Color.BLANK;
//		Color currColor = Color.BLANK;
		if (isBlueTurn){
			childColor = Color.BLUE;
//			currColor = Color.BLUE;
		}else{
			childColor = Color.GREEN;
//			currColor = Color.GREEN;
		}
		
		List<Board> children = board.expandTree(childColor);
		this.nodeExpansions += board.expansions;
		Board resultBoard = null;
		if (isBlueTurn){
			int maxVal = Integer.MIN_VALUE;
			for (Board child: children){
				int maxUtility = minimax(child, depth-1,!isBlueTurn).getUtility(this.startColor);
				if (maxVal < maxUtility){
					maxVal = maxUtility;
					resultBoard = child;
				}
			}
		}else{
			int minVal = Integer.MAX_VALUE;
			for (Board child: children){
				int minUtility = minimax(child, depth-1,!isBlueTurn).getUtility(this.startColor);
				if (minVal >= minUtility){
					minVal = minUtility;
					resultBoard = child;
				}
			}			
		}
		
		return new Board(resultBoard);
	}
	
	
	public AlphaBetaData alphabeta(Board board, int depth, boolean isBlueTurn, int alpha, int beta){
		if (board.isGameOver() || depth == 0 || board == null){
			return new AlphaBetaData(board.getUtility(this.startColor), board);
		}
		Color childColor = Color.BLANK;
//		Color currColor = Color.BLANK;
		if (isBlueTurn){
			childColor = Color.BLUE;
//			currColor = Color.BLUE;
		}else{
			childColor = Color.GREEN;
//			currColor = Color.GREEN;
		}
		
		List<Board> children = board.expandTree(childColor);
		this.nodeExpansions += board.expansions;
		if (isBlueTurn){
			Board resultBoard = null;
			for (Board child: children){
				int maxUtility = alphabeta(child, depth-1,!isBlueTurn, alpha, beta).utility;
				if (alpha < maxUtility){
					alpha = maxUtility;
					resultBoard = child;
				}
				if (beta < alpha)
					break;
			}
			return new AlphaBetaData(alpha, resultBoard);
		}else{
			Board resultBoard = null;
			for (Board child: children){
				int minUtility = alphabeta(child, depth-1,!isBlueTurn, alpha, beta).utility;
				if (beta >= minUtility){
					beta = minUtility;
					resultBoard = child;
				}
				if (beta < alpha)
					break;
			}
			return new AlphaBetaData(beta, resultBoard);
		}
		
		
	}
		
}
