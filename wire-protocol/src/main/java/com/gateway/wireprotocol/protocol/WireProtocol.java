package com.gateway.wireprotocol.protocol;

/**
 * Wire协议
 * create by Lyon.Cao in 2021/01/26 0:05
 **/
public class WireProtocol {
    private WireFixedHeader    fixedHeader;
    private WireVariableHeader variableHeader;
    private byte[]             payload;

    public WireFixedHeader getFixedHeader() {
        return fixedHeader;
    }

    public void setFixedHeader(WireFixedHeader fixedHeader) {
        this.fixedHeader = fixedHeader;
    }

    public WireVariableHeader getVariableHeader() {
        return variableHeader;
    }

    public void setVariableHeader(WireVariableHeader variableHeader) {
        this.variableHeader = variableHeader;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }
}
