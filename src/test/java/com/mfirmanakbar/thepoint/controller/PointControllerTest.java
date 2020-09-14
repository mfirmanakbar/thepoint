package com.mfirmanakbar.thepoint.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfirmanakbar.thepoint.model.Point;
import com.mfirmanakbar.thepoint.request.PointRequest;
import com.mfirmanakbar.thepoint.service.PointService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PointController.class)
public class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PointService pointService;

    @Test
    public void savePoint() throws Exception {
        PointRequest pointRequest = new PointRequest();
        pointRequest.setUserId(123);
        pointRequest.setPoint(BigInteger.valueOf(500));

        ResponseEntity responseEntity = new ResponseEntity<>("Success", HttpStatus.OK);

        when(pointService.save(any(PointRequest.class))).thenReturn(responseEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/point")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(pointRequest));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    public void updatePoint() throws Exception {
        PointRequest pointRequest = new PointRequest();
        pointRequest.setId(1);
        pointRequest.setUserId(123);
        pointRequest.setPoint(BigInteger.valueOf(500));

        ResponseEntity responseEntity = new ResponseEntity<>("Success", HttpStatus.OK);

        when(pointService.update(any(PointRequest.class))).thenReturn(responseEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/point")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(pointRequest));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    public void getAllPoints() throws Exception {

        String resExpected = "[{\"id\":1,\"user_id\":123,\"current_point\":500,\"created_at\":null,\"updated_at\":null,\"deleted_at\":null},{\"id\":2,\"user_id\":124,\"current_point\":800,\"created_at\":null,\"updated_at\":null,\"deleted_at\":null}]";

        Point point1 = Point.builder()
                .id(1)
                .userId(123)
                .currentPoint(BigInteger.valueOf(500))
                .build();

        Point point2 = Point.builder()
                .id(2)
                .userId(124)
                .currentPoint(BigInteger.valueOf(800))
                .build();

        List<Point> points = new ArrayList<>();
        points.add(0, point1);
        points.add(1, point2);

        ResponseEntity responseEntity = new ResponseEntity<>(points, HttpStatus.OK);

        when(pointService.findAll()).thenReturn(responseEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/point")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(resExpected));
    }
}
