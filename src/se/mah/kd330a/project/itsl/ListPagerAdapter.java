package se.mah.kd330a.project.itsl;

import java.util.ArrayList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class ListPagerAdapter extends FragmentStatePagerAdapter
{
	private static final String TAG = "ListPagerAdapter";
	private ArrayList<TabFragment> fragmentList;

	public ListPagerAdapter(FragmentManager fm, ArrayList<TabFragment> fragments)
	{
		super(fm);
		fragmentList = fragments;
	}

	@Override
	public Fragment getItem(int i)
	{
		if(i==0){
			return new FragmentFeatureStart();
		}
		if(i == 1){
			return new HelpFragment();
		}

//		if (fragmentList.isEmpty() || fragmentList.size() < i)
//		{
//			Log.e(TAG, "Index out of bounds: " + Integer.toString(i));
//			return null;
//		}
		else
		{
			return fragmentList.get(i);
		}
	}
		
	

	@Override
	public int getCount()
	{
		return fragmentList.size();
	}
	
	    @Override
	    public CharSequence getPageTitle(int position) {
	        if(position==0){
	        	return"Start";
	        }
	        if(position==1){
	        	return"help";
	        }
	    	return "OBJECT " + (position + 1);
	    }
}
