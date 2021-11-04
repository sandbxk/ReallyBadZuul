/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Player player = new Player();
    private Room currentRoom = player.getCurrentRoom();
    private Room entrance, kitchen, storage, hallway, office, crawlspace, hallway2, hallway3, hallway4, bedroom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        player = new Player();
        parser = new Parser();
        createRooms();
        initialiseExits();
        insertItems();
    }




    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {

        // create the rooms
        entrance = new Room("at the entrance of the basement. The door is locked.");
        kitchen = new Room("in a dirty kitchen. No one has been here for a while.");
        storage = new Room("in a dusty storage room. The air is thick. You spot a crawlspace.");
        hallway = new Room("in a long hallway. There are a lot of doors, but most are locked.");
        office = new Room("in the computing admin office. The computers are on, but no one's here. There's no internet connection.");
        crawlspace = new Room("in a crawlspace. It's a tight fit, but you just barely get through.");
        hallway2 = new Room("still in the hallway. It's eerily repetitive. All the doors a locked.");
        hallway3 = new Room("in another part of the hallway. There's a room that's missing a door.");
        hallway4 = new Room("at the end of the hallway. There is a single door in front of you.");
        bedroom = new Room("in a bedroom. There's a dirty mattress on the floor and an open beer. Someone has been here recently");

        player.setCurrentRoom(entrance);  // start game outside
    }
    private void initialiseExits(){
        // initialise room exits
        entrance.setExits("forward", hallway);

        hallway.setExits("left", kitchen);
        hallway.setExits("right", storage);
        hallway.setExits("forward", hallway2);
        hallway.setExits("back", entrance);

        kitchen.setExits("back", hallway);

        storage.setExits("down", crawlspace);


        hallway2.setExits("forward", hallway3);
        hallway2.setExits("back", hallway);

        hallway3.setExits("left", office);
        hallway3.setExits("forward", hallway4);
        hallway3.setExits("back", hallway2);
        office.setExits("back", hallway3);

        hallway4.setExits("forward", bedroom);
        hallway4.setExits("back", hallway3);
        bedroom.setExits("back", hallway4);


    }

    private void insertItems(){
        kitchen.putItem("knife", "a adull knife", 1);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocation();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are in a basement. It's chilly. You");
        System.out.println("wander aimlessly.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getAllCommands());
        System.out.println("Your directions are:");
        System.out.println(currentRoom.getExitString());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExits(direction);

        if (nextRoom == null)
            System.out.println("There is no door!");
        else {
            player.setCurrentRoom(nextRoom);
            System.out.println(currentRoom.getLongDescription());
        }

        }


    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    private void printLocation() {
        System.out.println("You are " + currentRoom.getDescription());
        System.out.println(currentRoom.getExitString());

        System.out.println();
    }

    /** Looks around the room and returns the descriptions
     *  and available direction in the room.
     */
    private void look(){
        System.out.println(currentRoom.getLongDescription());
        System.out.println("You found:" + currentRoom.showItem());
    }

}
