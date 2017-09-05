package com.zhaoxiang.rocketmq.filter;

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
 * Date: 2017/9/5 14:15
 */
public class FilterProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {

        DefaultMQProducer producer = new DefaultMQProducer("filter_group");
        producer.setNamesrvAddr(RocketMQConstants.NAMESRVADDR);
        producer.start();

        for (int i = 1; i <= 10; i++) {
            String msgBody = "Filter Msg " + i;
            Message msg = new Message("FilterTopic", "FilterTag", msgBody.getBytes(RemotingHelper.DEFAULT_CHARSET));
            msg.putUserProperty("a", String.valueOf(i));
            SendResult result = producer.send(msg);
            System.out.println(result);
        }

        producer.shutdown();
    }
}
