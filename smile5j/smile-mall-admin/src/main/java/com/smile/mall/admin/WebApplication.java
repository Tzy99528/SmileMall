package com.smile.mall.admin;

//  Spring Boot应用启动类
import org.springframework.boot.SpringApplication;      
//  1. @SpringBootApplication注解是一个组合注解，它包含了@Configuration、@EnableAutoConfiguration和@ComponentScan三个注解的功能。
// 2. 标记为配置类  
// 从 classpath 检测项目依赖的jar包，并根据对应的包自动创建相关对象并加载到Spring容器中 
// 3. 自动扫描当前包及其子包下的所有组件（Controller, Service），并注册为Spring Bean 放进 Spring 容器中
import org.springframework.boot.autoconfigure.SpringBootApplication;
// 可配置包扫描范围
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.smile.mall"})
public class WebApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(WebApplication.class, args);
    }
}
