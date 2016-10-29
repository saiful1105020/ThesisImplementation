/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thesis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import thesis.Constants;

/**
 *
 * @author Black_Knight
 */
public class FormSubgraph {
    Graph GQ = new Graph();
    InvertedList IL;
    
    public FormSubgraph(InvertedList IL,int queryType, String[] keywords)
    {
        this.IL = IL;
        Set<Integer> VQ = getReleventNodes(this.IL,queryType,keywords);
        constructSubgraph(keywords,VQ);
        //printSubgraph();
    }
    
    public Set<Integer> getReleventNodes(InvertedList IL,int queryType, String[] keywords)
    {
        Set<Integer> VQ = new TreeSet<Integer> ();
        
        //System.out.println("Stage #1 : Finding Relevent Nodes ...");
        
        for(int i=0;i<keywords.length;i++)
        {
            Set<Integer> ILx = IL.getNodes(keywords[i]);
            
            if(queryType == Constants.AND_QUERY)
            {
                if(VQ.isEmpty())
                {
                    VQ.addAll(ILx);
                }
                else
                {
                    VQ = SetOperations.intersection(VQ, ILx);
                }
            }
            else
            {
                VQ = SetOperations.union(VQ,ILx);
            }
        }
        
        
        for (Iterator<Integer> it = VQ.iterator(); it.hasNext();) {
            Integer integer = (Integer)it.next();
            //System.out.print(integer+" ");
            
        }
        //System.out.println();
        
        return VQ;
    }
    
    public void constructSubgraph(String[] keywords,Set<Integer> VQ) {
        
        //System.out.println("Stage #2 : Constructing Subgraph GQ ...");
        
        for (Iterator<Integer> it = VQ.iterator(); it.hasNext();) {
            Integer integer = it.next();
            Node node = GQ.IdNodeMap.get(integer);
            
            node.adjacencyList = SetOperations.intersection(node.adjacencyList, new TreeSet<Integer>(VQ));
            node.score = node.getInfluenceScore(keywords);
            
            GQ.V.add(node);
        }
        
    }
    
    public void printSubgraph()
    {
        //System.out.println("Subgraph GQ\n\n");
        //System.out.println("Printing Vertices:");
        for(int i=0;i<GQ.V.size();i++)
        {
            //System.out.println("------------------");
            Node node = GQ.V.get(i);
            
            //System.out.println("Node id: "+node.id+" \t score: "+node.score+"\nAdjacent Nodes: \n");
            for (Iterator it = node.adjacencyList.iterator(); it.hasNext();) {
                int adj = (int)it.next();
                //System.out.print(adj+"\t");
            }
            //System.out.println();
        }
    }
   
}
