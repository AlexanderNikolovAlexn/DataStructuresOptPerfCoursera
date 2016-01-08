package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		String[] words = sourceText.replaceAll("[!?.,]", " ").split("[\\s]+");
		if(words.length <= 0) {
			throw new NullPointerException("Empty list!");
		}
		this.starter = words[0];
		String prevWord = this.starter;
		for (int i = 1; i < words.length; i++) {
			int index = -1;
			for (int j = 0; j < wordList.size(); j++) {
				if(wordList.get(j).getWord().equals(prevWord)) {
					index = j;
					break;
				}
			}			
			if(index != -1) {
				this.wordList.get(index).addNextWord(words[i]);
			}
			else {
				ListNode newWord = new ListNode(prevWord);
				newWord.addNextWord(words[i]);								
				this.wordList.add(newWord);
			}
			prevWord = words[i];
		}
		
		int index = -1;
		for (int j = 0; j < wordList.size(); j++) {
			if(wordList.get(j).getWord().equals(prevWord)) {
				index = j;
			}
		}
		
		if(index == -1) {
			ListNode newWord = new ListNode(prevWord);
			newWord.addNextWord(this.starter);								
			this.wordList.add(newWord);
		}
		else {
			ListNode lastWord = this.wordList.get(this.wordList.size() - 1);
			lastWord.addNextWord(this.starter);
		}
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    String currWord = this.starter;
	    String output = "";
	    if(numWords <= 0) {
	    	return output;
	    }
	    output += currWord;
	    int i = 1;
	    while(i < numWords) {
	    	String randWord = "";
	    	for (int j = 0; j < wordList.size(); j++) {
				if(wordList.get(j).getWord().equals(currWord)) {
					ListNode node = wordList.get(j);
					randWord = node.getRandomNextWord(this.rnGenerator);
					break;
				}
			}
	    	output += " " + randWord;
	    	currWord = randWord;
	    	i++;
	    }
		return output;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		train(sourceText);
	}
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		//String textString = "hi there hi Leo";
		System.out.println(textString);		
		gen.train(textString);
		gen.train(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(40));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
	    return this.nextWords.get(generator.nextInt(this.nextWords.size()));
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


