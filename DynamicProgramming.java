import java.util.*;

public class AirlineScheduling {

    // -------- Greedy (Activity Selection) --------
    static void greedy(int[][] flights) {
        Arrays.sort(flights, Comparator.comparingInt(a -> a[1]));

        System.out.print("Selected Flights (Greedy): ");
        int count = 1;
        int lastEnd = flights[0][1];
        System.out.print(Arrays.toString(flights[0]) + " ");

        for (int i = 1; i < flights.length; i++) {
            if (flights[i][0] >= lastEnd) {
                System.out.print(Arrays.toString(flights[i]) + " ");
                lastEnd = flights[i][1];
                count++;
            }
        }
        System.out.println("\nTotal Flights: " + count);
    }

    // -------- DP (Max Non-Overlapping Flights) --------
    static int dp(int[][] flights) {
        int n = flights.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        Arrays.sort(flights, Comparator.comparingInt(a -> a[0]));

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (flights[i][0] >= flights[j][1])
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }

        int max = 0;
        for (int val : dp) max = Math.max(max, val);
        return max;
    }

    // -------- MAIN --------
    public static void main(String[] args) {

        int[][] flights = {
            {1, 3},
            {2, 5},
            {4, 6},
            {6, 7},
            {5, 8},
            {8, 9}
        };

        System.out.println("Flight Schedules:");
        for (int[] f : flights)
            System.out.println(Arrays.toString(f));

        greedy(flights);

        System.out.println("Max Flights using DP: " + dp(flights));
    }
}