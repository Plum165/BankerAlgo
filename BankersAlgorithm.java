import java.util.*;
import java.util.Scanner;
//Moegamat Samsodien
//20 April 2025
/**
 * BankersAlgorithm class implements the Banker's Algorithm
 * to check for safe states in resource allocation among processes.
 */
public class BankersAlgorithm {

    // Constructor - no special initialization needed
    public BankersAlgorithm() {}

    /**
     * Calculate the 'need' matrix: maxNeed - allocation
     * parameter allo current allocation matrix
     * parameter maxN maximum demand matrix
     * return need matrix showing remaining resources each process needs
     */
    public int[][] needCalculate(int[][] allo, int[][] maxN) {
        int[][] need = new int[allo.length][allo[0].length];
        for (int i = 0; i < allo.length; i++) {
            for (int j = 0; j < allo[i].length; j++) {
                need[i][j] = maxN[i][j] - allo[i][j];
            }
        }
        return need;
    }

    /**
     * Calculate the 'work' vector = available - sum of allocations
     * This represents resources currently available after accounting for allocations.
     * return work vector representing free resources
     */
    public int[] workCalculate(int[][] allo, int[] available) {
        int[] work = Arrays.copyOf(available, available.length);
        
        // Subtract all allocated resources from available
        for (int[] process : allo) {
            for (int j = 0; j < process.length; j++) {
                work[j] -= process[j];
            }
        }
        
        // Display resource types (A, B, C...)
        System.out.println("The work/available resources:");
        for (int a = 65; a < (work.length + 65); a++) {
            char ch = (char) a;
            System.out.print(ch + " ");
        }
        System.out.println();
        
        printArray(work);  // Show current work vector
        
        return work;
    }

    /**
     * Check if any resource count in work is negative,
     * indicating overallocation or invalid state.
     * return true if any resource is negative, else false
     */
    public boolean workChecker(int[] work) {
        for (int value : work) {
            if (value < 0) {
                return true;  // Negative resource means impossible state
            }
        }
        return false;
    }

    // Utility to print a 1D array of integers on one line
    public void printArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    /**
     * Print the need matrix with process labels and resource headings.
     */
    public void printNeed(int[][] need) {
        System.out.println("The need calculated:");
        int i = 0;
        System.out.print("\t\t");
        for (int a = 65; a < (need[0].length + 65); a++) {
            char ch = (char) a;
            System.out.print(ch + " ");
        }
        System.out.println();
        
        // Print each process and its resource needs
        for (int[] row : need) {
            System.out.print("P" + i + " |\t");
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
            i++;
        }
        System.out.println("_______________________________");
    }

    /**
     * Main method to check system state safety using Banker's Algorithm.
     * Prints progress and returns whether system is in a safe or unsafe state.
     *  allocation current allocation matrix
     *  available current available resources vector
     *  maximum maximum demand matrix
     * returns "Safe state", "Unsafe state", or "Impossible state"
     */
    public String state(int[][] allocation, int[] available, int[][] maximum) {
        List<Integer> sequence = new ArrayList<>();
        
        // Calculate initial work vector (available - allocations)
        int[] work = workCalculate(allocation, available);

        // If work has negative values, system is impossible state
        if (workChecker(work)) {
            return "Impossible state";
        }

        // Calculate need matrix
        int[][] need = needCalculate(allocation, maximum);
        printNeed(need);

        boolean[] finished = new boolean[allocation.length];  // Track finished processes

        while (true) {
            boolean flag = true;  // Flag to detect if no progress this iteration

            // Try to find a process that can finish with current work resources
            for (int i = 0; i < need.length; i++) {
                if (finished[i]) continue;

                boolean canRun = true;
                for (int j = 0; j < need[i].length; j++) {
                    if (need[i][j] > work[j]) {
                        canRun = false;
                        break;  // Not enough resources for this process
                    }
                }

                if (canRun) {
                    sequence.add(i);  // Add to safe sequence
                    System.out.println("Current Work + Allocation " + i + ":");
                    
                    // Print resource headings again
                    for (int a = 65; a < (work.length + 65); a++) {
                        char ch = (char) a;
                        System.out.print(ch + " ");
                    }
                    System.out.println();

                    printArray(work);
                    System.out.println("+");
                    printArray(allocation[i]);

                    // Release resources after process finishes
                    for (int j = 0; j < work.length; j++) {
                        work[j] += allocation[i][j];
                    }
                    System.out.println("________________");
                    printArray(work);

                    finished[i] = true;  // Mark process finished
                    flag = false;  // Made progress this loop
                }
            }

            if (flag) break;  // No progress = stop looping
        }
        
        System.out.println("_______________________________");

        // Check if all processes finished
        for (boolean f : finished) {
            if (!f) {
                System.out.println("Sequence made it to: " + sequence);
                return "Unsafe state";
            }
        }

        System.out.println("Safe sequence: " + sequence);
        return "Safe state";
    }
}
