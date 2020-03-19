package com.neotys.tcp;

import com.neotys.tcp.server.NeoLoadTCPServer;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.util.concurrent.TimeUnit;

public class NeoLoadTCPMockEndPoint {
    private static final int MAX=24;
    public static void main(String[] args) {

        VertxOptions options=new VertxOptions().setMaxWorkerExecuteTime(MAX).setMaxWorkerExecuteTimeUnit(TimeUnit.HOURS);
        Vertx.vertx(options).deployVerticle(new NeoLoadTCPServer());


    }
}
