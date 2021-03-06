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
import dk.dtu.playware.socialtiles.groups.GroupMainActivity;
import dk.dtu.playware.socialtiles.groups.create.GroupCreateMainActivity;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupsListTab.
 */
public class GroupsListTab extends Fragment   {

	/** The fragment activity. */
	private static FragmentActivity mAcFragmentActivity;
	
	/** The groups list. */
	private static ListView groupsList;
	
	/** The new group btn. */
	private Button newGroupBtn;
	
	/** The simple adapter. */
	private SimpleTextAdapter simpleAdapter;
	
	/** The info text. */
	private TextView infoText;
	
	/** The m data handler. */
	public static UserDataHandler mDataHandler;

	/**
	 * Instantiates a new groups list tab.
	 */
	public GroupsListTab(){}


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

		groupsList = (ListView) v.findViewById(R.id.listView1);
		groupsList.setEmptyView(emptyTextView);

		newGroupBtn = (Button) v.findViewById(R.id.button1);
		newGroupBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mAcFragmentActivity, GroupCreateMainActivity.class);
				startActivity(intent);	
			}
		});
		newGroupBtn.setText("New Group");



		mDataHandler = new UserDataHandler();

		simpleAdapter = new SimpleTextAdapter(mAcFragmentActivity, mDataHandler.getData(), Tags.name);
		groupsList.setAdapter(simpleAdapter);
		groupsList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent = new Intent(mAcFragmentActivity.getApplicationContext(), GroupMainActivity.class);
				Log.d(Tags.debugTag,"arguments are : "+createArgs(mDataHandler.getDataEl(position)));
				//intent.putExtras(createArgs(mDataHandler.getDataEl(position)));

				HashMap<String , String> hm  = mDataHandler.getDataEl(position);

				intent.putExtra(Tags.entity, hm.get(Tags.entity));
				intent.putExtra(Tags.creator, hm.get(Tags.creator));
				intent.putExtra(Tags.group_id, hm.get(Tags.group_id));
				intent.putExtra(Tags.name, hm.get(Tags.name));
				intent.putExtra(Tags.date_created, hm.get(Tags.date_created));
				intent.putExtra(Tags.description, hm.get(Tags.description));
				intent.putExtra(Tags.first_name, hm.get(Tags.first_name));
				intent.putExtra(Tags.last_name, hm.get(Tags.last_name));
				intent.putExtra(Tags.fbid, hm.get(Tags.fbid));
				startActivity(intent);	
			}

		});
		

		mDataHandler.clear();
		fetchData();

		return v;
	}


	@Override
	public void onResume() {
		super.onResume();
		
		Log.d(Tags.debugTag, "on resume");
		fetchData();
	}

	/**
	 * Fetch group info
	 */
	private void fetchData(){
		/*final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "getting your groups...", true);
		pd.setCancelable(false);*/
		Log.d(Tags.debugTag,"fetching groups...");
		
		infoText.setVisibility(View.VISIBLE);
		infoText.setText("getting your groups...");
		
		ServerApi.get(ServerApi.groups, ServerApi.getUserGroup(), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray groups) {
				super.onSuccess(groups);
				mDataHandler.clear();
				simpleAdapter.notifyDataSetChanged();
				Log.d(Tags.debugTag,"got some groups with length: "+ groups.length()+ " "+ groups);
				

				for(int i =0 ; i< groups.length();  i ++){
					JSONObject group;
					try {
						group = (JSONObject) groups.get(i);
						Log.d(Tags.debugTag, "group is : " + group);
						mDataHandler.add(getHashMap(group));

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				simpleAdapter.notifyDataSetChanged();
				//pd.dismiss();
				infoText.setVisibility(View.GONE);
			}

			@Override
			public void onSuccess(JSONObject group) {
				super.onSuccess(group);
				Log.d(Tags.debugTag,"got some group: "+ group);
				if(group.has("response")){
					//pd.dismiss();
					infoText.setVisibility(View.GONE);
				} 
				else{
					Log.d(Tags.debugTag,"I am here...., group is "+ group);
					mDataHandler.add(0, getHashMap(group));
					simpleAdapter.notifyDataSetChanged();
					//pd.dismiss();
					infoText.setVisibility(View.GONE);
				}
			}

			@Override
			public void onSuccess(String arg0) {
				super.onSuccess(arg0);				
				Log.d(Tags.debugTag,"got a string " + arg0);
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

	/**
	 * Creates the arguments .
	 *
	 * @param hm the hm
	 * @return the bundle
	 */
	private Bundle createArgs(HashMap<String, String> hm){
		Bundle args = new Bundle();

		args.putString(Tags.entity, hm.get(Tags.entity));
		args.putString(Tags.creator, hm.get(Tags.creator));
		args.putString(Tags.group_id, hm.get(Tags.group_id));
		args.putString(Tags.name, hm.get(Tags.name));
		args.putString(Tags.date_created, hm.get(Tags.date_created));
		args.putString(Tags.description, hm.get(Tags.description));

		return args;
	}

}