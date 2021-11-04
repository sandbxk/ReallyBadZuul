import java.util.ArrayList;

public class Player {

    private ArrayList<Item> inventory;
    private Room currentRoom;

    public Player(){
    inventory = new ArrayList<Item>();
    }

    public void addItem(Item item){
    inventory.add(item);
    }

    public String getInventory(){
        String inventoryString = "Inventory:  " + inventory;

        for (Item item : inventory) {
            inventoryString += item;
        }


        return inventoryString;
    }

    public void setCurrentRoom(Room room){
        currentRoom = room;
    }

    public Room getCurrentRoom(){
        return currentRoom;
    }
}
