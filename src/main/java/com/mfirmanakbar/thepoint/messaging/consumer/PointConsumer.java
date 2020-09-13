package com.mfirmanakbar.thepoint.messaging.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfirmanakbar.thepoint.constant.IConstants;
import com.mfirmanakbar.thepoint.model.Point;
import com.mfirmanakbar.thepoint.service.PointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import com.rabbitmq.client.Channel;

@Service
public class PointConsumer {

    @Autowired
    PointService pointService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(PointConsumer.class);

    @RabbitListener(queues = IConstants.RABBITMQ.QUEUE.Q_POINT_SAVE)
    public void listenSavePoint(Message message, Channel channel) throws IOException {
        Point point = objectMapper.readValue(message.getBody(), Point.class);
        printRabbitLogData(point);
        pointService.saveFromConsumer(point, channel, message.getMessageProperties().getDeliveryTag());
    }

    @RabbitListener(queues = IConstants.RABBITMQ.QUEUE.Q_POINT_UPDATE)
    public void listenUpdatePoint(Message message, Channel channel) throws IOException {
        Point point = objectMapper.readValue(message.getBody(), Point.class);
        printRabbitLogData(point);
        pointService.updateFromConsumer(point, channel, message.getMessageProperties().getDeliveryTag());
    }

    private void printRabbitLogData(Point request) {
        logger.info("getId: {}", request.getId());
        logger.info("getUserId: {}", request.getUserId());
        logger.info("getCurrentPoint: {}", request.getCurrentPoint());
        logger.info("getCreatedAt: {}", request.getCreatedAt());
        logger.info("getUpdatedAt: {}", request.getUpdatedAt());
        logger.info("getDeletedAt: {}", request.getDeletedAt());
    }
}
