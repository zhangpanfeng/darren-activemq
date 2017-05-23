package com.darren.activemq.queue;

import javax.jms.JMSException;

public class QueueTest {
    public static void main(String[] args) {
        Thread thread = null;
        try {
            // 启动消费者，消费者开始等待
            new QueueListenerConsumer("QueueListenerConsumer");
            new QueueReceiveConsumer("QueueReceiveConsumer");

            thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        // 启动生产者，生产者定时生产消息
                        QueueProducer producer = new QueueProducer("QueueProducer");
                        Thread.sleep(2000);
                        // 第一次发送
                        producer.sendMessage();
                        producer.commit();

                        Thread.sleep(2000);
                        // 第二次发送
                        producer.sendMessage();
                        producer.commit();

                        Thread.sleep(2000);
                        // 第三次发送
                        producer.sendMessage();
                        producer.commit();

                        // 发送结束标志
                        producer.sendFinishMessage(2);
                        producer.commit();

                        // 生产者生产完成，关闭连接
                        producer.closeConnection();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
