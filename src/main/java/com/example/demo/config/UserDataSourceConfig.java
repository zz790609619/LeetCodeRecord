package com.example.demo.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @Author ww
 * @Date 2020-04-22
 */
@Configuration
public class UserDataSourceConfig {

    @Value("${spring.user.datasource.ds0.type}")
    private String type;

    @Value("${spring.user.datasource.ds0.driver-class-name}")
    private String driverClassName;

    @Value("${spring.user.datasource.ds0.url}")
    private String url;

    @Value("${spring.user.datasource.ds0.username}")
    private String userName;

    @Value("${spring.user.datasource.ds0.password}")
    private String password;

    @Value("${spring.user.datasource.ds1.type}")
    private String typeOne;

    @Value("${spring.user.datasource.ds1.driver-class-name}")
    private String driverClassNameOne;

    @Value("${spring.user.datasource.ds1.url}")
    private String urlOne;

    @Value("${spring.user.datasource.ds1.username}")
    private String userNameOne;

    @Value("${spring.user.datasource.ds1.password}")
    private String passwordOne;

    @Value("${spring.user.datasource.ds2.type}")
    private String typeTwo;

    @Value("${spring.user.datasource.ds2.driver-class-name}")
    private String driverClassNameTwo;

    @Value("${spring.user.datasource.ds2.url}")
    private String urlTwo;

    @Value("${spring.user.datasource.ds2.username}")
    private String userNameTwo;

    @Value("${spring.user.datasource.ds2.password}")
    private String passwordTwo;


    @Value("${spring.datasource.initialSize}")
    private Integer initialSize;

    @Value("${spring.datasource.maxActive}")
    private Integer maxActive;

    @Value("${spring.datasource.minIdle}")
    private Integer minIdle;

    @Value("${spring.datasource.maxWait}")
    private Long maxWait;

    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private Long timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private Long minEvictableIdleTimeMillis;

    @Value("${spring.datasource.filters}")
    private String filters;




    @Bean(name = "shardingdsDataSource")
    public DataSource shardingDataSource() {
        AtomikosDataSourceBean sourceBean = new AtomikosDataSourceBean();
        DruidXADataSource druidDataSource = new DruidXADataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(password);
        druidDataSource.setDriverClassName(driverClassName);

        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);

        try {
            druidDataSource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sourceBean.setXaDataSource(druidDataSource);
        sourceBean.setUniqueResourceName("ds0");
        sourceBean.setMaxPoolSize(maxActive);
        return sourceBean;
    }

    @Bean(name = "shardingOneDataSource")
    public DataSource shardingOneDataSource() {
        AtomikosDataSourceBean sourceBean = new AtomikosDataSourceBean();
        DruidXADataSource druidDataSource = new DruidXADataSource();
        druidDataSource.setUrl(urlOne);
        druidDataSource.setUsername(userNameOne);
        druidDataSource.setPassword(passwordOne);
        druidDataSource.setDriverClassName(driverClassNameOne);

        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        try {
            druidDataSource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sourceBean.setXaDataSource(druidDataSource);
        sourceBean.setMaxPoolSize(maxActive);
        sourceBean.setUniqueResourceName("ds1");

        return sourceBean;
    }

    @Bean(name = "shardingTwoDataSource")
    public DataSource shardingTwoDataSource() {
        AtomikosDataSourceBean sourceBean = new AtomikosDataSourceBean();
        DruidXADataSource druidDataSource = new DruidXADataSource();
        druidDataSource.setUrl(urlTwo);
        druidDataSource.setUsername(userNameTwo);
        druidDataSource.setPassword(passwordTwo);
        druidDataSource.setDriverClassName(driverClassNameTwo);

        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        try {
            druidDataSource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sourceBean.setXaDataSource(druidDataSource);
        sourceBean.setMaxPoolSize(maxActive);
        sourceBean.setUniqueResourceName("ds2");
        return sourceBean;
    }


}
