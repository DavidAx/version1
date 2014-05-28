package se.mah.kd330a.project.TipOfTheDay;

import se.mah.kd330a.project.itsl.HelpFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class CollectionPagerAdapter extends FragmentStatePagerAdapter {
    
	

	public CollectionPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		if(i==0){
			return new FragmentEventTip();
			
		}
		if(i==1){
			return new FragmentEventFood();
		}
		if(i==2){
			return new FragmentEventEvents();
		}
		if(i==3){
			return new FragmentEventMah();
			
		}
		if(i== 4){
			return new FragmentEventPlaces();
		}
		else{
			return new HelpFragment();
		}
		
	}

	@Override
	public int getCount() {
		return 5;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if(position==0){
			return "All Tips";
		}
		if(position==1){
			return "Food & Drinks";
		}
		if(position==2){
			return "Events";
		}
		if(position==3){
			return "MAH";
		}
		if(position==4){
			return "Places";
		}
		else{
		return "OBJECT " + (position + 1);
		}
	}
}