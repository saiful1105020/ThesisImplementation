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
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Black_Knight
 */
public class InvertedIndexGenerator {
    static String fileName;
    static Map< String,ArrayList<Integer> > IL = new HashMap<String, ArrayList<Integer> >();
    
    public static void Generator(String fname)
    {
        fileName = fname;
        
        Scanner input = null;
        try {
            input = new Scanner(new File(fileName));
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(InvertedIndexGenerator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Input File Not Found");
            System.exit(-1);
        }
        
        System.out.println(input.nextLine());
        //read author id and keywords
        
        
        while(input.hasNextInt())
        {
            int authorId;
            authorId = input.nextInt();
            //System.out.print("Author ID: "+authorId+"\nKeywords: \n");
            
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
                //System.out.print(t[0]+"\t");
                
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
        
        System.out.println("\nNumber of keywords: "+IL.size());
        
    }
    
    public static void writeList(String fileName)
    {
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(fileName));
            
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
            
            bw.close();
            
        } catch (IOException ex) {
            Logger.getLogger(InvertedIndexGenerator.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }
}
