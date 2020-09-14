package com.mfirmanakbar.thepoint.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mfirmanakbar.thepoint.model.Point;
import com.mfirmanakbar.thepoint.request.PointRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PointUtilTest {

    @Test
    public void savePoint() {
        PointRequest pointRequest = new PointRequest();
        pointRequest.setUserId(123);
        pointRequest.setPoint(BigInteger.valueOf(500));

        Point point = PointUtil.savePoint(pointRequest);

        assertEquals(pointRequest.getUserId(), point.getUserId());
        assertEquals(pointRequest.getPoint(), point.getCurrentPoint());
    }

    @Test
    public void updateCurrentPoint() {
        BigInteger currentPoint = BigInteger.valueOf(200);
        BigInteger requestPointPlus = BigInteger.valueOf(50);
        BigInteger requestPointMinus = BigInteger.valueOf(-50);

        BigInteger actual1 = PointUtil.updateCurrentPoint(requestPointPlus, currentPoint);
        BigInteger actual2 = PointUtil.updateCurrentPoint(requestPointMinus, currentPoint);

        assertEquals(BigInteger.valueOf(250), actual1);
        assertEquals(BigInteger.valueOf(150), actual2);
    }

    @Test
    public void pointRequestToMsg() throws JsonProcessingException {

        String expected = "{\"id\":1,\"user_id\":123,\"current_point\":500,\"created_at\":null,\"updated_at\":null,\"deleted_at\":null}";

        Point point = Point.builder()
                .id(1)
                .userId(123)
                .currentPoint(BigInteger.valueOf(500))
                .build();

        String actual = PointUtil.pointRequestToMsg(point);

        assertEquals(expected, actual);
    }

}
