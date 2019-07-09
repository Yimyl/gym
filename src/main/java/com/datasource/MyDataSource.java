package com.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author ：Yimyl
 * @date ：Created in 2019/4/27 23:04
 * @description：
 * @modified By：
 * @version: $
 */
@Slf4j
@Configuration
public class MyDataSource {
    @Bean
//    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.user")
    public DataSourceProperties userDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "userDataSource")
    @Qualifier("userDataSource")
    @Primary
    public DataSource userDataSource() {
        DataSourceProperties dataSourceProperties = userDataSourceProperties();

        return dataSourceProperties.initializeDataSourceBuilder().build();
    }


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.gym")
    public DataSourceProperties gymDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "gymDataSource")
    @Qualifier("gymDataSource")
    public DataSource gymDataSource() {
        DataSourceProperties dataSourceProperties = gymDataSourceProperties();

//        log.info("gymdatasourceeeeeeeeeeeeee"+dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

}
