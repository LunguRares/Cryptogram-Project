import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class CryptoGame {
	private String phrase;
	private String playerName;
	private String previousPhrase;
	private int[] gameMapping;
	private int[] playerMapping;
	private int[] letterFrequency;
	private int mappingSeed;
	private boolean mappingType;
	
	private final boolean LETTER_MAPPING = true;
	private final boolean NUMBER_MAPPING = false;

	private final int ASCII_a = 97;
	private final int ASCII_z = 122;
	private final int NOT_MAPPED = -1;
	private final int EXIT_FAILURE = -1;
	private final String PHRASES_FILE = "phrases.txt";

	
	public CryptoGame(String playerName) {
		
		this.playerName = playerName;
		letterFrequency = new int[26];
		playerMapping = new int[26];
		gameMapping = new int[26];
		phrase = null;
		previousPhrase = null;
		}
	
	public void setMappingType(boolean mappingType) {
		this.mappingType = mappingType;
	}
	
	public int[] getGameMapping(){
		if(mappingType==NUMBER_MAPPING) {
			return gameMapping;
		}else {
			int[] letterMapping;
			letterMapping = new int[26];
			for(int index=0; index<gameMapping.length;index++) {
				letterMapping[index] = gameMapping[index] + ASCII_a;
			}
			return letterMapping;
		}
		
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
			this.phrase = phrases.get(random.nextInt(noPhrases));
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
		previousPhrase = phrase;
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
		
		if(LETTER_MAPPING)
		letterNum = letter - ASCII_a;
		
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
	
	public void undoLetter(int letter) {
		int letterNum;
		
		if(LETTER_MAPPING)
		letterNum = letter - ASCII_a;
		
		for(int index=0;index<gameMapping.length;index++) {
			if(gameMapping[index] == letterNum) {
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
}