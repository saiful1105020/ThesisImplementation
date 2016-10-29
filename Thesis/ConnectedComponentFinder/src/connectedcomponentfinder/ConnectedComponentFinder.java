/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectedcomponentfinder;

/**
 *
 * @author shamik
 */
import java.util.LinkedList;

/**
 *
 * @author shamik
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
    
    public static /*LinkedList*/ void GetConnectedComponents(){
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
                //indexC=Color.indexOf(node);
                //Color.remove(indexC);
                //Color.add(indexC, Black);
                n.remove(n.indexOf((int)queue.getFirst()));
                Component.add((int)queue.removeFirst());
                
            }
            ComponentList.add(Component);
        }
        
        for(int i=0;i<ComponentList.size();i++){
            LinkedList ll=(LinkedList)ComponentList.get(i);
            for(int j=0;j<ll.size();j++){
                System.out.print((int)ll.get(j)+" ");
            }
            System.out.println("\n");
        }        
        //return ComponentList;
        
        
    }
    
    public static void main(String[] args){
        int[][] Graph=new int[5][];
        Graph[0]=new int[3];
        Graph[0][0]=1;
        Graph[0][1]=2;
        Graph[0][2]=3;
        
        Graph[1]=new int[3];
        Graph[1][0]=2;
        Graph[1][1]=3;
        Graph[1][2]=1;
        
        Graph[2]=new int[3];
        Graph[2][0]=3;
        Graph[2][1]=2;
        Graph[2][2]=1;
        
        Graph[3]=new int[2];
        Graph[3][0]=4;
        Graph[3][1]=5;
        
        Graph[4]=new int[2];
        Graph[4][0]=5;
        Graph[4][1]=5;
        
        ConnectedComponentFinder ccf=new ConnectedComponentFinder(Graph);
        GetConnectedComponents();
    }
    
}
