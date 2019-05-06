package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016-12-30.
 */
public class Raindb extends SQLiteOpenHelper {

    public static final String TABLE_NAME_RAIN_WATER= "rain_water";//雨量水位表
    public static final String TABLE_NAME_ALARM_HESTORY= "alarm_hestory";//报警记录表
    public static final String ID = "_id";
    public static final String ID_ALARM = "_id_alarm";
    public static final String TODAY_RAIN = "today_rain";
    public static final String ALARM_RAIN = "alarm_rain";
    public static final String ALARM_WATER = "alarm_water";
    public static final String ALARM_JIBIE = "alarm_jibie";
    public static final String ALARM_TIME = "alarm_time";
    public static final String WATER = "water";
    public static final String MONTH_RAIN = "month_rain";
//    public static final String NUM_RAIN = "rain_num";
    public static final String TIMES_YEAR = "year";
    public static final String TIMES_MONTH = "month";
    public static final String TIMES_DAY = "day";
    public static final String TIMES_HOUR = "hour";
    public static final String TIMES_SECOND = "second";
    public static final String TIMES_MIN = "min";
//    public static final String TIMES_RAIN = "time_rain";
    public static final String TABLE_NAME_PHONE= "phone_tabel";//打电话设置表
    public static final String TABLE_NAME_IP_PORT= "ip_port_table";//IP和端口号设置表
    public static final String _P_ID = "_p_id";
    public static final String _I_ID = "_i_id";
    public static final String PHONE_NAME = "phone";
    public static final String IP_NAME = "ip";
    public static final String PORT_NAME = "port";

    public Raindb(Context context) {
        super(context, "dxp", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+TABLE_NAME_RAIN_WATER+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+TODAY_RAIN+" TEXT,"+MONTH_RAIN+" TEXT,"+WATER+" TEXT,"+ALARM_RAIN+" TEXT,"+ALARM_WATER+" TEXT,"+TIMES_YEAR+" TEXT,"+TIMES_MONTH+" TEXT,"+TIMES_DAY+" TEXT,"+TIMES_HOUR+" TEXT,"+TIMES_SECOND+" TEXT,"+TIMES_MIN+" TEXT)");
        db.execSQL("CREATE TABLE "+TABLE_NAME_ALARM_HESTORY+" ("+ID_ALARM+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ALARM_JIBIE+" TEXT, "+ALARM_TIME+" TEXT)");
        db.execSQL("CREATE TABLE "+TABLE_NAME_PHONE+" ("+_P_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+PHONE_NAME+" TEXT)");
        db.execSQL("CREATE TABLE "+TABLE_NAME_IP_PORT+" ("+_I_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+IP_NAME+" TEXT, "+PORT_NAME+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
