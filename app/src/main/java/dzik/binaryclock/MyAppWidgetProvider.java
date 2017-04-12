package dzik.binaryclock;

import android.appwidget.AppWidgetProvider;
import android.widget.RemoteViews;

import dzik.binaryclock.R;

public class MyAppWidgetProvider extends AppWidgetProvider {
    //TODO: don't update too often http://www.androidauthority.com/how-to-create-a-android-widget-726045/
    
    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.activity_main);
    appWidgetManager.updateAppWidget(currentWidgetId,views);
}
