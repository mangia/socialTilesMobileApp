package dk.dtu.playware.socialtiles.groups.create;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.adapters.ListWithCheckBoxAdapter;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupCreateMainActivity.
 * similar to the event create activity class
 */
public class GroupCreateMainActivity extends FragmentActivity implements CreateGroupForm.SendDataCallBack, ListWithCheckBoxAdapter.SendDataCallBack, VerifyData.SendDataCallBack, FriendsListWithCheckBox.SendDataCallBack {
	
	/** The m page adapter. */
	CreateGroupPageAdapter mPageAdapter ;
	
	/** The m view pager. */
	ViewPager  mViewPager;

	/** The participants list. */
	List<HashMap<String,String>> participantsList;
	
	/** The group name. */
	private String groupName = "";
	
	/** The group description. */
	private String groupDescription = "";

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_create_main);
		participantsList = new ArrayList<HashMap<String,String>>();
		mPageAdapter = new CreateGroupPageAdapter(getSupportFragmentManager());
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();


		actionBar.setTitle("Swipe to proceed ");
		// Specify that the Home button should show an "Up" caret, indicating that touching the
		// button will take the user one step up in the application's hierarchy.
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Set up the ViewPager, attaching the adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager_group);
		mViewPager.setAdapter(mPageAdapter);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.events_create_main, menu);
		return true;
	}

	/**
	 * The Class CreateGroupPageAdapter.
	 */
	public class CreateGroupPageAdapter extends FragmentPagerAdapter{
		
		/** The m titles. */
		ArrayList<String> mTitles = new ArrayList<String>();

		/**
		 * Instantiates a new creates the group page adapter.
		 *
		 * @param fm the fm
		 */
		public CreateGroupPageAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
			mTitles.add("Step 1: Fill the Form");
			mTitles.add("Step 2: Invite Friends");
			mTitles.add("Step 3: Finish");
		}

		/* (non-Javadoc)
		 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
		 */
		@Override
		public Fragment getItem(int i) {
			Fragment fragment=null;
			switch (i){
			case 0:
				fragment = new CreateGroupForm();
				break;
			case 1:
				fragment = new FriendsListWithCheckBox();
				break;
			case 2:
				fragment = new VerifyData();
				break;
			default:
				fragment = new CreateGroupForm();
				break;
			}

			//Bundle args = new Bundle();
			// Our object is just an integer :-P
			//args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
			//fragment.setArguments(args);
			return fragment;
		}

		/* (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#getCount()
		 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
		}

		/* (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return mTitles.get(position);
		}

	}

	/* (non-Javadoc)
	 * @see dk.dtu.playware.socialtiles.adapters.ListWithCheckBoxAdapter.SendDataCallBack#onsendData(int, java.util.HashMap)
	 */
	@Override
	public void onsendData(int key, HashMap<String, String> data) {
		// TODO Auto-generated method stub

		switch(key){
		case Tags.addFriendId:

			participantsList.add(data);
			if(participantsList!=null){
				Log.d(Tags.debugTag, "friend list size is "+participantsList.size() + " and fb id is "+data.get("friendsFbId"));
			}
			break;

		case Tags.removeFriendId:
			participantsList.remove(data);
			if(participantsList!=null){
				Log.d(Tags.debugTag, "friend list size is "+participantsList.size()+ " and fb id is "+data.get("friendsFbId"));
			}
			break;
		}
	}


	/* (non-Javadoc)
	 * @see dk.dtu.playware.socialtiles.adapters.ListWithCheckBoxAdapter.SendDataCallBack#getCheckedButtons()
	 */
	@Override
	public List<HashMap<String,String>> getCheckedButtons() {
		// TODO Auto-generated method stub
		return participantsList;
	}

	/* (non-Javadoc)
	 * @see dk.dtu.playware.socialtiles.groups.create.VerifyData.SendDataCallBack#sendConfirm()
	 */
	@Override
	public String sendConfirm() {
		// TODO Auto-generated method stub
		String errorMessage = "";
		if(groupName .equals("")){
			errorMessage += "Please choose a name or the group\n";
		}
		if(participantsList.isEmpty()){
			errorMessage += "Please Selece some participants\n";
		}
		return errorMessage;
	}

	/* (non-Javadoc)
	 * @see dk.dtu.playware.socialtiles.groups.create.CreateGroupForm.SendDataCallBack#onsendData(int, java.lang.String)
	 */
	@Override
	public void onsendData(int key, String data) {
		// TODO Auto-generated method stub
		Log.d(Tags.debugTag,"received key "+key+" and data "+data);
		switch(key){
		case Tags.groupNameId:
			groupName = data;
			break;
		case Tags.rewardTextId : 
			groupDescription = data;
			break;
		}

	}

	/* (non-Javadoc)
	 * @see dk.dtu.playware.socialtiles.groups.create.VerifyData.SendDataCallBack#getGroupInfo()
	 */
	@Override
	public HashMap<String, String> getGroupInfo() {
		HashMap<String, String> hm =  new HashMap<String, String>();
		hm.put(Tags.name, groupName);
		hm.put(Tags.description, groupDescription);
		return hm;
	}

	/* (non-Javadoc)
	 * @see dk.dtu.playware.socialtiles.groups.create.VerifyData.SendDataCallBack#getParticipants()
	 */
	@Override
	public List<HashMap<String, String>> getParticipants() {
		// TODO Auto-generated method stub
		return participantsList;
	}

	/* (non-Javadoc)
	 * @see dk.dtu.playware.socialtiles.adapters.ListWithCheckBoxAdapter.SendDataCallBack#clearParticipants()
	 */
	@Override
	public void clearParticipants() {
		participantsList.clear();
		
	}
}
