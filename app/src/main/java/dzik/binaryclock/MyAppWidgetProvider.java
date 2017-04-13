package dzik.binaryclock;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import java.util.Locale;

public class MyAppWidgetProvider extends AppWidgetProvider {
    private Context mContext;

    //TODO: don't update too often http://www.androidauthority.com/how-to-create-a-android-widget-726045/
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            createWidget(context, appWidgetManager, appWidgetId, appWidgetManager.getAppWidgetOptions(appWidgetId));
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        createWidget(context, appWidgetManager, appWidgetId, newOptions);
    }

    private void createWidget(Context context, AppWidgetManager appWidgetManager,
                              int appWidgetId, Bundle options) {
        mContext = context;
        //TODO: count everything with MAX_HEIGHT
        Intent intent = new Intent(mContext, ConfigurationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        RemoteViews mainBody = new RemoteViews(mContext.getPackageName(), R.layout.widget);
        clearWidget(mainBody);
        mainBody.setOnClickPendingIntent(R.id.widget, pendingIntent);
        for(int j = 0; j < 3; j++) {
            RemoteViews circleLine = new RemoteViews(mContext.getPackageName(), R.layout.circle_line);
            for (int k = 0; k < 6; k++) {
                RemoteViews circle = new RemoteViews(mContext.getPackageName(), R.layout.circle_layout);
                circle.setInt(R.id.imageViewCircle, "setColorFilter", Color.RED);
                circle.setInt(R.id.imageViewCircleInside, "setColorFilter", Color.BLUE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    circle.setTextViewTextSize(R.id.textView, TypedValue.COMPLEX_UNIT_SP, 40);
                } else {
                    circle.setFloat(R.id.textView, "setTextSize", 40);
                }
                circleLine.addView(R.id.circleLine, circle);
            }
            mainBody.addView(R.id.widget, circleLine);
        }
        appWidgetManager.updateAppWidget(appWidgetId, mainBody);
        /*
        String msg=
                String.format(Locale.getDefault(),
                        "[%d-%d] x [%d-%d]",
                        newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH),
                        newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH),
                        newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT),
                        newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT));

        //update.setTextViewText(R.id.size, msg);
        //update.removeAllViews();
        //update.setTextViewText(R.id.textView, Integer.toString(newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)));

        appWidgetManager.updateAppWidget(appWidgetId, update);
        */
    }

    private void clearWidget(RemoteViews views) {
        views.removeAllViews(R.id.widget);
    }

/*
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().contentEquals("com.sec.android.widgetapp.APPWIDGET_RESIZE")) {
            handleTouchWiz(context, intent);
        }
        super.onReceive(context, intent);
    }

    private void handleTouchWiz(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        int appWidgetId = intent.getIntExtra("widgetId", 0);
        int widgetSpanX = intent.getIntExtra("widgetspanx", 0);
        int widgetSpanY = intent.getIntExtra("widgetspany", 0);

        if(appWidgetId > 0 && widgetSpanX > 0 && widgetSpanY > 0) {
            Bundle newOptions = new Bundle();
            newOptions.putInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, widgetSpanY * 74);
            newOptions.putInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, widgetSpanX * 74);

            onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        }
    }
*/
}
