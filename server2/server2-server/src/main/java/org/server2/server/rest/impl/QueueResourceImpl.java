package org.server2.server.rest.impl;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.server2.api.QueueResource;
import org.springframework.web.bind.annotation.RestController;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;

@RestController
public class QueueResourceImpl implements QueueResource {

	@Override
	public void sendMessage() {
		Properties properties = new Properties();
		// 您在 MQ 控制台创建的 Producer ID
		properties.put(PropertyKeyConst.ProducerId, "");
		// 鉴权用 AccessKey，在阿里云服务器管理控制台创建
		properties.put(PropertyKeyConst.AccessKey, "");
		// 鉴权用 SecretKey，在阿里云服务器管理控制台创建
		properties.put(PropertyKeyConst.SecretKey, "");
		// 设置 TCP 接入域名（此处以公共云的公网接入为例）
		properties.put(PropertyKeyConst.ONSAddr, "");

		Producer producer = ONSFactory.createProducer(properties);
		// 在发送消息前，必须调用 start 方法来启动 Producer，只需调用一次即可
		producer.start();

		// 循环发送消息
		Message msg = new Message( //
				// 在控制台创建的 Topic，即该消息所属的 Topic 名称
				"kdyzm",
				// Message Tag,
				// 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在 MQ 服务器过滤
				"test1",
				// Message Body
				// 任何二进制形式的数据， MQ 不做任何干预，
				// 需要 Producer 与 Consumer 协商好一致的序列化和反序列化方式
				"Hello World!".getBytes());
		// 设置代表消息的业务关键属性，请尽可能全局唯一，以方便您在无法正常收到消息情况下，可通过 MQ 控制台查询消息并补发
		// 注意：不设置也不会影响消息正常收发
		msg.setKey("key_test");
		// 发送消息，只要不抛异常就是成功
		// 打印 Message ID，以便用于消息发送状态查询
		SendResult sendResult = producer.send(msg);
		System.out.println("Send Message success. Message ID is: " + sendResult.getMessageId());

		// 在应用退出前，可以销毁 Producer 对象
		// 注意：如果不销毁也没有问题
		 producer.shutdown();
	}

	@Override
	public void consumeMessage() {
		Properties properties = new Properties();
        // 您在控制台创建的 Consumer ID
        properties.put(PropertyKeyConst.ConsumerId, "");
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, "");
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, "");
        // 设置 TCP 接入域名（此处以公共云生产环境为例）
        properties.put(PropertyKeyConst.ONSAddr,
          "");
          // 集群订阅方式 (默认)
          // properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.CLUSTERING);
          // 广播订阅方式
          // properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.BROADCASTING);
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe("kdyzm", "test1", new MessageListener() { //订阅多个Tag
            public Action consume(Message message, ConsumeContext context) {
                try {
					System.out.println("Receive: " + new String(message.getBody(),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                return Action.CommitMessage;
            }
        });
        //订阅另外一个Topic
//        consumer.subscribe("TopicTestMQ-Other", "*", new MessageListener() { //订阅全部Tag
//            public Action consume(Message message, ConsumeContext context) {
//                System.out.println("Receive: " + message);
//                return Action.CommitMessage;
//            }
//        });
        consumer.start();
        System.out.println("Consumer Started");
	}

}
