package com.haojie.act;


import db.Raindb;
import adapter.AlarmAdapter;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class AlarmHestoryAct extends Activity {
	
	private ListView mListView;
	private ImageView mback;
	private Raindb mRaindb = new Raindb(this);
	private SQLiteDatabase sqLiteDatabase;
	private AlarmAdapter alarmAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_hestory);
		
		mListView =(ListView) findViewById(R.id.alarmList);
		mback =(ImageView) findViewById(R.id.back);
		mback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					finish();
			}
		});
		selectDB();
	}
	
	//创建查询数据库的方法
  	public void selectDB(){
  		//执行查询
  		sqLiteDatabase = mRaindb.getReadableDatabase();
  		Cursor cursorAlarm=sqLiteDatabase.query(Raindb.TABLE_NAME_ALARM_HESTORY, null, null, null, null, null, null);
  		alarmAdapter = new AlarmAdapter(this, cursorAlarm);
  		mListView.setAdapter(alarmAdapter);
  	}


	@Override
	protected void onResume() {
		super.onResume();
		selectDB();
		   /**
	      * 设置为横屏
	      */
	     if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
	      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	     }
	}

}
