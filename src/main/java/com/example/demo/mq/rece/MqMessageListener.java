package com.example.demo.mq.rece;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.example.demo.mq.AliMqComponent;

/**
 * 消费者消费逻辑
 * 该监听被装载消费者bean中 consumer.setSubscriptionTable()
 * @Author ww
 * @Date 2020-04-27
 */
public class MqMessageListener implements MessageListener {
    //在@ConditionOnBean中配置过 这边则不需要自动装配
    private AliMqComponent aliMqComponent;
    public MqMessageListener(AliMqComponent aliMqComponent) {
        this.aliMqComponent=aliMqComponent;
    }

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {

        try {
            //获取消息内容
            String msg = new String(message.getBody(),"UTF-8");
            System.out.println("消息消费成功:"+msg);
            return Action.CommitMessage;
            // }
        } catch (Exception e) {
            return Action.ReconsumeLater;
        } finally {
        }
        // return null;
    }


}
