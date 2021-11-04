public class Item extends Player {

    int weight;
    String name;

    public Item(String name, int weight){
    this.name = name;
    this.weight = weight;
    }

    public String getItemName(){
        return name;
    }

    public int getItemWeight(){
        return weight;
    }

}
