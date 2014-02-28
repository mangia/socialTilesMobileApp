package dk.dtu.playware.socialtiles.events.create;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
 * The Class EventsCreateMainActivity.
 * this class is responsible to communicate with the fragments that are loaded in order to get the information that the user 
 * has provided
 */
public class EventsCreateMainActivity extends FragmentActivity implements CreateEventForm.SendDataCallBack, 
ListWithCheckBoxAdapter.SendDataCallBack, VerifyData.SendDataCallBack, FriendsListWithCheckBox.SendDataCallBack{
	
	/**
	 * The Interface UpdateableFragment.
	 */
	public interface UpdateableFragment {
		   
   		/**
   		 * Update participant type.
   		 *
   		 * @param p_type the p_type
   		 */
   		public void updateParticipantType(String p_type);
	}
	
	/** The m page adapter. */
	CreateEventPageAdapter mPageAdapter ;
	
	/** The m view pager. */
	ViewPager  mViewPager;
	
	/** The event name. */
	String eventName ="";
	
	/** The reward text. */
	String rewardText="Congratulations";
	
	/** The start date. */
	Date startDate = null;
	
	/** The end date. */
	Date endDate   = null;
	
	/** The event participants. */
	String eventParticipants = "1";

	/** The participants list. */
	List<HashMap<String,String>> participantsList;
	
	/** The start date string. */
	private String startDateString = "";
	
	/** The end date string. */
	private String endDateString = "";
	
	/** The reward points. */
	private String rewardPoints = "";
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events_create_main);
		participantsList = new ArrayList<HashMap<String,String>>();
		mPageAdapter = new CreateEventPageAdapter(getSupportFragmentManager());
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();


		actionBar.setTitle("Swipe to proceed ");
		// Specify that the Home button should show an "Up" caret, indicating that touching the
		// button will take the user one step up in the application's hierarchy.
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Set up the ViewPager, attaching the adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
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
	 * The Class CreateEventPageAdapter.
	 */
	public class CreateEventPageAdapter extends FragmentPagerAdapter{
		
		/** The m titles. */
		ArrayList<String> mTitles = new ArrayList<String>();

		/**
		 * Instantiates a new creates the event page adapter.
		 *
		 * @param fm the fm
		 */
		public CreateEventPageAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
			mTitles.add("Step 1: Fill the Form");
			mTitles.add("Step 2: Choose Participants");
			mTitles.add("Step 3: Finish");
		}

		/* (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#getItemPosition(java.lang.Object)
		 */
		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			if(object instanceof FriendsListWithCheckBox){
				((FriendsListWithCheckBox) object).updateParticipantType(eventParticipants);
			}
			return super.getItemPosition(object);
		}


		/* (non-Javadoc)
		 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
		 */
		@Override
		public Fragment getItem(int i) {
			Fragment fragment=null;
			switch (i){
			case 0:
				fragment = new CreateEventForm();
				break;
			case 1:
				fragment = new FriendsListWithCheckBox();
				break;
			case 2:
				fragment = new VerifyData();
				break;
			default:
				fragment = new CreateEventForm();
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
	
	/* 
	 * interface where the fragments send data to the activity
	 * @param key  : the indicator of which kind of data is send  
	 * @param data : the data
	 */
	@Override
	public void onsendData(int key, String data){
		Log.d(Tags.debugTag,"received key "+key+" and data "+data);
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
		String [] dateInfo;
		switch(key){
		case Tags.eventNameId:
			eventName = data;
			break;
		case Tags.rewardTextId : 
			rewardText = data;
			break;
		case Tags.startDateId: 
			dateInfo= data.split(",");
			Tags.getDate();
			startDate = new Date(Integer.parseInt(dateInfo[0]), Integer.parseInt(dateInfo[1]), Integer.parseInt(dateInfo[2]));//year month day
			startDateString = df.format(startDate); 
			break;
		case Tags.endDateId:
			dateInfo = data.split(",");
			endDate = new Date(Integer.parseInt(dateInfo[0]), Integer.parseInt(dateInfo[1]), Integer.parseInt(dateInfo[2]));//year month day
			endDateString = df.format(endDate);; 
			break;
		case Tags.eventParticipantsId: 
			eventParticipants  = data;
			mPageAdapter.notifyDataSetChanged();
			break;
		case Tags.rewardPointsId:
			rewardPoints = data;
			break;
		}
	}

	/* 
	 * the same as the previous function with the difference that the fragment sends an array of data
	 * @param key  : the indicator of which kind of data is send  
	 * @param data : the data
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


	/* 
	 * get the list of the participants that are checked
	 */
	@Override
	public List<HashMap<String,String>> getCheckedButtons() {
		Log.d(Tags.debugTag,"participant list size is "+participantsList.size());
		return participantsList;
	}

	/* (non-Javadoc)
	 * @see dk.dtu.playware.socialtiles.events.create.VerifyData.SendDataCallBack#sendConfirm()
	 */
	@Override
	public String sendConfirm() {
		String errorMessage = "";
		if(eventName.equals("")){
			errorMessage += "Please choose a name or the event\n";
		}
		if(startDate == null){
			errorMessage += "Please select a start date\n";
		}
		if(endDate == null){
			errorMessage += "Please select an end date\n";
		}
		//if(startDate.equals(endDate)){
		//	errorMessage += "start date and end date cannot be the same\n";
		//}
		if(startDate!=null && endDate!=null && startDate.after(endDate)){
			errorMessage +="start date cannot be after end date \n";
		}
		if(participantsList.isEmpty()){
			errorMessage += "Please Selece some participants\n";
		}
		if(rewardPoints.isEmpty()){
			errorMessage+= "Please specify the reward points";
		}
		return errorMessage;
	}

	/* (non-Javadoc)
	 * @see dk.dtu.playware.socialtiles.events.create.VerifyData.SendDataCallBack#getEventInfo()
	 */
	@Override
	public HashMap<String, String> getEventInfo() {
		HashMap<String , String> hm =  new HashMap<String, String>();
		hm.put(Tags.name, eventName);
		hm.put(Tags.type_of_participants,eventParticipants );
		hm.put(Tags.start_date, startDateString);
		hm.put(Tags.end_date, endDateString);
		hm.put(Tags.date_created, Tags.getDate());
		hm.put(Tags.creator, ""+Tags.User.user_id);
		hm.put(Tags.reward_text, rewardText);
		hm.put(Tags.reward_points,rewardPoints);
		return hm;
	}

	/* (non-Javadoc)
	 * @see dk.dtu.playware.socialtiles.events.create.VerifyData.SendDataCallBack#getParticipants()
	 */
	@Override
	public List<HashMap<String, String>> getParticipants() {
		return participantsList;
	}

	/* (non-Javadoc)
	 * @see dk.dtu.playware.socialtiles.events.create.FriendsListWithCheckBox.SendDataCallBack#getParticipantId()
	 */
	@Override
	public String getParticipantId() {
		// TODO Auto-generated method stub
		return eventParticipants;
	}

	/* (non-Javadoc)
	 * @see dk.dtu.playware.socialtiles.adapters.ListWithCheckBoxAdapter.SendDataCallBack#clearParticipants()
	 */
	@Override
	public void clearParticipants() {
		Log.d(Tags.debugTag,"clearing data...");
		participantsList.clear();
		participantsList= new ArrayList<HashMap<String,String>>();
	}

}
