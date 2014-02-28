package dk.dtu.playware.socialtiles.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class FeedsAdapter. adapter that is used for showing the posts. 
 * it shows the image and the name of the post
 * and the acutal text
 */
public class FeedsAdapter extends BaseAdapter {
	
	/** The m array list. */
	ArrayList<HashMap<String, String>> mArrayList;
	
	/** The m atc fragment activity. */
	FragmentActivity mAtcFragmentActivity;

	/**
	 * The Class ViewHolder.
	 */
	static class ViewHolder {
		
		/** The post. */
		TextView post;
		
		/** The user name. */
		TextView userName;
		
		/** The user image. */
		ImageView userImage;
	}

	/**
	 * Instantiates a new feeds adapter.
	 *
	 * @param mAcFragmentActivity the m ac fragment activity
	 * @param data the data
	 */
	public FeedsAdapter(FragmentActivity mAcFragmentActivity,
			ArrayList<HashMap<String, String>> data) {
		// TODO Auto-generated constructor stub
		this.mAtcFragmentActivity = mAcFragmentActivity;
		this.mArrayList = data;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayList.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mArrayList.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		/*	TextView mTextView;
		if (convertView == null) {
			mTextView = new TextView(mAtcFragmentActivity);

			mTextView.setLayoutParams(new ListView.LayoutParams(
					LayoutParams.FILL_PARENT, 60));

			mTextView.setTextSize(15);
			convertView = mTextView;
		} else {
			mTextView = (TextView) convertView;
		}
		mTextView.setText(mLisArrayList.get(position));
		return convertView;
	}*/
		ViewHolder holder;
		if(convertView == null){
			convertView = mAtcFragmentActivity.getLayoutInflater().inflate(R.layout.post_layout, null);	
			holder = new ViewHolder();
			holder.post = (TextView) convertView.findViewById(R.id.post_text);
			holder.userName = (TextView) convertView.findViewById(R.id.profile_name);
			holder.userImage = (ImageView) convertView.findViewById(R.id.profile_image);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.userName.setText(mArrayList.get(position).get(Tags.profileName));
		holder.post.setText(mArrayList.get(position).get(Tags.post_text));
		UrlImageViewHelper.setUrlDrawable(holder.userImage,mArrayList.get(position).get(Tags.profileImg), R.drawable.loading);
		return convertView;
	}
}
