package com.example.demo.service;

import com.example.demo.config.MainDataSourceConfig;
import com.example.demo.data.mapper.main.MakeUpBonusMapper;
import com.example.demo.entity.MakeUpBonus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ww
 * @Date 2020-04-22
 */
@Service
public class MakeUpBonusService {

    @Autowired
    private MakeUpBonusMapper userPayOrderMapper;
    @Autowired
    private MainDataSourceConfig config;

    public List<MakeUpBonus> getMakeUpBonusList() {
        List<MakeUpBonus> upo = userPayOrderMapper.ss();
        return upo;
    }
    public void insertMakeUpBonus(MakeUpBonus makeUpBonus) {
        userPayOrderMapper.insertMakeUpBonus(makeUpBonus);
    }


}
