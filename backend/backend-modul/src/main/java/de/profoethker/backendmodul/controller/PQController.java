package de.profoethker.backendmodul.controller;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import de.profoethker.backendmodul.dao.PQDao;
import de.profoethker.backendmodul.model.CheckPA;
import de.profoethker.backendmodul.model.PQ;

@RestController
public class PQController {

	private final PQDao pqDao;

	Logger logger = LoggerFactory.getLogger(PQController.class);

	@Autowired
	public PQController(PQDao pq) {
		this.pqDao = pq;
	}

	@RequestMapping(value = "/api/p/generate", produces = "application/json")
	@CrossOrigin
	public String generateRandomPersonalQuestion() {
		logger.info("Fetching data and generating random personal question");
		List<PQ> pqList = pqDao.findAll();

		if (!pqList.isEmpty()) {
			Gson gson = new Gson();
			PQ randomPQ = getRandomPQ(pqList);
			PQ pq = new PQ();
			pq.setId(randomPQ.getId());
			pq.setQuestion(randomPQ.getQuestion());
			pq.setAnswer1(randomPQ.getAnswer1());
			pq.setAnswer2(randomPQ.getAnswer2());
			pq.setAnswer3(randomPQ.getAnswer3());
			pq.setAnswer4(randomPQ.getAnswer4());

			String json = gson.toJson(pq);
			logger.info(json);
			return json;
		} else {
			return null;
		}
	}

	@PostMapping("/api/p/store")
	@CrossOrigin
	public String storePQ(@RequestBody CheckPA checkPA) {
		if (checkPA.getPersonaSelectionID() != null && checkPA.getQuestionID() != null) {

			PQ toStore = pqDao.getOne(checkPA.getQuestionID());
			toStore.setPersona(checkPA.getPersonaSelectionID());
			pqDao.save(toStore);
			logger.info("Success created!");
			return "true";
		} else {
			return null;
		}

	}

	public PQ getRandomPQ(List<PQ> all) {
		Random r = new Random();
		PQ randomPQ = all.get(r.nextInt(all.size()));
		return randomPQ;
	}

}
