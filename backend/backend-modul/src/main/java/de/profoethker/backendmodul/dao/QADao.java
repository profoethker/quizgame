package de.profoethker.backendmodul.dao;

import org.springframework.stereotype.Repository;
import de.profoethker.backendmodul.model.QA;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

@Repository
public interface QADao extends JpaRepository<QA, Integer> {

	@Modifying
	QA save(QA data);


}
