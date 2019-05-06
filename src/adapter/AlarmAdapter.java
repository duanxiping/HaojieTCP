package adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haojie.act.R;

/**
 * Created by Administrator on 2016-12-30.
 */
public class AlarmAdapter extends BaseAdapter {

	private  Context context;
	//创建一个查询数据库的cursors对象
	public Cursor cursors;
    private LayoutInflater  inflater;
    private LinearLayout layout;
    	public AlarmAdapter (Context context, Cursor cursorAlarm ){

    	this.context=context;
		this.cursors=cursorAlarm;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
    	return cursors.getCount();
    }

    @Override
    public Object getItem(int position) {
    	return cursors;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        	LayoutInflater inflater=LayoutInflater.from(context);
        	layout = (LinearLayout) inflater.inflate(R.layout.item_list_alarm,null);

            TextView mAlarm = (TextView) layout.findViewById(R.id.alarm);
            TextView mAlarmTime = (TextView) layout.findViewById(R.id.alarmTime);
         

        //通过cursors对指定的对象进行查询
        cursors.moveToPosition(position);
        String mAlarmHestory = cursors.getString(cursors.getColumnIndex("alarm_jibie"));
        String mAlarmTimeHestory = cursors.getString(cursors.getColumnIndex("alarm_time"));
        
        
        int alarm=Integer.parseInt(mAlarmHestory);
        
        	if(alarm ==1){
			mAlarm.setText("  一级报警");
			
		} else if(alarm ==2){
			mAlarm.setText("  二级报警");
			
		} else if(alarm ==3){
			mAlarm.setText("  三级报警");
			
		}
        mAlarmTime.setText("  "+mAlarmTimeHestory);
        return layout;
    }
}
