package com.huhu.hupao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 *
 */
@SpringBootApplication
@MapperScan("com.huhu.hupao.mapper")
@EnableScheduling
public class HuPaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HuPaoApplication.class, args);
    }

}
