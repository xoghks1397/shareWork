package com.sharework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sharework.model.LocationFavorite;
import java.util.List;

public interface LocationFavoriteDao extends JpaRepository<LocationFavorite, Long> {

    List<LocationFavorite> findByUserId(long userId);

    int countByUserId(long userId);
}
