package models;

public class Episode {
	private String show;
	private int season;
	private int number;

	public Episode(String show, int season, int number) {
		this.show = show;
		this.season = season;
		this.number = number;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}