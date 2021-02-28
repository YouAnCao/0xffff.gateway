package com.gateway.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 路由配置信息
 * create by Lyon.Cao in 2021/02/28 21:47
 **/
@Component
@ConfigurationProperties(prefix = GatewayConfig.GATEWAY_ROUTER_PREFIX)
public class GatewayConfig {
    protected final static String GATEWAY_ROUTER_PREFIX = "gateway";

    private int         port;
    private int         node;
    private List<Route> routes;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}
