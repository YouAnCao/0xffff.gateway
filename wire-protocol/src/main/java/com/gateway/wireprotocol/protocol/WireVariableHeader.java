package com.gateway.wireprotocol.protocol;

import java.io.Serializable;

/**
 * 可变协议头
 * create by Lyon.Cao in 2021/01/26 0:07
 **/
public class WireVariableHeader implements Serializable {
    static final long serialVersionUID = 55L;
    private      long sequenceId;
    private      int  payloadLength;
    private      int  command;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(long sequenceId) {
        this.sequenceId = sequenceId;
    }

    public int getPayloadLength() {
        return payloadLength;
    }

    public void setPayloadLength(int payloadLength) {
        this.payloadLength = payloadLength;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }
}
