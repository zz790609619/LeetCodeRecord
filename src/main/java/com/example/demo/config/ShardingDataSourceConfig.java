package com.example.demo.config;

import com.example.demo.config.sharding.*;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @Author ww
 * @Date 2020-04-22
 */
@Component
public class ShardingDataSourceConfig {

    private DataSource shardingDataSource;
    @Resource(name = "shardingdsDataSource")
    private DataSource shardingdsDataSource;
    @Resource(name = "shardingOneDataSource")
    private DataSource shardingOneDataSource;
    @Resource(name = "shardingTwoDataSource")
    private DataSource shardingTwoDataSource;

    @PostConstruct
    public void init() throws SQLException {
        Map<String ,DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds0",shardingdsDataSource);
        dataSourceMap.put("ds1",shardingOneDataSource);
        dataSourceMap.put("ds2",shardingTwoDataSource);
        /**
         * public final class ShardingRuleConfiguration {
         *     //默认数据源名称
         *     private String defaultDataSourceName;
         *     //表规则配置
         *     private Collection<TableRuleConfiguration> tableRuleConfigs = new LinkedList<>();
         *     //相同表分片规则的组，如果表分片规则相同，则可以放在一个组里。
         *     private Collection<String> bindingTableGroups = new LinkedList<>();
         *     //默认数据库的分片算法配置
         *     private ShardingStrategyConfiguration defaultDatabaseShardingStrategyConfig;
         *     //默认表的分片算法配置
         *     private ShardingStrategyConfiguration defaultTableShardingStrategyConfig;
         *     //默认键的生成工具类
         *     private KeyGenerator defaultKeyGenerator;
         *     //主备配置信息
         *     private Collection<MasterSlaveRuleConfiguration> masterSlaveRuleConfigs = new LinkedList<>();
         * }
         */
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        shardingRuleConfiguration.getBindingTableGroups().addAll(Arrays.asList(
                "user_pay_order"
        ));

        List<TableRuleConfiguration> tableRuleConfigurationList = new ArrayList<>();
        /**
         *   //逻辑表名
         *     private String logicTable;
         *     //真实的数据节点名称
         *     private String actualDataNodes;
         *     //数据库分片算法配置
         *     private ShardingStrategyConfiguration databaseShardingStrategyConfig;
         *     //表分片算法配置
         *     private ShardingStrategyConfiguration tableShardingStrategyConfig;
         *     //自动生成键的名称
         *     private String keyGeneratorColumnName;
         *     //自动生成键的工具类
         *     private KeyGenerator keyGenerator;
         *
         *     private String logicIndex;
         */
        TableRuleConfiguration tableRuleConfiguration =  new TableRuleConfiguration("user_pay_order","ds${0..2}.user_pay_order_${0..29}");
        tableRuleConfiguration.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_pay_id",new ShardingAlgorithmLong()));
        tableRuleConfiguration.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_pay_id",new UserPayOrderDataSourceAlgo()));
        tableRuleConfigurationList.add(tableRuleConfiguration);

        shardingRuleConfiguration.getTableRuleConfigs().addAll(tableRuleConfigurationList);
        shardingDataSource =  ShardingDataSourceFactory.createDataSource(dataSourceMap,shardingRuleConfiguration,new Properties());
    }

    public DataSource getDataSource() {
        return shardingDataSource;
    }
}
