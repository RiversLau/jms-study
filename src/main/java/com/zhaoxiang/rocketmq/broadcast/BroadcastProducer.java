package com.zhaoxiang.rocketmq.broadcast;

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
 * Date: 2017/9/5 11:16
 */
public class BroadcastProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {

        DefaultMQProducer producer = new DefaultMQProducer("broadGroup");
        producer.setNamesrvAddr(RocketMQConstants.NAMESRVADDR);

        producer.start();

        for (int i = 0; i < 3; i++) {
            Message msg = new Message("BroadTopic", "TagA", "Broad0001", "Broadcast msg".getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult result = producer.send(msg);
            System.out.println(result);
        }

        producer.shutdown();
    }
}
