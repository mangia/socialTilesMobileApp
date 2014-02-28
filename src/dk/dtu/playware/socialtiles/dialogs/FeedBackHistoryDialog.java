package dk.dtu.playware.socialtiles.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.adapters.FeedBackAdapter;
import dk.dtu.playware.socialtiles.api.ServerApi;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class FeedBackHistoryDialog.
 */
public class FeedBackHistoryDialog extends DialogFragment implements FeedBackAdapter.ShareData {
	
	/** The groups data. */
	private ArrayList<HashMap<String, String>> groupsData ;
	
	/** The child data. */
	private ArrayList<ArrayList<HashMap<String, String>>> childData  ;
	
	/** The feedback history. */
	private ExpandableListView feedbackHistory;
	
	/** The m adapter. */
	private FeedBackAdapter mAdapter;


	/** The Constant PERMISSIONS. */
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");

	/** The Constant PENDING_PUBLISH_KEY. */
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";

	/** The pending publish reauthorization. */
	private boolean pendingPublishReauthorization = false;


	/** The ui helper. */
	private UiLifecycleHelper uiHelper;
	
	/** The callback. */
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state, final Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};
	
	/** The publish_title. */
	private String publish_title;
	
	/** The publish_description. */
	private String publish_description;
	
	/** The pendingpublish. */
	private ArrayList<HashMap<String, String>> pendingpublish = new ArrayList<HashMap<String,String>>();
	
	/** The position. */
	private int position;

	/**
	 * Instantiates a new feed back history dialog.
	 */
	public FeedBackHistoryDialog (){

	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		groupsData = new ArrayList<HashMap<String,String>>();
		childData  = new ArrayList<ArrayList<HashMap<String,String>>>();

		View v = inflater.inflate(R.layout.expanable_list_layout, container);
		getDialog().setTitle("Feedback History");
		feedbackHistory = (ExpandableListView) v.findViewById(R.id.expandableListView1);
		mAdapter  = new FeedBackAdapter(getActivity(),this, groupsData, childData);
		feedbackHistory.setAdapter(mAdapter);
		setGroupIndicatorToRight();
		fetchData();
		return v;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onCreate(android.os.Bundle)
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
		//setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();

		// For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		Session session = Session.getActiveSession();
		if (session != null &&
				(session.isOpened() || session.isClosed()) ) {
			onSessionStateChange(session, session.getState(), null);
		}

		uiHelper.onResume();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(PENDING_PUBLISH_KEY, pendingPublishReauthorization);
		uiHelper.onSaveInstanceState(outState);
	}


	/**
	 * Sets the group indicator to right.
	 */
	private void setGroupIndicatorToRight() {
		/* Get the screen width */
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;

		feedbackHistory.setIndicatorBounds(width - getDipsFromPixel(35), width
				- getDipsFromPixel(15));
	}

	/**
	 * Gets the dips from pixel.
	 *
	 * @param pixels the pixels
	 * @return the dips from pixel
	 */
	public int getDipsFromPixel(float pixels) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (pixels * scale + 0.5f);
	}

	/**
	 * Fetching the current user's feedback.
	 */
	private void fetchData(){
		ServerApi.get(ServerApi.feedbacks, new RequestParams(Tags.user_id, Tags.User.user_id), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray feedbacks) {
				// TODO Auto-generated method stub
				super.onSuccess(feedbacks);
				for(int i=0; i< feedbacks.length(); i++){
					try {
						JSONObject feedbackJson  = (JSONObject) feedbacks.get(i);


						HashMap<String, String> hmgroup = new HashMap<String, String>();
						hmgroup.put(Tags.gameDate, feedbackJson.get("date_created").toString());
						hmgroup.put(Tags.gameName, feedbackJson.get("gamename").toString());
						hmgroup.put(Tags.gameScore,	feedbackJson.get("score").toString());


						ArrayList<HashMap<String, String>> feedback = new ArrayList<HashMap<String, String>>();

						feedback.add(addAttribute(Tags.gameName, feedbackJson.get("gamename").toString()));
						feedback.add(addAttribute(Tags.gameDate, feedbackJson.get("date_created").toString()));
						feedback.add(addAttribute(Tags.gameScore, feedbackJson.get("score").toString()));
						feedback.add(addAttribute(Tags.gamePoints, feedbackJson.get("points").toString()));
						feedback.add(addAttribute(Tags.gameDuration, feedbackJson.get("duration").toString()));
						feedback.add(addAttribute(Tags.gameMiss, feedbackJson.get("miss").toString()));
						feedback.add(addAttribute(Tags.gameWinner, feedbackJson.get("winner").toString()));
						feedback.add(addAttribute(Tags.gameLevel, feedbackJson.get("level").toString()));
						feedback.add(addAttribute(Tags.gameNumTiles, feedbackJson.get("size").toString()));
						feedback.add(addAttribute(Tags.gameNumTiles, feedbackJson.get("size").toString()));

						groupsData.add(hmgroup);
						childData.add(feedback);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				mAdapter.notifyDataSetChanged();
			}

			@Override
			public void onSuccess(JSONObject feedbackJson) {
				// TODO Auto-generated method stub
				super.onSuccess(feedbackJson);
				HashMap<String, String> hmgroup = new HashMap<String, String>();
				try {
					hmgroup.put(Tags.gameDate, feedbackJson.get("date_created").toString());
					hmgroup.put(Tags.gameName, feedbackJson.get("gamename").toString());
					hmgroup.put(Tags.gameScore,	feedbackJson.get("score").toString());


					ArrayList<HashMap<String, String>> feedback = new ArrayList<HashMap<String, String>>();

					feedback.add(addAttribute(Tags.gameName, feedbackJson.get("gamename").toString()));
					feedback.add(addAttribute(Tags.gameDate, feedbackJson.get("date_created").toString()));
					feedback.add(addAttribute(Tags.gameScore, feedbackJson.get("score").toString()));
					feedback.add(addAttribute(Tags.gamePoints, feedbackJson.get("points").toString()));
					feedback.add(addAttribute(Tags.gameDuration, feedbackJson.get("duration").toString()));
					feedback.add(addAttribute(Tags.gameMiss, feedbackJson.get("miss").toString()));
					feedback.add(addAttribute(Tags.gameWinner, feedbackJson.get("winner").toString()));
					feedback.add(addAttribute(Tags.gameLevel, feedbackJson.get("level").toString()));
					feedback.add(addAttribute(Tags.gameNumTiles, feedbackJson.get("size").toString()));
					feedback.add(addAttribute(Tags.gameNumTiles, feedbackJson.get("size").toString()));

					groupsData.add(hmgroup);
					childData.add(feedback);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				mAdapter.notifyDataSetChanged();

			}
		});
	}


	/**
	 * Adds the attribute.
	 *
	 * @param attr the attr
	 * @param value the value
	 * @return the hash map
	 */
	private HashMap<String, String> addAttribute(String attr, String value) {
		// TODO Auto-generated method stub
		HashMap<String, String> hm =  new HashMap<String, String>();
		hm.put(Tags.feedback_attribute, attr);
		hm.put(Tags.feedback_value, value);
		return hm;
	}

	/**
	 * sharing data listener  
	 * posts data on facebook and in the main feed
	 */
	@Override
	public void onShareData(ArrayList<HashMap<String, String>> arrayList) {
		publish_title = "Just played a game using the therapy tiles";
		publish_description = "";
		for(int i = 0; i<arrayList.size()-1; i++){
			publish_description += ""+arrayList.get(i).get(Tags.feedback_attribute)+": "+arrayList.get(i).get(Tags.feedback_value)+"\n";
			Log.d(Tags.debugTag, ""+arrayList.get(i).get(Tags.feedback_attribute)+": "+arrayList.get(i).get(Tags.feedback_value));
		}
		HashMap<String, String> hm = new HashMap<String, String>() ;
		hm.put(Tags.title, publish_title);
		hm.put(Tags.description, publish_description);
		hm.put(Tags.published, "false");
		pendingpublish .add(hm);
		publishStory();

	}

	/**
	 * On session state change.
	 *
	 * @param session the session
	 * @param state the state
	 * @param exception the exception
	 */
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
			// shareButton.setVisibility(View.VISIBLE);
			if (Tags.pendingPublishReauthorization && 
					state.equals(SessionState.OPENED_TOKEN_UPDATED)) {
				Tags.pendingPublishReauthorization = false;
				publishStory();
			}
		} else if (state.isClosed()) {
			// shareButton.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * Publish story.
	 */
	private void publishStory() {

		//ServerApi.post(ServerApi.posts, ServerApi.addPost(Tags.User.user_id,Tags.User.entity, publish_title + "\n" + publish_description ), new JsonHttpResponseHandler());


		Session session = Session.getActiveSession();
		if (session != null) {
			for(int i =0 ; i< pendingpublish.size(); i++){
				// Check for publish permissions    
				position = i;
				List<String> permissions = session.getPermissions();
				if (!isSubsetOf(Tags.PERMISSIONS, permissions)) {
					Tags.pendingPublishReauthorization = true;
					Session.NewPermissionsRequest newPermissionsRequest = new Session
							.NewPermissionsRequest(this, Tags.PERMISSIONS);
					session.requestNewPublishPermissions(newPermissionsRequest);
					Log.d(Tags.debugTag,"I am missing the publish token");
					return ;
				}

				Bundle postParams = new Bundle();
				postParams.putString("name", pendingpublish.get(i).get(Tags.title));
				postParams.putString("caption", "Play with the tiles to experiance true fun");
				postParams.putString("description", pendingpublish.get(i).get(Tags.description));
				postParams.putString("picture", "http://www.e-robot.dk/imgscuts/teaching-10.jpg");

				Request.Callback callback= new Request.Callback() {
					public void onCompleted(Response response) {
						JSONObject graphResponse = response
								.getGraphObject()
								.getInnerJSONObject();
						String postId = null;
						try {
							postId = graphResponse.getString("id");
						} catch (JSONException e) {
							Log.i(Tags.debugTag,
									"JSON error "+ e.getMessage());
						}
						FacebookRequestError error = response.getError();
						if (error != null) {
							Toast.makeText(getActivity()
									.getApplicationContext(),
									error.getErrorMessage(),
									Toast.LENGTH_SHORT).show();
						} else {
							pendingpublish.get(position).put(Tags.published, "true");
							Toast.makeText(getActivity()
									.getApplicationContext(), 
									postId,
									Toast.LENGTH_LONG).show();
						}
					}
				};

				Request request = new Request(session, "me/feed", postParams, 
						HttpMethod.POST, callback);

				RequestAsyncTask task = new RequestAsyncTask(request);
				task.execute();
			}
		}

	}

	/**
	 * Checks if is subset of.
	 *
	 * @param subset the subset
	 * @param superset the superset
	 * @return true, if is subset of
	 */
	private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
		for (String string : subset) {
			if (!superset.contains(string)) {
				return false;
			}
		}
		return true;
	}

}
