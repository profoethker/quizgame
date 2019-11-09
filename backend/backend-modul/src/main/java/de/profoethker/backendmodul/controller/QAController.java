package de.profoethker.backendmodul.controller;


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
		// List<QA> qaList = qaDao.findall();
		/*
		 * if (!qaList.isEmpty()) { for (QA value : qaList) { System.out.println(value);
		 * } } else { System.out.println("No data in DB!"); }
		 */
		return "Etwas";
	}

	@RequestMapping(value = "/api/randomQuestion", produces="application/json")
	@CrossOrigin
	public String getRandomQuestionAndAnswer() {
		System.out.println("Inside getRandomQuestion");
		Gson gson = new Gson();

		QA qa = new QA();
		qa.setId(1);
		qa.setAnswer1("eine Katze");
		qa.setAnswer2("Pikachu");
		qa.setAnswer3("Hund");
		qa.setAnswer4("Eich");
		qa.setQuestion("Wer bin ich?");
		String json = gson.toJson(qa);

		return json;
	}

	@PostMapping("/api/sendAnswer")
	public String sendCorrect(@RequestBody QA qa) {
		System.out.println("Inside getRandomQuestion");
		// List<QA> qaList = qaDao.findall();
		/*
		 * if (!qaList.isEmpty()) { for (QA value : qaList) { System.out.println(value);
		 * } } else { System.out.println("No data in DB!"); }
		 */
		return "1";
	}

}
