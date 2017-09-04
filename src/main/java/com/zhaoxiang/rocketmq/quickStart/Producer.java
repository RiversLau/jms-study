package com.zhaoxiang.rocketmq.quickStart;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * Author: RiversLau
 * Date: 2017/9/4 17:27
 */
public class Producer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        DefaultMQProducer producer = new DefaultMQProducer("testGroupName");
        producer.setNamesrvAddr("39.108.141.144:9876");
        producer.setInstanceName("Producer");

        producer.start();

        Message msg = new Message("testTopic", "TagA", "test-0001", "Hello RocketMQ".getBytes());
        SendResult result = producer.send(msg);
        System.out.println(result.getSendStatus().toString());
    }
}
