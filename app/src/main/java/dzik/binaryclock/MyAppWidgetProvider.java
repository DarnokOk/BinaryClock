package dzik.binaryclock;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import dzik.binaryclock.R;

public class MyAppWidgetProvider extends AppWidgetProvider {

    //TODO: don't update too often http://www.androidauthority.com/how-to-create-a-android-widget-726045/
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int n = appWidgetIds.length;
        for (int i = 0; i < n; i++) {
            int appWidgetId = appWidgetIds[i];
            Intent intent = new Intent(context, ConfigurationActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

}
