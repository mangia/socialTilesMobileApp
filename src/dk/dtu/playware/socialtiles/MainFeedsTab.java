package dk.dtu.playware.socialtiles;




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

import dk.dtu.playware.socialtiles.adapters.FeedsAdapter;
import dk.dtu.playware.socialtiles.api.ServerApi;
import dk.dtu.playware.socialtiles.datahandlers.FeedsDataHandler;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class MainFeedsTab.
 */
public class MainFeedsTab extends Fragment {


	/** The char count. Every post has limited amount of characters */
	private TextView charCount;
	
	/** The feeds list. */
	private static ListView feedsList;
	
	/** The post text. */
	private EditText postText;
	
	/** The buttons that uploads the post to the server */
	private Button sendBtn;

	/** The m ac fragment activity. */
	private static FragmentActivity mAcFragmentActivity;

	/** The feeds adapter. */
	private FeedsAdapter feedsAdapter;
	
	/** The info text. */
	private TextView infoText;

	/** The m list strings. */
	public static ArrayList<String> mListStrings;
	
	/** The m data handler. */
	public static FeedsDataHandler mDataHandler;

	/**
	 * Instantiates a new main feeds tab.
	 */
	public MainFeedsTab(){

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
		View v = inflater.inflate(R.layout.feedstab_layout,container, false);
		infoText = (TextView)  v.findViewById(R.id.infoText);
		feedsList = (ListView) v.findViewById(R.id.feedsListview_id);
		feedsList.setEmptyView(emptyTextView);
		charCount = (TextView) v.findViewById(R.id.charCounts_id);
		postText      = (EditText) v.findViewById(R.id.post_id);
		postText.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				charCount.setText(s.length()+"/144");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

		});
		sendBtn = (Button) v.findViewById(R.id.sendBtn_id);
		sendBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HashMap<String, String> data =Tags.createPostHashMap(postText.getText().toString(), Tags.User.name_first, Tags.User.name_last, Tags.User.fbid);
				mDataHandler.add(0,data);
				feedsAdapter.notifyDataSetChanged();
				ServerApi.post(ServerApi.posts, ServerApi.addPost(Tags.User.user_id,Tags.User.entity,postText.getText().toString()), new JsonHttpResponseHandler());
			}
		});
		mDataHandler = new FeedsDataHandler();
		feedsAdapter = new FeedsAdapter(mAcFragmentActivity, mDataHandler.getData());
		feedsList.setAdapter(feedsAdapter);



		return v;
	}


	/**
	 * Fetch the posts.
	 */
	void fetchData(){
		infoText.setVisibility(View.VISIBLE);
		infoText.setText("getting posts...");
		//final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "getting the posts...", true);
		//pd.setCancelable(false);
		Log.d(Tags.debugTag, "fetching posts");
		ServerApi.get(ServerApi.posts, ServerApi.getUserPosts(Tags.User.user_id), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray posts) {

				super.onSuccess(posts);
				Log.d(Tags.debugTag, "posts are "+posts);
				try {
					for(int i =0; i< posts.length(); i ++){
						JSONObject post = posts.getJSONObject(i);
						HashMap<String, String> data =Tags.createPostHashMap(post.getString(Tags.post_text), post.getString(Tags.first_name), 
								post.getString(Tags.last_name), post.getString(Tags.fbid));
						mDataHandler.add(0,data);
					}
					feedsAdapter.notifyDataSetChanged();
					//pd.dismiss();
					infoText.setVisibility(View.GONE);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onSuccess(JSONObject post) {
				super.onSuccess(post);
				HashMap<String, String> data;
				Log.d(Tags.debugTag, "post is "+post);
				try {
					data = Tags.createPostHashMap(post.getString(Tags.post_text), post.getString(Tags.first_name), 
							post.getString(Tags.last_name), post.getString(Tags.fbid));
					mDataHandler.add(0,data);
					feedsAdapter.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});
	}





}
