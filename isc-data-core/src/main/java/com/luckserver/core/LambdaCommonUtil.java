package com.luckserver.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;

public class LambdaCommonUtil {
	public static void main(String[] args) {
		//替代匿名内部类
//		replaceAnonymousInnerClass();
		// 对集合进行迭代
//		toListIteration();
		//使用lambda表达式的Map和Reduce示例
//		toMapAndReduce();
		//事件处理
//		handleEvent();
		//list转map
//		listToMap();
		//filter操作
//		filterData();
		//与函数式接口Predicate配合
//		predicateInterface();
		//对列表的每个元素应用函数
//		utilityFunction();
		//复制不同的值，创建一个子列表
//		copyDataToList();
		//计算集合元素的最大值、最小值、总和以及平均值 
//		getElementValue();
		
	}
		

	/**
	 * 
	* @Title: replaceAnonymousInnerClass  
	* @Description: 替代匿名内部类
	* @param     参数  
	* @return void    返回类型  
	*
	 */
	public static void replaceAnonymousInnerClass() {
		// Java 8之前：
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Before Java8, too much code for too little to do");
			}
		}).start();

		//Java 8方式：
		new Thread( () -> System.out.println("In Java8, Lambda expression rocks !!") ).start();

	}

	/**
	 * 
	 * @Title: toListIteration
	 * @Description: 对集合进行迭代
	 * @param 参数
	 * @return void 返回类型
	 *
	 */
	public static void toListIteration() {
		// Java 8之前：
		List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
//		 for (String feature : features) {
//			 System.out.println(feature);
//		 }

		// Java 8之后：
//		features.forEach(n -> System.out.println(n));
//		features.forEach(System.out::println);
		features.forEach(n -> { 
			if("Stream API".equals(n))
				System.out.println(n);
		});
//		 features.forEach(n -> handle(n));

	}
	
	/**
	 * 
	* @Title: toMapAndReduce  
	* @Description: 使用lambda表达式的Map和Reduce示例
	* @param     参数  
	* @return void    返回类型  
	*
	 */
	public static void toMapAndReduce() {
		// 不使用lambda表达式为每个订单加上12%的税
//		double total = 0;
//		List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
//		for (Integer cost : costBeforeTax) {
//		    double price = cost + .12*cost;
//		    total = total + price;
//		    System.out.println(price);
//		}
//		System.out.println("Total : " + total);
		// 使用lambda表达式
//		costBeforeTax.stream().map((cost) -> cost + .12*cost).forEach(System.out::println);
//		double bill = costBeforeTax.stream().map(cost -> cost + .12*cost).reduce((sum, cost) -> sum + cost).get();
//		double bill = costBeforeTax.stream().mapToDouble(i -> i + .12 * i).sum();
//		System.out.println("Total : " + bill);
		
		//============Java8之前的方式==========
//		Map<String, Integer> items = new HashMap<>();
//		items.put("A", 10);
//		items.put("B", 20);
//		items.put("C", 30);
//		items.put("D", 40);
//		items.put("E", 50);
//		items.put("F", 60);
//		for (Map.Entry<String, Integer> entry : items.entrySet()) {
//		    System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
//		}
		//============forEach + Lambda表达式==========
		Map<String, Integer> itemMap = new HashMap<>();
		itemMap.put("A", 10);
		itemMap.put("B", 20);
		itemMap.put("C", 30);
		itemMap.put("D", 40);
		itemMap.put("E", 50);
		itemMap.put("F", 60);
		itemMap.forEach((k,v)->System.out.println("Item : " + k + " Count : " + v));
		itemMap.forEach((k,v)->{
		    System.out.println("Item : " + k + " Count : " + v);
		    if("E".equals(k)){
		        System.out.println("Hello E");
		    }
		});
		
	}
	
	/**
	 * 
	* @Title: handleEvent  
	* @Description: 事件处理 
	* @param     参数  
	* @return void    返回类型  
	*
	 */
	public static void handleEvent() {
		// Java 8之前：
//		JButton show =  new JButton("Show");
//		show.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("Event handling without lambda expression is boring");
//			}
//		});
//		// Java 8方式：
//		show.addActionListener((e) -> {
//		    System.out.println("Light, Camera, Action !! Lambda expressions Rocks");
//		});
		
		//经典 Comparator 示例：
//		List<Block> blocks = new ArrayList<>();
//		blocks.add(new Block(4, System.currentTimeMillis(), new ArrayList<Transaction>(), 1, "1", "a"));
//		blocks.add(new Block(1, System.currentTimeMillis(), new ArrayList<Transaction>(), 1, "1", "d"));
//		blocks.add(new Block(2, System.currentTimeMillis(), new ArrayList<Transaction>(), 1, "1", "c"));
//		blocks.add(new Block(3, System.currentTimeMillis(), new ArrayList<Transaction>(), 1, "1", "b"));
//		Comparator<Block> byIndex = new Comparator<Block>() {
//			@Override
//			public int compare(Block b1, Block b2) {
//				return b1.getHash().compareTo(b2.getHash());
////				return b1.getIndex() - b2.getIndex();
//			}
//		};
//		
//		Collections.sort(blocks, byIndex);
//		for (Block block : blocks) {
//			System.out.println(JSON.toJSONString(block));
//		}

		//Lambda 表达式示例：
//		Comparator<Block> byHashLambda = (Block b1, Block b2) -> b1.getHash().compareTo(b2.getHash());
//		blocks.sort(byHashLambda);
//		blocks.forEach(n -> System.out.println(JSON.toJSONString(n)));
//		
//		//Java8示例：
//		Comparator<Block> byHashLambdaSimple = Comparator.comparing(Block::getHash);
//		blocks.sort((b1,b2) -> b1.getIndex() - b2.getIndex());
//		blocks.sort(Comparator.comparing(Block::getHash).reversed());
//		blocks.forEach(n -> System.out.println(JSON.toJSONString(n)));
	}
	
	/**
	 * 
	* @Title: listToMap  
	* @Description: list转map
	* @param     参数  
	* @return void    返回类型  
	*
	 */
	public static void listToMap() {
		//常用方法
//		List<Block> blocks = new ArrayList<>();
//		blocks.add(new Block(4, System.currentTimeMillis(), new ArrayList<Transaction>(), 1, "1", "a"));
//		blocks.add(new Block(1, System.currentTimeMillis(), new ArrayList<Transaction>(), 1, "1", "d"));
//		blocks.add(new Block(1, System.currentTimeMillis(), new ArrayList<Transaction>(), 1, "1", "e"));
//		blocks.add(new Block(2, System.currentTimeMillis(), new ArrayList<Transaction>(), 1, "1", "c"));
//		blocks.add(new Block(3, System.currentTimeMillis(), new ArrayList<Transaction>(), 1, "1", "b"));
//		Map<Integer, Long> collect = blocks.stream().collect(Collectors.toMap(Block::getIndex, Block::getTimestamp));
//		Map<Integer, Block> collect2 = blocks.stream().collect(Collectors.toMap(Block::getIndex,Block -> Block));
		//Block -> Block是一个返回本身的lambda表达式，其实还可以使用Function接口中的一个默认方法代替，使整个方法更简洁优雅：
//		Map<Integer, Block> collect3 = blocks.stream().collect(Collectors.toMap(Block::getIndex,Function.identity()));
		//Duplicate key 重复key值问题解决
//		Map<Integer, Block> collect4 = blocks.stream().collect(Collectors.toMap(Block::getIndex,Function.identity(),(k1,k2) -> k2));
		
//		System.out.println(JSON.toJSONString(collect4));
	}
	
	/**
	 * 
	* @Title: filterData  
	* @Description: filter操作 在操作集合的时候，经常需要从原始的集合中过滤掉一部分元素
	* @param     参数  
	* @return void    返回类型  
	*
	 */
	public static void filterData() {
		List<Double> cost = Arrays.asList(10.0, 20.0,30.0,40.0);
//		List<Double> filteredCost = cost.stream().filter(x -> x > 25.0).collect(Collectors.toList());
//		filteredCost.forEach(System.out::println);
		
		//通过过滤创建一个String列表 创建一个字符串列表，每个字符串长度大于2
		List<String> strList = Arrays.asList("10","1","8","20","300");
		List<String> filtered = strList.stream().filter(x -> x.length()> 2).collect(Collectors.toList());
		System.out.printf("Original List : %s, filtered list : %s %n", strList, filtered);
	}
	
	/**
	 * 
	* @Title: predicateInterface  
	* @Description: 与函数式接口Predicate配合
	* @param     参数  
	* @return void    返回类型  
	*
	 */
	public static void predicateInterface() {
	    List languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
	    
	    System.out.println("Languages which starts with J :");
	    filter(languages, str -> str.startsWith("J"));
	 
	    System.out.println("Languages which ends with a ");
	    filter(languages, str -> str.endsWith("a"));
	 
	    System.out.println("Print all languages :");
	    filter(languages, str -> true);
	 
	    System.out.println("Print no language : ");
	    filter(languages, str -> false);
	 
	    System.out.println("Print language whose length greater than 4:");
	    filter(languages,str -> ((String) str).length() > 4);
	}
	
	public static void filter(List<String> names, Predicate<String> condition) {
		//原先方法
//	    for(String name: names)  {
//	        if(condition.test(name)) {
//	            System.out.println(name + " ");
//	        }
//	    }
	    //更好方法
	    names.stream().filter(n -> condition.test(n)).forEach(System.out::println);
	}
	
	/**
	 * 
	* @Title: utilityFunction  
	* @Description: 对列表的每个元素应用函数
	* @param     参数  
	* @return void    返回类型  
	*
	 */
	public static void utilityFunction() {
		// 将字符串换成大写并用逗号链接起来
		List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
		String G7Countries = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
		System.out.println(G7Countries);
	}
	
	/**
	 * 
	* @Title: copyDataToList  
	* @Description: 复制不同的值，创建一个子列表  
	* @param     参数  
	* @return void    返回类型  
	*
	 */
	public static void copyDataToList() {
		//如何利用流的 distinct() 方法来对集合进行去重,用所有不同的数字创建一个正方形列表
		List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4);
		List<Integer> distinct = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
		System.out.printf("Original List : %s,  Square Without duplicates : %s %n", numbers, distinct);
	}
	
	/**
	 * 
	* @Title: getElementValue  
	* @Description: 计算集合元素的最大值、最小值、总和以及平均值  
	* @param     参数  
	* @return void    返回类型  
	*
	 */
	public static void getElementValue() {
		//获取数字的个数、最小值、最大值、总和以及平均值
		List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
		IntSummaryStatistics stats = primes.stream().mapToInt((x) -> x).summaryStatistics();
		System.out.printf("Highest prime number in List : %s %n" , stats.getMax());
		System.out.printf("Lowest prime number in List : %s %n" , stats.getMin());
		System.out.printf("Sum of all prime numbers : %s %n" , stats.getSum());
		System.out.printf("Average of all prime numbers : %s %n" , stats.getAverage());
		primes.stream().forEach(System.out::println);
	}

	public static void handle(String event) {
		event = event + "123";
		System.out.println(event);
	}
}
