import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.management.monitor.GaugeMonitorMBean;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CryptoGameTest {

	CryptoGame game;
	private final String PHRASES_FILE = "phrases.txt";
	private final int NOT_MAPPED = -1;
	private final int ASCII_a = 97;


	
	@BeforeEach
	void setUp() throws Exception {
		game = new CryptoGame("Tester");

	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testGetPhrase() {
		String phrase = game.getPhrase();
		assertEquals(null, phrase);
		game.newGame();
		phrase = game.getPhrase();
		assertNotEquals(null, phrase);
	}
	
	@Test
	void testPhraseIsInFile() {
		game.newGame();
		
		BufferedReader fileReader;
		String phrase;
		ArrayList<String> phrases = new ArrayList<>();
		File phrasesFile;
		
		try {
			phrasesFile = new File(PHRASES_FILE);
			fileReader = new BufferedReader(new FileReader(phrasesFile));
			 phrase = fileReader.readLine();
			 while(phrase!=null) {
				 phrases.add(phrase);
				 phrase = fileReader.readLine();
			 }
			 fileReader.close();
		}
		catch(IOException e) {
			System.out.println("Error: The file "+PHRASES_FILE+" doesn't exist.");
			return;
		}
		
		phrase = game.getPhrase();
		assertTrue(phrases.contains(phrase));
	}
	
	

	@Test
	void testValidGameMapping() {
		game.newGame();
		
		int[] gameMapping = game.getGameMapping();
		int[] letterAppearance = new int[26]; //by default Java fills the array with 0
		boolean mappingToSameLetter = false;
		
		for(int i=0;i<gameMapping.length;i++) {
			letterAppearance[gameMapping[i]]++; //this should increase the value of each element in letterAppearance
			if(gameMapping[i]==i)
				mappingToSameLetter = true;
		}
		
		assertEquals(false, mappingToSameLetter);
		
		boolean mappingJustOnce = true;
		
		for(int i=0;i<letterAppearance.length;i++) {
			if(letterAppearance[i]!=1) {
				mappingJustOnce = false;
				break;
			}
		}
		
		assertEquals(true, mappingJustOnce);
	}

	@Test
	void testGetGameMapping() {
		int[] gameMapping = game.getGameMapping();
		boolean gameMappingIsEmpty = true;
		
		for(int i=0;i<gameMapping.length;i++) {
			if(gameMapping[i]!=0)
				gameMappingIsEmpty = false;
		}
		assertEquals(true, gameMappingIsEmpty);
		
		game.newGame();
		gameMapping = game.getGameMapping();
		
		for(int i=0;i<gameMapping.length;i++) {
			if(gameMapping[i]!=0)
				gameMappingIsEmpty = false;
		}
		assertEquals(false, gameMappingIsEmpty);
		
	}
	
	@Test
	void testGetPlayerMapping() {
		int[] playerMapping = game.getPlayerMapping();
		boolean playerMappingIsEmpty = true;
		
		for(int i=0;i<playerMapping.length;i++) {
			if(playerMapping[i]!=0)
				playerMappingIsEmpty = false;
		}
		assertEquals(true, playerMappingIsEmpty);
		
		playerMappingIsEmpty = true;
		game.newGame();
		playerMapping = game.getPlayerMapping();
		
		for(int i=0;i<playerMapping.length;i++) {
			if(playerMapping[i]!=0)
				playerMappingIsEmpty = false;
		}
		assertEquals(false, playerMappingIsEmpty);
	}

	@Test
	void testLoadGame() {
		fail("Not yet implemented");
	}

	@Test
	void testNewGame() {
		
	}

	@Test
	void testInputLetterMapping() {
		boolean letterNotSet;
		game.newGame();
		int[] playerMapping = game.getPlayerMapping();
		for(int i=0;i<playerMapping.length;i++) {
			letterNotSet = true;
			if(!(playerMapping[i]==NOT_MAPPED || playerMapping[i]==i))
				letterNotSet = false;
			assertEquals(true, letterNotSet);
		}
		Random random = new Random();
		int letter;
		char guess;
		
		letter = random.nextInt(26)+ASCII_a;
		guess = (char)(random.nextInt(26)+ASCII_a);
		
		game.inputLetter(letter, guess);
		playerMapping = game.getPlayerMapping();
		
		int[] gameMapping = game.getGameMapping();
		boolean letterGotMapped = false;
		
		for(int i=0;i<gameMapping.length;i++)
			if(gameMapping[i] == letter-ASCII_a) {
				if(playerMapping[i]==(guess-ASCII_a))
					letterGotMapped = true;
				break;
			}
		
		assertEquals(true, letterGotMapped);

	}
	
	@Test
	void testInputNumberMapping() {
		game.newGame();
		int[] playerMapping;
		Random random = new Random();
		int letter;
		char guess;
		
		letter = random.nextInt(26)+1;
		guess = (char)(random.nextInt(26)+ASCII_a);
		
		game.inputLetter(letter, guess);
		playerMapping = game.getPlayerMapping();
		
		int[] gameMapping = game.getGameMapping();
		boolean letterGotMapped = false;
		
		for(int i=0;i<gameMapping.length;i++)
			if(gameMapping[i] == letter-1) {
				if(playerMapping[i]==(guess-ASCII_a))
					letterGotMapped = true;
				break;
			}
		
		assertEquals(true, letterGotMapped);

	}

	@Test
	void testCheckAlreadyMapped() {
		game.newGame();
	}

	@Test
	void testCheckValueAlreadyMapped() {
		fail("Not yet implemented");
	}

	@Test
	void testUndoLetter() {
		fail("Not yet implemented");
	}

	@Test
	void testGetLetterFrequency() {
		fail("Not yet implemented");
	}

	@Test
	void testCheckCompletion() {
		fail("Not yet implemented");
	}

	@Test
	void testCheckWin() {
		fail("Not yet implemented");
	}

}
