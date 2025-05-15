# Program that is more of the blueprint for a skeleton of the Banker's Algorithm  
Moegamat Samsodien  
14/05/2025

# Description:
This program implements the Banker's Algorithm — a classic deadlock avoidance strategy in operating systems. 
The algorithm checks whether the system is in a safe state by simulating resource allocation for multiple processes. 
It ensures that there exists a safe sequence in which all processes can complete without causing a deadlock.

# Reason:
I implemented this to help me better understand and practice how the Banker's Algorithm works,
especially for manually calculating safe states and sequences — a common requirement in exams and tests.
It helps reinforce the logic behind comparing Need, Allocation, and Available matrices.

# Format to use files:
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

