import java.util.ArrayList;
import java.util.Random;

public class Mapping {
	
	private char mapping[];
	private Random randy;
	
	 public Mapping(){
		 mapping = new char[26];
		 randy = new Random();
	}
	 
	 public Mapping(int seed){
		 mapping = new char[26];
		 randy = new Random(seed);
	}
	 
	public char[] getMapping(){
		return mapping;
	}
	
	public void createMapping() {
		ArrayList<Integer> numbers = new ArrayList<>();
		for(int i=0;i<26;i++)
			numbers.add(i);
		
		int contender;
		for(int i=0;i<26;i++) {
			contender = randy.nextInt(26-i);
			while(numbers.get(contender)==i)
				contender = randy.nextInt(26-i);
			mapping[i] = (char)(numbers.remove(contender)+65);
		}
			
	}

	public static void main(String[] args) {
		Mapping m = new Mapping();
		m.createMapping();
		char[] f = m.getMapping();
		for(int i=65;i<65+26;i++) {
			System.out.println((char)i + " "+f[i-65]);
		}
	}

}
