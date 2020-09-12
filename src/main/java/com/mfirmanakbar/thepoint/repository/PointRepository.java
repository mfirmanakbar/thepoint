package com.mfirmanakbar.thepoint.repository;

import com.mfirmanakbar.thepoint.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    Optional<Point> findByUserId(Long aLong);
}
