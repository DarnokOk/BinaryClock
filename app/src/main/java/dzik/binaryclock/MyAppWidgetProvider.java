package dzik.binaryclock;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

import dzik.binaryclock.R;

public class MyAppWidgetProvider extends AppWidgetProvider {
    private Context mContext;

    //TODO: don't update too often http://www.androidauthority.com/how-to-create-a-android-widget-726045/
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int n = appWidgetIds.length;
        mContext = context;
        for (int i=0; i < n; i++) {
            int appWidgetId = appWidgetIds[i];
            Intent intent = new Intent(mContext, ConfigurationActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
            /*
            LayoutInflater inflater;
            inflater = (LayoutInflater) mContext.getApplicationContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.widget, null);
            //layout.setId(100);

            for(int j = 0; j < 3; j++) {
                layout.addView(createLine());
            }
            */

            //RemoteViews views = new RemoteViews(mContext.getPackageName(), 100);
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);
            views.setTextViewText(R.id.text, "XDDD");
            //appWidgetManager.updateAppWidget(appWidgetId, views);

            RemoteViews update = new RemoteViews(mContext.getPackageName(), R.layout.widget);
            for(int j = 0; j < 3; j++) {
                RemoteViews imageView = new RemoteViews(mContext.getPackageName(), R.layout.circle_line);
                //imageView.setImageViewResource(R.id.imageView, R.drawable.circle);
                //Drawable myDrawable = ContextCompat.getDrawable(mContext, R.drawable.circle);//mContext.getResources().getDrawable(R.drawable.anImage);
                //Bitmap bitmap  = ((BitmapDrawable) myDrawable).getBitmap();
                //Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.circle);
                //imageView.setImageViewBitmap(R.id.imageView, bitmap);
                imageView.setImageViewResource(R.id.imageView, R.drawable.circle); //TODO: http://stackoverflow.com/questions/14338091/setting-a-bitmap-using-remoteview-not-working
                update.addView(R.id.widget, imageView);
            }
            appWidgetManager.updateAppWidget(appWidgetId, update);
        }
    }

    private LinearLayout createLine() {
        LinearLayout linearLayout = new LinearLayout(mContext);
        for(int i = 0; i < 6; i++) {
            ImageView view = new ImageView(mContext);
            view.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.circle));
            linearLayout.addView(view);
        }
        return linearLayout;
    }
}
