package com.xgs.hisystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 *
 * @ConfigurationProperties 可以批量注入配置文件中的属性
 * @ConfigurationProperties()默认是从全局配置文件中获取值，也就是
 * application.properties这个文件中获取值。
 *
 * @Email // 配置文件书写的属性必须是邮箱格式，不符合报错！
 *
 */

//（把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>）
@Component  //组件
public class EmailConfig {

    //将配置文件 *.properties 或 *. yml  里 配置的 属性  注入这里
    // $()读取配置文件、环境变量中的值
    @Value("${spring.mail.username}")
    private String emailFrom;

    public String getEmailFrom(){
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom){
        this.emailFrom=emailFrom;
    }
}
