package com.mfirmanakbar.thepoint.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Setter
@Getter
public class PointRequest {

    @JsonProperty("id")
    private long id;

    @JsonProperty("user_id")
    private long userId;

    private BigInteger point;

}
