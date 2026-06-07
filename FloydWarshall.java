import java.util.*;

public class RideSharing {

    static final int INF = 9999;

    // -------- Dijkstra --------
    static void dijkstra(int[][] g, int src) {
        int V = g.length;
        int[] dist = new int[V];
        boolean[] vis = new boolean[V];

        Arrays.fill(dist, INF);
        dist[src] = 0;

        for (int i = 0; i < V - 1; i++) {
            int u = -1, min = INF;

            for (int v = 0; v < V; v++)
                if (!vis[v] && dist[v] < min) {
                    min = dist[v];
                    u = v;
                }

            vis[u] = true;

            for (int v = 0; v < V; v++)
                if (!vis[v] && g[u][v] != 0 &&
                        dist[u] + g[u][v] < dist[v]) {
                    dist[v] = dist[u] + g[u][v];
                }
        }

        System.out.println("Dijkstra (from 0): " + Arrays.toString(dist));
    }

    // -------- Bellman-Ford --------
    static void bellmanFord(int V, int[][] edges) {
        int[] dist = new int[V];
        Arrays.fill(dist, INF);
        dist[0] = 0;

        for (int i = 0; i < V - 1; i++) {
            for (int[] e : edges) {
                int u = e[0], v = e[1], w = e[2];
                if (dist[u] != INF && dist[u] + w < dist[v])
                    dist[v] = dist[u] + w;
            }
        }

        System.out.println("Bellman-Ford: " + Arrays.toString(dist));
    }

    // -------- Floyd-Warshall --------
    static void floydWarshall(int[][] g) {
        int V = g.length;
        int[][] dist = new int[V][V];

        for (int i = 0; i < V; i++)
            dist[i] = g[i].clone();

        for (int k = 0; k < V; k++)
            for (int i = 0; i < V; i++)
                for (int j = 0; j < V; j++)
                    if (dist[i][k] + dist[k][j] < dist[i][j])
                        dist[i][j] = dist[i][k] + dist[k][j];

        System.out.println("Floyd-Warshall:");
        for (int[] row : dist)
            System.out.println(Arrays.toString(row));
    }

    // -------- MAIN --------
    public static void main(String[] args) {

        int[][] graph = {
            {0, 5, 0, 10},
            {0, 0, 3, 0},
            {0, 0, 0, 1},
            {0, 0, 0, 0}
        };

        int[][] edges = {
            {0,1,5},{1,2,3},{2,3,1},{0,3,10}
        };

        dijkstra(graph, 0);
        bellmanFord(4, edges);
        floydWarshall(new int[][]{
            {0,5,INF,10},
            {INF,0,3,INF},
            {INF,INF,0,1},
            {INF,INF,INF,0}
        });
    }
}