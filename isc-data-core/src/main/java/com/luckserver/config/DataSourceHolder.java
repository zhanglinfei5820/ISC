package com.luckserver.config;

import java.util.Date;

/**
 * 
 * @author zlf
 *
 */
public class DataSourceHolder {

    //线程本地环境
    private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();
    //设置数据源
    public static void setDataSource(String customerType) {
    	System.out.println("更换数据源为:" + customerType);
        dataSources.set(customerType);
    }
    //获取数据源
    public static String getDataSource() {
        return (String) dataSources.get();
    }
    //清除数据源
    public static void clearDataSource() {
    	System.out.println("操作时间："+new Date());
        dataSources.remove();
    }
}
