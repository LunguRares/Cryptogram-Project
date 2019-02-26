import java.util.ArrayList;

public class Player implements Comparable<Player> {
    private final boolean WIN = false;
    private String name;
    private int completedGames;
    private int gamesPlayed;
    private int totalGuesses;
    private int correctGuesses;
    private float winLossRatio;

    public Player(String playerName) {
        name = playerName;
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
        gamesPlayed++;
        updateRatio();
    }

    private void updateRatio(){
     winLossRatio = gamesPlayed/completedGames;
    }

    public float getRatio(){
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
        Float ratioOne = this.getRatio();
        Float ratioTwo = playerTwo.getRatio();
        return ratioOne.compareTo(ratioTwo);
    }
}