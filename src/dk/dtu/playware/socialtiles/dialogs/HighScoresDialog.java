package dk.dtu.playware.socialtiles.dialogs;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.adapters.SimpleText2ItemsAdapter;
import dk.dtu.playware.socialtiles.api.ServerApi;
import dk.dtu.playware.socialtiles.datahandlers.UserDataHandler;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class HighScoresDialog.
 */
public class HighScoresDialog extends DialogFragment {

	/** The list view. */
	private ListView listView;
	
	/** The m data handler. */
	private UserDataHandler mDataHandler;
	
	/** The simple adapter. */
	private SimpleText2ItemsAdapter simpleAdapter;
	
	/** The user_id. */
	private int user_id;
	
	/** The info text. */
	private TextView infoText;
	
	/**
	 * Instantiates a new high scores dialog.
	 */
	public HighScoresDialog(){
		
	}
	
	/**
	 * Sets the user id.
	 *
	 * @param user_id the new user id
	 */
	public void setUserId(int user_id){
		this.user_id = user_id;
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().setTitle("Highscores");
		
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.listview_layout,null);
		infoText = (TextView)  v.findViewById(R.id.infoText);
		listView = (ListView) v.findViewById(R.id.listView1);
		mDataHandler = new UserDataHandler();
		simpleAdapter = new SimpleText2ItemsAdapter(getActivity(), mDataHandler.getData());
		listView.setAdapter(simpleAdapter);
		fetchData();
		return v;
	}

	/**
	 * Fetch the current users highscores .
	 */
	private void fetchData(){
		/*final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "getting your highscores...", true);
		pd.setCancelable(false);*/
		infoText.setText("getting your highscores...");
		infoText.setVisibility(View.VISIBLE);
		ServerApi.get(ServerApi.highscores, new RequestParams(Tags.user_id, ""+user_id), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray higscores) {
				// TODO Auto-generated method stub
				super.onSuccess(higscores);
				Log.d(Tags.debugTag, "highscores are "+higscores);
				for(int i=0; i<higscores.length(); i++){
					try {
						JSONObject higscore = (JSONObject) higscores.get(i);
						HashMap<String, String> hm =  new HashMap<String, String>();
						hm.put(Tags.item1, higscore.get("gamename").toString());
						hm.put(Tags.item2, higscore.get("highscore").toString());
						mDataHandler.add(hm);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				simpleAdapter.notifyDataSetChanged();
//				pd.dismiss();
				infoText.setVisibility(View.GONE);
			}
		});
	}
}
