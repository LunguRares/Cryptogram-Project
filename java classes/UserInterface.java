import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserInterface {
	
	final int NOT_MAPPED = -1;
	private final int ASCII_a = 97;
	private final int ASCII_z = 122;
	private final int GAME_COMPLETED = 1;
	private final int GAME_RUNNING = 0;

	boolean letterMapping = true;
	Scanner scanner; 
	
	Controller controller;
	
	public UserInterface(){
		scanner = new Scanner(System.in);
	}
	
	private void login(){
		System.out.println("Hi welcome to our cryptogram game, have fun!\nPlease enter your username or enter one if you don't already have one");
		Scanner scan = new Scanner(System.in);
		String playerName = scan.next();
		controller = new Controller(playerName);
	}
	private void newGame(){
		
		int gameState = GAME_RUNNING;
		int userOption;
		controller.newGame();
		getMappingType();
		while(gameState!=GAME_COMPLETED) {
			display();
			userOption = getPlayingUserOption();
			switch (userOption){
				case 1:
					gameState = enterLetter();
					break;
				case 2:
					undoLetter();
					gameState = GAME_RUNNING;
					break;
				default: 
					System.out.println("Invalid user option");
			}
		}	
		System.out.println("Hooray you are great you have successfully completed the cryptogram");
	}
	
	private void getMappingType() {
		System.out.println("Please select the mapping type:");
		System.out.println("1. Letter Mapping");
		System.out.println("2. Number Mapping");
		
		int option = getInput(2);
		
		if(option==1) {
			letterMapping = true;
		}else
			letterMapping = false;

	}

	private int getInput(int NoOptions) {
		
		try{
			String input = scanner.next();
			Scanner inputReader = new Scanner(input);
			if(inputReader.hasNextInt()) {
				int option = inputReader.nextInt();
				if(option>=1 &&  option<=NoOptions) {
					inputReader.close();
					return option;
				}else {
					inputReader.close();
					throw(new NoSuchElementException());
				}
			}else{
				inputReader.close();
				throw(new NoSuchElementException());
			}
			}
		catch(NoSuchElementException e) {
			System.out.println("Invalid input please try again");
			return getInput(NoOptions);
		}
	
		
	}

	private int getPlayingUserOption() {
		printPlayingOptions();
		return getInput(2);

	}
	
	private void printPlayingOptions() {
		System.out.println("Please select an option;");
		System.out.println("1.Enter a letter");
		System.out.println("2.Undo a letter");
		System.out.println();
	}

	private void loadGame(){
		
	}
	
	private void showLeaderboard(){
		
	}
	
	private void showStats()
	{
		
	}
	
	private void hint(){
		
	}
	
	private void showSolution(){
		
	}
	
	private int enterLetter(){
		char letter,guess;
		int[]gameMapping = controller.getGameMapping();
		String phrase = controller.getPhrase();
		
		if(letterMapping)
			System.out.println("\nPlease type the letter you want replaced");
		else 
			System.out.println("\nPlease type the number you want replaced");

		String input = scanner.next().toLowerCase();
		
		if(input.length()==1)
			letter = input.charAt(0);
		else {
			System.out.println("Error: Invalid input");
			return enterLetter();
		}
		 
		if(checkValidLetter(letter)) { //ask for the guess
			System.out.println("What letter would you like to replace it with ?");
			guess = scanner.next().toLowerCase().charAt(0);
			while(!checkValidGuess(guess)) {
				System.out.println("Please enter a valid letter");
				guess = scanner.next().toLowerCase().charAt(0);
			}
		}else {
			System.out.println("Invalid choice of letter selected");
			return enterLetter();
		}		
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

	
	private void undoLetter(){
		char undoLetter;
		System.out.println("Please enter the letter you want to undo");
		
		String input = scanner.next().toLowerCase();
		
		if(input.length()==1)
			undoLetter = input.charAt(0);
		else {
			System.out.println("Error: Invalid input");
			return;
		}

		if(checkValidUndo(undoLetter)) {
			controller.undoLetter(undoLetter - ASCII_a);
		}else {
			System.out.println("Invalid input");
			return;
		}
	}
	
	
	private boolean checkValidUndo(char undoLetter) {
		int[] playerMapping = controller.getPlayerMapping();
		int numUndoLetter = undoLetter - ASCII_a;
		for(int index = 0; index < playerMapping.length;index++){
			if(playerMapping[index] == numUndoLetter) {
					return true;
			}
		}
		return false;
		
	}

	private void refresh(){
		
	}
	
	private void exit(){
		//System.exit(0);
	}
	
	private void display(){	    
		displayPlayerGuess();
		System.out.println();
		displayGameMapping();
		System.out.println();
		displayLetterFrequency();
		System.out.println();

	}
	
	private void displayPlayerGuess() {
		String phrase = controller.getPhrase();	
		int [] playerMapping = controller.getPlayerMapping();
		for(int i = 0; i < phrase.length(); i++ ){
			char s = phrase.toLowerCase().charAt(i);
			if(s == 32){
					System.out.print("  ");
			}else {
				if(playerMapping[s- ASCII_a] == NOT_MAPPED)
					System.out.print("__ ");
				else
					if(letterMapping){
						System.out.print((char)(playerMapping[s - ASCII_a]+ASCII_a)+ "  ");
					}
			}
		}
	}
	
	private void displayGameMapping() {
		String phrase = controller.getPhrase();	
		int[] gameMapping = controller.getGameMapping();
		
		for(int i = 0; i < phrase.length(); i++){
			char s = phrase.toLowerCase().charAt(i);	
			if(s == 32){
					System.out.print("  ");
			}else {
				if(letterMapping) {
					s -= ASCII_a;
					System.out.print((char)(gameMapping[s]+ASCII_a)+"  ");
				}else {
					s -= ASCII_a;
					if(gameMapping[s]>8) 
						System.out.print((gameMapping[s]+1+" "));
					else
						System.out.print((gameMapping[s]+1)+"  ");
				}
			}
		}
	}
	
	private void displayLetterFrequency() {
		String phrase = controller.getPhrase();
		int[] letterFrequency = controller.getLetterFrequency();
	
		for(int i = 0; i < phrase.length(); i++){
			char s = phrase.toLowerCase().charAt(i);	
			if(s == 32){
					System.out.print("  ");
			}else {
				s -= ASCII_a;
				if(letterFrequency[s]>9)
					System.out.print(letterFrequency[s]+ " ");
				else 
					System.out.print(letterFrequency[s]+ "  ");
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
