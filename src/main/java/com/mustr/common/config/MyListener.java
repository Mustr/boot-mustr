package com.mustr.common.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
       System.out.println("web关闭。。。");
        
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("web启动。。。");
    }

}