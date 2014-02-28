package dk.dtu.playware.socialtiles.dialogs;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.adapters.GoalExpandableListAdapter;
import dk.dtu.playware.socialtiles.api.ServerApi;
import dk.dtu.playware.socialtiles.datahandlers.GoalsDataHandler;
import dk.dtu.playware.socialtiles.tags.Tags;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class GoalListDialog.
 */
public class GoalListDialog extends DialogFragment {
	
	/** The m ac fragment activity. */
	private static FragmentActivity mAcFragmentActivity;

	/** The m data handler. */
	private static GoalsDataHandler mDataHandler;
	
	/** The goal adapter. */
	private GoalExpandableListAdapter goalAdapter;
	
	/** The m add goal button fragment. */
	private Button mAddGoalButtonFragment;
	
	/** The goals list. */
	private static ExpandableListView goalsList;


	/** The m goals list data. */
	static ArrayList<HashMap<String, String>> mGoalsListData ;
	
	/** The m users list data. */
	static ArrayList<ArrayList<HashMap<String, String>>>  mUsersListData ;

	/** The params. */
	HashMap<String, String> params;

	/** The info text. */
	private TextView infoText;

	/**
	 * Instantiates a new goal list dialog.
	 */
	public GoalListDialog(){
		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		savedInstanceState =getArguments();
		mAcFragmentActivity = getActivity();
		View v = inflater.inflate(R.layout.goal_expandable_list_goal_dialog_layout,container, false);
		infoText = (TextView)  v.findViewById(R.id.infoText);
		getDialog().setTitle("Goals");
		mAddGoalButtonFragment = (Button) v.findViewById(R.id.add_new_goal);
		mAddGoalButtonFragment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fm = getChildFragmentManager();
				AddGoalDialog addGoalDialog = new AddGoalDialog();
				addGoalDialog.show(fm, "Add a new goal");
			}
		});

		goalsList = (ExpandableListView) v.findViewById(R.id.expandableListViewGoal);
		mDataHandler = new GoalsDataHandler();
		goalAdapter = new GoalExpandableListAdapter(mAcFragmentActivity, mDataHandler.getGoalData(), mDataHandler.getUsersData());
		goalsList.setAdapter(goalAdapter);
		setGroupIndicatorToRight();

		fetchData();

		return v;
	}
	
	/**
	 * Fetch the current users goals.
	 */
	void fetchData(){
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(Tags.op, "achieved_by");
		params.put(Tags.user_id, ""+Tags.User.user_id);
		//final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "getting  your goals...", true);
		infoText.setVisibility(View.VISIBLE);
		infoText.setText("getting  your goals...");

       // pd.setCancelable(false);
		ServerApi.get(ServerApi.goals, new RequestParams(params), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray goals) {
				super.onSuccess(goals);
				Log.d(Tags.debugTag, "goals are "+ goals);
				for(int i =0 ; i< goals.length();  i ++){
					JSONObject goal;
					try {
						goal = (JSONObject) goals.get(i);
						Log.d(Tags.debugTag,"goal is "+goal);
						int pos = mDataHandler.addGroup(getHashMapGoal(goal));
						Log.d(Tags.debugTag, "group pos is "+pos +" group member is "+getHashMapGoal(goal));

						if(pos >-1){
							int k = mDataHandler.addChild(pos, getHashMapUser(goal));
							Log.d(Tags.debugTag, "group pos is "+k +" group member is "+getHashMapUser(goal));
							Log.d(Tags.debugTag,"groups data size is "+mDataHandler.getGoalData().size()+ " childs data size is "+ mDataHandler.getGoalDataEl(pos)+"");

						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				goalAdapter.notifyDataSetChanged();
				//pd.dismiss();
				infoText.setVisibility(View.GONE);

			}

			@Override
			public void onSuccess(JSONObject goal) {
				super.onSuccess(goal);

				Log.d(Tags.debugTag, "goal are "+ goal);
				int pos = mDataHandler.addGroup(getHashMapGoal(goal));
				Log.d(Tags.debugTag, "group pos is "+pos +" group member is "+getHashMapGoal(goal));
				if(pos >-1){
					int k = mDataHandler.addChild(pos, getHashMapUser(goal));
					Log.d(Tags.debugTag, "group pos is "+k +" group member is "+getHashMapUser(goal));
					Log.d(Tags.debugTag,"groups data size is "+mDataHandler.getGoalData().size()+ " childs data size is "+ mDataHandler.getGoalDataEl(pos)+"");
				}	
				goalAdapter.notifyDataSetChanged();
				//pd.dismiss();
				infoText.setVisibility(View.GONE);

			}
		});
	}

	/**
	 * Sets the group indicator to right.
	 */
	private void setGroupIndicatorToRight() {
		/* Get the screen width */
		DisplayMetrics dm = new DisplayMetrics();
		mAcFragmentActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;

		goalsList.setIndicatorBounds(width - getDipsFromPixel(35), width
				- getDipsFromPixel(5));
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

	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAcFragmentActivity = getActivity();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	/**
	 * Gets the hash map goal.
	 *
	 * @param goal the goal
	 * @return the hash map goal
	 */
	public HashMap<String, String> getHashMapGoal(JSONObject goal){
		HashMap<String, String> hm= new HashMap<String, String>();
		try {
			hm.put(Tags.goalName, goal.getString(Tags.name));
			hm.put(Tags.goalExpire, goal.getString(Tags.end_date));
			hm.put(Tags.goal_id, goal.getString(Tags.goal_id));
			return hm;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the hash map user.
	 *
	 * @param goal the goal
	 * @return the hash map user
	 */
	public HashMap<String, String> getHashMapUser(JSONObject goal){
		HashMap<String, String> hm = new HashMap<String, String>() ;
		try {
			hm.put(Tags.profileImg,Tags.facebookApi+ goal.getString(Tags.fbid)+Tags.fbPictureSmall);
			hm.put(Tags.profileName, goal.getString(Tags.first_name)+ " "+ goal.getString(Tags.last_name));
			hm.put(Tags.goalThreshold, goal.getString(Tags.threshold));
			hm.put(Tags.goalProgress, goal.getString(Tags.currently));
			return hm;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
