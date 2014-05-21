package se.mah.kd330a.project.itsl;

import java.util.ArrayList;

import se.mah.kd330a.project.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class TabFragment extends Fragment implements OnChildClickListener
{
	private static final String TAG = "TabFragment";
	private ExpandableListAdapter listAdapter;
	private ListAdapter startListAdapter;
	private ExpandableListView expListView;
	private ListView listView;
	private ArrayList<Article> articleList;
	private ArrayList<String> categories;
	public String title = "rubrik";

	public TabFragment()
	{
		super();
		articleList = new ArrayList<Article>();
	}
	
	public void setArticles(ArrayList<Article> articles)
	{
		articleList = articles;
	}
	
	public void setTitles(ArrayList<String> titles)
	{
		categories = titles;
	}
	
	public void setTitle(String s){
		title = s;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView;
		if(title.equals("Start")){
			Log.i("Hej", "hejhejhej");
			setRetainInstance(true);
			rootView = inflater.inflate(R.layout.itsl_feeds_start, container, false);
			startListAdapter = (ListAdapter) new newListAdapter(getActivity(), categories);
			listView = (ListView) rootView.findViewById(R.id.startList);
			listView.setAdapter(startListAdapter);
			listView.setOnClickListener((OnClickListener) this);
		}
		else{
			// don't destroy fragment when orientation changes
			setRetainInstance(true);
			rootView = inflater.inflate(R.layout.itsl_fragment_main, container, false);
			listAdapter = new ExpandableListAdapter(getActivity(), articleList);
			expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);
			expListView.setAdapter(listAdapter);
			listAdapter.notifyDataSetChanged();
			expListView.setOnChildClickListener(this);
		}
		return rootView;
	}

	public void refresh()
	{
		/*
		 * Close all expanded childviews, otherwise they will incorrectly 
		 * linger in the UI even after we invalidate the dataset.
		 */
		int count = listAdapter.getGroupCount();
		for (int i = 0; i < count; i++)
			expListView.collapseGroup(i);

		listAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
	{
		return parent.collapseGroup(groupPosition);
	}
}
