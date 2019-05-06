package com.tcpip.model;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import junit.framework.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TCPService extends Service{

	//回调接口的集合
		private List<MessageListener> listenerList = null;

		private TCPBinder tcpBiner = null;
		
		private String sIpAddress = null;
		
		private String sPort = null;
		
		private Socket socket = null;
		
		private InputStream is = null;
		
		private OutputStream os = null;
		
		private String IMEI = null;
		
		private WifiInfo wifiInfo = null;
		
		private int mBatteryVolt = 0;
		
		@Override
		public IBinder onBind(Intent intent) {
			return new LocalBinder();
		}
		
		public void setAddressSport(String sIpAddress,String sPort){
			this.sIpAddress = sIpAddress;
			this.sPort = sPort;
			
			startReceiver();
		}

		public void doRegister(){
			String cmd = IContent.COMMON_LEN+IContent.TYPE_CMD1+IMEI+IContent.SEPARATOR+IContent.REGISTER_OK+IContent.OVER;
			sendCMD(cmd);
		}
		
		public void doTriggerAlarm(int iSwitch){
			String cmd = IContent.TRIGGER_ALARM_LEN+IContent.TYPE_CMD5+IMEI+IContent.SEPARATOR+IContent.TRIGGER_ALARM_STR+iSwitch+IContent.OVER;
			sendCMD(cmd);
		}
		
		public void doQueryDate(){
			String cmd = IContent.QUERY_DATA_LEN+IContent.TYPE_CMD5+IMEI+IContent.SEPARATOR+IContent.QUERY_DATA_STR+IContent.OVER;
			sendCMD(cmd);
		}
		
		
		@Override
		public void onCreate() {
			super.onCreate();
			
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);  
			IMEI = telephonyManager.getDeviceId();
			
			WifiManager wifi_service = (WifiManager)getSystemService(WIFI_SERVICE); 
			wifiInfo = wifi_service.getConnectionInfo();
			
			//初始化回调接口集合
			listenerList = new ArrayList<MessageListener>();

			//初始化MCUBinder
			tcpBiner = new TCPBinder();
			tcpBiner.setMessageListener(new CallBack());
			
			IntentFilter filter = new IntentFilter();
			filter.addAction(Intent.ACTION_BATTERY_CHANGED);
			registerReceiver(mBatteryReceiver,filter);
		}
		
		private BroadcastReceiver mBatteryReceiver = new BroadcastReceiver(){  
	        @Override  
	        public void onReceive(Context context, Intent intent) {  
	            String action = intent.getAction();  
	            if(Intent.ACTION_BATTERY_CHANGED.equals(action)){  
	            	
	            	//电池电压  
	            	int volt = intent.getIntExtra("voltage", 0);  
	            	
	            	//电池温度  
	                int temperature = intent.getIntExtra("temperature", 0);  
	                
	            	//level表示当前电量的值
	                int level = intent.getIntExtra("level", 0);
	                
	                //scale表示电量的总刻度
	                int scale = intent.getIntExtra("scale", 100);
	                
	                mBatteryVolt = volt;
	                
	                //将当前的电量换算成百分比的形式
	                Toast.makeText(TCPService.this, "volt:"+volt+"\n电池用量："+(level*100/scale)+"%", 200);  
	            }  
	        }  
	          
	    };  
		

		public void addCallback(MessageListener callback) {
			listenerList.add(callback);
		}

		ByteArrayOutputStream bufferOS = new ByteArrayOutputStream();
		
		public void startReceiver(){
			
			new Thread(new Runnable(){

				@Override
				public void run() {
					
					try {
						InetAddress ipAddress = InetAddress.getByName(sIpAddress);
						int port = Integer.valueOf(sPort);
						socket = new Socket(ipAddress, port);
						is = socket.getInputStream();
						os = socket.getOutputStream();
						//注册
						doRegister();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					byte[] buffer = new byte[512];
					
					while(true){
						try {
							int len = is.read(buffer);
							if(len>0){
								bufferOS.write(buffer);
								String bs = bufferOS.toString();
								if(bs.contains(IContent.OVER)){
									
									tcpBiner.analysis(bs);
									
									bufferOS.reset();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			}).start();
		}
		
		//发送命令
		public void sendCMD(String cmd){
			if(os!=null){
				try {
					os.write(cmd.getBytes());
					os.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}


		public final class LocalBinder extends Binder {
			public TCPService getService() {
				return TCPService.this;
			}
		}

		private class CallBack implements MessageListener{

			/**
			 * MCU数据回调函数
			 */
			@Override
			public void platformCallBack(MessageModel model) {
				
				if(model == null){
					return;
				}
				
				//给平台发送回执状态
				doReponse(model);
				
				
				//插入数据库
				//TODO

				
				//回调Activity
				for(MessageListener listener:listenerList){
					listener.platformCallBack(model);
				}
			}
		}

		private void doReponse(MessageModel model) {
			
			if(IContent.TYPE_HEART_BEAT == model.getType()){
				int rssi = wifiInfo.getRssi();
				rssi = Math.abs(rssi);
				String cmd = IContent.HEART_BEAT_LEN+IContent.TYPE_CMD2+IMEI+IContent.SEPARATOR+"00"+rssi+String.format("%04d", mBatteryVolt/10)+IContent.OVER;
				sendCMD(cmd);	
			}else if(IContent.TYPE_ALEAR_4_2 == model.getType()){
				if("00".equals(model.getState())){
					String cmd = IContent.COMMON_LEN+IContent.TYPE_CMD4+IMEI+IContent.SEPARATOR+IContent.RESPONSE_SUCCEED+IContent.OVER;
					sendCMD(cmd);	
				}else{
					String cmd = IContent.COMMON_LEN+IContent.TYPE_CMD4+IMEI+IContent.SEPARATOR+IContent.RESPONSE_ERROR+IContent.OVER;
					sendCMD(cmd);	
				}
			}else if(IContent.TYPE_ALEAR_4_1 == model.getType()){
				String alarmlevel = model.getAlarmlevel();
				String alarmContent = model.getAlarmContent();
				String alarmInterval = model.getAlarmInterval();
				String continueNumber = model.getContinueNumber();
				if(!"01".equals(alarmlevel) || !"02".equals(alarmlevel) || !"03".equals(alarmlevel)){
					String cmd = IContent.COMMON_LEN+IContent.TYPE_CMD4+IMEI+IContent.SEPARATOR+IContent.RESPONSE_ERROR+IContent.OVER;
					sendCMD(cmd);	
					return;
				}
				try{
					if(Integer.parseInt(alarmInterval) < 1 && Integer.parseInt(alarmInterval) > 99){
						String cmd = IContent.COMMON_LEN+IContent.TYPE_CMD4+IMEI+IContent.SEPARATOR+IContent.RESPONSE_ERROR+IContent.OVER;
						sendCMD(cmd);	
						return;
					}
				}catch(Exception e){
					e.printStackTrace();
					String cmd = IContent.COMMON_LEN+IContent.TYPE_CMD4+IMEI+IContent.SEPARATOR+IContent.RESPONSE_ERROR+IContent.OVER;
					sendCMD(cmd);	
					return;
				}
				
				try{
					if(Integer.parseInt(continueNumber) < 1 && Integer.parseInt(continueNumber) > 99){
						String cmd = IContent.COMMON_LEN+IContent.TYPE_CMD4+IMEI+IContent.SEPARATOR+IContent.RESPONSE_ERROR+IContent.OVER;
						sendCMD(cmd);	
						return;
					}
				}catch(Exception e){
					e.printStackTrace();
					String cmd = IContent.COMMON_LEN+IContent.TYPE_CMD4+IMEI+IContent.SEPARATOR+IContent.RESPONSE_ERROR+IContent.OVER;
					sendCMD(cmd);	
					return;
				}
				
				String cmd = IContent.COMMON_LEN+IContent.TYPE_CMD4+IMEI+IContent.SEPARATOR+IContent.RESPONSE_SUCCEED+IContent.OVER;
				sendCMD(cmd);	
			
			}
		}


}
