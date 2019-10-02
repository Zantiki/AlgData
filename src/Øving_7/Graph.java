package Øving_7;

import Øving_4.Node;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.StringTokenizer;

import static javax.swing.JOptionPane.showInputDialog;

public class Graph {
    int totNodes = 0;
    int totEdges = 0;
    GraphNode[] nodes = null;

    public GraphNode[] getNodes(){
        return nodes;
    }
    public void bfs(GraphNode start){
        initPrevious(start);
        Quene q = new Quene(totNodes -1);
        q.addToQuene(start);
        while(!q.empty()){
            GraphNode n = (GraphNode)q.nextInQuene();
            for(Edge e = n.edge1; e != null; e = e.next){
                Previous p = (Previous)e.to.d;
                if(p.dist == p.infinity){
                    p.dist = ((Previous)n.d).dist +1;
                    p.previous = n;
                    q.addToQuene(e.to);
                }
                //System.out.println(n.nodenr +"    "+e.to.nodenr+"        "+p.dist);
            }
        }

    }

    public void bfsModified(GraphNode start, GraphNode end){
        initPrevious(start);
        boolean countNodes = true;
        Quene q = new Quene(totNodes -1);
        q.addToQuene(start);
        while(!q.empty()){
            GraphNode n = (GraphNode)q.nextInQuene();
            for(Edge e = n.edge1; e != null; e = e.next){
                Previous p = (Previous)e.to.d;
                if(p.dist == p.infinity){
                    p.dist = ((Previous)n.d).dist +1;
                    p.previous = n;
                    q.addToQuene(e.to);
                }
                //System.out.println(n.nodenr +"    "+e.to.nodenr+"        "+p.dist);
            }
        }

    }


    public String bfsResult(GraphNode start){
        bfs(start);
        String res = "Node Previous Dist\n";
        for(int i = 0; i < totNodes; i++){
            if(((Previous)nodes[i].d).previous == null){
                res += nodes[i].nodenr+"    null     "+((Previous) nodes[i].d).dist +"\n";
            }else {
                res += nodes[i].nodenr + "    " + ((Previous) nodes[i].d).previous.nodenr + "        " + ((Previous) nodes[i].d).dist + "\n";
            }
        }
        return res;
    }

    public String bfsResultModifed(GraphNode start, GraphNode end) {
        bfsModified(start,end);
        String res = "From To Intersections\n";
        for (int i = 0; i < totNodes; i++) {
            if (i == end.nodenr) {
                res += nodes[i].nodenr + "    " + ((Previous) nodes[i].d).previous.nodenr + "        " + ((Previous) nodes[i].d).dist + "\n";
               // res += start.nodenr + "    " + end.nodenr+ "        " + (int) start.d + "\n";
            }
        }
        return res;
    }


    public GraphNode dfTopo(GraphNode n, GraphNode nl){
        TopoList nd = (TopoList)n.d;
        if(nd.found) return nl;
        nd.found = true;
        for(Edge e = n.edge1;  e != null; e = e.next){
            nl = dfTopo(e.to, nl);
        }
        nd.next = nl;
        return n;
    }

    public GraphNode topologicSort(){
        GraphNode nl = null;
        for(int i = totNodes; i -- >0; ){
            nodes[i].d = new TopoList();
        }
        for(int i = totNodes; i -- >0;){
            nl = dfTopo(nodes[i], nl);
        }
        return nl;
    }

    public void initPrevious(GraphNode node){
        for(int i = totNodes; i -- >0;) {
            nodes[i].d = new Previous();
        }
        ((Previous)node.d).dist = 0;
    }

    public void readGraphFromURL(String loc){
        try {
            URL url = new URL(loc);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringTokenizer st = new StringTokenizer(reader.readLine());
            totNodes = Integer.parseInt(st.nextToken());
            nodes = new GraphNode[totNodes];
            for(int i = 0; i < totNodes; ++i){
                nodes[i] = new GraphNode(i);
                //System.out.println("Node "+i+" added");
            }
            totEdges = Integer.parseInt(st.nextToken());
            for(int i = 0; i < totEdges; i++){
                st = new StringTokenizer(reader.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                Edge edge = new Edge(nodes[to], nodes[from].edge1);
                nodes[from].edge1 = edge;
               // System.out.println("An edge from "+from+" to "+to+" has been added");
            }
            reader.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

   /* public int getCityIndecesFromURL(String loc){
        try{
            URL url = new URL(loc);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    public String topoResult(){
        GraphNode nl = topologicSort();
        TopoList list = (TopoList) nl.d;
        GraphNode position = list.next;
        int[] result = new int[totNodes];
        result[0] = nl.nodenr;
        for(int i = 1; i < totNodes; i++){
          result[i] = position.nodenr;
          position = ((TopoList)position.d).next;
        }
        return Arrays.toString(result);
    }



    public static void main(String[] args) {
        //18058 = Moholt;
        //37774 = Kalvskinnet
        Graph test = new Graph();
        test.readGraphFromURL("http://www.iie.ntnu.no/fag/_alg/uv-graf/L7g1");
        GraphNode[] nodes = test.getNodes();
        System.out.println("Resultat fra bredde først søk");
        System.out.println(test.bfsResult(nodes[5]));
        System.out.println("Topologisk søk");
        test.readGraphFromURL("http://www.iie.ntnu.no/fag/_alg/uv-graf/L7g5");
        System.out.println("\n"+test.topoResult());

        showInputDialog("Ved trykk av ok vil hele skadinavia leses inn");
        test.readGraphFromURL("http://www.iie.ntnu.no/fag/_alg/uv-graf/L7Skandinavia");
        nodes = test.getNodes();
        System.out.print(test.bfsResultModifed(nodes[65205], nodes[3378527]));
        //49 veistykker
    }
}