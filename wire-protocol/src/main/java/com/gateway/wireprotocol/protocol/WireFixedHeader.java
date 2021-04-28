package com.gateway.wireprotocol.protocol;

/**
 * 固定头
 * create by Lyon.Cao in 2021/01/26 0:06
 **/
public class WireFixedHeader {
    private int sequenceId;    // 序列ID  - 0 不存在, 1 存在
    private int payload;       // 负载    - 0 不存在, 1 存在
    private int command;       // 命令    - 0 不存在, 1 存在
    private int compression;   // 压缩    - 0 不启用, 1 启用
    private int packetType;    // 类型    - 0 命令包, 1 应答包
    private int relay;         // 应答    - 0 不应答, 1 应答
    private int ping;          // 心跳    - 1 心跳包
    private int reserved;      // 保留    - 1 default

    /**
     * 包长度
     */
    private int baseLength = 0; // 包长度

    public WireFixedHeader() {
    }

    public WireFixedHeader(byte fixedHeader) {
        this.sequenceId = (fixedHeader & 0x80) >> 7;
        this.payload = (fixedHeader & 0x40) >> 6;
        this.command = (fixedHeader & 0x20) >> 5;
        this.compression = (fixedHeader & 0x10) >> 4;
        this.packetType = (fixedHeader & 0x8) >> 3;
        this.relay = (fixedHeader & 0x4) >> 2;
        this.ping = (fixedHeader & 0x2) >> 1;
        this.reserved = fixedHeader & 0x1;

        /* 计算协议长度 */
        baseLength = (sequenceId == 0 ? 0 : 8) + (payload == 0 ? 0 : 2) + command;
    }

    public int getBaseLength() {
        return baseLength;
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }

    public int getPayload() {
        return payload;
    }

    public void setPayload(int payload) {
        this.payload = payload;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public int getCompression() {
        return compression;
    }

    public void setCompression(int compression) {
        this.compression = compression;
    }

    public int getPacketType() {
        return packetType;
    }

    public void setPacketType(int packetType) {
        this.packetType = packetType;
    }

    public int getRelay() {
        return relay;
    }

    public void setRelay(int relay) {
        this.relay = relay;
    }

    public int getPing() {
        return ping;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }
}
