package com.averin.dotprogresslibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by egor on 17.07.17.
 */

public class DotProgress extends View {

  private int dotsCount;
  private int dotsPaddingPx;
  private int dotsProgressColor;
  private int dotsDefaultColor;
  private int progress;
  private Paint progressPaint = new Paint();
  private Rect  viewRect;
  private float dotRadiusPx;

  public DotProgress(Context context) {
    super(context);
  }

  public DotProgress(
      Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
    initAttrs(attrs);
  }

  public DotProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initAttrs(attrs);
  }

  public DotProgress(
      Context context,
      @Nullable AttributeSet attrs,
      int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    initAttrs(attrs);
  }

  private void initAttrs(AttributeSet attrs) {
    TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.DotProgress, 0, 0);
    try {
      dotsCount = a.getInteger(R.styleable.DotProgress_dotsCount, 25);
      dotsPaddingPx = a.getDimensionPixelSize(
          R.styleable.DotProgress_dotsPadding,
          (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2,
                                          getContext().getResources().getDisplayMetrics()));
      dotsDefaultColor = a.getColor(R.styleable.DotProgress_dotDefaultColor, Color.GRAY);
      dotsProgressColor = a.getColor(R.styleable.DotProgress_dotProgressColor, Color.BLUE);
    } finally {
      a.recycle();
    }
    viewRect = new Rect();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    viewRect.set(0,
                 0,
                 MeasureSpec.getSize(widthMeasureSpec),
                 MeasureSpec.getSize(heightMeasureSpec));
    dotRadiusPx = (viewRect.width() - (dotsCount - 1) * dotsPaddingPx) / dotsCount / 2;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    float          cy = viewRect.centerY();
    float          cx = dotRadiusPx;
    for (int i = 0; i < dotsCount; i++) {
      if (i >= progress) {
        progressPaint.setColor(dotsDefaultColor);
      } else {
        progressPaint.setColor(dotsProgressColor);
      }
      canvas.drawCircle(cx,
                        cy,
                        dotRadiusPx,
                        progressPaint);
      cx += dotRadiusPx * 2 + dotsPaddingPx;
    }
  }

  public void setProgress(float progress) {
    this.progress = (int) progress * dotsCount / 100;
    invalidate();
  }
}
