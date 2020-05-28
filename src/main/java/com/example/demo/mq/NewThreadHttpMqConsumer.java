package com.example.demo.mq;

import com.aliyun.mq.http.MQClient;
import com.aliyun.mq.http.MQConsumer;
import com.aliyun.mq.http.common.AckMessageException;
import com.aliyun.mq.http.model.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Http消费者 新开线程
 *
 * @param
 * @return
 */
@Configuration
public class NewThreadHttpMqConsumer implements ServletContextListener {


    /**
     * Http 跨平台
     */
    @Value("${aliyun.mq.http.instanceId}")
    private String instanceId;//实例id
    @Value("${aliyun.mq.http.access_key}")
    private String httpAccessKey;
    @Value("${aliyun.mq.http.secret_key}")
    private String httpSecretKey;
    @Value("${aliyun.mq.http.http_addr}")
    private String httpHttpAddr;//http地址
    /**
     * topic、group、tag
     */
    @Value("${aliyun.mq.http.group_id}")
    private String httpGroupId;
    @Value("${aliyun.mq.http.topic}")
    private String httpTopic;
    @Value("${aliyun.mq.http.tag}")
    private String httpTag;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        for (int i = 1; i <= 2; i++) {
            new Thread(() -> cxtConsumer()).start();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    public void cxtConsumer() {
        MQClient mqClient = new MQClient(httpHttpAddr, httpAccessKey, httpSecretKey);
        MQConsumer consumer = mqClient.getConsumer(instanceId, httpTopic, httpGroupId, null);

        // 在当前线程循环消费消息，建议是多开个几个线程并发消费消息
        do {
            List<Message> messages = null;
            try {
                // 长轮询消费消息
                // 长轮询表示如果topic没有消息则请求会在服务端挂住3s，3s内如果有消息可以消费则立即返回
                messages = consumer.consumeMessage(
                        3,// 一次最多消费3条(最多可设置为16条)
                        3// 长轮询时间3秒（最多可设置为30秒）
                );
            } catch (Throwable e) {
                e.printStackTrace();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            // 没有消息
            if (messages == null || messages.isEmpty()) {
                continue;
            }

            // 处理业务逻辑
            for (Message message : messages) {
                try {
                    //将传输正文转换成utf-8的json字符串
                    String msg = new String(message.getMessageBodyBytes(), "utf-8");
                }catch (Exception e){

                }

            }

            // Message.nextConsumeTime前若不确认消息消费成功，则消息会重复消费
            // 消息句柄有时间戳，同一条消息每次消费拿到的都不一样
            {
                List<String> handles = new ArrayList<String>();
                for (Message message : messages) {
                    handles.add(message.getReceiptHandle());
                }

                try {
                    consumer.ackMessage(handles);
                } catch (Throwable e) {
                    // 某些消息的句柄可能超时了会导致确认不成功
                    if (e instanceof AckMessageException) {
                        AckMessageException errors = (AckMessageException) e;
                        System.out.println("Ack message fail, requestId is:" + errors.getRequestId() + ", fail handles:");
                        if (errors.getErrorMessages() != null) {
                            for (String errorHandle : errors.getErrorMessages().keySet()) {
                                System.out.println("Handle:" + errorHandle + ", ErrorCode:" + errors.getErrorMessages().get(errorHandle).getErrorCode()
                                        + ", ErrorMsg:" + errors.getErrorMessages().get(errorHandle).getErrorMessage());
                            }
                        }
                        continue;
                    }
                    e.printStackTrace();
                }
            }
        } while (true);

    }
}
