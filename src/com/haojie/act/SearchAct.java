package com.haojie.act;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.ComponentName;
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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.tcpip.model.MessageListener;
import com.tcpip.model.MessageModel;
import com.tcpip.model.TCPService;

import db.Raindb;

public class SearchAct extends Activity implements View.OnClickListener, MessageListener{

	 private ImageView mBack;

	    private TextView mTodayRain;
	    private TextView mMonthRain;
	    private TextView mNumRain;
	    private TextView mNowWater;
	    private TextView mAlarmWater;
	    private TextView mAlarmRain;

	    private Raindb raindb = new Raindb(this);
	    private SQLiteDatabase db;
	    private String tcpIP;
	    private String tcpPort;
	    private TCPService tcpService = null;

	    //回调接口的集合
		 
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_search);
	        //默认是隐藏软键盘
	        getWindow().setSoftInputMode(   WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

	        initView();
	        bindService();
	    }
	    
	    private void initView() {

	        mBack = (ImageView) findViewById(R.id.back);

	        mBack.setOnClickListener(this);

	        mTodayRain = (TextView) findViewById(R.id.today_rain);
	        mMonthRain = (TextView) findViewById(R.id.mouth_rain);
	        mNumRain = (TextView) findViewById(R.id.rain_num);
	        mNowWater = (TextView) findViewById(R.id.today_water);
	        mAlarmWater = (TextView) findViewById(R.id.alarmWater);
	        mAlarmRain = (TextView) findViewById(R.id.alarmRain);

	    }
	    
	  //绑定服务
	    private void bindService(){
	        Intent intent = new Intent(SearchAct.this, TCPService.class);
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
	        	tcpService.addCallback(SearchAct.this);
	        	connectTPC();
	        	
	        }
	    };
	    
	    private void connectTPC(){
	        db = raindb.getReadableDatabase();

	        Cursor cursor = db.query("ip_port_table", new String[]{"_i_id,ip,port"}, null, null, null, null, null, null);
	        while (cursor.moveToNext()) {
	            int id = cursor.getInt(0); //获取id
	            tcpIP = cursor.getString(1);//获取IP地址
	            tcpPort = cursor.getString(2);//获取端口号
			}
	        Log.e("ip","==========="+ tcpIP);
	        Log.e("port","==========="+ tcpPort);
	        
	        if (tcpIP != null && tcpPort != null) {
				
				tcpService.setAddressSport(tcpIP, tcpPort);
			}else{
				
				Toast.makeText(this, "请设置IP和端口号", Toast.LENGTH_LONG).show();
			}
			
	        cursor.close();
	        db.close();
    }
	    
	    Handler handler = new Handler()
	    {
	        @Override
	        public void handleMessage(Message msg) {
	            super.handleMessage(msg);

	           if (msg.what == 100) {
	        	   
	        	   String receve=msg.obj.toString();
	        	   String[] receveDatabase=receve.split("\\,");
	        	   String  TodayRainFall =receveDatabase[0];
	        	   String  MonthRainFall =receveDatabase[1];
	        	   String  SessionRainFall =receveDatabase[2];
	        	   String  CurrentWater =receveDatabase[3];
	        	   String  	WaterAlarmlevel =receveDatabase[4];
	        	   String  	RainAlarmlevel =receveDatabase[5];
	        	   
//	        	   Toast.makeText(SearchAct.this, receve, Toast.LENGTH_LONG).show();
	        	   
					mTodayRain.setText(TodayRainFall);
	       		  	mMonthRain.setText(MonthRainFall);
	       		  	mNumRain.setText(SessionRainFall);
	       		  	mNowWater.setText(CurrentWater);
	       		  	mAlarmRain.setText(RainAlarmlevel);
	       		  	mAlarmWater.setText(WaterAlarmlevel);
	           } 
	        }

	    };

//	  @Override
//	   public void MCUCallBack(MessageModel model) {
//           
//           String TodayRainFall =model.getsTodayRainFall();
//           String MonthRainFall =model.getsMonthRainFall();
//           String SessionRainFall =model.getsSessionRainFall();
//           String CurrentWater =model.getiCurrentWater();
//           String mDtatabase =TodayRainFall+","+MonthRainFall+","+SessionRainFall+","+CurrentWater;
//           
//		  handler.sendMessage(handler.obtainMessage(100, mDtatabase));
//	    }
	 
	    @Override
	    public void onClick(View v) {

	        switch (v.getId()){

	            case R.id.back:
	                finish();
	                break;
	        }

	    }
	    

		@Override
		protected void onResume() {
			super.onResume();
		}

		@Override
		public void platformCallBack(MessageModel model) {
			
			String dd =model.getsTodayRainFall();
			float M=(float) (Float.parseFloat(dd)*0.1);
			
			DecimalFormat fnum = new DecimalFormat("##0.0");
			String TodayRainFall=fnum.format(M);
			
           String MonthRainFall =model.getsMonthRainFall();
           
           String ddd =model.getsSessionRainFall();
           float Mm=(float) (Float.parseFloat(ddd)*0.1);
			String SessionRainFall=fnum.format(Mm);
           
           String CurrentWater =model.getsWaterLevel();
           int RainFallAlarmlevel =model.getsRainFallAlarmlevel();
           int WaterLevelAlarmlevel =model.getsWaterLevelAlarmlevel();
           
           String RainAlarm = null;
           String Alarmlevel = null;
           
           if (RainFallAlarmlevel == 1) {
        	   RainAlarm = "一级报警";
		}else if (RainFallAlarmlevel== 2) {
			RainAlarm = "二级报警";
		}else if (RainFallAlarmlevel== 3) {
			RainAlarm = "三级报警";
		}else if (RainFallAlarmlevel== 0) {
			RainAlarm = "暂无报警";
		}else {
			RainAlarm = "数据异常";
		}
    	   
           if (WaterLevelAlarmlevel == 1) {
        	   Alarmlevel = "一级报警";
		}else if (WaterLevelAlarmlevel== 2) {
			Alarmlevel = "二级报警";
		}else if (WaterLevelAlarmlevel== 3) {
			Alarmlevel = "三级报警";
		}else if (WaterLevelAlarmlevel== 0) {
			Alarmlevel = "暂无报警";
		}else {
			Alarmlevel = "数据异常";
		}
           
           
           String mDtatabase =  TodayRainFall+","
        		   				+MonthRainFall+","
        		   				+SessionRainFall+","
        		   				+CurrentWater+","
        		   				+Alarmlevel+","
        		   				+RainAlarm;
           
		  handler.sendMessage(handler.obtainMessage(100, mDtatabase));
			
		}

//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		if (tcpService != null ) {
//			tcpService.unbindService(null);
//		}
//	}

}
