package com.luckserver.core;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class DataCore {
	
	public void conversion(String hash) {
		//根据Hash查到所属接口信息
		//执行脚本 数据转换
		//保存接口数据
		//同步数据
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
