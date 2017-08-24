package com.zhaoxiang.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Author: RiversLau
 * Date: 2017/8/21 17:03
 */
public class JmsProducer {

    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKENURL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKENURL);
        Connection connection = null;
        Session session = null;
        Destination destination;
        MessageProducer producer = null;
        try {
             connection = connectionFactory.createConnection();
             connection.start();
             session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
             destination = session.createQueue("HelloMQ");
             producer = session.createProducer(destination);
             producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
             sendMsg(session, producer);
             session.commit();
        } catch (JMSException e) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e1) {
                    e1.printStackTrace();
                }
            }
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e1) {
                    e1.printStackTrace();
                }
            }
            if (producer != null) {
                try {
                    producer.close();
                } catch (JMSException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void sendMsg(Session session, MessageProducer producer) throws JMSException {

        LauMessage lauMessage = new LauMessage();
        lauMessage.setType(LauMessage.MsgType.PHONE_CODE);
        for (int i = 0; i < 10; i++) {
            lauMessage.setContent("Hello MQ! This is a phone vcode msg!" + i);
            Message msg = session.createObjectMessage(lauMessage);
            producer.send(msg);
        }
    }
}
