package dk.dtu.playware.socialtiles.friends;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.adapters.UserAdapter;
import dk.dtu.playware.socialtiles.api.ServerApi;
import dk.dtu.playware.socialtiles.datahandlers.UserDataHandler;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class AddNewFriendMainActivity.
 * searches for users given the search keys and then the user can choose to send friend requests to the users
 * of his liking
 */
public class AddNewFriendMainActivity extends FragmentActivity {

	/** The search button. */
	private Button searchButton;
	
	/** The search text. */
	private EditText searchText;
	
	/** The search params text. */
	protected String searchParamsText;
	
	/** The search param. */
	protected String[] searchParam;
	
	/** The friends list. */
	private ListView friendsList;
	
	/** The m adapter. */
	private UserAdapter mAdapter;
	
	/** The m data handler. */
	private static UserDataHandler mDataHandler;
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_friend_main);

		searchText = (EditText) findViewById(R.id.searchText);
		searchText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				searchParamsText = s.toString();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		searchButton = (Button) findViewById(R.id.search_friends_button);
		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HashMap<String, String> params =  new HashMap<String, String>();
				params.put(Tags.op, "search");
				params.put(Tags.user_id,""+ Tags.User.user_id);
				params.put("search_entry", searchParamsText);

				Log.d(Tags.debugTag,"params are "+ params);
				ServerApi.get(ServerApi.users, new RequestParams(params),new JsonHttpResponseHandler(){

					@Override
					public void onSuccess(JSONArray users) {
						// TODO Auto-generated method stub
						super.onSuccess(users);
						Log.d(Tags.debugTag, "users are "+users.toString());
						for(int i =0 ; i< users.length();  i ++){
							JSONObject member;
							try {
								member = (JSONObject) users.get(i);
								mDataHandler.add(0, getHashMap(member));
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						mAdapter.notifyDataSetChanged();
					}
				});
			}
		});

		friendsList  = (ListView) findViewById(R.id.friendsList);
		mDataHandler = new UserDataHandler();
		mAdapter = new UserAdapter(AddNewFriendMainActivity.this, mDataHandler.getData(), false);
		friendsList.setAdapter(mAdapter);
		friendsList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AddNewFriendMainActivity.this, FriendMainPage.class);
				HashMap<String, String> hm = mDataHandler.getDataEl(arg2);
				Log.d(Tags.debugTag,"user is " + hm+ " and pos is" +arg2+ " and size is "+mDataHandler.getData().size());
				intent.putExtra(Tags.first_name, hm.get(Tags.first_name));
				intent.putExtra(Tags.last_name, hm.get(Tags.last_name));
				intent.putExtra(Tags.total_score, hm.get(Tags.total_score));
				intent.putExtra(Tags.user_id, hm.get(Tags.user_id));
				intent.putExtra(Tags.isFriend, hm.get(Tags.isFriend));
				Log.d(Tags.debugTag,"args are "+intent.getExtras());
				startActivity(intent);	
			}

		});
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_new_friend_main, menu);
		return true;
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
			hm.put(Tags.first_name, member.getString(Tags.first_name));
			hm.put(Tags.last_name,member.getString(Tags.last_name));
			hm.put(Tags.total_score, member.getString(Tags.total_score));
			hm.put(Tags.user_id, member.getString(Tags.user_id));
			hm.put(Tags.isFriend, member.getString(Tags.isFriend));
			return hm;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}



