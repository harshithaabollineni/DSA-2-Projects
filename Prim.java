import java.util.*;

public class SmartGrid {

    // -------- Segment Tree --------
    static class SegmentTree {
        int[] tree; int n;

        SegmentTree(int[] arr) {
            n = arr.length;
            tree = new int[4*n];
            build(arr,0,0,n-1);
        }

        void build(int[] arr,int node,int l,int r){
            if(l==r) tree[node]=arr[l];
            else{
                int m=(l+r)/2;
                build(arr,2*node+1,l,m);
                build(arr,2*node+2,m+1,r);
                tree[node]=tree[2*node+1]+tree[2*node+2];
            }
        }

        int query(int node,int l,int r,int ql,int qr){
            if(qr<l || r<ql) return 0;
            if(ql<=l && r<=qr) return tree[node];
            int m=(l+r)/2;
            return query(2*node+1,l,m,ql,qr)
                 + query(2*node+2,m+1,r,ql,qr);
        }
    }

    // -------- Fenwick Tree --------
    static class Fenwick {
        int[] bit; int n;
        Fenwick(int n){ this.n=n; bit=new int[n+1]; }

        void update(int i,int val){
            for(i++; i<=n; i+=i&-i) bit[i]+=val;
        }

        int sum(int i){
            int s=0;
            for(i++; i>0; i-=i&-i) s+=bit[i];
            return s;
        }
    }

    // -------- Graph BFS & DFS --------
    static class Graph {
        int V; List<List<Integer>> adj;

        Graph(int V){
            this.V=V;
            adj=new ArrayList<>();
            for(int i=0;i<V;i++) adj.add(new ArrayList<>());
        }

        void addEdge(int u,int v){
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        void BFS(int s){
            boolean[] vis=new boolean[V];
            Queue<Integer> q=new LinkedList<>();
            vis[s]=true; q.add(s);
            System.out.print("BFS: ");
            while(!q.isEmpty()){
                int x=q.poll();
                System.out.print(x+" ");
                for(int n:adj.get(x))
                    if(!vis[n]){ vis[n]=true; q.add(n); }
            }
            System.out.println();
        }

        void DFS(int s){
            boolean[] vis=new boolean[V];
            System.out.print("DFS: ");
            dfsUtil(s,vis);
            System.out.println();
        }

        void dfsUtil(int v,boolean[] vis){
            vis[v]=true;
            System.out.print(v+" ");
            for(int n:adj.get(v))
                if(!vis[n]) dfsUtil(n,vis);
        }
    }

    // -------- Prim MST --------
    static void prim(int[][] g){
        int V=g.length;
        int[] key=new int[V];
        boolean[] mst=new boolean[V];
        int[] parent=new int[V];

        Arrays.fill(key,Integer.MAX_VALUE);
        key[0]=0; parent[0]=-1;

        for(int i=0;i<V-1;i++){
            int u=-1,min=Integer.MAX_VALUE;
            for(int v=0;v<V;v++)
                if(!mst[v] && key[v]<min){ min=key[v]; u=v; }

            mst[u]=true;

            for(int v=0;v<V;v++)
                if(g[u][v]!=0 && !mst[v] && g[u][v]<key[v]){
                    key[v]=g[u][v];
                    parent[v]=u;
                }
        }

        int cost=0;
        System.out.println("Prim MST:");
        for(int i=1;i<V;i++){
            System.out.println(parent[i]+"-"+i+" : "+key[i]);
            cost+=key[i];
        }
        System.out.println("Cost: "+cost);
    }

    // -------- MAIN --------
    public static void main(String[] args){

        int[] power={10,20,30,40,50};

        SegmentTree st=new SegmentTree(power);
        System.out.println("Segment Query(1-3): "+
                st.query(0,0,power.length-1,1,3));

        Fenwick ft=new Fenwick(power.length);
        for(int i=0;i<power.length;i++)
            ft.update(i,power[i]);
        System.out.println("Fenwick Sum(3): "+ft.sum(3));

        Graph g=new Graph(5);
        g.addEdge(0,1); g.addEdge(0,2);
        g.addEdge(1,3); g.addEdge(2,4);

        g.BFS(0);
        g.DFS(0);

        int[][] grid={
            {0,2,0,6},
            {2,0,3,8},
            {0,3,0,0},
            {6,8,0,0}
        };

        prim(grid);
    }
}