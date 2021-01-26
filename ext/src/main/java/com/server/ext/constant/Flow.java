package com.server.ext.constant;

/**
 * 数据流向
 * created by Lyon.Cao in 2021/01/27 00:27:15
 */
public enum Flow {
    CLIENT_TO_SERVER(0),            // 客户端发往服务器
    SERVER_RESPONSE_CLIENT(1),      // 服务端响应客户端
    SERVER_TO_CLIENT(2),            // 服务器发往客户端
    CLIENT_RESPONSE_SERVER(3);      // 客户端响应服务器

    private int value;

    Flow(int value) {
        this.value = value;
    }
}
