package com.example.demo.mq.rece;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.example.demo.entity.MakeUpBonus;
import com.example.demo.service.MakeUpBonusService;
import org.apache.commons.lang3.StringUtils;

/**
 * @author ww
 * @version 1.0
 * @date 2020/9/17 14:05
 */

public class MqTransactionListener implements MessageListener {
    private MakeUpBonusService makeUpBonusService;

    public MqTransactionListener(MakeUpBonusService makeUpBonusService){
        this.makeUpBonusService=makeUpBonusService;
    }

    @Override
    public Action consume(Message message, ConsumeContext context) {
//        LogTrace.beginTrace();
        try {
            String msg = new String(message.getBody(), "UTF-8");
//            LogTrace.info("接收到事务消息", "msg", JSON.toJSONString(message));
            if(StringUtils.isNotBlank(msg)){
                MakeUpBonus makeUpBonus = JSON.parseObject(msg, MakeUpBonus.class);
                boolean is=makeUpBonusService.consumeTransactionService(makeUpBonus);
//                LogTrace.info("接收到事务消息:","success","success");
                //消费成功
                return Action.CommitMessage;
            }
            //消费成功
            return Action.CommitMessage;
        } catch (Exception e){
//            LogTrace.error("接收到事务消息-异常",e);
            return Action.ReconsumeLater;
        }
    }
}
