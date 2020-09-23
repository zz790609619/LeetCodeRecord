package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.config.MainDataSourceConfig;
import com.example.demo.data.mapper.main.MakeUpBonusMapper;
import com.example.demo.entity.MakeUpBonus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ww
 * @Date 2020-04-22
 */
@Service
public class MakeUpBonusService {

    @Autowired
    private MakeUpBonusMapper makeUpBonusMapper;
    @Autowired
    private MainDataSourceConfig config;

    public List<MakeUpBonus> getMakeUpBonusList() {
        List<MakeUpBonus> upo = makeUpBonusMapper.ss();
        return upo;
    }
    public void insertMakeUpBonus(MakeUpBonus makeUpBonus) {
        makeUpBonusMapper.insertMakeUpBonus(makeUpBonus);
    }

    @Transactional
    public void testMake(MakeUpBonus makeUpBonus) {
        makeUpBonusMapper.insertMakeUpBonus(makeUpBonus);
        MakeUpBonus makeUpBonus1=new MakeUpBonus();
        makeUpBonus1.setOrderId(makeUpBonus.getOrderId());
        makeUpBonus1=makeUpBonusMapper.sss(makeUpBonus1);
        System.out.println(JSON.toJSONString(makeUpBonus1));
    }

    @Transactional
    public MakeUpBonus test111(MakeUpBonus makeUpBonus) throws Exception {
        try{
            makeUpBonus.setOrderId("1");
            MakeUpBonus makeUpBonus1=null;
            makeUpBonus1.getMakeUpFlag();
        }catch (Exception e){
            System.out.println("xxx");
        }
        makeUpBonus=new MakeUpBonus();
        makeUpBonus.setOrderId("222");
        return makeUpBonus;
    }

    /**
     * 事务mq生产者执行逻辑服务
     */
    @Transactional
    public boolean execbusinessService(){
        MakeUpBonus makeUpBonus=new MakeUpBonus();
        makeUpBonus.setOrderId("mq生产者端事务执行逻辑");
        makeUpBonus.setMakeUpFlag(false);
        makeUpBonus.setPayType(1);
        makeUpBonus.setUserId(1L);
        makeUpBonus.setMerchantId(1L);
        makeUpBonusMapper.insertMakeUpBonus(makeUpBonus);
        return true;
    }

    /**
     * 生产者检查逻辑服务
     */
    public boolean checkbusinessService(){
        MakeUpBonus makeUpBonus=new MakeUpBonus();
        makeUpBonus.setOrderId("mq生产者端事务执行逻辑");
        MakeUpBonus makeUpBonus1=makeUpBonusMapper.sss(makeUpBonus);
        return makeUpBonus1!=null;
    }

    /**
     * 消费者执行逻辑服务
     */
    @Transactional
    public boolean consumeTransactionService(MakeUpBonus makeUpBonus){
        makeUpBonusMapper.insertMakeUpBonus(makeUpBonus);
//        throw new RuntimeException();
       return true;
    }
}
