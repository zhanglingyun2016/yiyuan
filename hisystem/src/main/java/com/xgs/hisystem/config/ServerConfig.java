package com.xgs.hisystem.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;


/**
 * @author XueGuiSheng
 * @date 2020/4/6
 * @description:
 */

@Component
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {
    private int serverPort;

    public String getUrl() {

        try {
            //返回ip地址
            InetAddress address = InetAddress.getLoopbackAddress();

            return "http://" + address.getHostAddress() + ":" + this.serverPort;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }


    //法中就是我们自己要在容器初始化完成后加载的数据或者缓存
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
    }

}
