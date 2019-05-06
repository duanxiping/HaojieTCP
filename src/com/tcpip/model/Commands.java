package com.tcpip.model;

public class Commands {

	//注册指令
		public static final String DATA_TYPE1 = "0001";
		
		//心跳
		public static final String DATA_TYPE2 = "0002";
		
		//远程设置参数指令
		public static final String DATA_TYPE3 = "0003";
		
		//平台触发报警
		public static final String DATA_TYPE4 = "0004";
		
		//呼叫器主动发数据给平台
		public static final String DATA_TYPE5 = "0005";
		
		//分隔符
		public static final String SEPARATOR = "$";
		
		//结束符
		public static final String OVER = "@";
		
		//平台回复 注册指令
		public static final String P_DATA_TYPE1 = "8001";
		
		//平台回复 心跳
		public static final String P_DATA_TYPE2 = "8002";
		
		//平台回复 远程设置参数指令
		public static final String P_DATA_TYPE3 = "8003";
		
		//平台回复 平台触发报警
		public static final String P_DATA_TYPE4 = "8004";
		
		//平台回复 呼叫器主动发数据给平台
		public static final String P_DATA_TYPE5 = "8005";
}
