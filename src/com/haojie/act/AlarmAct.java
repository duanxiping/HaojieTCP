package com.haojie.act;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.tcpip.model.TCPService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tcpip.model.MessageListener;
import db.Raindb;

public class AlarmAct extends BaseActivity implements OnClickListener,
		MessageListener {

	private TextView mNetConnect;
	private ImageView mAlarmOne;
	private ImageView mAlarmTwo;
	private ImageView mAlarmThree;
	private ImageView mAlarmClear;
	private TextView mTriggerRecord;
	private Raindb raindb = new Raindb(this);
	private SQLiteDatabase db;
	private String tcpIP;
	private String tcpPort;
	private TCPService tcpService = null;

	private String net;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			if (msg.what == 0x123) {
				mNetConnect.setText(BaseActivity.NetWorkConn);
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);

		initView();
		bindService();
		new MyThread().start();
		// connectTPC();
	}

	class MyThread extends Thread {
		@Override
		public void run() {
			// 延迟两秒更新
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			handler.sendEmptyMessage(0x123);
		}
	}

	public void initView() {

		mNetConnect = (TextView) findViewById(R.id.tv_net);
		mAlarmOne = (ImageView) findViewById(R.id.alarm_one);
		mAlarmTwo = (ImageView) findViewById(R.id.alarm_two);
		mAlarmThree = (ImageView) findViewById(R.id.alarm_three);
		mAlarmClear = (ImageView) findViewById(R.id.alarm_clear);
		mTriggerRecord = (TextView) findViewById(R.id.trigger_record);

		mAlarmOne.setOnClickListener(this);
		mAlarmTwo.setOnClickListener(this);
		mAlarmThree.setOnClickListener(this);
		mAlarmClear.setOnClickListener(this);
		mTriggerRecord.setOnClickListener(this);

	}

	// 绑定服务
	private void bindService() {
		Intent intent = new Intent(AlarmAct.this, TCPService.class);
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			tcpService = ((TCPService.LocalBinder) service).getService();
			tcpService.addCallback(AlarmAct.this);
			connectTPC();

		}
	};

	private void connectTPC() {
		db = raindb.getReadableDatabase();

		Cursor cursor = db.query("ip_port_table",
				new String[] { "_i_id,ip,port" }, null, null, null, null, null,
				null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(0); // 获取id
			tcpIP = cursor.getString(1);// 获取IP地址
			tcpPort = cursor.getString(2);// 获取端口号
		}
		Log.e("ip", "===========" + tcpIP);
		Log.e("port", "===========" + tcpPort);

		if (tcpIP != null && tcpPort != null) {

			tcpService.setAddressSport(tcpIP, tcpPort);
		} else {

			Toast.makeText(this, "请设置IP和端口号", Toast.LENGTH_LONG).show();
		}

		cursor.close();
		db.close();
	}

	private int flag;
	private Raindb mRaindb = new Raindb(this);

	@Override
	public void onClick(View v) {
		ContentValues values = new ContentValues();
		db = mRaindb.getWritableDatabase();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日 HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String alarmTimes = formatter.format(curDate);
		switch (v.getId()) {

		case R.id.trigger_record:
			Intent intent = new Intent();
			intent.setClass(this, AlarmHestoryAct.class);
			startActivity(intent);
			break;

		case R.id.alarm_one:

			if (tcpIP != null && tcpPort != null) {

				// 发送指令
				tcpService.doTriggerAlarm(1);
				flag = 1;
				values.put("alarm_time", alarmTimes);
				values.put("alarm_jibie", flag);
				db.insert("alarm_hestory", null, values);
				mAlarmOne.setBackgroundResource(R.drawable.alarem_one);
				mAlarmThree.setBackgroundResource(R.drawable.alarem_no);
				mAlarmTwo.setBackgroundResource(R.drawable.alarem_no);
			} else {

				Toast.makeText(this, "请设置IP和端口号", Toast.LENGTH_LONG).show();
			}

			break;

		case R.id.alarm_two:

			if (tcpIP != null && tcpPort != null) {
				// 发送指令
				tcpService.doTriggerAlarm(2);
				flag = 2;
				values.put("alarm_time", alarmTimes);
				values.put("alarm_jibie", flag);
				db.insert("alarm_hestory", null, values);
				mAlarmTwo.setBackgroundResource(R.drawable.alarem_two);
				mAlarmOne.setBackgroundResource(R.drawable.alarem_no);
				mAlarmThree.setBackgroundResource(R.drawable.alarem_no);
			} else {

				Toast.makeText(this, "请设置IP和端口号", Toast.LENGTH_LONG).show();
			}

			break;

		case R.id.alarm_three:

			if (tcpIP != null && tcpPort != null) {
				// 发送指令
				tcpService.doTriggerAlarm(3);
				flag = 3;
				values.put("alarm_jibie", flag);
				values.put("alarm_time", alarmTimes);
				db.insert("alarm_hestory", null, values);
				mAlarmThree.setBackgroundResource(R.drawable.alarem_three);
				mAlarmTwo.setBackgroundResource(R.drawable.alarem_no);
				mAlarmOne.setBackgroundResource(R.drawable.alarem_no);
			} else {

				Toast.makeText(this, "请设置IP和端口号", Toast.LENGTH_LONG).show();
			}

			break;

		case R.id.alarm_clear:
			tcpService.doTriggerAlarm(0);
			// flag =0;
			mAlarmOne.setBackgroundResource(R.drawable.alarem_no);
			mAlarmThree.setBackgroundResource(R.drawable.alarem_no);
			mAlarmTwo.setBackgroundResource(R.drawable.alarem_no);
			break;
		}

	}

	@Override
	public void platformCallBack(com.tcpip.model.MessageModel model) {
		// TODO Auto-generated method stub

	}
}
