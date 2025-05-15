import java.util.*;

public class BankersAlgorithm {
    public BankersAlgorithm(int processes, int resources)
    {}

    public static int[][] needCalculate(int[][] allo, int[][] maxN) {
        int[][] need = new int[allo.length][allo[0].length];
        for (int i = 0; i < allo.length; i++) {
            for (int j = 0; j < allo[i].length; j++) {
                need[i][j] = maxN[i][j] - allo[i][j];
            }
        }
        return need;
    }

    public static int[] workCalculate(int[][] allo, int[] available) {
        int[] work = Arrays.copyOf(available, available.length);
        for (int[] process : allo) {
            for (int j = 0; j < process.length; j++) {
                work[j] -= process[j];
            }
        }
        return work;
    }

    public static boolean workChecker(int[] work) {
        for (int value : work) {
            if (value < 0) {
                return true;
            }
        }
        return false;
    }
    public static void printArray(int[] arr)
 {
       
            for (int value : arr) {
                System.out.print(value + " ");
            }
            System.out.println();
        
    }
    public static void printNeed(int[][] need) {
    System.out.println("The need calculated:");
     int i = 0;
     int resources = 3;
     System.out.print("\t\t");
     for (int a = 65; a < (resources + 65); a++ )
     {
     char ch = (char) a;
     System.out.print( ch + " ");}
     System.out.println();
        for (int[] row : need) {
        System.out.print("P" + i + " |\t");
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
            i++;
        }
    }

    public static String state(int[][] allocation, int[] available, int[][] maximum) {
        List<Integer> sequence = new ArrayList<>();
        int[] work = workCalculate(allocation, available);

        if (workChecker(work)) {
            return "Impossible state";
        }

        int[][] need = needCalculate(allocation, maximum);
        printNeed(need);

        boolean[] finished = new boolean[allocation.length];

        while (true) {
            boolean flag = true;

            for (int i = 0; i < need.length; i++) {
                if (finished[i]) continue;

                boolean canRun = true;
                for (int j = 0; j < need[i].length; j++) {
                    if (need[i][j] > work[j]) {
                        canRun = false;
                        break;
                    }
                }

                if (canRun) {
                    sequence.add(i);
                    System.out.println("Current Work + Allocation " + i + ":");
                   int resources = 3;
                   for (int a = 65; a < (resources + 65); a++ )
                     {
                  char ch = (char) a;
                       System.out.print( ch + " ");}
                     System.out.println();
                    printArray(work);
                    System.out.println("+");
                    printArray(allocation[i]);
                    for (int j = 0; j < work.length; j++) {
                        work[j] += allocation[i][j];
                    }
                    for (int b = 0; b < resources; b++){
                    System.out.print("__");}
                    System.out.println();
                    printArray(work);
                    finished[i] = true;
                    flag = false;
                }
            }

            if (flag) break;
        }

        for (boolean f : finished) {
            if (!f) {
            System.out.println("Sequence made it to: " + sequence);
             return "Unsafe state";}
        }

        System.out.println("Safe sequence: " + sequence);
        return "Safe state";
    }

    public static void main(String[] args) {
        int numProcess = 4;
        int numResources = 3;//maximum 26 char[90 - 65]
        int[][] allocation = {
            {2, 0, 1}, // P0
            {1, 0, 1}, // P1
            {1, 1, 0}, // P2
            {0, 1, 0}, // P3
    
        };

        int[][] maximum = {
            {2, 1, 1},
            {3, 0, 2},
            {5, 2, 1},
            {1, 1, 1},
  
        };

        int[] available = {5, 2, 3};

        System.out.println("Banker's Algorithm Safety Check:");
        String result = state(allocation, available, maximum);
        System.out.println("Result: " + result);
    }
}
