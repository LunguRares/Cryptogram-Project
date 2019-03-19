import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserInterface {
	
	final int NOT_MAPPED = -1;
	private final int ASCII_a = 97;
	private final int ASCII_z = 122;
	private final int GAME_RUNNING = 0;
	private final int GAME_COMPLETED_WIN = 2;
	
	private String playerName;
	boolean letterMapping = true;
	Scanner scanner; 
	Controller controller;
	
	public UserInterface(){
		scanner = new Scanner(System.in);
	}
	
	/*
	 * 	Takes the player name provided by the player and either loads their player saved data or creates a new Player profile
	 */
	private void login(){
		System.out.println("Hi welcome to our cryptogram game, have fun!\nPlease enter your username or enter one if you don't already have one");
		playerName = scanner.next();
		controller = new Controller(playerName); //this requires testing (player name can't contain ',' due to formatting of the data files)
	}
	
	/*
	 * 	Does all the preparations for a new game and then allows the player to play the new cryptogram
	 */
	private void newGame(){
		controller.newGame(getMappingType());
		playCryptogram();
	}
	
	/*
	 * 	Allows the player to solve a cryptogram
	 */
	private void playCryptogram() {
		int gameState = GAME_RUNNING;
		int userOption;
		while(gameState==GAME_RUNNING) {	//while the game has not yet been completed or the user didn't chose to exit the cryptogram
			displayGameState();
			printPlayingOptions();
			userOption = getOption(4);
			switch (userOption){
				case 1:	enterLetter();		//enter a mapping
						gameState = checkWin();	//check if the game is over
						break;
						
				case 2:	undoLetter();	//undo a letter
						break;
						
				case 3: saveGame();		//save the current state of the game
						break;
						
				case 4:	this.mainMenu();	//return back to the main menu
						break;
				
				default:System.out.println("Invalid user option");
			}
		} 
	}
	
	/*
	 *	Checks if the player has already a saved game and then asks if they want to overwrite it 
	 */
	private void saveGame() {
		if(controller.playerHasGameSaved()) {		//check if the player has a saved game already
			System.out.println("Existing saved game detected. Please select an option:");
			System.out.println("1. Overwrite the currently saved game?");
			System.out.println("2. Do nothing.");
			int option = getOption(2);
			if(option==1) {
				controller.overwriteSavedGame();	//the player chose to overwrite their previously saved game
				return;
			}
			else
				return;
		}
		controller.saveGame();	//save the current game
	}

	/*
	 * 	Checks if the player has won the game. Returns 1 if the player won the game and 0 if the game is still running  
	 */
	private int checkWin() {
		if(controller.checkCompletion()){
			if(controller.checkWin()){
				controller.playerFinishedGame(true);  //the true part here makes no sense
				displayGameState();
				System.out.println("Well done you have successfully completed the cryptogram");
				controller.deleteSavedFinishedGame(); //if the player saved the cryptogram they just finished during play time, delete it 
				return GAME_COMPLETED_WIN;
			}else {		//the player completed all the mappings but some were incorrect
				System.out.println("Sorry but this is not the correct phrase. Try again");
				return GAME_RUNNING;
			}
		}else {		//there are still mappings that need to be completed 
			return GAME_RUNNING;
		}
	}

	/*
	 * 	Displays the main menu options and 
	 */
	private void mainMenu(){
		displayMainMenu();
		int option = getOption(5);
		switch(option) {
			case 1:	loadGame();		//try to load a saved game
					mainMenu();		//return back to the main menu after the game is over
					
			case 2:	newGame();		//begin a new game
					mainMenu();		//return back to the main menu after the game is over
					
			case 3:	getStats();		//display the stats of the player
					backToMainMenu();	//return back to the main menu 
					
			case 4:	showLeaderboard();	//display the leaderboard
					backToMainMenu();	//return back to the main menu
					
			case 5: controller.exit();	//take care of all the necessary cleaning required before closing the game
					System.out.println("Goodbye");
					System.exit(0);
		}
	}
	
	/*
	 * 	Prints out the main menu options
	 */
	private void displayMainMenu() {
		System.out.println("\nPlease choose one of the following options");
		System.out.println("1. Load Game");
		System.out.println("2. New Game");
		System.out.println("3. Show your stats");
		System.out.println("4. Show leaderboard");
		System.out.println("5. Exit Program");
	}

	private void getStats() {
		Player player = controller.getPlayer(playerName);
		
		System.out.println("Stats for " + player.getName());
		System.out.println("Games completed: " + player.getCompletedGames());
		System.out.println("Games played: " + player.getGamesPlayed());
		System.out.println("Total Guesses: " + player.getTotalGuesses());
		System.out.println("Correct Guesses: " + player.getCorrectGuesses());
		System.out.println("Ratio: " + player.getRatio());
	}

	/*
	 * 	Asks the player what mapping type they would prefer
	 */
	private int getMappingType() {
		System.out.println("Please select the mapping type:");
		System.out.println("1. Letter Mapping");
		System.out.println("2. Number Mapping");
		int option = getOption(2);	
		return option;
	}

	/*
	 * 	Method used whenever the user has to choose an option between NoOfOptions options
	 */
	private int getOption(int NoOptions) {	
		try{
			String input = scanner.next();
			Scanner inputReader = new Scanner(input);		
			if(inputReader.hasNextInt()) {
				int option = inputReader.nextInt();
				if(option>=1 && option<=NoOptions) {	//check that the option is between 0 and noOfOptions
					inputReader.close();
					return option;
				}else {
					inputReader.close();
					throw(new NoSuchElementException());	//throw NoSuchElementException if the value of the option is invalid. Going to get caught by the catch statement underneath
				}
			}else{
				inputReader.close();
				throw(new NoSuchElementException());		//throw NoSuchElementException if input contains no integer. Going to get caught by the catch statement underneath
			}
		}
		catch(NoSuchElementException e) {		//the input was invalid
			System.out.println("Invalid input please try again.");
			return getOption(NoOptions);		//recursively try to get a valid input
		}
	}
	
	/*
	 * 	Prints out the actions the player can choose between while it is trying to solve the cryptogram
	 */
	private void printPlayingOptions() {
		System.out.println("Please select an option;");
		System.out.println("1. Enter a letter");
		System.out.println("2. Undo a letter");
		System.out.println("3. Save Game");
		System.out.println("4. Exit to Main Menu");
		System.out.println();
	}

	/*
	 * 	Try to load a saved game. If a saved game exists then play it, if not, do nothing
	 */
	private void loadGame(){
		if(controller.loadGame())	//if loadGame() returns true then it means that a saved game was successfully loaded
			playCryptogram();
	}
	
    /*
	 *  Displays leaderboard with up to top 10 players
	 */
	private void showLeaderboard() {
	int RANK_ONE = 0;
	String[] leaderboardNames = controller.getLeaderboardNames();	
	double[] leaderboardScores = controller.getLeaderboardScores();
	if(leaderboardScores[RANK_ONE]==0) {
		System.out.println("Currently there are no scores to display");
		System.out.println("For a score to be displayed a game must be won");
		mainMenu();
	} else {
	System.out.printf("Rank\tName\tScore\n");
		for(int i =0; i < leaderboardNames.length; i++){
			if(!(leaderboardScores[i]==0)) {
				System.out.printf((i+1)+"."  + "\t" + leaderboardNames[i] + "\t" + leaderboardScores[i] + "\n");
			} else {
				System.out.printf((i+1)+"."  + "\n");
			}
			}
		}
	}


    /*
     *  Gives the user a hint for one of their mappings
     */
	private void hint(){
	    controller.displayHint();
		if(checkWin()==2) {
			mainMenu();
		}
		
	}
	
	private void showSolution(){
	controller.playerFinishedGame(false);
	}
	
	
	/*
	 * 	Allows the player to make a guess mapping
	 */
	private void enterLetter(){
		controller.displayEnterLetterMessage();
		String letterInput = scanner.next().toLowerCase();
		if(controller.checkValidLetterOrNumber(letterInput)) {		//check if the input was valid
			if(controller.checkValueIsAlreadyMapped(letterInput)) {		//check that the selected letter is not already mapped
				System.out.println("Existing mapping dettected. Please select an option:");
				System.out.println("1. Overwrite the existing mapping?");
				System.out.println("2. Keep the current mapping?");
				int option = getOption(2);
				if(option==2) {	//if the player chose to keep the current mapping then don't do anything
					return;
				}
			}
			System.out.println("What letter would you like to replace it with ?");
			String guessInput = scanner.next().toLowerCase();		//get the guess from the user 
			if(guessIsValid(guessInput)) {		//validate the guess
				controller.inputLetter(letterInput, guessInput);		//map the guess to the letter
			}else {
				System.out.println("Error: Invalid guess.");
				return;
			}
		}else {
			System.out.println("Error: Invalid letter/number, please try again.");
		}
	}
	
	/*
	 * 	Checks if the guess that the player has made is valid
	 */
	private boolean guessIsValid(String guessInput) {
		if(guessInput.length()==1) {
			char guess = guessInput.charAt(0);
			if(guess >= ASCII_a && guess <= ASCII_z) {	//guess is a letter of the alphabet
				if(controller.checkGuessAlreadyMapped(guess))
					return false;		//guess was already mapped so it is invalid
				else
					return true;		//guess was valid
			}
			return false;		//guess is not a letter of the alphabet so it is invalid
		}
		return false;		//the size of the guess input was larger than 1 so it is invalid
	}

	/*
	 * 	Deletes a mapping of the player 
	 */
	private void undoLetter(){
		char undoLetter;
		System.out.println("Please enter the letter you want to undo");
		String input = scanner.next().toLowerCase();
		if(input.length()==1)		//the input should have size 1 since it should only be 1 letter
			undoLetter = input.charAt(0);
		else {
			System.out.println("Error: Size of the input is too large. Input should be 1 character.");
			return;
		}
		if(controller.checkValidUndo(undoLetter)) {		//check that the letter that the player wants to undo has actually been mapped to something
			controller.undoLetter(undoLetter);			//if the letter is valid then delete its mapping
		}else {
			System.out.println("Error: Invalid input. Cannot undo that letter.");
			return;
		}
	}
	
	/*
	 * 	Displays the current state of the cryptogram
	 */
	private void displayGameState(){	    
		System.out.println();
		System.out.println(controller.getPlayerGuesses());	//prints out the sentence with the user mappings replaced
		System.out.println(controller.getGameMappings());	//prints out the game mappings
		System.out.println(controller.getLetterFrequencies());	//prints out the letter frequencies 
		System.out.println();
	}
	
	/*
	 * 	Returns the player back to the main menu
	 */
	private void backToMainMenu() {
		System.out.println("\n1. Exit back to main menu");
		int exitChoice = getOption(1);
			if (exitChoice == 1) {
				mainMenu();
			}
	}

	public static void main(String[] args) 
	{
		UserInterface ui = new UserInterface();
		ui.login();
		ui.mainMenu();
	}	
}
