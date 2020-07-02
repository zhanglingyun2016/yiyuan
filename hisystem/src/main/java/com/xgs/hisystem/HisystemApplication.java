package com.xgs.hisystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 可添加在主程序上方
 * @ImportResource(locations = {"classpath:bean.xml"}) // 通过此配置是 bean.xml生效
 * @ImportResource：导入Spring的配置文件，让配置文件生效
 *
 * @SpringBootApplication(scanBasePackages = "com.zhihao.miao")，指定扫描路径：
 *
 */



//来标注一个主程序，说明这是一个Sping Boot项目
@SpringBootApplication
@EnableScheduling
public class HisystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HisystemApplication.class, args);
    }

}
