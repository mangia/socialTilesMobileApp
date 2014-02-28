package dk.dtu.playware.socialtiles.events;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.AsyncTaskLoader;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.adapters.MemberGroupsEventAdapter;
import dk.dtu.playware.socialtiles.api.ServerApi;
import dk.dtu.playware.socialtiles.datahandlers.GroupMembersEventsHandler;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class MemberGroupsEventsTab.
 * if the event participant type is groups this fragment is visible
 */
public class MemberGroupsEventsTab extends Fragment {

	/** The m ac fragment activity. */
	private static FragmentActivity mAcFragmentActivity;
	
	/** The member group adapter. */
	private MemberGroupsEventAdapter memberGroupAdapter;
	
	/** The end date text. */
	private static TextView endDateText;
	
	/** The m goals list data. */
	public static ArrayList<HashMap<String, String>> mGoalsListData;
	
	/** The m users list data. */
	public static ArrayList<ArrayList<HashMap<String, String>>> mUsersListData;
	
	/** The m data handler. */
	public static GroupMembersEventsHandler mDataHandler;
	
	/** The members list. */
	private static ExpandableListView membersList;
	
	/** The event_id. */
	private int event_id ;
	
	/** The info text. */
	private TextView infoText;
	
	/**
	 * Instantiates a new member groups events tab.
	 */
	public MemberGroupsEventsTab(){

	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mAcFragmentActivity = getActivity();
		View v = inflater.inflate(R.layout.event_groups_layout,container, false); 
		savedInstanceState =getArguments();
		event_id     = Integer.parseInt(savedInstanceState.getString(Tags.event_id));
		endDateText = (TextView) v.findViewById(R.id.event_end_date_groups);
		changeDate(savedInstanceState.getString(Tags.end_date));
		infoText = (TextView)  v.findViewById(R.id.infoText);

		membersList = (ExpandableListView) v.findViewById(R.id.event_groups_list);
		mDataHandler = new GroupMembersEventsHandler();
		memberGroupAdapter= new MemberGroupsEventAdapter(mAcFragmentActivity, mDataHandler.getGroupsData(), mDataHandler.getUsersData());

		membersList.setAdapter(memberGroupAdapter);
		setGroupIndicatorToRight();

		fetchData();

		return v;
	}


	/**
	 * Fetch data.
	 */
	private void fetchData(){
		/*final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "getting the event's group participants...", true);
        pd.setCancelable(false);*/
		infoText.setVisibility(View.VISIBLE);
		infoText.setText("getting the event's group participants...");
		ServerApi.get(ServerApi.eventParticipants, ServerApi.getMembers(event_id), new JsonHttpResponseHandler(){			
			@Override
			public void onSuccess(JSONArray members) {
				super.onSuccess(members);
				Log.d(Tags.debugTag, "members are "+ members);
				for(int i =0 ; i< members.length();  i ++){
					JSONObject member;
					try {
						member = (JSONObject) members.get(i);
						int pos = mDataHandler.addGroup(getHashMapGroup(member));
						Log.d(Tags.debugTag, "group pos is "+pos +" group member is "+getHashMapGroup(member));

						if(pos >-1){
							int k = mDataHandler.addChild(pos, getHashMapUser(member));
							Log.d(Tags.debugTag, "group pos is "+k +" group member is "+getHashMapUser(member));
							Log.d(Tags.debugTag,"groups data size is "+mDataHandler.getGroupsData().size()+ " childs data size is "+ mDataHandler.getGroupsDataEl(pos)+"");
						
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				memberGroupAdapter.notifyDataSetChanged();
//				pd.dismiss();
				infoText.setVisibility(View.GONE);
			}

			@Override
			public void onSuccess(JSONObject member) {
				super.onSuccess(member);
				Log.d(Tags.debugTag, "member are "+ member);
				int pos = mDataHandler.addGroup(getHashMapGroup(member));
				Log.d(Tags.debugTag, "group pos is "+pos +" group member is "+getHashMapGroup(member));
				if(pos >-1){
					int k = mDataHandler.addChild(pos, getHashMapUser(member));
					Log.d(Tags.debugTag, "group pos is "+k +" group member is "+getHashMapUser(member));
					Log.d(Tags.debugTag,"groups data size is "+mDataHandler.getGroupsData().size()+ " childs data size is "+ mDataHandler.getGroupsDataEl(pos)+"");
				}	
				memberGroupAdapter.notifyDataSetChanged();
				//pd.dismiss();
				infoText.setVisibility(View.GONE);
			}
		});
	}

	/**
	 * Gets the hash map user.
	 *
	 * @param member the member
	 * @return the hash map user
	 */
	private HashMap<String, String> getHashMapUser(JSONObject member) {
		HashMap<String, String> hm  = new HashMap<String, String>();
		try {
			hm.put(Tags.profileImg,Tags.facebookApi+ member.getString(Tags.fbid)+Tags.fbPictureSquare);
			hm.put(Tags.profileName, member.getString(Tags.first_name)+ " "+member.getString(Tags.last_name));
			hm.put(Tags.currently, member.getString(Tags.currently));
			return hm;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the hash map group.
	 *
	 * @param member the member
	 * @return the hash map group
	 */
	private HashMap<String, String> getHashMapGroup(JSONObject member) {
		HashMap<String, String> hm  = new HashMap<String, String>();
		try {
			hm.put(Tags.groupName,member.getString(Tags.name));
			hm.put(Tags.group_id,member.getString(Tags.group_id));
			
			
			return hm;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * Change date.
	 *
	 * @param date the date
	 */
	static void changeDate(String date){
		endDateText.setText("Event finishes on "+date);
	}


	/**
	 * Sets the group indicator to right.
	 */
	private void setGroupIndicatorToRight() {
		/* Get the screen width */
		DisplayMetrics dm = new DisplayMetrics();
		mAcFragmentActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;

		membersList.setIndicatorBounds(width - getDipsFromPixel(35), width
				- getDipsFromPixel(5));
	}

	/**
	 * Gets the dips from pixel.
	 *
	 * @param pixels the pixels
	 * @return the dips from pixel
	 */
	public int getDipsFromPixel(float pixels) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (pixels * scale + 0.5f);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAcFragmentActivity = getActivity();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}



}
