import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;


public class PlayerList {
    private final String PLAYERS_FILE = "players.txt";
    private final int EXIT_FAILURE = -1;
    private ArrayList<Player> players = new ArrayList<>();

    public PlayerList(){
        loadList();
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
    public String[] getLeaderboard(){
        Collections.sort(players);
        String[] leaderboard = new String[10];
        for (String element: leaderboard) {
            element = null;
        }

        for (int i = 0;i<10;i++){
        	if(i>= players.size()) {
        		leaderboard[i]="";
        	}else{leaderboard[i] = players.get(i).getName();
        	}
        }
        return leaderboard;
    }
    
    /**
     * This method retruns the scores of the top 10 players of the game
     * @return An array of the scores of the top 10 players, from the top down
     * If the palyer doesnt exist then the score provided will be 0
     */ 
    public double[] getLeaderboardScores(){
        Collections.sort(players);
        double[] leaderboardScores = new double[10];
        for (double element: leaderboardScores) {
            element = 0.0;
        }
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
            String name = tokens[0].toLowerCase();
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
