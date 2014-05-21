package au.edu.jcu.it.appframework;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import au.edu.jcu.it.appframework.model.DaysEnum;
import au.edu.jcu.it.appframework.model.LoginType;

public class StudentTimeTableActivity extends FragmentActivity {
	
	TimeTableFragmentAdapter pageAdapter;
	List<Fragment> fList;
	int USERID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_table);
		
		fList = new ArrayList<Fragment>();
		
		for(int eachDay = 0; eachDay < DaysEnum.values().length; eachDay ++){
			fList.add(TimeTableFragment.newInstance(Integer.toString(eachDay), LoginType.Student));
		}
		
		final List<Fragment> fragments = fList;

		pageAdapter = new TimeTableFragmentAdapter(getSupportFragmentManager(),
				fragments);

		final ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setAdapter(pageAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.time_table, menu);
		return true;
	}
}