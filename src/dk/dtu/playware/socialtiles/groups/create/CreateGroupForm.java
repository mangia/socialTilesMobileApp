package dk.dtu.playware.socialtiles.groups.create;

import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.events.create.CreateEventForm.SendDataCallBack;
import dk.dtu.playware.socialtiles.tags.Tags;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateGroupForm.
 */
public class CreateGroupForm extends Fragment {

	/** The send data call back. */
	private SendDataCallBack sendDataCallBack;
	
	/** The group name. */
	private EditText groupName;
	
	/** The group description. */
	private EditText groupDescription;
	
	/** The char counts view. */
	private TextView charCountsView;

	/**
	 * Instantiates a new creates the group form.
	 */
	public CreateGroupForm(){

	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.create_event_form_layout);

	}

	// Container Activity must implement this interface
	/**
	 * The Interface SendDataCallBack.
	 */
	public interface SendDataCallBack {
		
		/**
		 * Onsend data.
		 *
		 * @param key the key
		 * @param data the data
		 */
		public void onsendData(int key, String data);
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
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v =inflater.inflate(R.layout.group_create_form_layout, container, false);
		groupName = (EditText)v.findViewById(R.id.group_name_edit);
		groupName.addTextChangedListener(new TextWatcher(){
			public void afterTextChanged(Editable s) {
				sendDataCallBack.onsendData(Tags.groupNameId, s.toString());
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
			}
		}); 

		groupDescription = (EditText)v.findViewById(R.id.group_description_edit);
		charCountsView = (TextView)v.findViewById(R.id.charCounts);
		groupDescription.addTextChangedListener(new TextWatcher(){
			public void afterTextChanged(Editable s) {
				charCountsView.setText(String.valueOf(s.length()) + " / 144"  );
				sendDataCallBack.onsendData(Tags.rewardTextId, s.toString());
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
			}
		}); 

		return v;

	}
}
