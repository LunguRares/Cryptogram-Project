import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserInterface {
	
	final int NOT_MAPPED = -1;
	private final int ASCII_a = 97;
	private final int ASCII_z = 122;
	private final int GAME_RUNNING = 0;
	private final int GAME_COMPLETED = 1;
	private final int GAME_COMPLETED_WIN = 2;

	boolean letterMapping = true;
	Scanner scanner; 
	
	Controller controller;
	
	public UserInterface(){
		scanner = new Scanner(System.in);
	}
	
	private void login(){
		System.out.println("Hi welcome to our cryptogram game, have fun!\nPlease enter your username or enter one if you don't already have one");
		scanner = new Scanner(System.in);
		String playerName = scanner.next();
		controller = new Controller(playerName);
	}
	private void newGame(){
		
		int gameState = GAME_RUNNING;
		int userOption;
		controller.newGame();
		getMappingType();
		while(gameState==GAME_RUNNING) {
			displayPlayingMenu();
			userOption = getPlayingUserOption();
			switch (userOption){
				case 1:
					enterLetter();
					gameState = checkWin();
					break;
				case 2:
					undoLetter();
					gameState = GAME_RUNNING;
					break;
				case 4:
					this.exit();
				default: 
					System.out.println("Invalid user option");
			}
		}
		printNewGameOptions();
		userOption = this.getOption(2);
		if (userOption == 1) {
			this.newGame();
		} else exit();
	}
	
	private int checkWin() {
		if(controller.checkCompletion()){
			if(controller.checkWin()){
				controller.playerFinishedGame(true);
				System.out.println("Well done you have successfully completed the cryptogram");
				return GAME_COMPLETED_WIN;
			}else {
				System.out.println("Sorry but this is not the correct phrase. Try again");
				return GAME_RUNNING;
			}
		}else {
			return GAME_RUNNING;
		}
	}

	

	private void getMappingType() {
		System.out.println("Please select the mapping type:");
		System.out.println("1. Letter Mapping");
		System.out.println("2. Number Mapping");
		
		int option = getOption(2);
		
		if(option==1) {
			letterMapping = true;
		}else
			letterMapping = false;

	}

	private int getOption(int NoOptions) {
		
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
			return getOption(NoOptions);
		}
	
		
	}

	private int getPlayingUserOption() {
		printPlayingOptions();
		return getOption(4);

	}
	
	private void printPlayingOptions() {
		System.out.println("Please select an option;");
		System.out.println("1.Enter a letter");
		System.out.println("2.Undo a letter");
		System.out.println("4.Exit Game");
		System.out.println();
	}
	private void printNewGameOptions() {
		System.out.println("Would you like to play a new game?");
		System.out.println("1. Yes");
		System.out.println("2. No");
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
	controller.playerFinishedGame(false);

	}
	
	private void enterLetter(){
		
		if(letterMapping)
			System.out.println("\nPlease type the letter you want replaced");
		else 
			System.out.println("\nPlease type the number you want replaced");

		String input = scanner.next().toLowerCase();
		if(validLetterOrNumber(input)) {
			if(valueIsAlreadyMapped(input)) {
				System.out.println("Existing mapping dettected. Please select an option:");
				System.out.println("1. Overwrite the existing mapping?");
				System.out.println("2.Keep the current mapping?");
				int option = getOption(2);
				if(option==2) {
					return;
				}
			}
			getAndMakeGuess(input);
		}else {
			System.out.println("Invalid letter/number, please try again");
		}
	}
	
	private void getAndMakeGuess(String input) {
		
		System.out.println("What letter would you like to replace it with ?");
		String guessInput = scanner.next().toLowerCase();
		if(guessIsValid(guessInput)) {
			int letter;
			char guess;
			
			if(letterMapping)
				letter = input.charAt(0);
			else {
				Scanner scanner = new Scanner(input);
				letter = scanner.nextInt();
				scanner.close();
			}
			guess = guessInput.charAt(0);
			controller.inputLetter(letter, guess);
		}else {
			System.out.println("Invalid guess");
			return;
		}
		
		
	}

	private boolean valueIsAlreadyMapped(String input) {
		if(letterMapping) {
			return controller.checkValueAlreadyMapped(input.charAt(0));
		}else {
			Scanner scanner = new Scanner(input);
			int value = scanner.nextInt();
			scanner.close();
			return controller.checkValueAlreadyMapped(value);	
		}
		
	}

	private boolean validLetterOrNumber(String input) {
		int inputNumber;
		
		if(letterMapping) {
			if(input.length()==1) {
				inputNumber = input.charAt(0)-ASCII_a;
			}else
				return false;
		}else {
			Scanner scanner = new Scanner(input);
			if(scanner.hasNextInt()) {
				inputNumber = scanner.nextInt()-1;
				scanner.close();
			} else {
				scanner.close();
				return false;
			}
		}
		if(inputNumber<0 || inputNumber>25) 
			return false;
		
		int[] gameMapping = controller.getGameMapping();
		String pottentialValidInput = null;
		for(int index=0;index<gameMapping.length;index++) {
			if(gameMapping[index]==inputNumber) {
				pottentialValidInput = (char)(index+ASCII_a) + "";
				break;
			}
		}
	
		String phrase = controller.getPhrase();
		return phrase.contains(pottentialValidInput);
	}
	
	private boolean guessIsValid(String guessInput) {
		if(guessInput.length()==1) {
			char guess = guessInput.charAt(0);
			if(guess >= ASCII_a && guess <= ASCII_z) {//Guess is a letter of the alphabet
				if(controller.checkAlreadyMapped(guess))
					return false;
				else
					return true;
			}
			return false;
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
		int[] letterFrequency = controller.getLetterFrequency();
		int numUndoLetter = undoLetter - ASCII_a;
		for(int index = 0; index < playerMapping.length;index++){
			if(playerMapping[index] == numUndoLetter && letterFrequency[index]!=0) {
					return true;
			}
		}
		return false;	
	}
	
	private void exit(){
		controller.exit();
		System.out.println("Statistics saved.");
		System.exit(0);
	}
	
	private void displayPlayingMenu(){	    
		System.out.println();
		displayPlayerGuess();
		System.out.println();
		displayGameMapping();
		System.out.println();
		displayLetterFrequency();
		System.out.println("\n");

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
				else{
					System.out.print((char)(playerMapping[s - ASCII_a]+ASCII_a)+"  ");
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
