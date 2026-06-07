import java.util.*;

class Edge {
    int destination;
    int weight;

    Edge(int destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }
}

public class EmergencyResponseSystem {

    private int vertices;
    private LinkedList<Edge>[] adjacencyList;

    EmergencyResponseSystem(int vertices) {
        this.vertices = vertices;
        adjacencyList = new LinkedList[vertices];

        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }

    void addRoad(int source, int destination, int distance) {
        adjacencyList[source].add(new Edge(destination, distance));
        adjacencyList[destination].add(new Edge(source, distance));
    }

    void findShortestPath(int source) {

        int[] distance = new int[vertices];
        boolean[] visited = new boolean[vertices];

        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        PriorityQueue<Integer> pq =
                new PriorityQueue<>(Comparator.comparingInt(a -> distance[a]));

        pq.add(source);

        while (!pq.isEmpty()) {

            int current = pq.poll();

            if (visited[current])
                continue;

            visited[current] = true;

            for (Edge edge : adjacencyList[current]) {

                int neighbor = edge.destination;
                int weight = edge.weight;

                if (!visited[neighbor] &&
                        distance[current] + weight < distance[neighbor]) {

                    distance[neighbor] = distance[current] + weight;
                    pq.add(neighbor);
                }
            }
        }

        System.out.println("Shortest Distance from Emergency Location:");

        for (int i = 0; i < vertices; i++) {
            System.out.println("Location " + i +
                    " -> Distance = " + distance[i]);
        }
    }

    public static void main(String[] args) {

        EmergencyResponseSystem ers =
                new EmergencyResponseSystem(6);

        // Roads between locations
        ers.addRoad(0, 1, 4);
        ers.addRoad(0, 2, 2);
        ers.addRoad(1, 2, 1);
        ers.addRoad(1, 3, 5);
        ers.addRoad(2, 3, 8);
        ers.addRoad(2, 4, 10);
        ers.addRoad(3, 4, 2);
        ers.addRoad(3, 5, 6);
        ers.addRoad(4, 5, 3);

        System.out.println("Emergency Response System");
        System.out.println("-------------------------");

        int emergencyLocation = 0;

        ers.findShortestPath(emergencyLocation);

        System.out.println();
        System.out.println("Hospital Locations:");
        System.out.println("Location 4 -> City Hospital");
        System.out.println("Location 5 -> Emergency Medical Center");

        System.out.println();
        System.out.println("Nearest Hospital can be identified");
        System.out.println("using the shortest distance values.");
    }
}