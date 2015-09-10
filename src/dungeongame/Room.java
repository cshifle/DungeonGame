/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dungeongame;
import java.util.ArrayList;


public class Room {
    //create instance variables
    String title, desc;
    boolean beenHere;
    ArrayList exit = new ArrayList<Exit>();
    
    public Room(String title, String desc, boolean beenHere){
        
        //set values of instance variables to those in the constructors
        this.title = title;
        this.desc = desc;
        this.beenHere = beenHere;
        
    }
    
    public String getTitle(){
        return title;
    }
    public void setDesc(String d){
        desc = d;
    }
    public String describe(){
        
        /*The describe() method returns a string of text that is of appropriate 
        length (i.e., it only includes the description if the user is new to the
        room.) A Room also maintains a list of Exits, each of which is associated
        with a direction. A description of each exit will be included in the
        describe() output, as explained above.  */
        
       if(beenHere){
           return desc;
      
        }else{
           return desc; //just placeholder
       }
    }
    
    public Room leaveBy(String dir){
        
        /*The leaveBy() method will return the Room object reachable by the exit
        in that direction, if any. (Just return null if there is no exit in that
        direction.) */
        
        return null; //placeholder
    }
    
    public void addExit(Exit ex){
        exit.add(ex);
        
    }
}
