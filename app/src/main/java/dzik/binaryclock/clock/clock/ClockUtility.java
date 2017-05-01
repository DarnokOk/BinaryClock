package dzik.binaryclock.clock.clock;

import java.util.Calendar;

public final class ClockUtility {
    public final static int BITS = 6;

    private ClockUtility() {
    }

    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    public static int getSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    public static boolean[] getBinaryHour() {
        return getBooleanArray(Integer.toBinaryString(getHour()));
    }

    public static boolean[] getBinaryMinute() {
        return getBooleanArray(Integer.toBinaryString(getMinute()));
    }

    public static boolean[] getBinarySecond() {
        return getBooleanArray(Integer.toBinaryString(getSecond()));
    }

    public static boolean[] getBooleanArray(String binary) {
        boolean[] array = new boolean[BITS];
        if(binary.length() < BITS) {
            for(int i = 0; i < BITS - binary.length(); i++) {
                array[i] = false;
            }
        }
        int n = 0;
        for(int i = BITS - binary.length(); i < BITS; i++) {
            array[i] = binary.charAt(n) == '1';
            n++;
        }
        return array;
    }
}