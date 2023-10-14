import subprocess
from typing import List, Dict, Tuple
import numpy as np # type: ignore
import time
import csv

# ---------------------
# Run Java application from Python
# ---------------------
TIMEOUT = 30

# run the given jar package,
# provide the given arg as the command-line 
# argument,
# feed the given input string to the stdin of the 
# process,
# and return the stdout from the process as string
def run_java(jar: str, arg: str, input: str)->str:
    print('Running java...')
    p = subprocess.Popen(['java','-jar', '-Xms8g',jar,arg], 
        stdin=subprocess.PIPE, 
        stdout=subprocess.PIPE)
    (output,_) = p.communicate(input.encode('utf-8'), 
        timeout=TIMEOUT)
    return output.decode('utf-8')

# Check that the application works
# if __name__ == '__main__':
#     print(run_java('threesum/app/build/libs/app.jar',
#         'cubic','3\n1 2 3'))
#     print(run_java('threesum/app/build/libs/app.jar',
#         'cubic','3\n1 2 -3'))
    


# ---------------------
# Generating input data
# ---------------------

TIMEOUT = 30

# FourSum HashMap gave a OutOfMemoryError exception when I_MAX = 25 and random OutOfMemoryError exception at random times in the benchmarking process.

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
def measure(algorithm: str, jar: str, input: List[int])->float:
    print('Measuring '+algorithm+' on '+str(len(input))+' inputs')
    input_string: str = f'{len(input)}\n' + \
        ' '.join(map(str,input))
    start: float = time.time()
    result_string: str = run_java(jar, algorithm, 
        input_string)
    end: float = time.time()
    assert result_string.strip() == 'null'
    return end - start

# Test to show measure function works
# if __name__ == '__main__':
#     print(measure('three-cubic', 
#         'threesum/app/build/libs/app.jar',
#         INPUT_DATA[30][0]))
    


# ---------------------
# Performing benchmarking
# ---------------------

def benchmark(algorithm: str, jar: str)-> \
    List[Tuple[int,float]]:
    results: List[Tuple[int,float]] = list()
    print(f'Benchmarking {algorithm}...')
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
            print("Timeout reached")
            break
    return results




# ---------------------
# Putting it all together (generating the csv file)
# ---------------------
# # ThreeSum testing
# INSTANCES: List[Tuple[str,str]] = [
#     ('three-cubic', 'project-files/app/build/libs/app.jar'),
#     ('three-quadratic', 'project-files/app/build/libs/app.jar'),
#     ('three-hashmap', 'project-files/app/build/libs/app.jar')
# ]

# FourSum testing
# INSTANCES: List[Tuple[str,str]] = [
#     ('four-quartic', 'project-files/app/build/libs/app.jar'),
#     ('four-cubic', 'project-files/app/build/libs/app.jar'),
#     ('four-hashmap', 'project-files/app/build/libs/app.jar')
# ]

## Testing everything
INSTANCES: List[Tuple[str,str]] = [
    ('three-cubic', 'project-files/app/build/libs/app.jar'),
    ('three-quadratic', 'project-files/app/build/libs/app.jar'),
    ('three-hashmap', 'project-files/app/build/libs/app.jar'),
    ('four-quartic', 'project-files/app/build/libs/app.jar'),
    ('four-cubic', 'project-files/app/build/libs/app.jar'),
    ('four-hashmap', 'project-files/app/build/libs/app.jar')
]

print('Creating results.csv')
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