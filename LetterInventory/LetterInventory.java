/**
 * Filename: LetterInventory.java
 *
 * @author raspi
 * @version 2.1
 * @since 2021-28-11
 *
 * This Class/File Description:
 * This class called LetterInventory was initially developed for a Data Structures course in 2017.
 * Its implementation has been updated over the years to improve performance and fix bugs.
 * An instance of LetterInventory stores information about a piece of text, specifically
 * how many of each letter of the alphabet appear in the text(like an inventory of letters).
 * LetterInventory also allows a user to search for information via the inventory of letters (# of
 * occurrences of a letter, if the inventory of letters is empty, how many letters
 * are in the inventory) as well as manipulate the inventory of letters (adding letters, removing
 * letters, converting the inventory of letters to text).
 * LetterInventory also includes the functionality of adding or subtracting 2 inventories.
 *
 */
public class LetterInventory {
   private int[] letterCounts; // array of integers describing how many times each letter of the
                               // alphabet appears in a word (The Inventory)
   private int numOfLetters; // the number of letters (excluding non-alphabetic charcters) in the String
   public static final int ALPHABET_LENGTH = 26; // A class constant representing 26 letters in the alphabet

   /**
    * When an instance of LetterInventory is created by invoking this constructor,
    * an array of integers is created of size 26, with each index representing a different
    * letter of the alphabet (in alphabetical order).
    * The number of each index of this array corresponds to the ordered letters of the alphabet.
    * The integer/data at each index represents the number of times it occurs in @param data.
    * @param data Contents of input file as a text string for LetterInventory instantiation
    */
   public LetterInventory(String data) {
      letterCounts = new int[ALPHABET_LENGTH];
      data = data.toLowerCase();
      for (int i = 0; i < data.length(); i++) { // go through each element of the string
         char ch = data.charAt(i); // turn the element of the string into a char
         if (Character.isLetter(ch)) {  // if that char is a letter
            numOfLetters++;
            letterCounts[ch - 'a']+= 1; // increment letterCounts for that letter
         }
         // else {
         // }
      }
   }

   /**
    * Returns the sum of this LetterInventory and @param other LetterInventory, combining
    * the number of letters in both Letter Inventories into their sum.
    * @param other LetterInventory object to add
    * @return Returns the sum of this LetterInventory and @param other inventory
    */
   public LetterInventory add(LetterInventory other) {
      LetterInventory sumInventory = new LetterInventory("");
      for(int i = 0; i < 26; i++) {
         sumInventory.letterCounts[i] = (other.letterCounts[i]+this.letterCounts[i]);
         sumInventory.numOfLetters += (sumInventory.letterCounts[i]);
      }  
      return sumInventory;
   }

   /**
    * Returns the difference between this LetterInventory and @param other LetterInventory, subtracting
    * the number of letters in @param other LetterInventory from this LetterInventory
    * unless a difference calculated is negative (returns null).
    * @param other LetterInventory object to subtract
    * @return Returns the difference between this LetterInventory and @param other LetterInventory
    */
   public LetterInventory subtract(LetterInventory other) {
      LetterInventory diffInventory = new LetterInventory("");  
      for(int i = 0; i < 26; i++) {
         diffInventory.letterCounts[i] = (this.letterCounts[i]-other.letterCounts[i]);
         diffInventory.numOfLetters += (diffInventory.letterCounts[i]);
         if (diffInventory.letterCounts[i] < 0) {
            return null;
         }
      }  
      return diffInventory;
   }

   /**
    * Return the total number of all letters in the inventory
    * @return Return the total number of all letters in the inventory
    */
   public int size(){
      return numOfLetters;
   }

   /**
    * Returns true if the inventory contains no letters
    * @return Returns true if the inventory contains no letters
    */
   public boolean isEmpty() {
      return numOfLetters == 0; 
   }

   /**
    * Returns a count of how many times @param letter exists in this LetterInventory.
    * Throws IllegalArgumentException if @param letter is not alphabetical (A-Z, a-z)
    * @param letter Character to find instances of
    * @return Returns a count of how many times @param letter exists
    */
   public int get(char letter) {
      if (!Character.isLetter(letter)) {
         throw new IllegalArgumentException("Must enter an alphabetic charcter");
      }
      letter = Character.toLowerCase(letter);
      return letterCounts[letter - 'a']; // This math operation finds the value
                                         // located at the index of the letter argument
   }

   /**
    * Returns a string representation of the contents of this LetterInventory.
    * The string representation consists of the letters in this LetterInventory the number
    * of times they occur, enclosed in square brackets ("[]").
    * @return Returns a string representation of the contents of this LetterInventory
    */
   public String toString() {
      String stringOfLetters = new String();
      char charIndex = 'a';     
      for(int num: this.letterCounts) {
         if (num != 0) {
            for (int i = 0; i < num; i++) {
               stringOfLetters += Character.toString(charIndex);
            }
         }
         else {
         }
         charIndex++;
      }     
      return("[" + stringOfLetters + "]");
   }

   /**
    * Sets the number of occurrences of a letter in this LetterInventory to an integer.
    * Throws IllegalArgumentException if either @param letter is a non-alphabetical character,
    * or if @param value is a negative integer.
    * @param letter The character/letter to set occurrences of
    * @param value The new occurrences of the letter
    */
   public void set(char letter, int value) {
      if (!Character.isLetter(letter)) {
         throw new IllegalArgumentException("Must enter an alphabetic character");
      }
      if (value<0) {
         throw new IllegalArgumentException("Must enter a positive value");
      }   
      
      letter = Character.toLowerCase(letter);
      numOfLetters += (value-letterCounts[letter - 'a']);
      letterCounts[letter - 'a'] = value; // This math operation replaces the integer value located
                                          // at the index of the letter argument with the user's 'value' argument
   } 
}