/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thesis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Black_Knight
 */
public class PrecomputationLoader {

    Map<String,ArrayList<Integer> > IL = new HashMap<String,ArrayList<Integer> >();
    String datasetName;
    
    public PrecomputationLoader(String datasetName) {
        this.datasetName = datasetName;
        this.loadInvertedList(datasetName, "InvertedList.txt");
    }
    
    public void loadInvertedList(String datasetName, String fileName)
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
        
        while(input.hasNextLine())
        {
            String keyword;
            int size;
            ArrayList<Integer> vertexList = new ArrayList<Integer>();
            
            if(!input.hasNext()) break;
            keyword = input.next();
            
            size = input.nextInt();
            //System.out.println(size);
            
            for(int i=0;i<size;i++)
            {
                int tmp = input.nextInt();
                vertexList.add(tmp);
            }
            
            IL.put(keyword, vertexList);
        }
    }
    
    public void printInvertedList()
    {
        for (Map.Entry<String, ArrayList<Integer>> entry : IL.entrySet()) {
            String string = entry.getKey();
            
            System.out.println("Keyword: "+string+"\n");
            
            ArrayList<Integer> arrayList = entry.getValue();
            
            for(int i=0;i<arrayList.size();i++)
            {
                System.out.print(arrayList.get(i)+" ");
            }
            System.out.println();
            
        }
    }
    
    public void loadPartitionInfo()
    {
        
    }
    
    public void loadPartitionGraphs()
    {
        
    }
    
    public void loadPartitionLists()
    {
        
    }
    
}
