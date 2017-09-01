package com.zhaoxiang.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

/**
 * Author: RiversLau
 * Date: 2017/9/1 16:41
 */
public class TopicEmitLog {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws IOException, TimeoutException {

        System.out.println("Please send your msg.");

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String msg = reader.readLine();
            if ("exit".equals(msg)) {
                break;
            }
            String[] msgs = msg.split("=");
            channel.basicPublish(EXCHANGE_NAME, msgs[0], null, msgs[1].getBytes());
            System.out.println("[x] Sent '" + msgs[1] + "'");

        }
        channel.close();
        connection.close();
    }
}
