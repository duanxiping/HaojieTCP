package com.tcpip.model;

public class IContent {
	
	public static final String TYPE_CMD1 = "0001";
	
	public static final String TYPE_CMD2 = "0002";
	
	public static final String TYPE_CMD3 = "0003";
	
	public static final String TYPE_CMD4 = "0004";
	
	public static final String TYPE_CMD5 = "0005";
	
	//////////////////////////////////////////////////////
	
	public static final String TYPE_P_CMD1 = "8001";
	
	public static final String TYPE_P_CMD2 = "8002";
	
	public static final String TYPE_P_CMD3 = "8003";
	
	public static final String TYPE_P_CMD4 = "8004";
	
	public static final String TYPE_P_CMD5 = "8005";
	
	
	public static final String SEPARATOR = "$";
	
	public static final String OVER = "@";
	
	public static final String COMMON_LEN = "0027";
	
	public static final String HEART_BEAT_LEN = "0033";
	
	public static final String TRIGGER_ALARM_LEN = "0031";
	
	public static final String TRIGGER_ALARM_STR = "SETBJ";
	
	public static final String QUERY_DATA_LEN = "0030";
	
	public static final String QUERY_DATA_STR= "ASKSJ";
	
	public static final String RESPONSE_SUCCEED = "11";
	
	public static final String RESPONSE_ERROR = "00";
	
	public static final String REGISTER_OK = "OK";
	
	
	////////////////////////////////////////////////////
	
	public static final String REGISTER_CMD = "00270001861694039966708$OK@";
	
	////////////////////////////////////////////////////
	

	//注册
	public static final int TYPE_REGISTER = 1;
	
	//心跳
	public static final int TYPE_HEART_BEAT = 2;
	
	//平台触发报警
	public static final int TYPE_ALEAR_4_1 = 3;
	
	//取消报警
	public static final int TYPE_ALEAR_4_2 = 4;
	
	//呼叫器控制报警协议
	public static final int TYPE_BEEPER_5_1 = 5;
	
	//呼叫器主动查询数据
	public static final int TYPE_BEEPER_5_2 = 6;

}
