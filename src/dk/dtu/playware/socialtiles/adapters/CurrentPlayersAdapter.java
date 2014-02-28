package dk.dtu.playware.socialtiles.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.adapters.MemberGroupsEventAdapter.ViewHolderChild;
import dk.dtu.playware.socialtiles.tags.Tags;

import android.R.bool;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class CurrentPlayersAdapter.
 */
public class CurrentPlayersAdapter extends BaseExpandableListAdapter {


	/** The m ac fragment activity. */
	private FragmentActivity mAcFragmentActivity;
	
	/** The groups. */
	private ArrayList<HashMap<String, String>> groups;
	
	/** The childs. */
	private ArrayList<ArrayList<HashMap<String, String>>> childs;
	
	/** The has color. */
	private boolean hasColor;


	/**
	 * The Class ViewHolderChild.
	 */
	static class ViewHolderChild{
		
		/** The user name. */
		TextView userName;
		
		/** The user image. */
		ImageView userImage;
		
		/** The user color. */
		TextView userColor;
	}

	/**
	 * Instantiates a new current players adapter.
	 *
	 * @param mAcFragmentActivity the m ac fragment activity
	 * @param groupsData the groups data
	 * @param usersData the users data
	 * @param hasColor the has color
	 */
	public CurrentPlayersAdapter(FragmentActivity mAcFragmentActivity,
			ArrayList<HashMap<String, String>> groupsData,
			ArrayList<ArrayList<HashMap<String, String>>> usersData, boolean hasColor) {
		this.mAcFragmentActivity = mAcFragmentActivity;
		this.groups = groupsData;
		this.childs = usersData;
		this.hasColor = hasColor;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChild(int, int)
	 */
	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return childs.get(arg0).get(arg1);
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChildId(int, int)
	 */
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub

		return childs.get(groupPosition).size();
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroup(int)
	 */
	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groups.get(groupPosition);
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroupCount()
	 */
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groups.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroupId(int)
	 */
	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#hasStableIds()
	 */
	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
	 */
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}


	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolderChild holder;
		if (convertView == null) {
			convertView = mAcFragmentActivity.getLayoutInflater().inflate(R.layout.event_users_item_layout, null);
			holder = new ViewHolderChild();
			holder.userName = (TextView) convertView.findViewById(R.id.profile_name_evet_member);
			holder.userImage = (ImageView) convertView.findViewById(R.id.profile_image_evet_member);
			holder.userColor = (TextView) convertView.findViewById(R.id.score_event_member);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolderChild) convertView.getTag();
		}
		holder.userName.setText(childs.get(groupPosition).get(childPosition).get(Tags.profileName));
		UrlImageViewHelper.setUrlDrawable(holder.userImage,childs.get(groupPosition).get(childPosition).get(Tags.profileImg), R.drawable.loading);
		
		if(hasColor){
			holder.userColor.setText(childs.get(groupPosition).get(childPosition).get(Tags.color));
		}else{
			holder.userColor.setVisibility(View.GONE);
		}
		return convertView;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = mAcFragmentActivity.getLayoutInflater().inflate(android.R.layout.simple_expandable_list_item_1, null);
		}
		TextView text = (TextView) convertView.findViewById(android.R.id.text1);
		text.setText("Current Players");
		return convertView;
	}

}
