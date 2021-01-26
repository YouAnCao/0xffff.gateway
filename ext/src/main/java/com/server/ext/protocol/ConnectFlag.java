package com.server.ext.protocol;

/**
 * 连接标识位
 * create by Lyon.Cao in 2021/01/26 0:31
 **/
public class ConnectFlag {
    private int     reserved;           // 保留位
    private boolean compression;        // 压缩 - 是否启用压缩
    private boolean command;            // 命令 - 可变头中是否有命令字段
    private boolean payload;            // 负载 - 是否有负载
    private boolean sequence;           // 序列 - 可变头中是否有序列
    private boolean status;             // 状态 - 可变头中是否有状态值
    private int     flow;               // 流向 - 当前包的流向 Flow

    public ConnectFlag() {
    }

    public ConnectFlag(int reserved, boolean compression, boolean command, boolean payload, boolean sequence, boolean status, int flow) {
        this.reserved = reserved;
        this.compression = compression;
        this.command = command;
        this.payload = payload;
        this.sequence = sequence;
        this.status = status;
        this.flow = flow;
    }

    private ConnectFlag(int compression, int command, int payload, int sequence, int status, int flow) {
        this.compression = compression == 1;
        this.command = command == 1;
        this.payload = payload == 1;
        this.sequence = sequence == 1;
        this.status = status == 1;
        this.flow = flow;
    }


    public static ConnectFlag toBuild(int connectFlag) {
        int compression = (connectFlag & 64) >> 6;
        int command     = (connectFlag & 32) >> 5;
        int payload     = (connectFlag & 16) >> 4;
        int sequence    = (connectFlag & 8) >> 3;
        int status      = (connectFlag & 4) >> 2;
        int flow        = connectFlag & 3;
        return new ConnectFlag(compression, command, payload, sequence, status, flow);
    }

    /**
     * 解析连接标识字段为byte数据
     */
    public byte parse2Byte() {
        return (byte) (reserved | ((compression ? 1 : 0) << 6)
                | ((command ? 1 : 0) << 5)
                | ((payload ? 1 : 0) << 4)
                | ((sequence ? 1 : 0) << 3)
                | ((status ? 1 : 0) << 2) | flow);
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public boolean isCompression() {
        return compression;
    }

    public void setCompression(boolean compression) {
        this.compression = compression;
    }

    public boolean isCommand() {
        return command;
    }

    public void setCommand(boolean command) {
        this.command = command;
    }

    public boolean isPayload() {
        return payload;
    }

    public void setPayload(boolean payload) {
        this.payload = payload;
    }

    public boolean isSequence() {
        return sequence;
    }

    public void setSequence(boolean sequence) {
        this.sequence = sequence;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }
}