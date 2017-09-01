package com.zhaoxiang.rabbitmq.rpc;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Author: RiversLau
 * Date: 2017/9/1 17:35
 */
public class RpcTest {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        RpcClient client = new RpcClient();
        String response = client.call("30");
        System.out.println(response);

        client.close();
    }
}
