package bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-12-30.
 */
public class SearchBean implements Serializable {
    
    private String rain;
    private String water;
    private String timers;


//    String _id = cursor.getString(cursor.getColumnIndex("_id"));
//    String today_rain = cursor.getString(cursor.getColumnIndex("today_rain"));
//    String water = cursor.getString(cursor.getColumnIndex("water"));
//    String month_rain = cursor.getString(cursor.getColumnIndex("month_rain"));
//    String times = cursor.getString(cursor.getColumnIndex("times"));
//    String numRain = cursor.getString(cursor.getColumnIndex("rain_num"));
//mTodayRain.setText(today_rain);
//mMonthRain.setText(month_rain);
//mNowWater.setText(water);
//mNumRain.setText(numRain);

    public String getTimers() {
        return timers;
    }

    public SearchBean setTimers(String timers) {
        this.timers = timers;
        return this;
    }

    public String getWater() {
        return water;
    }

    public SearchBean setWater(String water) {
        this.water = water;
        return this;
    }

    public String getRain() {
        return rain;
    }

    public SearchBean setRain(String rain) {
        this.rain = rain;
        return this;
    }

    @Override
    public String toString() {
        return "SearchBean{" +
                "rain='" + rain + '\'' +
                ", water='" + water + '\'' +
                ", timers=" + timers +
                '}';
    }
}
