import java.io.IOException;
import java.util.Scanner;

public class UserInterface {

	final int NOT_MAPPED = -1;
	private final int ASCII_a = 97;
	private final int ASCII_z = 122;
	private final int GAME_COMPLETED = 1;
	private final int GAME_RUNNING = 0;

	boolean letterMapping = false;
	
	Controller controller;
	public UserInterface()
	{
	
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
		controller.newGame();
		
		display();
		while(enterLetter()!=GAME_COMPLETED) {
			display();
		}
		
		
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
	private int enterLetter()
	{
		char letter,guess;
		int[]gameMapping = controller.getGameMapping();
		String phrase = controller.getPhrase();
		
		if(letterMapping)
			System.out.println("\n\nPlease type the letter you want replaced");
		else 
			System.out.println("\n\nPlease type the number you want replaced");

		Scanner scanner = new Scanner(System.in);
		letter = scanner.next().toLowerCase().charAt(0);
		 
		if(checkValidLetter(letter)) { //ask for the guess
			System.out.println("What letter would you like to replace it with ?");
			guess = scanner.next().toLowerCase().charAt(0);
			while(!checkValidGuess(guess)) {
				System.out.println("Please enter a valid letter");
				guess = scanner.next().toLowerCase().charAt(0);
			}
		}else {
			scanner.close();
			return enterLetter();
		}		
		
		scanner.close();
		controller.inputLetter(letter, guess);
		if(controller.checkWin())
			return GAME_COMPLETED;
		else
			return GAME_RUNNING;
	}
	private boolean checkValidLetter(char letter) {
		if(letter >=ASCII_a && letter <= ASCII_z){
			int[] gameMapping = controller.getGameMapping();
			int letterNumber = letter - ASCII_a;
			for(int index=0;index<gameMapping.length;index++) {
				if(gameMapping[index]==letterNumber) {
					int[] letterFrequency = controller.getLetterFrequency();
					if(letterFrequency[index]!=0)
						return true;
					else
						return false;
				}
			}
		}
		return false;
	}
	private boolean checkValidGuess(char guess) {
		if(guess >= ASCII_a && guess <= ASCII_z) {
			return true;
		}
		return false;
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
		
		for(int i = 0; i < phrase.length(); i++ ){
			char s = phrase.toLowerCase().charAt(i);
			if(s == 32){
					System.out.print("  ");
			}else {
				System.out.print("_ ");
			}
		}
			
		System.out.println();
		
		for(int i = 0; i < phrase.length(); i++){
			char s = phrase.toLowerCase().charAt(i);	
			if(s == 32){
					System.out.print("  ");
			}else {
				s -= 97;
				System.out.print((char)(gameMapping[s]+97)+" ");
			}
		}
		System.out.println();
		
		for(int i = 0; i < phrase.length(); i++){
			char s = phrase.toLowerCase().charAt(i);	
			if(s == 32){
					System.out.print("  ");
			}else {
				System.out.print(letterFrequency[s-97]+ " ");
			}
		}
	}
	
	public static void main(String[] args) 
	{
		UserInterface ui = new UserInterface();
		ui.login();
		//Main menu loop
		ui.newGame();
	}
	
}