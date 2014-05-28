package se.mah.kd330a.project.TipOfTheDay;

import java.lang.reflect.Field;
import java.util.ArrayList;

import se.mah.kd330a.project.R;
import se.mah.kd330a.project.itsl.Article;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

public class Fragment_Tipoftheday extends Fragment  {

	public static ViewPager viewPager;
	static Activity context;
	PagerTabStrip pagerTabStrip;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  
		context = getActivity();
		super.onCreate(savedInstanceState);

	}

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
    		View v = inflater.inflate(R.layout.fragment_screen_tipoftheday, container, false);
    		viewPager = (ViewPager) v.findViewById(R.id.pager);
    		viewPager.setAdapter(buildAdapter());
    		try {
                Field mScroller;
                mScroller = ViewPager.class.getDeclaredField("mScroller");
                mScroller.setAccessible(true);
               
                
            } catch (Exception e) {
            }
    		pagerTabStrip = (PagerTabStrip) v.findViewById(R.id.pager_tab_strip);
    		pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.yellow));
    		pagerTabStrip.setDrawFullUnderline(true);
    	return v;
	}
    
    private PagerAdapter buildAdapter() {
        return(new CollectionPagerAdapter(getChildFragmentManager()));
      }

}
