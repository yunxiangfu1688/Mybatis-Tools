package com.yxf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@MapperScan("com.yxf.**.dao")
@SpringBootApplication
public class NraDataApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(NraDataApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(NraDataApplication.class);
    }

}
