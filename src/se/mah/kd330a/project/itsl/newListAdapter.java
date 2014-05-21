package se.mah.kd330a.project.itsl;

import se.mah.kd330a.project.R;
import se.mah.kd330a.project.adladok.model.Course;
import se.mah.kd330a.project.adladok.model.Me;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class newListAdapter extends BaseExpandableListAdapter
{
	private Context _context;
	private List<String> _listDataHeader; // header titles
	private Date lastUpdate;
	private final String TAG = "ExpandableListAdapter";
	//HashMap<String, Integer> colors = new HashMap<String, Integer>();
	

	public newListAdapter(Context context, List<String> listDataHeader)
	{
		this._context = context;
		this._listDataHeader = listDataHeader;
		this.lastUpdate = Util.getLatestUpdate(_context);
			

	}


	public List<String> getList()
	{
		return _listDataHeader;
	}

	public void setList(List<String> list)
	{
		_listDataHeader = list;
		notifyDataSetInvalidated();
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon)
	{
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return childPosition;
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount()
	{
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{
		if (!_listDataHeader.isEmpty())
		{
			if (groupPosition == 0)
			{
				LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.itsl_list_group, null);
			}
			
			else {
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.itsl_list_group_shadow, null);
			}

			Article headerTitle = (Article) getGroup(groupPosition);
			TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
			TextView lblListHeaderDate = (TextView) convertView.findViewById(R.id.lblListHeaderDate);
			TextView lblListHeaderText = (TextView) convertView.findViewById(R.id.lblListHeaderText);
			TextView lblListCode = (TextView) convertView.findViewById(R.id.lblListCode);
			ImageView imgClrCode = (ImageView) convertView.findViewById(R.id.clrCode);
			ImageView imgPointer = (ImageView) convertView.findViewById(R.id.icPointer);
			TextView txtClrLine = (TextView) convertView.findViewById(R.id.clrLine);

			lblListHeader.setText(headerTitle.getArticleHeader());
			lblListHeaderDate.setText(headerTitle.getArticleDate().toString());
			
			/*
			 * find out if this article is new since last time we started the app
			 */
			if (lastUpdate.getTime() < headerTitle.getArticlePubDate().getTime())
				lblListCode.setText("New");
			else
				lblListCode.setText("");
				
			/*
			 * If the summary text is visible = not expanded
			 */
			if (headerTitle.isTextVisible())
			{

				lblListHeaderText.setVisibility(View.VISIBLE);
				lblListHeaderText.setText(headerTitle.getArticleSummary());
				imgPointer.setVisibility(View.VISIBLE);
			}
			else
			{
				lblListHeaderText.setVisibility(View.GONE);
				imgPointer.setVisibility(View.GONE);

			}

			if (headerTitle.getArticleSummary().equals(headerTitle.getArticleText()))
			{
				convertView.setClickable(true);
				imgPointer.setVisibility(View.GONE);
			}
			else
			{
				convertView.setClickable(false);
			}
		}
		return convertView;

	}

	@Override
	public boolean hasStableIds()
	{
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return true;
	}

	@Override
	public void onGroupCollapsed(int groupPosition)
	{
		super.onGroupCollapsed(groupPosition);

		if (!_listDataHeader.isEmpty())
		{
			Article headerTitle = (Article) getGroup(groupPosition);
			headerTitle.setTextVisible(true);
		}
	}

	@Override
	public void onGroupExpanded(int groupPosition)
	{
		super.onGroupExpanded(groupPosition);

		if (!_listDataHeader.isEmpty())
		{
			Article headerTitle = (Article) getGroup(groupPosition);
			headerTitle.setTextVisible(false);
		}

	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		return 1;
	}
	
	private int getColor(Article a){
		int start = a.getArticleCourseCode().indexOf(" - ");
		Log.i(TAG,"CourseCode: "+  a.getArticleCourseCode());
		String courseName="";
		try{
			courseName = a.getArticleCourseCode().substring(start+3,a.getArticleCourseCode().length()-1);
		}catch  (Exception e){
			Log.i(TAG,e.getMessage());
		}
		Log.i(TAG,"CourseName:"+ courseName+":");
		int color = _context.getResources().getColor(R.color.red_mah);
		for (Course c : Me.getInstance().getCourses())
		{
			Log.i(TAG,"course: "+c.getDisplaynameSv()+ " :courseNAME: "+courseName);
			//This is not right if the name on ITSL is a pert of the coursename example "Programmering for interaktionsdesign och Interaktionsdesign"
			if (c.getDisplaynameSv().contains(courseName)||c.getDisplaynameEn().contains(courseName)){
				Log.i(TAG," Color: " + c.getColor()+ " course: "+c.getDisplaynameSv()+ " Artcode: "+a.getArticleCourseCode());
				color=c.getColor();
				break;
			}
		}
		return color;
	}


	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
