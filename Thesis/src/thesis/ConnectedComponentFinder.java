/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thesis;

import java.util.LinkedList;

/**
 *
 * @author Black_Knight
 */
public class ConnectedComponentFinder {
    private static LinkedList[] Adj;
    private static LinkedList Nodes;
    private static LinkedList Color;
    private static int White=0,Gray=1,Black=3;
    
    ConnectedComponentFinder(int[][] Graph){
        this.Nodes=new LinkedList();
        this.Adj=new LinkedList[Graph.length];
        this.Color=new LinkedList();
        for(int i=0;i<Graph.length;i++){
            this.Nodes.add(i, Graph[i][0]);
            this.Adj[i]=new LinkedList();
            for(int j=1;j<Graph[i].length;j++){
                Adj[i].add(j-1, Graph[i][j]);
            }
        }
        
        
        for(int i=0;i<Graph.length;i++){
            Color.add(i, White);
        }
    }
    
    public LinkedList GetConnectedComponents(){
        LinkedList ComponentList=new LinkedList(),n=new LinkedList();
        for(int i=0;i<Nodes.size();i++){
            n.add((int)Nodes.get(i));
        }
        int node,color,index,child,indexC;
        while(!n.isEmpty()){
            LinkedList Component=new LinkedList();
            LinkedList queue=new LinkedList();
            node=(int)n.getFirst();
            index=Nodes.indexOf(node);
            color=(int)Color.get(index);
            if(color==White){
                Color.remove(index);
                Color.add(index, Gray);
            }
            queue.add(node);
            while(!queue.isEmpty()){
                node=(int)queue.getFirst();
                index=Nodes.indexOf(node);
                for(int i=0;i<Adj[index].size();i++){
                    child=(int)Adj[index].get(i);
                    indexC=Nodes.indexOf(child);
                    if((int)Color.get(indexC)==White){
                        Color.remove(indexC);
                        Color.add(indexC, Gray);
                        queue.add(child);
                    }
                }
                n.remove(n.indexOf((int)queue.getFirst()));
                Component.add((int)queue.removeFirst());
                
            }
            ComponentList.add(Component);
        }
        
        /**
        for(int i=0;i<ComponentList.size();i++){
            LinkedList ll=(LinkedList)ComponentList.get(i);
            for(int j=0;j<ll.size();j++){
                System.out.print((int)ll.get(j)+" ");
            }
            System.out.println("\n");
        } 
        */
        
        return ComponentList;
    }
    
}
