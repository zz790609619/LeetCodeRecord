package com.example.demo.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.example.demo.config.plugin.SqlMonitorInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @Author ww
 * @Date 2020-04-22
 */
@Configuration
@MapperScan(basePackages = {"com.example.demo.data.mapper.main"}, sqlSessionFactoryRef = "apiMainSqlSessionFactory")
public class MainDataSourceConfig {

    @Value("${spring.datasource.type}")
    private String type;

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;


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



    @Bean(name = "dataSource")
    @Primary
    public DataSource apiMainDataSource() {
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
        sourceBean.setMaxPoolSize(maxActive);
        sourceBean.setUniqueResourceName("main0");
        return sourceBean;
    }

    @Bean(name = "apiMainSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver
                .getResources("classpath:mapper/*.xml"));
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{new SqlMonitorInterceptor()});
        return sqlSessionFactoryBean.getObject();
    }

}
