package dk.dtu.playware.socialtiles.events;

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
import android.view.ViewGroup;
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
 * The Class MemberUserEventsTab.
 * if the event participant type is users this fragment is visible
 */
public class MemberUserEventsTab extends Fragment{

	/** The user list. */
	private static ListView userList;
	
	/** The m ac fragment activity. */
	private static FragmentActivity mAcFragmentActivity;
	
	/** The m data handler. */
	public static UserDataHandler mDataHandler;
	
	/** The end date text. */
	private static  TextView endDateText;
	
	/** The user adapter. */
	private UserAdapter userAdapter;
	
	/** The event_id. */
	private int event_id;
	
	/** The info text. */
	private TextView infoText;

	/**
	 * Instantiates a new member user events tab.
	 */
	public MemberUserEventsTab(){

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//		View v =inflater.inflate(R.layout.feedstab_layout, container, false);

		savedInstanceState =getArguments();
		event_id     = Integer.parseInt(savedInstanceState.getString(Tags.event_id));
		

		mAcFragmentActivity = getActivity();
		View v = inflater.inflate(R.layout.event_users_layout,container, false);
		userList = (ListView) v.findViewById(R.id.event_members_list);
		infoText = (TextView)  v.findViewById(R.id.infoText);
		endDateText = (TextView) v.findViewById(R.id.event_end_date);
		changeDate(savedInstanceState.getString(Tags.end_date));
		
		mDataHandler = new UserDataHandler();
		userAdapter = new UserAdapter(mAcFragmentActivity, mDataHandler.getData(), true);
		userList.setAdapter(userAdapter);
		fetchData();

		return v;
	}

	/**
	 * Change date.
	 *
	 * @param date the date
	 */
	static void changeDate(String date){
		endDateText.setText("Event finishes on "+date);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAcFragmentActivity = getActivity();
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}


	/**
	 * Fetch data.
	 */
	private void fetchData(){
		/*final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "getting the event's participants...", true);
        pd.setCancelable(false);*/
		
		infoText.setVisibility(View.VISIBLE);
		infoText.setText("getting the event's participants...");
		
		ServerApi.get(ServerApi.eventParticipants, ServerApi.getMembers(event_id), new JsonHttpResponseHandler(){			
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
			hm.put(Tags.userScore, member.getString("currently"));
			return hm;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}
