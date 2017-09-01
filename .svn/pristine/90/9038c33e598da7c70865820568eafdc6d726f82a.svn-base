package com.iycharge.server.domain.repository;

import com.iycharge.server.domain.entity.Authorization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {
    public Authorization findByProviderAndUid(Authorization.Provider provider, String uid);
    
    @Modifying 
    @Query("delete from Authorization a where a.id=:id")
    void deleteAuthorizationById(@Param("id") Long id); 
}
