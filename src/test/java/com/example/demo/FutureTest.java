package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FutureTest {
    @Test
    public void test() {
        ExecutorService executor=Executors.newFixedThreadPool(10);
    }

    /**
     * 通过线程池提交Callable,返回的future.get()获取返回值
     */
    public void callableAndFutureByThreadPools() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
            sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
            System.out.println("主线程开始运行:" + Thread.currentThread().getName() + sdf.format(new Date()));
            //新开一个线程去处理
            ExecutorService executor = Executors.newSingleThreadExecutor();
            //Callable
            Callable<String> call = () -> getDelayData();
            Future future = executor.submit(call);
            //主线程正常运行 同时 子线程也在执行
            System.out.println("子线程运行:" + Thread.currentThread().getName() + sdf.format(new Date()));
            //主线程等待 Future返回结果
            String s = (String) future.get();
            System.out.println("子线程得到结果：" + s + sdf.format(new Date()));
            System.out.println("主线程结束:" + Thread.currentThread().getName() + sdf.format(new Date()));
        } catch (Exception e) {

        }

    }

    /**
     * 通过新建线程提交Callable,返回的future.get()获取返回值
     */
    public void callableAndFutureByNewThread() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
            sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
            System.out.println("主线程开始运行:" + Thread.currentThread().getName() + sdf.format(new Date()));
            //新开一个FutureTask去包装callable对象
            Callable<String> call = () -> getDelayData();
            FutureTask futureTask = new FutureTask(call);
            //新开一个线程去处理
            new Thread(futureTask).start();
            //主线程正常运行 同时 子线程也在执行
            System.out.println("子线程运行:" + Thread.currentThread().getName() + sdf.format(new Date()));
            //主线程等待 Future返回结果
            String s = (String) futureTask.get();
            System.out.println("子线程得到结果：" + s + sdf.format(new Date()));
            System.out.println("主线程结束:" + Thread.currentThread().getName() + sdf.format(new Date()));
        } catch (Exception e) {

        }

    }

    public String getDelayData() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
        System.out.println("Delay" + Thread.currentThread().getName() + sdf.format(new Date()));
        Thread.currentThread().wait(10000);
        System.out.println("Delay" + Thread.currentThread().getName() + sdf.format(new Date()));
        return "DelayData";
    }

}
