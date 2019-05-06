package com.tcpip.model;

public class MessageModel {

	//今日降雨量
			private String sTodayRainFall = "";

			//本月降雨量
			private String sMonthRainFall = "";

			//时间
			private String year;
			private String month;
			private String day;
			private String hour;
			private String min;
			private String second;
			private String week;

			//雨量站报警级别
			private int sRainFallAlarmlevel;
			
			//水位站报警级别
			private int sWaterLevelAlarmlevel;
			
			private String sWaterLevel = "";

			//场次降雨量
			private String sSessionRainFall = "";
			
			//取消报警状态
			private String state = "";
			
			//报警级别
			private String alarmlevel = "";
			
			//报警内容
			private String alarmContent = "";
			
			//报警间隔
			private String alarmInterval = "";
			
			//持续次数
			private String continueNumber = "";


		private int type;
		
		public String getsWaterLevel() {
			return sWaterLevel;
		}

		public void setsWaterLevel(String sWaterLevel) {
			this.sWaterLevel = sWaterLevel;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public String getsTodayRainFall() {
			return sTodayRainFall;
		}

		public void setsTodayRainFall(String sTodayRainFall) {
			this.sTodayRainFall = sTodayRainFall;
		}

		public String getsMonthRainFall() {
			return sMonthRainFall;
		}

		public void setsMonthRainFall(String sMonthRainFall) {
			this.sMonthRainFall = sMonthRainFall;
		}

		public int getsRainFallAlarmlevel() {
			return sRainFallAlarmlevel;
		}

		public int getsWaterLevelAlarmlevel() {
			return sWaterLevelAlarmlevel;
		}

		public void setsWaterLevelAlarmlevel(int sWaterLevelAlarmlevel) {
			this.sWaterLevelAlarmlevel = sWaterLevelAlarmlevel;
		}

		public void setsRainFallAlarmlevel(int sRainFallAlarmlevel) {
			this.sRainFallAlarmlevel = sRainFallAlarmlevel;
		}

		public String getsSessionRainFall() {
			return sSessionRainFall;
		}

		public void setsSessionRainFall(String sSessionRainFall) {
			this.sSessionRainFall = sSessionRainFall;
		}

		public String getYear() {
			return year;
		}

		public void setYear(String year) {
			this.year = year;
		}

		public String getMonth() {
			return month;
		}

		public void setMonth(String month) {
			this.month = month;
		}

		public String getDay() {
			return day;
		}

		public void setDay(String day) {
			this.day = day;
		}

		public String getHour() {
			return hour;
		}

		public void setHour(String hour) {
			this.hour = hour;
		}

		public String getMin() {
			return min;
		}

		public void setMin(String min) {
			this.min = min;
		}

		public String getSecond() {
			return second;
		}

		public void setSecond(String second) {
			this.second = second;
		}

		public String getWeek() {
			return week;
		}

		public void setWeek(String week) {
			this.week = week;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getAlarmlevel() {
			return alarmlevel;
		}

		public void setAlarmlevel(String alarmlevel) {
			this.alarmlevel = alarmlevel;
		}

		public String getAlarmContent() {
			return alarmContent;
		}

		public void setAlarmContent(String alarmContent) {
			this.alarmContent = alarmContent;
		}

		public String getAlarmInterval() {
			return alarmInterval;
		}

		public void setAlarmInterval(String alarmInterval) {
			this.alarmInterval = alarmInterval;
		}

		public String getContinueNumber() {
			return continueNumber;
		}

		public void setContinueNumber(String continueNumber) {
			this.continueNumber = continueNumber;
		}

	}
