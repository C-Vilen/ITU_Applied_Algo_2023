package threesum;

import org.junit.Test;
import static org.junit.Assert.*;

/*
     * -----------------------------------------------------------------
     *                          ThreeSum tests
     * -----------------------------------------------------------------
     */

public class ThreeSumTest {
    @Test 
    public void testThreeSumCubic() {
        // Zero triplets
        assertNull(ThreeSum.threeSumCubic(
            new int [] { }
        ));

        // One triplet, no solution
        assertNull(ThreeSum.threeSumCubic(
            new int [] { 1, 2, 3 }
        ));

        // One triplet, not null
        assertNotNull(ThreeSum.threeSumCubic(
            new int [] { 1, 2, -3 }
        ));

        // One triplet, correct solution
        assertArrayEquals(new int[] { 1, 2, -3 },
           ThreeSum.threeSumCubic(new int [] { 
               1, 2, -3 
           })
        );

        // Two triplets, no solution
        assertNull(ThreeSum.threeSumCubic(
            new int [] { 1, 2, 3, 4, 5, 6 }
        ));

        // Two triplets, correct solution
        assertArrayEquals(new int[] { 1, 3, -4 },
            ThreeSum.threeSumCubic(new int [] { 
                1, 2, 3, -4, -5, -6 
            })
        );
    }

    @Test
    public void testThreeSumQuadratic() {
        // Zero triplets
        assertNull(ThreeSum.threeSumQuadratic(
            new int [] { }
        ));

        // One triplet, no solution
        assertNull(ThreeSum.threeSumQuadratic(
            new int [] { 1, 2, 3 }
        ));

        // One triplet, not null
        assertNotNull(ThreeSum.threeSumQuadratic(
            new int [] { 1, 2, -3 }
        ));

        // One triplet, correct solution
        assertArrayEquals(new int[] { -3, 1, 2 },
           ThreeSum.threeSumQuadratic(new int [] { 
               1, 2, -3 
           })
        );

        // Two triplets, no solution
        assertNull(ThreeSum.threeSumQuadratic(
            new int [] { 1, 2, 3, 4, 5, 6 }
        ));

        // Two triplets, correct solution
        assertArrayEquals(new int[] { -5, 2, 3 },
            ThreeSum.threeSumQuadratic(new int [] { 
                1, 2, 3, -4, -5, -6 
            })
        );
    }

    @Test
    public void testThreeSumHashMap() {
        // Zero triplets, no solution
        assertNull(ThreeSum.threeSumHashMap(
            new int [] { }
        ));

        // One triplet, no solution
        assertNull(ThreeSum.threeSumHashMap(
            new int [] { 1, 2, 3 }
        ));

        // One triplet, not null
        assertNotNull(ThreeSum.threeSumHashMap(
            new int [] { 1, 2, -3 }
        ));

        // One triplet, correct solution
        assertArrayEquals(new int[] { 1, 2, -3 },
           ThreeSum.threeSumHashMap(new int [] { 
               1, 2, -3 
           })
        );

        // Two triplets, no solution
        assertNull(ThreeSum.threeSumHashMap(
            new int [] { 1, 2, 3, 4, 5, 6 }
        ));
        
        int[] input = {2, 1, 0, -1, -2};
        // Two triplets, correct solution
        assertArrayEquals(new int[] { 2, 0, -2 },
            ThreeSum.threeSumHashMap(input)
        );
    }

    /* 
     * ------- PLEASE NOTE THE BELOW! -------   
     * The following tests is to show that the "ThreeSumHashMapMissingComparison" method does NOT work as it should.
     * The tests shows that "&& j < k" is important as it ensures the elements are at DISTINCT positions in the array.
     */
    @Test
    public void testThreeSumHashMapMissingComparison(){
        int[] hashArray1 = { 3, -6 };
        assertNull(ThreeSum.threeSumHashMap(hashArray1));
        assertNotNull(ThreeSum.ThreeSumHashMapMissingComparison(hashArray1));
        // The test below shows that the new method is not working as it should.
        assertArrayEquals(new int[] { 3, -6, 3 }, ThreeSum.ThreeSumHashMapMissingComparison(hashArray1)); 

        int[] hashArray2 = { -4, 2};
        assertNull(ThreeSum.threeSumHashMap(hashArray2));
        assertNotNull(ThreeSum.ThreeSumHashMapMissingComparison(hashArray2));
        // The test below shows that the new method is not working as it should.
        assertArrayEquals(new int[] { -4, 2, 2 }, ThreeSum.ThreeSumHashMapMissingComparison(hashArray2));
    }

    /*
     * -----------------------------------------------------------------
     *                          FourSum tests
     * -----------------------------------------------------------------
     */

    @Test
    public void testFourSumQuartic() {
        // Zero triplets
        assertNull(ThreeSum.fourSumQuartic(
            new int [] { }
        ));

        // One triplet, no solution
        assertNull(ThreeSum.fourSumQuartic(
            new int [] { 1, 2, 3, 4 }
        ));

        // One triplet, not null
        assertNotNull(ThreeSum.fourSumQuartic(
            new int [] { 5, 2, -3, -4 }
        ));

        // One triplet, correct solution
        assertArrayEquals(new int[] { -3, 2, -3, 4 },
           ThreeSum.fourSumQuartic(new int [] { 
               -3, 2, -3, 4 
           })
        );

        // Two triplets, no solution
        assertNull(ThreeSum.fourSumQuartic(
            new int [] { 1, 2, 3, 4, 5, 6 }
        ));

        // Two triplets, correct solution
        assertArrayEquals(new int[] { 1, 2, 3, -6 },
            ThreeSum.fourSumQuartic(new int [] { 
                1, 2, 3, -4, -5, -6, 5 
            })
        );
    }

    @Test
    public void testFourSumCubic(){
        // Zero triplets
        assertNull(ThreeSum.fourSumCubic(
            new int [] { }
        ));

        // One triplet, no solution
        assertNull(ThreeSum.fourSumCubic(
            new int [] { 1, 2, 3, 4 }
        ));

        // One triplet, not null
        assertNotNull(ThreeSum.fourSumCubic(
            new int [] { 5, 2, -3, -4 }
        ));

        // One triplet, correct solution
        assertArrayEquals(new int[] { -3, -3, 2, 4 },
           ThreeSum.fourSumCubic(new int [] { 
               -3, 2, -3, 4 
           })
        );

        // Two triplets, no solution
        assertNull(ThreeSum.fourSumCubic(
            new int [] { 1, 2, 3, 4, 5, 6 }
        ));

        // Two triplets, correct solution
        assertArrayEquals(new int[] { -6, 1, 2, 3 },
            ThreeSum.fourSumCubic(new int [] { 
                1, 2, 3, -4, -5, -6, 5 
            })
        );
    }

    @Test 
    public void testFourSumHashMap(){
        // Zero triplets
        assertNull(ThreeSum.fourSumHashMap(
            new int [] { }
        ));

        // One triplet, no solution
        assertNull(ThreeSum.fourSumHashMap(
            new int [] { 1, 2, 3, 4 }
        ));

        // One triplet, not null
        assertNotNull(ThreeSum.fourSumHashMap(
            new int [] { 5, 2, -3, -4 }
        ));

        // One triplet, correct solution
        assertArrayEquals(new int[] { -3, 2, -3, 4 },
           ThreeSum.fourSumHashMap(new int [] { 
               -3, 2, -3, 4 
           })
        );

        // Two triplets, no solution
        assertNull(ThreeSum.fourSumHashMap(
            new int [] { 1, 2, 3, 4, 5, 6 }
        ));

        // Two triplets, correct solution
        assertArrayEquals(new int[] { 1, 2, 3, -6 },
            ThreeSum.fourSumHashMap(new int [] { 
                1, 2, 3, -4, -5, -6, 5 
            })
        );
    }
}
