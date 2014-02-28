package dk.dtu.playware.socialtiles.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import dk.dtu.playware.socialtiles.adapters.UserAdapter.ViewHolder;
import dk.dtu.playware.socialtiles.tags.Tags;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleTextAdapter.
 */
public class SimpleTextAdapter extends BaseAdapter {
	
	/** The items. */
	ArrayList <HashMap<String , String>> items = new ArrayList<HashMap<String,String>>();

	/** The m atc fragment activity. */
	private FragmentActivity mAtcFragmentActivity;

	/** The tag. */
	private String tag;
	
	/**
	 * The Class ViewHolder.
	 */
	static class ViewHolder{
		
		/** The text view. */
		TextView textView ; 
	}


	/**
	 * Instantiates a new simple text adapter.
	 *
	 * @param mAcFragmentActivity the m ac fragment activity
	 * @param data the data
	 * @param tag the tag
	 */
	public SimpleTextAdapter(FragmentActivity mAcFragmentActivity,
			ArrayList<HashMap<String, String>> data, String tag) {
		// TODO Auto-generated constructor stub
		this.items = data;
		this.mAtcFragmentActivity = mAcFragmentActivity;
		this.tag = tag;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}


	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		TextView mTextView;
		if (convertView == null) {
			mTextView = new TextView(mAtcFragmentActivity);
			convertView = mTextView;
			

		}else {
			mTextView = (TextView) convertView;
		}

		mTextView.setText(items.get(position).get(tag));
		mTextView.setTextSize(20);
		mTextView.setTextAppearance(mAtcFragmentActivity, android.R.attr.textAppearanceMedium);
		
		return convertView;
	}

}
