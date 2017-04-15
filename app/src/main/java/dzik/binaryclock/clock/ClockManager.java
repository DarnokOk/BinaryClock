package dzik.binaryclock.clock;

import android.util.Log;

import java.util.Calendar;

public class ClockManager {
    public ClockManager() {
    }

    public int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR);
    }

    public int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    public int getSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    public boolean[] getBinaryHour() {
        return getBooleanArray(Integer.toBinaryString(getHour()));
    }

    public boolean[] getBinaryMinute() {
        return getBooleanArray(Integer.toBinaryString(getMinute()));
    }

    public boolean[] getBinarySecond() {
        return getBooleanArray(Integer.toBinaryString(getSecond()));
    }

    private boolean[] getBooleanArray(String binary) {
        boolean[] array = new boolean[6];
        Log.w("XD", binary);
        if(binary.length() < 6) {
            for(int i = 0; i < 6 - binary.length(); i++) {
                array[i] = false;
                Log.w("XD", Boolean.toString(array[i]));
            }
        }
        int n = 0;
        for(int i = 6 - binary.length(); i < 6; i++) {
            array[i] = binary.charAt(n) == '1';
            Log.w("XDD", Boolean.toString(array[i]));
            n++;
        }
        return array;
    }
}