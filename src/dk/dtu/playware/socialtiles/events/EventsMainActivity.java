package dk.dtu.playware.socialtiles.events;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.Menu;
import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class EventsMainActivity.
 */
public class EventsMainActivity extends FragmentActivity {

	/** The tab host. */
	private FragmentTabHost mTabHost;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events_main);

		mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent_events);

		Bundle b = getIntent().getExtras();
		if(b == null){
			Log.d(Tags.debugTag, "is null");
		}
		int type_of_participants = Integer.parseInt(b.getString(Tags.type_of_participants));
		mTabHost.addTab(mTabHost.newTabSpec("Feeds").setIndicator("Feeds"),
				FeedsEventsTab.class, b);
		if(type_of_participants == 1){
			mTabHost.addTab(mTabHost.newTabSpec("Group Members").setIndicator("Group Members"),
					MemberGroupsEventsTab.class, b);
		}
		else{
			mTabHost.addTab(mTabHost.newTabSpec("User Member").setIndicator("User Member"),
					MemberUserEventsTab.class, b);
		}
		mTabHost.addTab(mTabHost.newTabSpec("Event Info").setIndicator("Event Info"),
				EventsInfoTab.class, b);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.events_main, menu);
		return true;
	}

}
