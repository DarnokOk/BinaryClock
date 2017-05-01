package dzik.binaryclock;

import org.junit.Assert;
import org.junit.Test;

import dzik.binaryclock.clock.clock.ClockUtility;

public class ClockUtilityUnitTest {
    @Test
    public void testStringToBooleanArray() {
        String binary = "100000";
        boolean[] binaryResults = new boolean[ClockUtility.BITS];
        binaryResults[0] = true;
        for(int i = 1; i < ClockUtility.BITS; i++) {
            binaryResults[i] = false;
        }

        Assert.assertArrayEquals(binaryResults, ClockUtility.getBooleanArray(binary));

        binary = "001110";
        for(int i = 0; i < 2; i++) {
            binaryResults[i] = false;
        }
        for(int i = 2; i < 5; i++) {
            binaryResults[i] = true;
        }
        binaryResults[5] = false;

        Assert.assertArrayEquals(binaryResults, ClockUtility.getBooleanArray(binary));

        binary = "000001";
        for(int i = 0; i < 5; i++) {
            binaryResults[i] = false;
        }
        binaryResults[5] = true;

        Assert.assertArrayEquals(binaryResults, ClockUtility.getBooleanArray(binary));
    }
}