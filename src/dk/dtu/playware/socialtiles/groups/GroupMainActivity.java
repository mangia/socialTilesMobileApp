package dk.dtu.playware.socialtiles.groups;



import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.Menu;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.api.ServerApi;
import dk.dtu.playware.socialtiles.dialogs.AddGoalDialog;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupMainActivity.
 */
public class GroupMainActivity extends FragmentActivity implements AddGoalDialog.AddGoalListener {
	
	/** The m tab host. */
	private FragmentTabHost mTabHost;
	//private GoalListGroupsTab goalTab;

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle b = getIntent().getExtras();
		if(b == null){
			Log.d(Tags.debugTag, "is null");
		}
		Log.d(Tags.debugTag, "extras are "+b);
		Log.d(Tags.debugTag,""+Integer.parseInt( getIntent().getExtras().getString(Tags.group_id)));
		Log.d(Tags.debugTag,""+Integer.parseInt(getIntent().getExtras().getString(Tags.entity)));
		
		
		setContentView(R.layout.activity_group_main);
		mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent_group);
		
		
        mTabHost.addTab(mTabHost.newTabSpec("Feeds").setIndicator("Feeds"),
                GroupFeedsTab.class, b);
        mTabHost.addTab(mTabHost.newTabSpec("Members").setIndicator("Members"),
        		MemberGroupsTab.class, b);
        mTabHost.addTab(mTabHost.newTabSpec("Goals").setIndicator("Goals"),
        		GoalListGroupsTab.class, b);
        mTabHost.addTab(mTabHost.newTabSpec("Play").setIndicator("Play"),
        		GroupPlayTab.class, b);
        mTabHost.addTab(mTabHost.newTabSpec("Info").setIndicator("Info"),
        		GroupInfoTab.class, b);
       
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.group_main, menu);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onAttachFragment(android.support.v4.app.Fragment)
	 */
	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);

//		if (fragment.getClass() == MainFeedsTab.class) {
//			goalTab = (GoalListGroupsTab)fragment;
//			
//		}
	}
	
	
	/* (non-Javadoc)
	 * @see dk.dtu.playware.socialtiles.dialogs.AddGoalDialog.AddGoalListener#addGoal(java.util.HashMap)
	 */
	@Override
	public void addGoal(HashMap<String, String> hm) {
		HashMap<String, String> params = hm;
		params.put(Tags.date_created, Tags.getDate());
		params.put(Tags.created_for, ""+Integer.parseInt(getIntent().getExtras().getString(Tags.entity)));
		params.put(Tags.op, "multiple");
		
		Log.d(Tags.debugTag, "params are "+params);
		
		ServerApi.post(ServerApi.goals, new RequestParams(params),new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0);
				Log.d(Tags.debugTag, "received string : "+arg0);
				//goalTab.fetchData();
			}
		});
	}
	
}
