package com.zhaoxiang.rocketmq.quickStart;

import com.zhaoxiang.rocketmq.RocketMQConstants;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * Author: RiversLau
 * Date: 2017/9/4 17:27
 */
public class Consumer {

    public static void main(String[] args) throws MQClientException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("master-slave");
        consumer.setNamesrvAddr(RocketMQConstants.NAMESRVADDR);
//        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.setInstanceName("Producer");
        consumer.subscribe("msTopic", "TagA||TagB");

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                String brokerName = consumeConcurrentlyContext.getMessageQueue().getBrokerName();
                for (MessageExt messageExt : list) {
                    System.out.println(messageExt.getBornHostString() + ":" + messageExt.getStoreHost().toString());
                    System.out.println(brokerName + ":" + messageExt.getTopic() + ":" + messageExt.getTags() + ":" + new String(messageExt.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
    }
}
