package dzik.binaryclock;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.util.Locale;

import dzik.binaryclock.clock.CircleView;

public class MyAppWidgetProvider extends AppWidgetProvider {
    private Context mContext;

    //TODO: don't update too often http://www.androidauthority.com/how-to-create-a-android-widget-726045/
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        mContext = context;
        for (int appWidgetId : appWidgetIds) {
            createWidget(mContext, appWidgetManager, appWidgetId, appWidgetManager.getAppWidgetOptions(appWidgetId));
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        mContext = context;
        createWidget(context, appWidgetManager, appWidgetId, newOptions);
    }

    private void createWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle options) {
        mContext = context;
        RemoteViews mainBody = new RemoteViews(mContext.getPackageName(), R.layout.widget);
        clearWidget(mainBody); //TODO: BUG when creating the widget is empty - it populates after resizing
        populateRemoteView(mainBody);
        appWidgetManager.updateAppWidget(appWidgetId, mainBody);
    }

    private void populateRemoteView(RemoteViews views) {
        for(int j = 0; j < 3; j++) {
            RemoteViews circleLine = new RemoteViews(mContext.getPackageName(), R.layout.circle_line);
            for (int k = 0; k < 6; k++) {
                RemoteViews circle = new RemoteViews(mContext.getPackageName(), R.layout.circle_layout);
                circle.setTextViewTextSize(R.id.textView, TypedValue.COMPLEX_UNIT_SP, 20);
                circle.setImageViewBitmap(R.id.imageViewCircle, createCircleBitmap());
                circleLine.addView(R.id.circleLine, circle);
            }
            views.addView(R.id.widget, circleLine);
        }
    }
//TODO: Don't allow for loose values
    private Bitmap createCircleBitmap() {
        ImageView myView = new ImageView(mContext);
        myView.measure(45, 45);
        myView.layout(0, 0, 45, 45);
        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(mContext, R.drawable.circle);
        drawable.setStroke(1, Color.BLACK);
        myView.setImageDrawable(drawable);
        myView.setDrawingCacheEnabled(true);
        return myView.getDrawingCache();
    }

    private void clearWidget(RemoteViews views) {
        views.removeAllViews(R.id.widget);
    }
}
