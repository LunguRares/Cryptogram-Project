public class Controller {
	private Player player;
	private PlayerList playerList;
	private CryptoGame currentGame;
	
	public Controller(String playerName) {
		currentGame = new CryptoGame(playerName);
		playerList = new PlayerList();
		login(playerName);
	}
	
	public void login(String playerName) {
		player = playerList.getPlayer(playerName);
	}
	
	public Player getPlayer(String playerName) {
		return playerList.getPlayer(playerName);
	}
	
	/*
	 * 	Sets everything up in order to begin a new cryptogram
	 */
	public void newGame(int MappingType) {
		currentGame.newGame(MappingType);
		player.startedNewGame();
	}
	
	public String[] getLeaderboard() {
		return playerList.getLeaderboard();
	}

	/*
	 * 	Delete a mapping of the player
	 */
	public void undoLetter(char undoLetter) {
		currentGame.undoLetter(undoLetter);
	}
	
	/*
	 * 	Checks if all the letters in the phrase have been mapped
	 */
	public boolean checkCompletion() {
		return currentGame.checkCompletion(); 
	}
	
	/*
	 * 	Check if the player won the game. WIKTOR HAS TO DO SOMETHING HERE
	 */
	public boolean checkWin() {
		//WIKTOR PUT CODE HERE FOR UPDATING THE GAME COMPLETED FIELD
		return currentGame.checkWin();
	}
		
	public void exit() {
		playerList.saveList();
	}
	
	public void playerFinishedGame(boolean gameState) {
		player.finishedGame(gameState);
	}

	public boolean loadGame() {
		return currentGame.loadGame();
	}

	/*
	 * 	Returns the string to be displayed for the player guesses
	 */
	public String getPlayerGuesses() {
		return currentGame.getPlayerGuesses();
	}

	/*
	 * 	Returns the string to be displayed for the game mappings
	 */
	public String getGameMappings() {
		return currentGame.getGameMappings();
	}

	/*
	 * 	Returns the string to be displayed for the letter frequencies
	 */
	public String getLetterFrequencies() {
		return currentGame.getLetterFrequencies();
	}
	
	/*
	 * 	Prints out the appropriate message when a player wants to make a guess mapping
	 */
	public void displayEnterLetterMessage() {
		currentGame.displayEnterLetterMessage();
	}

	/*
	 * 	Checks if the value that was received as input is a letter that actually appears in the phrase
	 */
	public boolean checkValidLetterOrNumber(String input) {
		return currentGame.checkValidLetterOrNumber(input);
	}
	
	/*
	 * 	Checks if the letter that was received as input has been already mapped to a letter
	 */
	public boolean checkValueIsAlreadyMapped(String input) {
		return currentGame.checkValueIsAlreadyMapped(input);
	}

	/*
	 * 	Checks if the guess letter has been already used as a value for a mapping
	 */
	public boolean checkGuessAlreadyMapped(char guess) {
		return currentGame.checkGuessAlreadyMapped(guess);
	}

	/*
	 * 	Creates a mapping from the letter to the guess. Also updates player stats accordingly (in case the guess was correct or was incorrect)
	 */
	public void inputLetter(String letterInput, String guessInput) {
		boolean correctGuess;
		correctGuess = currentGame.inputLetter(letterInput,guessInput);
		player.playerGuess(correctGuess);
	}

	
	/*
	 * 	Checks that the letter that the player wants to undo has actually been mapped to something
	 */
	public boolean checkValidUndo(char undoLetter) {
		return currentGame.checkValidUndo(undoLetter);
	}

	/*
	 * 	Saves the current game (no other saved game exists for the player)
	 */
	public void saveGame() {
		currentGame.saveGame();
	}
	
	/*
	 * 	Delete the cryptogram data of the cryptogram the player just finished if it exists
	 */
	public void deleteSavedFinishedGame() {
		currentGame.deleteSavedFinishedGame();
	}
	
	/*
	 * 	Checks if the player has already a saved game
	 */
	public boolean playerHasGameSaved() {
		return currentGame.playerHasGameSaved();
	}

	/*
	 * 	Overwrites the previously saved game of the player
	 */
	public void overwriteSavedGame() {
		currentGame.overwriteSavedGame();
	}
}
