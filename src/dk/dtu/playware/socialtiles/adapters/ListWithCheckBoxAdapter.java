package dk.dtu.playware.socialtiles.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class ListWithCheckBoxAdapter.
 */
public class ListWithCheckBoxAdapter extends BaseAdapter {

	/** The m list data. */
	public ArrayList<HashMap<String,String>> mListData;
	
	/** The m atc fragment activity. */
	public FragmentActivity mAtcFragmentActivity;
	
	/** The checked buttons. */
	public static ArrayList<String> checkedButtons;
	
	/** The current position. */
	public static int currentPosition;
	
	/** The send data call back. */
	public SendDataCallBack sendDataCallBack;
	
	/** The img visibility. */
	private boolean imgVisibility = true;
	
	// Container Activity must implement this interface
	/**
	 * The Interface SendDataCallBack. it is the interface between the adapter and the main activity 
	 * which notifies when the checkbox is checked
	 */
	public interface SendDataCallBack {
		
		/**
		 * Onsend data.
		 *
		 * @param key the key
		 * @param data the data
		 */
		public void onsendData(int key, HashMap<String, String> data);
		
		/**
		 * Gets the checked buttons.
		 *
		 * @return the checked buttons
		 */
		public  List<HashMap<String,String>> getCheckedButtons();
		
		/**
		 * Clear participants.
		 */
		public void clearParticipants();
	}

	/**
	 * Instantiates a new list with check box adapter.
	 *
	 * @param listData the list data
	 * @param fragmentActivity the fragment activity
	 */
	public ListWithCheckBoxAdapter(ArrayList<HashMap<String, String>> listData, FragmentActivity fragmentActivity){
		super();
		mListData = listData;
		mAtcFragmentActivity = fragmentActivity;
		try {
			sendDataCallBack = (SendDataCallBack) mAtcFragmentActivity;
		} catch (ClassCastException e) {
			throw new ClassCastException(mAtcFragmentActivity.toString()
					+ " must implementSendDataCallBack");
		}
		checkedButtons = new ArrayList<String>();
	}

	/**
	 * Sets the image visible.
	 *
	 * @param visibility the new image visible
	 */
	public void setImageVisible(boolean visibility){
		this.imgVisibility  = visibility;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		currentPosition = position;
		//Log.d("SocialTherapyTiles","current position is "+currentPosition+"=="+position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = mAtcFragmentActivity.getLayoutInflater().inflate(R.layout.list_with_check_box_item_layout, null);
			holder = new ViewHolder();
			holder.friendsName = (TextView) convertView.findViewById(R.id.friend_name);
			holder.friendsImage = (ImageView) convertView.findViewById(R.id.profile_image);
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		
		if(imgVisibility){
			holder.friendsName.setText(mListData.get(position).get(Tags.first_name)+" "+mListData.get(position).get(Tags.last_name));
			holder.friendsImage.setVisibility(View.VISIBLE);
			UrlImageViewHelper.setUrlDrawable(holder.friendsImage, Tags.facebookApi+mListData.get(position).get(Tags.fbid)+Tags.fbPictureSmall, R.drawable.loading);
		}
		else{
			holder.friendsName.setText(mListData.get(position).get(Tags.name));
			holder.friendsImage.setVisibility(View.GONE);
		}
		holder.checkBox.setId(position);
		if(sendDataCallBack.getCheckedButtons().contains(mListData.get(position))){
			holder.checkBox.setChecked(true);
		}
		else{
			holder.checkBox.setChecked(false);
		}
		holder.checkBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Log.d("SocialTherapyTiles","current position is "+currentPosition+" mListData size is "+mListData.size());
				int position = ((CheckBox)v).getId();
				if (((CheckBox) v).isChecked()){
					sendDataCallBack.onsendData(Tags.addFriendId, mListData.get(position));
					checkedButtons.add(""+position);
				}
				else{
					sendDataCallBack.onsendData(Tags.removeFriendId, mListData.get(position));
					checkedButtons.remove(""+position);
				}
			}
		});
		return convertView;
	}

	/**
	 * The Class ViewHolder.
	 */
	static class ViewHolder {
		
		/** The friends name. */
		TextView friendsName;
		
		/** The check box. */
		CheckBox checkBox;
		
		/** The friends image. */
		ImageView friendsImage;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListData.size();
	}



	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListData.get(position);

	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * Clear data.
	 */
	public void clearData() {
		mListData.clear();	
		sendDataCallBack.clearParticipants();
	}

}
