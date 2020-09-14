package com.mfirmanakbar.thepoint.validate;

import com.mfirmanakbar.thepoint.request.PointRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PointValidatorTest {

    @InjectMocks
    PointValidator pointValidator;

    @Test
    public void validateSaveError_NoError() {

        PointRequest pointRequest = new PointRequest();
        pointRequest.setUserId(123);
        pointRequest.setPoint(BigInteger.valueOf(500));

        List<String> errs = pointValidator.validateSaveError(pointRequest);

        assertEquals(0, errs.size());
    }

    @Test
    public void validateSaveError_WithError() {

        PointRequest pointRequest = new PointRequest();
        pointRequest.setUserId(-1);
        pointRequest.setPoint(BigInteger.valueOf(0));

        List<String> errs = pointValidator.validateSaveError(pointRequest);

        assertEquals(2, errs.size());
        assertEquals("User Id can't be null", errs.get(0));
        assertEquals("User's Point can't be Zero", errs.get(1));
    }

    @Test
    public void validateUpdateError_NoError() {

        PointRequest pointRequest = new PointRequest();
        pointRequest.setId(1);
        pointRequest.setPoint(BigInteger.valueOf(500));

        List<String> errs = pointValidator.validateUpdateError(pointRequest);

        assertEquals(0, errs.size());
    }

    @Test
    public void validateUpdateError_WithError() {

        PointRequest pointRequest = new PointRequest();
        pointRequest.setId(0);
        pointRequest.setPoint(BigInteger.valueOf(0));

        List<String> errs = pointValidator.validateUpdateError(pointRequest);

        assertEquals(2, errs.size());
        assertEquals("Point Id can't be null", errs.get(0));
        assertEquals("User's Point can't be Zero", errs.get(1));
    }
}
