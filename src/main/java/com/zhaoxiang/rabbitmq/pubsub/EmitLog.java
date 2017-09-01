package com.zhaoxiang.rabbitmq.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

/**
 * Author: RiversLau
 * Date: 2017/9/1 16:07
 */
public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {

        System.out.println("Please send your msg.");

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String msg = reader.readLine();
            if ("exit".equals(msg)) {
                break;
            }
            channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
            System.out.println("[x] Sent '" + msg + "'");

        }
        channel.close();
        connection.close();
    }
}
