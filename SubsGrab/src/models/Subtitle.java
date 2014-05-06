package models;

public class Subtitle {
	private String name;
	private String url;

	public Subtitle(String name, String url) {
		this.name = name;
		this.url = url;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}