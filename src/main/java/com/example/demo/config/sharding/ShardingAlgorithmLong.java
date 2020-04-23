package com.example.demo.config.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @Author ww
 * @Date 2020-04-22
 */
public class ShardingAlgorithmLong implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        String postfix = "" +(preciseShardingValue.getValue() % collection.size());
        for (String tableName : collection){
            if(tableName.endsWith(postfix)){
                return tableName;
            }
        }
        throw new IllegalArgumentException("没有匹配到id:"+preciseShardingValue.getValue());
    }
}
