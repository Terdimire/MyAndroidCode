package com.lzm.musicseekbar;



public class TimeUtils {
	private String one="";
	private String timeString="";
	private String fenString1="";
	private String miaoString1="";
	private String fenString2="";
	private String miaoString2="";
	public String TimeUtils(int current, int duration) {
		// TODO Auto-generated construbctor stub

		if ((current/60000)>=1) {
			
			fenString1=String.valueOf(current/1000/60);
			if ((current%60000/1000)<10) {
				miaoString1="0"+String.valueOf(current%60000/1000);
			}else{
				miaoString1=String.valueOf(current%60000/1000);
			}
			one = fenString1+":"+miaoString1;
		}else {
			if ((current/1000)<10) {
				miaoString1="00:0"+String.valueOf(current/1000);
			}else {
				miaoString1="00:"+String.valueOf(current/1000);
			}
			
			one=miaoString1;
		}
		fenString2=String.valueOf(duration/1000/60);
		if ((duration%60000/1000)<10) {
			miaoString2="0"+String.valueOf(duration%60000/1000);
		}else {
			miaoString2=String.valueOf(duration%60000/1000);
		}
		timeString=one+"/"+fenString2+":"+miaoString2;
		return timeString;
	}
	
}
