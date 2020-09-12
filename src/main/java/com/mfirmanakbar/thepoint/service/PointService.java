package com.mfirmanakbar.thepoint.service;

import com.mfirmanakbar.thepoint.request.PointRequest;
import org.springframework.http.ResponseEntity;

public interface PointService {
    ResponseEntity<?> save(PointRequest pointRequest);
}
