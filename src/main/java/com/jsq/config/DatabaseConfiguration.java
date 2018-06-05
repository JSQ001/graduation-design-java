package com.jsq.config;

import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;

import com.jsq.exception.DataSourceLoginTimeOUtException;
import com.jsq.exception.ValidationError;
import com.jsq.exception.ValidationException;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @description:
 * @version: 1.0
 * @author: wenzhou.tang@hand-china.com
 * @date: 2017/7/21 10:59
 */
@Configuration
@EnableConfigurationProperties({DataSourceProperties.class, MybatisProperties.class})

public class DatabaseConfiguration {
    private final DataSourceProperties dataSourceProperties;  // 数据源配置文件，在yml文件中spring:dataSource的相关配置
    private final MybatisProperties properties;            //  MybatisPlus配置文件，yml文件的配置在加载时会构建相关类的实例
    private final ResourceLoader resourceLoader;
    public DatabaseConfiguration(DataSourceProperties dataSourceProperties, MybatisProperties properties, ResourceLoader resourceLoader) {
        this.dataSourceProperties = dataSourceProperties;
        this.properties = properties;
        this.resourceLoader = resourceLoader;
    }
    @Bean    //配置数据源
    public DataSource dataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        //dataSource.setPassword(new String(Base64.getDecoder().decode(dataSourceProperties.getPassword())));
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        try {
            dataSource.setLoginTimeout(1000);
        }catch (SQLException e){
            throw new DataSourceLoginTimeOUtException("数据库连接超时");
        }
        dataSource.setMaximumPoolSize(100);
        return dataSource;
    }
    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource());
        mybatisSqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        if (StringUtils.hasText(this.properties.getConfigLocation())) {
            mybatisSqlSessionFactoryBean.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
        }
        mybatisSqlSessionFactoryBean.setConfiguration(properties.getConfiguration());
        mybatisSqlSessionFactoryBean.setPlugins(getInterceptors());
        GlobalConfiguration globalConfig = new GlobalConfiguration();
//      globalConfig.setDbType(heliosCloudProperties.getDatasource().getDbType());
        globalConfig.setIdType(2);
        globalConfig.setDbColumnUnderline(true);
//        globalConfig.setSqlInjector(new LogicSqlInjector());
//        globalConfig.setLogicDeleteValue("1");
//        globalConfig.setLogicNotDeleteValue("0");
//        globalConfig.setMetaObjectHandler(new DomainObjectMetaObjectHandler());
        mybatisSqlSessionFactoryBean.setGlobalConfig(globalConfig);
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        mybatisSqlSessionFactoryBean.setConfiguration(mybatisConfiguration);
        if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
            mybatisSqlSessionFactoryBean.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
        }
        if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
            mybatisSqlSessionFactoryBean.setMapperLocations(this.properties.resolveMapperLocations());
        }
        if (!ObjectUtils.isEmpty(this.properties.getTypeHandlersPackage())) {
            mybatisSqlSessionFactoryBean.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
        }
        return mybatisSqlSessionFactoryBean;
    }
    @Bean
    public Interceptor[] getInterceptors() {
        List<Interceptor> interceptors = new ArrayList<Interceptor>();
        Interceptor performanceInterceptor = new PerformanceInterceptor();
        OptimisticLockerInterceptor optimisticLockerInterceptor = new OptimisticLockerInterceptor();
        Properties performanceInterceptorProps = new Properties();
        //调整最长的sql查询时间为10s
        performanceInterceptorProps.setProperty("maxTime", "10000");
        performanceInterceptorProps.setProperty("format", "true");
        performanceInterceptor.setProperties(performanceInterceptorProps);
        PaginationInterceptor pagination = new PaginationInterceptor();
        pagination.setDialectType("mysql");
        interceptors.add(pagination);
        interceptors.add(performanceInterceptor);
        interceptors.add(optimisticLockerInterceptor);
        return interceptors.toArray(new Interceptor[]{});
    }

    /**

     * mybatis-plus分页插件
     */
  /*  @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }*/
}