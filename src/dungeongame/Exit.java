/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dungeongame;
/**
 *
 * @author Christopher
 */
public class Exit {
    String dir;
    Room src;
    Room dest;
    
    public Exit(String dir, Room src, Room dest){
        
        //initialize instance variables
        this.dir = dir;
        this.src = src;
        this.dest = dest;
        
    }
    
    public String getDir(){
        return dir;
    }
    
    public Room getSrc(){
        return src;
    }
    
    public Room getDest(){
        return dest;
    }
    
    
}
