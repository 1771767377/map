package com.zicms.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {
	/*获取某天的0时*/
	public static List<String> getNextDay(Date date) {  
		List<String> list=new ArrayList<String>();
		//获得今天的0点
		Calendar calendar = Calendar.getInstance(); 
		date = calendar.getTime();
		SimpleDateFormat simple=new SimpleDateFormat("yyyyMMdd000000");
		String date1=simple.format(date);
		list.add(date1);
		//获得昨天的0点
		calendar.setTime(date);  
		calendar.add(Calendar.DAY_OF_MONTH, -1);  
		date = calendar.getTime();
		date1=simple.format(date);
		list.add(date1);
		
		return list;  
	}  
	/*获取某天的0时*/
	public static List<String> getNextDay_1(Date date) {  
		List<String> list=new ArrayList<String>();
		//获得今天的0点
		Calendar calendar = Calendar.getInstance(); 
		date = calendar.getTime();
		SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String date1=simple.format(date);
		list.add(date1);
		//获得昨天的0点
		calendar.setTime(date);  
		calendar.add(Calendar.DAY_OF_MONTH, -1);  
		date = calendar.getTime();
		date1=simple.format(date);
		list.add(date1);
		//获得明天的0点
		calendar.setTime(date);  
		calendar.add(Calendar.DAY_OF_MONTH, 2);  
		date = calendar.getTime();
		date1=simple.format(date);
		list.add(date1);
		return list;  
	}  

	/**   
     * 计算某个日期的天数
     * @param date   
     * @return  天数
     */  
	public static  int  getDay(String date){
		if(date==null||date.isEmpty()){
			return -1;
		}
		int day =0;
		Calendar calendar = Calendar.getInstance(); 
		SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			calendar.setTime(simple.parse(date));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		day=calendar.get(Calendar.DATE);
		return day;
	}
	
	/**   
     * 计算两个日期之间相差的天数   
     * @param date1   
     * @param date2   
     * @return   -1 输入有误，无结果
	 * @throws ParseException 
     */    
    public static int daysBetween(String date1,String date2){ 
    	long between_days=-1;
    	if(date1==null||date1.isEmpty()){
    		return  -1;
    	}
    	if(date2==null||date2.isEmpty()){
    		return  -1;
    	}
    	SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();     
        try {
        	cal.setTime(simple.parse(date1));
        	long time1 = cal.getTimeInMillis();                  
        	cal.setTime(simple.parse(date2));     
        	long time2 = cal.getTimeInMillis();          
        	between_days=(time2-time1)/(1000*3600*24); 
        } catch (ParseException e) {
        	return -1;
        }   
       return Integer.parseInt(String.valueOf(between_days));            
    }    
    
    /**
         * 获取过去第几天的日期(- 操作) 或者 未来 第几天的日期( + 操作)
         *
          * @param past
          * @return
         */
         public static String getNewDate(String date,int past) {
        	 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日期格式
        	 Date oldDate;
        	 Date newDate;
        	 if(date==null||date.isEmpty()){
        		 return null;
        	 }
			try {
				oldDate = dateFormat.parse(date);
			 // 指定日期
        	 long time = oldDate.getTime(); // 得到指定日期的毫秒数
        	 long day = past*24*60*60*1000; // 要加上的天数转换成毫秒数
        	 time+=day; // 相加得到新的毫秒数
        	 newDate= new Date(time); // 将毫秒数转换成日期
			} catch (ParseException e) {
				return null;
			}
        	 return dateFormat.format(newDate);
         }
     
}
