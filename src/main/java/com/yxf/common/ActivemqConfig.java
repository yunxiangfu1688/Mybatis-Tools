package com.yxf.common;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;

@Configuration
public class ActivemqConfig {
	@Value("${spring.activemq.broker-url}")
    private String broker_url;
    @Bean
    public JmsMessagingTemplate jmsMessagingTemplate(){
        return new JmsMessagingTemplate(new ActiveMQConnectionFactory(broker_url));
    }
    @Bean
    public JmsListenerContainerFactory<?> containerFactory(){	//配置监听容器工厂
        DefaultJmsListenerContainerFactory containerFactory=new DefaultJmsListenerContainerFactory();
        containerFactory.setConnectionFactory(new ActiveMQConnectionFactory(broker_url));
        containerFactory.setPubSubDomain(true);
        return containerFactory;
    }

}
