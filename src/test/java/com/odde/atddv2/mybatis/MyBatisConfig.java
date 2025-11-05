package com.odde.atddv2.mybatis;

import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.odde.atddv2.mybatis.mapper")
public class MyBatisConfig {
    @SneakyThrows
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        // 🔧 Optional: set location of MyBatis mapper XML files (if you use them)
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath*:mybatis/*.xml"));

        // 🔧 Optional: load MyBatis global configuration (mybatis-config.xml)
        // factoryBean.setConfigLocation(resolver.getResource("classpath:mybatis/mybatis-config.xml"));

        // 🔧 Optional: set type alias package (for simplifying class names in mappers)
        factoryBean.setTypeAliasesPackage("com.odde.atddv2.mybatis.entity");

        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
