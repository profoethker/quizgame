package de.profoethker.backendmodul.model;

public class Tip {
	private String type;
	private String tip;
	private Integer wrong1;
	private Integer wrong2;
	private Integer correct;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public Integer getWrong1() {
		return wrong1;
	}

	public void setWrong1(Integer wrong1) {
		this.wrong1 = wrong1;
	}

	public Integer getWrong2() {
		return wrong2;
	}

	public void setWrong2(Integer wrong2) {
		this.wrong2 = wrong2;
	}

	public Integer getCorrect() {
		return correct;
	}

	public void setCorrect(Integer correct) {
		this.correct = correct;
	}
	

}
