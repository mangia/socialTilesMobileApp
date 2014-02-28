package dk.dtu.playware.socialtiles.groups;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.adapters.UserAdapter;
import dk.dtu.playware.socialtiles.api.ServerApi;
import dk.dtu.playware.socialtiles.datahandlers.UserDataHandler;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class MemberGroupsTab.
 */
public class MemberGroupsTab extends Fragment  {

	/** The m ac fragment activity. */
	private static FragmentActivity mAcFragmentActivity;
	
	/** The m data handler. */
	private static UserDataHandler mDataHandler;
	
	/** The user list. */
	private static ListView userList;
	
	/** The add new member button. */
	private Button addNewMemberButton;
	
	/** The user adapter. */
	private UserAdapter userAdapter;
	
	/** The group_id. */
	private int group_id;
	
	/** The info text. */
	private TextView infoText;

	/**
	 * Instantiates a new member groups tab.
	 */
	public MemberGroupsTab(){

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

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//		View v =inflater.inflate(R.layout.feedstab_layout, container, false);
		savedInstanceState =getArguments();
		group_id     = Integer.parseInt(savedInstanceState.getString(Tags.group_id));

		TextView emptyTextView = new TextView(getActivity());
		emptyTextView.setText("no feeds available");
		mAcFragmentActivity = getActivity();
		View v = inflater.inflate(R.layout.members_layout,container, false);
		infoText = (TextView)  v.findViewById(R.id.infoText);

		userList = (ListView) v.findViewById(R.id.membersListview_id);
		userList.setEmptyView(emptyTextView);

		addNewMemberButton = (Button) v.findViewById(R.id.addnewmember_id);
		addNewMemberButton.setText("Add new member");
		addNewMemberButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		mDataHandler = new UserDataHandler();
		userAdapter = new UserAdapter(mAcFragmentActivity, mDataHandler.getData(), false);
		userList.setAdapter(userAdapter);
		fetchData();
		return v;
	}

	/**
	 * Fetch data.
	 */
	private void fetchData(){
		/*final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "getting the group's members...", true);
        pd.setCancelable(false);*/
		
		infoText.setVisibility(View.VISIBLE);
		infoText.setText("getting the group's members...");
		
		ServerApi.get(ServerApi.groupMember, ServerApi.getGroupMembers(group_id), new JsonHttpResponseHandler(){			
			@Override
			public void onSuccess(JSONArray members) {
				super.onSuccess(members);
				Log.d(Tags.debugTag, "members are "+ members);
				for(int i =0 ; i< members.length();  i ++){
					JSONObject member;
					try {
						member = (JSONObject) members.get(i);
						mDataHandler.add(0, getHashMap(member));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				userAdapter.notifyDataSetChanged();
				//pd.dismiss();
				infoText.setVisibility(View.GONE);
			}

			@Override
			public void onSuccess(JSONObject member) {
				super.onSuccess(member);
				Log.d(Tags.debugTag, "member are "+ member);
				mDataHandler.add(0, getHashMap(member));
				userAdapter.notifyDataSetChanged();
				//pd.dismiss();
				infoText.setVisibility(View.GONE);
			}
		});


	}

	/**
	 * Gets the hash map.
	 *
	 * @param member the member
	 * @return the hash map
	 */
	private HashMap<String, String> getHashMap(JSONObject member) {
		HashMap<String, String> hm  = new HashMap<String, String>();
		try {
			hm.put(Tags.profileImg,Tags.facebookApi+ member.getString(Tags.fbid)+Tags.fbPictureSquare);
			hm.put(Tags.profileName, member.getString(Tags.first_name)+ " "+member.getString(Tags.last_name));
			return hm;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
