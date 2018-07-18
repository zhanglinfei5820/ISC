package com.luckserver.core;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class CommonUtil {
	
	/** 
     *  
     * @param source 被复制的实体类对象 
     * @param to 复制完后的实体类对象   
     * @throws Exception 
     */  
    public static void Copy(Object source, Object to){    
        try {    
        	// 获取属性    
        	BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(),java.lang.Object.class);    
        	PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();    
        	
        	BeanInfo destBean = Introspector.getBeanInfo(to.getClass(),java.lang.Object.class);    
        	PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();    
            for (int i = 0; i < sourceProperty.length; i++) {    
    
                for (int j = 0; j < destProperty.length; j++) {    
    
                    if (sourceProperty[i].getName().equals(destProperty[j].getName())) {    
                        // 调用source的getter方法和dest的setter方法    
                        destProperty[j].getWriteMethod().invoke(to,sourceProperty[i].getReadMethod().invoke(source));    
                        break;    
                    }    
                }    
            }    
        } catch (Exception e) {    
           e.getStackTrace();  
        }    
    }
	
	/**
	 * 判断字符串中包含字符出现的次数
	 * @param str
	 * @param key
	 * @return
	 */
	public static int getCount(String str,String key){  
        if(str == null || key == null || "".equals(str.trim()) || "".equals(key.trim())){  
            return 0;  
        }  
           int count = 0;  
           int index = 0;  
           while((index=str.indexOf(key,index))!=-1){  
               index = index+key.length();  
               count++;  
           }  
           return count;  
    }
	
	/**
     * 功能：判断字符串是否为日期格式
     * 
     * @param str
     * @return
     */
    public static boolean isDate(String strDate) {
    	String a1 = "[0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}";//yyyyMMddHHmmss  
        String a2 = "[0-9]{4}[0-9]{2}[0-9]{2}";//yyyyMMdd  
        String a3 = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";//yyyy-MM-dd HH:mm:ss  
        String a4 = "[0-9]{4}-[0-9]{2}-[0-9]{2}";//yyyy-MM-dd  
        String a5= "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}";//yyyy-MM-dd  HH:mm  
        
        if(Pattern.compile(a1).matcher(strDate).matches() || Pattern.compile(a2).matcher(strDate).matches() || Pattern.compile(a3).matcher(strDate).matches() || Pattern.compile(a4).matcher(strDate).matches() || Pattern.compile(a5).matcher(strDate).matches()) {
        	return true;
        }else {
        	return false;
        }
    }
    
	/**
	 * 判断字符串是否是日期 是返回true 不是false
	 * @param str
	 * @return
	 */
	public static  boolean isValidDate(String str) {
	      boolean convertSuccess=true;
	      //指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
	       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	       try {
	    	   //设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
	          format.setLenient(false);
	          format.parse(str);
	       } catch (Exception e) {
	          // e.printStackTrace();
	    	   // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
	           convertSuccess=false;
	       } 
	       return convertSuccess;
	}

}
