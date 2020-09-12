package com.mfirmanakbar.thepoint.service;

import com.mfirmanakbar.thepoint.enumeration.ActionEnum;
import com.mfirmanakbar.thepoint.model.Point;
import com.mfirmanakbar.thepoint.repository.PointRepository;
import com.mfirmanakbar.thepoint.request.PointRequest;
import com.mfirmanakbar.thepoint.util.PointUtil;
import com.mfirmanakbar.thepoint.validate.PointValidator;
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

    @Override
    public ResponseEntity<?> save(PointRequest request) {
        List<String> errors = validateRequest(request, ActionEnum.SAVE);
        Optional<Point> currentRecord = findPointByUserId(request.getUserId());

        if (errors.size() > 0) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        if (currentRecord.isPresent()) {
            return new ResponseEntity<>("User ID already registered, please use another User ID.", HttpStatus.CONFLICT);
        }

        Point saveIt = pointRepository.save(PointUtil.savePoint(request));
        if (saveIt.getId() > 0) {
            return new ResponseEntity<>(saveIt, HttpStatus.OK);
        }

        return new ResponseEntity<>("Error when try to save Point", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> update(PointRequest request) {
        List<String> errors = validateRequest(request, ActionEnum.UPDATE);
        Optional<Point> currentRecord = findPointByUserId(request.getUserId());

        if (errors.size() > 0) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        if (!currentRecord.isPresent()) {
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        }

        Point point = currentRecord.get();
        point.setCurrentPoint(
                PointUtil.UpdateCurrentPoint(request.getPoint(), point.getCurrentPoint())
        );
        point.setUpdatedAt(new Date());

        Point updateIt = pointRepository.save(point);
        if (updateIt.getId() > 0) {
            return new ResponseEntity<>(updateIt, HttpStatus.OK);
        }

        return new ResponseEntity<>("Error when try to update Point", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> findAll() {
        List<Point> points = pointRepository.findAll();
        if (points.size() > 0) {
            return new ResponseEntity<>(points, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("no Point found", HttpStatus.NOT_FOUND);
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
