package com.tcpip.model;


public class TCPBinder {

private MessageListener listener = null;
	
	public void setMessageListener(MessageListener listener){
		this.listener = listener;
	}
	
	public void analysis(String msg){
		try{
			if(msg != null || "".equals(msg)){
				//数据长度
				String len = msg.substring(0, 4);
				//数据类型
				String type = msg.substring(4, 8);
				
				//注册平台
				if(IContent.TYPE_P_CMD1.equals(type)){
					String[] sArray = msg.split("\\"+IContent.SEPARATOR);
					if(sArray.length>1){
						String stime = sArray[1];
						String[] stimeArray = stime.split(IContent.OVER);
						String time = stimeArray[0];
						
						MessageModel model = new MessageModel();
						model.setType(IContent.TYPE_REGISTER);
						model.setYear(time.substring(0, 4));
						model.setMonth(time.substring(4, 6));
						model.setDay(time.substring(6, 8));
						model.setWeek(time.substring(8, 9));
						model.setHour(time.substring(9, 11));
						model.setMin(time.substring(11, 13));
						model.setSecond(time.substring(13, 15));
						
						listener.platformCallBack(model);
					}
					
				//心跳
				}else if(IContent.TYPE_P_CMD2.equals(type)){
					
					String[] sArray = msg.split("\\"+IContent.SEPARATOR);
					if(sArray.length > 5){
						
						String sSessionRainFall = sArray[1];
						String sTodayRainFall = sArray[2];
						String sMonthRainFall = sArray[3];
						String sWaterLevel = sArray[4];
						String sWarn = sArray[5];
						String[] sWarnArray = sWarn.split(IContent.OVER);
						String ss = sWarnArray[0];
						String sRainFallAlarmlevel = ss.substring(0, 1);
						String sWaterLevelAlarmlevel = ss.substring(1, 2);
						
						MessageModel model = new MessageModel();
						model.setType(IContent.TYPE_HEART_BEAT);
						model.setsSessionRainFall(sSessionRainFall);
						model.setsTodayRainFall(sTodayRainFall);
						model.setsMonthRainFall(sMonthRainFall);
						model.setsWaterLevel(sWaterLevel);
						model.setsRainFallAlarmlevel(Integer.valueOf(sRainFallAlarmlevel));
						model.setsWaterLevelAlarmlevel(Integer.valueOf(sWaterLevelAlarmlevel));
						
						listener.platformCallBack(model);
					}
					
				}else if(IContent.TYPE_P_CMD3.equals(type)){
				
				//平台报警
				}else if(IContent.TYPE_P_CMD4.equals(type)){
						//取消报警
						if(IContent.COMMON_LEN.equals(len)){
							MessageModel model = new MessageModel();
							model.setType(IContent.TYPE_ALEAR_4_2);
							try{
								String[] sArray = msg.split("\\"+IContent.SEPARATOR);
								if(sArray.length>1){
									String sState = sArray[1];
									String[] sStateArray = sState.split(IContent.OVER);
									String state = sStateArray[0];
									
									model.setState(state);
									
									listener.platformCallBack(model);
								}
							}catch(Exception e){
								e.printStackTrace();
								listener.platformCallBack(model);
							}
							
						//平台触发报警
						}else{
							
							MessageModel model = new MessageModel();
							model.setType(IContent.TYPE_ALEAR_4_1);
							try{
								String[] sArray = msg.split("\\"+IContent.SEPARATOR);
								if(sArray.length>4){
									String alarmlevel = sArray[1];
									String alarmContent = sArray[2];
									String alarmInterval = sArray[3];
									String[] sStateArray = sArray[4].split(IContent.OVER);
									String continueNumber = sStateArray[0];
									
									model.setAlarmlevel(alarmlevel);
									model.setAlarmContent(alarmContent);
									model.setAlarmInterval(alarmInterval);
									model.setContinueNumber(continueNumber);
									
									listener.platformCallBack(model);
									
								}
							}catch(Exception e){
								e.printStackTrace();
								listener.platformCallBack(model);
							}
							
						}
					
				}else if(IContent.TYPE_P_CMD5.equals(type)){
					
					//呼叫器控制报警协议
					if(IContent.COMMON_LEN.equals(len)){
						MessageModel model = new MessageModel();
						model.setType(IContent.TYPE_BEEPER_5_1);
						
						String[] sArray = msg.split("\\"+IContent.SEPARATOR);
						if(sArray.length>1){
							String sState = sArray[1];
							String[] sStateArray = sState.split(IContent.OVER);
							String state = sStateArray[0];
							
							model.setState(state);
							listener.platformCallBack(model);
						}
						
					//呼叫器主动查询数据
					}else{
						MessageModel model = new MessageModel();
						model.setType(IContent.TYPE_HEART_BEAT);
						
						String[] sArray = msg.split("\\"+IContent.SEPARATOR);
						if(sArray.length > 5){
							
							String sSessionRainFall = sArray[1];
							String sTodayRainFall = sArray[2];
							String sMonthRainFall = sArray[3];
							String sWaterLevel = sArray[4];
							String sWarn = sArray[5];
							String[] sWarnArray = sWarn.split(IContent.OVER);
							String ss = sWarnArray[0];
							String sRainFallAlarmlevel = ss.substring(0, 1);
							String sWaterLevelAlarmlevel = ss.substring(1, 2);
							
							model.setsSessionRainFall(sSessionRainFall);
							model.setsTodayRainFall(sTodayRainFall);
							model.setsMonthRainFall(sMonthRainFall);
							model.setsWaterLevel(sWaterLevel);
							model.setsRainFallAlarmlevel(Integer.valueOf(sRainFallAlarmlevel));
							model.setsWaterLevelAlarmlevel(Integer.valueOf(sWaterLevelAlarmlevel));
							
							listener.platformCallBack(model);
						}
					}
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
			listener.platformCallBack(null);
		}
		
	}
	
}
