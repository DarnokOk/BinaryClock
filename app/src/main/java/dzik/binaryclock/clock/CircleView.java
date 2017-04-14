package dzik.binaryclock.clock;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

public class CircleView extends View {
    public CircleView(Context context) {
        super(context);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width, height);
        setMeasuredDimension(size, size);
        setBackgroundColor(Color.GREEN);
    }


}
