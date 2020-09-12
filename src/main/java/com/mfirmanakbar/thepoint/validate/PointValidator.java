package com.mfirmanakbar.thepoint.validate;

import com.mfirmanakbar.thepoint.request.PointRequest;
import com.mfirmanakbar.thepoint.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class PointValidator {

    @Autowired
    private PointService pointService;

    public List<String> validateError(PointRequest request) {
        ArrayList<String> errors = new ArrayList<>();

        if (request.getUserId() <= 0) {
            errors.add("User Id can't be null");
        }

        if (request.getPoint().compareTo(BigInteger.ZERO) == 0) {
            errors.add("User Point can't be zero");
        }

        return errors;
    }
}
