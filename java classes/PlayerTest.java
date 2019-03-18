import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

	Player testPlayer = new Player ("blair",2,4,10,5,0.5);

	@Test
	void testNumberOfSuccessfullyCompletedGames() {
		//Testing successfully completed games is incremented
		//when a game is completed successfully
		testPlayer.finishedGame(true);
		assertEquals(3,testPlayer.getCompletedGames());
		testPlayer.finishedGame(true);
		testPlayer.finishedGame(true);
		assertEquals(5,testPlayer.getCompletedGames());
		testPlayer.finishedGame(false);
		assertEquals(5,testPlayer.getCompletedGames());
	}
	
	@Test
	void testNumberOfCryptogramsPlayed() {
		//Testing the number of games played is incremented 
		//when a new game is made
		testPlayer.startedNewGame();
		assertEquals(5,testPlayer.getGamesPlayed());
		testPlayer.startedNewGame();
		testPlayer.startedNewGame();
		assertEquals(7,testPlayer.getGamesPlayed());
	}
	
	@Test
	void testCorrectGuesses(){
		//Testing the total guesses and correct guesses are 
		//incremented if a correct guess is made
		testPlayer.playerGuess(true);
		assertEquals(11,testPlayer.getTotalGuesses());
		assertEquals(6,testPlayer.getCorrectGuesses());
		testPlayer.playerGuess(true);
		testPlayer.playerGuess(true);
		assertEquals(13,testPlayer.getTotalGuesses());
		assertEquals(8,testPlayer.getCorrectGuesses());
		//Testing that the correct guesses does not increment when given a wrong guess
		//and the total guesses still increments
		testPlayer.playerGuess(false);
		assertEquals(14,testPlayer.getTotalGuesses());
		assertEquals(8,testPlayer.getCorrectGuesses());
		testPlayer.playerGuess(false);
		testPlayer.playerGuess(false);
		assertEquals(16,testPlayer.getTotalGuesses());
		assertEquals(8,testPlayer.getCorrectGuesses());
	}
}
