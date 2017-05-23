package com.darren.activemq.queue;

import javax.jms.JMSException;
import javax.jms.Session;
import com.darren.activemq.ProducerConsumer;
import com.darren.activemq.listener.ConsumerListener;

public class QueueListenerConsumer extends ProducerConsumer {

    public QueueListenerConsumer(String name) throws JMSException {
        this.name = name;

        // 通过连接工厂获取连接
        this.connection = this.getConnection();
        // 启动连接
        this.connection.start();
        // 创建Session
        this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建连接的消息队列
        this.destination = this.session.createQueue("test-queue");
        // 创建消息消费者
        this.messageConsumer = this.session.createConsumer(destination);
        // 设置消息监听
        this.messageConsumer.setMessageListener(new ConsumerListener("Listener 1:", this));
    }
}
