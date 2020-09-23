package com.example.demo.mq;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.transaction.TransactionProducer;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;
import com.example.demo.config.mq.AliMqProduceConfig;
import com.example.demo.service.MakeUpBonusService;
import com.example.demo.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author cx
 * @Date 2019-10-29
 */
@Component
public class AliMqComponent {

    @Autowired
    private AliMqProduceConfig aliMqConfig;

    @Autowired
    MakeUpBonusService makeUpBonusService;

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

    /**
     * 发送mq消息
     */
    public String pushTransactionByTcp(String topic, String tag,String key, byte[] body) throws Exception {
        TransactionProducer producer = aliMqConfig.getTransactionProducer();
        //LogTrace.info("事务消息","事务发送体", JSON.toJSONString(producer));
        Message msg = new Message(topic, tag, body);
        msg.setKey(key);
        SendResult sendResult = producer.send(msg, (msg1, arg) -> {
            // 消息 ID（有可能消息体一样，但消息 ID 不一样，当前消息属于半事务消息，所以消息 ID 在消息队列 RocketMQ 版控制台无法查询）。
            String msgId = msg1.getMsgID();
            //LogTrace.info("事务消息","事务消息执行id",msgId);
            // 消息体内容进行 crc32，也可以使用其它的如 MD5。
            long crc32Id = HashUtil.crc32Code(msg1.getBody());
            // 消息 ID 和 crc32id 主要是用来防止消息重复。
            // 如果业务本身是幂等的，可以忽略，否则需要利用 msgId 或 crc32Id 来做幂等。
            // 如果要求消息绝对不重复，推荐做法是对消息体使用 crc32 或 MD5 来防止重复消息。
            TransactionStatus transactionStatus = TransactionStatus.Unknow;
            boolean isCommit =
                    makeUpBonusService.execbusinessService();
            //LogTrace.info("事务消息","事务消息执行结果",String.valueOf(isCommit));
            if (isCommit) {
                // 本地事务已成功则提交消息。
                transactionStatus = TransactionStatus.CommitTransaction;
            } else {
                // 本地事务已失败则回滚消息。
                transactionStatus = TransactionStatus.RollbackTransaction;
            }
            System.out.println(msg1.getMsgID());
            //LogTrace.info("Message Id:{}transactionStatus:{}", msgId, transactionStatus.name());
            return transactionStatus;
        }, null);
        return sendResult.getMessageId();
    }
}
