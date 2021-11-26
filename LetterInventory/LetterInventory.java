//Ravi Spigner
//10/2/17
//CSE143
//TA: Alina Liokumovich
//Homework 1
//
//This Object called LetterInventory stores information about a piece of text, specifically how many of each letter of the alphabet appear in the text(like an inventory of letters).
//LetterInventory also allows a user to search for information via the inventory of letters (# of occurences of a letter, if the inventory of letters is empty, how many letters
//are in the inventory) as well as manipulate the inventory of letters (adding letters, removing letters, converting the inventory of letters to text).
//LetterInventory also includes the functionality of adding 2 inventories or subtracting 2 inventories. 
public class LetterInventory {
   private int[] letterCounts; //array of integers describing how many times each letter of the alphabet appears in a word (The Inventory)
   private int numOfLetters; //the number of letters (excluding non-alphabetic charcters) in the String
   public static final int ALPHABET_LENGTH = 26; //A class constant representing 26 letters in the alphabet
   //pre:  The argument passed in is a piece of text
   //post: An array of integers is created of size 26, each index representing a different letter of the alphabet (in alphabetical order).
   //      The number of index corresponds to an alphanumeric letter appears in the string 
   public LetterInventory(String data) {
      letterCounts = new int[ALPHABET_LENGTH];
      data = data.toLowerCase();
      for (int i = 0; i < data.length(); i++) { //go through each element of the string
         char ch = data.charAt(i); //turn the element of the string into a char
         if (Character.isLetter(ch)) {  //if that char is a letter
            numOfLetters++;
            letterCounts[ch - 'a']+= 1; //increment letterCounts for that letter
         }
         else {
         }
      } 
   }
   //pre:  The 'other' argument is a LetterInventory object
   //post: Returns the sum of this letter inventory and the other inventory passed in as the 'other' argument
   public LetterInventory add(LetterInventory other) {
      LetterInventory sumInventory = new LetterInventory("");
      for(int i = 0; i < 26; i++) {
         sumInventory.letterCounts[i] = (other.letterCounts[i]+this.letterCounts[i]);
         sumInventory.numOfLetters += (sumInventory.letterCounts[i]);
      }  
      return sumInventory;
   }
   //pre:  The 'other' argument is a LetterInventory object
   //post: Returns the difference of this letter inventory and other inventory passed in as the 'other' argument 
   //      unless a difference calculated is negative (returns null) 
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
   //pre:  The size of the inventory is the same as the number of letters
   //post: The number of letters in the inventory is returned.
   public int size(){
      return numOfLetters;
   }
   //pre:  The number of letters in the array is greater than or equal to zero (also not a null value)
   //post: Returns true if the inventory contains no letters
   public boolean isEmpty() {
      return numOfLetters == 0; 
   }
   //pre:  The letter argument is an alphabetical character (IllegalArgumentException is thrown if not)
   //post: Returns the number of how many of the 'letter' argument are in the inventory
   public int get(char letter) {
      if (!Character.isLetter(letter)) {
         throw new IllegalArgumentException("Must enter an alphabetic charcter");
      }
      letter = Character.toLowerCase(letter);
      return letterCounts[letter - 'a']; //This math operation finds the value located at the index of the letter argument
   }
   //pre:  This method is called on a collection of integers
   //post: Returns a string of text containing the letters in the inventory the number of times the letters are present, surrounded by brackets '[]'
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
   //pre:  The letter argument is an alphabetical character (IllegalArgumentException is thrown if not)
   //      The value argument is a non-negative value (IllegalArgumentException is thrown if not)
   //post: Sets the number of a letter in the inventory to the user's integer argument
   public void set(char letter, int value) {
      if (!Character.isLetter(letter)) {
         throw new IllegalArgumentException("Must enter an alphabetic charcter");
      }
      if (value<0) {
         throw new IllegalArgumentException("Must enter a positive value");
      }   
      
      letter = Character.toLowerCase(letter);
      numOfLetters += (value-letterCounts[letter - 'a']);
      letterCounts[letter - 'a'] = value; //This math operation replaces the integer value located 
                                          //at the index of the letter argument with the user's 'value' argument
   } 
}