package com.zhaoxiang.rocketmq.batch;

import com.zhaoxiang.rocketmq.RocketMQConstants;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: RiversLau
 * Date: 2017/9/5 11:42
 */
public class BatchProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {

        DefaultMQProducer producer = new DefaultMQProducer("BatchGroup");
        producer.setNamesrvAddr(RocketMQConstants.NAMESRVADDR);

        producer.start();

        List<Message> msgList = new ArrayList<Message>();
        String topic = "BatchTopic";
        msgList.add(new Message(topic, "TagA", "Batch001", "Batch Msg".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        msgList.add(new Message(topic, "TagA", "Batch002", "Batch Msg".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        msgList.add(new Message(topic, "TagA", "Batch003", "Batch Msg".getBytes(RemotingHelper.DEFAULT_CHARSET)));

        ListSplitter splitter = new ListSplitter(msgList);
        while (splitter.hasNext()) {
            producer.send(splitter.next());
        }

        producer.shutdown();
    }
}
