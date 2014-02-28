package dk.dtu.playware.socialtiles.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class MemberGroupsEventAdapter. 
 * 
 */
public class MemberGroupsEventAdapter extends BaseExpandableListAdapter {

	/** The m ac fragment activity. */
	private FragmentActivity mAcFragmentActivity;
	
	/** The groups. */
	private ArrayList<HashMap<String, String>> groups;
	
	/** The childs. */
	private ArrayList<ArrayList<HashMap<String, String>>> childs;
	
	
	/**
	 * The Class ViewHolderChild.
	 */
	static class ViewHolderChild{
		
		/** The user name. */
		TextView userName;
		
		/** The user image. */
		ImageView userImage;
		
		/** The user score. */
		TextView userScore;
	}

	/**
	 * The Class ViewHolderGroup.
	 */
	static class ViewHolderGroup{
		
		/** The group name. */
		TextView groupName;
		
		/** The group score. */
		TextView groupScore;
	}
	
	/**
	 * Instantiates a new member groups event adapter.
	 *
	 * @param mAcFragmentActivity the m ac fragment activity
	 * @param groupsData the groups data
	 * @param usersData the users data
	 */
	public MemberGroupsEventAdapter(FragmentActivity mAcFragmentActivity,
			ArrayList<HashMap<String, String>> groupsData,
			ArrayList<ArrayList<HashMap<String, String>>> usersData) {
		this.mAcFragmentActivity = mAcFragmentActivity;
		this.groups = groupsData;
		this.childs = usersData;
	}


	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return childs.get(arg0).get(arg1);
	}

	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}


	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		
		return childs.get(groupPosition).size();
	}

	
	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groups.size();
	}


	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}


	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolderChild holder;
		if (convertView == null) {
			convertView = mAcFragmentActivity.getLayoutInflater().inflate(R.layout.event_users_item_layout, null);
			holder = new ViewHolderChild();
			holder.userName = (TextView) convertView.findViewById(R.id.profile_name_evet_member);
			holder.userImage = (ImageView) convertView.findViewById(R.id.profile_image_evet_member);
			holder.userScore = (TextView) convertView.findViewById(R.id.score_event_member);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolderChild) convertView.getTag();
		}
		holder.userName.setText(childs.get(groupPosition).get(childPosition).get(Tags.profileName));
		UrlImageViewHelper.setUrlDrawable(holder.userImage,childs.get(groupPosition).get(childPosition).get(Tags.profileImg), R.drawable.loading);
		holder.userScore.setText("Score : "+childs.get(groupPosition).get(childPosition).get(Tags.currently));

		return convertView;
	}


	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolderGroup holder;
		if(convertView == null){
			convertView = mAcFragmentActivity.getLayoutInflater().inflate(R.layout.event_groups_item_layout, null);
			holder = new ViewHolderGroup();
			holder.groupName = (TextView) convertView.findViewById(R.id.group_name_evet_member); 
			holder.groupScore = (TextView) convertView.findViewById(R.id.score_event__group_member);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolderGroup) convertView.getTag();
		}
		
		holder.groupName.setText(groups.get(groupPosition).get(Tags.groupName));
		
		int group_score = 0;
		for(int i = 0; i< childs.get(groupPosition).size();i++){
			group_score+= Integer.parseInt(childs.get(groupPosition).get(i).get(Tags.currently));
		}
		
		holder.groupScore.setText("Score : "+group_score);
		
		return convertView;
	}

}
