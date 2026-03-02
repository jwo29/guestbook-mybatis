package com.january.guestbook.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.january.guestbook.mapper")
public class MyBatisConfig {
}
