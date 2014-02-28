package dk.dtu.playware.socialtiles;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.storage.StorageManager;
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
import android.widget.TextView;
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

import dk.dtu.playware.socialtiles.adapters.FeedBackAdapter;
import dk.dtu.playware.socialtiles.api.ServerApi;
import dk.dtu.playware.socialtiles.datahandlers.UserDataHandler;
import dk.dtu.playware.socialtiles.db.DatabaseHandler;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayTab.
 */
public class PlayTab extends Fragment implements FeedBackAdapter.ShareData{

	/** spinner with the available bluetooth devices.  currently is not used*/
	private Spinner devicesSpinner;
	
	/** The devices spinner adapter. */
	private ArrayAdapter<String> devicesSpinnerAdapter;
	
	/** Button that is used for scanning the available devices and then connects to one. */
	private Button scan_connectButton;
	
	/** Spinner with all the games that the player chooses from. */
	private Spinner gamesSpinner;
	
	/** The play button. */
	private Button playButton;
	
	/** The second players spinner. It is used for the color race 2p*/
	private Spinner secondPlayersSpinner;
	
	/** The second players spinner adapter. */
	private ArrayAdapter<String> secondPlayersSpinnerAdapter;
	
	/** The feedback list. */
	private ExpandableListView feedbackList;
	
	/** The feedback list adapter. */
	private FeedBackAdapter feedbackListAdapter;

	/** The feedback group. */
	static ArrayList<HashMap<String, String>> feedbackGroup ;
	
	/** The feedback child. */
	static ArrayList<ArrayList<HashMap<String, String>>> feedbackChild  ;
	
	/** The selected device address. */
	private String device;
	
	/** The selected game. */
	protected String game;

	/** The current user group. */
	//static ArrayList<HashMap<String,String>> currentUserGroup;
	
	/** The current user child. */
	//static ArrayList<ArrayList<HashMap<String, String>>> currentUserChild  ;

	/** The  bluetooth adapter. */
	private BluetoothAdapter mBluetoothAdapter;

	
	/** The m chat service. The bluetooth service. The bluetooth communication was build based on 
	 * the bluetooth chat example
	 *  */
	private BluetoothChatService mChatService = null;


	/** The pendingpublish. -Facebook */
	private ArrayList<HashMap<String, String>> pendingpublish = new ArrayList<HashMap<String,String>>();
	//	private ArrayList<String> foundDevices = new ArrayList<String>();
	/** The games spinner adapter. */
	private ArrayAdapter<String> gamesSpinnerAdapter;
	
	/** The m out string buffer. */
	private StringBuffer mOutStringBuffer;
	
	/** The cmd. */
	private int cmd =0;
	
	/** The point. */
	private int point=0;
	
	/** The miss. */
	private int miss=0;
	
	/** The duration. */
	private int duration=0;
	
	/** The winner. */
	private int winner=0;
	
	/** The level. */
	private int level=0;
	
	/** The size. */
	private int size=0;
	
	/** The receive. */
	private String receive = "";
	
	/** The your score. */
	private String yourScore = "";
	
	/** The position. */
	private int position;	
	
	/** The user data. Basically is used to store different kind of data */
	private UserDataHandler userData = new UserDataHandler();
	
	/** The game name. */
	private String  gameName;
	
	/** The STAR tcmd. */
	private String STARTcmd = "a";
	
	/** The EN dcmd. */
	private String ENDcmd = "z";
	
	/** The user_ids. */
	private ArrayList<Integer> user_ids = new ArrayList<Integer>();


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
	
	/** The info text. */
	private TextView infoText;
	
	/** The btb connected text. */
	private TextView btbConnectedText;
	
	/** The new player. */
	protected String newPlayer;
	//private ExpandableListView currentPlayersList;
	//private Button addPlayerButton;
	//private CurrentPlayersAdapter currentPlayersAdabter;
	//private SpinnerAdapter spinnercurrentPlayerAdapter;
	/** The new player pos. */
	protected int newPlayerPos;
	
	/** The user name list. */
	private ArrayList<String> userNameList;
	
	/** The play2p button. */
	private Button play2pButton;
	
	/** The player. */
	protected String player;
	
	/** The player pos. */
	protected int playerPos;
	
	/** The num of feedbacks. */
	private int numOfFeedbacks =0;
	
	/** The second players spinner pos. */
	protected int secondPlayersSpinnerPos =0;

	/**
	 * Instantiates a new play tab.
	 */
	public PlayTab(){

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(Tags.debugTag,"PlayTab : Creating...");
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}


	@Override
	public synchronized void onResume() {
		super.onResume();
		Log.d(Tags.debugTag,"PlayTab : Resuming...");

		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
		if (mChatService != null) {
			// Only if the state is STATE_NONE, do we know that we haven't started already
			if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
				// Start the Bluetooth chat services
				mChatService.start();
			}
			else{
				Log.d(Tags.debugTag,"mChatService state is "+mChatService.getState());
			}
		}

		Session session = Session.getActiveSession();
		if (session != null &&
				(session.isOpened() || session.isClosed()) ) {
			onSessionStateChange(session, session.getState(), null);
		}

		uiHelper.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(Tags.debugTag,"PlayTab : Pausing...");
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(Tags.debugTag,"PlayTab : Destroying...");
		uiHelper.onDestroy();
		if(mChatService != null && mChatService.getState() != BluetoothChatService.STATE_NONE){
			stopBluetooth();
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(Tags.debugTag,"PlayTab : Saving instance state...");
		outState.putBoolean(Tags.PENDING_PUBLISH_KEY, Tags.pendingPublishReauthorization);
		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(Tags.debugTag,"PlayTab : getting activity result...");
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


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(mChatService != null && mChatService.getState() != BluetoothChatService.STATE_NONE){
			stopBluetooth();
		}
		Log.d(Tags.debugTag,"PlayTab : Creating view...");
		feedbackGroup = new ArrayList<HashMap<String,String>>();
		feedbackChild  = new ArrayList<ArrayList<HashMap<String,String>>>();
		View v =inflater.inflate(R.layout.play_tab_layout, container, false);
		btbConnectedText = (TextView) v.findViewById(R.id.device_connected_text);
		infoText = (TextView) v.findViewById(R.id.info_text);
		devicesSpinner = (Spinner) v.findViewById(R.id.devices_spinner);
		devicesSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Tags.deviceNameArray);
		//devicesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		devicesSpinner.setAdapter(devicesSpinnerAdapter);
		devicesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				//				device = parent.getItemAtPosition(pos).toString();
				//				device = device.substring(device.length() - 17);
				device = Tags.deviceAddressArray[1];
				Log.d(Tags.debugTag, "Address is "+device);
			}
			public void onNothingSelected(AdapterView<?> parent) {
				//				device = parent.getItemAtPosition(0).toString();
				//				device = device.substring(device.length() - 17);
				//				Log.d(Tags.debugTag,"Address is" + device);
				device = Tags.deviceAddressArray[1];
				Log.d(Tags.debugTag, "Address is "+device);
			}
		});
		scan_connectButton = (Button) v.findViewById(R.id.scan_connectButton);
		setConnectButton();


		gamesSpinner = (Spinner) v.findViewById(R.id.gamespinner);
		gamesSpinnerAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, Tags.gameNamesCurrent);
		gamesSpinner.setAdapter(gamesSpinnerAdapter);
		gamesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				game = parent.getItemAtPosition(pos).toString();

				Log.d(Tags.debugTag," "+game + " == "+Tags.gameNamesAll[Tags.ColorRace2p30sec]);
				if(game.equals(Tags.gameNamesAll[Tags.ColorRace2p30sec])){
					secondPlayersSpinner.setVisibility(View.VISIBLE);
					play2pButton.setVisibility(View.VISIBLE);
					playButton.setVisibility(View.INVISIBLE);

				} else{
					secondPlayersSpinner.setVisibility(View.GONE);
					play2pButton.setVisibility(View.GONE);
					playButton.setVisibility(View.VISIBLE);
				}
			}
			public void onNothingSelected(AdapterView<?> parent) {
				game = Tags.gameNamesAll[0];
			}
		});

		playButton   = (Button) v.findViewById(R.id.playButton);
		playButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendMessageWithBluetooth(game);
			}
		});

		userNameList = new ArrayList<String>();
		secondPlayersSpinner = (Spinner) v.findViewById(R.id.secondPlayerSpinner);
		//secondPlayersSpinner.setVisibility(View.VISIBLE);
		secondPlayersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				player = parent.getItemAtPosition(pos).toString();
				secondPlayersSpinnerPos  = pos;
			}
			public void onNothingSelected(AdapterView<?> parent) {
				player = userNameList.get(0);
				secondPlayersSpinnerPos = 0;
			}
		});
		secondPlayersSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, userNameList);
		secondPlayersSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		secondPlayersSpinner.setAdapter(secondPlayersSpinnerAdapter);
		fetchData();

		play2pButton = (Button) v.findViewById(R.id.playButton2p);
		//play2pButton.setVisibility(View.VISIBLE);
		play2pButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(secondPlayersSpinnerPos != 0){
					sendMessageWithBluetooth(game);
					playerPos = secondPlayersSpinnerPos-2;
				}
				else{
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setMessage("Please select a rival")
					.setCancelable(false)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							//do things
						}
					});
					AlertDialog alert = builder.create();
					alert.show();
				}
			}
		});


		feedbackList = (ExpandableListView) v.findViewById(R.id.feedbackExpandable);
		feedbackListAdapter  = new FeedBackAdapter(getActivity(),this, feedbackGroup, feedbackChild);
		feedbackList.setAdapter(feedbackListAdapter);

		setGroupIndicatorToRight();


		initBluetooth();

		if (savedInstanceState != null) {
			Tags.pendingPublishReauthorization = 
					savedInstanceState.getBoolean(Tags.PENDING_PUBLISH_KEY, false);
		}

		return  v;
	}

	/**
	 * Fetch data regarding the friend names of the user
	 * is used for the color race 2p
	 */
	private void fetchData(){

		userNameList = new ArrayList<String>();
		//userNameList.add("Select a rival player");
		secondPlayersSpinnerAdapter.add("Select a rival player");
		secondPlayersSpinnerAdapter.add("Unkonwn Player");
		//secondPlayersSpinnerAdapter.addAll(userNameList);
		secondPlayersSpinnerAdapter.notifyDataSetChanged();
		
//		infoText.setVisibility(View.VISIBLE);
//		infoText.setText("getting your friends...");

		ServerApi.get(ServerApi.friends, ServerApi.getFriends(Tags.User.user_id), new JsonHttpResponseHandler(){
			HashMap<String, String> hm;
			@Override
			public void onSuccess(JSONArray members) {
				super.onSuccess(members);
				Log.d(Tags.debugTag,"friends are : "+ members.toString());
				for(int i =0 ; i< members.length();  i ++){
					JSONObject member;
					try {
						member = (JSONObject) members.get(i);
						hm = getHashMap(member);
						userData.add(getHashMap(member));
						userNameList.add(hm.get(Tags.profileName));
						//spinnercurrentPlayerAdapter.add(member.getString(Tags.first_name)+ " "+member.getString(Tags.last_name));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				//secondPlayersSpinner.removeAllViews();
				secondPlayersSpinnerAdapter.addAll(userNameList);
				secondPlayersSpinnerAdapter.notifyDataSetChanged();
				Log.d(Tags.debugTag,"spinner count is "+secondPlayersSpinnerAdapter.getCount());

				//pd.dismiss();
				//				infoText.setVisibility(View.GONE);
			}

			@Override
			public void onSuccess(JSONObject member) {
				super.onSuccess(member);
				//if(member.has(name))
				if(member.has("response")){
					//pd.dismiss();
					//infoText.setVisibility(View.GONE);

				}
				else{

					hm = getHashMap(member);
					userData.add(getHashMap(member));
					userNameList.add(hm.get(Tags.profileName));

					//pd.dismiss();
				}

			}
		});


	}

	/**
	 * Gets the hash map.
	 * Takes the json object data and turn it to a hash map
	 * @param member : the json object
	 * @return the hash map
	 */
	private HashMap<String, String> getHashMap(JSONObject member) {
		HashMap<String, String> hm  = new HashMap<String, String>();
		try {
			hm.put(Tags.profileImg,Tags.facebookApi+ member.getString(Tags.fbid)+Tags.fbPictureSquare);
			hm.put(Tags.profileName, member.getString(Tags.first_name)+ " "+member.getString(Tags.last_name));
			hm.put(Tags.user_id, member.getString(Tags.user_id));
			hm.put(Tags.color, "");
			return hm;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Sets the connecting button.
	 */
	private void setConnectingButton(){
		scan_connectButton.setText("Connecting ...");
		scan_connectButton.setClickable(false);		
		infoText.setText(Tags.bluetoothConnectToDeviceString);
	}

	/**
	 * Sets the connect button.
	 */
	private void setConnectButton(){
		devicesSpinner.setVisibility(View.VISIBLE);
		btbConnectedText.setVisibility(View.GONE);
		scan_connectButton.setText("Connect");
		scan_connectButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(Tags.debugTag, "address is "+device);
				BluetoothDevice btbDevice = mBluetoothAdapter.getRemoteDevice(device);

				Log.d(Tags.debugTag, "address is "+device+"\n device address "+btbDevice.getAddress() + " and name is "+btbDevice.getName());
				// Attempt to connect to the device
				mChatService.connect(btbDevice, false);
			}
		});
		infoText.setText(Tags.bluetoothConnectToDeviceString);
	}

	/**
	 * Sets the disconnect button.
	 */
	private void setDisconnectButton(){
		devicesSpinner.setVisibility(View.GONE);
		infoText.setVisibility(View.GONE);
		btbConnectedText.setVisibility(View.VISIBLE);
		scan_connectButton.setText("Disconnect");
		scan_connectButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stopBluetooth();
				setConnectButton();

			}
		});
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
	 * Send message with bluetooth.
	 *
	 * @param msg the msg
	 */
	private void sendMessageWithBluetooth(String msg){
		byte[] start = STARTcmd.getBytes();
		byte[] c = msg.getBytes();
		byte[] end = ENDcmd.getBytes();
		mChatService.write(start);
		mChatService.write(c);
		mChatService.write(end);
	}

	/**
	 * Stop bluetooth.
	 */
	private void stopBluetooth(){
		sendMessageWithBluetooth("RESTART");
		mChatService.stop();
	}

	void stroreFeedbackToDb(String gamename, String score, int point2, int duration2, int miss2, int winner2, int level2, int size2, int user_id){
		SimpleDateFormat s = new SimpleDateFormat("dd:MM:yyyy:hh:mm:ss");
		String timestamp = s.format(new Date());
		DatabaseHandler dbHandler =  new DatabaseHandler(getActivity());
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());
		/**
		 * stores feedback to the local db
		 */
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		String formattedDate = df.format(c.getTime());
		long feedback_id = dbHandler.addFeedback(user_id, gamename, formattedDate, timestamp, point2, miss2,  duration2,  winner2,  level2,  size2,  Integer.parseInt(score));
	}
	
	/**
	 * Upload feedback to the server.
	 *
	 * @param gamename the gamename
	 * @param score the score
	 * @param point2 the point2
	 * @param duration2 the duration2
	 * @param miss2 the miss2
	 * @param winner2 the winner2							
	 * @param level2 the level2
	 * @param size2 the size2
	 * @param user_id the user_id
	 */
	void uploadFeedback(String gamename, String score, int point2, int duration2, int miss2, int winner2, int level2, int size2, int user_id){

		HashMap<String, String> params = new HashMap<String, String>();
		params.put(Tags.user_id, ""+user_id);
		params.put("gamename", gamename);
		params.put(Tags.date_created, Tags.getDate());
		params.put("points",""+point2);
		params.put("miss", ""+miss2);
		params.put("duration", ""+duration2);
		params.put("winner", ""+winner2);
		params.put("level", ""+level2);
		params.put("size", ""+size2);
		params.put("score", score);

		Log.d(Tags.debugTag, "parameters are : "+ params);
		ServerApi.post(ServerApi.feedbacks, new RequestParams(params) , new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray arg0) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0);
				Log.d(Tags.debugTag, "upload feedback response is "+arg0.toString());
			}

			@Override
			public void onSuccess(JSONObject arg0) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0);
				Log.d(Tags.debugTag, "upload feedback response is "+arg0.toString());
			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0);
				Log.d(Tags.debugTag, "upload feedback response is "+arg0.toString());
			}
		});

	}

	/**
	 * Show feedback to screen.
	 *
	 * @param gamename the gamename
	 * @param score the score
	 * @param point2 the point2
	 * @param duration2 the duration2
	 * @param miss2 the miss2
	 * @param winner2 the winner2
	 * @param level2 the level2
	 * @param size2 the size2
	 */
	void showFeedbackToScreen(String gamename, String score, int point2, int duration2, int miss2, int winner2, int level2, int size2){
		Log.d(Tags.debugTag,"Showing feedback on screen ...");
		HashMap<String, String> hmgroup = new HashMap<String, String>();
		hmgroup.put(Tags.gameDate, Tags.getDate());
		hmgroup.put(Tags.gameName, gamename);
		hmgroup.put(Tags.gameScore,	""+score);


		ArrayList<HashMap<String, String>> feedback = new ArrayList<HashMap<String, String>>();

		feedback.add(addAttribute(Tags.gameName, gamename));
		feedback.add(addAttribute(Tags.gameDate, Tags.getDate()));
		feedback.add(addAttribute(Tags.gameScore, ""+score));
		feedback.add(addAttribute(Tags.gamePoints, ""+point2));
		feedback.add(addAttribute(Tags.gameDuration, ""+duration2));
		feedback.add(addAttribute(Tags.gameMiss, ""+miss2));
		feedback.add(addAttribute(Tags.gameWinner, ""+winner2));
		feedback.add(addAttribute(Tags.gameLevel, ""+level2));
		feedback.add(addAttribute(Tags.gameNumTiles, ""+size2));
		feedback.add(addAttribute(Tags.gameNumTiles, ""+size2));

		feedbackGroup.add(hmgroup);
		feedbackChild.add(feedback);

	}
	

	
	/**
	 * Sets the group indicator for the expandable to right.
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
	 * Creates the hash map structure that the adapter needs.
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
	 * Gets bluetooth  message.
	 *
	 * @param str the str
	 * @return the response
	 */
	private void getResponse(String str) {
		Log.d("TherapyTiles", " Bluetooth message: " + str);
		Log.d("TherapyTiles", " recieve before : " + receive);
		receive += str;
		Log.d("TherapyTiles", " recieve after : " + receive);

		if(receive.indexOf('a')!= -1 && receive.indexOf('z')!=-1){
			StringBuffer strBuff = new StringBuffer(receive);
			String feedbackString = strBuff.substring(receive.indexOf('a')+1, receive.indexOf('z'));
			strBuff.delete(receive.indexOf('a'), receive.indexOf('z')+1);
			receive = strBuff.toString(); 
			feedbackString = feedbackString.trim();
			String[] feedbackStringArray = feedbackString.split(" ");

			if(feedbackStringArray.length != 7){
				Toast.makeText(getActivity(), "feedback corrupted", Toast.LENGTH_SHORT).show();

				Log.d(Tags.debugTag,"feedback array length is "+feedbackStringArray.length);
				for(int i=0; i<feedbackStringArray.length;i++){
					Log.d(Tags.debugTag,"feedbackStringArray["+i+"] = "+feedbackStringArray[i]);

				}
			}

			cmd 		 =   Integer.parseInt( feedbackStringArray[0]);
			point 		+=   Integer.parseInt( feedbackStringArray[1]);
			miss     	 =   Integer.parseInt( feedbackStringArray[2]);
			duration	 +=   Integer.parseInt( feedbackStringArray[3]);
			winner   	 =   Integer.parseInt(feedbackStringArray[4]);
			level		 =   Integer.parseInt( feedbackStringArray[5]);
			size     	 =   Integer.parseInt( feedbackStringArray[6]);

			Log.d(Tags.debugTag,"CMD: "+cmd+ " POINT: " + point + " MISS: " + miss + " DURATION: " + duration + 
					" WINNER: " + winner + " LEVEL: " + level + 
					" SIZE: "  + size    + " GAME: "  +game   + " SCORE: "+yourScore);

			if(cmd == 25){ // the game is finished so we can upload the feedback and show it

				setScore();




				if(game.equals(Tags.gameNamesAll[Tags.ColorRace2p30sec])){
					int user_id_2p = Tags.User.user_id;
					if(numOfFeedbacks == 0){


						if(winner ==2){
							if(playerPos >=0){
								user_id_2p = Integer.parseInt(userData.getData().get(playerPos).get(Tags.user_id));
							}
							else{
								user_id_2p = -1;
							}
						} else {
							showFeedbackToScreen(game, yourScore, point, duration, miss, winner, level, size);
							
						}
						Log.d(Tags.debugTag,"winner = "+winner+" user_id = "+user_id_2p+" numofFeedbacks = "+numOfFeedbacks);
						if(user_id_2p >=0){
							uploadFeedback(game, yourScore, point, duration, miss, winner, level, size, user_id_2p);
							stroreFeedbackToDb(game, yourScore, point, duration, miss, winner, level, size, user_id_2p);
							
						}
						numOfFeedbacks ++;
					}
					else{

						if(winner ==1){
							if(playerPos >=0){
								user_id_2p = Integer.parseInt(userData.getData().get(playerPos).get(Tags.user_id));
							}
							else{
								user_id_2p = -1;
							}
						} else {
							showFeedbackToScreen(game, yourScore, point, duration, miss, winner, level, size);
						}
						Log.d(Tags.debugTag,"winner = "+winner+" user_id = "+user_id_2p+" numofFeedbacks = "+numOfFeedbacks);
						if(user_id_2p >=0){
							uploadFeedback(game, yourScore, point, duration, miss, winner, level, size, user_id_2p);
							stroreFeedbackToDb(game, yourScore, point, duration, miss, winner, level, size, user_id_2p);
						}
						numOfFeedbacks = 0;
					}
				}else{
					uploadFeedback(game, yourScore, point, duration, miss, winner, level, size, Tags.User.user_id);
					stroreFeedbackToDb(game, yourScore, point, duration, miss, winner, level, size, Tags.User.user_id);
					showFeedbackToScreen(game, yourScore, point, duration, miss, winner, level, size);
				}

				feedbackListAdapter.notifyDataSetChanged();
				/*				for (Integer user_id : user_ids) {

					uploadFeedback(game, yourScore, point, duration, miss, winner, level, size, user_id);
					if(user_id == Tags.User.user_id){
						showFeedbackToScreen(game, yourScore, point, duration, miss, winner, level, size);
					}
					feedbackListAdapter.notifyDataSetChanged();
				}*/
				point = 0;
				duration = 0;
			}
		}
	}

	/**
	 * Sets the score.
	 */
	private final void setScore() {
		Log.d(Tags.debugTag,"current game is "+game +" and colorrace is "+Tags.gameNamesAll[Tags.ColorRace1p30sec]);
		if(game.equals(Tags.gameNamesAll[Tags.ColorRace1p30sec])){
			yourScore = "";
			yourScore = ""+point;
		}
		else if(game.equals(Tags.gameNamesAll[Tags.ColorRace2p30sec])){
			yourScore = ""+point;
		}
		else if(game.equals(Tags.gameNamesAll[Tags.SimonSays])){
			yourScore = ""+point*level;
		}
		else if(game.equals(Tags.gameNamesAll[Tags.FinalCountDown])){
			yourScore = ""+point;
		}
		else if(game.equals(Tags.gameNamesAll[Tags.MemoryNumber])){
			if (duration <40){
				yourScore  = ""+30;
			}
			else if (duration < 50){
				yourScore =""+20;
			}
			else {
				yourScore =""+10;
			}
		}
	}

	/** bluetooth handler. */
	private  final  Handler mHandler = new Handler() {
		private Object mConnectedDeviceName;

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Tags.MESSAGE_STATE_CHANGE:
				Log.d(Tags.debugTag, "MESSAGE_STATE_CHANGE: " + msg.arg1);
				switch (msg.arg1) {
				case BluetoothChatService.STATE_CONNECTED:
					setDisconnectButton();
					break;
				case BluetoothChatService.STATE_CONNECTING:
					//					scan_connectButton.setText("Connecting ... ");
					setConnectingButton();
					break;
				case BluetoothChatService.STATE_LISTEN:
				case BluetoothChatService.STATE_NONE:
					setConnectButton();
					break;
				}
				break;
			case Tags.MESSAGE_WRITE:
				byte[] writeBuf = (byte[]) msg.obj;
				String writeMessage = new String(writeBuf);
				break;
			case Tags.MESSAGE_READ:
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
	 * Publish the feedback to facebook.
	 */
	private void publishStory() {

		ServerApi.post(ServerApi.posts, ServerApi.addPost(Tags.User.user_id,Tags.User.entity, publish_title + "\n" + publish_description ), new JsonHttpResponseHandler());


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
	 * Checks if a string is subset of another string.
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
}
