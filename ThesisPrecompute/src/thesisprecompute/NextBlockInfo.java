/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thesisprecompute;

import java.util.ArrayList;

/**
 *
 * @author Black_Knight
 */
public class NextBlockInfo {
    int blockId;
    ArrayList<Integer> portalIds = new ArrayList<Integer>();

    public NextBlockInfo(int blockId,ArrayList<Integer> portalIds) {
        this.blockId = blockId;
        for(int i=0;i<portalIds.size();i++)
        {
            this.portalIds.add(portalIds.get(i));
        }
    }
    
    
    
}
