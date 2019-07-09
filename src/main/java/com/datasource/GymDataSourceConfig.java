package com.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.Map;

/**
 * @author ：Yimyl
 * @date ：Created in 2019/4/27 22:40
 * @description：
 * @modified By：
 * @version: $
 */
@Slf4j
@Configuration
@EnableTransactionManagement
//@Transactional
@EnableJpaRepositories(
        entityManagerFactoryRef="entityManagerFactoryGym",
        transactionManagerRef="transactionManagerGym",
        basePackages= {"com.repositoryGym"}) //设置Repository所在位置
public class GymDataSourceConfig {

    @Autowired
    @Qualifier("gymDataSource")
    private DataSource gymDataSource;

    @Autowired
    private JpaProperties jpaProperties;

    @Bean(name = "entityManagerGym")
    public EntityManager entityManagerGym(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryGym(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactoryGym")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryGym (EntityManagerFactoryBuilder builder) {
        Map<String, String> properties = jpaProperties.getProperties();
        properties.forEach((k,v)->log.info("key:"+k+" ##### "+"value"+v));
        log.info("gymmmmm"+jpaProperties.getDatabasePlatform());
        return builder
                .dataSource(gymDataSource)
                .properties(jpaProperties.getProperties())
                .packages("com.modelGym") //设置实体类所在位置
                .persistenceUnit("GymPersistenceUnit")
                .build();
    }

    @Bean(name = "transactionManagerGym")
//    @Resource
    public PlatformTransactionManager transactionManagerGym(DataSource gymDataSource) {
        return new DataSourceTransactionManager(gymDataSource);
    }

}
