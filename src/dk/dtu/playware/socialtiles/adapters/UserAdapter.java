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
 * Adabter that shows the user image name and score on the list.
 */
public class UserAdapter extends BaseAdapter  {
	
	/** The m array list. */
	ArrayList<HashMap<String,String>> mArrayList = new ArrayList<HashMap<String,String>>();
	
	/** The m atc fragment activity. */
	private FragmentActivity mAtcFragmentActivity;
	
	/** The show score. */
	private boolean showScore = false;

	/**
	 * The Class ViewHolder.
	 */
	static class ViewHolder {
		
		/** The user name. */
		TextView userName;
		
		/** The user image. */
		ImageView userImage;
		
		/** The user score. */
		TextView userScore;
	}

	/**
	 * Instantiates a new user adapter.
	 *
	 * @param mAcFragmentActivity the m ac fragment activity
	 * @param data the data
	 * @param showScore the show score
	 */
	public UserAdapter(FragmentActivity mAcFragmentActivity, ArrayList<HashMap<String,String>> data, boolean showScore) {
		// TODO Auto-generated constructor stub
		this.mAtcFragmentActivity = mAcFragmentActivity;
		this.mArrayList = data;
		this.showScore = showScore;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayList.size();
	}


	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return  mArrayList.get(position);
	}


	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder;
		if (convertView == null) {
			convertView = mAtcFragmentActivity.getLayoutInflater().inflate(R.layout.profileimg_profilename_item, null);
			holder = new ViewHolder();
			holder.userName = (TextView) convertView.findViewById(R.id.profile_name_id);
			holder.userImage = (ImageView) convertView.findViewById(R.id.profile_image_id);
			holder.userScore = (TextView) convertView.findViewById(R.id.profile_score_id);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.userName.setText(mArrayList.get(position).get(Tags.profileName) );
		UrlImageViewHelper.setUrlDrawable(holder.userImage,mArrayList.get(position).get(Tags.profileImg), R.drawable.loading);
		
		if(showScore ){
			holder.userScore.setVisibility(View.VISIBLE);
			//Log.d(Tags.debugTag,"entry is "+mArrayList.get(position) );
			holder.userScore.setText(mArrayList.get(position).get(Tags.userScore));
		}
		
		return convertView;
	}

}
