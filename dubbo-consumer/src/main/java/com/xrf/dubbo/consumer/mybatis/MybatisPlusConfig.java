package com.xrf.dubbo.consumer.mybatis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
//        return interceptor;
//    }

    @Bean
    public MybatisPlusSqlInjector sqlInjector() {
        return new MybatisPlusSqlInjector();
    }

    @Bean
    public MybatisPlusMetaObjectHandler mybatisPlusCommonDateFieldValueFillHandler() {
        return new MybatisPlusMetaObjectHandler();
    }

}
