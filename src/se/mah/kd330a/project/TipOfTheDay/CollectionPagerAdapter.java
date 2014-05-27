package se.mah.kd330a.project.TipOfTheDay;

import se.mah.kd330a.project.itsl.FragmentFeatureStart;
import se.mah.kd330a.project.itsl.HelpFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class CollectionPagerAdapter extends FragmentStatePagerAdapter {
    
	Bundle args;

	public CollectionPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		if(i==0){
			return new FragmentFeatureStart();
		}
		else{
			return new HelpFragment();
		}
		
//		Fragment fragment = new ChildFragment();
//		args = new Bundle();
//		args.putInt("POSITION", i);
//		fragment.setArguments(args);
		//return Fragment;
	}

	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "OBJECT " + (position + 1);
	}
}