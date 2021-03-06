package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
		if(word == null || word.equals("")) {
			return false;
		}
		TrieNode currTrieNode = this.root;
		TrieNode prevTrieNode = this.root;
		String wordToLower = word.toLowerCase();
	    for (int i = 0; i < wordToLower.length(); i++) {
	    	char ch = wordToLower.charAt(i);
	    	currTrieNode = prevTrieNode.insert(ch);
	    	if(currTrieNode == null) {
	    		currTrieNode = prevTrieNode.getChild(ch);
	    	}
	    	prevTrieNode = currTrieNode;
		}
	    if(!currTrieNode.endsWord()) {
	    	currTrieNode.setEndsWord(true);
	    	this.size++;
	    	return true;
	    }
	    else {
	    	return false;
	    }
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    return this.size;
	}
	
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) 
	{
		String word = s.toLowerCase();
		TrieNode node = this.root;
		if(node != null && !word.equals("")) {
		    for (int i = 0; i < word.length(); i++) {
		    	node = node.getChild(word.charAt(i));
		    	if (node == null) {
		    		return false;
		    	}
			}
		    if(node.getText().equals(word)) {
		    	return true;
		    }
		}
		return false;
	}

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 List<String> result = new ArrayList<>();
    	 if(prefix == null || this.size == 0 || numCompletions <= 0) {
    		 return result;
    	 }
    	 String word = prefix.toLowerCase();
    	 TrieNode currNode = this.root;
    	 for (int i = 0; i < word.length(); i++) {
    		 currNode = currNode.getChild(word.charAt(i));
    		 if(currNode == null) {
    			 return result;
    		 }
		}    	
    	Queue<TrieNode> q = new LinkedList<>();
    	q.add(currNode);
    	 while(!q.isEmpty() && result.size() < numCompletions) {
    		 currNode = q.remove();
    		 if(currNode != null && currNode.endsWord()) {
    			 result.add(currNode.getText());
    		 }
    		 Set<Character> chars = currNode.getValidNextCharacters();
    		 for (Character ch : chars) {
				TrieNode node = currNode.getChild(ch);
				if(node != null) {
					q.add(node);
				}
			}
    	 }
    	 
    	 return result;
    	 
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}