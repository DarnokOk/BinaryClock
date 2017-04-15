package dzik.binaryclock.clock;

import android.util.Log;

import java.util.Calendar;

public final class ClockUtility { //TODO: static class
    private ClockUtility() {
    }

    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR);
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

    private static boolean[] getBooleanArray(String binary) {
        boolean[] array = new boolean[6];
        if(binary.length() < 6) {
            for(int i = 0; i < 6 - binary.length(); i++) {
                array[i] = false;
            }
        }
        int n = 0;
        for(int i = 6 - binary.length(); i < 6; i++) {
            array[i] = binary.charAt(n) == '1';
            n++;
        }
        return array;
    }
}