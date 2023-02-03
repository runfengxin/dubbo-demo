package com.xrf.dubbo.provider;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//-Dskywalking.agent.service_name=provider-app
//-javaagent:E:\mid-soft\apache-skywalking-apm-8.1.0\apache-skywalking-apm-bin\agent\skywalking-agent.jar
//-javaagent:C:\Users\85464\Downloads\apache-skywalking-java-agent-8.14.0\skywalking-agent\skywalking-agent.jar
@SpringBootApplication
@EnableDubbo
@MapperScan("com.xrf.dubbo.provider.mapper")
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
