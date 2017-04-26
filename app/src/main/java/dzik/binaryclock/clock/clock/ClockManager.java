package dzik.binaryclock.clock.clock;

import android.content.Context;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import dzik.binaryclock.R;
import dzik.binaryclock.clock.layout.LinearLayoutLine;

public class ClockManager {
    private static final int MS_TILL_REFRESH = 400; //At worst it will be delayed by 0,4s only, which isn't that much noticeable
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

    public void onUpdatedPreferences() {
        updateColors();
        updateTextViews();
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

    private void updateTextViews() {
        for(int i = 0; i < 3; i++) {
            ((LinearLayoutLine) mClockLayout.getChildAt(i)).updateTextViews();
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
        updateTextViews();
        updateColors();
        updateClock();
    }

    private void getColors() {
        mColor[0] = getColor(mContext.getString(R.string.color_circle_hour_key));
        mColor[1] = getColor(mContext.getString(R.string.color_circle_minute_key));
        mColor[2] = getColor(mContext.getString(R.string.color_circle_second_key));
    }

    private int getColor(String key) {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getInt(key, 0);
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
