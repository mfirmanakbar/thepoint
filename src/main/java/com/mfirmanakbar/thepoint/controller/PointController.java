package com.mfirmanakbar.thepoint.controller;

import com.mfirmanakbar.thepoint.request.PointRequest;
import com.mfirmanakbar.thepoint.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("point")
public class PointController {

    @Autowired
    private PointService pointService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> savePoint(@RequestBody PointRequest pointRequest) {
        return pointService.save(pointRequest);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getPoint(@RequestBody PointRequest pointRequest) {
        return pointService.save(pointRequest);
    }

}
