package com.cdac.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.BookingEntity;
import com.cdac.entities.UserEntity;
import com.cdac.entities.WasherEntity;


public interface BookingDao extends JpaRepository<BookingEntity, Long> {
	List<BookingEntity> findByUser(UserEntity user);

	List<BookingEntity> findByWasher(WasherEntity washer);
	
//	List<BookingEntity> findByUserIdAndPackageIdAndWasherIdAndStatusIn(Long userId, Long packageId, Long washerId, List<String> statuses);
	List<BookingEntity> findByUserIdAndPackageEntityIdAndWasherIdAndStatusIn(Long userId, Long packageEntityId, Long washerId, List<String> statuses);

}
