
package au.edu.jcu.it.appframework;

import java.util.ArrayList;
import java.util.Map;

import android.graphics.Color;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import au.edu.jcu.it.appframework.model.DaysEnum;
import au.edu.jcu.it.appframework.model.LecturerInfoDB;
import au.edu.jcu.it.appframework.model.StudentInfoDB;

public class FragmentGenerator {

	View timeTableView;
	int dayOfTheWeek;
	StudentInfoDB studentDatabase;
	LecturerInfoDB lecturerDatabase;
	@SuppressWarnings("rawtypes")
	ArrayList<Map> classesForTheDay;
	String loginType, email; 

	public FragmentGenerator(View timeTableView, int dayOfTheWeek, String loginType) {
		this.timeTableView = timeTableView;
		this.dayOfTheWeek = dayOfTheWeek;
		this.loginType = loginType;

		
		if(loginType == "Student"){
			System.out.println("Student Activity");
			studentDatabase = new StudentInfoDB(timeTableView.getContext());
			studentDatabase.open();
			classesForTheDay = studentDatabase.getEnrolledClasses(dayOfTheWeek);
			studentDatabase.close();
			polulateStudentView();
		}else{
			System.out.println("LECTURER LOGIN");
			lecturerDatabase = new LecturerInfoDB(timeTableView.getContext());
			lecturerDatabase.open();
			classesForTheDay = lecturerDatabase.getRoster(dayOfTheWeek);
			lecturerDatabase.close();
			polulateStudentView();
		}
	}

	private void polulateStudentView() {
		TextView dayDisplay = (TextView) timeTableView
				.findViewById(R.id.dayDisplay);
		dayDisplay.setText(DaysEnum.values()[dayOfTheWeek].toString());
		for (@SuppressWarnings("rawtypes") Map eachClass : classesForTheDay) {
			System.out.println(eachClass);
			int startTime = Integer.parseInt(eachClass.get("Raw_Start").toString().split(":")[0]);
			int endTime = Integer.parseInt(eachClass.get("Raw_End").toString().split(":")[0]);
			int duration = endTime - startTime;
		
			//Set Text For Subject Display
			int displayTextID = timeTableView.getResources().getIdentifier(
					"_" + startTime + "Text","id", timeTableView.getContext().getPackageName());
			
			TextView eachTime = (TextView) timeTableView.findViewById(displayTextID);
			System.out.println(eachTime.getId());
			eachTime.setText("DDD");
			eachTime.setText( 
			eachClass.get("Category").toString() + " " + eachClass.get("Lecture_Room").toString());
			
			//Set Color Coding For Subject
			for(int i = 0; i < duration+1 ; i++){
				int tableRowID = timeTableView.getResources().getIdentifier(
						"_" + Integer.toString(startTime + i),"id", timeTableView.getContext().getPackageName());
				TableRow eachTableRow = (TableRow) timeTableView.findViewById(tableRowID);
				
				if(loginType == "Student"){
					eachTableRow.setBackgroundColor(Color.parseColor(eachClass.get("Colour").toString()));
				}else{
					eachTableRow.setBackgroundColor(Color.parseColor("GREEN"));
				}
			}
		}
	}
	
	public View getViewPopulated() {
		return timeTableView;
	}
}