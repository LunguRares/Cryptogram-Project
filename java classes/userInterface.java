public class userInterface {

	Controller controller;
	
public userInterface()
{
	
}
	private void login()
	{
		
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
	private void displayGameMapping()
	{
		for(int i = 0; i < this.phrase.length(); i++)
		{
			char s = this.phrase.charAt(i);	
			if(s == 32)
			{
					System.out.print(" ");
			}
			else
			{
				s -= 97;
				System.out.print((char)(gameMapping[s]+97));
			}
		}
	}
}
	

