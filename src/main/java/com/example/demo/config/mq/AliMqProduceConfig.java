package com.example.demo.config.mq;

import com.aliyun.mq.http.MQClient;
import com.aliyun.mq.http.MQProducer;
import com.aliyun.mq.http.model.TopicMessage;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.aliyun.openservices.ons.api.transaction.TransactionProducer;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;
import com.example.demo.service.MakeUpBonusService;
import com.example.demo.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
import java.util.UUID;

/**
 * @Author ww
 * @Date 2020-04-27
 */
@Configuration
public class AliMqProduceConfig {

    /**
     * tcp 相同平台/语言 参数
     */
    @Value("${aliyun.mq.namesrv_addr}")
    private String namesrvAddr;
    @Value("${aliyun.mq.suspend_time_millis}")
    private String suspendTimeMillis;
    @Value("${aliyun.mq.max_reconsume_times}")
    private String maxReconsumeTimes;
    @Value("${aliyun.mq.access_key}")
    private String accessKey;
    @Value("${aliyun.mq.secret_key}")
    private String secretKey;
    /**
     * topic、group、tag
     */
    @Value("${aliyun.mq.groupId}")
    private String groupId;
    @Value("${aliyun.mq.topic}")
    private String topic;
    @Value("${aliyun.mq.tag}")
    private String tag;
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

    @Autowired
    MakeUpBonusService makeUpBonusService;


/**
 *      指定组建的init方法和destroy的几种方法
 *      在new该类的时候 会自动执行initMethod
 *      1：在配置类中 @Bean(initMethod = "init",destroyMethod = "destory")注解指定
 *      2：实现InitializingBean接口重写其afterPropertiesSet方法，实现DisposableBean接口重写destroy方法
 *      3：利用java的JSR250规范中的@PostConstruct标注在init方法上，@PreDestroy标注在destroy注解上
 */

    /**
     *  tcp 注册生产者Bean
     *
     * @return
     */
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ProducerBean getProducer() {
        ProducerBean producerBean = new ProducerBean();
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.GROUP_ID, groupId);
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, accessKey);
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, secretKey);
        // 设置 TCP 接入域名，进入控制台的实例管理页面的“获取接入点信息”区域查看
        properties.put(PropertyKeyConst.NAMESRV_ADDR, namesrvAddr);
        producerBean.setProperties(properties);
        return producerBean;
    }

    /**
     * tcp 生产者
     *
     * @return
     */
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ProducerBean getCommonProducer() {
        ProducerBean producerBean = new ProducerBean();
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.GROUP_ID, groupId);
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, accessKey);
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, secretKey);
        // 设置 TCP 接入域名，进入控制台的实例管理页面的“获取接入点信息”区域查看
        properties.put(PropertyKeyConst.NAMESRV_ADDR, namesrvAddr);
        properties.put(PropertyKeyConst.InstanceName, UUID.randomUUID().toString());
        producerBean.setProperties(properties);
        return producerBean;
    }

    /**
     * Http发送mq消息
     *
     * @param requestContext 发送内容
     * @param tag
     * @return
     */
    public String sendMessageByHttp(String requestContext, String tag) {
        MQClient mqClient = new MQClient(httpHttpAddr,  httpAccessKey, secretKey);
        MQProducer producer = mqClient.getProducer(instanceId, httpSecretKey);
        TopicMessage topicMessage = new TopicMessage(
                requestContext.getBytes(),
                tag
        );
        TopicMessage pubResultMsg = producer.publishMessage(topicMessage);
        String messageId = pubResultMsg.getMessageId();
        mqClient.close();
        return messageId;
    }

    /**
     * 事务MQ Bean
     *
     * @return
     */
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public TransactionProducer getTransactionProducer() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.GROUP_ID, groupId);
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, accessKey);
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, secretKey);
        // 设置 TCP 接入域名，进入控制台的实例管理页面的“获取接入点信息”区域查看
        properties.put(PropertyKeyConst.NAMESRV_ADDR, namesrvAddr);
        return ONSFactory.createTransactionProducer(properties,
                msg -> {
                    //消息 ID（有可能消息体一样，但消息 ID 不一样，当前消息属于半事务消息，所以消息 ID 在消息队列 RocketMQ 版控制台无法查询）。
                    String msgId = msg.getMsgID();
                    //Log.info("事务消息","检查事务状态id",msgId);
                    //消息体内容进行 crc32，也可以使用其它的方法如 MD5。
                    long crc32Id = HashUtil.crc32Code(msg.getBody());
                    //消息 ID 和 crc32Id 主要是用来防止消息重复。
                    //如果业务本身是幂等的，可以忽略，否则需要利用 msgId 或 crc32Id 来做幂等。
                    //如果要求消息绝对不重复，推荐做法是对消息体使用 crc32 或 MD5 来防止重复消息。
                    //业务自己的参数对象，这里只是一个示例，需要您根据实际情况来处理。
                    TransactionStatus transactionStatus = TransactionStatus.Unknow;
                    try {
                        boolean isCommit = makeUpBonusService.checkbusinessService();
//                        LogTrace.info("事务消息","检查事务状态", String.valueOf(isCommit));
                        if (isCommit) {
                            //本地事务已成功则提交消息。
                            transactionStatus = TransactionStatus.CommitTransaction;
                        } else {
                            //本地事务已失败则回滚消息。
                            transactionStatus = TransactionStatus.RollbackTransaction;
                        }
                    } catch (Exception e) {
                        System.out.println("事务mq Message Id:{}"+ msgId);
                    }
                    //LogTrace.info("Message Id:{}transactionStatus:{}", msgId, transactionStatus.name());
                    return transactionStatus;
                });
    }

//    /**
//     * Http消费mq消息
//     * //@Bean(initMethod = "start", destroyMethod = "shutdown")注释是因为没有消息 所以会一直报错
//     * 会阻塞主线程 所以需要另开一个线程(NewThreadHttpMqConsumer)
//     * @param
//     * @return
//     */
//    //@Bean(initMethod = "start", destroyMethod = "shutdown")
//    public ConsumerBean getCxUserSynchronizeUserPlatform() {
//        MQClient mqClient = new MQClient(httpHttpAddr, httpAccessKey, httpSecretKey);
//        MQConsumer consumer = mqClient.getConsumer(instanceId, httpTopic, httpGroupId, null);
//
//        // 在当前线程循环消费消息，建议是多开个几个线程并发消费消息
//        do {
//            List<Message> messages = null;
//            try {
//                // 长轮询消费消息
//                // 长轮询表示如果topic没有消息则请求会在服务端挂住3s，3s内如果有消息可以消费则立即返回
//                messages = consumer.consumeMessage(
//                        3,// 一次最多消费3条(最多可设置为16条)
//                        3// 长轮询时间3秒（最多可设置为30秒）
//                );
//            } catch (Throwable e) {
//                e.printStackTrace();
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }
//            }
//            // 没有消息
//            if (messages == null || messages.isEmpty()) {
//                continue;
//            }
//
//            // 处理业务逻辑
//            for (Message message : messages) {
//                try {
//                    //将传输正文转换成utf-8的json字符串
//                    String msg = new String(message.getMessageBodyBytes(), "utf-8");
//                }catch (Exception e){
//
//                }
//
//            }
//
//            // Message.nextConsumeTime前若不确认消息消费成功，则消息会重复消费
//            // 消息句柄有时间戳，同一条消息每次消费拿到的都不一样
//            {
//                List<String> handles = new ArrayList<String>();
//                for (Message message : messages) {
//                    handles.add(message.getReceiptHandle());
//                }
//
//                try {
//                    consumer.ackMessage(handles);
//                } catch (Throwable e) {
//                    // 某些消息的句柄可能超时了会导致确认不成功
//                    if (e instanceof AckMessageException) {
//                        AckMessageException errors = (AckMessageException) e;
//                        System.out.println("Ack message fail, requestId is:" + errors.getRequestId() + ", fail handles:");
//                        if (errors.getErrorMessages() != null) {
//                            for (String errorHandle : errors.getErrorMessages().keySet()) {
//                                System.out.println("Handle:" + errorHandle + ", ErrorCode:" + errors.getErrorMessages().get(errorHandle).getErrorCode()
//                                        + ", ErrorMsg:" + errors.getErrorMessages().get(errorHandle).getErrorMessage());
//                            }
//                        }
//                        continue;
//                    }
//                    e.printStackTrace();
//                }
//            }
//        } while (true);
//    }

}
