package com.mfirmanakbar.thepoint.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    private ResponseUtil() {
    }

    public static ResponseEntity<?> response(Object body, HttpStatus httpStatus) {
        return new ResponseEntity<>(body, httpStatus);
    }

}
