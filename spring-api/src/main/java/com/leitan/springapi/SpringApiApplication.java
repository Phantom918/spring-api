package com.leitan.springapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lei.tan
 * @Description spring 工具项目
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.leitan.springapi.dao")
public class SpringApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringApiApplication.class, args);
    }

}
