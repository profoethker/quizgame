package de.profoethker.backendmodul.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import de.profoethker.backendmodul.dao.QADao;
import de.profoethker.backendmodul.model.QA;

@RestController
public class QAController {

	private final QADao qaDao;

	@Autowired
	public QAController(QADao qa) {
		System.out.println("Constructor");
		this.qaDao = qa;
	}
	// @Autowired
	// public QADao qaDao;

	@GetMapping("/api/qa")
	@CrossOrigin
	public String getQuestionAndAnswer() {
		System.out.println("Inside qa");
		List<QA> qaList = qaDao.findAll();

		if (!qaList.isEmpty()) {
			QA randomQa = getRandomQa(qaList);
			System.out.println(randomQa.getQuestion());
		} else {
			System.out.println("No data in DB!");
		}

		return "Etwas";
	}

	public QA getRandomQa(List<QA> all) {
		Random r = new Random();
		QA randomQa = all.get(r.nextInt(all.size()));
		return randomQa;
	}

	@RequestMapping(value = "/api/randomQuestion", produces = "application/json")
	@CrossOrigin
	public String getRandomQuestionAndAnswer() {
		System.out.println("Inside getRandomQuestion");
		Gson gson = new Gson();
		List<QA> qaList = qaDao.findAll();
		if (!qaList.isEmpty()) {
			QA randomQa = getRandomQa(qaList);

			QA qa = new QA();
			qa.setId(randomQa.getId());
			qa.setAnswer1(randomQa.getAnswer1());
			qa.setAnswer2(randomQa.getAnswer2());
			qa.setAnswer3(randomQa.getAnswer3());
			qa.setAnswer4(randomQa.getAnswer4());
			qa.setQuestion(randomQa.getQuestion());
			String json = gson.toJson(qa);

			return json;
		} else {
			return null;
		}
	}

	/*
	 * 
	 * 
	 * @RequestMapping(value = "/api/randomQuestion", produces = "application/json")
	 * 
	 * @CrossOrigin public String getRandomQuestionAndAnswer() {
	 * System.out.println("Inside getRandomQuestion"); Gson gson = new Gson();
	 * 
	 * QA qa = new QA(); qa.setId(1); qa.setAnswer1("eine Katze");
	 * qa.setAnswer2("Pikachu"); qa.setAnswer3("Hund"); qa.setAnswer4("Eich");
	 * qa.setQuestion("Wer bin ich?"); String json = gson.toJson(qa);
	 * 
	 * return json; }
	 */

	@PostMapping("/api/sendAnswer")
	@CrossOrigin
	public String sendCorrect(@RequestBody String qa) {
		System.out.println("Inside getRandomQuestion");
		System.out.println(qa);
		// List<QA> qaList = qaDao.findall();
		/*
		 * if (!qaList.isEmpty()) { for (QA value : qaList) { System.out.println(value);
		 * } } else { System.out.println("No data in DB!"); }
		 */
		return "1";
	}

}
