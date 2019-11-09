package de.profoethker.backendmodul.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import de.profoethker.backendmodul.dao.QADao;
import de.profoethker.backendmodul.model.CheckQA;
import de.profoethker.backendmodul.model.QA;
import de.profoethker.backendmodul.model.Tip;
import de.profoethker.backendmodul.rest.RestfullController;

@RestController
public class QAController {

	private final QADao qaDao;

	Logger logger = LoggerFactory.getLogger(RestfullController.class);

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
		logger.info("Inside qa");
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

	@PostMapping("/api/sendAnswer")
	@CrossOrigin
	public String sendCorrect(@RequestBody CheckQA check) {
		System.out.println("Inside getRandomQuestion");
		System.out.println(check.getAnswerID());

		Optional<QA> qaList = qaDao.findById(check.getQuestionID());

		Gson gson = new Gson();
		QA qa = new QA();
		if (qaList.isPresent()) {
			if (qaList.get().getCorrect() == check.getAnswerID()) {
				System.out.println("Correct");
				qa.setInfo(qaList.get().getInfo());
				qa.setCorrect(check.getAnswerID());
				String json = gson.toJson(qa);
				return json;
			}
		}
		qa.setInfo(qaList.get().getInfo());
		qa.setCorrect(qaList.get().getCorrect());
		String json = gson.toJson(qa);
		return json;
	}

	@PostMapping("/api/tip")
	@CrossOrigin
	public String generateTip(@RequestBody String answerID) {

		System.out.println(answerID);
		JsonObject convertedObject = new Gson().fromJson(answerID, JsonObject.class);

		Random random = new Random();

		Optional<QA> fetchQa = qaDao.findById(Integer.valueOf(convertedObject.get("currentQuestionId").getAsString()));
		List<String> answers = new ArrayList<>();
		answers.add(fetchQa.get().getAnswer1());
		answers.add(fetchQa.get().getAnswer2());
		answers.add(fetchQa.get().getAnswer3());
		answers.add(fetchQa.get().getAnswer4());

		List<Integer> filteredList = new ArrayList<>();

		Tip tip = new Tip();
		Gson gson = new Gson();

		if (fetchQa.isPresent()) {
			float chance = random.nextFloat();
			// 10%
			if (chance <= 0.10f) {
				logger.info("50/50");
				// Correct answer for 50%
				// filteredList.add(fetchQa.get().getCorrect());

				List<Integer> wrongAnswers = new ArrayList<>();
				for (int i = 1; i <= 4; i++) {
					if (i != fetchQa.get().getCorrect()) {
						wrongAnswers.add(i);
					}
				}
				System.out.println("Wrong ANswers!");
				for (Integer a : wrongAnswers) {
					System.out.println(a);
				}

				System.out.println("Filered");
				for (Integer b : filteredList) {
					System.out.println(b);
				}

				tip.setWrong1(wrongAnswers.get(0));
				tip.setWrong2(wrongAnswers.get(random.nextInt(2) + 1));

				tip.setType("50/50");
				String json = gson.toJson(tip);
				return json;
				// Random asnwer for 50% from the rest answers
				// filteredList.add()
				// 20%
			} else if (chance <= 0.20f) {
				logger.info("Skip");
				tip.setType("skip");
				tip.setCorrect(fetchQa.get().getCorrect());
				String json = gson.toJson(tip);
				return json;
			}
			// 70 %
			else if (chance <= 1.0f) {
				logger.info("tip");
				tip.setType("tip");
				tip.setTip(fetchQa.get().getHint());
				String json = gson.toJson(tip);
				return json;
			}
			// "50/50"- >Json ZWEI % 20
			// Tip -> STRING % 70
			// SkipQuestion "skipt" % 10
		}
		return null;

	}

	public boolean isCorrectAnswer(Integer answerID, Integer answerNum) {
		Optional<QA> fetchQa = qaDao.findById(answerID);
		if (fetchQa.isPresent()) {
			return fetchQa.get().getCorrect() == answerNum;
		}
		return false;
	}

}
