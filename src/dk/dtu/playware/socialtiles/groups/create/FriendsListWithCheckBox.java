package dk.dtu.playware.socialtiles.groups.create;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import dk.dtu.playware.socialtiles.adapters.ListWithCheckBoxAdapter;
import dk.dtu.playware.socialtiles.api.ServerApi;
import dk.dtu.playware.socialtiles.datahandlers.UserDataHandler;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class FriendsListWithCheckBox.
 */
public class FriendsListWithCheckBox extends ListFragment {


	/** The m list adabter. */
	private ListWithCheckBoxAdapter mListAdabter;
	
	/** The m data handler. */
	private UserDataHandler mDataHandler;
	
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
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		//		try {
		//			sendDataCallBack = (SendDataCallBack) activity;
		//		} catch (ClassCastException e) {
		//			throw new ClassCastException(activity.toString()
		//					+ " must implementSendDataCallBack");
		//		}
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

		fetchData();
		return super.onCreateView(inflater, container, savedInstanceState); 

	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * Fetch data.
	 */
	private void fetchData(){
		final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "getting your friends ...", true);
		pd.setCancelable(false);
		ServerApi.get(ServerApi.friends, new RequestParams(Tags.user_id,Tags.User.user_id), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray friends) {
				super.onSuccess(friends);
				for(int i =0 ; i< friends.length();  i ++){
					JSONObject group;
					try {
						group = (JSONObject) friends.get(i);
						mDataHandler.add(0, getHashMap(group));
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
				if(friend.has("response")){
					pd.dismiss();

				}
				else{
					mDataHandler.add(0, getHashMap(friend));
					mListAdabter.notifyDataSetChanged();
					pd.dismiss();
				}
			}

		});
	}

	/**
	 * Gets the hash map.
	 *
	 * @param group the group
	 * @return the hash map
	 */
	private HashMap<String, String> getHashMap(JSONObject group) {
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


}
