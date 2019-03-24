import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;



public class PlayerTest {
    private final String PLAYERS_FILE = "playersTest.txt";
    private final int EXIT_FAILURE = -1;
    private ArrayList<Player> players = new ArrayList<>();

    public PlayerTest(){
        loadList();    	
    	
    }
    @Test
	void testLeaderboardOrderNames() {
		String testNames[]=getLeaderboardNames();
		assertEquals("blair",testNames[0]);
		assertEquals("bob",testNames[1]);
		assertEquals("rares",testNames[2]);
		assertEquals("james",testNames[3]);
		assertEquals("betty",testNames[4]);
		assertEquals("wiktor",testNames[5]);
		assertEquals("audrey",testNames[6]);
		assertEquals("karl",testNames[7]);
		assertEquals("chris",testNames[8]);
		assertEquals("stephen",testNames[9]);
	}
    @Test
    void testLeaderboardOrderScores() {
    	double testScores[]=getLeaderboardScores();
    	assertEquals(1.0,testScores[0]);
    	assertEquals(0.95,testScores[1]);
    	assertEquals(0.9,testScores[2]);
    	assertEquals(0.89,testScores[3]);
    	assertEquals(0.75,testScores[4]);
    	assertEquals(0.5,testScores[5]);
    	assertEquals(0.25,testScores[6]);
    	assertEquals(0.23,testScores[7]);
    	assertEquals(0.1,testScores[8]);
    	assertEquals(0.05,testScores[9]);
    }
    public static void main(String[] args)
    {
    	PlayerList playerList = new PlayerList();
    	playerList.loadList();
    }

    /**
     * This method returns the top ten players of the game
     * @return An array of the names of the top ten players, filled from the top down.
     * If the players don't exist, it will be filled with NULL's.
     * etc
     */
        public String[] getLeaderboardNames(){
        Collections.sort(players);
        String[] leaderboardNames = new String[10];
        for (int i = 0;i<10;i++){
        	if(players.size()>i) {
            leaderboardNames[i] = players.get(players.size()-i-1).getName();
        	} else {
        		leaderboardNames[i] = "";
        	}
        }
        return leaderboardNames;
    }

        
    
    /**
     * This method retruns the scores of the top 10 players of the game
     * @return An array of the scores of the top 10 players, from the top down
     * If the palyer doesnt exist then the score provided will be 0
     */ 
    public double[] getLeaderboardScores(){
        Collections.sort(players);
        double[] leaderboardScores = new double[10];
        for (int i = 0;i<10;i++){
        	if(players.size()>i) {
            leaderboardScores[i] = players.get(players.size()-i-1).getRatio();
        	} else {
        		leaderboardScores[i] = 0.0;
        	}
        }
        return leaderboardScores;
    }
    /**
     * Takes in the player name and returns the player object.
     * @param playerName - The name of the player to be searched
     * @return the player object, or NULL if the player is not found.
     */
    public Player getPlayer(String playerName){

        for (Player player: players){
            if (player.getName().equals(playerName)){
                return player;
            }
        }
        System.out.println("Player not found, creating "+ playerName);
       return newPlayer(playerName);
    }

     /** Takes in the player name and returns the player object of the new player.
      * @param playerName - The name of the player to be created.
      * @return the player object of the newly released player.
      */
    private Player newPlayer(String playerName){
        Player newPlayer;
        newPlayer = new Player(playerName);
        players.add(newPlayer);
        return newPlayer;
    }

    /** Reads the file "players.txt", and adds the lines to an array.
     * It then calls the "populatePlayerList" class to create the player objects.
     */
    public void loadList(){

        ArrayList<String> playerLines = new ArrayList<>();
        try(
        	BufferedReader reader = new BufferedReader(new FileReader(PLAYERS_FILE))){ 
            String line;
            while ((line = reader.readLine() )!= null) 
            {
                playerLines.add(line);
            }
            reader.close();
        }
       
        catch(IOException e) {
            System.out.println("Error: The file " +PLAYERS_FILE+ " doesn't exist.");
            System.exit(EXIT_FAILURE);
        }
        //System.out.println(playerLines.get(0));
        populatePlayerList(playerLines);
        
        }

    /** Takes in the lines created by the file reader and creates the player object from them.
     * @param playerLines - The file lines created by the loader class.
     */
    private void populatePlayerList(ArrayList<String> playerLines){
    	for (String playerInformation: playerLines) {
            String[] tokens = playerInformation.split(",");
            String name = tokens[0];
            int  completedGames = Integer.parseInt(tokens[1]);
            int gamesPlayed = Integer.parseInt(tokens[2]);
            int totalGuesses = Integer.parseInt(tokens[3]);
            int correctGuesses = Integer.parseInt(tokens[4]);
            double winLossRatio = Double.parseDouble(tokens[5]);
            Player player = new Player(name, completedGames,gamesPlayed,totalGuesses,correctGuesses,winLossRatio);
            players.add(player);
        }
    }

    /** Saves the current player list and their parameters to a file called "players.txt"
     */
    public void saveList(){
        try{FileWriter writer = new FileWriter(PLAYERS_FILE);

        String newLine = System.getProperty("line.separator");

        for(Player player: players){
            String line = "";
            line += player.getName() + ",";
            line += player.getCompletedGames() + ",";
            line += player.getGamesPlayed() + ",";
            line += player.getTotalGuesses() + ",";
            line += player.getCorrectGuesses() + ",";
            line += player.getRatio() + newLine;
            writer.write(line);
        }
        writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }}
