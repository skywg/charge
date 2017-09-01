package com.iycharge.server.domain.repository;

import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.charger.ChargerGun;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChargerGunRepository extends JpaRepository<ChargerGun, Long> {
	public void deleteByCharger(Charger id);
}
