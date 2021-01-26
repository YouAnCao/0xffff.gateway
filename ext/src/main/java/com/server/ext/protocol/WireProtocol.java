package com.server.ext.protocol;

import java.nio.ByteBuffer;

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

    /* 解析协议为字节数组 */
    private byte[] toByteArray() {
        ConnectFlag connectFlag = null;
        /* 如果固定头不存在,需要初始化固定头 */
        if (fixedHeader == null) {
            fixedHeader = new WireFixedHeader(null);
        }
        if ((connectFlag = fixedHeader.getConnectFlag()) == null) {
            connectFlag = new ConnectFlag();
            fixedHeader.setConnectFlag(connectFlag);
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(7);
        int        index      = 0;
        if (variableHeader != null) {
            /* 检查是否有命令标记，有则添加命令到协议头上，否则重置标记 */
            Integer command = variableHeader.getCommand();
            if (command != null) {
                byte data = command.byteValue();
                connectFlag.setCommand(true);
                byteBuffer.put(++index, data);
            } else {
                connectFlag.setStatus(false);
            }
            /* 检查是否有状态标记，有则添加状态到协议头上，否则重置标记 */
            Integer status = variableHeader.getStatus();
            if (status != null) {
                byte data = status.byteValue();
                connectFlag.setStatus(true);
                byteBuffer.put(++index, data);
            } else {
                connectFlag.setStatus(false);
            }

            Integer sequence = variableHeader.getSequence();
            if (sequence != null) {
                int data = sequence.intValue();
                connectFlag.setSequence(true);
                byteBuffer.putInt(++index, data);
            }
        }
        /* 如果有负载,修改连接标识中的负载位为true */
        if (payload != null && payload.length > 0) {
            connectFlag.setPayload(true);
        }
        /* 获取连接标记的字节对象, 并设置到头字节数据的最前面（连接标记总是在第零位） */
        byte flag = connectFlag.parse2Byte();
        byteBuffer.put(0, flag);
        byteBuffer.flip();
        /* 获取固定头与可变头数据 */
        byte[] header = byteBuffer.array();
        /* 如果有负载，则构造协议头加负载的buffer，否则可以直接返回 */
        if (connectFlag.isPayload()) {
            ByteBuffer result = ByteBuffer.allocate(header.length + payload.length);
            result.put(header);
            result.put(payload);
            result.flip();
            return result.array();
        } else {
            return header;
        }
    }

    private void toBuild(Byte[] data) {
        if (data != null && data.length > 0) {
            int         index       = 0;
            ConnectFlag connectFlag = ConnectFlag.toBuild(data[++index]);
            fixedHeader = new WireFixedHeader(connectFlag);
            variableHeader = new WireVariableHeader();

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
