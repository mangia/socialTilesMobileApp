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

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.adapters.SimpleTextAdapter;
import dk.dtu.playware.socialtiles.api.ServerApi;
import dk.dtu.playware.socialtiles.datahandlers.UserDataHandler;
import dk.dtu.playware.socialtiles.events.EventsMainActivity;
import dk.dtu.playware.socialtiles.events.create.EventsCreateMainActivity;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class EventsListTab.
 */
public class EventsListTab extends Fragment{

	/** The m ac fragment activity. */
	private static FragmentActivity mAcFragmentActivity;
	
	/** The events list. */
	private static ListView eventsList;
	
	/** The new group btn. */
	private Button newGroupBtn;
	
	/** The m data handler. */
	public static UserDataHandler mDataHandler;
	
	/** The simple adapter. */
	public SimpleTextAdapter simpleAdapter;
	
	/** The info text. */
	private TextView infoText;
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAcFragmentActivity = getActivity();
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		TextView emptyTextView = new TextView(getActivity());
		emptyTextView.setText("no feeds available");
		mAcFragmentActivity = getActivity();
		View v = inflater.inflate(R.layout.button_listview_layout,container, false);
		infoText = (TextView)  v.findViewById(R.id.infoText);

		eventsList = (ListView) v.findViewById(R.id.listView1);
		eventsList.setEmptyView(emptyTextView);

		newGroupBtn = (Button) v.findViewById(R.id.button1);
		newGroupBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mAcFragmentActivity, EventsCreateMainActivity.class);
				startActivity(intent);	
			}
		});
		newGroupBtn.setText("New Event");

		mDataHandler = new UserDataHandler();
		simpleAdapter = new SimpleTextAdapter(mAcFragmentActivity, mDataHandler.getData(), Tags.name);
		eventsList.setAdapter(simpleAdapter);
		eventsList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent = new Intent(mAcFragmentActivity, EventsMainActivity.class);
				HashMap<String , String> hm  = mDataHandler.getDataEl(position);
				
				intent.putExtra(Tags.entity, hm.get(Tags.entity));
				intent.putExtra(Tags.creator, hm.get(Tags.creator));
				intent.putExtra(Tags.event_id, hm.get(Tags.event_id));
				intent.putExtra(Tags.name, hm.get(Tags.name));
				intent.putExtra(Tags.date_created, hm.get(Tags.date_created));
				intent.putExtra(Tags.reward_text, hm.get(Tags.reward_text));
				intent.putExtra(Tags.start_date, hm.get(Tags.start_date));
				intent.putExtra(Tags.end_date, hm.get(Tags.end_date));
				intent.putExtra(Tags.type_of_participants, hm.get(Tags.type_of_participants));
				intent.putExtra(Tags.reward_points, hm.get(Tags.reward_points));
				startActivity(intent);	
			}

		});
		
		fetchData();
		
		return v;
	}

	/**
	 * Fetch data.
	 */
	private void fetchData(){
		Log.d(Tags.debugTag,"fetching events");
		/*final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "getting your events...", true);
        pd.setCancelable(false);*/
		
		infoText.setVisibility(View.VISIBLE);
		infoText.setText("getting your events...");
        
		ServerApi.get(ServerApi.events, ServerApi.getUserEvents(), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray events) {
				super.onSuccess(events);
				Log.d(Tags.debugTag, "events are: "+events);
				for(int i =0 ; i< events.length();  i ++){
					JSONObject event;
					try {
						event = (JSONObject) events.get(i);
						mDataHandler.add(getHashMap(event));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				simpleAdapter.notifyDataSetChanged();
				//pd.dismiss();
				infoText.setVisibility(View.GONE);

			}

			@Override
			public void onSuccess(JSONObject event) {
				super.onSuccess(event);
				Log.d(Tags.debugTag, "event is : "+event);
				mDataHandler.add(getHashMap(event));
				simpleAdapter.notifyDataSetChanged();
				//pd.dismiss();
				infoText.setVisibility(View.GONE);

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
			hm.put(Tags.creator, group.getString(Tags.creator));
			hm.put(Tags.event_id, group.getString(Tags.event_id));
			hm.put(Tags.name, group.getString(Tags.name));
			hm.put(Tags.date_created, group.getString(Tags.date_created));
			hm.put(Tags.reward_text, group.getString(Tags.reward_text));
			hm.put(Tags.start_date, group.getString(Tags.start_date));
			hm.put(Tags.end_date, group.getString(Tags.end_date));
			hm.put(Tags.type_of_participants, group.getString(Tags.type_of_participants));
			hm.put(Tags.reward_points, group.getString(Tags.reward_points));
			return hm;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}






}