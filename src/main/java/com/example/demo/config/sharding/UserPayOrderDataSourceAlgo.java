package com.example.demo.config.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @Author ww
 * @Date 2020-04-22
 */
public class UserPayOrderDataSourceAlgo implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {

        int postfix = (int)((shardingValue.getValue() / 30) % availableTargetNames.size());
        for (String dataSource : availableTargetNames) {
            if (dataSource.endsWith(String.valueOf(postfix))) {
                return dataSource;
            }
        }
        throw new IllegalArgumentException();
    }

}
