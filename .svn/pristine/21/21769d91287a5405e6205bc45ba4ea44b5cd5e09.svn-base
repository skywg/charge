package com.iycharge.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iycharge.server.domain.entity.account.favorite.Favorite;

/**
 * 
 * @author bwang
 */
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    
    /**
     * 用户取消收藏
     * @param accountId    用户id
     * @param stationId    站点id
     */
    public void deleteByAccountIdAndStationId(Long accountId, Long stationId);
    

    
    /**
     * 查询用户收藏站点
     * @param accountId         用户id
     * @param station           站点id
     * @return
     */  
   Favorite findByAccountIdAndStationId(Long accountId, Long stationId);
}
