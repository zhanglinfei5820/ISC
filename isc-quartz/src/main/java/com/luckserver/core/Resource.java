package com.luckserver.core;
 
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 用在字段上,表示引用的Bean
 */
@Target(value=ElementType.FIELD)
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Resource {
	
	String value();
 
	
}