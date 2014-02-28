package dk.dtu.playware.socialtiles.dialogs;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class AddGoalDialog.
 */
public class AddGoalDialog extends DialogFragment {

	/** The add goal listener. */
	private AddGoalListener addGoalListener;

	/** The m goal name text. */
	private EditText mGoalNameText;
	
	/** The m game goal text. */
	private EditText mGameGoalText;
	
	/** The m game goal reward text. */
	private EditText mGameGoalRewardText;

	/** The m game goal ending. */
	private TextView mGameGoalEnding;

	/** The m game name spinner. */
	private Spinner mGameNameSpinner;
	
	/** The m game type spinner. */
	private Spinner mGameTypeSpinner;

	/** The m add goal button. */
	private Button mAddGoalButton;
	
	/** The change start date btn. */
	private Button changeStartDateBtn;
	
	/** The change end date btn. */
	private Button changeEndDateBtn;

	/** The m year. */
	private int mYear;
	
	/** The m month. */
	private int mMonth;
	
	/** The m day. */
	private int mDay;

	/** The m game goal string. */
	private String mGameGoalString = "2";
	
	/** The m game goal reward string. */
	private String mGameGoalRewardString = "200";
	
	/** The m game name string. */
	private String mGameNameString = "";
	
	/** The start date. */
	private String startDate = "";
	
	/** The end date. */
	private String endDate = "";
	
	/** The m goal name string. */
	private String mGoalNameString = "";
	
	/** The m game type string. */
	private String mGameTypeString = "1";

	/** The df. */
	private SimpleDateFormat df;


	/**
	 * The listener interface for receiving addGoal events.
	 * The class that is interested in processing a addGoal
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addAddGoalListener<code> method. When
	 * the addGoal event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see AddGoalEvent
	 */
	public interface AddGoalListener{

		/**
		 * Adds the goal.
		 *
		 * @param hm the hm
		 */
		void addGoal(HashMap<String, String> hm);
	}



	/**
	 * Instantiates a new adds the goal dialog.
	 */
	public AddGoalDialog() {

	}


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			addGoalListener = (AddGoalListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement AddGoalListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().setTitle("Add a new goal");

		df = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());

		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		View v = inflater.inflate(R.layout.set_new_goal_layoout, container);

		mGoalNameText = (EditText) v.findViewById(R.id.goal_name);
		mGoalNameText.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
				mGoalNameString = s.toString();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
		});

		mGameGoalText = (EditText) v.findViewById(R.id.game_goal_text);
		mGameGoalText.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
				mGameGoalString = s.toString();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
		});

		mGameGoalRewardText = (EditText) v
				.findViewById(R.id.game_goal_reward_text);
		mGameGoalRewardText.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
				mGameGoalRewardString = s.toString();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
		});

		mGameGoalEnding = (TextView) v.findViewById(R.id.game_goal_ending_text);

		mGameNameSpinner = (Spinner) v.findViewById(R.id.game_name_spinner);
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				Tags.gameNamesAll);
		mGameNameSpinner.setAdapter(spinnerAdapter);
		mGameNameSpinner
		.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent,
					View view, int pos, long id) {
				mGameNameString = parent.getItemAtPosition(pos)
						.toString();
			}

			public void onNothingSelected(AdapterView<?> parent) {
				mGameNameString = Tags.gameNamesAll[0];
			}
		});

		mGameTypeSpinner = (Spinner) v.findViewById(R.id.game_type_spinner);
		spinnerAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, Tags.goalType);
		mGameTypeSpinner.setAdapter(spinnerAdapter);
		mGameTypeSpinner
		.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent,
					View view, int pos, long id) {
				mGameTypeString = parent.getItemAtPosition(pos)
						.toString();
				if (mGameTypeString.equals(Tags.goalType[0])) {
					mGameGoalEnding.setText("minutes");
				} else {
					mGameGoalEnding.setText("points");
				}
			}

			public void onNothingSelected(AdapterView<?> parent) {
				mGameTypeString = Tags.goalType[0];
				mGameGoalEnding.setText("minutes");
			}
		});

		mAddGoalButton = (Button) v.findViewById(R.id.add_goal_button);
		mAddGoalButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (addGoal()) {
					if(mGameTypeString.equals(Tags.goalType[0])){
						mGameTypeString = "1";
					}
					else{
						mGameTypeString = "0";
					}
					HashMap<String, String> hm = new HashMap<String, String>();
					hm.put(Tags.reward_points, mGameGoalRewardString);

					hm.put(Tags.start_date, startDate);
					hm.put(Tags.end_date, endDate);
					hm.put(Tags.name, mGoalNameString);
					hm.put(Tags.game_name, mGameNameString);
					hm.put(Tags.goal_type, mGameTypeString );
					hm.put(Tags.threshold, mGameGoalString);
					hm.put(Tags.reward_points, mGameGoalRewardString);

					Log.d(Tags.debugTag, "Goal info is "+hm);
					addGoalListener.addGoal(hm);
					AddGoalDialog.this.dismiss();
				}
			}

		});

		changeStartDateBtn = (Button) v
				.findViewById(R.id.setStartDate_button_id);
		// changeStartDateTxt = (TextView)
		// v.findViewById(R.id.dateStartText_id);
		changeStartDateBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DatePickerDialog DPD = new DatePickerDialog(getActivity(),
						mDateSetListenerStart, mYear, mMonth, mDay);
				DPD.show();
			}
		});

		changeEndDateBtn = (Button) v.findViewById(R.id.setEndDate_button_id);
		// changeEndDateTxt = (TextView) v.findViewById(R.id.dateEndText_id);
		changeEndDateBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DatePickerDialog DPD = new DatePickerDialog(getActivity(),
						mDateSetListenerEnd, mYear, mMonth, mDay);
				DPD.show();
			}
		});

		return v;
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

			//StringBuilder strBuilder = new StringBuilder();

			startDate = df.format(new Date(mYear, mMonth+1, mDay));//year month day

			//			startDate = strBuilder.append(mYear).append(",").append(mMonth + 1)
			//					.append(",").append(mDay).toString();
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
			//StringBuilder strBuilder = new StringBuilder();
			endDate = df.format(new Date(mYear, mMonth+1, mDay));
			//					strBuilder.append(mYear).append(",").append(mMonth + 1)
			//					.append(",").append(mDay).toString();
		}
	};

	/**
	 * Adds the goal.
	 *
	 * @return true, if successful
	 */
	public boolean addGoal() {
		String errorString = "";

		if (mGameGoalString.equals("")) {
			errorString += "Please specify the goal\n";
		}
		if (mGameGoalRewardString.equals("")) {
			errorString += "Please specify the reward of the goal\n";
		}
		if (mGameNameString.equals("")) {
			errorString += "Please choose a game \n";
		}
		if (startDate.equals("")) {
			errorString += "Please choose a start date \n";
		}
		if (endDate.equals("")) {
			errorString += "Please choose an end date\n";
		}
		if (mGoalNameString.equals("")) {
			errorString += "Please spectify the name of the goal\n";
		}
		if (mGameTypeString.equals("")) {
			errorString += "Please choose a type of goal";
		}

		if (errorString.equals("")) {
			return true;
		}


		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());

		// set title
		alertDialogBuilder.setTitle("Process couldn't be completed");

		// set dialog message
		alertDialogBuilder
		.setMessage(errorString)
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

		return false;
	}

}
