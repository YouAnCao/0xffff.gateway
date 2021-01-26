package com.server.ext.protocol;

/**
 * TODO
 * create by Lyon.Cao in 2021/01/26 0:31
 **/
public class ConnectFlag {
    private int     reserved    = 0;     // 保留位
    private boolean compression = false; // 压缩
    private boolean command     = true;  // 命令
    private boolean payload     = true;  // 负载
    private boolean sequence    = false; // 序列
    private boolean status      = false; // 状态
    private int     flow        = 0;     // 流向

    public ConnectFlag() {
    }

    public ConnectFlag(int compression, int command, int payload, int sequence, int status, int flow) {
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

    public int toNumber() {
        return reserved | ((compression ? 1 : 0) << 6)
                | ((command ? 1 : 0) << 5)
                | ((payload ? 1 : 0) << 4)
                | ((sequence ? 1 : 0) << 3)
                | ((status ? 1 : 0) << 2) | flow;
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