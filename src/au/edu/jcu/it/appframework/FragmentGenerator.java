package au.edu.jcu.it.appframework;

import android.view.View;
import android.widget.TextView;
import au.edu.jcu.it.appframework.model.DaysEnum;

public class FragmentGenerator {
	
	View timeTableView;
	int dayOfTheWeek;
	
	public FragmentGenerator(View timeTableView, int dayOfTheWeek){
		this.timeTableView = timeTableView;
		this.dayOfTheWeek = dayOfTheWeek;
		populateView();
	}
	
	private void populateView(){
		TextView dayDisplay = (TextView) timeTableView.findViewById(R.id.dayDisplay);
		dayDisplay.setText(DaysEnum.values()[dayOfTheWeek].toString());
	}
	
	public View getViewPopulated(){
		return timeTableView;
	}
}