package models;

public class Show {
	private String title;
	private String reference;
	private String directory;

	public Show(String title, String reference, String directory) {
		this.title = title;
		this.reference = reference;
		this.directory = directory;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}
}