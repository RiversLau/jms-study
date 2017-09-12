package com.zhaoxiang.rocketmq.quickStart;

import com.zhaoxiang.rocketmq.RocketMQConstants;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

/**
 * Author: RiversLau
 * Date: 2017/9/4 17:27
 */
public class Producer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        DefaultMQProducer producer = new DefaultMQProducer("testGroupName");
        producer.setNamesrvAddr(RocketMQConstants.NAMESRVADDR);
        producer.setInstanceName("Producer");
//        producer.setVipChannelEnabled(false);

        producer.start();

        for (int i = 0; i < 20; i++) {
            Message msg = new Message("testTopic", "TagA", "test-0001", ("Hello RocketMQ" + i).getBytes());
            SendResult result = producer.send(msg);
            System.out.println(result.getSendStatus().toString());
        }

        producer.shutdown();
    }
}
