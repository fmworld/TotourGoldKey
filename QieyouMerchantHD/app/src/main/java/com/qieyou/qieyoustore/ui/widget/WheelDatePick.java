package com.qieyou.qieyoustore.ui.widget;

import android.content.Context;
import android.view.View;

import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.wheel.ArrayWheelAdapter;
import com.qieyou.qieyoustore.ui.widget.wheel.OnWheelChangedListener;
import com.qieyou.qieyoustore.ui.widget.wheel.WheelView;
import com.qieyou.qieyoustore.util.DateUtil;

import java.util.Date;

/**
 * Created by zhoufeng'an on 2015/8/17.
 */
public class WheelDatePick implements View.OnClickListener {
    private View content;
    private WheelView wheelYear;
    private WheelView wheelMonth;
    private WheelView wheelDay;
    private WheelView wheelHour;
    private WheelView wheelmin;
    private View hourLayout;
    private View minLayout;
    private Context mContext;
    private String[] months;
    private String[] days;
    private String[] years;
    private String[] hours;
    private String[] mins;
    private int year_value;
    private int month_value;
    private int day_value;
    private int hour_value;
    private int min_value;
    private WheelDatePickClickListener clickListener;

    public interface WheelDatePickClickListener {
        void navigationClicked();

        void positiveClicked(Date date);
    }

    public WheelDatePick(Context context) {
        content = View.inflate(context, R.layout.widget_wheel_date_layout, null);
        content.findViewById(R.id.wheel_date_cancel).setOnClickListener(this);
        content.findViewById(R.id.wheel_date_confirm).setOnClickListener(this);
        content.setOnClickListener(this);
        wheelYear = (WheelView) content.findViewById(R.id.wheel_date_year);
        wheelMonth = (WheelView) content.findViewById(R.id.wheel_date_month);
        wheelDay = (WheelView) content.findViewById(R.id.wheel_date_day);
        wheelHour = (WheelView) content.findViewById(R.id.wheel_date_hour);
        wheelmin = (WheelView) content.findViewById(R.id.wheel_date_mins);
        hourLayout = content.findViewById(R.id.wheel_date_hour_layout);
        minLayout = content.findViewById(R.id.wheel_date_min_layout);
        mContext = context;
        initView();
    }

    public void setTimeStyle(boolean showTimeStyle) {
        if (showTimeStyle) {
            hourLayout.setVisibility(View.VISIBLE);
            minLayout.setVisibility(View.VISIBLE);
            initTimeView();
        } else {
            hourLayout.setVisibility(View.GONE);
            minLayout.setVisibility(View.GONE);
            hours = null;
            mins = null;
        }
    }

    public void initView() {
        initDateView();
        initTimeView();
    }

    public void initDateView() {

        initYearView(2015, 2035);
        months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        ArrayWheelAdapter monthAdapter = new ArrayWheelAdapter<String>(mContext, months);
        wheelMonth.setViewAdapter(monthAdapter);
        month_value = months.length / 2;
        wheelMonth.setCurrentItem(month_value);

        wheelMonth.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                month_value = newValue;
            }
        });
        days = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15"
                , "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        ArrayWheelAdapter dayAdapter = new ArrayWheelAdapter<String>(mContext, days);
        wheelDay.setViewAdapter(dayAdapter);
        day_value =days.length / 2;
        wheelDay.setCurrentItem(day_value);
        wheelDay.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                day_value = newValue;
            }
        });
    }

    public void initYearView(int start, int end) {
        if (start < end) {
            years = new String[end - start];
            for (int i = 0; start + i < end; i++) {
                years[i] = new String(String.valueOf(start + i));
            }
            ArrayWheelAdapter yearAdapter = new ArrayWheelAdapter<String>(mContext, years);
            wheelYear.setViewAdapter(yearAdapter);
            year_value =years.length / 2;
            wheelYear.setCurrentItem(year_value);
            wheelYear.addChangingListener(new OnWheelChangedListener() {
                @Override
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    year_value = newValue;
                }
            });
        }
    }


    public void initTimeView() {
        hours = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17"
                , "18", "19", "20", "21", "22", "23"};
        ArrayWheelAdapter hourAdapter = new ArrayWheelAdapter<String>(mContext, hours);
        wheelHour.setViewAdapter(hourAdapter);
        hour_value = hours.length / 2;
        wheelHour.setCurrentItem(hour_value);
        wheelHour.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                hour_value = newValue;
            }
        });
        mins = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15"
                , "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32"
                , "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49"
                , "40", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
        ArrayWheelAdapter minAdapter = new ArrayWheelAdapter<String>(mContext, mins);
        wheelmin.setViewAdapter(minAdapter);
        min_value = mins.length / 2;
        wheelmin.setCurrentItem(min_value);
        wheelmin.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                min_value = newValue;
            }
        });
    }

    public View getContent() {
        return content;
    }

    public WheelDatePickClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(WheelDatePickClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        if (R.id.wheel_date_cancel == v.getId()) {
            if(null != clickListener){
                clickListener.navigationClicked();
            }
        }else if(content == v){

        } else {
            if(null != clickListener){
                clickListener.positiveClicked(DateUtil.getDate(initDateString()));
            }
        }
    }
    private String initDateString(){
        StringBuffer sb = new StringBuffer();
        if(null != years&&null != months&&null != days){
            sb.append(years[year_value]).append("-");
            sb.append(months[month_value]).append("-");
            sb.append(days[day_value]).append(" ");
        }

        if(null != hours && null != mins){
            sb.append(hours[hour_value]).append(":");
            sb.append(mins[min_value]);
            sb.append(":00");
        }else{
            sb.append("00:00:00");
        }


        return sb.toString();
    }
}
