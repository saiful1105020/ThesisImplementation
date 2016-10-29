/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thesisprecompute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Black_Knight
 */
public class GraphPartition {
    static Graph G;
    static int K = 10; //number of clusters
    static Cluster clusters[];
    
    static String fileName = "20k_data/Phase1.txt";
    
    static Random random = new Random(System.currentTimeMillis());
    static Map<Integer,Boolean> isCentre =  new HashMap<Integer,Boolean>();
    
    public static void main(String[] args)
    {
        /*
         * Read Graph
         */
        G = new Graph();
        G.constructGraph(fileName);
        //G.printGraph();
        
        kMeansCluster();
    }
    
    public static void kMeansCluster()
    {
        clusters = new Cluster[K];
        
        
        /*
         * Randomly choose k nodes as the initial clusters
         */
        for(int i=0;i<K;i++)
        {
            int index = random.nextInt(G.V.size());
            if(isCentre.containsKey(index) && isCentre.get(index))
            {
                i--;
            }
            else
            {
                clusters[i] = new Cluster(G.V.get(index));
                isCentre.put(index, true);
            }
            
            //System.out.println(clusters[i].centre.adjacencyList.size());
        }
        
        
        
        /**
         * print centres
         */
        System.out.print("Centres: ");
        for(int i=0;i<K;i++)
        {
            System.out.print(clusters[i].centre.id+"\t");
        }
        System.out.println();
        
        
        
        /**
         * For each nodes, assign a cluster
         */
        for(int i=0;i<G.V.size();i++)
        {
            Node node = G.V.get(i);
            
            boolean isAssigned = false;
            
            for(int j=0;j<K;j++)
            {
                if(clusters[j].centre.equals(node))
                {
                    clusters[j].addNode(node);
                    isAssigned = true;
                    break;
                }
            }
            
            if(isAssigned) continue;
            
            double minDist = Double.MAX_VALUE;
            int clusterId = 0;
            
            for(int j=0;j<K;j++)
            {
                double d = distance(node, clusters[j].centre);
                
                if(d<minDist)
                {
                    minDist = d;
                    clusterId = j;
                }
            }
            
            clusters[clusterId].addNode(node);
            
        }
        
        int centresUpdated = 0;
        
        /**
         * Determine mean node of each clusters
         */
        for(int i=0;i<K;i++)
        {
            System.out.println(i+" : "+clusters[i].nodes.size());
            
            Node temp = clusters[i].findCentre();
           
            if(!temp.equals(clusters[i].centre))
            {
                centresUpdated++;
                clusters[i].updateCentre(temp);
            }
            
            clusters[i].clear();
        }
        System.out.println("Centers Updated: "+centresUpdated);
        
        /**
         * if any center of a cluster is changed, repeat
         */
    }
    
    public static double distance(Node n1,Node n2)
    {
        double d = 0;
        
        if(!n1.isAdjacent(n2))
        {
            
            int temp1 = SetOperations.intersection(n1.adjacencyList, n2.adjacencyList).size();
            int temp2 = SetOperations.union(n1.adjacencyList, n2.adjacencyList).size();
            
            d = d+ ((double)(temp2-temp1)/(double)temp2);
            
            //System.out.println(temp1);
            
        }
        //System.out.println(d);
        return d;
    }
    
}
