package se.mah.kd330a.project.itsl;

import se.mah.kd330a.project.R;
import android.os.Bundle;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class FragmentFeatureStart extends Fragment {
	private ViewGroup rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		
			rootView = (ViewGroup) inflater.inflate(R.layout.itsl_feeds_start, container, false);
		
			
		return rootView;
	}
}
