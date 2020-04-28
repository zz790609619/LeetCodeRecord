package com.example.demo.mq;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.example.demo.config.mq.AliMqConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author cx
 * @Date 2019-10-29
 */
@Component
public class AliMqComponent {

    @Autowired
    private AliMqConfig aliMqConfig;

    /**
     * 普通发送消息
     *
     * @param topic 主题
     * @param tag   多个用| * 代表所有
     * @param key
     * @param body
     */
    public SendResult sendMessage(String topic, String tag, String key, byte[] body) {
        //获取生产者bean(配置好地址,access_key,access_secret)
        Producer producer = aliMqConfig.getProducer();
        //组装消息 设置topic tag body key
        Message msg = new Message(topic, tag, body);
        msg.setKey(key);
        try {
            //调用生产者bean的send方法，并且获取返回内容
            SendResult sendResult = producer.send(msg);
            if (null != sendResult) {
                return sendResult;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
         * 发送延时消息
         *
         * @param topic     主题
         * @param tag       多个用| * 代表所有
         * @param key
         * @param body      内容
         * @param delayTime 延时时间，单位毫秒
         * @return
         */
        public SendResult sendDelayMessage(String topic, String tag, String key, byte[] body, long delayTime) {
            Producer producer = aliMqConfig.getProducer();
            Message msg = new Message(topic, tag, body);
            msg.setKey(key);
            msg.setStartDeliverTime(delayTime);
            try {
                SendResult sendResult = producer.send(msg);
                if (null != sendResult) {
                    return sendResult;
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

    /**
     * 普通发送信息
     * @param key
     * @param body
     * @return
     */
    public String sendCommonMessage(String topic, String tag, String key, byte[] body) {
        Producer producer = aliMqConfig.getCommonProducer();
        Message msg = new Message(topic, tag, body);
        msg.setKey(key);
        try {
            SendResult sendResult = producer.send(msg);
            if (null != sendResult) {
                return sendResult.getMessageId();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("xxx");
        }

        return null;
    }


}
