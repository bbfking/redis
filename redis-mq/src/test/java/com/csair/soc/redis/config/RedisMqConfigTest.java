package com.csair.soc.redis.config;

import com.csair.soc.redis.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pfxiong on 2015/12/14 23:40.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RedisMqConfig.class})
public class RedisMqConfigTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * redis存储字符串
     */
    @Test
    public void testString() {
        System.out.println("begin testString");
//        stringRedisTemplate.getConnectionFactory().getConnection().flushDb();
        //-----添加数据----------
        ValueOperations<String,String> ofv = stringRedisTemplate.opsForValue();
        ofv.set("name", "hello");//向key-->name中放入了value-->xinxin
        System.out.println(ofv.get("name"));//执行结果：xinxin

        ofv.append("name", " world"); //拼接
        System.out.println(ofv.get("name"));

        stringRedisTemplate.delete("name");//删除某个键
        System.out.println(ofv.get("name"));
        //设置多个键值对
        Map<String,String> map = new HashMap<>();
        map.put("name", "xiaoming");
        map.put("age", "23");
        map.put("qq", "476777XXX");
        ofv.multiSet(map);
        ofv.increment("age", 1L);
        System.out.println(ofv.get("name") + "-" + ofv.get("age") + "-" + ofv.get("qq"));
    }

    @Test
    public void testObject(){
        System.out.println("begin testObject");
//        redisTemplate.getConnectionFactory().getConnection().flushDb();
        ValueOperations<String,User> ofv  = redisTemplate.opsForValue();
        User user = new User();
        user.setAge(20);
        user.setId("1");
        user.setName("mike");
        ofv.set("user", user);
        System.out.println(ofv.get("user"));
    }
}
