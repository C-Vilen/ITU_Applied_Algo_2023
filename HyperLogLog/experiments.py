from typing import List
import numpy as np
import csv
import subprocess
import random

# ---------------------
# Run Java application from Python
# ---------------------

def run_java(jar: str, AlgoArg0: str, RegistersArg1: str, SizeArg1: str, input_str: str) -> str:
    print("Running java....")
    command = ['java', '-Xmx5G', '-jar', jar, AlgoArg0, RegistersArg1, SizeArg1]
    process = subprocess.Popen(command,
                               stdin=subprocess.PIPE,
                               stdout=subprocess.PIPE,
                               stderr=subprocess.PIPE,
                               text=True)  

    (output, _) = process.communicate(input=input_str)
    
    if process.returncode == 0:
        return output
    else:
        print("Error occurred while running Java subprocess:")
        print(process.stderr)
        return ""

def run_output(jar: str, AlgoArg0: str, RegistersArg1: str, SizeArg1: str, input: List[int]) -> str:
    print("Running output....")
    input_str = ' '.join(map(str, input))
    result_str = run_java(jar, AlgoArg0, RegistersArg1, SizeArg1, input_str)
    return result_str

# ---------------------
# Generating input data
# ---------------------

SEED: int = 314159
rng = np.random.default_rng(SEED)
registersArr = ['1024', '2048', '4096']
M = 10  # Number of different lists
N = 100000  # Initial length of lists

# Generates a single list with distinctive elements
def randomGenerator(n: int, previous_list: List[int] = None) -> List[int]:
    random.seed(SEED)
    if previous_list:
        lower_bound = max(previous_list) + 1
        upper_bound = lower_bound + n
        return random.sample(range(lower_bound, upper_bound), n)
    else:
        return random.sample(range(1, 2**28), n)

# Generate M lists with increasing lengths and distinctive elements
INPUT_DATA: List[List[int]] = []
previous_list = None

for _ in range(M):
    current_list = randomGenerator(N, previous_list)
    INPUT_DATA.append(current_list)
    previous_list = current_list
    N += 100000  # Increase the length for the next list


# ---------------------
# Putting it all together (generating the csv file)
# ---------------------

if __name__ == '__main__':
    import csv
    with open('resultsHyperLogLog.csv', 'w') as f:
        writer = csv.DictWriter(f,
            fieldnames = ['m = ' + registersArr[0], 'm = ' + registersArr[1], 'm = ' + registersArr[2],'elements'])
        writer.writeheader()
        for l in INPUT_DATA:
            writer.writerow({
                'm = ' + registersArr[0]: run_output('app/build/libs/app.jar', 'HyperLogLog', registersArr[0], str(len(l)), l),
                'm = ' + registersArr[1]: run_output('app/build/libs/app.jar', 'HyperLogLog', registersArr[1], str(len(l)), l),
                'm = ' + registersArr[2]: run_output('app/build/libs/app.jar', 'HyperLogLog', registersArr[2], str(len(l)), l),
                'elements': len(l)
            })

