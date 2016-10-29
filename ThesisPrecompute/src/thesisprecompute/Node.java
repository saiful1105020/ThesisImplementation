/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thesisprecompute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import sun.security.provider.certpath.AdjacencyList;
import thesisprecompute.Constants;

/**
 *
 * @author Black_Knight
 */
public class Node {
    
    //unique id of a person
    int id;
    
    //optional
    String name="";
    
    //regions are defined by integers ranging from [0,200] -> used in graph partitioning
    int regionId;
    
    //for each keyword , keep an influence score of the person
    HashMap<String, Double> influenceScore = new HashMap<String,Double> ();
    
    double score = 0;
    
    Set<Integer> adjacencyList = new TreeSet<Integer>();
    
    //degree -> adjacenyList.size()
    
    
    public Node()
    {
        
    }
    
    public Node(Node n)
    {
        this.id = n.id;
        this.name = n.name;
        this.regionId = n.regionId;
        
        for (Map.Entry<String, Double> entry : n.influenceScore.entrySet()) {
            String keyword = entry.getKey();
            Double score = entry.getValue();
            influenceScore.put(keyword, score);
        }
        
        this.adjacencyList.addAll(n.adjacencyList);
        
    }
    
    public Node(int id,HashMap<String, Double> influenceScore)
    {
        this.id = id;
        
        //copy by reference should be ok here
        this.influenceScore = influenceScore;   
    }
    
    public Node(int id, String name, HashMap<String, Double> influenceScore)
    {
        this.id = id;
        this.name = name;
        this.influenceScore = influenceScore;
    }
    
    public Node(int id, int regionId, HashMap<String, Double> influenceScore)
    {
        this.id = id;
        this.regionId = regionId;
        this.influenceScore = influenceScore;
    }
    
    public Node(int id, String name, int regionId, HashMap<String, Double> influenceScore)
    {
        this.id = id;
        this.name = name;
        this.regionId = regionId;
        this.influenceScore = influenceScore;
    }
    
    public double getInfluenceScore(String keyword)
    {
        if(influenceScore.containsKey(keyword))
        {
            return influenceScore.get(keyword);
        }
        else return 0;
    }
    
    public double getInfluenceScore(String[] keywords)
    {
        double score = 0.0;
        
        for (int i = 0; i < keywords.length; i++) {
            String keyword = keywords[i];
            
            if(influenceScore.containsKey(keyword))
            {
                score+=influenceScore.get(keyword);
            }
        }
        
        return score/keywords.length;
    }
    
    public double getInfluenceScore(int queryType,String[] keywords)
    {
        if(queryType == Constants.OR_QUERY)
        {
            double score = 0.0;
            
            for (int i = 0; i < keywords.length; i++) {
                String keyword = keywords[i];

                if(influenceScore.containsKey(keyword))
                {
                    score=Math.max(score,influenceScore.get(keyword));
                }
            }
            return score;
        }
        else
        {
            double score = 0.0;

            for (int i = 0; i < keywords.length; i++) {
                String keyword = keywords[i];

                if(influenceScore.containsKey(keyword))
                {
                    score+=influenceScore.get(keyword);
                }
            }

            return score/keywords.length;
        }
        
    }
    
    public void addAdjacent(int nodeId)
    {
        adjacencyList.add(nodeId);
    }
    
    public boolean isAdjacent(int nodeId)
    {
        if(adjacencyList.contains(nodeId)) return true;
        else return false;
    }
    public boolean isAdjacent(Node node)
    {
        if(adjacencyList.contains(node.id)) return true;
        else return false;
    }
    
    public void print()
    {
        System.out.println("--------------------------\nNode id: "+this.id+"\nAdjacency List: ");
        
        for (Iterator<Integer> it = adjacencyList.iterator(); it.hasNext();) {
            Integer integer = it.next();
            System.out.print(integer+"\t");
        }
        
        System.out.println();
    }
    
    public boolean equals(Object n)
    {
        Node temp = (Node) n;
        if(this.id == temp.id) return true;
        else return false;
    }
    
}
