package com.example.demo.config.mq;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.example.demo.mq.AliMqComponent;
import com.example.demo.mq.rece.MqMessageListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * @Author ww
 * @Date 2020-04-27
 */
@Configuration
public class AliMqConfig {

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

    @Value("${aliyun.mq.groupId}")
    private String groupId;
    @Value("${aliyun.mq.topic}")
    private String topic;
    @Value("${aliyun.mq.tag}")
    private String tag;
/**
 *      指定组建的init方法和destroy的几种方法
 *      在new该类的时候 会自动执行initMethod
 *      1：在配置类中 @Bean(initMethod = "init",destroyMethod = "destory")注解指定
 *      2：实现InitializingBean接口重写其afterPropertiesSet方法，实现DisposableBean接口重写destroy方法
 *      3：利用java的JSR250规范中的@PostConstruct标注在init方法上，@PreDestroy标注在destroy注解上
 */

    /**
     * 注册生产者Bean
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
     * 生产者
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
     * 消费者
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
}
