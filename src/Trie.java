import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Trie
{
	//Static variables
	private static final int ALPHABET_SIZE = 26;
	
	//dynamic variables
	private boolean isLeaf;
	private ArrayList<Trie> listOfTrees;   //using arrayList rather than hashmap
	
	
	
	// Constructor
	Trie() 
	{
		isLeaf = false; 
		listOfTrees = new ArrayList<>();

		//Creates an empty list with 26 null slots
		for (int i = 0; i < ALPHABET_SIZE; i++)
		{
			listOfTrees.add(null); 	
		}
		
	}

	
	
	
	//inserts new word into trie
	public void insert(String word)
	{
		Trie temp = this;
		
		
		if (word.length() >= 3) //ask group about this (minimum word length)
		{	
			//for every letter in the word
			for (int i = 0; i < word.length(); i++)
			{
				//if element in listOfTrees(x) == null, create a new Trie
				if (temp.listOfTrees.get(word.charAt(i) - 97) == null) //a=97 z=122 numerically 
				{
					temp.listOfTrees.set(word.charAt(i) - 97, new Trie());
				}
	
				// sets temp equal to the newly created trie (aka the next node)
				temp = temp.listOfTrees.get(word.charAt(i) - 97);
			}
			
			// Once done looping this marks the end of the word
			temp.isLeaf = true;
		}
		
	}

	
	
	
	
	//searches trie for specific word
	public boolean isWord(String word)
	{
		Trie temp = this; 
			
		//for every letter in the word
		for (int i = 0; i < word.length(); i++)
		{
			// sets temp equal to the next node in the trie 
			temp = temp.listOfTrees.get(word.charAt(i) - 97); //a=97 z=122 numerically

			// if the string doesn't match one in the trie it returns false
			if (temp == null)
				return false;
		}

		//If the for loop successfully loops through the entire word without anything == null
		//then it returns true.
		return temp.isLeaf;
	}
	
	
	
	public void importDictionary()
	{
				 
				//While the current line in the text file isn't null it loops adding each word(line in text file)
				//to the Trie
				BufferedReader reader;
				try 
				{
					reader = new BufferedReader(new FileReader("src/dictionary.txt"));
					String line = reader.readLine();
					while (line != null)
						{
							
							this.insert(line);	//inserts word into the trie
							line = reader.readLine();
						}
					reader.close();
				} 
				catch (IOException e) 
				{
					System.out.println("ERROR: Invalid Dictionary File.");
				}
	}
	
}

