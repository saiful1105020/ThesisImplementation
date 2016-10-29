/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thesis;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Black_Knight
 */
public class SetOperations {
    public static Set<Integer> union(Set<Integer> a, Set<Integer> b)
    {
        Set<Integer> c = new TreeSet<Integer>(a);
        c.addAll(b);
        return c;
    }
    
    public static Set<Integer> intersection(Set<Integer> a, Set<Integer> b)
    {
        Set<Integer> c = new TreeSet<Integer>(a);
        c.retainAll(b);
        return c;
    }
    
    public static Set<Integer> minus(Set<Integer> a, Set<Integer> b)
    {
        Set<Integer> c = new TreeSet<Integer>(a);
        c.removeAll(b);
        return c;
    }
}
