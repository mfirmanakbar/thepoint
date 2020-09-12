package com.mfirmanakbar.thepoint.util;

import com.mfirmanakbar.thepoint.model.Point;
import com.mfirmanakbar.thepoint.request.PointRequest;

import java.util.Date;

public class PointUtil {

    public static Point convertPointRequestToPoint(PointRequest request){

        return Point.builder()
                //.id(request.getId())
                .userId(request.getUserId())
                .currentPoint(request.getPoint())
                .createdAt(new Date())
                .updatedAt(new Date())
                .deletedAt(null)
                .build();
    }

}
