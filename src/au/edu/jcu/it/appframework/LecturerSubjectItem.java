package au.edu.jcu.it.appframework;

public class LecturerSubjectItem {

	private String title;
	private String description;

	public LecturerSubjectItem(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}

	public CharSequence getTitle() {
		return title;
	}

	public CharSequence getDescription() {
		return description;
	}

}
