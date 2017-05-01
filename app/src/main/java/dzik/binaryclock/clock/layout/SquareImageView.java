package dzik.binaryclock.clock.layout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import dzik.binaryclock.R;

public class SquareImageView extends ImageView {
    private GradientDrawable mDrawable;
    private boolean mToggled;
    private int mActiveColor;

    public SquareImageView(Context context) {
        super(context);
        createView();
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createView();
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        createView();
    }

    private void createView() {
        mDrawable = new GradientDrawable();
        mDrawable.setShape(GradientDrawable.OVAL);
        setImageDrawable(mDrawable);
    }

    public void setToggled(boolean toggled) {
        mToggled = toggled;
        toggle(mToggled);
    }

    private void toggle(boolean toggle) {
        int color;
        if(toggle) {
            color = mActiveColor;
        } else {
            color = PreferenceManager.getDefaultSharedPreferences(getContext())
                    .getInt(getResources().getString(R.string.color_circle_inactive_key), 0);
        }
        int multiplier = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getContext())
                .getString(getResources().getString(R.string.circle_width_key),
                        getResources().getString(R.string.circle_width_normal_value)));
        mDrawable.setStroke(getMeasuredHeight() * multiplier / 120, color);
        if(PreferenceManager.getDefaultSharedPreferences(getContext())
                .getBoolean(getResources().getString(R.string.fill_circles_key), false)) {
            mDrawable.setColor(color);
        } else {
            mDrawable.setColor(Color.TRANSPARENT);
        }
    }

    public void setActiveColor(int color) {
        mActiveColor = color;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        toggle(mToggled); //This sets the border to correct size
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        int margin = getMeasuredHeight() / 30;
        layoutParams.setMargins(margin, margin, margin, margin);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredHeight());
        requestLayout();
    }
}