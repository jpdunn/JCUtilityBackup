package au.edu.jcu.it.appframework.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class LecturerDBPopulator {

	Context context;
	String rosterCodeString, rosterNameString, xmlRefString, rosterIDString,
			rosterStartTimeString, rosterEndTimeString, rosterDayString,
			rosterCategoryString, rosterRoomString, rawStartString,
			rawEndString, repeatString;

	public LecturerDBPopulator(Context context) {
		this.context = context;
	}

	public void addClasses(String classesStringJSON) {

		LecturerInfoDB rosters = new LecturerInfoDB(context);

		try {
			JSONArray classesArray = new JSONArray(classesStringJSON);
			rosters.open();
			for (int i = 0; i < classesArray.length() - 1; i++) {
				JSONObject rosterCode = classesArray.getJSONObject(i);
				rosterIDString = rosterCode.getString("Subject_Code");
				rosterStartTimeString = rosterCode.getString("Start_Time");
				rosterEndTimeString = rosterCode.getString("End_Time");
				rosterDayString = rosterCode.getString("Day");
				rosterCategoryString = rosterCode.getString("Category");
				rosterRoomString = rosterCode.getString("Lecture_Room");
				rawStartString = rosterCode.getString("Raw_Start");
				rawEndString = rosterCode.getString("Raw_End");

				rosters.createEntryRoster(rosterIDString,
						rosterStartTimeString, rosterEndTimeString,
						rosterDayString, rosterCategoryString,
						rosterRoomString, rawStartString, rawEndString);
			}
			rosters.close();

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
