package com.example.demo.config;

import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlEsConfig {
    @Value("${es.host}")
    private String host;

    @Value("${es.port}")
    private Integer port;


    @Bean("restsHighLevelClient")
    public RestHighLevelClient restHighLevelClient(){
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        //密码和用户名未声明，所以未使用
       //credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(account,pwd));
        //连接elasticSearch端口
        RestClientBuilder builder = RestClient.builder(new HttpHost(host,port,"http"));
        return new RestHighLevelClient(builder);
    }
}
