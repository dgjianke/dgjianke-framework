package com.cmcc.zysoft.spring_mvc.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class HashMapTest {
	
	private static final Logger logger = LogManager.getLogger(HashMapTest.class);
	
	public void doit() throws InterruptedException {
		final int count = 2000;
		final AtomicInteger  checkNum = new AtomicInteger(0);
		ExecutorService pool = Executors.newFixedThreadPool(100);
		final Map<Long, String> map = new HashMap<Long, String>(16);
		//final Map<Long, String> map = new ConcurrentHashMap<Long, String>(16);
		 map.put(0L, "www.imxylz.cn");
		 for(int j=0;j<count;j++) {
			 pool.submit(new Runnable() {
				@Override
				public void run() {
					 map.put(System.nanoTime()+new Random().nextLong(), "www.imxylz.cn");
					 String obj = map.get(0L);
					 if (obj == null) {
						 checkNum.incrementAndGet();
					 }
				}
			});
		 }
		 pool.shutdown();//shutdown-不添加新的任务
		 pool.awaitTermination(10000, TimeUnit.MILLISECONDS);//只有shutdown状态下才会所有线程跑完结束掉
		 logger.info("checkNum:{}",checkNum);
	}
	
	//@Test
	public void test1() throws InterruptedException {
		for(int i=0;i<100;i++) {
			doit();
			Thread.sleep(500);
		}
	}
	
	public void doit2() throws InterruptedException {
		final int count = 2000;
		int checkNum = 0;
		ExecutorService pool = Executors.newFixedThreadPool(100);
		final AtomicInteger  c = new AtomicInteger(0);
		//final List<Integer> list  = new ArrayList<>();
		final List<Integer> list  = new CopyOnWriteArrayList<>();
		//final List<Integer> list  = Collections.synchronizedList(new ArrayList<Integer>());
		//final Map<Long, String> map = new ConcurrentHashMap<Long, String>(16);
		 for(int j=0;j<count;j++) {
			 pool.submit(new Runnable() {
				@Override
				public void run() {
					 list.add(c.getAndIncrement());
				}
			});
		 }
		 pool.shutdown();//shutdown-不添加新的任务
		 pool.awaitTermination(10000, TimeUnit.MILLISECONDS);//只有shutdown状态下才会所有线程跑完结束掉
		 for(int i=0;i<list.size();i++) {
			 if(list.get(i)!=null) {
				// logger.info("i:{},get:{}",i,list.get(i));
			 }
			 if(list.get(i)==null) { //线程之间的执行是无序的，故不用判断是否值也对应
				 checkNum++;
			 }
		 }
		 logger.info("checkNum:{}",checkNum);
	}
	
	
	@Test
	public void test2() throws InterruptedException {
		for(int i=0;i<10;i++) {
			doit2();
			Thread.sleep(500);
		}
	}
}
