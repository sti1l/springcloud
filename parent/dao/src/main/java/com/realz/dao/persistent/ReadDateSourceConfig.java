package com.realz.dao.persistent;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef="readentityManagerFactory",
						basePackages= {"com.realz.dao"},
						transactionManagerRef="readTransactionManager") 
public class ReadDateSourceConfig {
	
	/**
	 * 定义数据源
	 * @return
	 */
	@Bean("readdataSource")  
    @ConfigurationProperties(prefix = "spring.datasource.read")  
    public DataSource dataSource() {  
        DruidDataSource druidDataSource = new DruidDataSource();  
        return druidDataSource;  
    }  
	
	/**
	 * 定义实体管理器
	 * @param builder
	 * @return
	 */
    @Bean("readentityManagerFactory")
    public LocalContainerEntityManagerFactoryBean readentityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource())
                .packages("com.jhtsoft.entity") //设置实体类所在位置
                .persistenceUnit("readPersistenceUnit")
                .build();
    }
    
    
    /**
     * 定义事务管理器
     * @param factory
     * @return
     */
    @Bean(name = "readTransactionManager")  
    public PlatformTransactionManager readTransactionManager(@Qualifier("readentityManagerFactory")LocalContainerEntityManagerFactoryBean factory) {  
        return new JpaTransactionManager(factory.getObject());  
    }  
    
}

