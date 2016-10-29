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
public class PortalConnectivity {
    int portalId;
    ArrayList<NextBlockInfo> nextBlocks = new ArrayList<NextBlockInfo>();

    public PortalConnectivity(int portalId,ArrayList<NextBlockInfo> nextBlocks) {
        this.portalId = portalId;
        
        for(int i=0;i<nextBlocks.size();i++)
        {
            this.nextBlocks.add(nextBlocks.get(i));
        }
        
    }
}
