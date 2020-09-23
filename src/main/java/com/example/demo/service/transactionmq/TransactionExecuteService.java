//package com.example.demo.service.transactionmq;
//
//import com.aliyun.openservices.ons.api.Message;
//import com.aliyun.openservices.ons.api.transaction.LocalTransactionExecuter;
//import com.aliyun.openservices.ons.api.transaction.TransactionStatus;
//import com.example.demo.service.MakeUpBonusService;
//import com.example.demo.util.HashUtil;
//import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * @author ww
// * @version 1.0
// * @date 2020/9/21 17:27
// */
//@Component
//@RocketMQTransactionListener
//public class TransactionExecuteService implements LocalTransactionExecuter {
//    @Autowired
//    MakeUpBonusService makeUpBonusService;
//    @Override
//    public TransactionStatus execute(Message msg, Object arg) {
//            // 消息 ID（有可能消息体一样，但消息 ID 不一样，当前消息属于半事务消息，所以消息 ID 在消息队列 RocketMQ 版控制台无法查询）。
//            String msgId = msg.getMsgID();
//            //LogTrace.info("事务消息","事务消息执行id",msgId);
//            // 消息体内容进行 crc32，也可以使用其它的如 MD5。
//            long crc32Id = HashUtil.crc32Code(msg.getBody());
//            // 消息 ID 和 crc32id 主要是用来防止消息重复。
//            // 如果业务本身是幂等的，可以忽略，否则需要利用 msgId 或 crc32Id 来做幂等。
//            // 如果要求消息绝对不重复，推荐做法是对消息体使用 crc32 或 MD5 来防止重复消息。
//            TransactionStatus transactionStatus = TransactionStatus.Unknow;
//            boolean isCommit =
//                    makeUpBonusService.execbusinessService();
//            //LogTrace.info("事务消息","事务消息执行结果",String.valueOf(isCommit));
//            if (isCommit) {
//                // 本地事务已成功则提交消息。
//                transactionStatus = TransactionStatus.CommitTransaction;
//            } else {
//                // 本地事务已失败则回滚消息。
//                transactionStatus = TransactionStatus.RollbackTransaction;
//            }
//            System.out.println(msg.getMsgID());
//            //LogTrace.info("Message Id:{}transactionStatus:{}", msgId, transactionStatus.name());
//            return transactionStatus;
//        };
//}
