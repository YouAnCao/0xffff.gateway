package com.server.gateway.constant;

import io.netty.util.AttributeKey;

import java.util.Set;

/**
 * channel 属性
 * create by Lyon.Cao in 2021/01/28 0:52
 **/
public class AttributeKeys {
    /**
     * session 的分组信息
     */
    public static AttributeKey<Set<String>> SESSION_GROUPS = AttributeKey.newInstance("SESSION_GROUPS");

    /**
     * session id
     */
    public static AttributeKey<String> SESSION_ID = AttributeKey.newInstance("SESSION_ID");
}

