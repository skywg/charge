package com.iycharge.server.domain.repository;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.AccountRechargeLog;
import com.iycharge.server.domain.entity.account.AccountStation;
import com.iycharge.server.domain.entity.station.Station;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStationRepository extends JpaRepository<AccountStation, Long> {
	
	public  List<AccountStation> findByAccountAndStation(Account account,Station station);
}
