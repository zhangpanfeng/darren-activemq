package com.darren.activemq.queue;

import javax.jms.JMSException;
import javax.jms.Session;
import com.darren.activemq.ProducerConsumer;
import com.darren.activemq.listener.UglyConsumerListener;

public class QueueReceiveConsumer extends ProducerConsumer {

    public QueueReceiveConsumer(String name) throws JMSException {
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

        // 启动一个异步线程接受消息，模拟一个消息监听器
        Thread thread = new Thread(new UglyConsumerListener("UglyListener 1:", this));
        thread.start();
    }
}
