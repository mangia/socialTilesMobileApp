package dk.dtu.playware.socialtiles.events.create;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import dk.dtu.playware.socialtiles.adapters.ListWithCheckBoxAdapter;
import dk.dtu.playware.socialtiles.api.ServerApi;
import dk.dtu.playware.socialtiles.datahandlers.UserDataHandler;
import dk.dtu.playware.socialtiles.events.create.EventsCreateMainActivity.UpdateableFragment;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class FriendsListWithCheckBox.
 */
public class FriendsListWithCheckBox extends ListFragment implements UpdateableFragment {


	/** The Constant TAG. */
	private static final String TAG = null;
	
	/** The send data call back. */
	private SendDataCallBack sendDataCallBack;
	
	/** The a list. */
	private ArrayList<HashMap<String, String>> aList;
	
	/** The m data handler. */
	private UserDataHandler mDataHandler;
	
	/** The m list adabter. */
	private ListWithCheckBoxAdapter mListAdabter;
	
	/** The p_type. */
	private String p_type = "";

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}



	// Container Activity must implement this interface
	/**
	 * The Interface SendDataCallBack.
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
		 * Gets the participant id.
		 *
		 * @return the participant id
		 */
		public String getParticipantId();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			sendDataCallBack = (SendDataCallBack) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implementSendDataCallBack");
		}
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.ListFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//https://www.facebook.com/sniperas.ready?fref=ts		
		mDataHandler = new UserDataHandler();
		mListAdabter= new ListWithCheckBoxAdapter(mDataHandler.getData(), getActivity());
		setListAdapter(mListAdabter);
		
		return super.onCreateView(inflater, container, savedInstanceState); 

	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
	}

	//	@Override
	/* (non-Javadoc)
	 * @see android.support.v4.app.ListFragment#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	public void onListItemClick(ListView parent, View v, int position, long id){
		Log.d(TAG,"I am deleting the item: "+position);		

	}

	/* (non-Javadoc)
	 * @see dk.dtu.playware.socialtiles.events.create.EventsCreateMainActivity.UpdateableFragment#updateParticipantType(java.lang.String)
	 */
	@Override
	public void updateParticipantType(String p_type) {
		// TODO Auto-generated method stub
		if(!this.p_type .equals(p_type)){
			mListAdabter.clearData();
			this.p_type = p_type;

			if(this.p_type.equals("1")){
				fetchDataGroups();
				mListAdabter.setImageVisible(false);
			}
			else{
				fetchDataUsers();
				mListAdabter.setImageVisible(true);
			}
		}
	}
	
	/**
	 * Fetch data users.
	 */
	private void fetchDataUsers(){
		final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "getting your friends...", true);
        pd.setCancelable(false);
		ServerApi.get(ServerApi.friends, new RequestParams(Tags.user_id,Tags.User.user_id), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray friends) {
				super.onSuccess(friends);
				for(int i =0 ; i< friends.length();  i ++){
					JSONObject group;
					try {
						group = (JSONObject) friends.get(i);
						mDataHandler.add(0, getHashMapUser(group));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				mListAdabter.notifyDataSetChanged();
				pd.dismiss();
			}

			@Override
			public void onSuccess(JSONObject friend) {
				super.onSuccess(friend);
				mDataHandler.add(0, getHashMapUser(friend));
				mListAdabter.notifyDataSetChanged();
				pd.dismiss();
			}

		});
	}
	
	
	/**
	 * Fetch data groups.
	 */
	private void fetchDataGroups(){

		ServerApi.get(ServerApi.groups, new RequestParams(Tags.op, "all_groups"), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray groups) {
				super.onSuccess(groups);
				//Log.d(Tags.debugTag,"got some groups: "+ groups);
				for(int i =0 ; i< groups.length();  i ++){
					JSONObject group;
					try {
						group = (JSONObject) groups.get(i);
						//Log.d(Tags.debugTag, "group is : " + group);
						mDataHandler.add(getHashMapGroup(group));

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				mListAdabter.notifyDataSetChanged();
			}

			@Override
			public void onSuccess(JSONObject group) {
				super.onSuccess(group);
				//Log.d(Tags.debugTag,"got some group: "+ group);
				mDataHandler.add(0, getHashMapGroup(group));
				mListAdabter.notifyDataSetChanged();
			}

			@Override
			public void onSuccess(String arg0) {
				super.onSuccess(arg0);				
				Log.d(Tags.debugTag,"got a string " + arg0);
			}
		});

	}
	
	/**
	 * Gets the hash map user.
	 *
	 * @param group the group
	 * @return the hash map user
	 */
	private HashMap<String, String> getHashMapUser(JSONObject group) {
		HashMap<String, String> hm = new HashMap<String, String>();
		try {
			hm.put(Tags.entity, group.getString(Tags.entity));
			hm.put(Tags.user_id, group.getString(Tags.user_id));
			hm.put(Tags.first_name, group.getString(Tags.first_name));
			hm.put(Tags.last_name, group.getString(Tags.last_name));
			hm.put(Tags.fbid, group.getString(Tags.fbid));
			hm.put(Tags.total_score, group.getString(Tags.total_score));
			hm.put(Tags.total_duration, group.getString(Tags.total_duration));
			hm.put(Tags.num_achievments, group.getString(Tags.num_achievments));
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
	 * @param group the group
	 * @return the hash map group
	 */
	private HashMap<String, String> getHashMapGroup(JSONObject group) {
		HashMap<String, String> hm = new HashMap<String, String>();
		try {
			hm.put(Tags.entity, group.getString(Tags.entity));
			hm.put(Tags.creator, group.getString(Tags.creator));
			hm.put(Tags.group_id, group.getString(Tags.group_id));
			hm.put(Tags.name, group.getString(Tags.name));
			hm.put(Tags.date_created, group.getString(Tags.date_created));
			hm.put(Tags.description, group.getString(Tags.description));
			hm.put(Tags.first_name, group.getString(Tags.first_name));
			hm.put(Tags.last_name, group.getString(Tags.last_name));
			hm.put(Tags.fbid, group.getString(Tags.fbid));
		} catch (JSONException e) {
			e.printStackTrace();
			hm = null;
		}
		//Log.d(Tags.debugTag, "hash map is :"+hm);
		return hm;
	}

}
