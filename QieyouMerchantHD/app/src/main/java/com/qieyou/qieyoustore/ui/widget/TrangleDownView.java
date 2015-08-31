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
public class TrangleDownView extends View {
    private Paint mPaint;
    private int width;
    private int height;
    public TrangleDownView(Context context) {
        super(context);
        intitView();
    }

    public TrangleDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        intitView();
    }

    public TrangleDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intitView();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        width = w;
        height =h;
    }

    private void intitView(){
        mPaint = new Paint();
        mPaint.setColor(this.getResources().getColor(R.color.green));
        mPaint.setStrokeWidth(DisplayUtil.dip2px(this.getContext(),1));
        mPaint.setAntiAlias(true);
    }

    public void onDraw(Canvas canvas){
        canvas.drawLine(0,0,width / 2, height,mPaint);
        canvas.drawLine(width / 2, height,width, 0,mPaint);
    }
}
