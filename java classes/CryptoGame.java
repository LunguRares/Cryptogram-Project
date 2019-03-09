import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class CryptoGame {
	private final String GAME_STATS_FILE = "gameStats.txt";
	private String phrase;
	private String playerName;
	private int[] gameMapping;
	private int[] playerMapping;
	private int[] letterFrequency;
	private int mappingSeed;

	private final int ASCII_a = 97;
	private final int ASCII_z = 122;
	private final int NOT_MAPPED = -1;
	private final int EXIT_FAILURE = -1;
	private final int ONLY_ONE_PHRASE_IN_FILE = 1;
	private final String PHRASES_FILE = "phrases.txt";
	private final String NO_PREVIOUS_PHRASE = null;


	
	public CryptoGame(String playerName) {
		
		this.playerName = playerName;
		letterFrequency = new int[26];
		playerMapping = new int[26];
		gameMapping = new int[26];
		phrase = getPreviousPhrase();
		}
	
	private String getPreviousPhrase() {
		BufferedReader fileReader;
		File gameStatsFile;
		String phrase = NO_PREVIOUS_PHRASE;
		
		
		try {
			gameStatsFile = new File(GAME_STATS_FILE);
			fileReader = new BufferedReader(new FileReader(gameStatsFile));
			String line = fileReader.readLine();
			Scanner scanner;
			while(line!=null) {
				scanner = new Scanner(line);
				scanner.useDelimiter(",");
				if(scanner.next().equals(playerName)) {
					phrase = scanner.next();
					break;
				}
				line = fileReader.readLine();
			}
		}
		catch(Exception e) {
			System.out.println("Error when trying to load the previous sentence");
		}
		return phrase;
	}

	public String getPhrase() {
		return phrase;
	}
	
	public int[] getGameMapping(){
			return gameMapping;
	}		
	
	public int[] getPlayerMapping(){
		return playerMapping;
	}
	
	private void setGameMapping() {
		
		Random random;
		
		random = new Random();
		mappingSeed = random.nextInt();
		random = new Random(mappingSeed);
		
		generateMapping(random);
	}
	
	private void generateMapping(Random random) {
		ArrayList<Integer> numbers = new ArrayList<>();
		for(int i=0;i<26;i++)
			numbers.add(i);
		
		int contender;
		for(int i=0;i<26;i++) {
			contender = random.nextInt(26-i);
			while(numbers.get(contender)==i)
				contender = random.nextInt(26-i);
			gameMapping[i] = numbers.remove(contender);
		}
		if(gameMapping[25]==25)
			generateMapping(random);
			
	}

	private void setNewPhrase() {
		//get a random phrase from the file, save into "phrase" and return true if no error occurred and false if not  
		ArrayList<String> phrases;
		int noPhrases;
		
		phrases = getPhrases();
		noPhrases = phrases.size();
		
		if(noPhrases==0) {
			System.out.println("Error, the file contained no phrases.");
			System.exit(EXIT_FAILURE);
		}else {
			Random random = new Random();
			String potentialPhrase = phrases.get(random.nextInt(noPhrases));
			if(phrases.size()!=ONLY_ONE_PHRASE_IN_FILE)
				while(phrase.equals(potentialPhrase))
					potentialPhrase =  phrases.get(random.nextInt(noPhrases));
			phrase = potentialPhrase;
		}
		
	}
	
	public boolean loadGame() {
		
		return false;
	}
	
	public void newGame() {
		setNewPhrase();
		setGameMapping();
		setLetterFrequency();
		setInitialPlayerMapping();
		System.out.println("New CryptoGame successfully created.");
	}
	
	private ArrayList<String> getPhrases() {
		
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
			System.exit(EXIT_FAILURE);
		}
		
		return phrases;
	}
	
	private void setLetterFrequency() {
		int letter;
		String phrase;
		phrase = this.phrase.toLowerCase();
		
		for(int index=0;index<phrase.length();index++) {
			letter = phrase.charAt(index);
			if(letter>=ASCII_a && letter<=ASCII_z)
			letterFrequency[letter-ASCII_a]++;
		}
	}

	private void setInitialPlayerMapping() {
		int zMapping = 25;
		for(int index=0;index<=zMapping;index++) {
			if(letterFrequency[index]==0) {
				playerMapping[index] = index;
			}else {
				playerMapping[index] = NOT_MAPPED;
			}
				
		}
	}
	
	public boolean inputLetter(int letter,char guess) {
		int letterNum;
		int guessNum;
		
		if(letter>=ASCII_a)
			letterNum = letter - ASCII_a;
		else
			letterNum = letter - 1;
		
		guessNum = guess - ASCII_a;

		for(int index=0;index<gameMapping.length;index++) {
			if(gameMapping[index] == letterNum) {
				playerMapping[index] = guessNum;
				return index==guessNum;
			}
		}
		System.out.println("Error: inputLetter method failed.");
		return false;
	}
	
	public boolean checkAlreadyMapped(char guess) {
		boolean alreadyMapped;
		int guessNum;
		
		alreadyMapped = false;
		guessNum = guess - ASCII_a;
		
		for(int index=0;index<playerMapping.length;index++) {
			if(playerMapping[index]==guessNum && letterFrequency[index]!=0)
				alreadyMapped = true;
		}
		
		return alreadyMapped;
	}
	
	public boolean checkValueAlreadyMapped(int value) {
		boolean alreadyMapped = true;
		
		if(value>=ASCII_a)
			value = value - ASCII_a;
		else
			value = value - 1;
		
		for(int index=0;index<gameMapping.length;index++) {
			if(gameMapping[index]==value) {
				if(playerMapping[index]==NOT_MAPPED)
					alreadyMapped = false;
				else
					alreadyMapped = true;
			break;
			}
		}
		return alreadyMapped;
	}

	public void undoLetter(int letter) {
		
		for(int index=0;index<playerMapping.length;index++) {
			if(playerMapping[index] == letter && letterFrequency[index]!=0) {
				playerMapping[index] = NOT_MAPPED;
			}
		}
	}
	
	private void test() {
		System.out.println(this.playerName);
		System.out.println(this.phrase);
		System.out.println(this.mappingSeed);
		for(int i=0;i<26;i++) {
			System.out.println((char)(97+i)+" "+(char)(gameMapping[i]+97)+" "+(char)(playerMapping[i]+97)+" "+letterFrequency[i]);
		}
	}

	public static void main(String[] args) {
        CryptoGame test = new CryptoGame("Drake");
        test.newGame();
        test.test();
    }

	public int[] getLetterFrequency() {
		return letterFrequency;
	}

	public boolean checkCompletion() {
		boolean gameCompleted = true;
		
		for(int index=0;index<playerMapping.length;index++) {
			if(playerMapping[index]==NOT_MAPPED)
				gameCompleted = false;
		}
		return gameCompleted;
	}
	
	public boolean checkWin() {
		boolean gameWon = true;
				
		for(int index=0;index<playerMapping.length;index++) {
			if(playerMapping[index]!=index)
				gameWon = false;
			}
		return gameWon;
	}
	
	public void saveGame() {
			
		BufferedWriter fileWriter;
		File gameStatusFile;
		ArrayList<String> linesToSave = getLinesToSave(); 
		String lineToSave;
		
		lineToSave = playerName + "," + phrase + "," + mappingSeed + ",";
		
		for(int index=0;index<playerMapping.length;index++) {
			lineToSave = lineToSave + playerMapping[index] + ",";
		}
		linesToSave.add(lineToSave);
		
		try {
			gameStatusFile = new File(GAME_STATS_FILE);
			fileWriter = new BufferedWriter(new FileWriter(gameStatusFile));
			for(int index=0;index<linesToSave.size();index++) {
				fileWriter.write(linesToSave.get(index));
				fileWriter.newLine();
			}
			fileWriter.close();
			 
		}catch(Exception e) {
			System.out.println("Error while trying to create "+ GAME_STATS_FILE);
			System.exit(EXIT_FAILURE);
		}
		
		System.out.println("Game successfully saved.");
		
	}
	
	private ArrayList<String> getLinesToSave(){
		ArrayList<String> linesToSave = new ArrayList<>();
		BufferedReader fileReader;
		File gameStatusFile;
		
		try {
			gameStatusFile = new File(GAME_STATS_FILE);
			fileReader = new BufferedReader(new FileReader(gameStatusFile));
			String line = fileReader.readLine();
			while(line!=null) {
				if(!line.contains(playerName))
					linesToSave.add(line);
				line = fileReader.readLine();
			}
			fileReader.close(); 
		}catch(Exception e) {
			System.out.println("Error: The file "+GAME_STATS_FILE+" doesn't exist.");
			System.out.println("Creating the file now");
		}		
		return linesToSave;
	}

}