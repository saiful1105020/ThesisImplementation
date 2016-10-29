/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thesisprecompute;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Black_Knight
 */
public class Block {
    int blockId;
    
    Map< String,ArrayList<Integer> > IL = new HashMap<String, ArrayList<Integer> >();   //inverted list for this block
    Set<Integer> portals = new TreeSet<Integer>();
    Set<Integer> nodes = new TreeSet<Integer>();
    Set<String> keywords = new TreeSet<String>();
    
    Graph G = new Graph();
        
    
    String inputFileName;
    String outputFileDirectory;
    
    public Block(int id, String dirName)
    {
        outputFileDirectory = dirName;
        blockId = id;
        
        inputFileName = GraphPartitionReader.blockFileNames.get(blockId);
        
        /**
         * pre-compute keyword-node inverted list
         */
        readInvertedList();
        writeList();
        
        /**
         * pre-compute connectivity table
         */
        System.out.println("Constructing Graph for Block: "+blockId+"\t File: "+inputFileName);
        G.constructGraph(inputFileName);
        
        /**
         * get all node ids for this partition
         */
        //System.out.println("Printing Nodes:");
        
        
        for(int i=0;i<G.V.size();i++)
        {
            nodes.add(G.V.get(i).id);
            //System.out.print(G.V.get(i).id+"\t");
        }
        //System.out.println();
        
        for(int i=0;i<G.V.size();i++)
        {
            Node node = G.V.get(i);
            
            if(!SetOperations.minus(node.adjacencyList, nodes).isEmpty())
            {
                portals.add(node.id);
            }
        }
        
        System.out.println("Total Nodes: "+nodes.size()+"\t Portals: "+portals.size());
        
        writePortalList();
        writeNodeList();
        writeKeywordList();
        
        /**
        System.out.println("Keywords: ");
        
        for (Iterator<String> it = keywords.iterator(); it.hasNext();) {
            String str = it.next();
            
            System.out.print(str+"\t");
        }
        System.out.println();
        */
    }
    
    public void writeNodeList()
    {
        String outputFileName = "partitionNodes/blockNodeList"+blockId+".txt";
        
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(outputFileName));
            
            bw.write(nodes.size()+"\n");
            
            for (Iterator<Integer> it = nodes.iterator(); it.hasNext();) {
                Integer integer = it.next();
                bw.write(integer+" ");
            }
            bw.write("\n");
            bw.flush();
            
            bw.close();
            
        } catch (IOException ex) {
            Logger.getLogger(InvertedIndexGenerator.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
        
    }
    
    public void writeKeywordList()
    {
        String outputFileName = "partitionKeywords/blockKeywordList"+blockId+".txt";
        
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(outputFileName));
            
            bw.write(keywords.size()+"\n");
            
            for (Iterator<String> it = keywords.iterator(); it.hasNext();) {
                String str = it.next();
                bw.write(str+" ");
            }
            bw.write("\n");
            bw.flush();
            
            bw.close();
            
        } catch (IOException ex) {
            Logger.getLogger(InvertedIndexGenerator.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
        
    }
    
    public void writePortalList()
    {
        String outputFileName = "partitionPortals/blockPortalList"+blockId+".txt";
        
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(outputFileName));
            
            bw.write(portals.size()+"\n");
            
            for (Iterator<Integer> it = portals.iterator(); it.hasNext();) {
                Integer integer = it.next();
                bw.write(integer+" ");
            }
            bw.write("\n");
            bw.flush();
            
            bw.close();
            
        } catch (IOException ex) {
            Logger.getLogger(InvertedIndexGenerator.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
        
    }
    
    /**
     * Read the block, store inverted index into map IL
     * Also, update the keyword list
     */
    public void readInvertedList()
    {
        Scanner input = null;
        try {
            input = new Scanner(new File(inputFileName));
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(InvertedIndexGenerator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Input File Not Found");
            System.exit(-1);
        }
        
        input.nextLine();
        //read author id and keywords
        
        
        while(input.hasNextInt())
        {
            int authorId;
            authorId = input.nextInt();
            
            int paperCount = input.nextInt();
            int adjacentCount = input.nextInt();
            
            for(int i=0;i<adjacentCount;i++)
            {
                input.nextInt();
            }
            
            int keywordCount = input.nextInt();
            
            for(int i=0;i<keywordCount;i++)
            {
                String temp = input.next();
                temp = temp.replace("(", "");
                temp = temp.replace(")", "");
                
                String t[] = new String[2];
                
                t = temp.split(",");
                
                keywords.add(t[0]);
                
                if(IL.containsKey(t[0]))
                {
                    ArrayList<Integer> list = IL.get(t[0]);
                    if(!list.contains(authorId))
                    {
                        list.add(authorId);
                        IL.put(t[0], list);
                    }
                    
                }
                else
                {
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(authorId);
                    IL.put(t[0], list);
                }
                
            }
        }
    }
    
    /**
     * write back the map IL to a file and put the output filename into GraphPartition.outputFileName
     */
    public void writeList()
    {
        String outputFileName = outputFileDirectory+"blockInvertedList"+blockId+".txt";
        
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(outputFileName));
            
            bw.write("<Keyword> <No of vertices> <vertex1> <vertex2> .... <vertexN> \n");
            
            for (Map.Entry<String, ArrayList<Integer>> entry : IL.entrySet()) {
                String keyword = entry.getKey();
                ArrayList<Integer> vertices = entry.getValue();
                
                int size = vertices.size();
                
                String ws = keyword + " "+ size +" ";
                
                for(int i=0;i<size;i++)
                {
                    ws+=vertices.get(i)+" ";
                }
                ws+="\n";
                bw.write(ws);
            }
            bw.flush();
            bw.close();
            
        } catch (IOException ex) {
            Logger.getLogger(InvertedIndexGenerator.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
        
        GraphPartitionReader.blockILFileNames.put(blockId, outputFileName);
    }
}
