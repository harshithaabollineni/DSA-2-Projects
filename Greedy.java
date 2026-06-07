import java.util.*;

public class WarehouseSystem {

    // -------- Merge Sort --------
    static void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    static void merge(int[] arr, int l, int m, int r) {
        int[] left = Arrays.copyOfRange(arr, l, m + 1);
        int[] right = Arrays.copyOfRange(arr, m + 1, r + 1);

        int i = 0, j = 0, k = l;

        while (i < left.length && j < right.length)
            arr[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];

        while (i < left.length) arr[k++] = left[i++];
        while (j < right.length) arr[k++] = right[j++];
    }

    // -------- Greedy (Priority Processing) --------
    static void greedy(int[] priority) {
        Arrays.sort(priority);
        System.out.print("Greedy Order (High Priority First): ");
        for (int i = priority.length - 1; i >= 0; i--)
            System.out.print(priority[i] + " ");
        System.out.println();
    }

    // -------- Dynamic Programming (Min Cost Path) --------
    static int dp(int[][] cost) {
        int m = cost.length, n = cost[0].length;
        int[][] dp = new int[m][n];

        dp[0][0] = cost[0][0];

        for (int i = 1; i < m; i++)
            dp[i][0] = dp[i - 1][0] + cost[i][0];

        for (int j = 1; j < n; j++)
            dp[0][j] = dp[0][j - 1] + cost[0][j];

        for (int i = 1; i < m; i++)
            for (int j = 1; j < n; j++)
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + cost[i][j];

        return dp[m - 1][n - 1];
    }

    // -------- MAIN --------
    public static void main(String[] args) {

        int[] orders = {5, 2, 9, 1, 7};

        System.out.println("Original Orders: " + Arrays.toString(orders));

        // Merge Sort
        mergeSort(orders, 0, orders.length - 1);
        System.out.println("Sorted Orders (Merge Sort): " + Arrays.toString(orders));

        // Greedy
        greedy(orders);

        // DP
        int[][] cost = {
            {1, 3, 5},
            {2, 1, 2},
            {4, 3, 1}
        };

        System.out.println("Minimum Processing Cost (DP): " + dp(cost));
    }
}