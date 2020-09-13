package com.mfirmanakbar.thepoint.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfirmanakbar.thepoint.model.Point;
import com.mfirmanakbar.thepoint.request.PointRequest;

import java.math.BigInteger;
import java.util.Date;

public class PointUtil {

    private ObjectMapper objectMapper = new ObjectMapper();

    private PointUtil() {
    }

    public static Point savePoint(PointRequest request){

        return Point.builder()
                .userId(request.getUserId())
                .currentPoint(request.getPoint())
                .createdAt(new Date())
                .updatedAt(new Date())
                .deletedAt(null)
                .build();
    }

    public static BigInteger updateCurrentPoint(BigInteger requestPoint, BigInteger currentPoint) {
        if (currentPoint.compareTo(BigInteger.ZERO) < 0) {
            currentPoint = currentPoint.subtract(requestPoint);
        } else {
            currentPoint = currentPoint.add(requestPoint);
        }
        return currentPoint;
    }

    public static String pointRequestToMsg(Point point) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(point);
    }

}
