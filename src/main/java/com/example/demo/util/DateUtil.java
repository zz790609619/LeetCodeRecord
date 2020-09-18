package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * 拼接时间格式 例如 2019/09/09 可以转换为2019/09/09 00:00:00 或者 2019/09/09 23:59:59
     * @param time
     * @param type 1:start 2:end
     * @return
     */
    public String getStartOrEndTime(String time, int type){
        Calendar calendar=Calendar.getInstance();
        String result="";
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sd.parse(time);
            calendar.setTime(date);
            if(type==2){
                calendar.add(Calendar.HOUR,23);
                calendar.add(Calendar.MINUTE,59);
                calendar.add(Calendar.SECOND,59);
            }else{
                calendar.add(Calendar.HOUR,0);
                calendar.add(Calendar.MINUTE,0);
                calendar.add(Calendar.SECOND,0);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result=sdf.format(calendar.getTime());
        }catch (Exception e){
        }
        return result;
    }
}
