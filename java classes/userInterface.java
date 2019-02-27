import java.io.IOException;
import java.util.Scanner;

public class UserInterface {

	final int NOT_MAPPED = -1;
	private final int ASCII_a = 97;
	private final int ASCII_z = 122;
	boolean letterMapping = false;
	
	Controller controller;
	public UserInterface()
	{
	
	}
	
	public static void main(String[] args) 
	{
		UserInterface ui = new UserInterface();
		ui.login();
		ui.newGame();
		//ui.display();
		ui.enterLetter();
	}
	
	private void login()
	{
		System.out.println("Hi welcome to our cryptogram game, have fun!\nPlease enter your username or enter one if you don't already have one");
		Scanner scan = new Scanner(System.in);
		String playerName = scan.next();
		controller = new Controller(playerName);
	}
	private void newGame()
	{
		controller.newGame(letterMapping);
	}
	private void loadGame()
	{
		
	}
	private void showLeaderboard()
	{
		
	}
	private void showStats()
	{
		
	}
	private void hint()
	{
		
	}
	private void showSolution()
	{
		
	}
	private void enterLetter()
	{
		char letter,guess;
		display();	
		int[]gameMapping = controller.getGameMapping();
		String phrase = controller.getPhrase();
		System.out.println("\n\nPlease type the letter you want replaced");
		Scanner input = new Scanner(System.in);
		
		letter = input.next().toLowerCase().charAt(0);
		
		
			if(letter >=ASCII_a && letter <= ASCII_z)
			{
				System.out.println("What letter would you like to replace it with ?");
				
				guess = input.next().toLowerCase().charAt(0);
				
				 if(!(guess>96 && guess <123))
					{
						System.out.println("Please enter a valid character");
					}
			}
				
		input.close();
		//controller.inputLetter(letter, guess);
	}
	private void undo()
	{
		
	}
	private void refresh()
	{
		
	}
	private void exit()
	{
		//System.exit(0);
	}
	
	private void display()
	{	    
		int[] gameMapping = controller.getGameMapping();
		int[] letterFrequency = controller.getLetterFrequency();
		String phrase = controller.getPhrase();	
		
		
			for(int i = 0; i < phrase.length(); i++ )
			{
				char s = phrase.toLowerCase().charAt(i);
				if(s == 32)
				{
						System.out.print("  ");
				}
				else
				{
					System.out.print("_ ");
				}
			}
			
		System.out.println();
			
			for(int i = 0; i < phrase.length(); i++)
			{
				char s = phrase.toLowerCase().charAt(i);	
				if(s == 32)
				{
						System.out.print("  ");
				}
				else
				{
					s -= 97;
					System.out.print((char)(gameMapping[s]+97)+" ");
				}
			}
			System.out.println();
			for(int i = 0; i < phrase.length(); i++)
			{
				char s = phrase.toLowerCase().charAt(i);	
				if(s == 32)
				{
						System.out.print("  ");
				}
				else
				{
					System.out.print(letterFrequency[s-97]+ " ");
				}
			}
	}
}
	