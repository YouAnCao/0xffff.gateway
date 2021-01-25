package com.server.gateway.protocol;

/**
 * Biu协议固定请求头
 * create by Lyon.Cao in 2021/01/19 22:16
 **/
public class FixedHeader {

    private ConnectFlag connectFlag;
    private int         command;
    private int         status;

    public FixedHeader() {

    }

    public FixedHeader(ConnectFlag connectFlag, int command, int status) {
        this.connectFlag = connectFlag;
        this.command = command;
        this.status = status;
    }

    public ConnectFlag getConnectFlag() {
        return connectFlag;
    }

    public void setConnectFlag(ConnectFlag connectFlag) {
        this.connectFlag = connectFlag;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 连接标识：保留4位
     * create by Lyon.Cao in 2021/1/19 23:00:35
     */
    class ConnectFlag {
        private int compression; // 是否压缩数据
        private int flow;        // 数据流向

        public int getCompression() {
            return compression;
        }

        public void setCompression(int compression) {
            this.compression = compression;
        }

        public int getFlow() {


            return flow;
        }

        public void setFlow(int flow) {
            this.flow = flow;
        }
    }


}
