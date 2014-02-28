package dk.dtu.playware.socialtiles.groups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import dk.dtu.playware.socialtiles.BluetoothChatService;
import dk.dtu.playware.socialtiles.R;
import dk.dtu.playware.socialtiles.adapters.FeedBackAdapter;
import dk.dtu.playware.socialtiles.api.ServerApi;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupPlayTab.
 * this was suppose to be a fragment where more than one people could play without connecting and disconnecting thei devices
 * This fragment wasn't tested and it is not fully functional
 */
public class GroupPlayTab extends Fragment implements FeedBackAdapter.ShareData{

	/** The devices spinner. */
	private Spinner devicesSpinner;
	
	/** The scan_connect button. */
	private Button scan_connectButton;
	
	/** The games spinner. */
	private Spinner gamesSpinner;
	
	/** The play button. */
	private Button playButton;
	
	/** The secon player spinner. */
	private Spinner seconPlayerSpinner;
	
	/** The feedback list. */
	private ExpandableListView feedbackList;

	/** The device. */
	private String device;
	
	/** The game. */
	protected String game;
	
	/** The second player. */
	private String secondPlayer;
	
	/** The m adapter. */
	private FeedBackAdapter mAdapter;

	/** The groups data. */
	static ArrayList<HashMap<String, String>> groupsData ;
	
	/** The child data. */
	static ArrayList<ArrayList<HashMap<String, String>>> childData  ;


	/** The m bluetooth adapter. */
	private BluetoothAdapter mBluetoothAdapter;
	
	/** The devices spinner adapter. */
	private ArrayAdapter<String> devicesSpinnerAdapter;
	
	/** The m chat service. */
	private BluetoothChatService mChatService = null;


	/** The pendingpublish. */
	private ArrayList<HashMap<String, String>> pendingpublish = new ArrayList<HashMap<String,String>>();
	
	/** The found devices. */
	private ArrayList<String> foundDevices = new ArrayList<String>();
	
	/** The games spinner adapter. */
	private ArrayAdapter<String> gamesSpinnerAdapter;
	
	/** The m out string buffer. */
	private StringBuffer mOutStringBuffer;
	
	/** The cmd. */
	private String cmd;
	
	/** The point. */
	private String point;
	
	/** The miss. */
	private String miss;
	
	/** The duration. */
	private String duration;
	
	/** The winner. */
	private String winner;
	
	/** The level. */
	private String level;
	
	/** The size. */
	private String size;
	
	/** The receive. */
	private String receive;
	
	/** The your score. */
	private String yourScore;
	
	/** The score. */
	private int score = 0;

	/** The game name. */
	private String Cmd, gameName;
	
	/** The STAR tcmd. */
	private String STARTcmd = "a";
	
	/** The EN dcmd. */
	private String ENDcmd = "z";
	
	/** The current_user. */
	private String current_user;

	/** The ui helper. */
	private UiLifecycleHelper uiHelper;
	
	/** The callback. */
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state, final Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};



	/** The publish_title. */
	private String publish_title;
	
	/** The publish_description. */
	private String publish_description;
	
	/** The current player spinner. */
	private Spinner currentPlayerSpinner;
	
	/** The current player spinner adapter. */
	private ArrayAdapter<String>  currentPlayerSpinnerAdapter;
	
	/** The group_id. */
	private int group_id;
	
	/** The users. */
	private ArrayList<String> users = new ArrayList<String>();

	/**
	 * Instantiates a new group play tab.
	 */
	public GroupPlayTab() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public synchronized void onResume() {
		super.onResume();
		Log.d(Tags.debugTag, "+ ON RESUME +");

		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
		if (mChatService != null) {
			// Only if the state is STATE_NONE, do we know that we haven't started already
			if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
				// Start the Bluetooth chat services
				mChatService.start();
			}
		}

		Session session = Session.getActiveSession();
		if (session != null &&
				(session.isOpened() || session.isClosed()) ) {
			onSessionStateChange(session, session.getState(), null);
		}

		uiHelper.onResume();
	}



	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(Tags.PENDING_PUBLISH_KEY, Tags.pendingPublishReauthorization);
		uiHelper.onSaveInstanceState(outState);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Tags.REQUEST_ENABLE_BT) {
			if (resultCode == Activity.RESULT_OK) {
				//mIsEnable = true;
				//onEnabled();
				setUpBluetooth();
				scan_connectButton.setClickable(true);
			} else {
				//mIsEnable = false;
			}
			//mCallback.onChangeBluetoothState(mIsEnable);
		}
	}


	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		savedInstanceState =getArguments();
		group_id     = Integer.parseInt(savedInstanceState.getString(Tags.group_id));
		groupsData = new ArrayList<HashMap<String,String>>();
		childData  = new ArrayList<ArrayList<HashMap<String,String>>>();

		View v =inflater.inflate(R.layout.play_tab_layout, container, false);
		devicesSpinner = (Spinner) v.findViewById(R.id.devices_spinner);
		devicesSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1);
		devicesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		devicesSpinner.setAdapter(devicesSpinnerAdapter);
		devicesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				device = parent.getItemAtPosition(pos).toString();
				device = device.substring(device.length() - 17);
				Log.d(Tags.debugTag, "Address is "+device);
			}
			public void onNothingSelected(AdapterView<?> parent) {
				device = parent.getItemAtPosition(0).toString();
				device = device.substring(device.length() - 17);
				Log.d(Tags.debugTag,"Address is" + device);
			}
		});
		scan_connectButton = (Button) v.findViewById(R.id.scan_connectButton);
		scan_connectButton.setText("Scan");
		scan_connectButton.setClickable(false);

		scan_connectButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doDiscovery();
			}
		});
		gamesSpinner = (Spinner) v.findViewById(R.id.gamespinner);
		gamesSpinnerAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, Tags.gameNamesCurrent);
		gamesSpinner.setAdapter(gamesSpinnerAdapter);
		gamesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				game = parent.getItemAtPosition(pos).toString();
			}
			public void onNothingSelected(AdapterView<?> parent) {
				game = Tags.gameNamesAll[0];
			}
		});


		currentPlayerSpinner = (Spinner) v.findViewById(R.id.currentPlayer);
		currentPlayerSpinnerAdapter =  new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1);
		currentPlayerSpinner.setVisibility(View.VISIBLE);
		currentPlayerSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		currentPlayerSpinner.setAdapter(currentPlayerSpinnerAdapter);
		fetchData();
		currentPlayerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				current_user = users.get(pos);
				Log.d(Tags.debugTag, "Current user is "+current_user);
			}
			public void onNothingSelected(AdapterView<?> parent) {
				current_user = users.get(0);

				Log.d(Tags.debugTag, "Current user is "+current_user);
			}
		});
		

		playButton   = (Button) v.findViewById(R.id.playButton);
		//playButton.setClickable(false);
		playButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				byte[] start = STARTcmd.getBytes();
				byte[] c = game.getBytes();
				byte[] end = ENDcmd.getBytes();

				mChatService.write(start);
				mChatService.write(c);
				mChatService.write(end);

			}
		});

		seconPlayerSpinner = (Spinner) v.findViewById(R.id.secondPlayer);
		ArrayAdapter<String> spinnerSecondPlayerAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, Tags.profilName_array);
		seconPlayerSpinner.setAdapter(spinnerSecondPlayerAdapter);
		seconPlayerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				secondPlayer = parent.getItemAtPosition(pos).toString();
			}
			public void onNothingSelected(AdapterView<?> parent) {
				secondPlayer = Tags.gameNamesAll[0];
			}
		});


		feedbackList = (ExpandableListView) v.findViewById(R.id.feedbackExpandable);
		mAdapter  = new FeedBackAdapter(getActivity(),this, groupsData, childData);
		feedbackList.setAdapter(mAdapter);
		setGroupIndicatorToRight();


		addFeedback("COLORRACE 1P 10PNT", ""+50, ""+10, ""+40, ""+0, ""+1, ""+1, ""+20);
		addFeedback("COLORRACE 2P 30SEC", ""+50, ""+10, ""+40, ""+0, ""+1, ""+1, ""+20);
		addFeedback("COLORRACE 3P 10PNT", ""+50, ""+10, ""+40, ""+0, ""+1, ""+1, ""+20);

		mAdapter.notifyDataSetChanged();


		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		getActivity().registerReceiver(mReceiver, filter);

		// Register for broadcasts when discovery has finished
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		getActivity().registerReceiver(mReceiver, filter);

		initBluetooth();

		if (savedInstanceState != null) {
			Tags.pendingPublishReauthorization = 
					savedInstanceState.getBoolean(Tags.PENDING_PUBLISH_KEY, false);
		}

		return  v;
	}



	/**
	 * Inits the bluetooth.
	 */
	private void initBluetooth(){
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(mBluetoothAdapter == null){
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					getActivity());
			alertDialogBuilder.setTitle("No bluetooth detected");
			alertDialogBuilder.setMessage("This device do not support bluetooth")
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});
			return;
		}

		if(!mBluetoothAdapter.isEnabled()){
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, Tags.REQUEST_ENABLE_BT);
			scan_connectButton.setClickable(true);
		}
		else{
			//addPairedDevices();
			if (mChatService == null) setUpBluetooth();
		}

	}

	/**
	 * Sets the up bluetooth.
	 */
	private void setUpBluetooth(){
		// Initialize the BluetoothChatService to perform bluetooth connections
		mChatService = new BluetoothChatService(getActivity(), mHandler);

		// Initialize the buffer for outgoing messages
		mOutStringBuffer = new StringBuffer("");
	}

	/**
	 * Adds the paired devices.
	 */
	private void addPairedDevices(){
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

		// If there are paired devices, add each one to the ArrayAdapter
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
				//devicesSpinnerAdapter.add(device.getName() + "\n" + device.getAddress());
				foundDevices.add(device.getName() + "\n" + device.getAddress());
			}
		}
	}

	/**
	 * Do discovery.
	 */
	private void doDiscovery() {
		Log.d(Tags.debugTag, "doDiscovery()");

		// Indicate scanning in the title
		scan_connectButton.setText("Scanning ...");
		addPairedDevices();
		// If we're already discovering, stop it
		if (mBluetoothAdapter.isDiscovering()) {
			mBluetoothAdapter.cancelDiscovery();
		}

		// Request discover from BluetoothAdapter
		mBluetoothAdapter.startDiscovery();
	}

	/**
	 * Adds the feedback.
	 *
	 * @param gamename the gamename
	 * @param score the score
	 * @param points the points
	 * @param duration the duration
	 * @param miss the miss
	 * @param winner the winner
	 * @param level the level
	 * @param numoftiles the numoftiles
	 */
	void addFeedback(String gamename, String score, String points, String duration, String miss, String winner, String level, String numoftiles){

		HashMap<String, String> params = new HashMap<String, String>();
		params.put(Tags.user_id, current_user);
		params.put("gamename", gamename);
		params.put(Tags.date_created, Tags.getDate());
		params.put("points",""+points);
		params.put("miss", ""+miss);
		params.put("duration", ""+duration);
		params.put("winner", ""+winner);
		params.put("level", ""+level);
		params.put("size", ""+numoftiles);
		params.put("score", score);


		ServerApi.post(ServerApi.feedbacks, new RequestParams(params) , new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray arg0) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0);
			}

			@Override
			public void onSuccess(JSONObject arg0) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0);
			}
		});

		HashMap<String, String> hmgroup = new HashMap<String, String>();
		hmgroup.put(Tags.gameDate, Tags.getDate());
		hmgroup.put(Tags.gameName, gamename);
		hmgroup.put(Tags.gameScore,	""+score);


		ArrayList<HashMap<String, String>> feedback = new ArrayList<HashMap<String, String>>();

		feedback.add(addAttribute(Tags.gameName, gamename));
		feedback.add(addAttribute(Tags.gameDate, Tags.getDate()));
		feedback.add(addAttribute(Tags.gameScore, ""+score));
		feedback.add(addAttribute(Tags.gamePoints, ""+points));
		feedback.add(addAttribute(Tags.gameDuration, ""+duration));
		feedback.add(addAttribute(Tags.gameMiss, ""+miss));
		feedback.add(addAttribute(Tags.gameWinner, ""+winner));
		feedback.add(addAttribute(Tags.gameLevel, ""+level));
		feedback.add(addAttribute(Tags.gameNumTiles, ""+numoftiles));
		feedback.add(addAttribute(Tags.gameNumTiles, ""+numoftiles));

		groupsData.add(hmgroup);
		childData.add(feedback);

	}

	/**
	 * Sets the group indicator to right.
	 */
	private void setGroupIndicatorToRight() {
		/* Get the screen width */
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;

		feedbackList.setIndicatorBounds(width - getDipsFromPixel(35), width
				- getDipsFromPixel(5));
	}

	/**
	 * Adds the attribute.
	 *
	 * @param attr the attr
	 * @param value the value
	 * @return the hash map
	 */
	private HashMap<String, String> addAttribute(String attr, String value) {
		// TODO Auto-generated method stub
		HashMap<String, String> hm =  new HashMap<String, String>();
		hm.put(Tags.feedback_attribute, attr);
		hm.put(Tags.feedback_value, value);
		return hm;
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

	/**
	 * Gets the response.
	 *
	 * @param str the str
	 * @return the response
	 */
	private void getResponse(String str) {
		Log.d("TherapyTiles", " getResponse: response is " + str);
		receive += str;
		// Log.d(TAG, "STR: " + receive);
		// Log.d(TAG, "start byte: " + receive.charAt(0));
		// Log.d(TAG, "start: " + receive.startsWith("a"));
		// Log.d(TAG, "end: " + receive.endsWith("z"));

		//isBluetoothActive = true; // << ===== change that! ;
		Log.d("TherapyTiles", " getResponse: made isBluetoothActive true");

		if (!receive.startsWith("a")) {
			receive = "";
		}

		if (receive.startsWith("a") && receive.endsWith("z")) {
			Log.d(Tags.debugTag, "STR: " + receive);
			if (receive.contains("PING")) {
				Log.d("TherapyTiles", " getResponse: Received PING");
				return;
			}
			cmd = "";
			point = "";
			miss = "";
			duration = "";
			winner = "";
			level = "";
			size = "";

			int firstClosingBracket = receive.indexOf(' ');
			int secondOpeningBracket = firstClosingBracket + 1;
			int secondClosingBracket = receive.indexOf(' ',
					secondOpeningBracket);
			int thirdOpeningBracket = secondClosingBracket + 1;
			int thirdClosingBracket = receive.indexOf(' ', thirdOpeningBracket);
			int fourthOpeningBracket = thirdClosingBracket + 1;
			int fourthClosingBracket = receive.indexOf(' ',
					fourthOpeningBracket);
			int fifthOpeningBracket = fourthClosingBracket + 1;
			int fifthClosingBracket = receive.indexOf(' ', fifthOpeningBracket);
			int sixthOpeningBracket = fifthClosingBracket + 1;
			int sixthClosingBracket = receive.indexOf(' ', sixthOpeningBracket);
			int seventhOpeningBracket = sixthClosingBracket + 1;
			int seventhClosingBracket = receive.indexOf(' ',
					seventhOpeningBracket);
			// int eighthOpeningBracket = seventhClosingBracket + 1;
			// int eighthClosingBracket = receive.indexOf(' ',
			// eighthOpeningBracket);

			for (int i = firstClosingBracket + 1; i < secondClosingBracket; i++) {
				// cmd += receive.charAt(i);
				point += receive.charAt(i);
			}
			for (int i = secondClosingBracket + 1; i < thirdClosingBracket; i++) {
				// point += receive.charAt(i);
				miss += receive.charAt(i);
			}
			for (int i = thirdClosingBracket + 1; i < fourthClosingBracket; i++) {
				// miss += receive.charAt(i);
				duration += receive.charAt(i);
			}
			for (int i = fourthClosingBracket + 1; i < fifthClosingBracket; i++) {
				// duration += receive.charAt(i);
				winner += receive.charAt(i);
			}
			for (int i = fifthClosingBracket + 1; i < sixthClosingBracket; i++) {
				// winner += receive.charAt(i);
				level += receive.charAt(i);
			}
			for (int i = sixthClosingBracket + 1; i < seventhClosingBracket; i++) {
				// level += receive.charAt(i);
				size += receive.charAt(i);
			}
			// for (int i = seventhClosingBracket + 1; i < eighthClosingBracket;
			// i++) {
			// size += receive.charAt(i);
			// }

			setScore();

			Log.d(Tags.debugTag, "CMD: " + cmd);
			Log.d(Tags.debugTag, "POINT: " + point);
			Log.d(Tags.debugTag, "MISS: " + miss);
			Log.d(Tags.debugTag, "DURATION: " + duration);
			Log.d(Tags.debugTag, "WINNER: " + winner);
			Log.d(Tags.debugTag, "LEVEL: " + level);
			Log.d(Tags.debugTag, "SIZE: " + size);

			//System.out.println(dateFormat.format(date));

			addFeedback(game, yourScore, point, duration, miss, winner, level, size);
			mAdapter.notifyDataSetChanged();

			receive = "";
		}
		// receive = "";
		// Log.d(TAG, "clean receive");
	}

	/**
	 * Sets the score.
	 */
	private final void setScore() {
		int p;
		int l;
		int t;

		if(game.equals(Tags.gameNamesAll[Tags.ColorRace1p30sec])){
			yourScore = "";
			yourScore = point;
		}
		else if(game.equals(Tags.gameNamesAll[Tags.ColorRace2p30sec])){
			yourScore = "";
			yourScore = point;
		}
		else if(game.equals(Tags.gameNamesAll[Tags.SimonSays])){
			if (point.length() == 1) {
				yourScore = "";
				p = Integer.parseInt("" + point.charAt(0));
				l = Integer.parseInt("" + level.charAt(0));
				score  = p * l;
				yourScore = Integer.toString(score);
			} else if (point.length() == 2) {
				p = Integer.parseInt("" + point.charAt(1));
				p = p + 10;
				l = Integer.parseInt("" + level.charAt(0));
				score = p * l;
				yourScore = Integer.toString(score);
			}
		}
		else if(game.equals(Tags.gameNamesAll[Tags.FinalCountDown])){
			yourScore = "";
			yourScore = point;
		}
		else if(game.equals(Tags.gameNamesAll[Tags.MemoryNumber])){
			if (duration.length() == 1) {
				yourScore = "";
				yourScore = "30";
			} else if (duration.length() == 2) {
				t = Integer.parseInt("" + duration.charAt(1));
				if (t < 4) {
					yourScore = "";
					yourScore = "30";
				} else if (t < 5) {
					yourScore = "";
					yourScore = "20";
				} else if (t >= 5) {
					yourScore = "";
					yourScore = "10";
				}
			} else if (duration.length() > 2) {
				yourScore = "";
				yourScore = "10";

			}
		}

	}

	/** The m handler. */
	private  final  Handler mHandler = new Handler() {
		private Object mConnectedDeviceName;

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Tags.MESSAGE_STATE_CHANGE:
				Log.d(Tags.debugTag, "MESSAGE_STATE_CHANGE: " + msg.arg1);
				switch (msg.arg1) {
				case BluetoothChatService.STATE_CONNECTED:
					scan_connectButton.setText("Connected");
					scan_connectButton.setClickable(false);
					break;
				case BluetoothChatService.STATE_CONNECTING:
					scan_connectButton.setText("Connecting ... ");
					break;
				case BluetoothChatService.STATE_LISTEN:
				case BluetoothChatService.STATE_NONE:
					devicesSpinnerAdapter.clear();
					devicesSpinnerAdapter.notifyDataSetChanged();
					scan_connectButton.setText("Scan");
					scan_connectButton.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							doDiscovery();
						}
					});
					break;
				}
				break;
			case Tags.MESSAGE_WRITE:
				// byte[] writeBuf = (byte[]) msg.obj;
				// construct a string from the buffer
				//String writeMessage = new String(writeBuf);
				//mConversationArrayAdapter.add("Me:  " + writeMessage);
				byte[] writeBuf = (byte[]) msg.obj;
				String writeMessage = new String(writeBuf);
				break;
			case Tags.MESSAGE_READ:
				//byte[] readBuf = (byte[]) msg.obj;
				// construct a string from the valid bytes in the buffer
				//String readMessage = new String(readBuf, 0, msg.arg1);
				//mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
				String readMessage = (String) msg.obj;
				getResponse(readMessage);
				break;
			case Tags.MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(Tags.DEVICE_NAME);
				Toast.makeText(getActivity().getApplicationContext(),
						"Connected to " + mConnectedDeviceName,
						Toast.LENGTH_SHORT).show();
				break;
			case Tags.MESSAGE_TOAST:
				Toast.makeText(getActivity().getApplicationContext(), msg.getData().getString(Tags.TOAST),
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	/** The m receiver. */
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// If it's already paired, skip it, because it's been listed already
				if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
					//mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
					//devicesSpinnerAdapter.add(device.getName() + "\n" + device.getAddress());
					foundDevices.add(device.getName() + "\n" + device.getAddress());
				}
				// When discovery is finished, change the Activity title
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				for(int i=0; i<foundDevices.size();i++){
					devicesSpinnerAdapter.add(foundDevices.get(i));
				}

				devicesSpinnerAdapter.notifyDataSetChanged();
				scan_connectButton.setText("Connect");
				scan_connectButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.d(Tags.debugTag, "address is "+device);
						BluetoothDevice btbDevice = mBluetoothAdapter.getRemoteDevice(device);

						Log.d(Tags.debugTag, "address is "+device+"\n device address "+btbDevice.getAddress() + " and name is "+btbDevice.getName());
						// Attempt to connect to the device
						mChatService.connect(btbDevice, false);
					}
				});
				Log.d(Tags.debugTag, "finished discovery");
				// setProgressBarIndeterminateVisibility(false);
				// setTitle(R.string.select_device);
				// if (mNewDevicesArrayAdapter.getCount() == 0) {
				//     String noDevices = getResources().getText(R.string.none_found).toString();
				//     mNewDevicesArrayAdapter.add(noDevices);
				//  }
			}
		}
	};
	
	/** The position. */
	private int position;	


	/* (non-Javadoc)
	 * @see dk.dtu.playware.socialtiles.adapters.FeedBackAdapter.ShareData#onShareData(java.util.ArrayList)
	 */
	@Override
	public void onShareData(ArrayList<HashMap<String, String>> arrayList) {
		// TODO Auto-generated method stub
		publish_title = "Just played a game using the therapy tiles";
		publish_description = "";
		for(int i = 0; i<arrayList.size()-1; i++){
			publish_description += ""+arrayList.get(i).get(Tags.feedback_attribute)+": "+arrayList.get(i).get(Tags.feedback_value)+"\n";
			Log.d(Tags.debugTag, ""+arrayList.get(i).get(Tags.feedback_attribute)+": "+arrayList.get(i).get(Tags.feedback_value));
		}
		HashMap<String, String> hm = new HashMap<String, String>() ;
		hm.put(Tags.title, publish_title);
		hm.put(Tags.description, publish_description);
		hm.put(Tags.published, "false");
		pendingpublish.add(hm);
		publishStory();
	}

	/**
	 * On session state change.
	 *
	 * @param session the session
	 * @param state the state
	 * @param exception the exception
	 */
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
			// shareButton.setVisibility(View.VISIBLE);
			if (Tags.pendingPublishReauthorization && 
					state.equals(SessionState.OPENED_TOKEN_UPDATED)) {
				Tags.pendingPublishReauthorization = false;
				publishStory();
			}
		} else if (state.isClosed()) {
			// shareButton.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * Publish story.
	 */
	private void publishStory() {

		ServerApi.post(ServerApi.posts, ServerApi.addPost(Integer.parseInt(current_user),Tags.User.entity, publish_title + "\n" + publish_description ), new JsonHttpResponseHandler());


		Session session = Session.getActiveSession();
		if (session != null) {
			for(int i =0 ; i< pendingpublish.size(); i++){
				// Check for publish permissions    
				position = i;
				List<String> permissions = session.getPermissions();
				if (!isSubsetOf(Tags.PERMISSIONS, permissions)) {
					Tags.pendingPublishReauthorization = true;
					Session.NewPermissionsRequest newPermissionsRequest = new Session
							.NewPermissionsRequest(this, Tags.PERMISSIONS);
					session.requestNewPublishPermissions(newPermissionsRequest);
					Log.d(Tags.debugTag,"I am missing the publish token");
					return ;
				}

				Bundle postParams = new Bundle();
				postParams.putString("name", pendingpublish.get(i).get(Tags.title));
				postParams.putString("caption", "Play with the tiles to experiance true fun");
				postParams.putString("description", pendingpublish.get(i).get(Tags.description));
				postParams.putString("picture", "http://www.e-robot.dk/imgscuts/teaching-10.jpg");

				Request.Callback callback= new Request.Callback() {
					public void onCompleted(Response response) {
						JSONObject graphResponse = response
								.getGraphObject()
								.getInnerJSONObject();
						String postId = null;
						try {
							postId = graphResponse.getString("id");
						} catch (JSONException e) {
							Log.i(Tags.debugTag,
									"JSON error "+ e.getMessage());
						}
						FacebookRequestError error = response.getError();
						if (error != null) {
							Toast.makeText(getActivity()
									.getApplicationContext(),
									error.getErrorMessage(),
									Toast.LENGTH_SHORT).show();
						} else {
							pendingpublish.get(position).put(Tags.published, "true");
							Toast.makeText(getActivity()
									.getApplicationContext(), 
									postId,
									Toast.LENGTH_LONG).show();
						}
					}
				};

				Request request = new Request(session, "me/feed", postParams, 
						HttpMethod.POST, callback);

				RequestAsyncTask task = new RequestAsyncTask(request);
				task.execute();
			}
		}

	}

	/**
	 * Checks if is subset of.
	 *
	 * @param subset the subset
	 * @param superset the superset
	 * @return true, if is subset of
	 */
	private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
		for (String string : subset) {
			if (!superset.contains(string)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Fetch data.
	 */
	private void fetchData(){
		final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "getting the group's members...", true);
		pd.setCancelable(false);
		ServerApi.get(ServerApi.groupMember, ServerApi.getGroupMembers(group_id), new JsonHttpResponseHandler(){			
			@Override
			public void onSuccess(JSONArray members) {
				super.onSuccess(members);
				Log.d(Tags.debugTag, "members are "+ members);
				for(int i =0 ; i< members.length();  i ++){
					JSONObject member;
					try {
						member = (JSONObject) members.get(i);
						Log.d(Tags.debugTag,"member is "+member.toString());
						Log.d(Tags.debugTag,member.get(Tags.first_name).toString()+ " "+ member.get(Tags.last_name).toString());
						currentPlayerSpinnerAdapter.add(member.get(Tags.first_name).toString()+ " "+ member.get(Tags.last_name).toString());
						users.add(member.get(Tags.user_id).toString());

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				currentPlayerSpinnerAdapter.notifyDataSetChanged();
				pd.dismiss();
			}

			@Override
			public void onSuccess(JSONObject member) {
				super.onSuccess(member);
				Log.d(Tags.debugTag, "member are "+ member);
				try {
					currentPlayerSpinnerAdapter.add(member.get(Tags.first_name)+ " "+ member.get(Tags.last_name));
					currentPlayerSpinnerAdapter.notifyDataSetChanged();
					users.add(member.get(Tags.user_id).toString());
					pd.dismiss();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}



}
