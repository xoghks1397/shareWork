package com.sharework.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sharework.base.model.BaseTagSub;

public interface TagSubDao extends JpaRepository<BaseTagSub, Long> {
	List<BaseTagSub> getByBaseTagId(long baseTagId);
}
