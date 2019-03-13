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
	
	public void newGame() {
		currentGame.newGame();
		player.startedNewGame();
	}
	
	public String getPhrase() {
		String phrase;
		phrase = currentGame.getPhrase();
		return phrase;
	}
	
	public int[] getGameMapping() {
		int [] gameMapping;
		gameMapping = currentGame.getGameMapping();
		return gameMapping;
	}
	
	public int[] getPlayerMapping() {
		return currentGame.getPlayerMapping();
	}
	
	public int[] getLetterFrequency() {
		int [] letterFrequency;
		letterFrequency = currentGame.getLetterFrequency();
		return letterFrequency;
	}
	
	public String[] getLeaderboard() {
		return playerList.getLeaderboard();
	}
	
	public void inputLetter(int letter,char guess) {
		boolean correctGuess;
		correctGuess = currentGame.inputLetter(letter, guess);
		player.playerGuess(correctGuess);
	}
	
	public void undoLetter(int letter) {
		currentGame.undoLetter(letter);
	}
	
	public boolean checkCompletion() {
		return currentGame.checkCompletion(); 
	}
	
	public boolean checkWin() {
		return currentGame.checkWin();

	}
	
	public boolean checkAlreadyMapped(char guess) {
		return currentGame.checkAlreadyMapped(guess);
	}
	
	public boolean checkValueAlreadyMapped(int value) {
		return currentGame.checkValueAlreadyMapped(value);
	}
	
	public void exit() {
		currentGame.saveGame();
		playerList.saveList();
	}
	
	public void playerFinishedGame(boolean gameState) {
		player.finishedGame(gameState);
	}

	public boolean loadGame() {
		return currentGame.loadGame();
	}
}
