package au.edu.jcu.it.appframework;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import au.edu.jcu.it.appframework.model.DaysEnum;

public class TimeTableFragment extends Fragment {
	public static final String DAY_NUMBER = "DAY_NUMBER";
	

	
	public static Fragment newInstance(String message) {
		TimeTableFragment f = new TimeTableFragment();
		Bundle bdl = new Bundle(1);
	    bdl.putString(DAY_NUMBER, message);
	    f.setArguments(bdl);
	    return f;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
		int dayNumber = Integer.parseInt(getArguments().getString(DAY_NUMBER));
		View v = inflater.inflate(R.layout.timetablefragment_layout, container, false);
		
		return populateFragment(v, dayNumber);
    }
	
	public View populateFragment(View unpopulatedView, int dayNumber){
		FragmentGenerator fragmentGenerator = new FragmentGenerator(unpopulatedView, dayNumber);
		return fragmentGenerator.getViewPopulated();
	}
	
}