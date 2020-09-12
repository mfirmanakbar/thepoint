package com.mfirmanakbar.thepoint.model;

import com.mfirmanakbar.thepoint.helper.CustomJSONRootName;
import lombok.*;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "points")
@CustomJSONRootName(singular = "point", plural = "points")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long userId;

    private BigInteger currentPoint;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;


}
