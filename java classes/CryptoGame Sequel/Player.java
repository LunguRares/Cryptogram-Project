public class Player implements Comparable<Player> {
    private final boolean WIN = true;
    private String name;
    private int completedGames;
    private int gamesPlayed;
    private int totalGuesses;
    private int correctGuesses;
    private double winLossRatio;

    public Player(String playerName, int gamesCompleted, int playedGames, int guessesTotal, int guessesCorrect, double ratio){
        name = playerName;
        completedGames = gamesCompleted;
        gamesPlayed = playedGames;
        totalGuesses = guessesTotal;
        correctGuesses = guessesCorrect;
        winLossRatio = ratio;
    }


    public Player(String playerName) {
        name = playerName;
        completedGames = 0;
        gamesPlayed = 0;
        totalGuesses = 0;
        correctGuesses = 0;
        winLossRatio = 0;
    }


    public void playerGuess(boolean guess){
        if (guess){
            correctGuesses++;
        }
        totalGuesses++;
    }

    public void finishedGame(boolean gameState){
        if(gameState == WIN){
            completedGames++;
        }
        updateRatio();
    }
    
    public void startedNewGame() {
    	gamesPlayed++;
    	updateRatio();
    }

    private void updateRatio(){
    	if (completedGames == 0) {
    		winLossRatio = 0;
    	}else {
    		winLossRatio = (double)completedGames/(double)gamesPlayed;
    		winLossRatio = Math.round(winLossRatio * 100.0) / 100.0;
    	}
    }

    public double getRatio(){
        return winLossRatio;
    }

    public int getCompletedGames(){
        return completedGames;
    }
    public int getGamesPlayed(){
        return gamesPlayed;
    }

    public int getTotalGuesses(){
        return totalGuesses;
    }

    public int getCorrectGuesses(){
        return correctGuesses;
    }
    public String getName(){
        return name;
    }

    public int compareTo(Player playerTwo) {
        Double ratioOne = this.getRatio();
        Double ratioTwo = playerTwo.getRatio();
        return ratioOne.compareTo(ratioTwo);
    }
}
