package com.zhaoxiang.rocketmq.simple;

import com.zhaoxiang.rocketmq.RocketMQConstants;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * Author: RiversLau
 * Date: 2017/9/5 10:37
 */
public class OnewayProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {

        DefaultMQProducer producer = new DefaultMQProducer("oneway_producer");
        producer.setNamesrvAddr(RocketMQConstants.NAMESRVADDR);
        producer.start();

        Message msg = new Message("oneway_topic", "TagA||TagB", "Hello RocketMQ sync message".getBytes(RemotingHelper.DEFAULT_CHARSET));
        producer.sendOneway(msg);

        producer.shutdown();
    }
}
