/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thesis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import sun.reflect.generics.tree.Tree;

/**
 *
 * @author Black_Knight
 */
public class BasicAlgo {
    ArrayList<Graph> A = new ArrayList<Graph>();
    String[] keywords;
        
    double minScore = 0;
    int r;
    
    public BasicAlgo(PrecomputationLoader PL,int queryType , String[] keywords,int r)
    {
        this.keywords = keywords;
        this.r = r;
        
        //System.out.println("Stage #0: Running Basic Algorithm");
        
        Graph G = new Graph();
        G.constructGraph(Thesis.inputDirectory, "Phase1.txt");
        //G.printGraph();
        
        InvertedList IL = new InvertedList(PL.IL);
        
        FormSubgraph FS = new FormSubgraph(IL, queryType, keywords);
        
        int k = 2;
        //System.out.println("Stage #3 : Extracting k-cores");
        expand(FS.GQ, k);
        
        
        System.out.print("Top "+r+" Communities: \n");
        for(int j=0;j<r;j++)
        {
            if(j>=A.size()) break;
            
            Graph g = A.get(j);
            System.out.println("------------------------------------\n------------------------------------\n"
                    + "Community Rank: "+(j+1)+"\t Score = "+g.score+"\t K = "+g.k+"\n");
            g.printGraph();
        }
    }
    
    public void expand(Graph GQ,int k)
    {
        /**
         * Line 1: Ck <- set of maximal k-cores in GQ
         */
        Graph GK = kCore(GQ, k);
        
        //System.out.println("Step #4: Finding Connected Components");
        
        int G[][] = new int[GK.V.size()][];
        
        for(int i=0;i<GK.V.size();i++)
        {
            Node node = GK.V.get(i);
            G[i] = new int[node.adjacencyList.size()+1];
            
            G[i][0] = node.id;
            
            int j=1;
            for (Iterator<Integer> it = node.adjacencyList.iterator(); it.hasNext();) {
                G[i][j] = it.next();
                j++;
            }
        }
        
        LinkedList Ck = (new ConnectedComponentFinder(G).GetConnectedComponents());
        
        for(int i=0;i<Ck.size();i++)
        {
            /**
             * Line 3: Find community c
             */
            LinkedList ll=(LinkedList)Ck.get(i);
            Graph c = new Graph();
            for(int j=0;j<ll.size();j++)
            {
                Node node = c.IdNodeMap.get((int)ll.get(j));
                node.adjacencyList = SetOperations.intersection(node.adjacencyList, new TreeSet<Integer>(ll));
                node.score = node.getInfluenceScore(keywords);
                
                c.V.add(node);
            }
            
            double score = c.score(k);
            c.score = score;
            c.k = k;
            //System.out.print("K = "+k+" \t Score = "+score+"\t");
            
            if(score>minScore)
            {
                A.add(c);
                if(A.size()>r)
                {
                   Collections.sort(A, new Comparator<Graph>() {
                        @Override
                        public int compare(Graph c1, Graph c2) {
                            return Double.compare(c2.score, c1.score);
                        }
                    });
                   
                   A.remove(r);
                   minScore = A.get(r-1).score;
                }
            }
            
            int max_k = c.maxDegree();
            
            double sumScore = c.sumScore();
            //System.out.print("Sumscore = "+sumScore+"\t"+"Max Degree: "+max_k+"\n");
            
            int next_k;
            for(next_k = k+1;next_k<=max_k;next_k++)
            {
                double sc = sumScore*Math.log(1.0+next_k);
                
                if(sc>c.score)
                {
                    //System.out.println("Expanding.. k = "+next_k);
                    expand(c, next_k);
                }
            }
        }
            
        return;
    }
    
    public Graph kCore(Graph G, int k)
    {
        Graph GK = new Graph(G);
        
        while(true)
        {
            ArrayList<Node> toDelete = new ArrayList<Node>();
            Set<Integer> excluded = new TreeSet<Integer>();

            for(int i=0;i<GK.V.size();i++)
            {
                Node node = GK.V.get(i);
                if(node.adjacencyList.size()<k)
                {
                    toDelete.add(node);
                    excluded.add(node.id);
                }
            }

            int size = toDelete.size();
            for(int i=0;i<size;i++)
            {
                GK.V.remove(toDelete.get(i));
            }

            for(int i=0;i<GK.V.size();i++)
            {
                Node node = GK.V.get(i);
                node.adjacencyList = SetOperations.minus(node.adjacencyList, excluded);
            }
            
            if(GK.V.isEmpty()) break;
            
            if(isKCore(GK, k)) break;
        }
        
        //GK.printGraph();
        
        return GK;
    }
    
    public boolean isKCore(Graph G,int k)
    {
        for(int i=0;i<G.V.size();i++)
        {
            Node node = G.V.get(i);
            if(node.adjacencyList.size()<k) return false;
        }
        
        return true;
    }
}
