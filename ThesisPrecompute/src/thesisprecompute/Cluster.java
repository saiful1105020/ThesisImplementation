/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thesisprecompute;

import java.util.ArrayList;

/**
 *
 * @author Black_Knight
 */
public class Cluster {
    Node centre;
    ArrayList<Node> nodes = new ArrayList<Node>();

    public Cluster(Node c) {
        centre = new Node(c);
    }
    
    public void addNode(Node n)
    {
        nodes.add(new Node(n));
    }
    
    public Node findCentre()
    {
        double minSumDist = Double.MAX_VALUE;
        Node newCentre = new Node();
        
        System.out.println("Total Nodes: "+nodes.size());
        
        for(int i=0;i<nodes.size();i++)
        {
            System.out.println(i);
            
            double sumDist = 0;
            
            for(int j=0;j<nodes.size();j++)
            {
                sumDist+=GraphPartition.distance(nodes.get(i), nodes.get(j));
            }
            
            if(sumDist<minSumDist)
            {
                minSumDist = sumDist;
                newCentre = nodes.get(i);
            }
        }
        
        return newCentre;
    }
    
    public void updateCentre(Node n)
    {
        this.centre = new Node(n);
    }
    
    public void clear()
    {
        nodes.clear();
    }
    
    @Override
    public boolean equals(Object c)
    {
        Cluster temp = (Cluster) c;
        if(this.centre.equals(temp.centre)) return true;
        else return false;
    }
}
