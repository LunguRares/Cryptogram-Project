import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class testing {
	private CryptoGame test = new CryptoGame("Blair");
	@Test
	void gameStateCheck(){
		assertFalse(test.checkWin());
		assertTrue(test.checkCompletion());
	}
	@Test
	void mappingChecks() {
		int [] mappingG = test.getGameMapping();
		int[] mappingP = test.getPlayerMapping();
		assertEquals(26,mappingG.length);
		assertEquals(26,mappingP.length);
		
		assertFalse(test.checkAlreadyMapped('7'));
		test.inputLetter(97, 'f');
		assertFalse(test.checkAlreadyMapped('f'));
		test.inputLetter(97, 'f');
		assertTrue(test.checkValueAlreadyMapped('f'));
	
	}
	@Test
	void inputChecks() {
		assertFalse(test.inputLetter(-1, 'c'));
		assertFalse(test.inputLetter(9, '7'));
	}
}
