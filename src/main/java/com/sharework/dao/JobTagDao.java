package com.sharework.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import com.sharework.model.JobTag;
import java.util.List;

public interface JobTagDao extends JpaRepository<JobTag, Long> {
    List<JobTag> findByJobId(long jobId);
}
