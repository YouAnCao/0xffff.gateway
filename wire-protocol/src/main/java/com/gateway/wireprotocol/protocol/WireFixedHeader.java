package com.gateway.wireprotocol.protocol;

/**
 * 固定头
 * create by Lyon.Cao in 2021/01/26 0:06
 **/
public class WireFixedHeader {

    private ConnectFlag connectFlag;

    public WireFixedHeader(ConnectFlag connectFlag) {
        this.connectFlag = connectFlag;
    }

    public ConnectFlag getConnectFlag() {
        return connectFlag;
    }

    public void setConnectFlag(ConnectFlag connectFlag) {
        this.connectFlag = connectFlag;
    }


}
