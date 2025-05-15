import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// 15 May 2025
// Moegamat Samsodien
/*
 This application is a console-based Banker's Algorithm calculator.
 It helps check if a system state is safe by simulating resource allocation 
 and can handle manual input or file input for process/resource data.
*/
public class Banker_Algorithm_App {
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);
      BankersAlgorithm banker = new BankersAlgorithm();
   
      System.out.println("Welcome to the Banker's Algorithm Calculator");
      System.out.println("Would you like to either:\na) Manually place values\nb) Enter a file that contains the values");
      String choice = input.nextLine().trim().toLowerCase();
   
      int numProcess = 0, numResources = 0;
      int[][] allocation = null, maximum = null;
      int[] available = null;
   
      if (choice.equals("a")) {
         // Get number of processes and resources from user
         numProcess = getPositiveInt(input, "Number of processes:");
         numResources = getPositiveInt(input, "Number of resources:");
      
         allocation = createMatrix(numProcess, numResources);
         maximum = createMatrix(numProcess, numResources);
      
         System.out.println("Give the available instances (Format e.g. 1 2 3):");
         headings(numResources); // Print resource headers A B C ...
         available = getValidIntArray(input, numResources);
      
         // Get allocation matrix row by row
         System.out.println("Give the allocated instances for each process:");
         for (int i = 0; i < numProcess; i++) {
            System.out.println("Process P" + i + ":");
            headings(numResources);
            allocation[i] = getValidIntArray(input, numResources);
         }
      
         // Get maximum demand matrix row by row
         System.out.println("Give the maximum instances for each process:");
         for (int i = 0; i < numProcess; i++) {
            System.out.println("Process P" + i + ":");
            headings(numResources);
            maximum[i] = getValidIntArray(input, numResources);
         }
      
      } else if (choice.equals("b")) {
         // Load all inputs from a formatted file
         System.out.print("Enter file path: ");
         String filename = input.nextLine().trim();
      
         try {
            Object[] result = parseInput(filename);
            numProcess = (int) result[0];
            numResources = (int) result[1];
            allocation = (int[][]) result[2];
            maximum = (int[][]) result[3];
            available = (int[]) result[4];
         
            System.out.println("Data successfully loaded from file.");
         } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;  // Exit on file error
         }
      } else {
         System.out.println("Invalid option selected.");
         return;  // Exit on invalid input
      }
   
      // Run Banker's Algorithm safety check on provided data
      System.out.println("Banker's Algorithm Safety Check:");
      String result = banker.state(allocation, available, maximum);
      System.out.println("Result: " + result);
    
      // Optionally handle additional resource request from a process
      System.out.println("Do you wish to continue:\nYes or No?");
      String c = input.nextLine().toLowerCase();
      if (c.equals("yes")) {
         System.out.println("Which process request more instances?\n");
         int index = input.nextInt();
         input.nextLine();
         System.out.println("What the request needed?\nFormat eg. A B C");
         int[] request = getValidIntArray(input, numResources);
         // Validate and update allocation with the request
         for (int i = 0; i < numResources; i++) {   
            if (allocation[index][i] <= maximum[index][i]){
               allocation[index][i] += request[i];
            } else {
               System.out.println("Process exceeded maximum instances");
               System.exit(1); // Terminate if request invalid
            }
         }
         // Check if state remains safe after request
         System.out.println("Banker's Algorithm Safety Check with request:");
         result = banker.state(allocation, available, maximum);
         System.out.println("Result: " + result);
      }
   }  
   
   // Utility: prompt user for positive integer input
   public static int getPositiveInt(Scanner input, String prompt) {
      int number = -1;
      while (number < 1) {
         System.out.println(prompt);
         if (input.hasNextInt()) {
            number = input.nextInt();
            if (number < 1) {
               System.out.println("Please enter a number greater than 0.");
            }
         } else {
            System.out.println("Invalid input. Please enter a valid integer.");
            input.next(); // consume invalid token
         }
      }
      input.nextLine(); // clear newline
      return number;
   }

   // Utility: get a valid integer array from user input of expected length
   public static int[] getValidIntArray(Scanner input, int expectedLength) {
      int[] result = null;
      while (true) {
         String line = input.nextLine();
         String[] tokens = line.trim().split("\\s+");
         if (tokens.length != expectedLength) {
            System.out.println("Incorrect number of values. Expected " + expectedLength + " integers. Try again:");
            continue;
         }
         result = new int[expectedLength];
         boolean valid = true;
         for (int i = 0; i < expectedLength; i++) {
            try {
               result[i] = Integer.parseInt(tokens[i]);
               if (result[i] < 0) {
                  System.out.println("Negative numbers are not allowed. Try again:");
                  valid = false;
                  break;
               }
            } catch (NumberFormatException e) {
               System.out.println("Invalid input. Please enter only integers. Try again:");
               valid = false;
               break;
            }
         }
         if (valid) 
            break;
      }
      return result;
   }

   // Utility: create a matrix (2D array) of given size
   public static int[][] createMatrix(int x, int y) {
      return new int[x][y];
   }

   // Utility: print resource headings A B C ...
   public static void headings(int r) {
      for (int a = 65; a < (r + 65); a++) {
         char ch = (char) a;
         System.out.print(ch + " ");
      }
      System.out.println();
   }
    
   // Reads formatted input file and parses process/resource info and matrices
   public static Object[] parseInput(String filename) throws IOException {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      int numProcesses = Integer.parseInt(reader.readLine().trim());
      int numResources = Integer.parseInt(reader.readLine().trim());
   
      int[] available = new int[numResources];
      int[][] allocation = new int[numProcesses][numResources];
      int[][] max = new int[numProcesses][numResources];
   
      String line;
      String section = "";
   
      int allocRow = 0, maxRow = 0;
   
      // Reads the file line-by-line, switching parsing modes for sections
      while ((line = reader.readLine()) != null) {
         line = line.trim();
         if (line.isEmpty()) 
            continue;
      
         switch (line) {
            case "Available":
            case "Allocation":
            case "Max":
               section = line;
               continue;
         }
      
         String[] parts = line.split("[,\\s]+"); // support commas or spaces as delimiters
         int[] numbers = new int[parts.length];
         for (int i = 0; i < parts.length; i++) {
            numbers[i] = Integer.parseInt(parts[i]);
         }
      
         // Store parsed numbers based on current section
         switch (section) {
            case "Available":
               available = numbers;
               break;
            case "Allocation":
               allocation[allocRow++] = numbers;
               break;
            case "Max":
               max[maxRow++] = numbers;
               break;
         }
      }
   
      reader.close();
      return new Object[]{numProcesses, numResources, allocation, max, available};
   }
}
