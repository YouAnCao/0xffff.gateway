package com.gateway.wireprotocol.protocol;

import java.io.Serializable;

/**
 * 可变协议头
 * create by Lyon.Cao in 2021/01/26 0:07
 **/
public class WireVariableHeader implements Serializable {
    static final long serialVersionUID = 55L;

    private Integer command;
    private Integer status;
    private Integer sequence;

    public WireVariableHeader() {
    }

    public WireVariableHeader(Integer command) {
        this.command = command;
    }

    public WireVariableHeader(Integer command, Integer status) {
        this.command = command;
        this.status = status;
    }

    public WireVariableHeader(Integer command, Integer status, Integer sequence) {
        this.command = command;
        this.status = status;
        this.sequence = sequence;
    }

    public Integer getCommand() {
        return command;
    }

    public void setCommand(Integer command) {
        this.command = command;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
