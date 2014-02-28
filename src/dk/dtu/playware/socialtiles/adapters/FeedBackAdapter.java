package dk.dtu.playware.socialtiles.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import dk.dtu.playware.socialtiles.PlayTab;
import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class FeedBackAdapter.
 */
public class FeedBackAdapter extends BaseExpandableListAdapter {
	
	/** The m ac fragment activity. */
	private FragmentActivity mAcFragmentActivity;
	
	/** The groups. */
	private ArrayList<HashMap<String, String>> groups;
	
	/** The childs. */
	private ArrayList<ArrayList<HashMap<String, String>>> childs;
	
	/** The share data. */
	private ShareData shareData;

	/**
	 * The Class ViewHolderChild.
	 */
	static class ViewHolderChild{
		
		/** The feed back attribute. */
		TextView feedBackAttribute;
		
		/** The feed back value. */
		TextView feedBackValue;
		
		/** The share button. */
		Button   shareButton;

	}

	/**
	 * The Class ViewHolderGroup.
	 */
	static class ViewHolderGroup{
		
		/** The game name. */
		TextView gameName;
		
		/** The game date. */
		TextView gameDate;
		
		/** The game score. */
		TextView gameScore;
	}
	
	// Container Activity must implement this interface
		/**
	 * The Interface ShareData.
	 */
	public interface ShareData {
			
			/**
			 * On share data.
			 *
			 * @param arrayList the array list
			 */
			public void onShareData(ArrayList<HashMap<String, String>> arrayList);
		}


	/**
	 * Instantiates a new feed back adapter.
	 *
	 * @param mAcFragmentActivity the m ac fragment activity
	 * @param fragment the fragment
	 * @param groupsData the groups data
	 * @param usersData the users data
	 */
	public FeedBackAdapter(FragmentActivity mAcFragmentActivity,
			Fragment fragment, ArrayList<HashMap<String, String>> groupsData,
			ArrayList<ArrayList<HashMap<String, String>>> usersData) {
		this.mAcFragmentActivity = mAcFragmentActivity;
		this.groups = groupsData;
		this.childs = usersData;
		
		try {
			shareData = (ShareData) fragment;
		} catch (ClassCastException e) {
			throw new ClassCastException(fragment.toString()
					+ " must implementSendDataCallBack");
		}
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

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolderChild holder;
		if (convertView == null) {
			convertView = mAcFragmentActivity.getLayoutInflater().inflate(R.layout.feedback_expandable_child_layout, null);
			holder = new ViewHolderChild();
			 holder.feedBackAttribute = (TextView) convertView.findViewById(R.id.feedback_component);
			 holder.feedBackValue = (TextView) convertView.findViewById(R.id.feedback_value);
			 holder.shareButton = (Button) convertView.findViewById(R.id.share_button);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolderChild) convertView.getTag();
		}
		
		holder.feedBackAttribute.setText(childs.get(groupPosition).get(childPosition).get(Tags.feedback_attribute));
		holder.feedBackValue.setText(childs.get(groupPosition).get(childPosition).get(Tags.feedback_value));
		
		if(isLastChild){
			holder.shareButton.setId(groupPosition);
			holder.shareButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int groupPosition =  ((Button)v).getId();
					// TODO Auto-generated method stub
					shareData.onShareData(childs.get(groupPosition));
				}
			});
			holder.shareButton.setVisibility(View.VISIBLE);
		}
		else{
			holder.shareButton.setVisibility(View.GONE);
		}
		
		return convertView;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolderGroup holder;
		if(convertView == null){
			convertView = mAcFragmentActivity.getLayoutInflater().inflate(R.layout.feedback_expandable_group_layout, null);
			holder = new ViewHolderGroup();
			holder.gameName = (TextView) convertView.findViewById(R.id.game_name); 
			holder.gameDate = (TextView) convertView.findViewById(R.id.game_date);
			holder.gameScore = (TextView) convertView.findViewById(R.id.game_score);
			//holder.shareButton = (Button) convertView.findViewById(R.id.share_button);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolderGroup) convertView.getTag();
		}
				
		holder.gameName.setText(groups.get(groupPosition).get(Tags.gameName));
		holder.gameDate.setText(groups.get(groupPosition).get(Tags.gameDate));
		holder.gameScore.setText(groups.get(groupPosition).get(Tags.gameScore));
		
		return convertView;
	}


}
