package com.fm.fmlib.utils;

import android.text.Selection;
import android.text.Spannable;
import android.util.Log;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtil {

	/**
	 * 检查日期格式
	 *
	 * @param date
	 * @return
	 */
	public static boolean checkDate(String date) {
		String eL = "^((//d{2}(([02468][048])|([13579][26]))[//-/////s]?((((0?[13578])|(1 [02]))[//-/////s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[//-/////s]?((0?[1-9])|([1-2] [0-9])|(30)))|(0?2[//-/////s]?((0?[1-9])|([1-2][0-9])))))|(//d{2}(([02468][1235679])|([13579][01345789]))[//-/////s]?((((0?[13578])|(1[02]))[//-/////s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0? [469])|(11))[//-/////s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[//-/////s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(//s(((0?[0-9])|([1][0-9])|([2][0-3]))//:([0-5]?[0-9])((//s)|(//:([0-5]?[0-9])))))?$";
				Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(date);
		boolean b = m.matches();
		return b;
	}

	/**
	 * 检查整数
	 *
	 * @param num
	 * @param type
	 *            "0+":非负整数 "+":正整数 "-0":非正整数 "-":负整数 "":整数
	 * @return
	 */
	public static boolean checkNumber(String num, String type) {
		String eL = "";
		if (type.equals("0+")) {
			eL = "^//d+$";// 非负整数
		}else if (type.equals("+")) {
			eL = "^//d*[1-9]//d*$";// 正整数
		}else if (type.equals("-0")) {
			eL = "^((-//d+)|(0+))$";// 非正整数
		}else if (type.equals("-")) {
			eL = "^-//d*[1-9]//d*$";// 负整数
		}else {
			eL = "^-?//d+$";// 整数
		}
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(num);
		boolean b = m.matches();
		return b;
	}

	/**
	 * 检查浮点数
	 *
	 * @param num
	 * @param type
	 *            "0+":非负浮点数 "+":正浮点数 "-0":非正浮点数 "-":负浮点数 "":浮点数
	 * @return
	 */
	public static boolean checkFloat(String num, String type) {
		String eL = "";
		if (type.equals("0+"))
			eL = "^//d+(//.//d+)?$";// 非负浮点数
		else if (type.equals("+"))
			eL = "^((//d+//.//d*[1-9]//d*)|(//d*[1-9]//d*//.//d+)|(//d*[1-9]//d*))$";// 	正浮点数

		else if (type.equals("-0"))
			eL = "^((-//d+(//.//d+)?)|(0+(//.0+)?))$";// 非正浮点数
		else if (type.equals("-"))
			eL = "^(-((//d+//.//d*[1-9]//d*)|(//d*[1-9]//d*//.//d+)|(//d*[1-9]//d*)))$";// 负浮点数
		else
		eL = "^(-?//d+)(//.//d+)?$";// 浮点数
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(num);
		boolean b = m.matches();
		return b;
	}

	/**
	 * 检查浮点数
	 *
	 * @param num
	 *  type
	 *            "0+":非负浮点数 "+":正浮点数 "-0":非正浮点数 "-":负浮点数 "":浮点数
	 * @return
	 */
	public static boolean checkPrice(String num) {
		// 检查输入时，默认可以不输入或输入空格，但是不能输入错误
		if ("".equals(num.trim()))
			return true;

		String eL = "^[0-9]+(.[0-9]{1,3})?$";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(num);
		boolean b = m.matches();
		if (b) {
			return true;
		}

		eL = "^\\+?[1-9][0-9]*$";
		p = Pattern.compile(eL);
		m = p.matcher(num);
		b = m.matches();
		return b;
	}

	/**
	 *
	 * 判定输入汉字
	 *
	 *
	 *
	 * @param c
	 *
	 * @return
	 */

	public boolean isChinese(char c) {

		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {

			return true;

		}

		return false;

	}

	/**
	 * 检查中文，数字， 字母（车牌号码）
	 *
	 * @param num
	 *
	 *
	 * @return
	 */
	public static boolean checkCharacter(CharSequence num) {
		if ("".equals(num) || null == num)
			return false;
		String eL = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w]{1,7}$";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(num);
		Log.v("aiaiai", "m.matches()  " + m.matches());

		return m.matches();
	}

	/**
	 * 设置光标位置
	 *
	 *
	 * @return
	 */
	public static void setFocusIndex(EditText editText, int index) {
		CharSequence text = editText.getText();
		// Debug.asserts(text instanceof Spannable);
		if (text instanceof Spannable) {
			Spannable spanText = (Spannable) text;
			if (0 <= index && index <= text.length()) {
				Selection.setSelection(spanText, index);
			}
		}
	}

	/**
	 * 是否是手机
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNum(String mobiles)
	{
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 判断长度是否足够
	 * @param mobiles
	 * @param length
	 * @return
	 */
	public static boolean isLengthEnough(String mobiles, int length)
	{
		if (mobiles.length() >= length)
		{
			return true;
		}
		return false;
	}

	/**
	 * 判断长度是否超出
	 * @param mobiles
	 * @param length
	 * @return
	 */
	public static boolean isOutOfLength(String mobiles, int length)
	{
		if (mobiles.length() > length)
		{
			return true;
		}
		return false;
	}

	/**
	 * 有数字和字母组成，必需包含数字和字母
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean checkLetterNumber(String value, int min, int max){

		Pattern p = Pattern.compile("^(?![^a-zA-Z]+$)(?!\\D+$).{"+min+","+max+"}$");
		Matcher m = p.matcher(value);
		return m.matches();
	}

	/**
	 *
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean checkLetterNumberSpecial(String value, int min, int max){

		Pattern p = Pattern.compile("^(?![^a-zA-Z]+$)(?!\\D+$)(?![`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]).{"+min+","+max+"}$");
		Matcher m = p.matcher(value);
		return m.matches();
	}

}
