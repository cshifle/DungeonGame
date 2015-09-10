/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dungeongame;
import java.util.HashMap;
/**
 *
 * @author Christopher
 */
public class Dungeon {
    
    //Create a hashtable.  Each key (the room name) is linked to a Room object
    HashMap<String, Room> dungeon = new HashMap<>();
    String name;
    Room entry;
    
    public Dungeon(Room entry, String name){
        this.name = name;    
        this.entry = entry;
        dungeon.put(name, entry);
        
    }
    
    public Room getEntry(){
        return entry;
       
    }
    
    public String getName(){
        return name;
    }
    
    public void add(Room room){
        dungeon.put(name, room);
    }
    
    public Room getRoom(String roomTitle){
        return dungeon.get(roomTitle);
    }
}
