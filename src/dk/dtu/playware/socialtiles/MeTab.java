package dk.dtu.playware.socialtiles;



import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.userdetails.mesubtabs.EventsListTab;
import dk.dtu.playware.socialtiles.userdetails.mesubtabs.FriendsListTab;
import dk.dtu.playware.socialtiles.userdetails.mesubtabs.GroupsListTab;
import dk.dtu.playware.socialtiles.userdetails.mesubtabs.MeDetailTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// TODO: Auto-generated Javadoc
/**
 * The Class MeTab.
 */
public class MeTab extends Fragment {
	
	/** The tab host. */
	private FragmentTabHost mTabHost;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Bundle b = new Bundle();
		b.putString("key", "Friednds");
		
		mTabHost = new FragmentTabHost(getActivity());
		mTabHost.setup(getActivity(), getChildFragmentManager(),
				R.id.action_settings);

		
		mTabHost.addTab(mTabHost.newTabSpec("Friends").setIndicator("Friends"),
				FriendsListTab.class, b);
		//
		b = new Bundle();
		b.putString("key", "Groups");
		mTabHost.addTab(mTabHost.newTabSpec("Groups")
				.setIndicator("Groups"), GroupsListTab.class, b);
		
		b = new Bundle();
		b.putString("key", "Events");
		mTabHost.addTab(mTabHost.newTabSpec("Events")
				.setIndicator("Events"), EventsListTab.class, b);
		
		b = new Bundle();
		b.putString("key", "Me");
		mTabHost.addTab(mTabHost.newTabSpec("Me")
				.setIndicator("Me"), MeDetailTab.class, b);
		
		return mTabHost;
	}	
	
	/**
	 * Instantiates a new me tab.
	 */
	public MeTab(){
		
	}

}
