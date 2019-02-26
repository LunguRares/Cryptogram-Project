import java.util.Scanner;

public class UserInterface {

	final int NOT_MAPPED = -1;
	Controller controller;
	
public UserInterface()
{

}
	public static void main(String[] args) 
	{
		UserInterface ui = new UserInterface();
		ui.login();
		ui.enterLetter();
		
	}
	private void login()
	{
		System.out.println("Hi welcome to our cryptogram game, have fun!\nPlease enter your username");
		Scanner scan = new Scanner(System.in);
		String playerName = scan.next();
		//controller.login(playerName);
		
	}
	private void newGame()
	{
		
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
		boolean letterMapping = false;
		controller = new Controller("DRAKE");
		controller.newGame(letterMapping);

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
		System.out.println("\n\nPlease type the letter you want replaced");
		Scanner input = new Scanner(System.in);
		
		char letter = input.next().toLowerCase().charAt(0);
		
		for(int i = 0 ; i < 26; i++)
		{
			if(letter-97 == gameMapping[i])
			{
				System.out.println("What letter would you like to replace it with ?");
				char guess = input.next().toLowerCase().charAt(0);
				
				 if(!(guess>96 && guess <123))
					{
						System.out.println("Please enter a valid character");
					}
				 for(int j = 0; j < phrase.length(); j++)
				 {
					 
				 }
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
		System.exit(0);
	}
}
	