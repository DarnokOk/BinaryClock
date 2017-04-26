package dzik.binaryclock.clock.layout;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

import dzik.binaryclock.R;

public class LinearLayoutLine extends LinearLayout {
    public final static int CIRCLES_IN_LINE = 6;
    private boolean[] mBinaryTime;
    private String mTime;
    private int mActiveColor;
    private TextView mTimeTextView;
    private ArrayList<SquareImageView> mCircles;

    public LinearLayoutLine(Context context) {
        super(context);
        createView();
    }

    public LinearLayoutLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        createView();
    }

    public LinearLayoutLine(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        createView();
    }

    public void setBinaryTime(boolean[] binaryTime) {
        mBinaryTime = binaryTime;
        for(int i = 0; i < CIRCLES_IN_LINE; i++) {
            mCircles.get(i).setToggled(mBinaryTime[i]);
        }
    }

    public void setTime(String time) {
        mTime = time;
        mTimeTextView.setText(mTime);
    }

    public void setActiveColor(int color) {
        mActiveColor = color;
        for(int i = 0; i < CIRCLES_IN_LINE; i++) {
            mCircles.get(i).setActiveColor(mActiveColor);
        }
    }

    public void createView() {
        mCircles = new ArrayList<>();
        setOrientation(LinearLayout.HORIZONTAL);

        for(int j = 0; j < CIRCLES_IN_LINE; j++) {
            RelativeLayout circle = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.circle_layout, null);
            circle.setLayoutParams(new TableLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, 1f));

            if(j == CIRCLES_IN_LINE - 1) {
                mTimeTextView = ((TextView) circle.findViewById(R.id.circleTextView));
            }

            mCircles.add((SquareImageView) circle.findViewById(R.id.circleImageView));
            addView(circle);
        }
    }

    public void updateTextViews() {
        for(int j = 0; j < CIRCLES_IN_LINE; j++) {
            RelativeLayout circle = (RelativeLayout) getChildAt(j);
            TextView timeTextView = ((TextView) circle.findViewById(R.id.circleTextView));
            timeTextView.setTextSize(getResources().getDimension(R.dimen.medium_text_size));
            timeTextView.setTextColor(PreferenceManager.getDefaultSharedPreferences(getContext())
                    .getInt(getResources().getString(R.string.color_font_key), 0));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int circleDiameter = getMeasuredWidth() / CIRCLES_IN_LINE;
        setMeasuredDimension(circleDiameter * CIRCLES_IN_LINE, circleDiameter);
    }
}
