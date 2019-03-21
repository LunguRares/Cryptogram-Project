import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CryptoGame {
	
	private String playerName;
	private String phrase;
	private int[] gameMapping;		//Index = Actual letter in alphabet (0-25) ;  Value at index = Letter that the index letter maps to (0-25)
	private int[] letterFrequency;	
	private int[] playerMapping;	//Index = Actual letter in alphabet (0-25) ; Value at index = Letter that the user mapped to (0-25) ||OR|| '-1' if that letter hasn't been set yet ||OR|| value of index if letter doesn't appear in sentence
	private int mappingSeed;
	private boolean letterMapping;		//if TRUE then the mapping type is Letter and if FALSE the mapping type is Number
	
	private static final int ASCII_a = 97;
	private static final int ASCII_z = 122;
	private static final int NOT_MAPPED = -1;
	private static final int LETTER_MAPPING = 1;
	private static final int NUMBER_MAPPING = 2;
	private static final boolean LOADING_FAILED = false;
	private static final boolean LOADING_SUCCESSFULL = true;
	private static final boolean VALID_INPUT = true;
	private static final boolean INVALID_INPUT = false;
	private static final String PHRASES_FILE = "phrases.txt";
	private static final String GAMES_DATA_FILE = "gamesData.txt";
	private static final int[] englishLetterFrequencies = {8,2,5,3,11,2,2,3,8,1,1,5,3,7,7,3,1,8,6,7,4,1,1,1,2,1};

	public CryptoGame(String playerName) {
		this.playerName = playerName;
		phrase = "";
		gameMapping = new int[26];
		playerMapping = new int[26];
		letterFrequency = new int[26];
		mappingSeed = 0;
	}
	public static void main (String args[]) {
		System.out.println(englishLetterFrequencies.length);
		int count = 0;
		for(int i=0;i<englishLetterFrequencies.length;i++) {
			count+=englishLetterFrequencies[i];
		}
		System.out.println(count);
	}
	
	/*
	 * 	Sets a new phrase for the current game
	 */
	public void setNewPhrase() {
		ArrayList<String> phrases;		
		int noPhrases;
		phrases = getPhrases();			//get all the phrases
		noPhrases = phrases.size();
		Random random = new Random();
		phrase = phrases.get(random.nextInt(noPhrases));		//use a randomizer to get a random sentence
	}

	/*
	 *	Gets the phrases from the phrases file and also checks if the file contains corrupted data, at which point the program terminates   
	 */
	private ArrayList<String> getPhrases() {
		ArrayList<String> phrases = new ArrayList<>();
		File phrasesFile;
		BufferedReader fileReader;
		String phrase;
		try {
			phrasesFile = new File(PHRASES_FILE);		//open the phrase file for reading
			fileReader = new BufferedReader(new FileReader(phrasesFile)); 		//components of the file will go through a buffer
			phrase = fileReader.readLine();
			while(phrase!=null) {		//read the file line by line until the end of the file is reached
				phrase.toLowerCase();
				for(int i=0;i<phrase.length();i++) {	//check all characters in the sentence to make sure they are valid
					char letter = phrase.charAt(i);
					if(!((letter>=ASCII_a && letter<=ASCII_z) || letter==' ' || letter=='\n')){		//if letter is not valid then exit the program
						System.out.println("Phrase contained invalid characters. Terminating...");
						System.exit(-1);
					}
				}
				phrases.add(phrase);		//if this point is reached then phrase must be valid and so it is saved into the ArrayList
				phrase = fileReader.readLine();		//read a new phrase
			}
		}
		catch(Exception e) {
			System.out.println("An error occured when trying to open the phrase file. Terminating...");
			System.exit(-1);
		}
		return phrases;
	}
	
	/*
	 * 	Sets a new game mapping
	 */
	private void setGameMapping() {	
		Random random;
		random = new Random();
		mappingSeed = random.nextInt();		//use the first generated integer to create a random mapping seed (used when loading games in order to reconstruct the mapping)
		generateMapping(mappingSeed);
	}
	
	/*
	 * 	Generates a mapping based on the mapping seed it receives 
	 */
	private void generateMapping(int mappingSeed) {
		Random random = new Random(mappingSeed);
		ArrayList<Integer> numbers = new ArrayList<>();
		for(int i=0;i<26;i++)	//fills an ArrayList with numbers from 0 to 25 (inclusive)
			numbers.add(i);		//for each element in the Array, Index = Value at Index
		int contender;
		for(int i=0;i<25;i++) {		//every time the loop runs, an int from the numbers array is taken and placed into the gameMapping array
			contender = random.nextInt(26-i);		//get a random number between 0 and 26-i
			while(numbers.get(contender)==i)		//while the random number is equal to the current index (i) then get a new random number
				contender = random.nextInt(26-i);
			gameMapping[i] = numbers.remove(contender);	
		}
		if(numbers.get(0)==25)		//if by any chance the last int in the numbers Array is 25 then the mapping is invalid and the generation process is repeated 
			generateMapping(mappingSeed);
		else
			gameMapping[25] = numbers.remove(0);		
	}
	
	/*
	 *  Inspects the phrase and generates the frequency of each letter
	 */
	private void setLetterFrequency() {
		int letter;
		letterFrequency = new int[26];
		for(int index=0;index<phrase.length();index++) {		//look at each letter in the sentence and add 1 to the letter frequency of that letter
			letter = phrase.charAt(index);
			if(letter>=ASCII_a && letter<=ASCII_z)
			letterFrequency[letter-ASCII_a]++;
		}
	}
	
	/*
	 * 	Sets the initial player mapping. IF a certain letter does not appear in the sentence THEN | (Value at Index = Index) | ELSE | (Value at index = 'NOT_MAPPED'[-1])
	 */
	private void setInitialPlayerMapping() {
		for(int index=0;index<playerMapping.length;index++) {
			if(letterFrequency[index]==0) 
				playerMapping[index] = index;
			else 
				playerMapping[index] = NOT_MAPPED;		
		}
	}

	/*
	 * 	Sets the mapping type to either letter or number mapping
	 */
	public void setMappingType(int mappingType) {
		if(mappingType==1)
			letterMapping = true;
		else
			letterMapping = false;
	}

	/*
	 * 	Checks if the game was completed (i.e. there are no more unmapped letters)
	 */
	public boolean checkGameCompleted() {
		boolean gameCompleted = true;
		for(int index=0;index<playerMapping.length;index++) 
			if(playerMapping[index]==NOT_MAPPED) {		//if a letter is not mapped then the game is not completed yet
				gameCompleted = false;
				break;
			}
		return gameCompleted;
	}

	/*
	 * 	Checks if the player has won the game. To win, all player mappings must equal their index in the playerMapping array
	 */
	public boolean checkWin() {
		boolean gameWon = true;		
		for(int index=0;index<playerMapping.length;index++) 
			if(playerMapping[index]!=index) {		//in order to win all the values in the player mapping must be equal to their index
				gameWon = false;
				break;
			}
		return gameWon;
	}

	
	/*
	 * 	Creates and returns the String to be displayed in the console for the player guesses
	 */
	public String getPlayerGuesses() {
		String playerGuesses = "";
		for(int i=0;i<phrase.length();i++){
			char letter = phrase.charAt(i);
			if(letter == 32){		//if the letter is actually a 'space' character
					playerGuesses = playerGuesses + "  ";
			}else {
				if(playerMapping[letter-ASCII_a] == NOT_MAPPED)		//if the letter has not yet been mapped
					playerGuesses = playerGuesses + "__ ";
				else{
					playerGuesses = playerGuesses + (char)(playerMapping[letter-ASCII_a]+ASCII_a)+"  ";		//receive the value the player has mapped, add 97 to it to get a ASCII letter value and cast it to a character
				}
			}
		}
		playerGuesses+= "  <---Player Guesses";
		return playerGuesses;
	}

	/*
	 * 	Creates and returns the String to be displayed in the console for the game mapping
	 */
	public String getGameMappings() {
		String gameMappings = "";
		for(int i=0;i<phrase.length();i++){
			char letter = phrase.charAt(i);	
			if(letter == 32){		//if the letter is actually a 'space' character
					gameMappings = gameMappings + "  ";
			}else {
				if(letterMapping) 		//if the mapping is of letter type then letters must be displayed
					gameMappings = gameMappings + (char)(gameMapping[letter-ASCII_a]+ASCII_a) + "  ";		//receive the value of the game mapping for that letter, add 97 to it to get a ASCII letter value and cast it to a character
				else {
					if(gameMapping[letter-ASCII_a]>8) 		//condition required for correctly indenting the characters on the console
						gameMappings = gameMappings + (gameMapping[letter-ASCII_a]+1) +" ";		
					else
						gameMappings = gameMappings + (gameMapping[letter-ASCII_a]+1) + "  ";
				}
			}
		}
		gameMappings+= "  <---Cryptogram Mappings";
		return gameMappings;
	}
	
	/*
	 * 	Creates and returns the String to be displayed in the console for the letter frequencies
	 */
	public String getLetterFrequencies() {
		String letterFrequencies = "";
		for(int i=0;i<phrase.length();i++){
			char letter = phrase.charAt(i);	
			if(letter == 32){		//if the letter is actually a 'space' character
					letterFrequencies = letterFrequencies + "  ";
			}else 
				if(letterFrequency[letter-ASCII_a]>9)		//condition required for correctly indenting the characters on the console
					letterFrequencies = letterFrequencies + letterFrequency[letter-ASCII_a] + " ";
				else 
					letterFrequencies = letterFrequencies + letterFrequency[letter-ASCII_a] + "  ";
		}
		letterFrequencies+= "  <---Sentence Letter Frequencies (no. of occurences)";
		return letterFrequencies;
	}
	
	/*
	 * 	Sets up everything for a new game
	 */
	public void newGame(int mappingType) {
		System.out.println("Generating a new game...");
		this.setMappingType(mappingType);
		this.setNewPhrase();
		this.setGameMapping();
		this.setLetterFrequency();
		this.setInitialPlayerMapping();
		System.out.println("Successfully created a new game.\nGood luck!");
	}

	/*
	 * 	If a game exists then it will be loaded (return true), if not, an error message is displayed (return false) 
	 */
	public boolean loadGame() {
		File gamesDataFile;
		BufferedReader fileReader;
		String fileLine;
		System.out.println("Loading game...");
		try {
			gamesDataFile = new File(GAMES_DATA_FILE);		
			fileReader = new BufferedReader(new FileReader(gamesDataFile));		//open the game data file
			fileLine = fileReader.readLine();		//get the first line 
			while(fileLine!=null) {		//while there are still lines in the file read them and look for the player name
				if(fileLine.contains(playerName)) {		//if the line contains the player name then load the data from the line
					fileReader.close();
					return loadDataFromLine(fileLine);
				}
				fileLine = fileReader.readLine(); 	//read another line
			}
			fileReader.close();
		}
		catch(Exception e) {		//some error occurred when trying to open the data file
			System.out.println("Error occured when trying to load the previous game.");
			return LOADING_FAILED;
		}
		System.out.println("Couldn't find any saved game for player "+playerName);		//if this point is reached it means that the player had no saved game
		return LOADING_FAILED;
	}

	/*
	 * 	Loads the player data from the line and return true if data is valid and false if it is invalid
	 */
	private boolean loadDataFromLine(String fileLine) {
		Scanner scanner;
		boolean dataIsCorrupted = true;		
		scanner = new Scanner(fileLine);
		scanner.useDelimiter(",");	 //commas are used to delimit the data in the file and so it is used as a delimiter
		dataIsCorrupted = checkCorruptedPlayerName(scanner);	//check that the first token is the player name
		if(dataIsCorrupted) {
			System.out.println("Loading error occured due to corrupted player name.");
			return LOADING_FAILED;
		}
		dataIsCorrupted = checkCorruptedPhrase(scanner);		//check that the second token is a valid phrase
		if(dataIsCorrupted) {
			System.out.println("Loading error occured due to corrupted phrase.");
			return LOADING_FAILED;
		}
		dataIsCorrupted = checkCorruptedMappingType(scanner);		//check that the third token is a valid mapping type (either '1' or '2')
		if(dataIsCorrupted) {
			System.out.println("Loading error occured due to corrupted mapping type.");
			return LOADING_FAILED;
		}
		dataIsCorrupted = checkCorruptedMappingSeed(scanner);		//check that the fourth token is a valid mapping seed
		if(dataIsCorrupted) {
			System.out.println("Loading error occured due to corrupted mapping seed.");
			return LOADING_FAILED;
		}
		dataIsCorrupted = checkCorruptedPlayerMapping(scanner);		//check that the rest of the tokens are valid player mappings
		if(dataIsCorrupted) {
			System.out.println("Loading error occured due to a corrupted player mapping.");
			return LOADING_FAILED;
		}
		this.setLetterFrequency();
		scanner.close();
		System.out.println("Game has successfully been loaded. Good luck!");
		return LOADING_SUCCESSFULL;
	}
	
	/*
	 * 	Checks if the saved player name is corrupted
	 */
	private boolean checkCorruptedPlayerName(Scanner scanner) {
		if(scanner.hasNext()) {
			if(scanner.next().equals(playerName))
				return false;
		}
		return true;
	}

	/*
	 * 	Checks if the saved phrase is corrupted. If not, sets the phrase to the value returned by the scanner
	 */
	private boolean checkCorruptedPhrase(Scanner scanner) {
		if(scanner.hasNext()) {
			phrase = scanner.next();
			ArrayList<String> phrases = getPhrases();		//get all the phrases 
			if(phrases.contains(phrase)) {					//check if the phrase is in the phrases file
				return false;
			}
		}
		return true;
	}

	/*
	 * 	Checks if the mapping type is corrupted. If not, sets the mapping type to the value returned by the scanner
	 */
	private boolean checkCorruptedMappingType(Scanner scanner) {
		if(scanner.hasNextInt()) {
			int mappingType = scanner.nextInt();
			if(mappingType==LETTER_MAPPING || mappingType==NUMBER_MAPPING) {		//any other value is invalid
				setMappingType(mappingType);
				return false;
			}
		}
		return true;
	}
	
	/*
	 * 	Checks if the mapping seed is corrupted. If not, sets the mapping seed to the value returned by the scanner
	 */
	private boolean checkCorruptedMappingSeed(Scanner scanner) {
		if(scanner.hasNextInt()) {	//there are no other conditions for the seed rather than to exist
			mappingSeed = scanner.nextInt();
			generateMapping(mappingSeed);
			return false;
		}
		return true;
	}

	/*
	 * 	Checks if the player mappings are corrupted. If not,set the player mappings to the mappings returned by the scanner
	 */
	private boolean checkCorruptedPlayerMapping(Scanner scanner) {
		for(int i=0;i<26;i++) {		// 0 to <26 since there are 26 letters in the alphabet => 26 player mappings 
			if(scanner.hasNextInt()) {
				int mapping = scanner.nextInt();
				if(!(mapping==NOT_MAPPED || (mapping>=0 && mapping<=25)))	//if the player mapping is anything besides these values then it is corrupted
					return true;
				playerMapping[i] = mapping;
			}else
				return true;
		}
		return false;
	}

	/*
	 * 	Prints out the appropriate message when a player wants to make a guess mapping
	 */
	public void displayEnterLetterMessage() {
		if(letterMapping)
			System.out.println("\nPlease type the letter you want replaced");
		else 
			System.out.println("\nPlease type the number you want replaced");
	}

	/*
	 * 	Checks if the input is a letter that appears in the phrase
	 */
	public boolean checkValidLetterOrNumber(String input) {
		int inputNumber;
		if(letterMapping) {
			if(input.length()==1) {		//if the mapping is of type letter then the input should have size 1 (since it it a letter from a to z)
				inputNumber = input.charAt(0)-ASCII_a;		//get a number from 0 to 25 from the input letter. This value represents a value in the game mapping
			}else
				return INVALID_INPUT;		//if the size was larger than 1 then the input was invalid
		}else {
			Scanner scanner = new Scanner(input);		
			if(scanner.hasNextInt()) {				//if the mapping is of type Number it should be any number between 1 and 26
				inputNumber = scanner.nextInt()-1;	//get a number from 0 to 25 from the input letter. This value represents a value in the game mapping
				scanner.close();
			} else {
				scanner.close();
				return INVALID_INPUT;
			}
		}
		if(inputNumber<0 || inputNumber>25) 	//makes sure that the input was a letter from a to z or a number from 1 to 26
			return INVALID_INPUT;		
		int letterThatIsMapped = NOT_MAPPED;
		for(int index=0;index<gameMapping.length;index++) {
			if(gameMapping[index]==inputNumber) {		//look through the game mapping and stop when a value is equal to the input number  
				letterThatIsMapped = index;
				break;
			}
		}
		if(letterFrequency[letterThatIsMapped]!=0)		//if phrase contains that letter then the input was valid
			return VALID_INPUT;
		else
			return INVALID_INPUT;
	}


	/*
	 * 	Checks if the input letter has already been mapped
	 */
	public boolean checkValueIsAlreadyMapped(String input) {
		int letterToCheck;
		if(letterMapping) 
			letterToCheck = input.charAt(0) - ASCII_a;		//get a number from 0 to 25 from the input letter
		else {
			Scanner scanner = new Scanner(input);
			letterToCheck = scanner.nextInt() - 1;			//get a number from 0 to 25 from the input number
			scanner.close();
		}
		boolean alreadyMapped = true;
		for(int index=0;index<gameMapping.length;index++) {		//look at the values in the game mapping and stop when you find the one equal to the letter to be checked
			if(gameMapping[index]==letterToCheck) {				//the letter to be checked maps to the letter represented by this index
				if(playerMapping[index]==NOT_MAPPED)			//if the value of the player mapping at this index is not mapped then it means that the input letter was not already mapped 
					alreadyMapped = false;
				else
					alreadyMapped = true;
			break;
			}
		}
		return alreadyMapped;			
	}

	
	/*
	 * 	Checks if the guess letter has been already used as a value for a mapping
	 */
	public boolean checkGuessAlreadyMapped(char guess) {
		boolean alreadyMapped;
		int guessNum;
		alreadyMapped = false;
		guessNum = guess - ASCII_a;		//get a value between 0 and 25 to compare to the indexes of the player mapping
		for(int index=0;index<playerMapping.length;index++) {
			if(playerMapping[index]==guessNum && letterFrequency[index]!=0) {		//if a letter has already been mapped to the value of the guess and the guess letter appears in the phrase then that guess was already mapped
				alreadyMapped = true;
				break;
			}
		}
		return alreadyMapped;
	}

	
	/*
	 * 	Creates a mapping from the letter to the guess and returns true if the mapping is correct and false otherwise
	 */
	public boolean inputLetter(String letterInput, String guessInput) {
		int letterNum;
		int guessNum;
		if(letterMapping)
			letterNum = letterInput.charAt(0) - ASCII_a;		//get a number between 97 and 122 and subtract 97 from it to get a number between 0 and 25
		else {
			Scanner scanner = new Scanner(letterInput);
			letterNum = scanner.nextInt() - 1;				//get a number between 1 and 26 and subtract 1 from it to get a number between 0 and 25
			scanner.close();
		}
		guessNum = guessInput.charAt(0) - ASCII_a;				//get a number between 97 and 122 and subtract 97 from it to get a number between 0 and 25
		for(int index=0;index<gameMapping.length;index++) {
			if(gameMapping[index] == letterNum) {
				playerMapping[index] = guessNum;		//create the mapping
				return index==guessNum;				//if the index and guess number are equal then the mapping is correct, otherwise it is false
			}
		}
		System.out.println("Error: inputLetter method failed.");		//this part of the code should never get reached but is here in case some error occurred in the for loop
		return false;
		
	}

        /*
         *Provides the player with a hint for one of their mappings
         */
    public void getHint() {
		Random rand = new Random();
		int hint = rand.nextInt(25); 
			if(playerMapping[hint]== NOT_MAPPED) {
				playerMapping[hint] = hint;
				return;
			} else if(!(playerMapping[hint]==hint)) {
				System.out.println("You're mapping of letter " + ((char)(playerMapping[hint]+ASCII_a)) + " was incorrect and has been changed to the correct " +  (char)(hint + ASCII_a));
				playerMapping[hint] = hint;
				}else {
				getHint();
			}	
	}

	/*
	 * 	Checks that all the letters in the phrase have been mapped
	 */
	public boolean checkCompletion() {
		boolean gameCompleted = true;
		for(int index=0;index<playerMapping.length;index++) {
			if(playerMapping[index]==NOT_MAPPED)		//if there is at least one letter that is still not mapped then the game is not completed yet
				gameCompleted = false;
		}
		return gameCompleted;
	}

	
	/*
	 * 	Check that the letter that the player wants to undo has actually been mapped to something
	 */
	public boolean checkValidUndo(char undoLetter) {
		int numUndoLetter = undoLetter - ASCII_a;		//get a value between 0 and 25 
		for(int index = 0; index < playerMapping.length;index++){
			if(playerMapping[index] == numUndoLetter && letterFrequency[index]!=0) {		//check if the player has mapped any letter(index) to the letter it wants to undo and also makes sure that that letter(index) actually appears in the phrase
					return true;
			}
		}
		return false;	
	}

	/*
	 * 	Delete a mapping of the player
	 */
	public void undoLetter(char undoLetter) {
		for(int index=0;index<playerMapping.length;index++) {
			if(playerMapping[index]==undoLetter-ASCII_a && letterFrequency[index]!=0) {	//search for the player mapping that has the undo letter as its value 
				playerMapping[index] = NOT_MAPPED;
			}
		}
	}
	
	/*
	 * 	Delete the cryptogram data of the cryptogram the player just finished if it exists
	 */
	public void deleteSavedFinishedGame() {
		ArrayList<String> gameDataToSave = new ArrayList<>();
		ArrayList<String> allGamesData = this.getAllGamesData();
		for(int i=0;i<allGamesData.size();i++) {			//go through the saved games and delete the line that contains the player name and the just played phrase 
			String gameData = allGamesData.get(i);
			if(gameData.indexOf(playerName+",")==0) {
				Scanner scanner = new Scanner(gameData);
				scanner.useDelimiter(",");
				if(scanner.hasNext()){ 	//check that player name exists
					if(scanner.next().equals(playerName)) {	//check that the player name is at the correct place in the line
						if(scanner.hasNext()) { 		//check that the phrase exists
							if(!scanner.next().equals(phrase))	//check that the saved phrase matches the currently played phrase
								gameDataToSave.add(gameData);
							else {
								scanner.close();
								System.out.println("I see what you're trying to do here but I won't let you cheat. Play by the rules.");
								continue;
							}
						}else {
							scanner.close();
							System.out.println("Error: The game data file is corrupted (sentence not found)");
							continue;
						}
					}else {
						scanner.close();
						System.out.println("Error: The game data file is corrupted (player name in wrong place)");
						continue;
					}
				}else {
					System.out.println("Error: The game data file is corrupted (player name not found)");
					continue;
				}
				scanner.close();
			}
			gameDataToSave.add(gameData);		//add the line to the data to be saved
		}
		saveGameData(gameDataToSave);		//save the data to be saved 
	}

	/*
	 * 	Save the data in the array to the game data file (by overwriting the current file)
	 */
	private void saveGameData(ArrayList<String> gameDataToSave) {
		File gameDataFile;
		BufferedWriter fileWriter; 
		try {
			gameDataFile = new File(GAMES_DATA_FILE);		//open the game data file
			fileWriter = new BufferedWriter(new FileWriter(gameDataFile));			//create a buffered writer to write to the game data file
			for(int i=0;i<gameDataToSave.size();i++)
				fileWriter.write(gameDataToSave.get(i)+"\n");		//write each piece of data on one line each
			fileWriter.close();
		}
		catch(Exception e) {
			System.out.println("Error occured when trying to save the game data");
		}
	}

	/*
	 * 	Saves the current game (no other saved game exists for the player)
	 */
	public void saveGame() {
		ArrayList<String> gameDataToSave = this.getAllGamesData();		//get all the currently saved games
		String gameData = this.getGameData();				//game data that will replace the currently saved game of the player 
		gameDataToSave.add(gameData);		//add the current game data to the data to be saved
		this.saveGameData(gameDataToSave);		//save all the game data
	}
	
	/*
	 * 	Checks if the player has already a saved game
	 */
	public boolean playerHasGameSaved() {
		boolean playerHasGameSaved = false;
		File gameDataFile;
		BufferedReader fileReader; 
		try {
			gameDataFile = new File(GAMES_DATA_FILE);		//open the game data file
			fileReader = new BufferedReader(new FileReader(gameDataFile));		//create a buffered reader to read from the game data file
			String gameData = fileReader.readLine();
			while(gameData!=null) {			//read through the game data file and check if there is any line that starts with the player name 
				if(gameData.indexOf(playerName+",")==0) {
					playerHasGameSaved = true;
					break;
				}
				gameData = fileReader.readLine();
			}
			fileReader.close();
		}
		catch(Exception e) {
			System.out.println("Error occured when trying to check if the player has a previously saved game.");
		}
		return playerHasGameSaved;
	}
	
	/*
	 * 	Overwrites the previously saved game of the player
	 */

	public void overwriteSavedGame() {
		ArrayList<String> allGamesData = this.getAllGamesData();		//get all the currently saved games
		ArrayList<String> gameDataToSave = new ArrayList<>();		//array that holds the data that will be saved
		String gameData = this.getGameData();				//game data that will replace the currently saved game of the player 
		for(int i=0;i<allGamesData.size();i++) {		//go through the saved games and when the game data to be overwritten is found, replace it with the new game data
			if(allGamesData.get(i).indexOf(playerName+",")==0)
				gameDataToSave.add(gameData);
			else
				gameDataToSave.add(allGamesData.get(i));
		}
		System.out.println("Successfully overwritten the previously saved game");
		this.saveGameData(gameDataToSave);		//save all the data to the file
	}

	/*
	 * 	Gets all the saved games data and returns it
	 */
	private ArrayList<String> getAllGamesData() {
		File gameDataFile;
		BufferedReader fileReader; 
		ArrayList<String> allGamesData = new ArrayList<>();
		try {
			gameDataFile = new File(GAMES_DATA_FILE);		//open the game data file
			fileReader = new BufferedReader(new FileReader(gameDataFile));		//create a buffered reader to read from the game data file
			String gameData = fileReader.readLine();
			while(gameData!=null) {			//read all the lines from the games data file and place them in an array
				allGamesData.add(gameData);
				gameData = fileReader.readLine();
			}
		}
		catch(Exception e) {
			System.out.println("Error occured when trying to get data for all saved games.");
		}
		return allGamesData;
	}

	/*
	 * 	Generates the game data to be saved for the current game that is being played and returns it
	 */
	private String getGameData() {
		String gameData = "";
		gameData = gameData + playerName + ",";		//save the player name
		gameData = gameData + phrase + ","; 		//save the phrase
		if(letterMapping)							//save the mapping type
			gameData = gameData + 1 + ",";
		else
			gameData = gameData + 2 + ",";
		gameData = gameData + mappingSeed + ",";	//save the mapping seed
		for(int i=0;i<playerMapping.length;i++)
			gameData = gameData + playerMapping[i] + ","; 	//save the player mappings
		return gameData;
	}
	
	/*
	 * 	Completes the player mapping with the correct mappings
	 */

	public void setSolution() {
		for(int i=0;i<playerMapping.length;i++) {
			playerMapping[i] = i;
		}
	}

	
	/*
	 * 	Returns the string to be displayed for the letter frequencies in the English language
	 */
	public String getEnglishLetterFrequencies() {
		String letterFrequencies = "";
		for(int i=0;i<phrase.length();i++){
			char letter = phrase.charAt(i);	
			if(letter == 32){		//if the letter is actually a 'space' character
					letterFrequencies = letterFrequencies + "  ";
			}else 
				if(englishLetterFrequencies[letter-ASCII_a]>9)		//condition required for correctly indenting the characters on the console
					letterFrequencies = letterFrequencies + englishLetterFrequencies[letter-ASCII_a] + " ";
				else 
					letterFrequencies = letterFrequencies + englishLetterFrequencies[letter-ASCII_a] + "  ";
		}
		letterFrequencies+= "  <---English Letter Frequencies(%)";
		return letterFrequencies;
	}

}