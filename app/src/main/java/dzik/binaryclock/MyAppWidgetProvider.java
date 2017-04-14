package dzik.binaryclock;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Bundle;

public class MyAppWidgetProvider extends AppWidgetProvider {
    private Context mContext;

    //TODO: don't update too often http://www.androidauthority.com/how-to-create-a-android-widget-726045/
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        mContext = context;
        for (int appWidgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId, new BigWidget(mContext).getWidget());
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        mContext = context;
        appWidgetManager.updateAppWidget(appWidgetId, new BigWidget(mContext).getWidget());
    }
}
