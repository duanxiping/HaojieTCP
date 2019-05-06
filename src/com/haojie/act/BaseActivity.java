package com.haojie.act;

import util.NetWorkUtils;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class BaseActivity extends Activity {

	public static int isNetWorkConnected;
	public static String NetWorkConn;
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			NetWorkUtils netType = new NetWorkUtils();
			if (msg.what == 0x123) {
				if (isNetWorkConnected == 1) {
					
					NetWorkConn = "网络正常";
				}else if (NetWorkUtils.netType == 0) {
					NetWorkConn = "网络异常";
				}
			}
		}

	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		new MyThread().start();
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
}
