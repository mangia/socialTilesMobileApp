package dk.dtu.playware.socialtiles.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.adapters.UserAdapter.ViewHolder;
import dk.dtu.playware.socialtiles.tags.Tags;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class MemberUserEventAdapter.
 */
public class MemberUserEventAdapter  extends BaseAdapter{

	/** The m lis array list. */
	ArrayList<HashMap<String,String>> mLisArrayList = new ArrayList<HashMap<String,String>>();
	
	/** The m atc fragment activity. */
	private FragmentActivity mAtcFragmentActivity;

	/**
	 * The Class ViewHolder.
	 */
	static class ViewHolder {
		
		/** The user name. */
		TextView userName;
		
		/** The user score. */
		TextView userScore;
		
		/** The user image. */
		ImageView userImage;
	}

	/**
	 * Instantiates a new member user event adapter.
	 *
	 * @param mAcFragmentActivity the m ac fragment activity
	 * @param data the data
	 */
	public MemberUserEventAdapter(FragmentActivity mAcFragmentActivity, ArrayList<HashMap<String,String>> data) {
		// TODO Auto-generated constructor stub
		this.mAtcFragmentActivity = mAcFragmentActivity;
		this.mLisArrayList = data;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mLisArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return  mLisArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = mAtcFragmentActivity.getLayoutInflater().inflate(R.layout.event_users_item_layout, null);
			holder = new ViewHolder();
			holder.userName = (TextView) convertView.findViewById(R.id.profile_name_evet_member);
			holder.userImage = (ImageView) convertView.findViewById(R.id.profile_image_evet_member);
			holder.userScore = (TextView) convertView.findViewById(R.id.score_event_member);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.userName.setText(mLisArrayList.get(position).get(Tags.profileName));
		UrlImageViewHelper.setUrlDrawable(holder.userImage,mLisArrayList.get(position).get(Tags.profileImg), R.drawable.loading);
		holder.userScore.setText("Score : "+mLisArrayList.get(position).get(Tags.userScore));

		return convertView;
	}

}
