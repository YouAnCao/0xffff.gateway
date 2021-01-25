package com.server.ext.protocol;

import java.util.Arrays;
import java.util.Spliterator;

/**
 * Wire协议
 * create by Lyon.Cao in 2021/01/26 0:05
 **/
public class WireProtocol {
    private WireFixedHeader    fixedHeader;
    private WireVariableHeader variableHeader;
    private byte[]             payload;

    public WireProtocol toBuild(Byte[] data) {
        if (data != null && data.length > 0) {
            int         index       = 1;
            ConnectFlag connectFlag = ConnectFlag.toBuild(data[index]);
            fixedHeader = new WireFixedHeader(connectFlag);
            variableHeader = new WireVariableHeader();

            if (connectFlag.isCompression()) {
                ++index;
            }
            /* 初始化命令标识 */
            if (connectFlag.isCommand()) {
                variableHeader.setCommand((int) data[++index]);
            }
            /* 初始化状态标识 */
            if (connectFlag.isStatus()) {
                variableHeader.setStatus((int) data[++index]);
            }
            /* 初始化序列号 */
            if (connectFlag.isSequence()) {
                int sequence = data[++index] << 32
                        & data[++index] << 16
                        & data[++index] << 8
                        & data[++index];
                variableHeader.setSequence(sequence);
            }
            byte[] payload = new byte[data.length - index];
            System.arraycopy(data, index, payload, 0, data.length - index);
            this.payload = payload;
        }
        return null;
    }

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
