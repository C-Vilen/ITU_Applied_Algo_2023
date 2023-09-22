## Goal
The purpose of this exercise is to familiarize yourself with simple bit operations using a very frequently occurring idiom, namely to select and output the value of a designated bit in a word. You should use bitwise operators in Java that operate on words. Do not convert the integer values into strings; this is very inefficient.

## Task 1 (k-th bit)
Solve the following in a file KTHBit.java.

### Mathematical description
Given a 32-bit integer 
, we number the bits 0, 1, ..., 31 starting from the least significant bit. That is, as we would commonly write the value of the integer as a binary number, bit number 0, corresponding to 1, would be the rightmost bit, and bit number 31, corresponding to 
, would be the leftmost bit. You are supposed to extract the 
th bit using bitwise operations.

### Specification of your program
Your program should read a number of unsigned integers from standard input, extract the 
th bit in each, and print out the values of the said bits (one per line).

The first line of the input contains two integers: 
 and 
 separated by spaces. The number 
 is equal to the number of integers that follow, and 
 is the number of the bit to extract.

The remaining 
 lines each contain an unsigned 32-bit integer as a 8-byte hex string.

Your program should output 
 lines each containing either a character '1' or a character '0' (without the apostrophes).

 ### Test data:
 ```
 3 2
1A
FF
80
 ```

 ### Expected output:
 ```
 0
 1
 0
 ```
### Expected output in 32-bit binary:
```
0000000000000000000000000011010 -> 26
0000000000000000000000001111111 -> 255
0000000000000000000000001000000 -> 128
```

## Task 2 (Population Count)
Submit the following program as PopCount.java.

### Mathematical description
Given a signed 64-bit integer 
, the population count is simply the number of bits that are one, or equivalently the sum of all bits in the integer. If we denote the 
th bit by 
, then the population count would be equal to 
. For the purposes of this assignment, a simple summation will do. There are ways to make this computation more efficient, but they are beyond the scope of this assignment.

### Specification of your program
Your program should read a number of signed integers from standard input, compute the population count of each integer, and output the population counts into standard output on separate lines. The first line of the input contains one integers 
 that is equal to the number of integers that follow. The remaining 
 lines each contain a signed, positive 64-bit integer as a 16-byte hex string. All numbers are positive, so there are at most 63 bits set. Your program should output 
 lines each containing the population count as a decimal number (between 0 and 63, inclusive).

## Task 3 (Jenkins Hash Function)
Solve the following in a file jenkins.py. (I did not get this to work correctly in Java!)

### Mathematical description
Comparing two strings can be very inefficient if the strings are long. However, in many applications, we would like to determine if there exist a pair of strings in a database that are equal. One option to approach this would be by using hashes: a simple value (usually a word-length integer) computed from the strings such that two strings are guaranteed to have the same hash if they are equal. Note though, that this guarantee goes only one way: two strings may also have equal values even if they are distinct. This is called a hash collision. So, differing hash values ensure that the two strings are distinct, but the opposite may not necessarily hold. A good hash function has few collisions; it behaves as if a random value was associated with each string. Look up the definition of the function in Wikipedia. The function should map an arbitrary-length string to a 32-bit unsigned integer. For example, correctly implemented hash function will map the string "a" to ca2e9442 and "The quick brown fox jumps over the lazy dog" to 519e91f5 (without quotation marks).

### Specification of your program
Your program should read an arbitrary string from standard input. You may assume that the string contains only printable 7-bit ASCII characters. The program should then compute the Jenkins' hash value for the string, and output it as a 8-byte hex string to standard output.