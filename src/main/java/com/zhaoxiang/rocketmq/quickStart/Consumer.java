package com.zhaoxiang.rocketmq.quickStart;

import com.zhaoxiang.rocketmq.RocketMQConstants;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * Author: RiversLau
 * Date: 2017/9/4 17:27
 */
public class Consumer {

    public static void main(String[] args) throws MQClientException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("testGroupName");
        consumer.setNamesrvAddr(RocketMQConstants.NAMESRVADDR);
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.setInstanceName("Producer");
        consumer.subscribe("testTopic", "TagA||TagB");

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            int brokerA = 0, brokerB;
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                String brokerName = consumeConcurrentlyContext.getMessageQueue().getBrokerName();
                if ("broker-a".equals(brokerName)) {
                    brokerA++;
                }
                if ("broker-b".equals(brokerName)) {
                    brokerB++;
                }
                for (MessageExt messageExt : list) {
                    System.out.println(messageExt.getStoreHost().toString() + messageExt.getTopic() + ":" + messageExt.getTags() + ":" + new String(messageExt.getBody()));
                }
                System.out.println("broker-a ==" + brokerA + ";broker-b ==" + brokerB);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
    }
}
