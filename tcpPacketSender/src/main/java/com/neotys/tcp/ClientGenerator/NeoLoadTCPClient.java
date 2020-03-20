package com.neotys.tcp.ClientGenerator;

import com.neotys.extensions.action.engine.Context;
import com.neotys.extensions.action.engine.SampleResult;
import com.neotys.tcp.tcpclient.TCPClient;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.*;

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



    public void senData(Context context, SampleResult result) throws IOException, ClassNotFoundException {

        Socket socket = null;
        OutputStream output = null;
        InputStream input = null;

        socket = new Socket(host, port);
        output = socket.getOutputStream();
        input = socket.getInputStream();


        output.write(createDataSize(size).getBytes());
        output.flush();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        //read the server response message
        int character;
        StringBuilder data = new StringBuilder();

        InputStream finalInput = input;
        Callable<Integer> readTask = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return finalInput.read();
            }
        };

        int readByte = 1;
        while (readByte >= 0) {
            Future<Integer> future = executor.submit(readTask);
            try {
                readByte = future.get(5, TimeUnit.SECONDS);
            } catch (Exception e) {
              break;
            }
            if (readByte >= 0)
                data.append((char) readByte);
        }


        input.close();
        output.close();
        socket.close();

        result.setResponseContent("data received :"+  data.toString());

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
