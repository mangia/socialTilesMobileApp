package dk.dtu.playware.socialtiles.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * Simple SpinnerAdapter.
 */
public class SpinnerAdapter extends ArrayAdapter<HashMap<String, String>>  {

	/** The m ac fragment activity. */
	private FragmentActivity mAcFragmentActivity;
	
	/** The data. */
	private ArrayList<HashMap<String, String>> data =  new ArrayList<HashMap<String,String>>();

	/**
	 * Instantiates a new spinner adapter.
	 *
	 * @param mAcFragmentActivity the m ac fragment activity
	 * @param resource the resource
	 * @param data the data
	 */
	public SpinnerAdapter(FragmentActivity mAcFragmentActivity, int resource, ArrayList<HashMap<String, String>> data) {
		super(mAcFragmentActivity, resource, data);
		this.mAcFragmentActivity = mAcFragmentActivity;
		this.data = data;
		Log.d(Tags.debugTag,"data are "+data);
	}


	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = mAcFragmentActivity.getLayoutInflater().inflate(android.R.layout.simple_dropdown_item_1line, null);
		}

		Log.d(Tags.debugTag, "position is "+position+" and member is data.get(position) \n and profil name is "+data.get(position).get(Tags.profileName));
		TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
		textView.setText(data.get(position).get(Tags.profileName));

		return convertView;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = mAcFragmentActivity.getLayoutInflater().inflate(android.R.layout.simple_dropdown_item_1line, null);
		}

		Log.d(Tags.debugTag, "position is "+position+" and member is data.get(position) \n and profil name is "+data.get(position).get(Tags.profileName));
		TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
		textView.setText(data.get(position).get(Tags.profileName));

		return convertView;
	}



	

}
