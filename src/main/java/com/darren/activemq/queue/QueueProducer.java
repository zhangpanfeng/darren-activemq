package com.darren.activemq.queue;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.darren.activemq.ActivemqContants;
import com.darren.activemq.ProducerConsumer;

public class QueueProducer extends ProducerConsumer {
    private int startNumber;
    private int endNumber;

    public QueueProducer(String name) throws JMSException {
        this.name = name;

        // 通过连接工厂获取连接
        this.connection = this.getConnection();
        // 启动连接
        this.connection.start();
        // 创建Session
        this.session = this.connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        // 创建消息队列
        this.destination = this.session.createQueue("test-queue");
        // 创建消息生产者
        this.messageProducer = this.session.createProducer(destination);
    }

    /**
     * 发送消息
     * 
     * @throws JMSException
     */
    public void sendMessage() throws JMSException {
        this.startNumber = this.endNumber;
        this.endNumber = this.startNumber + MESSAGE_COUNT;
        for (int i = this.startNumber; i < this.endNumber; i++) {
            TextMessage message = this.session.createTextMessage("I send the message " + i);
            System.out.println(message.getText());
            this.messageProducer.send(message);
        }
    }

    /**
     * 发送结束标志
     * 
     * @param times
     *            发送次数
     * @throws JMSException
     */
    public void sendFinishMessage(int times) throws JMSException {
        for (int i = 0; i < times; i++) {
            TextMessage message = this.session.createTextMessage(ActivemqContants.FINISH_FLAG);
            System.out.println("Send finish flag: " + message.getText());
            this.messageProducer.send(message);
        }
    }

    /**
     * 提交事务
     * 
     * @throws JMSException
     */
    public void commit() throws JMSException {
        this.session.commit();
    }
}
