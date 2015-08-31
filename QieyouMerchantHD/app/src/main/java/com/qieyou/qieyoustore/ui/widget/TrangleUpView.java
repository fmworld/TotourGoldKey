package com.qieyou.qieyoustore.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.fm.fmlib.utils.DisplayUtil;
import com.qieyou.qieyoustore.R;

/**
 * Created by zhoufeng'an on 2015/8/31.
 */
public class TrangleUpView extends View {
    private Paint mPaint;
    private Path mPath;
    private int width;
    private int height;
    public TrangleUpView(Context context) {
        super(context);
        intitView();
    }

    public TrangleUpView(Context context, AttributeSet attrs) {
        super(context, attrs);
        intitView();
    }

    public TrangleUpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intitView();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        width = w;
        height =h;
        mPath.moveTo(0,height);
        mPath.lineTo(width / 2, 0);
        mPath.lineTo(width, height);
    }

    private void intitView(){
        mPaint = new Paint();
        mPath = new Path();
        mPaint.setColor(this.getResources().getColor(R.color.black_bg));
        mPaint.setStrokeWidth(1);
        mPaint.setAntiAlias(true);
    }

    public void onDraw(Canvas canvas){
        canvas.drawPath(mPath, mPaint);
    }
}
