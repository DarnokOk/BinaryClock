package dzik.binaryclock.clock;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import dzik.binaryclock.R;
import dzik.binaryclock.clock.layout.LinearLayoutLine;

public class ClockManager {
    private static final int MS_TILL_REFRESH = 500; //At worst it will be delayed by 0,5s only
    private Context mContext;
    private LinearLayout mClockLayout;
    private boolean[][] mBinaryTime = new boolean[3][6];
    private int[] mTime = new int[3];
    private int[] mColor = new int[3];

    public ClockManager(Context context) {
        mContext = context;
        createClock();
    }

    public LinearLayout getClockLayout() {
        return mClockLayout;
    }

    public void turnOn() {
        final Handler handler = new Handler();
        final Runnable updateTask = new Runnable() {
            @Override
            public void run() {
                updateClock();
                handler.postDelayed(this, MS_TILL_REFRESH);
            }
        };
        handler.postDelayed(updateTask, MS_TILL_REFRESH);
    }

    private void updateClock() {
        getActualTime();
        for(int i = 0; i < 3; i++) {
            LinearLayoutLine line = (LinearLayoutLine) mClockLayout.getChildAt(i);
            line.setBinaryTime(mBinaryTime[i]);
            String time = mTime[i] >= 10 ? Integer.toString(mTime[i]) : "0" + Integer.toString(mTime[i]);
            line.setTime(time);
        }
    }

    private void updateTextSize() {
        for(int i = 0; i < 3; i++) {
            ((LinearLayoutLine) mClockLayout.getChildAt(i)).updateTextSize();
        }
    }

    private void updateColors() {
        getColors();
        for(int i = 0; i < 3; i++) {
            ((LinearLayoutLine) mClockLayout.getChildAt(i)).setActiveColor(mColor[i]);
        }
    }

    private void createClock() {
        mClockLayout = new LinearLayout(mContext);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        mClockLayout.setLayoutParams(params);
        mClockLayout.setOrientation(LinearLayout.VERTICAL);
        mClockLayout.setKeepScreenOn(true);
        for(int i = 0; i < 3; i++) {
            mClockLayout.addView(new LinearLayoutLine(mContext));
        }
        updateTextSize();
        updateColors();
        updateClock();
    }

    private void getColors() {
        mColor[0] = ResourcesCompat.getColor(mContext.getResources(), R.color.defaultHourCircle, null);
        mColor[1] = ResourcesCompat.getColor(mContext.getResources(), R.color.defaultMinuteCircle, null);
        mColor[2] = ResourcesCompat.getColor(mContext.getResources(), R.color.defaultSecondCircle, null);
    }

    private void getActualTime() {
        mBinaryTime[0] = ClockUtility.getBinaryHour();
        mBinaryTime[1] = ClockUtility.getBinaryMinute();
        mBinaryTime[2] = ClockUtility.getBinarySecond();

        mTime[0] = ClockUtility.getHour();
        mTime[1] = ClockUtility.getMinute();
        mTime[2] = ClockUtility.getSecond();
    }
}
