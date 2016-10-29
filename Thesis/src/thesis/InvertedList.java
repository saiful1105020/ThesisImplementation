/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thesis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import thesis.Constants;
/**
 *
 * @author Black_Knight
 */
public class InvertedList {

    Map<String,ArrayList<Integer> > map = new HashMap<String,ArrayList<Integer> >();
    
    public InvertedList(Map<String,ArrayList<Integer> > IL) {
        this.map = IL;
    }
    
    
    public Set<Integer> getNodes(String keyword)
    {
        ArrayList<Integer> nodes = map.get(keyword);
        
        Set<Integer> vSet = new TreeSet<Integer> (nodes);
        
        return vSet;
    }
}
