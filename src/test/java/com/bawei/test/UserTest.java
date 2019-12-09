package com.bawei.test;

import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bawei.cms.bean.User;
import com.bawei.cms.util.TimeRandomUtil;
import com.bawei.cms.util.UserRandomUtil;

@SuppressWarnings("all")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring-context.xml")
public class UserTest {
	@Resource
	private RedisTemplate redisTemplate;

	@Test
	public void userTest() {
		//开始前时间
		long time1 = System.currentTimeMillis();
		
		ValueOperations ops = redisTemplate.opsForValue();
		//hash方式
		BoundHashOperations hashOps = redisTemplate.boundHashOps("hash_user");
		for(int i = 1;i<=50000;i++) {
			User user = new User();
			//id
			user.setId(i);
			//名字
			user.setName(UserRandomUtil.getChineseName());
			//性别
			user.setSex(getSex());
			//手机
			user.setPhone(getPhone());
			//邮箱
			user.setEmail(UserRandomUtil.getEmail());
			//年龄
			user.setBirthday(TimeRandomUtil.randomDate("1949-01-01 00:00:00", "2001-01-01 00:00:00"));
			//System.out.println(user);
			//将user对象存入redis里
			//ops.set(i+"", user);
			hashOps.put(i+"", user.toString());
			
		}
		//获取运行结束时的当前时间
		long time2 = System.currentTimeMillis();
		//运行结束时间
		long time = time2-time1;
		System.out.println("hash序列化,耗时:"+time);
	}
	
	//性别方法
	public static String getSex() {
		return new Random().nextBoolean()?"男":"女";
	}
	
	//获取手机号方法
	public static String getPhone() {
		String phone ="";
		for(int i =1;i<10;i++) {
			phone +=new Random().nextInt(10);
		}
		
		return "13"+phone;
	}
}
