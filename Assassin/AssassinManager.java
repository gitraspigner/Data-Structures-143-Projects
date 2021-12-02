//Ravi Spigner
//10/15/17
//CSE143
//TA: Alina Liokumovich
//Homework 3
//AssassinManager is capable of retrieving, displaying, and manipulating information in a 
//game of Assassin. AssassinManager can display the kill ring and graveyard, find if
//an assassin is in the kill ring or graveyard, kill an assassin, and
//display the winner of the game.
import java.util.*;
public class AssassinManager {
   private AssassinNode killRing;
   private AssassinNode graveyard;
   //pre:  The 'names' parameter is a non-empty list of names. (throws IllegalArgument Exception
   //      if the size of the list is 0)
   //post: Creates the graveyard and kill ring of Assassins for the game of Assassin,
   //      using the names from the 'names' parameter.
   public AssassinManager(List<String> names) {
      int namesSize = names.size();
      if (namesSize == 0) {
         throw new IllegalArgumentException("List of names must be non-empty.");
      }
      for(int i = namesSize-1; i >= 0; i--) {
         if( i == namesSize-1) {
            AssassinNode endAssassin = new AssassinNode(names.get(i));
            killRing = endAssassin;
         }
         else {
            AssassinNode assassin = new AssassinNode(names.get(i), killRing);
            killRing = assassin;
         }
      }
   }
   
   //pre:  None
   //post: Prints the assassins currently in the kill ring and who they are stalking.
   public void printKillRing() {
      AssassinNode current = killRing;
      while(current.next != null) {
         System.out.println("    " + current.name + " is stalking " + current.next.name);
         current = current.next;
      }
      System.out.println("    " + current.name + " is stalking " + killRing.name);
   }
   
   //pre:  None
   //post: Prints the assassins currently in the graveyard and who they were killed by.
   public void printGraveyard() {
      AssassinNode current = graveyard;
      if (graveyard == null) {
      }
      else {
         while(current.next != null) {
            System.out.println("    " + current.name + " was killed by " + current.killer);
            current = current.next;
         }
         System.out.println("    " + current.name + " was killed by " + current.killer);
      }
   }
   
   //pre:  The 'name' parameter is not empty (contains characters)
   //post: Returns if the 'name' is in the Kill Ring.
   public boolean killRingContains(String name) {
      AssassinNode current = killRing;
      if(current.name.equalsIgnoreCase(name)) {
         return true;
      }
      while (current.next != null) {
         if(current.name.equalsIgnoreCase(name)) {
            return true;
         }
         current = current.next;
      }
      if(current.name.equalsIgnoreCase(name)) {
         return true;
      }
      return false;
   }
   
   //pre:  The 'name' parameter is not empty (contains characters)
   //post: Returns if the 'name' is in the Graveyard.
   public boolean graveyardContains(String name) {
      if (graveyard == null) {
         return false;
      }
      else {
         AssassinNode current = graveyard;
         if(current.name.equalsIgnoreCase(name)) {
            return true;
         }
         while (current.next != null) {
            if(current.name.equalsIgnoreCase(name)) {
               return true;
            }
            current = current.next;
         }
         if(current.name.equalsIgnoreCase(name)) {
            return true;
         }
         return false;
      }
   }
   
   //pre:  None
   //post: Returns true if the game is over (if there is one person left in the kill ring).
   public boolean gameOver() {
      return killRing.next == null;
   }
   //pre:  The Kill ring has no less than one person in it.
   //post: Returns the name of the winner of Assassin.
   public String winner() {
      if (gameOver() == true) {
         return killRing.name;
      }
      else {
         return null;
      }
   }
   
   //pre:  'name' parameter must be an assassin in the Kill Ring (throws 
   //      IllegalArgumentException if this condition is not met)
   //      Game must also still be in progress (throws IllegalStateException
   //      if this condition is not met).
   //post: Kills the assassin specified by 'name'. Assassin is removed from the Kill Ring
   //      and added to the graveyard. The Killer of the Assassin is assigned the 
   //      dead Assassin's target.
   public void kill(String name) {
      if (killRingContains(name) == false) {
         throw new IllegalArgumentException("Name is not in the Kill Ring.");
      }
      if (gameOver() == true) {
         throw new IllegalStateException("Game is already over!");
      }
      AssassinNode current = killRing;
      AssassinNode temp = graveyard;
      if(current.name.equalsIgnoreCase(name)) {
         graveyard = current;
         killRing = current.next;
         AssassinNode assassinator = killRing;
         while (assassinator.next != null) {
            assassinator = assassinator.next;
         }
         graveyard.killer = assassinator.name;
      }
      else {
         while (current != null && current.next != null) {
            if(current.next.name.equalsIgnoreCase(name)) {
                  graveyard = current.next;
                  current.next.killer = current.name;
                  current.next = current.next.next;    
            }
            current = current.next;
         }
      }
      graveyard.next = temp;
      temp = null;
   }
}