package se.mah.kd330a.project.itsl;

import se.mah.kd330a.project.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.kobjects.util.Strings;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.FragmentTransaction;

public class FragmentITSL extends Fragment implements 
	FeedManager.FeedManagerDoneListener, 
	ActionBar.TabListener
{	
	private ArrayList<String> titles;
	private ArrayList<String> categories;
	private static final String TAG = "FragmentITSL";
	private static final long UPDATE_INTERVAL = 600000; // every ten minute
	private static final long INITIAL_START_AFTER = 1000; // one minute
	//private static final long UPDATE_INTERVAL = 60000; // every minute for testing
	private FeedManager feedManager;
	private ProgressDialog dialog;
	private PendingIntent backgroundUpdateIntent;
	private ViewPager mViewPager;
	private PagerTabStrip pagerTabStrip;
	private ListPagerAdapter listPagerAdapter;
	private ViewGroup rootView;
	

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		titles = new ArrayList<String>();
		categories = new ArrayList<String>();
		
		/*
		 * Set up the repeating task of updating data in the background 
		 */
		Context appContext = getActivity().getApplicationContext();
		backgroundUpdateIntent = PendingIntent.getService(appContext, 0, new Intent(appContext, TimeAlarm.class), 0);
		feedManager = new FeedManager(this, appContext);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		if (feedManager.queueSize() > 0)
		{
			rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_itsl, container, false);
			
			/*
			 *  In case there is nothing in the cache, or it doesn't exist
			 *  we have to refresh
			 */
			if (!feedManager.loadCache()){
				refresh();
			}
		}
		else
		{
			/*
			 * no feeds registered, show help screen
			 */
			rootView = (ViewGroup) inflater.inflate(R.layout.itsl_help, container, false);
		}
		
			
		return rootView;
	}

	public void onResume()
	{
		super.onResume();
		Log.i(TAG, "Resumed: Stopping background updates");
		AlarmManager alarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
		alarm.cancel(backgroundUpdateIntent);
		
	
	}
	
	public void onPause()
	{
		super.onPause();
		Log.i(TAG, "Paused: Setting up background updates");
		AlarmManager alarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
		//WHY NOT DIRECTLY in OnCreate
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + INITIAL_START_AFTER, UPDATE_INTERVAL, backgroundUpdateIntent);
		
		/*
		 * Removes tabs and everything associated with it.
		 */
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

		/*
		 * Remember when we last had this view opened 
		 */
		Date date = new Date(System.currentTimeMillis());

		Util.setLatestUpdate(getActivity().getApplicationContext(), date);
	}

	public class FeedObject
	{
		public ArrayList<Article> articles;

		public FeedObject()
		{
			articles = new ArrayList<Article>();
		}
	}

	public HashMap<String, FeedObject> getFeedObjects()
	{
		HashMap<String, FeedObject> foList = new HashMap<String, FeedObject>();

		for (Article a : feedManager.getArticles())
		{
			FeedObject fo;

			if (foList.containsKey(a.getArticleCourseCode()))
			{
				fo = foList.get(a.getArticleCourseCode());
			}
			else
			{
				fo = new FeedObject();
				foList.put(a.getArticleCourseCode(), fo);
			}

			fo.articles.add(a);
		}

		return foList;
	}

	/**
	 * Creates tabs in the actionbar and the fragments associated with them.
	 * 
	 * @return ArrayList of fragments
	 */
	private ArrayList<TabFragment> createFragments()
	{
		ArrayList<TabFragment> fragments = new ArrayList<TabFragment>();
		HashMap<String, FeedObject> foList = getFeedObjects();
		

		/*
		 * For all feeds we have downloaded, create a new tab and add the 
		 * corresponding data to a new TabFragment
		 */
		
		for (String category : foList.keySet())
		{
			String categoryName = "course name";
			try
			{
				String[] parts = category.split("-");
				categoryName = parts[2].substring(1, parts[2].length() - 1);
				categories.add(categoryName);
			}
			catch (Exception e)
			{
				categoryName = category;
				categories.add(categoryName);
			}
		}
		

		
		
		
		TabFragment fragment;

		String ShortTitle ="";
		String[] words = null;
		for (String title : foList.keySet())
		{
			String titleDisp = "course name";
			
			try
			{
				//Here we split up the coursenames to shorter names.
				String[] parts = title.split("-");
				titleDisp = parts[2].substring(1, parts[2].length() - 1);
				for(int i = 0;i<titleDisp.length();i++){
					words = titleDisp.split(" ");
				}
				for(int j = 0;j<words.length;j++){
					char letter = words[j].charAt(0);
					ShortTitle = ShortTitle+letter;
					for(int k = 0;k<words[j].length();k++){
						if(words[j].charAt(k) == ':'){
							ShortTitle = ShortTitle+":";
						}
					}
				}
				titleDisp = ShortTitle.toUpperCase();
				titles.add(ShortTitle);
				ShortTitle="";
			}
			catch (Exception e)
			{
				titleDisp = title;
				titles.add(title);
				ShortTitle="";
			}
			//Add the short title to the tabs for the feeds
			fragment = new TabFragment();
			fragment.setArticles(foList.get(title).articles);
			fragment.setTitle(titleDisp);
			fragments.add(fragment);
			
			
			
			
			Log.i(TAG, "Filtered map key => tab title is: " + title);
			for (TabFragment frag : fragments) {
				Log.i(TAG,"fragmentName "+frag.title);
			}
			
		}
		//This code adds the tab for the helpscreen
		TabFragment add;
		add = new TabFragment();
		add.setTitle("Help");
		fragments.add(add);
		return fragments;
	}
	
	
	
	
	public void onFeedManagerProgress(FeedManager fm, int progress, int max)
	{
		/*
		 *  set up progress dialog if there isn't one.
		 */
		if (dialog == null)
		{
			dialog = new ProgressDialog(getActivity());
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setMessage("Downloading...");
			dialog.show();
		}

		dialog.setProgress(progress);
		dialog.setMax(max);
	}

	@Override
	public void onFeedManagerDone(FeedManager fm, ArrayList<Article> articles)
	{
		if (dialog != null)
		{
			dialog.dismiss();
			dialog = null;
		}
			
		mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
		mViewPager.setOnPageChangeListener(
	            new ViewPager.SimpleOnPageChangeListener() {
	                @Override
	                public void onPageSelected(int position) {

	                }
	            });

		// Here we edit the properties for the tabs. 
		listPagerAdapter = new ListPagerAdapter(getActivity().getSupportFragmentManager(), createFragments());
		mViewPager.setAdapter(listPagerAdapter);
		pagerTabStrip = (PagerTabStrip) rootView.findViewById(R.id.pager_tab_strip);
		pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.orange));
		pagerTabStrip.setTextSpacing(1);
		pagerTabStrip.setDrawFullUnderline(true);
		
		
		
	}

	private void refresh()
	{
		feedManager.reset();
		feedManager.processFeeds();
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft)
	{
		/*
		 *  here we retrieve the tabfragment object that should already have 
		 *  been initialized and added to the adapter
		 */
		
		
		if (mViewPager != null)
			mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft)
	{
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft)
	{
	}
}
