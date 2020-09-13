package com.mfirmanakbar.thepoint.service;

import com.mfirmanakbar.thepoint.enumeration.ActionEnum;
import com.mfirmanakbar.thepoint.messaging.producer.PointProducer;
import com.mfirmanakbar.thepoint.model.Point;
import com.mfirmanakbar.thepoint.repository.PointRepository;
import com.mfirmanakbar.thepoint.request.PointRequest;
import com.mfirmanakbar.thepoint.util.PointUtil;
import com.mfirmanakbar.thepoint.util.RabbitUtil;
import com.mfirmanakbar.thepoint.util.ResponseUtil;
import com.mfirmanakbar.thepoint.validate.PointValidator;
import com.rabbitmq.client.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PointServiceImpl implements PointService {

    @Autowired
    PointValidator pointValidator;

    @Autowired
    PointRepository pointRepository;

    @Autowired
    PointProducer pointProducer;

    @Override
    public ResponseEntity<?> save(PointRequest request) {
        List<String> errors = validateRequest(request, ActionEnum.SAVE);
        Optional<Point> currentRecord = findPointByUserId(request.getUserId());

        if (errors.size() > 0) {
            return ResponseUtil.response(errors, HttpStatus.BAD_REQUEST);
        }

        if (currentRecord.isPresent()) {
            return ResponseUtil.response("User ID already registered, please use another User ID.", HttpStatus.CONFLICT);
        }

        return pushProducer(PointUtil.savePoint(request), ActionEnum.SAVE);
    }

    @Override
    public ResponseEntity<?> update(PointRequest request) {
        List<String> errors = validateRequest(request, ActionEnum.UPDATE);
        Optional<Point> currentRecord = findPointByUserId(request.getUserId());

        if (errors.size() > 0) {
            return ResponseUtil.response(errors, HttpStatus.BAD_REQUEST);
        }

        if (!currentRecord.isPresent()) {
            return ResponseUtil.response("User not found.", HttpStatus.NOT_FOUND);
        }

        Point point = currentRecord.get();
        point.setCurrentPoint(
                PointUtil.updateCurrentPoint(request.getPoint(), point.getCurrentPoint())
        );
        point.setUpdatedAt(new Date());

        return pushProducer(point, ActionEnum.UPDATE);
    }

    private ResponseEntity<?> pushProducer(Point point, ActionEnum actionEnum) {
        try {
            pointProducer.sendMessage(PointUtil.pointRequestToMsg(point), actionEnum);
            return ResponseUtil.response("Success", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseUtil.response("Error when try to Save/Update Point", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean saveFromConsumer(Point point, Channel channel, long deliveryTag) {
        RabbitUtil.acknowledge(channel, deliveryTag);
        Point saveIt = pointRepository.save(point);
        return saveIt.getId() > 0;
    }

    @Override
    public boolean updateFromConsumer(Point point, Channel channel, long deliveryTag) {
        RabbitUtil.acknowledge(channel, deliveryTag);
        Point updateIt = pointRepository.save(point);
        return updateIt.getId() > 0;
    }

    @Override
    public ResponseEntity<?> findAll() {
        List<Point> points = pointRepository.findAll();
        if (points.size() > 0) {
            return ResponseUtil.response(points, HttpStatus.OK);
        } else {
            return ResponseUtil.response("Data not found", HttpStatus.NOT_FOUND);
        }
    }

    private Optional<Point> findPointByUserId(long userId) {
        return pointRepository.findByUserId(userId);
    }

    private List<String> validateRequest(PointRequest request, ActionEnum actionEnum) {
        List<String> errors;
        switch (actionEnum) {
            case SAVE:
                errors = pointValidator.validateSaveError(request);
                break;
            case UPDATE:
                errors = pointValidator.validateUpdateError(request);
                break;
            default:
                errors = new ArrayList<>();
        }
        return errors;
    }
}
