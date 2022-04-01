package com.sharework.dao;

import com.sharework.model.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationDao extends JpaRepository<Application, Long> {
	List<Application> findUserIdByJobId(long jobId);

	Application findByJobIdAndUserId(long jobId, long userId);

	List<Application> findByJobIdAndStatus(long jobId, String status);

	@Query(value = "select * from application where job_id = :job_id and status = :status", nativeQuery = true)
	Page<Application> findByJobIdAndApplied(@Param("job_id") long JobId, @Param("status")String status, Pageable pageable);

	@Query(value = "select * from application where user_id = :user_id and status = :status", nativeQuery = true)
	Page<Application> getApplicationList(@Param("user_id") long userId, @Param("status") String status,
			Pageable pageable);

	int countByUserIdAndStatus(long userId, String status);
}
