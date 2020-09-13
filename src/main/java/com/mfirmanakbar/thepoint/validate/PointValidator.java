package com.mfirmanakbar.thepoint.validate;

import com.mfirmanakbar.thepoint.request.PointRequest;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PointValidator {

    public List<String> validateSaveError(PointRequest request) {

        List<String> errors = new ArrayList<>();

        if (request.getUserId() <= 0) {
            errors.add("User Id can't be null");
        }

        return Stream.concat(errors.stream(), sameError(request).stream()).collect(Collectors.toList());
    }

    public List<String> validateUpdateError(PointRequest request) {
        List<String> errors = new ArrayList<>();

        if (request.getId() < 1) {
            errors.add("Point Id can't be null");
        }

        return Stream.concat(errors.stream(), sameError(request).stream()).collect(Collectors.toList());
    }

    private List<String> sameError(PointRequest request) {
        ArrayList<String> errors = new ArrayList<>();

        if (request.getPoint().compareTo(BigInteger.ZERO) == 0) {
            errors.add("User's Point can't be Zero");
        }

        return errors;
    }

}
