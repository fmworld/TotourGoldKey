package com.fm.fmlib.utils;
import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class StringUtils {
	
	public static String md5(String string) {
		if(null == string){
			return null;
		}

	    byte[] hash;
	    try {
	        hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException("Huh, MD5 should be supported?", e);
	    } catch (UnsupportedEncodingException e) {
	        throw new RuntimeException("Huh, UTF-8 should be supported?", e);
	    }

	    StringBuilder hex = new StringBuilder(hash.length * 2);
	    for (byte b : hash) {
	        if ((b & 0xFF) < 0x10) hex.append("0");
	        hex.append(Integer.toHexString(b & 0xFF));
	    }
	    return hex.toString();
	}
	
	private final static double EARTH_RADIUS = 6378137.0;  
	/**
	 * 计算两个经纬度之间的距离
	 * @param lat_a
	 * @param lng_a
	 * @param lat_b  target
	 * @param lng_b  target
	 * @return
	 */
	public static double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
		double s =-1;
		if(lat_b<=0 && lng_b<=0){
			return s;
		}
		try{
			double radLat1 = (lat_a * Math.PI / 180.0);
			double radLat2 = (lat_b * Math.PI / 180.0);
			double a = radLat1 - radLat2;
			double b = (lng_a - lng_b) * Math.PI / 180.0;
			s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
					+ Math.cos(radLat1) * Math.cos(radLat2)
					* Math.pow(Math.sin(b / 2), 2)));
			s = s * EARTH_RADIUS;
			s = Math.round(s * 10000) / 10000;
		}catch(Exception e){

		}

		return s;
	}
	
	public static String timestamp2DateTime(String timestamp){
		if(timestamp == null || "".equals(timestamp) || "0".equals(timestamp)){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(new Date(str2Long(timestamp)*1000));
	}
	
	public static String timestamp2Date(String timestamp){
		if(timestamp == null || "".equals(timestamp) || "0".equals(timestamp)){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date(str2Long(timestamp)*1000));
	}
	
	public static String getAssestFile2Str(Context context, String filePath){
		try {
			InputStream mInputStream = context.getApplicationContext().getAssets().open(filePath);
			BufferedReader mBufferedReader = new BufferedReader(new InputStreamReader(mInputStream,"GBK"));
			StringBuffer mStringBuffer = new StringBuffer();
			String line = "";
			String nl = System.getProperty("line.separator");
			while((line = mBufferedReader.readLine()) != null){
//				Logger.systemOut("line->"+line);
				mStringBuffer.append(line + nl);
			}
			mBufferedReader.close();
			return mStringBuffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getStr(String str){
		return (str == null ? "" : str);
	}
	
	public static int str2Int(String str){
		try {
			return Integer.valueOf(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static Float str2Float(String str){
		try {
			return Float.valueOf(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0f;
	}
	
	public static long str2Long(String str){
		try {
			return Long.valueOf(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
