package Algorithm.Graph_Traverse;

import java.io.File;
import java.io.IOException;
import java.util.*;

class GRAPH {

    private int v;
    private int e;
    private List<List<Integer>> g;
    // Use List<List<Integer>> for Adjacency List

    GRAPH(String s) {
        try {

            File f = new File(s);
            Scanner sc = new Scanner(f);
            v = sc.nextInt();
            e = sc.nextInt();

            g = new ArrayList<>(v + 1);

            for (int i = 0; i <= v; i++) {
                g.add(new ArrayList<>());
            }

            for (int i = 0; i < e; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();

                add_edge(x, y);
            }

        }

        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void add_edge(int u, int v) {
        g.get(u).add(v);
        // g.get(v).add(u);
    }

    public void add_vertex(int n) {
        for (int i = 0; i < n; i++) {
            g.add(new ArrayList<>());
            v++;
        }
    }

    public int vertex_num() {
        return v;
    }

    public void display_vertex(int v) {
        System.out.println(g.get(v));
    }

    public void displayGraph() {
        for (int i = 0; i < v; i++) {
            System.out.print(i + "-->");

            for (Integer child : g.get(i)) {
                System.out.print(child + " ");
            }
            System.out.println();
        }
    }

    // public void print_dis(int source) {
    // System.out.println("\nDistances from source vertex " + source + ":");
    // for (int i = 1; i <= v; i++) {
    // System.out.println("Vertex " + i + " is at distance " + dist[i]);
    // }
    // }

    public int[] BFS(int source) {
        int[] visit = new int[v + 1];
        int[] dist = new int[v + 1];

        Arrays.fill(dist, -1);

        Queue<Integer> q = new LinkedList<>();

        q.add(source);
        visit[source] = 1;
        dist[source] = 0;

        while (!q.isEmpty()) {

            int curr_val = q.poll();
            System.out.println("Visit" + curr_val);

            for (int child : g.get(curr_val)) {
                if (visit[child] == 0) {
                    visit[child] = 1;
                    dist[child] = dist[curr_val] = 1;
                    q.add(child);
                }
            }
        }
        return dist;
    }

    public void DFS(int source) {
        int[] visit = new int[v + 1];
        DFS_run(source, visit);
        System.out.println();
    }

    public void DFS_run(int source, int[] visit) {
        visit[source] = 1;
        System.out.print(source + " ");

        for (int child : g.get(source)) {

            if (visit[child] == 0) {
                DFS_run(child, visit);
            }

        }
    }

    public void DFS_run_2(int source, List<Integer> ans, int[] visit) {
        visit[source] = 1;

        for (int child : g.get(source)) {

            if (visit[child] == 0) {
                DFS_run_2(child, ans, visit);
            }

        }
        ans.add(source);
    }

    public List<Integer> Topological_Sort() {
        ArrayList<Integer> ans = new ArrayList<>();
        int[] visit = new int[v + 1];

        for (int i = 0; i < v; i++) {
            if (visit[i] == 0) {
                DFS_run_2(i, ans, visit);
            }
        }
        Collections.reverse(ans);
        return ans;
    }

    public List<List<Integer>> SCC() {

        List<Integer> topological_Order = Topological_Sort();
        List<List<Integer>> gt = new ArrayList<>(v + 1);

        for (int i = 0; i <= v; i++) {
            gt.add(new ArrayList<>());
        }

        // transpose

        for (int i = 0; i < v; i++) {
            for (int child : g.get(i)) {
                gt.get(child).add(i);
            }
        }

        List<List<Integer>> ans = new ArrayList<>();
        int[] visit = new int[v + 1];
        for (int i : topological_Order) {
            if (visit[i] == 0) {
                List<Integer> comp = new ArrayList<>();
                dfs_SCC(i, visit, gt, comp);
                ans.add(comp);
            }
        }
        return ans;
    }

    public void dfs_SCC(int source, int[] visit, List<List<Integer>>gt, List<Integer>ans){
        visit[source] = 1;
        ans.add(source);

        for (int child : gt.get(source)) {
            if (visit[child] == 0) {
                dfs_SCC(source, visit, gt, ans);
            }
        }
    }

}

public class Template {
    public static void main(String args[]) {

        GRAPH graph = new GRAPH("input.txt");

        // LAB_01

        System.out.println("Graph Adjacency List:");
        // graph.displayGraph();
        // System.out.println(graph.vertex_num());
        // graph.add_vertex(3);
        // System.out.println(graph.vertex_num());
        // graph.display_vertex(3);

        // bfs

        // int[] dist = graph.BFS(1);
        // for (int i : dist) {
        // System.out.println(i);
        // }

        System.out.println("Graph Adjacency List:");

        // LAB_02
        //DFS
        System.out.println("Performing DFS starting From Vertex 5:");
        graph.DFS(5);
        graph.DFS(4);

        //Topological_Sort
        System.out.println("\n Performing Topological Sort:");
        List<Integer> TopologicalSORT = graph.Topological_Sort();
        System.out.println("Topologocal Sorting Order:" + TopologicalSORT);

        //Strongly_Connected_Components
        System.out.println("\n Strongly Connected Componenets:");
        List<List<Integer>> SCC_List = graph.SCC();

        System.out.println("Strongly Connected Componenets:");
        for (List<Integer> list : SCC_List) {
            System.out.println(list);
        }
    }
}