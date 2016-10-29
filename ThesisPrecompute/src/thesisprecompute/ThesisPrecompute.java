/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thesisprecompute;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Black_Knight
 */
public class ThesisPrecompute {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        /**
         * READ INPUT DATA
         * PRE-COMPUTE INVERTED LIST
         * WRITE INVERTED LIST INTO A FILE
         */
        
        InvertedIndexGenerator.Generator("inputData/Phase1.txt");
        InvertedIndexGenerator.writeList("inputData/InvertedList.txt");
        
        GraphPartitionReader gp = new GraphPartitionReader("inputPartitionData/Partitions/","partitionInvertedList/");
        gp.generateKeywordBlockList();
        gp.generateKeywordPortalList();
    }
    
    
}
