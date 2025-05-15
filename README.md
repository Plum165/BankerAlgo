# Program that is more of the blueprint for a skeleton of the Banker's Algorithm  
Moegamat Samsodien  
14/05/2025
# Note
Educational Purposes and that there could be a different order in execution as Banker Algorithm just looks for safe state so yours can be different.
# Description:
This program implements the Banker's Algorithm — a classic deadlock avoidance strategy in operating systems. 
The algorithm checks whether the system is in a safe state by simulating resource allocation for multiple processes. 
It ensures that there exists a safe sequence in which all processes can complete without causing a deadlock.

# Reason:
I implemented this to help me better understand and practice how the Banker's Algorithm works,
especially for manually calculating safe states and sequences — a common requirement in exams and tests.
It helps reinforce the logic behind comparing Need, Allocation, and Available matrices.

# 🏦 Banker's Algorithm Java Program

This Java program implements the **Banker's Algorithm**, a deadlock avoidance algorithm used in operating systems. It determines if a system is in a safe state given a set of resource allocations and maximum demands.

---

## 🔧 Requirements

- Java (JDK 11 or higher)
- Terminal or command prompt
- An input file (`.txt` or `.csv`) formatted correctly

---

## ▶️ How to Run

1. **Compile the program**:
   javac Banker_Algorithm_App.java BankersAlgorithm.java
2. **Run the program via bash or from APP**
   java Banker_Algorithm_App


# Format for text files to follow:
```
2         <-- numProcesses
3         <-- numResources
Available
3 3 2     <-- Available resources
Allocation
0 1 0     <-- One row per process
2 0 0
Max
7 5 3
3 2 2
```

