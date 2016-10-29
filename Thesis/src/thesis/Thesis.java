/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thesis;
import java.util.ArrayList;
import java.util.Scanner;
import thesis.Constants;
/**
 *
 * @author Black_Knight
 */
public class Thesis {

    public static String inputDirectory = "../ThesisPrecompute/inputData";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        PrecomputationLoader PL = new PrecomputationLoader(inputDirectory);
        //PL.printInvertedList();
        
        
        //input
        Scanner scanner = new Scanner(System.in);
        
        int queryType = Constants.OR_QUERY;
        int n;
        System.out.println("Number of keywords: ");
        
        n = scanner.nextInt();
        
        System.out.println("Enter keywords: ");
        
        String[] keywords = new String[n];
        
        int j=0;
        for(int i=0;i<n;i++)
        {
            String k = scanner.next().toLowerCase();
            if(PL.IL.containsKey(k)) keywords[j++] = k;
        }
        
        String filteredKeys[] = new String[j];
        for(int i=0;i<j;i++)
        {
            filteredKeys[i] = keywords[i];
        }
        
        System.out.println("Top ? Communities: ");
        int r = scanner.nextInt();
        
        long prevTime = System.currentTimeMillis();
        BasicAlgo BA = new BasicAlgo(PL,queryType,filteredKeys,r);
        long lastTime = System.currentTimeMillis();
        
        System.out.println("\n\n\nTotal Runtime: "+(lastTime-prevTime)+" milli seconds\n");
        
    }
    
    
}
