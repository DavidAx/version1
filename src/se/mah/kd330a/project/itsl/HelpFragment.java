package se.mah.kd330a.project.itsl;

import se.mah.kd330a.project.R;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class HelpFragment extends Fragment {
	private ViewGroup rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		
			rootView = (ViewGroup) inflater.inflate(R.layout.itsl_help, container, false);
		
			
		return rootView;
	}
}
