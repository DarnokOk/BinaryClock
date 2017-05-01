package dzik.binaryclock.clock.layout;

import android.content.Context;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

import dzik.binaryclock.R;

public class LinearLayoutLine extends LinearLayout {
    private final static int CIRCLES_IN_LINE = 6;
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
        for(int i = 0; i < CIRCLES_IN_LINE; i++) {
            mCircles.get(i).setToggled(binaryTime[i]);
        }
    }

    public void setTime(String time) {
        if(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(getResources().getString(R.string.display_numbers_key), true)) {
            mTimeTextView.setText(time);
        }
    }

    public void setActiveColor(int color) {
        for(int i = 0; i < CIRCLES_IN_LINE; i++) {
            mCircles.get(i).setActiveColor(color);
        }
    }

    private void createView() {
        mCircles = new ArrayList<>();
        setOrientation(LinearLayout.HORIZONTAL);

        for(int j = 0; j < CIRCLES_IN_LINE; j++) {
            RelativeLayout circle = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.circle_layout, this, false);
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
            String size = PreferenceManager.getDefaultSharedPreferences(getContext())
                    .getString(getResources().getString(R.string.numbers_size_key),
                            getResources().getString(R.string.numbers_size_small_value));
            size = size.replaceAll("[^\\d\\.]", "");
            timeTextView.setTextSize(Float.parseFloat(size));
            timeTextView.setTextColor(PreferenceManager.getDefaultSharedPreferences(getContext())
                    .getInt(getResources().getString(R.string.color_numbers_key), 0));
        }
        if(!PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(getResources().getString(R.string.display_numbers_key), true)) {
            mTimeTextView.setText("");
        }
        if(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(getResources().getString(R.string.bold_numbers_key), false)) {
            mTimeTextView.setTypeface(mTimeTextView.getTypeface(), Typeface.BOLD);
        } else {
            mTimeTextView.setTypeface(null, Typeface.NORMAL);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int circleDiameter = getMeasuredWidth() / CIRCLES_IN_LINE;
        setMeasuredDimension(circleDiameter * CIRCLES_IN_LINE, circleDiameter);
    }
}
