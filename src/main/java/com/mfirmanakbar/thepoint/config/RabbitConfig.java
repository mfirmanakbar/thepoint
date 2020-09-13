package com.mfirmanakbar.thepoint.config;

import com.mfirmanakbar.thepoint.constant.IConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitConfig {

    @Bean
    Queue queueSavePoint() {
        return new Queue(IConstants.RABBITMQ.QUEUE.Q_POINT_SAVE, true, false, false);
    }

    @Bean
    Queue queueUpdatePoint() {
        return new Queue(IConstants.RABBITMQ.QUEUE.Q_POINT_UPDATE, true, false, false);
    }

    @Bean
    DirectExchange directExchangePoint() {
        return new DirectExchange(IConstants.RABBITMQ.EXCHANGE.X_POINT, true, false);
    }

    @Bean
    Binding bindingQueueSavePoint() {
        return bindingBuilder(IConstants.RABBITMQ.KEY.SAVE, queueSavePoint());
    }

    @Bean
    Binding bindingQueueUpdatePoint() {
        return bindingBuilder(IConstants.RABBITMQ.KEY.UPDATE, queueUpdatePoint());
    }

    private Binding bindingBuilder(String key, Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(directExchangePoint())
                .with(key);
    }
}
