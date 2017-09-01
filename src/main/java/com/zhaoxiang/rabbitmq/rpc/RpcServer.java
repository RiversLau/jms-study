package com.zhaoxiang.rabbitmq.rpc;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Author: RiversLau
 * Date: 2017/9/1 17:13
 */
public class RpcServer {

    private static final String RPC_QUEUE_NAME = "rpc_queue";

    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = null;
        try {
            connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
            channel.basicQos(1);

            System.out.println("[x] Waiting for RPC Request");

            Consumer consumer = new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                    AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder().
                            correlationId(properties.getCorrelationId()).build();
                    String response = "";
                    try {
                        String message = new String(body, "utf-8");
                        int n = Integer.parseInt(message);
                        System.out.println("[.] fib(" + message + ")");
                        response += fib(n);
                    } finally {
                        channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("utf-8"));
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                }
            };
            channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private static long fib(int n) {

        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }
}
