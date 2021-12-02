/**
 * Filename:
 *
 * @author: Ravi Spigner
 * @version: 2.1
 * @since: 2021-28-11
 *
 * This CLass/File Description:
 * HangmanManager is capable of retrieving, displaying, and manipulating information in a
 * game of EVIL, EVIL Hangman. HangmanManager can initialize the state of a game, return
 * the current collection of potential word answers, the number of guesses the user has left, the
 * collection of letters the user has guessed, the letter pattern that the potential word answers
 *  follow, and can decide the next collection of words (and word pattern) to use for answers,
 * based on the guesses the user makes.
 *
 */

import java.util.*;
public class HangmanManager {
   private int guessesRemain;
   private TreeSet<Character> guessedLetters;
   private Set<String> possibleWords;
   private String patternOutline;


   //pre:  'dictionary' parameter is a text file containing a set of words
   //      'length' parameter is the length of the word the user wishes to guess, throws
   //         IllegalArgumentException if length is less than 1
   //      'max' parameter is the maximum amount of guesses the user is allowed, throws
   //         IllegalArgumentException if max is less than zero
   //post: Creates the gamestate of the Hangman Game. Creates a collection of potential
   //      word answers, of the length argument. Sets the number of user guesses to max.
   //      also creates the word-guessing pattern to be displayed to the user.
   public HangmanManager(Collection<String> dictionary, int length, int max) {
      if (length < 1) {
         throw new IllegalArgumentException("Word length cannot be less than 1");
      }
      if (max < 0) {
         throw new IllegalArgumentException("Max amount of guesses cannot be less than 0");
      }
      guessesRemain = max;
      possibleWords = new TreeSet<String>();
      guessedLetters = new TreeSet<Character>();
      for(String s : dictionary) {
         if(s.length() == length) {
            possibleWords.add(s);
         }
      }
      patternOutline = "-";
      for(int i = 0; i < length - 1; i++) {
         patternOutline += " -";
      }
   }
   
   //pre:  none
   //post: Returns the current collection of potential answers (words)    
   public Set<String> words() {
      return possibleWords;
   }
   
   //pre:  none
   //post: Returns the amount of guesses the user has remaining
   public int guessesLeft() {
      return guessesRemain;
   }
   
   //pre:  none
   //post: Returns the current collection if letters the user has guessed
   public SortedSet<Character> guesses() {
      return guessedLetters;
   }
   
   //pre:  The collection of potential word answers must be greater than 0, throws
   //         IllegalStateException if not
   //post: Returns the current word pattern to be displayed
   public String pattern() {
      if (possibleWords.size() == 0) {
         throw new IllegalStateException("Set of words is empty.");
      }
      return patternOutline;
   }
   
   //pre:  'guess' parameter is the charcter that the user inputs
   //      If this character has already been guessed, and the collection of possible
   //      word answers isn't empty, throws IllegalArgumentException
   //      Additionally must have more than one guess remaining and collection of possible words 
   //      must be greater than 0. Throws IllegalStateException if either condition 
   //      is not satisfied
   //post: Returns the amount of times the 'guess'ed letter appears in the pattern of
   //      words that are potential answers. 
   //      Determines the new set of words to use as potential answers.
   //      Changes the word pattern that is displayed to the user based on their guess. 
   public int record(char guess) {
      if (guessesRemain < 1 || possibleWords.size() == 0) {
         throw new IllegalStateException("No guesses remaining.");
      }
      if (possibleWords.size() > 0 && guessedLetters.contains(guess)) {
         throw new IllegalArgumentException("Character has already been guessed.");
      }
      guessedLetters.add(guess);
      TreeMap<String, Set<String>> wordFamilies = new TreeMap<String, Set<String>>();
      for (String word: possibleWords) {
         String wordFam = patternOutline;
         char[] wordFamChars = wordFam.toCharArray();
         for (int i = 0; i < word.length(); i++) {
            if (guess == word.charAt(i)) {
               wordFamChars[2 * i] = guess;
            }
         }
         wordFam = String.valueOf(wordFamChars);
         if (!wordFamilies.containsKey(wordFam)) {
            wordFamilies.put(wordFam, new TreeSet<String>());
         }
         wordFamilies.get(wordFam).add(word);
      }
      String maxKey = findMaxKey(wordFamilies);
      possibleWords = wordFamilies.get(maxKey);
      patternOutline = maxKey;
      return guessAppearsCheck(guess);
   }
   
   //pre:  'wordFamilies' parameter refers to the collection of potential word answers
   //      with each different family in the collection characterized by a letter pattern
   //post: Finds and Returns the letter pattern with the most words that match the pattern
   private String findMaxKey (TreeMap<String, Set<String>> wordFamilies) {
      String maximumKey = patternOutline;
         for (String key : wordFamilies.keySet()) {
            Set<String> values = wordFamilies.get(key);
               if (!wordFamilies.containsKey(maximumKey) || 
                     values.size() > wordFamilies.get(maximumKey).size()) {
                  maximumKey = key;
               }
         }
      return maximumKey;
   }
   
   //pre:  'guess' parameter refers to the character guessed via user input
   //post: Returns the number of times the guess appears in the word pattern
   //      that is to be displayed.
   private int guessAppearsCheck(char guess){
      int guessAppears = 0;
      for (int i = 0; i < patternOutline.length(); i++) {
         if (guess == patternOutline.charAt(i)) {
            guessAppears++;
         }
      }
      if (guessAppears == 0) {
         guessesRemain--;
      }
      return guessAppears;
   }        
}