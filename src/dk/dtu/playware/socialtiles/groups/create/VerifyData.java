package dk.dtu.playware.socialtiles.groups.create;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
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

import com.google.gson.JsonArray;
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
		 * Gets the group info.
		 *
		 * @return the group info
		 */
		public HashMap<String, String> getGroupInfo();
		
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
				// TODO Auto-generated method stub
				String errorMessage  = sendDataCallBack.sendConfirm();
				if(!errorMessage.equals("")){
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
				else{
					HashMap<String, String> hm = sendDataCallBack.getGroupInfo();
					/*final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "creating the group...", true);
			        pd.setCancelable(false);*/
					
					infoText.setVisibility(View.VISIBLE);
					infoText.setText("creating the group...");
					
					ServerApi.post(ServerApi.groups,ServerApi.addGroup(hm.get(Tags.name),hm.get(Tags.description)), new JsonHttpResponseHandler(){
						@Override	
						public void onSuccess(JSONObject group) {
							try {
								String group_id = group.getString(Tags.group_id);
								List<HashMap<String, String>> participants = sendDataCallBack.getParticipants();
								ArrayList<String > user_ids = new ArrayList<String>();
								for(int i= 0 ; i< participants.size(); i++){
									user_ids.add(participants.get(i).get(Tags.user_id));
								}
								//Log.d(Tags.debugTag, "array is "+ array.toString());
								ServerApi.post(ServerApi.groupMember, ServerApi.addGroupMembers(group_id, Tags.getList(participants, Tags.user_id)), new JsonHttpResponseHandler(){
									@Override
									public void onSuccess(String arg0) {
										super.onSuccess(arg0);

										Log.d(Tags.debugTag, "string is "+arg0);
										//pd.dismiss();
										infoText.setVisibility(View.GONE);
										getActivity().finish();
									};
									
									@Override
									public void onSuccess(JSONObject arg0) {
										super.onSuccess(arg0);

										Log.d(Tags.debugTag, "string is "+arg0.toString());
										//pd.dismiss();
										infoText.setVisibility(View.GONE);
										getActivity().finish();
									};
								});
							} catch (JSONException e) {
								// TODO Auto-generated catch block
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
