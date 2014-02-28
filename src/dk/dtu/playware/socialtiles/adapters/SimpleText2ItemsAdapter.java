package dk.dtu.playware.socialtiles.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * SimpleText2ItemsAdapter shows two simple textes side by side
 */
public class SimpleText2ItemsAdapter extends BaseAdapter {
	
	/** The items. */
	ArrayList <HashMap<String , String>> items = new ArrayList<HashMap<String,String>>();

	/** The m atc fragment activity. */
	private FragmentActivity mAtcFragmentActivity;

	/**
	 * The Class ViewHolder.
	 */
	static class ViewHolder{
		
		/** The text view1. */
		TextView textView1 ;
		
		/** The text view2. */
		TextView textView2 ;
	}
	
	/**
	 * Instantiates a new simple text2 items adapter.
	 *
	 * @param mAcFragmentActivity the m ac fragment activity
	 * @param data the data
	 */
	public SimpleText2ItemsAdapter(FragmentActivity mAcFragmentActivity,
			ArrayList<HashMap<String, String>> data) {
		// TODO Auto-generated constructor stub
		this.items = data;
		this.mAtcFragmentActivity = mAcFragmentActivity;
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
		
		ViewHolder holder;
		if (convertView == null) {
			convertView = mAtcFragmentActivity.getLayoutInflater().inflate(R.layout.list_item_2_items, null);
			holder = new ViewHolder();
			holder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
			holder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Log.d(Tags.debugTag, "item is "+ items.get(position));
		Log.d(Tags.debugTag, "item 1 is "+items.get(position).get(Tags.item1) +" and item 2 is "+ items.get(position).get(Tags.item2));
		holder.textView1.setText(items.get(position).get(Tags.item1).toString());
		holder.textView2.setText(items.get(position).get(Tags.item2).toString());
		
		
		return convertView;
	}
}
