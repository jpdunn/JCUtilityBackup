package au.edu.jcu.it.appframework.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class StudentDBPopulator {

	Context context;
	String subjectCodeString, subjectNameString, xmlRefString, subjectIDString, 
	subjectStartTimeString, subjectEndTimeString, subjectDayString, subjectCategoryString,
	subjectRoomString, rawStartString, rawEndString, repeatString;

	public StudentDBPopulator(Context context) {
		this.context = context;
	}

	public void addSubjects(String subjectsStringJSON) {

		StudentInfoDB subjects = new StudentInfoDB(context);

		try {
			JSONArray array = new JSONArray(subjectsStringJSON);
			System.out.println(array.length());
			subjects.open();
			for (int i = 0; i < array.length() - 1; i++) {
				JSONObject subjectCode = array.getJSONObject(i);
				subjectCodeString = subjectCode.getString("Subject_Code");

				subjectNameString = subjectCode.getString("Subject_Name");

				JSONObject xmlRef = array.getJSONObject(i);
				xmlRefString = xmlRef.getString("XML_Reference");

				subjects.createEntrySubjects(subjectCodeString,
						subjectNameString);

			}
			subjects.close();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public void addClasses(String classesStringJSON) {

		StudentInfoDB subjects = new StudentInfoDB(context);

		try {
			JSONArray classesArray = new JSONArray(classesStringJSON);
			System.out.println(classesArray.length());
			subjects.open();
			for (int i = 0; i < classesArray.length() - 1; i++) {
				JSONObject subjectCode = classesArray.getJSONObject(i);
				subjectIDString = subjectCode.getString("Subject_ID");
				subjectStartTimeString = subjectCode.getString("Start_Time");
				subjectEndTimeString = subjectCode.getString("End_Time");
				subjectDayString = subjectCode.getString("Day");
				subjectCategoryString = subjectCode.getString("Category");
				subjectRoomString = subjectCode.getString("Lecture_Room");
				rawStartString = subjectCode.getString("Raw_Start");
				rawEndString = subjectCode.getString("Raw_End");
				repeatString = subjectCode.getString("Repeat");
				
				
				subjects.createEntryClasses(subjectIDString, subjectStartTimeString,
						subjectEndTimeString, subjectDayString, subjectCategoryString, 
						subjectRoomString, rawStartString, rawEndString, repeatString);
			

			}
			subjects.close();

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	
}
