package se.mah.kd330a.project.TipOfTheDay;

import se.mah.kd330a.project.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentEventTip extends Fragment{

	private ViewGroup rootView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		
			rootView = (ViewGroup) inflater.inflate(R.layout.tip_of_the_day_test, container, false);
		
			
		return rootView;
	}
}

