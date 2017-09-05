package com.zhaoxiang.rocketmq.schedule;

import com.zhaoxiang.rocketmq.RocketMQConstants;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * Author: RiversLau
 * Date: 2017/9/5 11:28
 */
public class ScheduleProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {

        DefaultMQProducer producer = new DefaultMQProducer("ScheduleGroup");
        producer.setNamesrvAddr(RocketMQConstants.NAMESRVADDR);

        producer.start();
        for (int i = 0; i < 3; i++) {
            Message msg = new Message("ScheduleTopic", ("Schedule Msg" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            msg.setDelayTimeLevel(3);
            producer.send(msg);
        }

        producer.shutdown();
    }
}
