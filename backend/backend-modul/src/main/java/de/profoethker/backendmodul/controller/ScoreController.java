package de.profoethker.backendmodul.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import de.profoethker.backendmodul.dao.ScoreDao;
import de.profoethker.backendmodul.model.Score;

public class ScoreController {

	private final ScoreDao scoreDao;

	Logger logger = LoggerFactory.getLogger(ScoreController.class);

	@Autowired
	public ScoreController(ScoreDao score) {
		this.scoreDao = score;
	}

	@PostMapping("/api/score/store")
	public String storeScore(@RequestBody Score score) {
		if (score.getUsername() != null && score.getValue() != null) {
			logger.info("Inside score");
			scoreDao.save(score);
			logger.info("Score success stored");
			return "success";
		}
		return "fail";
	}
}
