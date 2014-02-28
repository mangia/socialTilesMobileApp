package dk.dtu.playware.socialtiles.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class GoalExpandableListAdapter.
 */
public class GoalExpandableListAdapter extends BaseExpandableListAdapter {

	/** The groups. */
	private ArrayList<HashMap<String,String>> groups;
	
	/** The childs. */
	private ArrayList<ArrayList<HashMap<String,String>>> childs;
	
	/** The m atc fragment activity. */
	private FragmentActivity mAtcFragmentActivity;

	/**
	 * The Class ViewHolderChild.
	 */
	static class ViewHolderChild{
		
		/** The user name. */
		TextView userName;
		
		/** The user image. */
		ImageView userImage;
		
		/** The goal progress. */
		ProgressBar goalProgress;
	}

	/**
	 * The Class ViewHolderGroup.
	 */
	static class ViewHolderGroup{
		
		/** The goal. */
		TextView goal;
		
		/** The expire date. */
		TextView expireDate;
	}

	/**
	 * Instantiates a new goal expandable list adapter.
	 *
	 * @param mAcFragmentActivity the m ac fragment activity
	 * @param groups the groups
	 * @param childs the childs
	 */
	public GoalExpandableListAdapter(FragmentActivity mAcFragmentActivity, ArrayList<HashMap<String,String>> groups, ArrayList<ArrayList<HashMap<String,String>>> childs){
		this.groups = groups;
		this.childs = childs;
		this.mAtcFragmentActivity = mAcFragmentActivity;
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
			convertView = mAtcFragmentActivity.getLayoutInflater().inflate(R.layout.child_layout, null);
			holder = new ViewHolderChild();
			holder.userName = (TextView) convertView.findViewById(R.id.profil_name_expandable);
			holder.userImage = (ImageView) convertView.findViewById(R.id.profil_image_expandable);
			holder.goalProgress = (ProgressBar) convertView.findViewById(R.id.progressBar_goal_expandable);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolderChild) convertView.getTag();
		}
		HashMap<String, String> child = childs.get(groupPosition).get(childPosition);
		Log.d("SocialTherapyTiles","child is "+child);
		holder.userName.setText(child.get(Tags.profileName));
		UrlImageViewHelper.setUrlDrawable(holder.userImage,child.get(Tags.profileImg), R.drawable.loading);
		holder.goalProgress.setProgress(Integer.parseInt(child.get(Tags.goalProgress)));
		holder.goalProgress.setMax(Integer.parseInt(child.get(Tags.goalThreshold)));



		return convertView;
	}	

	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolderGroup holder;
		if(convertView == null){
			convertView = mAtcFragmentActivity.getLayoutInflater().inflate(R.layout.group_layout, null);
			holder = new ViewHolderGroup();
			holder.expireDate = (TextView) convertView.findViewById(R.id.goal_date_expandable);
			holder.goal       = (TextView) convertView.findViewById(R.id.goal_name_group_expandable);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolderGroup) convertView.getTag();
		}
		
		HashMap<String, String> group = groups.get(groupPosition);
		Log.d(Tags.debugTag, "group is "+group);
		holder.expireDate.setText(group.get(Tags.goalExpire));
		holder.goal.setText(group.get(Tags.goalName));
		
		return convertView;
	}



}
