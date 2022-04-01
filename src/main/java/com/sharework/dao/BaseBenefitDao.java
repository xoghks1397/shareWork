package com.sharework.dao;


import com.sharework.base.model.BaseBenefit;
import com.sharework.model.JobBenefit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BaseBenefitDao extends JpaRepository<BaseBenefit, Long> {
    Optional<BaseBenefit> findById(long id);

}
