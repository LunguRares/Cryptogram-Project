import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CryptoGameTest {
	
	CryptoGame currentGame;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		currentGame = new CryptoGame("TestPlayer");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSaveAndLoad() {
		currentGame.newGame(1);
		this.deleteSavedGame();
		assertEquals(false,currentGame.playerHasGameSaved());		//there should be no saved game so loadGame should return false
		currentGame.saveGame();
		currentGame.loadGame();
		assertEquals(true, currentGame.playerHasGameSaved());			//there should be a saved game so loadGame should return true
		currentGame.deleteSavedFinishedGame();
		assertEquals(false, currentGame.playerHasGameSaved());		//there should be no saved game so loadGame should return false
	}
	
	@Test
	void testHint() {
		currentGame.newGame(1);
		int previousNoCorrectMappings = getNoCorrectMappings();
		currentGame.getHint();
		int newNoCorrectMappings = getNoCorrectMappings();
		assertEquals(previousNoCorrectMappings+1, newNoCorrectMappings);
	}
	
	@Test
	void testShowSolution() {
		currentGame.newGame(1);
		int noCorrectMappings = getNoCorrectMappings();
		assertNotEquals(26, noCorrectMappings);
		currentGame.setSolution();
		noCorrectMappings = getNoCorrectMappings();
		assertEquals(26, noCorrectMappings);
	}
	
	@Test
	void testFrequency() {
		currentGame.newGame(1);
		String phrase = currentGame.getPhrase();
		double[] letterCount = new double[26];
		int totalLetters = 0;
		for(int i=0;i<phrase.length();i++) {
			if(phrase.charAt(i)!=' ') {
				letterCount[phrase.charAt(i)-97]++;
				totalLetters++;
			}
		}
		double[] letterFrequencies = currentGame.getLetterFrequencyArray();
		for(int i=0;i<26;i++) {
			double letterFreq = (double)Math.round((letterCount[i]/totalLetters)*1000)/10;
			assertEquals(letterFreq, letterFrequencies[i]);
		}
	}

	private int getNoCorrectMappings() {
		int noCorrectMappings = 0;
		int[] playerMapping = currentGame.getPlayerMappingArray();
		for(int i=0;i<playerMapping.length;i++) 
			if(playerMapping[i]==i)
				noCorrectMappings++;
		return noCorrectMappings;
	}
	private void deleteSavedGame() {
		File gamesDataFile;
		BufferedReader fileReader;
		BufferedWriter fileWriter;
		String fileLine;
		ArrayList<String> dataToSave = new ArrayList<>();
		try {
			gamesDataFile = new File("gamesData.txt");		
			fileReader = new BufferedReader(new FileReader(gamesDataFile));		//open the game data file
			fileLine = fileReader.readLine();		//get the first line 
			while(fileLine!=null) {		//while there are still lines in the file read them and look for the player name
				if(!fileLine.contains("TestPlayer")) {		//if the line contains the player name then don't save it
					dataToSave.add(fileLine);
				}
				fileLine = fileReader.readLine(); 	//read another line
			}
			fileReader.close();
			fileWriter = new BufferedWriter(new FileWriter(gamesDataFile));
			for(String lineToSave: dataToSave)
				fileWriter.write(lineToSave);
			fileWriter.close();
		}
		catch(Exception e) {		//some error occurred when trying to open the data file
			return;
		}
		
	}

}
