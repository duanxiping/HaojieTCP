package com.haojie.act;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import db.Raindb;

public class MainAct extends Activity implements OnClickListener {

    private RelativeLayout mAlarm;
    private RelativeLayout mSearch;
    private RelativeLayout mMusic;
    private RelativeLayout mFinger;
    private RelativeLayout mRecoderTrack;
    private ImageButton mSound;
    private TextView mSpeek;
    private TextView mComd;

    private Raindb raindb = new Raindb(this);
    private SQLiteDatabase db;
    private String mPhone;
    
    @Override
    protected void onResume() {
    	
     /**
      * 设置为横屏
      */
     if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
     }
     super.onResume();
     
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mains);

        initView();
//        LogConfig.initLog();
//        log = Logger.getLogger(MainAct.class);
    }

    private void initView() {

        mRecoderTrack = (RelativeLayout) findViewById(R.id.soundlayout);
        mSound = (ImageButton) findViewById(R.id.sound);
        mSpeek = (TextView) findViewById(R.id.speek);
        mComd = (TextView) findViewById(R.id.tv_comd);
        mAlarm = (RelativeLayout) findViewById(R.id.alarmlayout);
        mSearch = (RelativeLayout) findViewById(R.id.searchlayout);
        mMusic = (RelativeLayout) findViewById(R.id.musiclayout);
        mFinger = (RelativeLayout) findViewById(R.id.fingerslayout);

        mRecoderTrack.setOnClickListener(this);
        mAlarm.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mMusic.setOnClickListener(this);
        mFinger.setOnClickListener(this);
        mComd.setOnClickListener(this);
        mSound.setOnClickListener(this);
        
        db = raindb.getReadableDatabase();
        Cursor cursor = db.query("phone_tabel", new String[]{"_p_id,phone"}, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0); //获取id
            mPhone = cursor.getString(1);//获取电话号码
		}
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {

            //报警
            case R.id.alarmlayout:
                intent.setClass(this, AlarmAct.class);
                startActivity(intent);
                break;

            //查询
            case R.id.searchlayout:
                intent.setClass(this, SearchAct.class);
                startActivity(intent);
                break;

            //服务设置
            case R.id.musiclayout:
                intent.setClass(this, SetServerAct.class);
                startActivity(intent);
                break;

            //指纹设置
            case R.id.fingerslayout:
                Intent intent1 = new Intent(Settings.ACTION_SETTINGS);
                ComponentName cName = new ComponentName("com.android.phone", "com.android.phone.Settings");
                intent.setComponent(cName);
                startActivity(intent1);
                break;
                
                //拨打电话
            case R.id.sound:
            	callPhone();
                break;
        }
    }
    
    private void callPhone(){
    	
    	 if (mPhone == null) {
         	Toast.makeText(this, "电话号码未设置，请到服务设置里设置!", Toast.LENGTH_LONG).show();
         	return;
 		}
    	
    	TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);  
        String simSer = tm.getSimSerialNumber();  
        if(simSer == null || simSer.equals("")) {  
            //Toast.makeText(this, "插入SIM卡才能开启此应用", Toast.LENGTH_LONG).show();  
            Builder builder = new AlertDialog.Builder(this);  
            builder.setIcon(R.drawable.datasync);  
            builder.setTitle("插入SIM卡才能开启此应用");  
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {  
                @Override  
                public void onClick(DialogInterface dialog, int which) {  
//                    System.exit(0);  
                }  
            });  
            builder.show();  
        }  else {
        	
			//用intent启动拨打电话  
            Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+mPhone));  
            startActivity(intent); 
		}
	       
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    protected void onStart() {
        super.onStart();
//        bindService();
    }

    @Override
    protected void onStop() {
        super.onStop();


    }
}
