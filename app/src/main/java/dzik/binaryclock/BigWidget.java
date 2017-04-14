package dzik.binaryclock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RemoteViews;

import dzik.binaryclock.clock.ClockManager;

public class BigWidget {
    private RemoteViews mRemoteViews;
    private Context mContext;

    public BigWidget(Context context) {
        mContext = context;

        createWidget();
    }

    public RemoteViews getWidget() {
        return mRemoteViews;
    }

    public RemoteViews createWidget() {
        mRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget);

        populateRemoteView();

        return mRemoteViews;
    }

    private void populateRemoteView() {
        clearRemoteView();

        ClockManager manager = new ClockManager();

        boolean[][] binary = new boolean[3][6];
        //writeToArray(binary[0], manager.getBinaryHour());
        //writeToArray(binary[1], manager.getBinaryMinute());
        //writeToArray(binary[2], manager.getBinarySecond());
        binary[0] = manager.getBinaryHour();
        binary[1] = manager.getBinaryMinute();
        binary[2] = manager.getBinarySecond();

        int[] time = new int[3];
        time[0] = manager.getHour();
        time[1] = manager.getMinute();
        time[2] = manager.getSecond();

        for(int j = 0; j < 3; j++) {
            RemoteViews circleLine = new RemoteViews(mContext.getPackageName(), R.layout.circle_line);

            for (int k = 0; k < 6; k++) {
                circleLine.addView(R.id.circleLine, createCircleView(
                        k == 5 ? Integer.toString(time[j]) : null, binary[j][k]));
                //TODO: instead of toString just return string
            }

            mRemoteViews.addView(R.id.widget, circleLine);
        }
    }

    private void writeToArray(boolean[] to, boolean[] from) {
        for(int i = 0; i < from.length; i++) {
            to[i] = from[i];
        }
    }

    private RemoteViews createCircleView(String text, boolean glow) {
        RemoteViews circle = new RemoteViews(mContext.getPackageName(), R.layout.circle_layout);
        circle.setTextViewTextSize(R.id.textView, TypedValue.COMPLEX_UNIT_SP, 20);
        if(text != null) {
            circle.setTextViewText(R.id.textView, text);
        }
        circle.setImageViewBitmap(R.id.imageViewCircle, createCircleBitmap(glow));
        return circle;
    }

    private Bitmap createCircleBitmap(boolean glow) {
        ImageView myView = new ImageView(mContext);
        myView.measure(45, 45); //TODO: Don't allow for loose values
        myView.layout(0, 0, 45, 45);

        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(mContext, R.drawable.circle);
        //Log.w("XD", Boolean.toString(glow));
        drawable.setStroke(1, glow ? Color.WHITE : Color.BLACK);

        myView.setImageDrawable(drawable);
        myView.setDrawingCacheEnabled(true);
        return myView.getDrawingCache();
    }

    private void clearRemoteView() {
        mRemoteViews.removeAllViews(R.id.widget);
    }
}
