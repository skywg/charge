package com.iycharge.server.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iycharge.server.domain.entity.account.favorite.Favorite;
import com.iycharge.server.domain.repository.FavoriteRepository;
import com.iycharge.server.domain.service.FavoriteService;

/**
 * 站点收藏业务接口实现类
 * @author bwang
 */
@Transactional(readOnly=false)
@Service
public class FavoriteServiceImpl implements FavoriteService {
    
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Override
    @Transactional(readOnly=true)
    public void save(Favorite favorite) {
        favoriteRepository.saveAndFlush(favorite);
    }

    @Override
    @Transactional(readOnly=true)
    public void deleteAccountFavorite(Long accountId, Long stationId) {
        favoriteRepository.deleteByAccountIdAndStationId(accountId, stationId);       
    }

    @Override
    public Favorite findAccountFavorite(Long accountId, Long stationId) {
        return favoriteRepository.findByAccountIdAndStationId(accountId, stationId);
    }  
}
