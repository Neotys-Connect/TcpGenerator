package com.neotys.tcp.ClientGenerator;

import com.neotys.extensions.action.engine.Context;
import com.neotys.extensions.action.engine.SampleResult;
import com.neotys.tcp.tcpclient.TCPClient;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.neotys.tcp.common.TCPConstant.MAX;

public class NeoLoadTCPClient {
    private Random random ;
    private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvw1234567890";
    private String host;
    private int port;
    private int size;

    public NeoLoadTCPClient(String host, String port,String size) {
        random= new Random();
        this.host=host;
        this.port=Integer.parseInt(port);
        this.size=Integer.parseInt(size);
    }

    public void senData(Context context, SampleResult result)
    {
        VertxOptions options=new VertxOptions().setMaxWorkerExecuteTime(MAX).setMaxWorkerExecuteTimeUnit(TimeUnit.MINUTES);
        Vertx.vertx(options).deployVerticle(new TCPClient(port,host,createDataSize(size),context,result));
    }


    private   String createDataSize(int msgSize) {
        // Java chars are 2 bytes
        msgSize = msgSize/2;
        msgSize = msgSize * 1024;
        StringBuilder sb = new StringBuilder(msgSize);
        for (int i=0; i<msgSize; i++) {
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return sb.toString();
    }
}
