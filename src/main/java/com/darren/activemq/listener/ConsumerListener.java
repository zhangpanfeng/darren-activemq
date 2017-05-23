package com.darren.activemq.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import com.darren.activemq.ActivemqContants;
import com.darren.activemq.ProducerConsumer;

/**
 * 消息监听
 * 
 * @author Darren
 *
 */
public class ConsumerListener implements MessageListener {
    private String name;
    private ProducerConsumer producerConsumer;

    public ConsumerListener(String name, ProducerConsumer producerConsumer) {
        this.name = name;
        this.producerConsumer = producerConsumer;
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println(name + " 接收到的消息 " + textMessage.getText());
                // 如果接收到结束标志，修改消费者的状态
                if (ActivemqContants.FINISH_FLAG.equals(textMessage.getText())) {
                    // 消费者消费完成，关闭连接
                    this.producerConsumer.closeConnection();
                }
            } else if (message instanceof ObjectMessage) {
                ObjectMessage objectMessage = (ObjectMessage) message;

                System.out.println(name + " 接收到的消息 " + objectMessage.getObject());
            } else {
                System.out.println("不支持的消息类型！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
