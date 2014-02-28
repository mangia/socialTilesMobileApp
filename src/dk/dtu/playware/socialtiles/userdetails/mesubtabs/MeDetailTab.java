package dk.dtu.playware.socialtiles.userdetails.mesubtabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.dialogs.FBLoginDialog.FBLoginListener;
import dk.dtu.playware.socialtiles.dialogs.FeedBackHistoryDialog;
import dk.dtu.playware.socialtiles.dialogs.GoalListDialog;
import dk.dtu.playware.socialtiles.dialogs.HighScoresDialog;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class MeDetailTab.
 * This fragment shows user detailes like highscores feedbacks etc etc
 */
public class MeDetailTab extends  Fragment {
	
	/** The img. */
	private ImageView img ;
	
	/** The user_name. */
	private TextView user_name;
	
	/** The user_score. */
	private TextView user_score;
	
	/** The feedback history button. */
	private Button feedbackHistoryButton;
	
	/** The goal list button. */
	private Button goalListButton;
	
	/** The show statistics button. */
	private Button showStatisticsButton;
	
	
	/** The ui helper. */
	private UiLifecycleHelper uiHelper;
	
	/** The callback. */
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state, final Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
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
	 * @see android.support.v4.app.Fragment#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.user_details_layout,null);
		img =(ImageView) v.findViewById(R.id.user_image);
		UrlImageViewHelper.setUrlDrawable(img,Tags.facebookApi+Tags.User.fbid+Tags.fbPictureNormal, R.drawable.loading);
		user_name = (TextView) v.findViewById(R.id.user_name);
		user_score = (TextView) v.findViewById(R.id.user_score);
		
		Log.d(Tags.debugTag, "fb image: "+Tags.facebookApi+Tags.User.fbid+Tags.fbPictureNormal);
		Log.d(Tags.debugTag,"user name : "+Tags.User.name_first+ " "+Tags.User.name_last);
		Log.d(Tags.debugTag, "user score "+Tags.User.total_score);
		
		user_name.setText(Tags.User.name_first+ " "+Tags.User.name_last);
		user_score.setText(""+Tags.User.total_score);
		
		
		feedbackHistoryButton = (Button) v.findViewById(R.id.feedback_history_button);
		feedbackHistoryButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getChildFragmentManager();
				FeedBackHistoryDialog fh = new FeedBackHistoryDialog();
				fh.show(fm, "Feedback History");
			}
		});
		
		goalListButton = (Button) v.findViewById(R.id.goal_list_button);
		goalListButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fm = getChildFragmentManager();
				GoalListDialog gl = new GoalListDialog();
				gl.show(fm, "Goal List");
			}
		});
		
		showStatisticsButton = (Button) v.findViewById(R.id.show_statistics_button);
		showStatisticsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getChildFragmentManager();
				HighScoresDialog hs = new HighScoresDialog();
				hs.setUserId(Tags.User.user_id);
				hs.show(fm, "Highscores");
			}
		});
		
		return v;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	//
	
	/**
	 * On session state change.
	 *
	 * @param session the session
	 * @param state the state
	 * @param exception the exception
	 */
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
			Log.i(Tags.debugTag, "Logged in...");

			Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

				@Override
				public void onCompleted(GraphUser user, Response response) {
					// TODO Auto-generated method stub
					if (user != null) {
					}
				}
			});


		} else if (state.isClosed()) {
			Log.i(Tags.debugTag, "Logged out...");
			getActivity().finish();
		}
	}
}