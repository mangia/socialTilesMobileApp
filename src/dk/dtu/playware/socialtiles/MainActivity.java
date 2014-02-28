package dk.dtu.playware.socialtiles;


import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import dk.dtu.playware.socialtiles.api.ServerApi;
import dk.dtu.playware.socialtiles.dialogs.AddGoalDialog.AddGoalListener;
import dk.dtu.playware.socialtiles.dialogs.FBLoginDialog;
import dk.dtu.playware.socialtiles.tags.Tags;

import android.view.View.OnTouchListener;


// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 * creates and shows the three tabs (mainfeeds, metab and playtab)
 * as well as implements listeners 
 */
public class MainActivity extends FragmentActivity implements FBLoginDialog.FBLoginListener , AddGoalListener {

	/** The m tab host. */
	private FragmentTabHost mTabHost;
	
	/** The feeds tab. */
	private MainFeedsTab feedsTab;
	//private GraphUser mUser;

	/** Facebook ui helper. */
	private UiLifecycleHelper uiHelper;
	
	/** Facebook callback. */
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state, final Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		//mUser = null;
		feedsTab = null;

		//initializing the tab host
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		mTabHost.addTab(mTabHost.newTabSpec("Feeds").setIndicator("Feeds"),
				MainFeedsTab.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("Play")
				.setIndicator("Play"), PlayTab.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("Me").setIndicator("Me"),
				MeTab.class, null);
		//		mTabHost.addTab(mTabHost.newTabSpec("Requests").setIndicator("Requests"),
		//				RequestsTab.class, null);
		
		
		for(int i =0; i< mTabHost.getChildCount(); i++){
			if(i ==1){
				continue;
			}
			mTabHost.getChildAt(i).setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					Log.d(Tags.debugTag,"my tag is "+mTabHost.getCurrentTabTag());
					Log.d(Tags.debugTag,"current tab is "+mTabHost.getCurrentTab()+" and in total there are "+mTabHost.getChildCount());
					if(mTabHost.getCurrentTab() == 1){
						return false;
					}
					return true;
				}
			});
		}
		
		

	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);

		if (fragment.getClass() == MainFeedsTab.class) {
			feedsTab = (MainFeedsTab)fragment;
			//if(mUser !=null){
			//feedsTab.setUser(mUser);
			//}
		}
	}



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


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}


	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}


	/* 
	 * when the user is logged in through facebook
	 * his basic info are stored so that other classes can have access to them 
	 * and the feeds tab fetches the posts
	 */
	@Override
	public void onLoggedIn(GraphUser user) {
		//mUser = user;

		// if(feedsTab != null){
		ServerApi.post(ServerApi.users, ServerApi.addUserParams(user.getFirstName(), user.getLastName(), user.getId()),new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject userinfo) {
				// Pull out the first event on the public timeline
				Log.d(Tags.debugTag,"json response is :\n"+userinfo+"\n");
				JSONObject user;
				try {
					user = (JSONObject) userinfo;
					String fbid        = user.getString(Tags.fbid);
					String name_first  = user.getString(Tags.first_name);
					String name_last   = user.getString(Tags.last_name);
					int user_id         = user.getInt(Tags.user_id);
					int entity          = user.getInt(Tags.entity);
					int total_score     = user.getInt(Tags.total_score);
					int total_duration  = user.getInt(Tags.total_duration);
					int num_achievments = user.getInt(Tags.num_achievments);

					//Log.d(Tags.debugTag,"user id is :"+user_id +" user entity is "+entity);

					Tags.setUser(fbid, name_first, name_last, user_id, 
							total_score, total_duration, num_achievments, entity);
					if(feedsTab != null){
						feedsTab.fetchData();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}


			}
		});

		//}


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
			Log.i(Tags.debugTag, "I am already logged in...");

			Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

				@Override
				public void onCompleted(GraphUser user, Response response) {
					if (user != null) {
						onLoggedIn(user);
					}
				}
			});

		} else if (state.isClosed()) {
			Log.i(Tags.debugTag,"is not already logged in");
			FragmentManager fm = getSupportFragmentManager();
			FBLoginDialog fbLoginDialog = new FBLoginDialog();
			fbLoginDialog.show(fm, "Login with Facebook");
		}
	}

	/* (non-Javadoc)
	 * @see dk.dtu.playware.socialtiles.dialogs.AddGoalDialog.AddGoalListener#addGoal(java.util.HashMap)
	 */
	@Override
	public void addGoal(HashMap<String, String> hm) {
		HashMap<String, String> params = hm;
		params.put(Tags.date_created, Tags.getDate());
		params.put(Tags.achieved_by, ""+Tags.User.user_id);
		params.put(Tags.created_for, ""+Tags.User.entity);
		params.put(Tags.op, "single");

		Log.d(Tags.debugTag, "params are "+params);

		ServerApi.post(ServerApi.goals, new RequestParams(params),new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(String arg0) {
				super.onSuccess(arg0);
				Log.d(Tags.debugTag, "received string : "+arg0);
				//goalTab.fetchData();
			}
		});

	}



}
