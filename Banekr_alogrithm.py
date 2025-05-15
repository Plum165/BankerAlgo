#Program that more of the blue print for a skeleton of banker alogrithm 
#Moegamat Samsodien
#14/05/2025

#need to check no maximum exceeds avaiable 
def need_caculate(allo, maxN):
    need = []
    for i in range(len(allo) ):
        a = maxN[i][0] - allo[i][0]
        b = maxN[i][1] - allo[i][1]
        c = maxN[i][2] - allo[i][2]
        need.append([a,b,c])
    
    return need
def work_caculate(allo, avaiable):
    work = []
    for h in avaiable:
        work.append(h)
    for th in allo:
        for t in range(len(th)):
            work[t] -= th[t]
        
       
    return work
def work_checker(work):
    for i in work:
        if i < 0:
            return True
    return False

def print_need(need):
    for n in need:
        print(" ".join(map(str, n)))

def state(allo, avaiable, maxN):
    sequence = []
    work = work_caculate(allo,avaiable)
    if work_checker(work):
        return "Impossible state"

    need  = need_caculate(allo, maxN)
    print_need(need)
    while True:
        flag = True
        for i in range(len(need)):
            t = need[i]
            if t == None:
                continue
            if t[0] <= work[0] and t[1] <= work[1] and t[2] <= work[2]:
                sequence.append(i)
                
                print("Current Work + allocation:")
                print(" ".join(map(str, work)))
                print("+")
                print(" ".join(map(str, allo[i])))
                for j in range(len(work)):
                    work[j] += allo[i][j]
                print("=", " ".join(map(str, work)))
                need[i] = None
                flag = False
              
        if flag:
            break
    if len(sequence) != len(need):
        return "Unsafe state"
    else : 
        print("Safe sequence:", sequence)
        return "Safe state"
        
def main():
    # Allocation matrix: what each process is currently holding
    allocation = [
        [2, 0, 1],  # P0
        [1, 0, 1],  # P1
        [1, 1, 0],  # P2
        [0, 1, 0],  # P3
      
    ]

    # Maximum matrix: max demand for each process
    maximum = [
        [2, 1, 1],  # P0
        [3, 0, 2],  # P1
        [5, 2, 1],  # P2
        [1, 1, 1],  # P3
     
    ]

    # Available resources
    available = [5, 2, 3]

    print("Banker's Algorithm Safety Check:")
    result = state(allocation, available, maximum)
    print("Result:", result)

# Run the tester
main()
