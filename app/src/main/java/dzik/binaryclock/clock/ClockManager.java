package dzik.binaryclock.clock;

import java.util.Calendar;

public class ClockManager {
    public String getBinaryHour() {
        return Integer.toBinaryString(Calendar.getInstance().get(Calendar.HOUR));
    }

    public String getBinaryMinute() {
        return Integer.toBinaryString(Calendar.getInstance().get(Calendar.MINUTE);
    }

    public String getBinarySecond() {
        return Integer.toBinaryString(Calendar.getInstance().get(Calendar.SECOND));
    }

    public boolean[] getBinary(String binary) {
        boolean[] array;
        array = new boolean[binary.length()];
        for(int i = binary.length() - 1; i >= 0; i--) {
            array[i] = binary.charAt(i) == 1; //TODO: check if it works
        }
        return array;
    }
}