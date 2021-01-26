package com.server.ext.protocol;

/**
 * Wire协议
 * create by Lyon.Cao in 2021/01/26 0:05
 **/
public class WireProtocol {
    private WireFixedHeader    fixedHeader;
    private WireVariableHeader variableHeader;
    private byte[]             payload;

    public WireProtocol(WireFixedHeader fixedHeader, WireVariableHeader variableHeader, byte[] payload) {
        this.fixedHeader = fixedHeader;
        this.variableHeader = variableHeader;
        this.payload = payload;
    }

    public WireProtocol(Byte[] data) {
        this.toBuild(data);
    }

    private void toBuild(Byte[] data) {
        if (data != null && data.length > 0) {
            int         index       = 0;
            ConnectFlag connectFlag = ConnectFlag.toBuild(data[++index]);
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
            /* 初始化序列号,大端 */
            if (connectFlag.isSequence()) {
                int sequence = data[++index] << 24
                        & data[++index] << 16
                        & data[++index] << 8
                        & data[++index];
                variableHeader.setSequence(sequence);
            }

            /* 设置payload */
            if (connectFlag.isPayload()) {
                if (data.length > index + 1) {
                    byte[] payload = new byte[data.length - index];
                    System.arraycopy(data, index, payload, 0, data.length - index);
                    this.payload = payload;
                }
            }
        }
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
