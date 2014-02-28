package dk.dtu.playware.socialtiles.userdetails.mesubtabs;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import dk.dtu.playware.socialtiles.MainActivity;
import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.adapters.UserAdapter;
import dk.dtu.playware.socialtiles.api.ServerApi;
import dk.dtu.playware.socialtiles.datahandlers.UserDataHandler;
import dk.dtu.playware.socialtiles.friends.AddNewFriendMainActivity;
import dk.dtu.playware.socialtiles.friends.FriendMainPage;
import dk.dtu.playware.socialtiles.tags.Tags;
import dk.dtu.playware.socialtiles.MainActivity;
// TODO: Auto-generated Javadoc

/**
 * The Class FriendsListTab.
 */
public class FriendsListTab extends Fragment {
	
	static String TAG = "SocialTherapyTiles";
	
	/** The m ac fragment activity. */
	private static FragmentActivity mAcFragmentActivity;
	
	/** The m data handler. */
	private static UserDataHandler mDataHandler;
	
	/** The user list. */
	private static ListView userList;
	
	/** The add new friend. */
	private Button addNewFriend;
	
	/** The user adapter. */
	private UserAdapter userAdapter;
	
	/** The info text. */
	private TextView infoText;

	/**
	 * Instantiates a new friends list tab.
	 */
	public FriendsListTab(){

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



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//		View v =inflater.inflate(R.layout.feedstab_layout, container, false);
		TextView emptyTextView = new TextView(getActivity());
		emptyTextView.setText("no feeds available");
		mAcFragmentActivity = getActivity();
		View v = inflater.inflate(R.layout.button_listview_layout,container, false);
		infoText = (TextView)  v.findViewById(R.id.infoText);

		userList = (ListView) v.findViewById(R.id.listView1);
		userList.setEmptyView(emptyTextView);

		addNewFriend = (Button) v.findViewById(R.id.button1);
		addNewFriend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mAcFragmentActivity, AddNewFriendMainActivity.class);
				startActivity(intent);	
			}
		});
		addNewFriend.setText("Add New Friend");


		mDataHandler = new UserDataHandler();
		userAdapter = new UserAdapter(mAcFragmentActivity, mDataHandler.getData(), false);
		userList.setAdapter(userAdapter);
		userList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				return;
				// TODO Auto-generated method stub
//				Intent intent = new Intent(getActivity(), FriendMainPage.class);
//				HashMap<String, String> hm = mDataHandler.getDataEl(arg2);
//				Log.d(Tags.debugTag,"user is " + hm+ " and pos is" +arg2+ " and size is "+mDataHandler.getData().size());
//				intent.putExtra(Tags.first_name, hm.get(Tags.first_name));
//				intent.putExtra(Tags.last_name, hm.get(Tags.last_name));
//				intent.putExtra(Tags.total_score, hm.get(Tags.total_score));
//				intent.putExtra(Tags.user_id, hm.get(Tags.user_id));
//				intent.putExtra(Tags.isFriend, "true");
//				Log.d(Tags.debugTag,"args are "+intent.getExtras());
//				startActivity(intent);	
			}

		});
		fetchData();
		return v;
	}

	/**
	 * Fetch all the friends
	 */
	private void fetchData(){
		/*final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "getting your friends...", true);
        pd.setCancelable(false);*/

		infoText.setVisibility(View.VISIBLE);
		infoText.setText("getting your friends...");

		ServerApi.get(ServerApi.friends, ServerApi.getFriends(Tags.User.user_id), new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONArray members) {
				super.onSuccess(members);
				Log.d(Tags.debugTag,"friends are : "+ members.toString());
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
				//if(member.has(name))
				if(member.has("response")){
					//pd.dismiss();
					infoText.setVisibility(View.GONE);

				}
				else{
					mDataHandler.add(0, getHashMap(member));
					userAdapter.notifyDataSetChanged();
					//pd.dismiss();
					infoText.setVisibility(View.GONE);
				}

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


