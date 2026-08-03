# Dungeon analysis

Code at: https://github.com/gigamonkey/dungeon

 ## Action Interface
  * Description method that returns a String
  * Reactions method that returns a Stream of Actions from a Thing
  * Generic Action implementations
    * Attack
    * Close
    * Drop
    * Etc...

 ## Attack Interface
  * Description method that returns a String
  * Basic records that implement Attack
    * Simple - only applies Attack to the Target
    * Useless - does nothing
    * Full - applies Attack to the Target and has special effects
      (currently only used for the Ring of Great Power)
  * **Target**
    * Things that can be attacked (Player and Things)
    * Has applyAttack method for when something gets attacked

 ## Command Interface
  * Converts Parse to Action
  * Two types (both records that implement Command)
    * Turn - changes the state of the world such as moving or attacking
    * NoTurn - used for help, inventory, and quit

 ## CommandParser Record
  * Parse interface
  * Good & Bad records
  * Methods that return Parses
  * Parses can succeed and get transformed into Actions, or fail
  * Custom BadCommandException that extends Exception to help trace
   Exceptions due to Commands failing

 ## Direction Enum
  * North, South, East, West
  * Opposite direction method using ordinal method
  * Used to connect Rooms accurately and to allow the Player
   to move in a specific direction

 ## Door Record
  * From method to go from one room in the record to the other

 ## Dungeon Class
  * Loop method reads commands while the game runs
  * Main method creates Player with Maze arg and Dungeon

 ## Location Interface
  * PlacedThing record with Thing and location
  * Methods to return Collections of Things

 ## Maze Class
  * Creates all the rooms and connects them
  * Creates all the Things and places them in Rooms

 ## Player Class
  *Implements Location, Attack.Target*
  * Methods that return Actions
  * Contains a lot of Command implementation
  * Implementing Location allows the Player to place Things
   in their inventory

 ## Room Class
 *Implements Location*
 * Connect method connects a Room to another Room 
 * Can contain objects, monsters 

 ## Text Class
 * Utility class, used in various places in the code to communicate
   info to the player
 * Methods that return Strings to apply grammar
    * Articles
    * Commas
    * Capitalization
    * Etc...
 * **Wrapped Class**
    * Adds a String or a Stream of Strings to a List 
    * Returns a String with new lines to wrap text correctly 

 ## Thing Class
 *Implements Location, Attack.Target*
 * Things can be nested
 * Methods that return Strings, like verbs and describing all the Things in the Thing
 * Generic Thing child classes
    * Monster
    * Furniture
    * Weapon
    * Food

## Control Flow
 The main method in the Dungeon file creates the Player and creates the Dungeon. The loop method in the Dungeon
 controls turns & runs Player commands, which are created from methods in Player using the CommandParser. The
 CommandParser is used to return an Action, whose reactions are run when a Command is invoked by the Player.

## Opinion on Streams
 I think it was justified to use Streams because they allow for easier manipulation of collections. Using Streams,
 it's much easier to filter or map to something else to quickly extrapolate other information from the Stream.

## Questions I have
 * How does the String[] args get passed into the Player commands if there are no inputs in Dungeon?
 * Why does CommandParser take in a Player? I don't see any records being made anywhere.
 * Why did you choose to make Attack not extend Action? That would intuitively make more sense.
