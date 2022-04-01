package com.sharework.dao;

import com.sharework.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobDao extends JpaRepository<Job, Long> {

	@Query(value = "SELECT * FROM job WHERE lat BETWEEN :s.Lat AND :n.Lat AND lng BETWEEN  :s.Lng AND :n.Lng LIMIT 800", nativeQuery = true)
	List<Job> selectJobs(@Param("s.Lat") double SouthwestLat, @Param("n.Lat") double northeastLat,
			@Param("s.Lng") double SouthwestLng, @Param("n.Lng") double northeastLng);

	@Query(value = "SELECT j FROM Job j WHERE j.id IN ?1")
	Page<Job> findJobsDetail(long[] JobId, Pageable pageable);

	List<Job>  findJobsByUserId(long userId);
}
