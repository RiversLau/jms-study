package com.zhaoxiang.rocketmq.simple;

import com.zhaoxiang.rocketmq.RocketMQConstants;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * Author: RiversLau
 * Date: 2017/9/5 10:15
 */
public class SyncProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {

        DefaultMQProducer producer = new DefaultMQProducer("sync_producer");
        producer.setNamesrvAddr(RocketMQConstants.NAMESRVADDR);
        producer.start();

        Message msg = new Message("SyncTopic", "TagA", "Hello RocketMQ sync message".getBytes(RemotingHelper.DEFAULT_CHARSET));
        SendResult result = producer.send(msg);
        System.out.println(result);

        producer.shutdown();
    }
}
