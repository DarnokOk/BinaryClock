package dzik.binaryclock.clock;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class LinearLayoutLine extends LinearLayout { //TODO: merge this class into ClockManager
    public LinearLayoutLine(Context context) {
        super(context);
    }

    public LinearLayoutLine(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearLayoutLine(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //TODO: code for width/6 > height/3
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()/6);
    }
}
