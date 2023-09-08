

# ---------------------
# Run Java application from Python
# ---------------------
import subprocess
TIMEOUT = 30

# run the given jar package,
# provide the given arg as the command-line 
# argument,
# feed the given input string to the stdin of the 
# process,
# and return the stdout from the process as string
def run_java(jar: str, arg: str, input: str)->str:
    p = subprocess.Popen(['java','-jar',jar,arg], 
        stdin=subprocess.PIPE, 
        stdout=subprocess.PIPE)
    (output,_) = p.communicate(input.encode('utf-8'), 
        timeout=TIMEOUT)
    return output.decode('utf-8')

if __name__ == '__main__':
    print(run_java('threesum/app/build/libs/app.jar',
        'cubic','3\n1 2 3'))
    print(run_java('threesum/app/build/libs/app.jar',
        'cubic','3\n1 2 -3'))
    


# ---------------------
# Generating input data
# ---------------------

from typing import List, Dict, Tuple
import numpy as np # type: ignore

TIMEOUT = 30

# how many different values of n
I_MAX: int = 30
# the different values of n
NS: List[int] = [int(30 * 1.41**i) \
    for i in range(I_MAX)]
# how many repetitions for the same n
M: int = 5
# seed for the pseudorandom number generator
SEED: int = 314159
# the PRNG object
rng = np.random.default_rng(SEED)
# The generated input:
# The dictionary maps n to a list of lists
# each list contains M lists of n ints
INPUT_DATA: Dict[int,List[List[int]]] = {
    n : [rng.integers(1, 2**28, n) \
         for _ in range(M)] \
    for n in NS
}


# ---------------------
# Add a framework to measure the running time
# ---------------------
from typing import List
import time

def measure(algorithm: str, jar: str, 
    input: List[int])->float:
    input_string: str = f'{len(input)}\n' + \
        ' '.join(map(str,input))
    start: float = time.time()
    result_string: str = run_java(jar, algorithm, 
        input_string)
    end: float = time.time()
    assert result_string.strip() == 'null'
    return end - start

if __name__ == '__main__':
    print(measure('cubic', 
        'threesum/app/build/libs/app.jar',
        INPUT_DATA[30][0]))
    


# ---------------------
# Performing benchmarking
# ---------------------

def benchmark(algorithm: str, jar: str)-> \
    List[Tuple[int,float]]:
    results: List[Tuple[int,float]] = list()

    for n in NS:
        try: 
            result_n: List[Tuple[int,float]] = list()
            for i in range(M):
                input: List[int] = INPUT_DATA[n][i]
                diff: float = measure(algorithm,jar,
                    input)
                result_n.append((n,diff))
            results += result_n
        except subprocess.TimeoutExpired:
            break
    return results




# ---------------------
# Putting it all together (generating the csv file)
# ---------------------
import csv

INSTANCES: List[Tuple[str,str]] = [
    ('cubic', 'threesum/app/build/libs/app.jar'),
    ('quadratic', 'threesum/app/build/libs/app.jar'),
    ('hashmap', 'threesum/app/build/libs/app.jar')
]

if __name__ == '__main__':
    with open('results.csv','w') as f:
        writer = csv.DictWriter(f, 
            fieldnames = ['algorithm','n','time'])
        writer.writeheader()
        for algorithm, jar in INSTANCES:
            results: List[Tuple[int,float]] = \
                benchmark(algorithm,jar)
            for (n,t) in results:
                writer.writerow({ 
                    'algorithm' : algorithm,
                    'n' : n,
                    'time' : t
                })