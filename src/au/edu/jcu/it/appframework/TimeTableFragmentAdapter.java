package au.edu.jcu.it.appframework;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

public class TimeTableFragmentAdapter extends FragmentStatePagerAdapter  {
	private List<Fragment> fragments;

    public TimeTableFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }
 
    @Override
    public int getCount() {
        return this.fragments.size();
    }
    
    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }
    
    public void RemoveCard(int cardNumber){
    	fragments.remove(cardNumber);
    }
}