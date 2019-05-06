package com.haojie.act;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import db.Raindb;
import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetServerAct extends Activity implements OnClickListener {

	private EditText etPhone;
	private EditText etIp;
	private EditText etPort;
	private EditText etUpdatePhone;
	private EditText etOldPhone;
	private EditText etUpdateIp;
	private EditText etUpdatePort;
	private EditText etUpdateOldIp;
	private EditText etUpdateOldPort;
	private Button btnOkPhone;
	private Button btnOkIpPort;
	private Button btnUpdatePhone;
	private Button btnUpdateIpPort;
	private Button btnUpdateOkPhone;
	private Button btnUpdateOkIpPort;
	private TextView tvShowIP;
	private TextView tvShowPort;
	private TextView tvShowPhone;

	private String phone;
	private String ip;
	private String port;

	Raindb db = new Raindb(this);
	SQLiteDatabase sqlDB;
	ContentValues values;
	
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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone_serverce_set);

		initView();
	}

	private void initView() {

		etPhone = (EditText) findViewById(R.id.et_phone);
		etIp = (EditText) findViewById(R.id.et_ip);
		etPort = (EditText) findViewById(R.id.et_port);
		etUpdatePhone = (EditText) findViewById(R.id.et_update_phone);
		etUpdateIp = (EditText) findViewById(R.id.et_ip_update);
		etUpdatePort = (EditText) findViewById(R.id.et_port_update);
		etOldPhone = (EditText) findViewById(R.id.et_old_phone);
		etUpdateOldIp = (EditText) findViewById(R.id.et_old_ip_update);
		etUpdateOldPort = (EditText) findViewById(R.id.et_old_port_update);
		btnOkPhone = (Button) findViewById(R.id.btn_set_phone);
		btnOkIpPort = (Button) findViewById(R.id.btn_ok_ip_port);
		btnUpdatePhone = (Button) findViewById(R.id.btn_update_phone);
		btnUpdateIpPort = (Button) findViewById(R.id.btn_update_ip_port);
		btnUpdateOkPhone = (Button) findViewById(R.id.btn_update_ok_phone);
		btnUpdateOkIpPort = (Button) findViewById(R.id.btn_update_ok_ip_port);
		tvShowPhone = (TextView) findViewById(R.id.tv_show_phone);
		tvShowIP = (TextView) findViewById(R.id.tv_show_ip);
		tvShowPort = (TextView) findViewById(R.id.tv_show_port);
		

		btnOkPhone.setOnClickListener(this);
		btnOkIpPort.setOnClickListener(this);
		btnUpdatePhone.setOnClickListener(this);
		btnUpdateIpPort.setOnClickListener(this);
		btnUpdateOkPhone.setOnClickListener(this);
		btnUpdateOkIpPort.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_set_phone:
			insetPhoneData();
			break;

		case R.id.btn_ok_ip_port:
			insetIpPortData();
			break;
			
		case R.id.btn_update_phone:
			btnUpdateOkPhone.setVisibility(View.VISIBLE);
			etUpdatePhone.setVisibility(View.VISIBLE);
			etOldPhone.setVisibility(View.VISIBLE);
			tvShowPhone.setVisibility(View.GONE);
			tvShowPhone.setVisibility(View.GONE);
			btnUpdatePhone.setVisibility(View.GONE);
			
			break;
			
		case R.id.btn_update_ok_ip_port:
			updateIpPort();
			break;
			
		case R.id.btn_update_ok_phone:
			updatePhone();
			break;
			
		case R.id.btn_update_ip_port:
			etUpdateIp.setVisibility(View.VISIBLE);
			etUpdateOldIp.setVisibility(View.VISIBLE);
			etUpdatePort.setVisibility(View.VISIBLE);
			etUpdateOldPort.setVisibility(View.VISIBLE);
			btnUpdateOkIpPort.setVisibility(View.VISIBLE);
			tvShowIP.setVisibility(View.GONE);
			tvShowPort.setVisibility(View.GONE);
			btnUpdateIpPort.setVisibility(View.GONE);
			break;
		default:
			break;
		}
		
	}

	//插入电话号码
	private void insetPhoneData() {

		phone = etPhone.getText().toString().trim();
		
		sqlDB = db.getWritableDatabase(); // 取得数据库操作实例
		values = new ContentValues();
		values.put("phone", phone);

		if (!phone.equals("")) {

			sqlDB.insert("phone_tabel", null, values);
			Toast.makeText(this, "电话号码设置成功！", Toast.LENGTH_LONG).show();
			etPhone.setVisibility(View.GONE);
			btnOkPhone.setVisibility(View.GONE);
			tvShowPhone.setVisibility(View.VISIBLE);
			tvShowPhone.setText("电话号码是："+ phone);
			btnUpdatePhone.setVisibility(View.VISIBLE);
		} else {

			Toast.makeText(this, "电话号码不能为空", Toast.LENGTH_LONG).show();
		}
		
		
	}
	
	
	//插入IP和端口
	private void insetIpPortData() {
		
		ip = etIp.getText().toString().trim();
		port = etPort.getText().toString().trim();

		// IP地址格式的限制
		String ipGeshi = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";// 限定输入格式

		Pattern p = Pattern.compile(ipGeshi);
		Matcher m = p.matcher(ip);
		boolean b = m.matches();
		
		sqlDB = db.getWritableDatabase(); // 取得数据库操作实例
		values = new ContentValues();
		values.put("ip", ip);
		values.put("port", port);
		
		if (!ip.equals("") && !port.equals("")) {

			sqlDB.insert("ip_port_table", null, values);
			etIp.setVisibility(View.GONE);
			etPort.setVisibility(View.GONE);
			btnOkIpPort.setVisibility(View.GONE);
			Toast.makeText(this, "服务设置成功！", Toast.LENGTH_LONG).show();
			tvShowIP.setVisibility(View.VISIBLE);
			tvShowIP.setText("服务的IP地址是："+ ip);
			tvShowPort.setVisibility(View.VISIBLE);
			tvShowPort.setText("服务的端口号是："+ port);
			btnUpdateIpPort.setVisibility(View.VISIBLE);
		} else if (b = false) {
			Toast.makeText(this, "IP格式输入错误", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "IP或端口号不能为空", Toast.LENGTH_LONG).show();
		}
	}
	
	private int id;
	private String phoneShow;
	
	private int ids;
	private String ipShow;
	private String portShow;
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		sqlDB = db.getReadableDatabase();
		 Cursor cursor = sqlDB.query("phone_tabel", new String[]{"_p_id,phone"}, null, null, null, null, null, null);
	        while (cursor.moveToNext()) {
	           id = cursor.getInt(0); //获取id
	           phoneShow = cursor.getString(1);//获取电话
	            
	            etPhone.setVisibility(View.GONE);
				btnOkPhone.setVisibility(View.GONE);
				tvShowPhone.setVisibility(View.VISIBLE);
				tvShowPhone.setText("电话号码是："+ phoneShow);
				btnUpdatePhone.setVisibility(View.VISIBLE);
	}
	     
	        Cursor cursor1 = sqlDB.query("ip_port_table", new String[]{"_i_id,ip,port"}, null, null, null, null, null, null);
	        while (cursor1.moveToNext()) {
	            ids = cursor1.getInt(0); //获取id
	            ipShow = cursor1.getString(1);//获取ip
	            portShow = cursor1.getString(2);//获取port
	            
	            etIp.setVisibility(View.GONE);
				etPort.setVisibility(View.GONE);
				btnOkIpPort.setVisibility(View.GONE);
				tvShowIP.setVisibility(View.VISIBLE);
				tvShowIP.setText("服务的IP地址是："+ ipShow);
				tvShowPort.setVisibility(View.VISIBLE);
				tvShowPort.setText("服务的端口号是："+ portShow);
				btnUpdateIpPort.setVisibility(View.VISIBLE);
	}
	}
	
	private String updataPhone;
	private String oldPhone;
	private void updatePhone(){
		
		updataPhone = etUpdatePhone.getText().toString().trim();
		oldPhone = etOldPhone.getText().toString().trim();
		
		sqlDB = db.getWritableDatabase(); // 取得数据库操作实例
		values = new ContentValues();
		values.put("phone", updataPhone);
		
		Log.e("旧号码是=====", phoneShow);
		
		if (!phoneShow.equals(oldPhone)) {
			
			Toast.makeText(this, "输入的旧号码不正确！", Toast.LENGTH_LONG).show();
		}
		
		if (oldPhone.equals("") || updataPhone.equals("")) {
			
			Toast.makeText(this, "请输入旧号码和新号码！", Toast.LENGTH_LONG).show();
		}
			if(phoneShow.equals(oldPhone)){
			
			sqlDB.delete("phone_tabel", null, null);
			sqlDB.insert("phone_tabel", null, values);
			Toast.makeText(this, "电话号码修改成功！", Toast.LENGTH_LONG).show();
			etUpdatePhone.setVisibility(View.GONE);
			etOldPhone.setVisibility(View.GONE);
			btnUpdateOkPhone.setVisibility(View.GONE);
			tvShowPhone.setVisibility(View.VISIBLE);
			tvShowPhone.setText("电话号码是："+ updataPhone);
			btnUpdatePhone.setVisibility(View.VISIBLE);
			
		}
		
		
	}
	
	private String updataIp;
	private String oldIp;
	private String updataPort;
	private String oldPort;
	private void updateIpPort(){
		
		updataIp = etUpdateIp.getText().toString().trim();
		oldIp = etUpdateOldIp.getText().toString().trim();
		updataPort = etUpdatePort.getText().toString().trim();
		oldPort = etUpdateOldPort.getText().toString().trim();
		
		sqlDB = db.getWritableDatabase(); // 取得数据库操作实例
		values = new ContentValues();
		values.put("ip", updataIp);
		values.put("port", updataPort);
		
//		Log.e("旧号码是=====", phoneShow);
		
		if (!ipShow.equals(oldIp) || !portShow.equals(oldPort)) {
			
			Toast.makeText(this, "输入的旧IP或端口号不正确！", Toast.LENGTH_LONG).show();
		}
		
		if (ipShow.equals("") || portShow.equals("") || oldIp.equals("") || oldPort.equals("")) {
			
			Toast.makeText(this, "内容不能为空，请输入！", Toast.LENGTH_LONG).show();
		}
			if(ipShow.equals(oldIp) && portShow.equals(oldPort)){
			
			sqlDB.delete("ip_port_table", null, null);
			sqlDB.insert("ip_port_table", null, values);
			Toast.makeText(this, "IP端口号修改成功！", Toast.LENGTH_LONG).show();
			etUpdateIp.setVisibility(View.GONE);
			etUpdateOldIp.setVisibility(View.GONE);
			etUpdatePort.setVisibility(View.GONE);
			etUpdateOldPort.setVisibility(View.GONE);
			btnUpdateOkIpPort.setVisibility(View.GONE);
			tvShowIP.setVisibility(View.VISIBLE);
			tvShowPort.setVisibility(View.VISIBLE);
			tvShowIP.setText("IP地址是："+ updataIp);
			tvShowPort.setText("端口号是："+ updataPort);
			btnUpdateIpPort.setVisibility(View.VISIBLE);
			
		}
		
		
	}
	

}
