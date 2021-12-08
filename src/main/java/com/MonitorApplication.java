package com;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;


/**
 * @Author: luo
 * @Description:
 * @Data: 10:48 2021/11/3
 */
public class MonitorApplication {
    static Logger logger = LoggerFactory.getLogger(MonitorApplication.class);

    public static void main(String[] args) {
        System.setProperty("java.library.path", System.getProperty("user.dir")+"/dll");
        SystemInfo.initMessage();
        SystemInfo.os();
        logger.info("系统监控服务启动");
        new MainFrame().setVisible(false);
        try {
            new WebSocketServerManager(8887).start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
