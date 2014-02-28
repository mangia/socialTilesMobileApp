package dk.dtu.playware.socialtiles.groups;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupInfoTab.
 */
public class GroupInfoTab  extends Fragment {
	
	/** The group name. */
	TextView groupName;
	
	/** The group description. */
	TextView groupDescription;
	
	/** The group creator. */
	TextView groupCreator;
	
	/** The group current goals. */
	TextView groupCurrentGoals;
	
	/** The group total goals. */
	TextView groupTotalGoals;
	
	/**
	 * Instantiates a new group info tab.
	 */
	public GroupInfoTab() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.create_event_form_layout);

	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v =inflater.inflate(R.layout.group_info_layout, container, false);
		
		savedInstanceState =getArguments();
		
		Log.d(Tags.debugTag, "bundle is " + savedInstanceState);
		
		groupName = (TextView) v.findViewById(R.id.group_name);
		groupName.setText(savedInstanceState.getString(Tags.name));
		groupCreator = (TextView) v.findViewById(R.id.group_creator);
		groupCreator.setText(savedInstanceState.getString(Tags.first_name)+ " "+savedInstanceState.getString(Tags.last_name) );
		groupDescription = (TextView) v.findViewById(R.id.group_description);
		groupDescription.setText(savedInstanceState.getString(Tags.description));
		groupCurrentGoals = (TextView) v.findViewById(R.id.group_current_goals);
		groupCurrentGoals.setText("3");
		groupTotalGoals = (TextView) v.findViewById(R.id.group_total_goals);
		groupTotalGoals.setText("10");
		
		return v;
	}
	
}
