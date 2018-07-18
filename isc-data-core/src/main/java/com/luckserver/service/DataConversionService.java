package com.luckserver.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * 
* @ClassName: DataConversionService  
* @Description: 数据转换服务
* @author zlf  
* @date 2018年7月6日  
* @modified By
* @modified Date
*
 */
@WebService(name = "DataConversionService",// 暴露服务名称
targetNamespace = "http://service.lucksoft.com/"// 命名空间,一般是接口的包名倒序
)
public interface DataConversionService {
	/**
	 * 
	* @Title: Conversion  
	* @Description: 数据转换方法
	* @param     参数  
	* @return void    返回类型  
	*
	 */
	@WebMethod
	void Conversion(String hashCode);
}
