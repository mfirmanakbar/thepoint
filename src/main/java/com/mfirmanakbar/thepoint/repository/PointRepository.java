package com.mfirmanakbar.thepoint.repository;

import com.mfirmanakbar.thepoint.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

}
