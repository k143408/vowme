package com.vowme.app.utilities.spannables;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

public class BackgroundColorWithoutLineHeightSpan extends ReplacementSpan {
    private final int mColor;

    public BackgroundColorWithoutLineHeightSpan(int color) {
        this.mColor = color;
    }

    public int getSize(Paint paint, CharSequence text, int start, int end, FontMetricsInt fm) {
        return Math.round(measureText(paint, text, start, end));
    }

    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        int paintColor = paint.getColor();
        Rect bound = new Rect();
        paint.getTextBounds(text.toString(), start, end, bound);
        int textHeight = bound.height();
        int topGreyBox = top == 0 ? top : top - (textHeight / 6);
        RectF rectF = new RectF(x, (float) topGreyBox, measureText(paint, text, start, end) + x, (float) ((topGreyBox + textHeight) + ((textHeight * 5) / 6)));
        paint.setColor(this.mColor);
        canvas.drawRect(rectF, paint);
        paint.setColor(paintColor);
        canvas.drawText(text, start, end, x, (float) y, paint);
    }

    private float measureText(Paint paint, CharSequence text, int start, int end) {
        return paint.measureText(text, start, end);
    }
}
