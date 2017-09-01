package com.iycharge.server.domain.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.Car;
import com.iycharge.server.domain.entity.account.CarAuditLog;
import com.iycharge.server.domain.entity.account.CarIdentifyStatus;
import com.iycharge.server.domain.repository.AccountRepository;
import com.iycharge.server.domain.repository.CarRepository;
import com.iycharge.server.domain.service.CarService;


@Service
public class CarServiceImpl implements CarService {

	@Autowired
	private CarRepository carRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Override
	public Page<Car> findAll(Pageable pageable) {

		return carRepository.findAll(pageable);
	}
	@Override
	public List<Car> findByCarIdentifyStatus() {

		return carRepository.findByCarIdentifyStatus(CarIdentifyStatus.CHECKING);
	}
	
	@Override
	public Page<Car> findAllSearch(final String[] fields, final Car car, Pageable pageable) {
		Specification<Car> spec = new Specification<Car>() {
			@Override
			public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate ps = null;
				Predicate plist[] = null;
				List<Predicate> predicate = new ArrayList<Predicate>();
				Map<String, Object> map = new HashMap<String, Object>();
				for (String fieldName : fields) {
					if ("createdAt".equals(fieldName)) {
						if (car.getStartAt()!= null && car.getUpdatedAt() != null) {
							List<Date> date = new ArrayList<Date>();
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(car.getUpdatedAt());
							calendar.add(calendar.DATE, 1);
							date.add(car.getStartAt());
							date.add(calendar.getTime());
							map.put("updatedAt", date);
						}
					} else if("carIdentifyStatus".equals(fieldName)){
						Object o = ReflectField.getFieldValueByName(fieldName, car);
						if (o != null && !"".equals(o)) {
							map.put(fieldName, o.toString().trim());
						}
					}
					else {
						Object o = ReflectField.getFieldValueByName(fieldName, car.getAccount());
						if (o != null && !"".equals(o)) {
							map.put(fieldName, o.toString().trim());
						}
					}
				}
				// 为属性附加sql条件
				if (map != null) {
					for (String fieldName : map.keySet()) {
						Path<String> nick = null;
						Path<Date> nickDate = null;
						if ("updatedAt".equals(fieldName)) {
							nickDate = root.get("updatedAt");
						}else if("carIdentifyStatus".equals(fieldName)){
							nick = root.get("carIdentifyStatus");
						}
						else {
							nick = root.get("account").get(fieldName);
						}
						Object str = map.get(fieldName);
						if (fieldName.equals("realName")) {
							if (StringUtils.isNotEmpty((String) (str))) {
								predicate.add(cb.like(nick, "%" + str + "%"));
							}
						} else if (fieldName.equals("phone")) {
							if (StringUtils.isNotEmpty((String) (str))) {
								predicate.add(cb.like(nick, "%" + str + "%"));
							}
						} else if (fieldName.equals("carIdentifyStatus")) {
							if (StringUtils.isNotEmpty((String) (str))) {
								predicate.add(cb.equal(nick, car.getCarIdentifyStatus()));
							}
						} if (fieldName.equals("updatedAt")) {
							List<Date> date = (List<Date>) str;
							predicate.add(cb.between(nickDate, date.get(0), date.get(1)));
						}
					}
				}
				if (predicate.size() > 0) {
					plist = new Predicate[predicate.size()];
					// 把集合转换成数组
					ps = cb.and(predicate.toArray(plist));
					if (ps != null) {
						query.where(ps);
					}
				}
				return null;
			}
		};
		return carRepository.findAll(spec, pageable);
	}

	@Override
	public Car findById(Long id) {

		return carRepository.findOne(id);
	}

	@Override
	public Car save(Car car) {
		
		return carRepository.saveAndFlush(car);
	}

	@Override
	public Account addAndUpdate(Account account,final Car car) {
		account.setCar(car);
		return accountRepository.saveAndFlush(account);
	}


	@Override
	public Car updateCarAndCarAuditLog(Car car, List<CarAuditLog> carAuditLogList) {
		car.setCarAuditLogList(carAuditLogList);
		return carRepository.saveAndFlush(car);
	}
	
	
}
