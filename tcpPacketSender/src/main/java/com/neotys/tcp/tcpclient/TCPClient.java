package com.neotys.tcp.tcpclient;

import com.neotys.extensions.action.engine.Context;
import com.neotys.extensions.action.engine.SampleResult;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

public class TCPClient extends AbstractVerticle {
    private int port;
    private String host;
    private  NetClient tcpClient;
    private String data;
    private Context context;
    private SampleResult sampleResult;

    public TCPClient(int port, String host, String data, Context context, SampleResult result) {
        this.port = port;
        this.host = host;
        this.data = data;
        this.context=context;
        this.sampleResult =result;
    }

    public void start() {
        tcpClient = vertx.createNetClient();
        tcpClient.connect(port, host,
                new Handler<AsyncResult<NetSocket>>(){

                    @Override
                    public void handle(AsyncResult<NetSocket> result) {
                        context.getLogger().debug("Connected to "+ host);
                        NetSocket socket = result.result();
                        context.getLogger().debug("Sending data : "+data);
                        sampleResult.setRequestContent("Sendin to "+ host +":"+port +" content "+data);
                        sampleResult.sampleStart();
                        socket.write(data);
                        socket.handler(new Handler<Buffer>(){
                            @Override
                            public void handle(Buffer buffer) {
                               context.getLogger().debug("Received data: " + buffer.length());
                               sampleResult.setResponseContent("Received data : " +buffer.getString(0, buffer.length()) );
                               sampleResult.sampleEnd();
                               close();
                            }
                        });
                    }
                });


    }

    public void close()
    {
        tcpClient.close();
    }
}