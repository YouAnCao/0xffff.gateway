package com.server.gateway.client;

import com.server.ext.protocol.ConnectFlag;
import com.server.ext.protocol.WireFixedHeader;
import com.server.ext.protocol.WireProtocol;
import com.server.ext.protocol.WireVariableHeader;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 * create by Lyon.Cao in 2021/01/30 16:55
 **/
public class ServerTester {

    final static Executor executor = new ThreadPoolExecutor(8, 8, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));

    public static void main(String[] args) {

        List<Client> clients = new ArrayList<>();


        for (int i = 0; i < 500; i++) {
            Client client = new Client();
            client.connect("127.0.0.1", 2025);
            clients.add(client);
        }

        for (int i = 0; i < 8; i++) {
            executor.execute(() -> {
                while (true) {
                    for (Client client : clients) {
                        ConnectFlag        connectFlag    = new ConnectFlag(false, true, true, false, false, 0);
                        WireFixedHeader    fixedHeader    = new WireFixedHeader(connectFlag);
                        WireVariableHeader variableHeader = new WireVariableHeader(12);
                        WireProtocol       protocol       = new WireProtocol(fixedHeader, variableHeader, "hello world".getBytes(Charset.forName("utf-8")));
                        client.context.writeAndFlush(protocol);
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });
        }

    }
}
