package de.profoethker.backendmodul.dao;

import org.springframework.stereotype.Repository;
import de.profoethker.backendmodul.model.QA;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface QADao extends JpaRepository<QA, Long> {

	@Modifying
	QA save(QA data);


}
