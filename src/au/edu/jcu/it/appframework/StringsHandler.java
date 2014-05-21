package au.edu.jcu.it.appframework;

public class StringsHandler {
	
	public static String convertEmail(String email){
		
		String namePortion = email.split("@")[0].replaceAll("\\d","");

		String firstName = namePortion.split("\\.")[0];
		String lastName  = namePortion.split("\\.")[1];
		
		firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
		lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);

		return firstName + " " + lastName;
	}
}