package com.im.gateway.protocol;

/**
 * 连接协议
 * create by Lyon.Cao in 2021/01/19 22:07
 **/
public class BiuProtocol {
    private FixedHeader fixedHeader; // 固定请求头
    private byte[]      payload; // 数据负载

    public BiuProtocol() {
    }


    public FixedHeader getFixedHeader() {
        return fixedHeader;
    }

    public void setFixedHeader(FixedHeader fixedHeader) {
        this.fixedHeader = fixedHeader;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }
}
