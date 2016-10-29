/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thesis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author Black_Knight
 */
public class Graph {
    
    ArrayList<Node> V = new ArrayList<Node> ();
    static Map<Integer,Node> IdNodeMap = new HashMap<Integer,Node>();
    double score=0;
    int k = 0;
    
    public Graph()
    {
        
    }
    
    public Graph(Graph G)
    {
        for(int i=0;i<G.V.size();i++)
        {
            Node node = new Node(G.V.get(i));
            this.V.add(node);
        }
    }
    
    public void constructGraph(String datasetName, String fileName)
    {
        Scanner input = null;
        try {
            input = new Scanner(new File(datasetName+"/"+fileName));
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(PrecomputationLoader.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Input File Not Found");
            System.exit(-1);
        }
        
        input.nextLine();
        //System.out.println();
        
        while(input.hasNextInt())
        {
            Node node = new Node();
            
            int authorId;
            int paperCount; //not used apparently
            int adjCount;
            
            authorId = input.nextInt();
            
            node.id = authorId;
            
            paperCount = input.nextInt();
            adjCount = input.nextInt();
            
            for(int i=0;i<adjCount;i++)
            {
                int tmp = input.nextInt();
                node.adjacencyList.add(tmp);
            }
            
            int keywordCount;
            keywordCount = input.nextInt();
            
            for(int i=0;i<keywordCount;i++)
            {
                String temp = input.next(Pattern.compile("\\([^()]*\\)"));
                temp = temp.replace("(", "");
                temp = temp.replace(")", "");
                
                String t[] = new String[2];
                
                t = temp.split(",");
                
                //System.out.println("#DEBG --> "+t[0]+" ::: "+t[1]);
                
                String keyword = t[0];
                double score = sigmoid(Integer.parseInt(t[1]));
                
                node.influenceScore.put(keyword, score);
            }
            
            V.add(node);
            
            IdNodeMap.put(node.id, node);
            
        }
        
    }
    
    public double score(int k)
    {
        double s = 0;
        for(int i=0;i<V.size();i++)
        {
            Node node = V.get(i);
            s+=node.score;
        }
        
        s = s*Math.log(1.0+k);
        
        return s;
    }
    
    public double score()
    {
        return score;
    }
    
    public double sumScore()
    {
        double s = 0;
        for(int i=0;i<V.size();i++)
        {
            Node node = V.get(i);
            s+=node.score;
        }
       
        return s;
    }
    
    public int maxDegree()
    {
        int degree = 0;
        
        for(int i=0;i<V.size();i++)
        {
            Node node = V.get(i);
            int SZ = node.adjacencyList.size();
            if(SZ>degree) degree = SZ;
        }
        return degree;
    }
    
    public double sigmoid(int numPapers)
    {
        double k = 0.3;
        int x0 = 4;
        
        double score=0;
        
        score = 10.0/(1+Math.exp(-k*(numPapers-x0)));
        
        return score;
    }
    
    public void printGraph()
    {
        int size = V.size();
        Node node=null;
        
        for(int i=0;i<size;i++)
        {
            node = V.get(i);
            System.out.println("---------------");
            System.out.print("Node id: "+node.id+"\n");
            System.out.print("Adjacency List: \n");
            
            for (Iterator<Integer> it = node.adjacencyList.iterator(); it.hasNext();) {
                int nodeId = it.next();
                System.out.print(nodeId+"\t");
            }
            
            System.out.println();
            
            /**
            System.out.println(" <Keyword, Influence Score> Pairs: \n");
            for (Map.Entry<String, Double> entry : node.influenceScore.entrySet()) {
                String str = entry.getKey();
                double score = entry.getValue();
                
                System.out.print("<"+str+" , "+score+" >\t");
            }
            
            System.out.println();
            */
        }
        
    }
}
