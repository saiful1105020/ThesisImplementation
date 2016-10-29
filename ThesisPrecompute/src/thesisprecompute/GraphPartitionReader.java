/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thesisprecompute;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Black_Knight
 */
public class GraphPartitionReader {
    
    static int totalBlocks;
   
    static ArrayList<Block> blocks = new ArrayList<Block>();
    static Set<String> allKeywords = new TreeSet<String>();
    
    static Map<Integer,String> blockFileNames = new HashMap<Integer,String>();                  //index: block_id, val: file_name where the block will be stored
    static Map<Integer,String> blockILFileNames = new HashMap<Integer,String>();                //key: blockId, val: fileName where inverted list is stored
    
    static Map< String,ArrayList<Integer> > KB = new HashMap<String, ArrayList<Integer> >();    //keyword-blocks inverted list
    static String keywordBlockListFileName = "inputPartitionData/keywordBlockList.txt";
    
    static Map< String,ArrayList<Integer> > KP = new HashMap<String, ArrayList<Integer> >();    //keyword-portals inverted list
    static String keywordPortalListFileName = "inputPartitionData/keywordPortalList.txt";
    
    String outputDirectoryName;
    
    public GraphPartitionReader(String inputDirectoryName,String outputDirectoryName) {
        this.outputDirectoryName = outputDirectoryName;
        File folder = new File(inputDirectoryName);
        File[] listOfFiles = folder.listFiles();

        int id = 0;
        
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                blockFileNames.put(id, inputDirectoryName+(listOfFiles[i].getName()));
                id++;
                
                //System.out.println(blockFileNames.get(id-1));
            } 
        }
        totalBlocks = id;
        
        readBlocks();
    }
    
    public void generateKeywordPortalList()
    {
        for (Iterator<String> it = allKeywords.iterator(); it.hasNext();) {
            String key = it.next();
            
            //portal list for keyword key
            ArrayList<Integer> KPL = new ArrayList<Integer>();
            
            ArrayList<Integer> blockIds = KB.get(key);
            for(int i=0;i<blockIds.size();i++)
            {
                int blockId = blockIds.get(i);
                Block block = blocks.get(blockId);
                
                for (Iterator<Integer> it1 = block.portals.iterator(); it1.hasNext();) {
                    Integer portalId = it1.next();
                    
                    if(KPL.contains(portalId)) continue;
                    
                    Node portal = block.G.IdNodeMap.get(portalId);
                    if(portal.influenceScore.containsKey(key))
                    {
                        KPL.add(portalId);
                    }
                    
                }
            }
            
            KP.put(key, KPL);
        }
        writeKP();
    }
    
    public void generateKeywordBlockList()
    {
        for(int i=0;i<blocks.size();i++)
        {
            Block b = blocks.get(i);
            
            for (Iterator<String> it = b.keywords.iterator(); it.hasNext();) {
                String key = it.next();
                
                if(KB.containsKey(key))
                {
                    ArrayList<Integer> temp = KB.get(key);
                    if(!temp.contains(b.blockId))
                    {
                        temp.add(b.blockId);
                    }
                    KB.put(key, temp);
                }
                else
                {
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    temp.add(b.blockId);
                    
                    KB.put(key, temp);
                }
            }
        }
        
        writeKB();
        
        
//        for (Map.Entry<String, ArrayList<Integer>> entry : BL.entrySet()) {
//            String string = entry.getKey();
//            ArrayList<Integer> temp = entry.getValue();
//            
//            System.out.println(string+" => ");
//            
//            for(int i=0;i<temp.size();i++)
//            {
//                System.out.print(temp.get(i)+" ");
//            }
//            System.out.println();
//        }
//        
//        System.out.println("All Keywords: ");
//        for (Iterator<String> it = allKeywords.iterator(); it.hasNext();) {
//            String str = it.next();
//            System.out.print(str+"\t");
//        }
//        System.out.println();
    }
    
    public void readBlocks()
    {
        File dir = new File(outputDirectoryName);
        
        for(File file: dir.listFiles())
        {
            if (!file.isDirectory()) 
                file.delete();; 
        }
        
        dir = new File("partitionPortals");
        
        for(File file: dir.listFiles())
        {
            if (!file.isDirectory()) 
                file.delete();; 
        }
        
        dir = new File("partitionNodes");
        
        for(File file: dir.listFiles())
        {
            if (!file.isDirectory()) 
                file.delete();; 
        }
        
        dir = new File("partitionKeywords");
        
        for(File file: dir.listFiles())
        {
            if (!file.isDirectory()) 
                file.delete();; 
        }
        
        
        for(int i=0;i<totalBlocks;i++)
        {
            Block b = new Block(i,outputDirectoryName);
            blocks.add(b);
            
            allKeywords = SetOperations.strUnion(allKeywords, b.keywords);
        }
    }
    
    public void partition()
    {
        
    }
    
    public void savePartitionInfo()
    {
        
    }
    
    public void writeKB()
    {
        String outputFileName = keywordBlockListFileName;
        
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(outputFileName));
            
            //number of keywords
            bw.write(KB.size()+"\n");
            
            for (Map.Entry<String, ArrayList<Integer>> entry : KB.entrySet()) {
                String string = entry.getKey();
                ArrayList<Integer> temp = entry.getValue();

                bw.write(string+"\n");
                bw.write(temp.size()+"\n");

                for(int i=0;i<temp.size();i++)
                {
                    bw.write(temp.get(i)+"\n");
                }
                
            }
            
            bw.flush();
            bw.close();
            
        } catch (IOException ex) {
            Logger.getLogger(InvertedIndexGenerator.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
        
    }
    
    public void writeKP()
    {
        String outputFileName = keywordPortalListFileName;
        
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(outputFileName));
            
            //number of keywords
            bw.write(KP.size()+"\n");
            
            for (Map.Entry<String, ArrayList<Integer>> entry : KP.entrySet()) {
                String string = entry.getKey();
                ArrayList<Integer> temp = entry.getValue();

                bw.write(string+"\n");
                bw.write(temp.size()+"\n");

                for(int i=0;i<temp.size();i++)
                {
                    bw.write(temp.get(i)+"\n");
                }
                
            }
            
            bw.flush();
            bw.close();
            
        } catch (IOException ex) {
            Logger.getLogger(InvertedIndexGenerator.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }
    
}
