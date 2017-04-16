package dzik.binaryclock.clock;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import dzik.binaryclock.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity {
    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private FrameLayout mActivityContent;
    private boolean mVisible;

    private final Runnable mSetFullscreenRunnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mActivityContent.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    private final Runnable mShowActionBarRunnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };

    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVisible = true;
        setupLayout();
        setupActionBar();
    }

    private void setupLayout() {
        setContentView(R.layout.activity_main);
        mActivityContent = (FrameLayout) findViewById(R.id.activityContent);
        mActivityContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        //TODO: ClockManager class {
        LinearLayout lines = new LinearLayout(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        lines.setLayoutParams(params);
        lines.setOrientation(LinearLayout.VERTICAL);
        lines.setKeepScreenOn(true);
        for(int i = 0; i < 3; i++) {
            LinearLayoutLine line = new LinearLayoutLine(this);
            line.setOrientation(LinearLayout.HORIZONTAL);

            for(int j = 0; j < 6; j++) {
                RelativeLayout circle = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.circle_layout, null); //TODO: margins
                circle.setLayoutParams(new TableLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, 1f));
                GradientDrawable drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.OVAL);
                drawable.setStroke((int) getResources().getDimension(R.dimen.circle_stroke_size), Color.parseColor("#EEEEEE"));
                SquareImageView circleImageView = ((SquareImageView) circle.findViewById(R.id.circleImageView));
                circleImageView.setImageDrawable(drawable);

                line.addView(circle);
            }

            lines.addView(line);
        }
        //TODO: }
        mActivityContent.addView(lines);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar(); //TODO: add options to this bar
        if(actionBar != null) {
            actionBar.setTitle(getString(R.string.app_name));
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(UI_ANIMATION_DELAY);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;
        mHideHandler.removeCallbacks(mShowActionBarRunnable);
        mHideHandler.postDelayed(mSetFullscreenRunnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        mActivityContent.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;
        mHideHandler.removeCallbacks(mSetFullscreenRunnable);
        mHideHandler.postDelayed(mShowActionBarRunnable, UI_ANIMATION_DELAY);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
