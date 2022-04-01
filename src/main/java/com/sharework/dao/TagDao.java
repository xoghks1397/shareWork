package com.sharework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sharework.base.model.BaseTag;
import com.sharework.base.model.BaseTagSub;

import java.util.List;

public interface TagDao extends JpaRepository<BaseTag, Long> {
}
