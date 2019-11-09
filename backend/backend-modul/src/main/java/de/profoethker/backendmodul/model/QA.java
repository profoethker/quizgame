package de.profoethker.backendmodul.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "qa")
@SequenceGenerator(name = "hibernate_sequence")
public class QA {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate_sequence")
	private int id;

	private String question;
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private Integer correct;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public String getAnswer4() {
		return answer4;
	}

	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	public Integer getCorrect() {
		return correct;
	}

	public void setCorrect(Integer correct) {
		this.correct = correct;
	}

}
