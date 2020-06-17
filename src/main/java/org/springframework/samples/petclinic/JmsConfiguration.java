package org.springframework.samples.petclinic;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JmsConfiguration {

	public static final String OWNER_NOTIFICATION_DESTINATION = "ownerNotificationDestination";
	
	@Bean
	public ConnectionFactory factory() {
		return new ActiveMQConnectionFactory();
	}
	
	@Bean
	public Queue ownerNotificationDestination() {
		return new Queue() {
			
			@Override
			public String getQueueName() throws JMSException {
				return OWNER_NOTIFICATION_DESTINATION;
			}
		};
	}
}
