package dk.dtu.playware.socialtiles.friends;

import java.util.HashMap;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.api.ServerApi;
import dk.dtu.playware.socialtiles.dialogs.HighScoresDialog;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class FriendMainPage.
 */
public class FriendMainPage extends FragmentActivity {

	/** The img. */
	private ImageView img ;
	
	/** The user_name. */
	private TextView user_name;
	
	/** The user_score. */
	private TextView user_score;
	
	/** The friend request button. */
	private Button friendRequestButton;
	
	/** The show statistics button. */
	private Button showStatisticsButton;


	/** The fbid. */
	private String  fbid ;
	
	/** The name_first. */
	private String name_first;
	
	/** The name_last. */
	private String name_last;
	
	/** The total_score. */
	private String total_score ;
	
	/** The user_id. */
	private String user_id;
	
	/** The is friend. */
	private Object isFriend;

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_main_page);
		Bundle args = getIntent().getExtras();

		fbid = args.getString(Tags.fbid);
		name_first = args.getString(Tags.first_name);
		name_last  = args.getString(Tags.last_name);
		total_score = args.getString(Tags.total_score);
		user_id     = args.getString(Tags.user_id);
		isFriend   = args.getString(Tags.isFriend);


		img =(ImageView) findViewById(R.id.user_image);
		UrlImageViewHelper.setUrlDrawable(img,Tags.facebookApi+fbid+Tags.fbPictureNormal, R.drawable.loading);
		user_name = (TextView) findViewById(R.id.user_name);
		user_score = (TextView)findViewById(R.id.user_score);

		Log.d(Tags.debugTag, "fb image: "+Tags.facebookApi+fbid+Tags.fbPictureNormal);
		Log.d(Tags.debugTag,"user name : "+name_first+ " "+name_last);
		Log.d(Tags.debugTag, "user score "+total_score);

		user_name.setText(name_first+ " "+name_last);
		user_score.setText(total_score);

		friendRequestButton = (Button) findViewById(R.id.friend_request_button);
		if(isFriend.equals("true")){
			setUnfriendButton();
		}
		else{
			setSendFriendRequestButton();
		}
		showStatisticsButton = (Button) findViewById(R.id.show_statistics_button);
		showStatisticsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fm = getSupportFragmentManager();
				HighScoresDialog hs = new HighScoresDialog();
				hs.setUserId(Integer.parseInt(user_id));
				hs.show(fm, "Highscores");
			}
		});


	}

	/**
	 * Sets the unfriend button.
	 */
	private void setUnfriendButton(){
		friendRequestButton.setText("Unfriend");
		friendRequestButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * Sets the send friend request button.
	 */
	private void setSendFriendRequestButton(){
		friendRequestButton.setText("Send friend request");
		friendRequestButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				friendRequestButton.setClickable(false);
				friendRequestButton.setText("Request Sent");
				HashMap<String, String> hm =  new HashMap<String, String>();
				hm.put("from_user", ""+Tags.User.user_id);
				hm.put("to_user", user_id);
				ServerApi.post(ServerApi.friends,new RequestParams(hm), new JsonHttpResponseHandler(){
					@Override
					public void onSuccess(JSONArray arg0) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0);
						friendRequestButton.setClickable(true);
						setUnfriendButton();
					}
				});

			}
		});
	}


	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friend_main_page, menu);
		return true;
	}

}
