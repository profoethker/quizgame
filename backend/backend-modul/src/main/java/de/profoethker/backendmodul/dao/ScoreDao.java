package de.profoethker.backendmodul.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import de.profoethker.backendmodul.model.Score;

@Repository
public interface ScoreDao extends JpaRepository<Score, Integer> {

	@Modifying
	Score save(Score data);

}
