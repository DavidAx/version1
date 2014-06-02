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

		if(i == fragmentList.size()-1){
			return new HelpFragment();
		}
		
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


	        if(position== fragmentList.size()-1){

	        	return"Help";
	        }
	        TabFragment frag = fragmentList.get(position);
	    	return frag.title;
	    }
}
