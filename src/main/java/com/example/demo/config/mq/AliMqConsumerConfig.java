package com.example.demo.config.mq;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.example.demo.mq.AliMqComponent;
import com.example.demo.mq.rece.MqMessageListener;
import com.example.demo.mq.rece.MqMessageSpringListener;
import com.example.demo.mq.rece.MqTransactionListener;
import com.example.demo.service.MakeUpBonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author ww
 * @Date 2020-04-27
 */
@Configuration
public class AliMqConsumerConfig {

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
     * tcp 消费者  MqMessageListener没有放入Spring管理 所以在中间无法直接依赖注入。需要以前放在接口里面
     *
     * @param
     * @return
     */
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    @ConditionalOnBean(name = "aliMqComponent") //如果需要注入其他的类或者接口 需要放在@ConditionalOnBean标签
    public ConsumerBean getCommonConsumer(AliMqComponent aliMqComponent) {
        ConsumerBean consumerBean = new ConsumerBean();
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.GROUP_ID, groupId);
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, accessKey);
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, secretKey);
        // 设置 TCP 接入域名，进入控制台的实例管理页面的“获取接入点信息”区域查看
        properties.put(PropertyKeyConst.NAMESRV_ADDR, namesrvAddr);
        consumerBean.setProperties(properties);
        //组装订阅者消息
        Map<Subscription, MessageListener> map = new HashMap<Subscription, MessageListener>();
        Subscription subscription = new Subscription();
        subscription.setTopic(topic);//设置需要消费的消息所属的topic
        subscription.setExpression(tag);//设置需要消费的消息所属的tag
        map.put(subscription, new MqMessageListener(aliMqComponent));//MqMessageListener实现MessageListener接口，并且在consume方法中实现消费逻辑
        consumerBean.setSubscriptionTable(map);//将订阅者消息放入consumerBean中，在Spring初始加载该bean的时候，监听MQ中的Topic和tag下的消息
        return consumerBean;
    }

    @Autowired
    MqMessageSpringListener mqMessageListener;
    /**
     * tcp 消费者  将MqMessageListener放入Spring管理 可以在逻辑里依赖注入其他类也可以获取配置文件
     *
     * @param
     * @return
     */
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ConsumerBean getCommonConsumer() {
        ConsumerBean consumerBean = new ConsumerBean();
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.GROUP_ID, groupId);
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, accessKey);
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, secretKey);
        // 设置 TCP 接入域名，进入控制台的实例管理页面的“获取接入点信息”区域查看
        properties.put(PropertyKeyConst.NAMESRV_ADDR, namesrvAddr);
        consumerBean.setProperties(properties);
        //组装订阅者消息
        Map<Subscription, MessageListener> map = new HashMap<Subscription, MessageListener>();
        Subscription subscription = new Subscription();
        subscription.setTopic(topic);//设置需要消费的消息所属的topic
        subscription.setExpression(tag);//设置需要消费的消息所属的tag
        map.put(subscription, mqMessageListener);//MqMessageListener实现MessageListener接口，并且在consume方法中实现消费逻辑
        consumerBean.setSubscriptionTable(map);//将订阅者消息放入consumerBean中，在Spring初始加载该bean的时候，监听MQ中的Topic和tag下的消息
        return consumerBean;
    }


    /**
     * 事务消费者
     *
     */
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    @ConditionalOnBean(name = {"makeUpBonusService"})
    public ConsumerBean getMerchantDearPowderConsumer(MakeUpBonusService makeUpBonusService) {
        ConsumerBean consumerBean = new ConsumerBean();
        Properties properties = new Properties();
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, accessKey);
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, secretKey);
        // 设置 TCP 接入域名，进入控制台的实例管理页面的“获取接入点信息”区域查看
        properties.put(PropertyKeyConst.NAMESRV_ADDR, namesrvAddr);
        properties.put(PropertyKeyConst.GROUP_ID, groupId);
        consumerBean.setProperties(properties);
        //组装订阅者消息
        Map<Subscription, MessageListener> map = new HashMap<Subscription, MessageListener>();
        Subscription consumeProduce = new Subscription();
        consumeProduce.setTopic(topic);
        map.put(consumeProduce, new MqTransactionListener(makeUpBonusService));
        consumerBean.setSubscriptionTable(map);
        return consumerBean;
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
