package dzik.binaryclock;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class WidgetLayout extends LinearLayout {
    public WidgetLayout(Context context) {
        super(context);
    }

    public WidgetLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WidgetLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(16)
    public WidgetLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int padding;
        if(getMeasuredWidth() / 6 < getMeasuredHeight() / 3) {
            padding = getMeasuredHeight() - getMeasuredWidth() / 2;
            setPadding(0, padding/2, 0, padding/2);
        } else {
            padding = getMeasuredWidth() - getMeasuredHeight() * 2;
            setPadding(padding/2, 0, padding/2, 0);
        }
    }
}
