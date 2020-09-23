package com.example.demo;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapTest {
    Map<String,Integer> map =new HashMap<>();
    @Test
    public void test() {
        try{
            for (int i = 0; i < 100; i++) {
                map.put(String.valueOf(i),i);
            }
            ExecutorService executor= Executors.newFixedThreadPool(10);
            for (int i = 0; i < 100; i++) {
                RunnableWrite runnableWrite=new RunnableWrite(i+1);
                executor.execute(runnableWrite);
            }
            for (int i = 0; i < 100; i++) {
                RunnableRead runnableRead=new RunnableRead(i);
                executor.execute(runnableRead);
            }

            System.out.println(JSON.toJSONString(map));
        }catch (Exception e){

        }

    }
    public class RunnableWrite implements Runnable{
        private Integer integer;
        public RunnableWrite( Integer integer){
            this.integer=integer;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            map.put(String.valueOf(integer-1),integer);
        }
    }
    public class RunnableRead implements Runnable{
        private Integer integer;
        public RunnableRead( Integer integer){
            this.integer=integer;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            map.get(String.valueOf(integer));
        }
    }

}
