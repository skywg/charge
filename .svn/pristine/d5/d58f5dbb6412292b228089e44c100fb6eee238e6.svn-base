package com.iycharge.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.iycharge.server.admin.web.CCUServerStart;
import com.iycharge.server.admin.web.LoadEntityRunner;
import com.iycharge.server.ccu.util.Utility;

@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        
        Utility.setApplicationContext(context);
        
        //初始化数据
        context.getBean(LoadEntityRunner.class).run();
        
        //启动电桩控制器
        context.getBean(CCUServerStart.class).start();
        
    }
}
