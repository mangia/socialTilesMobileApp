package dk.dtu.playware.socialtiles.events;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.adapters.UserAdapter;
import dk.dtu.playware.socialtiles.api.ServerApi;
import dk.dtu.playware.socialtiles.datahandlers.UserDataHandler;
import dk.dtu.playware.socialtiles.groups.GroupMainActivity;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class EventsInfoTab.
 */
public class EventsInfoTab extends Fragment {
	
	/** The event name. */
	private TextView eventName;
	
	/** The event creator. */
	private TextView eventCreator;
	
	/** The event end date. */
	private TextView eventEndDate;
	
	/** The event top player. */
	private TextView eventTopPlayer;
	
	/** The event top player img. */
	private ImageView eventTopPlayerImg;
	
	/** The topten list. */
	private static ListView toptenList;
	
	/** The m ac fragment activity. */
	private static FragmentActivity mAcFragmentActivity;
	
	/** The user adapter. */
	private UserAdapter userAdapter;
	
	/** The event_entity. */
	private int event_entity;
	
	/** The event_id. */
	private int event_id;

	/** The m data handler. */
	private static UserDataHandler mDataHandler;
	
	/**
	 * Instantiates a new events info tab.
	 */
	public EventsInfoTab(){

	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.create_event_form_layout);

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
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		savedInstanceState =getArguments();
		
		View v =inflater.inflate(R.layout.events_info_layout, container, false);
		mAcFragmentActivity = getActivity();
		event_entity = Integer.parseInt(savedInstanceState.getString(Tags.entity));
		event_id     = Integer.parseInt(savedInstanceState.getString(Tags.event_id));
		eventName = (TextView) v.findViewById(R.id.event_name);
		eventName.setText(savedInstanceState.getString(Tags.name));
		eventCreator = (TextView) v.findViewById(R.id.event_creator);
		eventCreator.setText("Manos Giannisakis");
		eventEndDate = (TextView) v.findViewById(R.id.event_end_date);
		eventEndDate.setText(savedInstanceState.getString(Tags.end_date));
		eventTopPlayer = (TextView) v.findViewById(R.id.event_top_player_name);
		eventTopPlayer.setText("Manos Giannisakis");
		eventTopPlayerImg = (ImageView) v.findViewById(R.id.event_top_player_image);
		UrlImageViewHelper.setUrlDrawable(eventTopPlayerImg,Tags.myProfilimg, R.drawable.loading);
		toptenList = (ListView) v.findViewById(R.id.top_ten_players_listview);

		
		mDataHandler = new UserDataHandler();
		userAdapter = new UserAdapter(mAcFragmentActivity, mDataHandler.getData(), true);
		toptenList.setAdapter(userAdapter);

		fetchData();

		return v;
	}

	
	/**
	 * Fetching the events participants.
	 */
	private void fetchData(){
		HashMap<String, String> params =  new HashMap<String, String>();
		params.put(Tags.entity, ""+event_entity);
		params.put(Tags.op, "event_topten");
		ServerApi.get(ServerApi.eventParticipants, ServerApi.getMembers(event_id), new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONArray members) {
				super.onSuccess(members);
				int count = 0;
				for(int i =0 ; i< members.length();  i ++){
					if(count>=10){break;}
					JSONObject member;
					try {
						member = (JSONObject) members.get(i);
						mDataHandler.add(0, getHashMap(member));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				userAdapter.notifyDataSetChanged();
				count ++;
			}

			@Override
			public void onSuccess(JSONObject member) {
				super.onSuccess(member);
				//if(member.has(name))
				mDataHandler.add(0, getHashMap(member));
				userAdapter.notifyDataSetChanged();
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
			Log.d(Tags.debugTag, "hm is "+hm);
			return hm;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
}
