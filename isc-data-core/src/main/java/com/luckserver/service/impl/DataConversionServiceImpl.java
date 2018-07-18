package com.luckserver.service.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.luckserver.config.DataSourceHolder;
import com.luckserver.service.DataConversionService;

import oracle.jdbc.OracleTypes;

/**
 * 
* @ClassName: DataConversionServiceImpl  
* @Description: 数据转换服务实现类 aop面前切面动态切换数据源  
* @author zlf  
* @date 2018年7月6日  
* @modified By
* @modified Date
*
 */
@Transactional
@Aspect
@WebService(serviceName = "DataConversionService", // 与接口中指定的name一致
targetNamespace = "http://service.lucksoft.com/", // 与接口中的命名空间一致,一般是接口的包名倒
endpointInterface = "com.luckserver.service.DataConversionService"// 接口地址
)
public class DataConversionServiceImpl implements DataConversionService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DataConversionServiceImpl.class);
	
	@Autowired
    private EntityManager entityManager;
	
	@Autowired
    EntityManagerFactory factory;
	
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public void Conversion(String hashCode) {
		//根据Hash查到所属接口信息
		//执行脚本 数据转换
		//保存接口数据
		//同步数据
		try {
			List<?> resultList = this.entityManager.createNativeQuery("select * from sys_user where sname = '"+"测试人员1"+"'").getResultList();
			System.out.println(JSON.toJSONString(resultList));
			DataSourceHolder.setDataSource("oms");
			List<?> resultList2 = this.entityManager.createNativeQuery("select * from sys_user where sname = '"+"测试人员1"+"'").getResultList();
			System.out.println(JSON.toJSONString(resultList2));
			DataSourceHolder.clearDataSource();
			DataSourceHolder.setDataSource("aq");
			
			//---------------------------------------
			
//			String sql = getSql("indocno,sloginid,msg_id", "agency", "indocno", "[{\"sloginid\":\"123\",\"msg_id\":\"0\"},{\"sloginid\":\"456\",\"msg_id\":\"0\"}]");
//			LOGGER.warn("通过计算获得SQL为:" + sql);
//			String executeSql = executeSql(sql);
//			LOGGER.warn("执行SQL结果为:" + executeSql);
			
			String executeProcedure = executeProcedure("{call sp_isc_data_conversion()}");
			System.out.println(JSON.toJSONString("执行同步代码返回值为：" + executeProcedure));
			
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * 
	* @Title: executeProcedure  
	* @Description: 执行存储过程 或者SQL
	* @param @param procedure 同步代码 存储过程、SQL
	* @param @return    参数  
	* @return String    返回类型  
	*
	 */
	public String executeProcedure(String procedure) {
		String executeResult = "";
		try {
			EntityManager createEntityManager = factory.createEntityManager();
			createEntityManager.getTransaction().begin();
			int executeUpdate = createEntityManager.createNativeQuery(procedure).executeUpdate();
			createEntityManager.getTransaction().commit();
			createEntityManager.close();
			executeResult = String.valueOf(executeUpdate);
		} catch (Exception e) {
			e.getStackTrace();
			executeResult = e.getMessage();
		}
		return executeResult;
	}
	
	/**
	 * 
	* @Title: getSql  
	* @Description: 获取SQL
	* @param @param infField 接口字段
	* @param @param tableName 表名
	* @param @param primaryKey 主键名称
	* @param @param jsonData   经过数据模板转换之后的json数据
	* @param @return    参数  	
	* @return String    返回类型  
	*
	 */
	public String getSql(String infField, String tableName, String primaryKey,String jsonData) {
		if(StringUtils.isBlank(infField)) {
			return "接口字段不能为空!";
		}
		if(StringUtils.isBlank(primaryKey)) {
			return "主键名不能为空!";
		}
		StringBuffer sqlBuff = new StringBuffer();
		try {
			sqlBuff.append(" INSERT ALL into ").append(tableName).append(" ( ").append(infField).append(" ) ").append(" values ")
			.append(" ( ");
			List<String> asList = Arrays.asList(infField.split(","));
			JSONArray jsonDataArray = JSON.parseArray(jsonData);
			if(jsonDataArray == null || jsonDataArray.size() <= 0 || StringUtils.isBlank(jsonData)) {
				return "jsonData数据为空或者数据格式不对!";
			}
			StringBuffer valueBuff = new StringBuffer();
			for (int i = 0; i < jsonDataArray.size(); i++) {
				//获取当前表主键最大值
				Object singleResult = this.entityManager.createNativeQuery("select Max("+primaryKey+") + "+ (i + 1) +" from " + tableName + "").getSingleResult();
				JSONObject parseObject = JSON.parseObject(jsonDataArray.getString(i).toString());
				asList.forEach(m -> {
					if(primaryKey.equals(m)) {
						valueBuff.append("'").append(singleResult.toString()).append("'").append(",");
					}else {
						valueBuff.append("'").append(parseObject.get(m)).append("'").append(",");
					}
				});
				//去掉最后一个逗号
				sqlBuff.append(valueBuff.substring(0, valueBuff.toString().lastIndexOf(",")));
				sqlBuff.append(" ) ");
				if(i != jsonDataArray.size() -1) {
					sqlBuff.append(" into ").append(tableName).append(" (").append(infField).append(" ) ").append(" values ")
					.append(" ( ");
				}
				//清空
				valueBuff.setLength(0);
			}
			sqlBuff.append(" select 1 from dual");
			System.out.println(sqlBuff.toString());
		} catch (Exception e) {
			e.getStackTrace();
			return "异常!执行的sql为：" + sqlBuff.toString() + ";错误信息为:" + e.getMessage();
		}
		return sqlBuff.toString();
	}
	
	/**
	 * 
	* @Title: executeSql  
	* @Description: 执行SQL
	* @param @param sql
	* @param @return    参数  
	* @return String    返回类型  
	*
	 */
	public String executeSql(String sql) {
		if(StringUtils.isBlank(sql)) {
			return "sql语句不能为空";
		}
		String executeResult = "";
		try {
			EntityManager createEntityManager = factory.createEntityManager();
			createEntityManager.getTransaction().begin();
			executeResult = String.valueOf(createEntityManager.createNativeQuery(sql).executeUpdate());
			createEntityManager.getTransaction().commit();
			createEntityManager.close();
		} catch (Exception e) {
			e.getStackTrace();
			return "执行SQL出错，错误信息为：" + e.getMessage();
		}
		return executeResult;
	}
	
	/**
	 * 
	* @Title: invokeFunctionWithParam  
	* @Description: 执行js函数 传递参数
	* @param @param jsCode 脚本
	* @param @param jsName  脚本名称
	* @param @param data 接收的json数据
	* @param @param correspondence 字段对应关系 接收数据字段和接口表字段对应关系
	* @param @throws ScriptException
	* @param @throws NoSuchMethodException    参数  
	* @return void    返回类型  
	*
	 */
	public void invokeFunctionWithParam(String jsCode,String jsName ,String data,String correspondence) throws ScriptException, NoSuchMethodException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		String js = "function dataConvert(data,correspondence){\r\n" + 
				"	var jsonData = JSON.parse(data);\r\n" + 
				"	var jsonField = JSON.parse(correspondence);\r\n" + 
				"	var map = new Array(); \r\n" + 
				"	for(var i in jsonField){\r\n" + 
				"		for(var j in jsonData){\r\n" + 
				"			if(jsonField[i] == j){\r\n" + 
				"				map.push(i,jsonData[j]);\r\n" + 
				"			}\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"	return JSON.stringify(map);\r\n" + 
				"}\r\n" + 
				"";
		engine.eval(js);
		Invocable invocable = (Invocable) engine;
		String result = (String) invocable.invokeFunction("dataConvert", "{\"Violet\":\"123\",\"character\":\"321\"}","{ \"name\": \"Violet\", \"occupation\": \"character\" }");
		System.out.println(result);
	}
}
