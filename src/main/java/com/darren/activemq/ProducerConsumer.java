package com.darren.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public abstract class ProducerConsumer {
    protected static final String USERNAME = ActiveMQConnection.DEFAULT_USER;// 默认的连接用户名
    protected static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;// 默认的连接密码
    protected static final String BROKERURL = ActiveMQConnection.DEFAULT_BROKER_URL;// 默认的连接地址
    protected static final int MESSAGE_COUNT = 10;// 发送的消息数量

    protected static ConnectionFactory connectionFactory;// 连接工厂
    protected Connection connection = null; // 连接
    protected Session session;// 会话 接受或者发送消息的线程
    protected Destination destination;// 消息的目的地
    protected MessageProducer messageProducer;// 消息生产者
    protected MessageConsumer messageConsumer;// 消息消费者

    // 状态
    protected volatile boolean isFinished = false;
    protected String name;

    static {
        getConnectionFactory();
    }

    /**
     * 获取连接工厂
     * 
     * @return
     */
    protected synchronized static ConnectionFactory getConnectionFactory() {
        if (connectionFactory == null) {
            connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);
        }

        return connectionFactory;
    }

    /**
     * 获取连接
     * 
     * @return
     * @throws JMSException
     */
    protected Connection getConnection() throws JMSException {
        return connectionFactory.createConnection();
    };

    /**
     * 关闭连接
     * 
     * @throws JMSException
     */
    public void closeConnection() throws JMSException {
        if (this.connection != null) {
            this.connection.close();
        }

        System.out.println(this.name + " connection close!");

        this.isFinished = true;
    }

    /**
     * 获取状态
     * 
     * @return
     */
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * 获取消费者
     * 
     * @return
     */
    public MessageConsumer getMessageConsumer() {
        return messageConsumer;
    }

    /**
     * 获取生产者或消费者名称
     * 
     * @return
     */
    public String getName() {
        return name;
    }
}
