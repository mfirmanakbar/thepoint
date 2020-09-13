package com.mfirmanakbar.thepoint.util;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RabbitUtil {

    private static final Logger logger = LoggerFactory.getLogger(RabbitUtil.class);

    public static boolean acknowledge(Channel channel, Long queueDeliveryTag) {
        try {
            if (queueChannelAndTagValidate(channel, queueDeliveryTag)) {
                channel.basicAck(queueDeliveryTag, false);
                logger.info("Successful Acknowledge Queue");
                return true;
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return false;
    }

    public static boolean reject(Channel channel, Long queueDeliveryTag) {
        try {
            if (queueChannelAndTagValidate(channel, queueDeliveryTag)) {
                channel.basicReject(queueDeliveryTag, false);
                logger.info("Successful Reject Queue");
                return true;
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return false;
    }

    private static boolean queueChannelAndTagValidate(Channel channel, Long queueDeliveryTag) {
        return (channel != null && queueDeliveryTag > 0);
    }

}
