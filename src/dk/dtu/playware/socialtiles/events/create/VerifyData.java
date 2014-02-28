package dk.dtu.playware.socialtiles.events.create;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.api.ServerApi;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class VerifyData.
 */
public class VerifyData extends  Fragment {

	/** The send data call back. */
	private SendDataCallBack sendDataCallBack;
	
	/** The m button. */
	private Button mButton;
	
	/** The info text. */
	private TextView infoText;

	/**
	 * Instantiates a new verify data.
	 */
	public VerifyData(){

	}

	// Container Activity must implement this interface
	/**
	 * The Interface SendDataCallBack.
	 */
	public interface SendDataCallBack {
		
		/**
		 * Send confirm.
		 *
		 * @return the string
		 */
		public String sendConfirm();
		
		/**
		 * Gets the event info.
		 *
		 * @return the event info
		 */
		public HashMap<String, String> getEventInfo();
		
		/**
		 * Gets the participants.
		 *
		 * @return the participants
		 */
		public List<HashMap<String,String>> getParticipants();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			sendDataCallBack = (SendDataCallBack) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implementSendDataCallBack");
		}
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.confirm_button_layout);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v =inflater.inflate(R.layout.confirm_button_layout, container, false);
		
		infoText = (TextView)  v.findViewById(R.id.infoText);
		
		mButton  = (Button) v.findViewById(R.id.confirmButton);
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String errorMessage  = sendDataCallBack.sendConfirm();
				if(!errorMessage.equals("")){ //there is some info missing
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							getActivity());

					// set title
					alertDialogBuilder.setTitle("Process couldn't be completed");

					// set dialog message
					alertDialogBuilder
					.setMessage(errorMessage)
					.setCancelable(false)
					.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close
							// current activity

						}
					});

					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();
				}
				else{ // parse the data and uploads them to the server
					HashMap<String, String> hm = sendDataCallBack.getEventInfo();
					/*final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "creating the event...", true);
			        pd.setCancelable(false);*/
					
					infoText.setVisibility(View.VISIBLE);
					infoText.setText("creating the event...");
					Log.d(Tags.debugTag, ServerApi.events);
					Log.d(Tags.debugTag, ""+hm);
					ServerApi.post(ServerApi.events,ServerApi.addEvent(hm), new JsonHttpResponseHandler(){
						@Override
						public void onSuccess(int arg0, org.json.JSONArray arg1) {};
						
						@Override
						public void onSuccess(org.json.JSONObject event) {
							try {
								String event_id = event.getString(Tags.event_id);
								List<HashMap<String, String>> participants = sendDataCallBack.getParticipants();
								ArrayList<String > pids = new ArrayList<String>();
								for(int i= 0 ; i< participants.size(); i++){
									if(participants.get(i).containsKey(Tags.user_id)){
										pids.add(participants.get(i).get(Tags.user_id));
									}
									else if (participants.get(i).containsKey(Tags.group_id)){
										pids.add(participants.get(i).get(Tags.group_id));
									}
								}
								//Log.d(Tags.debugTag, "array is "+ array.toString());
								if(participants.get(0).containsKey(Tags.user_id)){
									pids.add(""+Tags.User.user_id);
								}
								ServerApi.post(ServerApi.eventParticipants, ServerApi.addEventMembers(event_id, pids), new JsonHttpResponseHandler(){
									@Override
									public void onSuccess(String arg0) {
										super.onSuccess(arg0);
										Log.d(Tags.debugTag, "!!!! string is "+arg0);
										getActivity().finish();
									};
									
									@Override
									public void onSuccess(JSONObject arg0) {
										super.onSuccess(arg0);

										Log.d(Tags.debugTag, "string is "+arg0.toString());
										infoText.setVisibility(View.GONE);
										
										getActivity().finish();
									};
								});
							} catch (JSONException e) {
								e.printStackTrace();
							}
						};
						
					});
				}
			}
		});
		return v;
	}

}
