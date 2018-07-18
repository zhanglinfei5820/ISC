package com.luckserver.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.collections.bag.SynchronizedSortedBag;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Demo8 {
	public static void main(String[] args) throws ScriptException, NoSuchMethodException, FileNotFoundException {
//		new Demo8().runThread();
//		new Demo8().getScriptEngineFactory();
//		new Demo8().clientOracle();
//		new Demo8().invokeFunctionWithParam();
//		{"occupation":"321","name":"123"}
//		{"occupation":"654","name":"456"}
//		StringBuffer sqlBuff = new StringBuffer();
//		String inftablr = "indocno,sloginid,msg_id";
//		sqlBuff.append(" INSERT into").append(" agency").append(" (").append(inftablr).append(" )").append(" values")
//			.append(" (");
//		List<String> asList = Arrays.asList(inftablr.split(","));
//		String json = "[{\"sloginid\":\"123\",\"msg_id\":\"0\"},{\"sloginid\":\"456\",\"msg_id\":\"0\"}]";
//		JSONArray parseArray = JSON.parseArray(json);
//		StringBuffer valueBuff = new StringBuffer();
//		asList.forEach(m -> {
//			valueBuff.append("?").append(",");
//		});
//		sqlBuff.append(valueBuff.substring(0, valueBuff.toString().lastIndexOf(","))).append(")");
//		List<Object[]> batchArgs = new ArrayList<>();
//		for (int i = 0; i < parseArray.size(); i++) {
//			JSONObject parseObject = JSON.parseObject(parseArray.getString(i).toString());
//			Object[] objs = new Object[asList.size()];
//			for (int j = 0; j < asList.size(); j++) {
//				objs[j] = parseObject.get(asList.get(j));
//			}
//			batchArgs.add(objs);
//		}
//		System.out.println(JSON.toJSONString(batchArgs));
//		System.out.println(sqlBuff.toString());
//		new Demo8().invokeFunctionWithParam();
		demo1();
	}
	
	public static void demo1() {
//		Object singleResult = this.entityManager.createNativeQuery("select Max(indocno) + "+ (i + 1) +" from agency").getSingleResult();
//		System.out.println("最大数量是：" + singleResult.toString());
		StringBuffer sqlBuff = new StringBuffer();
		String inftablr = "indocno,sloginid,msg_id";
		sqlBuff.append(" INSERT ALL into").append(" agency").append(" (").append(inftablr).append(" )").append(" values")
			.append(" (");
		List<String> asList = Arrays.asList(inftablr.split(","));
		String json = "[{\"sloginid\":\"123\",\"msg_id\":\"0\"},{\"sloginid\":\"456\",\"msg_id\":\"0\"}]";
		JSONArray parseArray = JSON.parseArray(json);
		StringBuffer valueBuff = new StringBuffer();
		for (int i = 0; i < parseArray.size(); i++) {
			JSONObject parseObject = JSON.parseObject(parseArray.getString(i).toString());
			asList.forEach(m -> {
				valueBuff.append("'").append(parseObject.get(m)).append("'").append(",");
			});
			sqlBuff.append(valueBuff.substring(0, valueBuff.toString().lastIndexOf(",")));
			sqlBuff.append(" )");
			if(i != parseArray.size() -1) {
				sqlBuff.append(" into").append(" agency").append(" (").append(inftablr).append(" )").append(" values")
				.append(" (");
			}
			valueBuff.setLength(0);
		}
		sqlBuff.append(" select 1 from dual");
		System.out.println(sqlBuff.toString());
	}

	/**
	 * 
	 * @Title: getScriptEngineFactory @Description: 查看java支持哪些脚本语言 [nashorn,
	 * Nashorn, js, JS, JavaScript, javascript, ECMAScript,ecmascript] @param
	 * 参数 @return void 返回类型 @throws
	 */
	public void getScriptEngineFactory() {
		ScriptEngineManager manager = new ScriptEngineManager();
		List<ScriptEngineFactory> factories = manager.getEngineFactories();
		for (ScriptEngineFactory factory : factories) {
			System.out.println(factory.getNames());
		}
	}
	
	/**
	 * 
	* @Title: invokeExpression  
	* @Description: 执行一段简单的js
	* @param @throws ScriptException    参数  
	* @return void    返回类型  
	* @throws
	 */
	public void invokeExpression() throws ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		String js = "1 + 2";
		Integer result = (Integer) engine.eval(js);
		System.out.println(result);
	}

	/**
	 * 
	* @Title: invokeFunction  
	* @Description: 执行js函数
	* @param @throws ScriptException
	* @param @throws NoSuchMethodException    参数  
	* @return void    返回类型  
	* @throws
	 */
	public void invokeFunction() throws ScriptException, NoSuchMethodException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		String js = "function welcom(){return 'welcom';}";
		engine.eval(js);
		Invocable invocable = (Invocable) engine;
		String result = (String) invocable.invokeFunction("welcom");
		System.out.println(result);
	}

	/**
	 * 
	* @Title: invokeFunctionWithParam  
	* @Description: 执行js函数 传递参数
	* @param @throws ScriptException
	* @param @throws NoSuchMethodException    参数  
	* @return void    返回类型  
	* @throws
	 */
	public void invokeFunctionWithParam() throws ScriptException, NoSuchMethodException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		String js = "function dataConvert(data,correspondence){\r\n" + 
				"	var jsonData = JSON.parse(data);\r\n" + 
				"	var jsonField = JSON.parse(correspondence);\r\n" + 
				"	var obj = {};\r\n" + 
				"	var map = new Array(); \r\n" + 
				"	if(jsonData.length > 1) {\r\n" + 
				"		for(var k in jsonData){\r\n" + 
				"			for(var i in jsonData[k]){\r\n" + 
				"				for(var j in jsonField){\r\n" + 
				"					if(jsonField[j] == i){\r\n" + 
				"						obj[j] = jsonData[k][i];\r\n" + 
				"					}\r\n" + 
				"				}\r\n" + 
				"			}\r\n" + 
				"			map.push(obj);\r\n" + 
				"			obj = {};\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	return JSON.stringify(map);\r\n" + 
				"}";
		engine.eval(js);
		Invocable invocable = (Invocable) engine;
//		String result = (String) invocable.invokeFunction("dataConvert", "{\"Violet\":\"123\",\"character\":\"321\"}","{ \"name\": \"Violet\", \"occupation\": \"character\" }");
		String result = (String) invocable.invokeFunction("dataConvert", "[{\"Violet\":\"123\",\"character\":\"321\"},{\"Violet\":\"456\",\"character\":\"654\"}]","{ \"name\": \"Violet\", \"occupation\": \"character\" }");
		System.out.println(result);
	}

	/**
	 * 
	* @Title: inject  
	* @Description: 将java对象注入js代码中运行
	* @param @throws ScriptException
	* @param @throws NoSuchMethodException    参数  
	* @return void    返回类型  
	* @throws
	 */
	public void inject() throws ScriptException, NoSuchMethodException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		Date date = new Date();
		System.out.println(date.getTime());
		engine.put("date", date);
		String js = "function getTime(){return date.getTime();}";
		engine.eval(js);
		Invocable invocable = (Invocable) engine;
		Long result = (Long) invocable.invokeFunction("getTime");
		System.out.println(result);
	}

	/**
	 * 
	* @Title: runThread  
	* @Description: java通过线程启动js函数
	* @param @throws ScriptException
	* @param @throws NoSuchMethodException    参数  
	* @return void    返回类型  
	* @throws
	 */
	public void runThread() throws ScriptException, NoSuchMethodException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		engine.put("out", System.out);
		String js = "var obj=new Object();obj.run=function(){out.println('thread...')}";
		engine.eval(js);
		Object obj = engine.get("obj");
		Invocable inv = (Invocable) engine;
		Runnable r = inv.getInterface(obj, Runnable.class);
		Thread t = new Thread(r);
		t.start();
	}
	
	public void clientOracle() throws FileNotFoundException, ScriptException, NoSuchMethodException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		String filePath = "D:\\zlf\\code\\clientOracle.js";
		File file = new File(filePath);
		FileReader fileReader = new FileReader(file);
		if(fileReader!=null) {
            engine.eval(fileReader);
            Invocable inv = (Invocable) engine;
            String result = String.valueOf(inv.invokeFunction("clientOracle"));
            System.out.println("获取返回值为：" + result);
		}
	}

}
