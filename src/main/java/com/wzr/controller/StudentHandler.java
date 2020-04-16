package com.wzr.controller;

import com.wzr.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class StudentHandler {
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/set")
    public void set(@RequestBody Student student){
        System.out.println("进入");
        redisTemplate.opsForValue().set("student",student);
    }

    @GetMapping("/get/{key}")
    public Student getStudent(@PathVariable("key") String key){
        return (Student) redisTemplate.opsForValue().get(key);
    }

    @DeleteMapping("/del/{key}")
    public boolean delStudent(@PathVariable("key") String key){
        return redisTemplate.delete(key);
    }

    @GetMapping("/string")
    public String getString(){
        redisTemplate.opsForValue().set("string","HelloWorld");
        String str = (String) redisTemplate.opsForValue().get("string");
        return str;
    }

    @GetMapping("/list")
    public List<String> listTest(){
        ListOperations<String,String> listOperations = redisTemplate.opsForList();
        listOperations.leftPush("list","Hello");
        listOperations.leftPush("list","World");
        listOperations.leftPush("list","Java");
        List<String> list = listOperations.range("list",0,2);
        return list;
    }

    @GetMapping("/set")
    public Set<String> setTest(){
        SetOperations<String,String> setOperations = redisTemplate.opsForSet();
        setOperations.add("set","Hello");
        setOperations.add("set","Hello");
        setOperations.add("set","World");
        setOperations.add("set","Java");
        setOperations.add("set","SpringBoot");
        Set<String> set =  setOperations.members("set");
        return set;
    }

    @GetMapping("/zset")
    public Set<String> zSetTest(){
        ZSetOperations<String,String> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("zset","Hello",1);
        zSetOperations.add("zset","Java",3);
        zSetOperations.add("zset","World",2);
        Set<String> zset = zSetOperations.range("zset", 0, 2);
        return zset;
    }
}
