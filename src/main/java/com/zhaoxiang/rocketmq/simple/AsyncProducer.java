package com.zhaoxiang.rocketmq.simple;

import com.zhaoxiang.rocketmq.RocketMQConstants;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * Author: RiversLau
 * Date: 2017/9/5 10:32
 */
public class AsyncProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {

        DefaultMQProducer producer = new DefaultMQProducer("async_producer");
        producer.setNamesrvAddr(RocketMQConstants.NAMESRVADDR);
        producer.setInstanceName("AsyncProducer");
        producer.start();

        Message msg = new Message("async_topic", "TagA||TagB", "Hello RocketMQ async message".getBytes(RemotingHelper.DEFAULT_CHARSET));
        producer.send(msg, new SendCallback() {
            public void onSuccess(SendResult sendResult) {
                System.out.println("Send async msg success!");
                System.out.println(sendResult);
            }

            public void onException(Throwable throwable) {
                System.out.println("Send async msg failed!");
                System.out.println(throwable.getMessage());
            }
        });

        producer.shutdown();
    }
}
