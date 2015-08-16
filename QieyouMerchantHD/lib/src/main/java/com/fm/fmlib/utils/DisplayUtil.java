package com.fm.fmlib.utils;

import android.content.Context;

public class DisplayUtil {
	private float scale = 0.87f;
	private final static float standardWidth = 640.0f;
	private final static float standardHeight = 1136.0f;
	
	

	public static float getDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}
	
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}


	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}


	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static float getDisplayProportion(Context context){
		float widthProportion = getScreenWidth(context)/standardWidth;
		float heightProportion = getScreenHeight(context)/standardHeight;
		return (widthProportion + heightProportion)/2;
	}
	
	public static int getSize(Context context,int size){
		float proportion = DisplayUtil.getDisplayProportion(context);
//		float proportion = 1;
		return (int)(size * proportion);
	}
}