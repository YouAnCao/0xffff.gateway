package com.gateway.server.runner;

import com.gateway.server.config.GatewayConfig;
import com.gateway.server.config.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 路由信息加载服务
 * create by Lyon.Cao in 2021/02/28 22:03
 **/
@Component
@Order(3)
public class RouteBootstrap implements ApplicationRunner {
    private static Logger logger = LoggerFactory.getLogger(RouteBootstrap.class);

    @Autowired
    GatewayConfig config;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Route> routes = config.getRoutes();
        if (routes == null) {
            logger.error("the route setting is empty");
            System.exit(1);
        }

    }
}
