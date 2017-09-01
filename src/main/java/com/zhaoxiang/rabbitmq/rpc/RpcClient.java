package com.zhaoxiang.rabbitmq.rpc;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * Author: RiversLau
 * Date: 2017/9/1 17:24
 */
public class RpcClient {

    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";
    private String replyQueueName;

    public RpcClient() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        connection = factory.newConnection();
        channel = connection.createChannel();

        replyQueueName = channel.queueDeclare().getQueue();
    }

    public String call(String message) throws IOException, InterruptedException {

        final String ccid = UUID.randomUUID().toString();
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(ccid).
                replyTo(replyQueueName).build();

        channel.basicPublish("", requestQueueName, props, message.getBytes("utf-8"));

        final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);
        channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if (properties.getCorrelationId().equals(ccid)) {
                    response.offer(new String(body, "utf-8"));
                }
            }
        });
        return response.take();
    }

    public void close() throws IOException, TimeoutException {

        channel.close();
        connection.close();
    }
}
