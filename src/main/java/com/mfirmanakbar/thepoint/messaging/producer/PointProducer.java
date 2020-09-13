package com.mfirmanakbar.thepoint.messaging.producer;

import com.mfirmanakbar.thepoint.constant.IConstants;
import com.mfirmanakbar.thepoint.enumeration.ActionEnum;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String pointRequest, ActionEnum actionEnum) {
        switch (actionEnum) {
            case SAVE:
                pushPoint(IConstants.RABBITMQ.KEY.SAVE, pointRequest);
                break;
            case UPDATE:
                pushPoint(IConstants.RABBITMQ.KEY.UPDATE, pointRequest);
                break;
            default:
        }
    }

    private void pushPoint(String key, String pointRequest) {
        rabbitTemplate.convertAndSend(IConstants.RABBITMQ.EXCHANGE.X_POINT, key, pointRequest);
    }
}
