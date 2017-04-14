package dzik.binaryclock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RemoteViews;

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

    public RemoteViews createWidget() { //TODO: make other class for this
        mRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget);
        clearWidget();
        populateRemoteView();
        return mRemoteViews;
    }

    private void populateRemoteView() {
        for(int j = 0; j < 3; j++) {
            RemoteViews circleLine = new RemoteViews(mContext.getPackageName(), R.layout.circle_line);
            for (int k = 0; k < 6; k++) {
                circleLine.addView(R.id.circleLine, createCircle());
            }
            mRemoteViews.addView(R.id.widget, circleLine);
        }
    }

    private RemoteViews createCircle() {
        RemoteViews circle = new RemoteViews(mContext.getPackageName(), R.layout.circle_layout);
        circle.setTextViewTextSize(R.id.textView, TypedValue.COMPLEX_UNIT_SP, 20);
        circle.setImageViewBitmap(R.id.imageViewCircle, createCircleBitmap());
        return circle;
    }

    private Bitmap createCircleBitmap() {
        ImageView myView = new ImageView(mContext);
        myView.measure(45, 45); //TODO: Don't allow for loose values
        myView.layout(0, 0, 45, 45);
        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(mContext, R.drawable.circle);
        drawable.setStroke(1, Color.BLACK);
        myView.setImageDrawable(drawable);
        myView.setDrawingCacheEnabled(true);
        return myView.getDrawingCache();
    }

    private void clearWidget() {
        mRemoteViews.removeAllViews(R.id.widget);
    }
}
