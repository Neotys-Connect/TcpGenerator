package com.neotys.tcp.server;

import com.neotys.tcp.Logger.NeoLoadLogger;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;

import static com.neotys.tcp.conf.Constants.DEFAULT_PORT;
import static com.neotys.tcp.conf.Constants.SECRET_PORT;

public class NeoLoadTCPServer  extends AbstractVerticle {
    private NeoLoadLogger loadLogger;
    int tcpPort;
    private Vertx rxvertx;
    @Override
    public void start() throws Exception {
        loadLogger=new NeoLoadLogger(this.getClass().getName());

        String port=System.getenv(SECRET_PORT);
        if(port==null)
        {
            tcpPort =DEFAULT_PORT;
        }
        else
        {
            tcpPort =Integer.parseInt(port);
        }
        NetServer server = vertx.createNetServer();
        server.connectHandler(new Handler<NetSocket>() {

            @Override
            public void handle(NetSocket netSocket) {
                loadLogger.debug("Incoming connection!");
                netSocket.handler(new Handler<Buffer>() {

                    @Override
                    public void handle(Buffer buffer) {
                        loadLogger.debug("incoming data: "+buffer.length());

                        buffer.getString(0,buffer.length());
                        Buffer outBuffer = Buffer.buffer();
                        outBuffer.appendString("TCP packets received with the size  "+buffer.length());

                        netSocket.write(outBuffer);
                        loadLogger.debug("reponse sent to client");
                    }
                });
            }
        });
        server.listen(tcpPort);
    }
}
