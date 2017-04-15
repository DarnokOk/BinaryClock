package dzik.binaryclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import dzik.binaryclock.widget.BigWidget;

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
//TODO alarm doesnt work, it's not enabled? http://code4reference.com/2012/07/android-homescreen-widget-with-alarmmanager/
    @Override
    public void onDisabled(Context context) {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ 1000, 1000 , pi);
    }
}
