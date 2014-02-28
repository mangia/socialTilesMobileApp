package dk.dtu.playware.socialtiles.events.create;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateEventForm.
 */
public class CreateEventForm extends Fragment{
	
	/** The change start date btn. */
	private Button changeStartDateBtn ;
	
	/** The change end date btn. */
	private Button changeEndDateBtn; 
	
	/** The change start date txt. */
	private TextView changeStartDateTxt;
	
	/** The change end date txt. */
	private TextView changeEndDateTxt;
	
	/** The char counts view. */
	private TextView charCountsView;
	
	/** The event name. */
	private EditText eventName;
	
	/** The event reward text. */
	private EditText eventRewardText;
	
	/** The event participant type radio. */
	private RadioButton eventParticipantTypeRadio;
	
	/** The Constant Date_dialog_id. */
	public static final int Date_dialog_id = 1;
	
	/** The Constant TAG. */
	private static final String TAG = "SocialTherapyTiles";
	// date and time
	/** The m year. */
	private int mYear;
	
	/** The m month. */
	private int mMonth;
	
	/** The m day. */
	private int mDay;
	
	/** The send data call back. */
	SendDataCallBack sendDataCallBack;

	/**
	 * Instantiates a new creates the event form.
	 */
	public CreateEventForm(){
		
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

	
	/** The m date set listener start. */
	private DatePickerDialog.OnDateSetListener mDateSetListenerStart = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			changeStartDateBtn.setText(new StringBuilder()
			// Month is 0 based so add 1
			.append(mMonth + 1).append("-").append(mDay).append("-")
			.append(mYear));

			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append(mYear).append(",").append(mMonth).append(",").append(mDay);
			sendDataCallBack.onsendData(Tags.startDateId, strBuilder.toString());
		}
	};

	/** The m date set listener end. */
	private DatePickerDialog.OnDateSetListener mDateSetListenerEnd = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			changeEndDateBtn.setText(new StringBuilder()
			// Month is 0 based so add 1
			.append(mMonth + 1).append("-").append(mDay).append("-")
			.append(mYear));
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append(mYear).append(",").append(mMonth).append(",").append(mDay);
			sendDataCallBack.onsendData(Tags.endDateId, strBuilder.toString());
		}
	};
	
	/** The event type. */
	protected String eventType;
	
	/** The reward points. */
	private EditText rewardPoints;
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		// Inflate the layout for this fragment
		View v =inflater.inflate(R.layout.events_create_form_layout, container, false);

		changeStartDateBtn = (Button) v.findViewById(R.id.setStartDate_button_id);
		//changeStartDateTxt = (TextView) v.findViewById(R.id.dateStartText_id);
		changeStartDateBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				DatePickerDialog DPD = new DatePickerDialog(
						getActivity(), mDateSetListenerStart, mYear, mMonth,
						mDay);
				DPD.show();
			} 
		});


		changeEndDateBtn = (Button) v.findViewById(R.id.setEndDate_button_id);
		//changeEndDateTxt = (TextView) v.findViewById(R.id.dateEndText_id);
		changeEndDateBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				DatePickerDialog DPD = new DatePickerDialog(
						getActivity(), mDateSetListenerEnd, mYear, mMonth,
						mDay);
				DPD.show();
			} 
		});

		
		rewardPoints = (EditText) v.findViewById(R.id.reward_points_text);
		rewardPoints.addTextChangedListener(new TextWatcher(){
			public void afterTextChanged(Editable s) {
				sendDataCallBack.onsendData(Tags.rewardPointsId, s.toString());
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

		
		eventName = (EditText)v.findViewById(R.id.enter_event_name_text);

		
		eventName.addTextChangedListener(new TextWatcher(){
			public void afterTextChanged(Editable s) {
				sendDataCallBack.onsendData(Tags.eventNameId, s.toString());
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

		
		charCountsView = (TextView)v.findViewById(R.id.charCounts);
		eventRewardText=(EditText)v.findViewById(R.id.rewardTex_id);
		eventRewardText.addTextChangedListener(new TextWatcher(){
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

		eventParticipantTypeRadio = (RadioButton) v.findViewById(R.id.radio_friends);
		eventParticipantTypeRadio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					RadioButton button = (RadioButton) buttonView;
					Log.d(TAG,button.getText().toString()+"was chosen");
					if(button.getText().toString().equals("Friends")) {eventType  = "0";}
					else{eventType = "1";}
					Log.d(Tags.debugTag,"event type is "+eventType);
					sendDataCallBack.onsendData(Tags.eventParticipantsId,eventType);
				}
			}
		});

		eventParticipantTypeRadio = (RadioButton) v.findViewById(R.id.radio_groups);
		eventParticipantTypeRadio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					RadioButton button = (RadioButton) buttonView;
					Log.d(TAG,button.getText().toString()+"was chosen");
					if(button.getText().toString().equals("Friends")) {eventType  = "0";}
					else{eventType = "1";}
					Log.d(Tags.debugTag,"event type is "+eventType);
					sendDataCallBack.onsendData(Tags.eventParticipantsId,eventType);
				}
			}
		});


		return v;
	}
	
}
