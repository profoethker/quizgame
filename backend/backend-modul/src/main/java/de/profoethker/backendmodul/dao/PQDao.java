package de.profoethker.backendmodul.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import de.profoethker.backendmodul.model.PQ;

@Repository
public interface PQDao extends JpaRepository<PQ, Integer> {

	@Modifying
	PQ save(PQ data);
}
