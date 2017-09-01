package com.zhaoxiang.rabbitmq.workQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

/**
 * Author: RiversLau
 * Date: 2017/9/1 15:09
 */
public class WorkQueueSender {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {

        System.out.println("Please send your msg.");

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String msg = reader.readLine();
            if ("exit".equals(msg)) {
                break;
            }
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
            System.out.println("[x] Sent '" + msg + "'");

        }
        channel.close();
        connection.close();
        System.exit(0);
    }
}
