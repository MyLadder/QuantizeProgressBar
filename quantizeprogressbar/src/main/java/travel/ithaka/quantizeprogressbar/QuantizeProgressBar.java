package travel.ithaka.quantizeprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

public class QuantizeProgressBar extends View {

    private int count;

    private int current = 0;

    private int completeColor;
    private int remainingColor;
    private int currentColor;

    private float dotRadius;
    private float xCoordinate;

    private Paint completePaint;
    private Paint remainingPaint;
    private Paint currentPaint;

    public QuantizeProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeAttributes(attrs);
    }

    private void initializeAttributes(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.QuantizeProgressBar,
                    0, 0);

            try {
                count = 3;
                setCompleteColor(a.getInteger(
                        R.styleable.QuantizeProgressBar_complete,
                        ContextCompat.getColor(getContext(), R.color.white)));

                setRemainingColor(a.getInteger(
                        R.styleable.QuantizeProgressBar_remaining,
                        ContextCompat.getColor(getContext(), R.color.semi_transparent_white)));

                setCurrentColor(a.getInteger(
                        R.styleable.QuantizeProgressBar_current,
                        ContextCompat.getColor(getContext(), R.color.ithaka_primary_color)));
            } finally {
                a.recycle();
            }

        } else {
            count = 3;
            setCompleteColor(ContextCompat.getColor(getContext(), R.color.ithaka_primary_color));
            setRemainingColor(ContextCompat.getColor(getContext(), R.color.ithaka_primary_color));
            setCurrentColor(ContextCompat.getColor(getContext(), R.color.ithaka_primary_color));
        }
    }

    private void init() {
        completePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        completePaint.setColor(completeColor);
        completePaint.setStrokeJoin(Paint.Join.ROUND);
        completePaint.setStrokeCap(Paint.Cap.ROUND);
        completePaint.setStrokeWidth(20);

        remainingPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        remainingPaint.setColor(remainingColor);
        remainingPaint.setStrokeJoin(Paint.Join.ROUND);
        remainingPaint.setStrokeCap(Paint.Cap.ROUND);
        remainingPaint.setStrokeWidth(20);

        currentPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        currentPaint.setColor(currentColor);
        currentPaint.setStrokeJoin(Paint.Join.ROUND);
        currentPaint.setStrokeCap(Paint.Cap.ROUND);
        currentPaint.setStrokeWidth(20);
    }

    public void reinitialize() {
        init();
        invalidate();
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());

        if (getMeasuredHeight() > getMeasuredWidth()) {
            dotRadius = getMeasuredWidth() / count / 4;
        } else {
            dotRadius = getMeasuredHeight() / 4;
        }

        float circlesWidth = (count * (dotRadius * 2)) + dotRadius * (count - 1);
        xCoordinate = (getMeasuredWidth() - circlesWidth) / 2 + dotRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircles(canvas);
    }

    private void drawCompletedCircle(Canvas canvas, float step) {
        canvas.drawCircle(xCoordinate + step,
                getMeasuredHeight() / 2,
                dotRadius,
                completePaint);
    }

    private void drawRemainingCircle(Canvas canvas, float step) {
        canvas.drawCircle(xCoordinate + step,
                getMeasuredHeight() / 2,
                dotRadius,
                remainingPaint);
    }

    private void drawCurrentPaint(Canvas canvas, float step) {
        canvas.drawCircle(xCoordinate + step,
                getMeasuredHeight() / 2,
                dotRadius,
                currentPaint);
    }

    private void drawCircles(Canvas canvas) {
        float step = 0;
        for (int i = 0; i < count; i++) {
            if (i < current) {
                drawCompletedCircle(canvas, step);
            } else if (i == current) {
                drawCurrentPaint(canvas, step);
            } else {
                drawRemainingCircle(canvas, step);
            }
            step += dotRadius * 3;
        }
    }

    public void setCount(int count) {
        this.count = count;
        reinitialize();
    }

    public void setCurrent(int current) {
        this.current = current;
        reinitialize();
    }

    public void setCompleteColor(int completeColor) {
        this.completeColor = completeColor;
    }

    public void setRemainingColor(int remainingColor) {
        this.remainingColor = remainingColor;
    }

    public void setCurrentColor(int currentColor) {
        this.currentColor = currentColor;
    }
}
