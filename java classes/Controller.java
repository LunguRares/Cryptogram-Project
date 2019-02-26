
public class Controller {
	//private Player player;
	//private PlayerList playerList;
	private CryptoGame currentGame;
	
	public Controller(String playerName) {
		currentGame = new CryptoGame(playerName);
	}
	
	public void newGame(boolean LETTER_MAPPING) {
		currentGame.setMappingType(LETTER_MAPPING);
		currentGame.newGame();
		
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
	
}
