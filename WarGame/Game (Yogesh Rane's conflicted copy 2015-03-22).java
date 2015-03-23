import java.util.Scanner;
import java.io.IOException;
public class Game {
	public Board currBoard;
	public int MINMAX_DEPTH = 3;
	public int AB_DEPTH = 3;
	public Game(String filename)throws IOException{
		this.currBoard = new Board(filename);
//		currBoard.printBoard();
	}
	public static void main(String[] args)throws IOException{
		System.out.println("Select choice of Boards:");
		System.out.println("1. Keren");
		System.out.println("2. Narvik");
		System.out.println("3. Sevastopol");
		System.out.println("4. Smolensk");
		System.out.println("5. Westerplatte");
		Game currGame = null;
		Scanner sc = new Scanner(System.in);
		while (true){
			System.out.print("Your choice:");
			int ch = sc.nextInt();
			
			switch (ch){
				case 1:
					currGame = new Game("Keren.txt");
					break;
				case 2:
					currGame = new Game("Narvik.txt");
					break;
				case 3:
					currGame = new Game("Sevastopol.txt");
					break;
				case 4:
					currGame = new Game("Smolensk.txt");
					break;
				case 5:
					currGame = new Game("Westerplatte.txt");
					break;	
				default:
					System.out.println("Enter valid choice!");
					
			}
			if (ch > 0 && ch < 6){
				break;
			}
		}
//		sc.close();
		
		currGame.play();
		
	}
	
	public void play(){
//		int ch = 0;
		long startTime = 0;
		long endTime = 0;
		long turnTimeP1 = 0;
		long turnTimeP2 = 0;
		int turnP1 = 0;
		int turnP2 = 0;
		int p1Expansions = 0;
		int p2Expansions = 0;
		int[] score = new int[2];
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		boolean abFlag = false;
		
		AlphaBetaData abd = null;
		Board currRound = this.currBoard;
		boolean isBlueTurn = true;
		System.out.println("Select choice of Players:");
		System.out.println("1. minimax vs minimax");
		System.out.println("2. alpha-beta vs alpha-beta");
		System.out.println("3. minimax vs alpha-beta(minimax goes first)");
		System.out.println("4. minimax vs alpha-beta(alpha-beta goes first)");
		System.out.print("Your choice:");
		Scanner sc2 = new Scanner(System.in);
		int ch = sc2.nextInt();
//		if(sc.hasNextLine()){
//		    ch = sc.nextInt();
//		}
//		while (ch < 1 && ch > 4){
//			System.out.println("Enter a valid choice");
//			System.out.print("Your choice:");
//			ch = sc.nextInt();
//		}
		
		while (!currRound.isGameOver()){
			Player pl = null;
			if (isBlueTurn){
				startTime = System.currentTimeMillis();
				pl = new Player(Color.BLUE, currRound);
				if (ch == 1 || ch == 3){
					currRound = pl.minimax(currRound,MINMAX_DEPTH,true);
					endTime = System.currentTimeMillis();
					
				}else if (ch == 2 || ch == 4){
					abFlag = true;
					abd = pl.alphabeta(currRound, AB_DEPTH, true, alpha, beta);
					endTime = System.currentTimeMillis();
					
				}
				p1Expansions += pl.nodeExpansions;
				turnTimeP1 += (endTime - startTime);
				turnP1++;
			}else{
				startTime = System.currentTimeMillis();
				pl = new Player(Color.GREEN, currRound);
				if (ch == 1 || ch == 4){
					currRound = pl.minimax(currRound,MINMAX_DEPTH,false);
					endTime = System.currentTimeMillis();
				}else if (ch == 2 || ch == 3){
					abFlag = true;
					abd = pl.alphabeta(currRound, AB_DEPTH, false, alpha, beta);
					endTime = System.currentTimeMillis();
				}
				p2Expansions += pl.nodeExpansions;
				turnTimeP2 += (endTime - startTime);
				turnP2++;
			}
			if (abFlag == true){
				currRound = abd.board;
				abFlag = false;
			}
//		    score = currRound.getScore();
			isBlueTurn = !isBlueTurn;
			
		}
		
		sc2.close();
//		System.out.println("Total Time p1: " + turnTimeP1 + " and total turns: " + turnP1);
//		System.out.println("Total Time p2: " + turnTimeP2 + " and total turns: " + turnP2);
		System.out.println("Final Board State:");
		currRound.printBoard();
		long av_p1 = (turnTimeP1/turnP1);
		long av_p2 = (turnTimeP2/turnP2);
		
		long av_exp_p1 = (p1Expansions/turnP1);
		long av_exp_p2 = (p2Expansions/turnP2);
		
		System.out.println("Average Turn Time taken by Player Blue: " + av_p1);
		System.out.println("Average Turn Time taken by Player Green: " + av_p2);
		
		System.out.println("Average Nodes Expanded by Player Blue: " + av_exp_p1);
		System.out.println("Average Nodes Expanded by Player Green: " + av_exp_p2);
		
		score = currRound.getScore();
		
		if (score[0] > score[1]){
			System.out.println("Final Scores are:");
			System.out.println("Blue: " + score[0]);
			System.out.println("Green: " + score[1]);
			System.out.println("Player Blue Wins!");
		}else if (score[0] < score[1]){
			System.out.println("Final Scores are:");
			System.out.println("Blue: " + score[0]);
			System.out.println("Green: " + score[1]);
			System.out.println("Player Green Wins!");
		}else{
			System.out.println("Final Scores are:");
			System.out.println("Blue: " + score[0]);
			System.out.println("Green: " + score[1]);
			System.out.println("It's a Tie!");
		}
	}
}
