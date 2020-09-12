package com.mfirmanakbar.thepoint.service;

import com.mfirmanakbar.thepoint.model.Point;
import com.mfirmanakbar.thepoint.repository.PointRepository;
import com.mfirmanakbar.thepoint.request.PointRequest;
import com.mfirmanakbar.thepoint.util.PointUtil;
import com.mfirmanakbar.thepoint.validate.PointValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class PointServiceImpl implements PointService {

    @Autowired
    PointValidator pointValidator;

    @Autowired
    PointRepository pointRepository;

    @Override
    public ResponseEntity<?> save(PointRequest pointRequest) {
        List<String> errors = pointValidator.validateError(pointRequest);
        if (errors.isEmpty()) {
            Optional<Point> pointById = pointRepository.findById(pointRequest.getUserId());
            if (pointById.isPresent() && pointById.get().getId() > 0) {
                Point point = pointById.get();
                if (pointRequest.getPoint().compareTo(BigInteger.ZERO) < 0) {
                    point.setCurrentPoint(point.getCurrentPoint().subtract(pointRequest.getPoint()));
                } else {
                    point.setCurrentPoint(point.getCurrentPoint().add(pointRequest.getPoint()));
                }
                Point pointSaved = pointRepository.save(point);
                if (pointSaved.getId() > 0) {
                    return new ResponseEntity<>(pointSaved, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Error when save user", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                Point pointSaved = pointRepository.save(PointUtil.convertPointRequestToPoint(pointRequest));
                if (pointSaved.getId() > 0) {
                    return new ResponseEntity<>(pointSaved, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Error when save user", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
