package hyperloglog;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;


public class HyperLogLogTest {

    // Asserts the input is read correctly.
    @Test 
    public void testReadInput() {
        String input = "1\n2\n3\n4\n5\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        long[] expectedInputArr = {1L, 2L, 3L, 4L, 5L};
        long[] inputArr = HyperLogLog.readInput(5);
        assertArrayEquals(expectedInputArr, inputArr);
    }

    // Asserts the register count is correct for a given input size.
    @Test 
    public void testProcessInput() {
        HyperLogLog.HyperLogLogEstimator estimator = new HyperLogLog.HyperLogLogEstimator(64);
        estimator.processInput(1);
        estimator.processInput(2);
        estimator.processInput(3);
        int[] registers = estimator.getRegisters();
        estimator.processInput(2);
        int[] updatedRegisters = estimator.getRegisters();
        assertArrayEquals(registers, updatedRegisters);
    }

    // Assert the cardinality is within a reasonable range.
    @Test 
    public void testEstimateCardinality() {
        HyperLogLog.HyperLogLogEstimator estimator = new HyperLogLog.HyperLogLogEstimator(64);
        estimator.processInput(1);
        estimator.processInput(2);
        estimator.processInput(3);
        long estimatedCardinality = estimator.estimateCardinality();
        assertTrue(estimatedCardinality >= 3 && estimatedCardinality <= 5);
    }

    // Assert the hash is different.
    @Test 
    public void testComputeHash() {
        int hash1 = HyperLogLog.HyperLogLogEstimator.computeHash(123);
        int hash2 = HyperLogLog.HyperLogLogEstimator.computeHash(456);
        assertNotEquals(hash1, hash2);
    }

    // Assert the registers are different.
    @Test 
    public void testPickRegister() {
        int register1 = HyperLogLog.HyperLogLogEstimator.pickRegister(123, 64);
        int register2 = HyperLogLog.HyperLogLogEstimator.pickRegister(456, 64);
        assertNotEquals(register1, register2);
    }

    // Assert the pComputation is different.
    @Test 
    public void testPComputations() {
        int result1 = HyperLogLog.HyperLogLogEstimator.pComputations(123);
        int result2 = HyperLogLog.HyperLogLogEstimator.pComputations(456);
        assertNotEquals(result1, result2);
    }
}
