package com.gateway.wireprotocol.exception;

/**
 * 协议异常
 * create by Lyon.Cao in 2021/01/27 0:36
 **/
public class ProtocolBuildException extends RuntimeException {

    
    public ProtocolBuildException() {
    }

    public ProtocolBuildException(String message) {
        super(message);
    }

    public ProtocolBuildException(String message, Throwable cause) {
        super(message, cause);
    }
}
