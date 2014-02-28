package dk.dtu.playware.socialtiles.events;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.adapters.FeedsAdapter;
import dk.dtu.playware.socialtiles.api.ServerApi;
import dk.dtu.playware.socialtiles.datahandlers.FeedsDataHandler;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class FeedsEventsTab.
 */
public class FeedsEventsTab extends Fragment  {

	/** The feeds adapter. */
	private FeedsAdapter feedsAdapter;

	/** The m list strings. */
	public static ArrayList<String> mListStrings;
	
	/** The m data handler. */
	public static FeedsDataHandler mDataHandler;

	/** The char count. */
	private TextView charCount;
	
	/** The feeds list. */
	private static ListView feedsList;
	
	/** The post text. */
	private EditText postText;
	
	/** The send btn. */
	private Button sendBtn;

	/** The event_entity. */
	private int event_entity;

	/** The event_id. */
	private int event_id;

	/** The info text. */
	private TextView infoText;

	/** The m ac fragment activity. */
	private static FragmentActivity mAcFragmentActivity;

	/**
	 * Instantiates a new feeds events tab.
	 */
	public FeedsEventsTab(){

	}



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
		//		View v =inflater.inflate(R.layout.feedstab_layout, container, false);
		
		savedInstanceState =getArguments();
		if(savedInstanceState == null){
			Log.d(Tags.debugTag, "savedInstanceState is null");
		}
		Log.d(Tags.debugTag, "savedInstanceState are "+savedInstanceState);
		
		event_id = Integer.parseInt(savedInstanceState.getString(Tags.event_id));
		event_entity = Integer.parseInt(savedInstanceState.getString(Tags.entity));

		TextView emptyTextView = new TextView(getActivity());
		emptyTextView.setText("no feeds available");
		mAcFragmentActivity = getActivity();
		View v = inflater.inflate(R.layout.feedstab_layout,container, false);
		feedsList = (ListView) v.findViewById(R.id.feedsListview_id);
		feedsList.setEmptyView(emptyTextView);
		infoText = (TextView)  v.findViewById(R.id.infoText);
		charCount = (TextView) v.findViewById(R.id.charCounts_id);
		postText      = (EditText) v.findViewById(R.id.post_id);
		postText.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				charCount.setText(s.length()+"/144");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

		});
		sendBtn = (Button) v.findViewById(R.id.sendBtn_id);
		sendBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HashMap<String, String> data =Tags.createPostHashMap(postText.getText().toString(), Tags.User.name_first, Tags.User.name_last, Tags.User.fbid);
				mDataHandler.add(0,data);
				feedsAdapter.notifyDataSetChanged();
				ServerApi.post(ServerApi.posts, ServerApi.addPost(Tags.User.user_id,event_entity,postText.getText().toString()), new JsonHttpResponseHandler());
			}
		});

		mDataHandler = new FeedsDataHandler();
		feedsAdapter = new FeedsAdapter(mAcFragmentActivity, mDataHandler.getData());
		feedsList.setAdapter(feedsAdapter);

		fetchData();

		return v;
	}
	
	/**
	 * Fetch data.
	 */
	private void fetchData(){
		Log.d(Tags.debugTag, "fetching data");
		/*final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "getting the event's posts...", true);
        pd.setCancelable(false);*/
		
		infoText.setVisibility(View.VISIBLE);
		infoText.setText("getting posts...");
		
		ServerApi.get(ServerApi.posts, ServerApi.getEventPosts(event_entity), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray posts) {

				super.onSuccess(posts);
				try {
					for(int i =0; i< posts.length(); i ++){
						JSONObject post = posts.getJSONObject(i);
						HashMap<String, String> data =Tags.createPostHashMap(post.getString(Tags.post_text), post.getString(Tags.first_name), 
								post.getString(Tags.last_name), post.getString(Tags.fbid));
						mDataHandler.add(0,data);
					}
					feedsAdapter.notifyDataSetChanged();
//					pd.dismiss();
					infoText.setVisibility(View.GONE);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onSuccess(JSONObject post) {
				super.onSuccess(post);
				HashMap<String, String> data;
				try {
					data = Tags.createPostHashMap(post.getString(Tags.post_text), post.getString(Tags.first_name), 
							post.getString(Tags.last_name), post.getString(Tags.fbid));
					mDataHandler.add(0,data);
					feedsAdapter.notifyDataSetChanged();
//					pd.dismiss();
					infoText.setVisibility(View.GONE);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});
	}

	

}
