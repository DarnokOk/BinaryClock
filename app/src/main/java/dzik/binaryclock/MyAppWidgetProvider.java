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
        for (int appWidgetId : appWidgetIds) {
            createWidget(context, appWidgetManager, appWidgetId, appWidgetManager.getAppWidgetOptions(appWidgetId));
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        createWidget(context, appWidgetManager, appWidgetId, newOptions);
    }

    private void createWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle options) {
        mContext = context;
        int diameter = getCircleDiameter(options);
        Intent intent = new Intent(mContext, ConfigurationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        RemoteViews mainBody = new RemoteViews(mContext.getPackageName(), R.layout.widget);
        clearWidget(mainBody); //TODO: when creating the widget is empty
        mainBody.setOnClickPendingIntent(R.id.widget, pendingIntent);
        //TODO: https://www.google.pl/search?client=ubuntu&channel=fs&q=android+get+widget+size&ie=utf-8&oe=utf-8&gfe_rd=cr&ei=DP3vWMf8GvKv8wfSmZjYBA

        AppWidgetProviderInfo providerInfo = AppWidgetManager.getInstance(
                mContext.getApplicationContext()).getAppWidgetInfo(appWidgetId);
        for(int j = 0; j < 3; j++) {
            RemoteViews circleLine = new RemoteViews(mContext.getPackageName(), R.layout.circle_line);
            for (int k = 0; k < 6; k++) {
                RemoteViews circle = new RemoteViews(mContext.getPackageName(), R.layout.circle_layout);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    circle.setTextViewTextSize(R.id.textView, TypedValue.COMPLEX_UNIT_SP, 20);
                } else {
                    circle.setFloat(R.id.textView, "setTextSize", 20);
                }
                ImageView myView = new ImageView(context);
                myView.measure(45, 45);
                myView.layout(0, 0, 45, 45);
                GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(mContext, R.drawable.circle);
                drawable.setStroke(1, Color.BLACK);
                myView.setImageDrawable(drawable);
                myView.setDrawingCacheEnabled(true);
                Bitmap bitmap = myView.getDrawingCache();
                circle.setImageViewBitmap(R.id.imageViewCircle, bitmap);

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

    public Bitmap createBitmapFromLayoutWithText(Context context) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout lines = new LinearLayout(context);
        lines.setOrientation(LinearLayout.VERTICAL);
        for(int i = 0; i < 3; i++) {
            LinearLayout line = new LinearLayout(context);
            line.setOrientation(LinearLayout.HORIZONTAL);
            for(int j = 0; j < 6; j++) {
                TextView textView = new TextView(context);
                textView.setText("XD");
                line.addView(textView);
            }
            lines.addView(line);
        }
        lines.setLayoutParams(new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));
        lines.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        lines.layout(0, 0, lines.getMeasuredWidth(), lines.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(lines.getMeasuredWidth(),
                lines.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        lines.draw(c);
        return bitmap;
/*
        RelativeLayout view = new RelativeLayout(context);
        mInflater.inflate(R.layout.widget, view, true);
        TextView tv = (TextView) findViewById(R.id.my_text);
        tv.setText("Beat It!!");

        //Provide it with a layout params. It should necessarily be wrapping the
        //content as we not really going to have a parent for it.
        view.setLayoutParams(new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));

        //Pre-measure the view so that height and width don't remain null.
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

        //Assign a size and position to the view and all of its descendants
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        //Create the bitmap
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        //Create a canvas with the specified bitmap to draw into
        Canvas c = new Canvas(bitmap);

        //Render this view (and all of its children) to the given Canvas
        view.draw(c);
        return bitmap;
        */
    }

    private int getCircleDiameter(Bundle options) {
        //TODO: const for circles in a row and a column
        if(options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH) / 6 < options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT) / 3) {
            return options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH) / 6;
        } else {
            return options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT) / 3;
        }
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
