import java.util.Scanner;
import java.util.ArrayList;
public class Life
{
	//Conway's game of life
	//" " = empty
	//"#" = alive
	public static void main(String[] args)
	{
		if(args.length == 0)
		{
			System.out.println("Please provide a grid size");
		}
		else
		{
			int size = Integer.parseInt(args[0]);
			Scanner reader = new Scanner(System.in);
			System.out.println("Enter initial boardstate; \'#\' = live, \' \' = dead, \'n\' = newline and \'.\'= end");
			char[] init = reader.nextLine().toCharArray();
			Life game = new Life(size, init);
		}
	}

	public Life(int boardsize, char[] init)
	{
		char[][] board = new char[boardsize][boardsize];
		int tickCount = 0;
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[0].length; j++)
			{
				board[i][j] = ' ';
			}
		}
		int row = 0;
		int column = 0;
		for(int i = 0; i < init.length; i++)
		{
			if(init[i] == '#'){board[row][column] = '#';column++;}
			else if(init[i] == 'n'){row++;column=0;}
			else if(init[i] == ' '){column++;}
			else if(init[i] == '.'){break;}

			if(column == boardsize){row++;column=0;}
		}

		while(hasLife(board))
		{
			System.out.println(printBoard(board));
			delay();
			tick(board);
			tickCount++;
		}
		System.out.println(printBoard(board));
		System.out.println("Game ended after " + tickCount + " ticks.");
	}

	public char[][] tick(char[][] board)
	{
		ArrayList<int[]> killList= new ArrayList<int[]>();
		ArrayList<int[]> breedList= new ArrayList<int[]>();
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[0].length; j++)
			{
				int neighbors = liveNeighbors(board, i, j);
				if(board[i][j]=='#' && neighbors > 3){killList.add(new int[] {i,j});}
				else if(board[i][j]=='#' && neighbors < 2){killList.add(new int[] {i,j});}
				else if(board[i][j]==' ' && neighbors == 3){breedList.add(new int[] {i,j});}
			}
		}
		//System.out.println("(2,3) " + liveNeighbors(board, 2 ,3) + " " + (board[2][3]==' ') + " \""+board[2][3]+"\"");
		//System.out.println(breedList.get(0));
		board = resolveKills(board, killList);
		board = resolveBreeds(board, breedList);
		return board;
	}

	public char[][] resolveKills(char[][] board, ArrayList<int[]> list)
	{
		for(int i = 0; i < list.size(); i++)
		{
			board[list.get(i)[0]][list.get(i)[1]] = ' ';
		}
		return board;
	}

	public char[][] resolveBreeds(char[][] board, ArrayList<int[]> list)
	{
		for(int i = 0; i < list.size(); i++)
		{
			board[list.get(i)[0]][list.get(i)[1]] = '#';
		}
		return board;
	}

	public int liveNeighbors(char[][] board, int i, int j)
	{
		int neighbors = 0;
		if(j > 0 && board[i][j-1]=='#'){neighbors++;}
		if(i < board.length-1 && j > 0 && board[i+1][j-1]=='#'){neighbors++;}
		if(i > 0 && j > 0 && board[i-1][j-1]=='#'){neighbors++;}
		if(j < board.length-1 && board[i][j+1]=='#'){neighbors++;}
		if(i < board.length-1 && j < board.length-1 && board[i+1][j+1]=='#'){neighbors++;}
		if(i > 0 && j < board.length-1 && board[i-1][j+1]=='#'){neighbors++;}
		if(i < board.length-1 && board[i+1][j]=='#'){neighbors++;}
		if(i > 0 && board[i-1][j]=='#'){neighbors++;}
		return neighbors;
	}

	public boolean hasLife(char[][] board)
	{
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[0].length; j++)
			{
				if(board[i][j] == '#'){return true;}
			}
		}
		return false;
	}

	public String printBoard(char[][] board)
	{
		String str = "";
		String horDiv = "";
		while(horDiv.length() < board.length)
		{
			horDiv = horDiv + "-";
		}
		horDiv = horDiv + "\n";
		for(int i = 0; i < board.length; i++)
		{
			//str = str + horDiv;
			for(int j = 0; j < board[0].length; j++)
			{
				//if(board[i][j] == ' '){board[i][j] = '_';}
				str = str + "|" + (board[i][j] == '#' ? board[i][j] : "_");
			}
			str = str + "|\n";
		}
		//str = str + horDiv;
		return str;
	}

	public void delay()
	{
		try
		{
			Thread.sleep(700);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}