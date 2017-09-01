package com.iycharge.server.domain.service;

import com.iycharge.server.domain.entity.account.favorite.Favorite;

/**
 * 站点收藏业务接口
 * @author bwang
 */
public interface FavoriteService {
    
    /**
     * 添加收藏
     * @param favorite
     */
    public void save(Favorite favorite);
    
    /**
     * 取消收藏
     * @param favorite
     */
    public void deleteAccountFavorite(Long accountId, Long stationId);
    
    /**
     * 查询用户收藏的某个电站
     * @param account
     * @param station
     * @return
     */
    public Favorite findAccountFavorite(Long accountId, Long stationId);
}
