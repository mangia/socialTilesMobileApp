package dk.dtu.playware.socialtiles.dialogs;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.tags.Tags;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class FBLoginDialog.
 */
public class FBLoginDialog extends DialogFragment {

	/** The ui helper. */
	private UiLifecycleHelper uiHelper;
	
	/** The fb login listener. */
	private FBLoginListener fbLoginListener;
	
	/** The callback. */
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state, final Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	/**
	 * The listener interface for receiving FBLogin events.
	 * The class that is interested in processing a FBLogin
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addFBLoginListener<code> method. When
	 * the FBLogin event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see FBLoginEvent
	 */
	public interface FBLoginListener{

		/**
		 * On logged in.
		 *
		 * @param user the user
		 */
		void onLoggedIn(GraphUser user);
	}

	/**
	 * Instantiates a new fB login dialog.
	 */
	public FBLoginDialog(){

	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		View v = inflater.inflate(R.layout.facebook_login, container);
		LoginButton authButton = (LoginButton) v.findViewById(R.id.login_facebook_btn_login);
		authButton.setFragment(this);
		//authButton.setReadPermissions(Tags.PERMISSIONS);
		return v;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onCreate(android.os.Bundle)
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
	 * @see android.support.v4.app.DialogFragment#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			fbLoginListener = (FBLoginListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement FBLoginistener");
		}
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
			Log.i(Tags.debugTag, "Logged in...");

			Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

				@Override
				public void onCompleted(GraphUser user, Response response) {
					// TODO Auto-generated method stub
					if (user != null) {
						fbLoginListener.onLoggedIn(user);
						FBLoginDialog.this.dismiss();
					}
				}
			});


		} else if (state.isClosed()) {
			Log.i(Tags.debugTag, "Logged out...");
		}
	}
}
