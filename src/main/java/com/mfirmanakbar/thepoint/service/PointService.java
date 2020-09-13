package com.mfirmanakbar.thepoint.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mfirmanakbar.thepoint.model.Point;
import com.mfirmanakbar.thepoint.request.PointRequest;
import com.rabbitmq.client.Channel;
import org.springframework.http.ResponseEntity;

public interface PointService {

    ResponseEntity<?> save(PointRequest request);

    ResponseEntity<?> update(PointRequest request);

    ResponseEntity<?> findAll();

    boolean saveFromConsumer(Point point, Channel channel, long deliveryTag);

    boolean updateFromConsumer(Point point, Channel channel, long deliveryTag);
}
