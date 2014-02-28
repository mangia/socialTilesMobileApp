package dk.dtu.playware.socialtiles.tags;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * The Class Tags.
 * The class contains some static variables  that are accessed through the other classes
 */
public class Tags {
	
	/** The Constant debugTag. */
	public final static String debugTag            = "SocialTherapyTiles";
	
	/** The Constant profileName. */
	public final static String profileName         = "profile_name";
	
	/** The Constant profileImg. */
	public final static String profileImg          = "profile_img";
	
	/** The Constant goalProgress. */
	public final static String goalProgress        = "goal_progress";
	
	/** The Constant goalThreshold. */
	public final static String goalThreshold       = "goal_threshold";
	
	/** The Constant goalName. */
	public final static String goalName            = "goal_name";
	
	/** The Constant goalExpire. */
	public final static String goalExpire          = "goal_expire";
	
	/** The Constant userScore. */
	public final static String userScore           = "user_score";
	
	/** The Constant groupName. */
	public final static String groupName           = "group_name";
	
	/** The Constant groupScore. */
	public final static String groupScore          = "group_score";
	
	/** The Constant eventName. */
	public final static String eventName           = "event_name";
	
	/** The Constant feedback_attribute. */
	public final static String feedback_attribute  = "feedbac_attr";
	
	/** The Constant feedback_value. */
	public final static String feedback_value      ="feedback_value";
	
	/** The Constant gameName. */
	public final static String gameName            = "Game Name";
	
	/** The Constant gameDate. */
	public final static String gameDate            = "Date";
	
	/** The Constant gameScore. */
	public final static String gameScore           = "Score";
	
	/** The Constant gamePoints. */
	public final static String  gamePoints         = "Points";
	
	/** The Constant gameDuration. */
	public final static String  gameDuration       = "Duration";
	
	/** The Constant gameWinner. */
	public final static String  gameWinner         = "Winner";		
	
	/** The Constant gameMiss. */
	public final static String  gameMiss           = "Miss";
	
	/** The Constant gameLevel. */
	public final static String  gameLevel          = "Level";
	
	/** The Constant gameNumTiles. */
	public final static String  gameNumTiles       = "Number of tiles";
	
	/** The Constant goal_id. */
	public final static String goal_id= "goal_id";
	
	/** The Constant participants. */
	public final static String participants = "participants";
	
	/** The Constant op. */
	public final static String op = "option";
	
	/** The Constant members. */
	public final static String members = "user_ids";
	
	/** The Constant first_name. */
	public final static String first_name       = "name_first";
	
	/** The Constant last_name. */
	public final static String last_name        = "name_last";
	
	/** The Constant fbid. */
	public final static String fbid             = "fbid";
	
	/** The Constant total_score. */
	public final static String total_score      = "total_score";
	
	/** The Constant total_duration. */
	public final static String total_duration   = "total_duration";
	
	/** The Constant num_achievments. */
	public final static String num_achievments  = "num_achievments";
	
	/** The Constant entity. */
	public final static String entity           = "entity";
	
	/** The Constant user_id. */
	public final static String user_id          = "user_id";
	
	/** The Constant date_created. */
	public final static String date_created =  "date_created";
	
	/** The Constant group_id. */
	public final static String group_id     = "group_id";
	
	/** The Constant group_entity. */
	public final static String group_entity = "group_entity";
	
	/** The Constant user_groups. */
	public final static String user_groups  = "user_groups";
	
	/** The Constant name. */
	public final static String name        = "name";
	
	/** The Constant description. */
	public final static String description = "description";
	
	/** The Constant event_id. */
	public final static String event_id    = "event_id";
	
	/** The Constant reward_text. */
	public final static String reward_text = "reward_text";
	
	/** The Constant start_date. */
	public final static String start_date  = "start_date";
	
	/** The Constant end_date. */
	public final static String end_date  = "end_date";
	
	/** The Constant type_of_participants. */
	public final static String type_of_participants = "type_of_participants";
	
	/** The Constant reward_points. */
	public final static String reward_points = "reward_points";
	
	/** The Constant currently. */
	public final static String currently    = "currently";
	
	/** The Constant post_text. */
	public final static String post_text    = "post_text";
	
	/** The Constant creator. */
	public final static String creator      = "creator";
	
	/** The Constant posted_to. */
	public final static String posted_to    = "posted_to";
	
	/** The Constant posted_text. */
	public final static String posted_text  = "post_text";
	
	/** The Constant post_date. */
	public final static String post_date    = "post_date";
	
	/** The Constant published. */
	public final static String published    = "published";
	
	/** The Constant title. */
	public final static String title        = "title";
	
	/** The Constant game_name. */
	public final static String game_name = "game_name";
	
	/** The Constant goal_type. */
	public final static String goal_type = "goal_type";
	
	/** The Constant threshold. */
	public final static String threshold = "threshold";
	
	/** The Constant created_for. */
	public final static String created_for = "created_for";
	
	/** The Constant created_for_multi. */
	public final static String created_for_multi = "created_for_multi";
	
	/** The Constant achieved_by. */
	public final static String achieved_by = "achieved_by";
	
	/** The Constant item1. */
	public final static String item1 = "item1";
	
	/** The Constant item2. */
	public final static String item2 = "item2";
	
	/** The Constant myProfilimg. */
	public final static String myProfilimg  = "http://graph.facebook.com/1141789001/picture";
	
	/** The Constant isFriend. */
	public final static String isFriend = "isfriend";
	
	/** The Constant facebookApi. */
	public final static String facebookApi      = "https://graph.facebook.com/";
	
	/** The Constant fbPictureSquare. */
	public final static String fbPictureSquare  = "/picture?type=square";
	
	/** The Constant fbPictureSmall. */
	public final static String fbPictureSmall   = "/picture?type=small";
	
	/** The Constant fbPictureNormal. */
	public final static String fbPictureNormal  = "/picture?type=normal";
	
	/** The Constant fbPictureLarge. */
	public final static String fbPictureLarge   = "/picture?type=large";


	/** The Constant eventNameId. */
	public final static int eventNameId          = 0;
	
	/** The Constant rewardTextId. */
	public final static int rewardTextId         = 1;
	
	/** The Constant startDateId. */
	public final static int startDateId          = 2;
	
	/** The Constant endDateId. */
	public final static int endDateId            = 3;
	
	/** The Constant eventParticipantsId. */
	public final static int eventParticipantsId  = 4;
	
	/** The Constant eventTypeId. */
	public final static int eventTypeId          = 5;
	
	/** The Constant addGoal. */
	public final static int addGoal              = 6;
	
	/** The Constant removeGoal. */
	public final static int removeGoal           = 7;
	
	/** The Constant addFriendId. */
	public final static int addFriendId          = 8;
	
	/** The Constant removeFriendId. */
	public final static int removeFriendId       = 9;
	
	/** The Constant groupNameId. */
	public final static int groupNameId          = 10;
	
	/** The Constant groupDescriptionId. */
	public final static int groupDescriptionId   = 11;
	
	/** The Constant rewardPointsId. */
	public final static int rewardPointsId       = 12;

	//public static final int MESSAGE_READ = 1;
	//public static final int MESSAGE_CONNECTED = 2;
	//public static final int MESSAGE_OPEN = 3;

	/** The Constant MESSAGE_STATE_CHANGE. */
	public static final int MESSAGE_STATE_CHANGE = 1;
	
	/** The Constant MESSAGE_READ. */
	public static final int MESSAGE_READ = 2;
	
	/** The Constant MESSAGE_WRITE. */
	public static final int MESSAGE_WRITE = 3;
	
	/** The Constant MESSAGE_DEVICE_NAME. */
	public static final int MESSAGE_DEVICE_NAME = 4;
	
	/** The Constant MESSAGE_TOAST. */
	public static final int MESSAGE_TOAST = 5;

	// Key names received from the BluetoothChatService Handler
	/** The Constant DEVICE_NAME. */
	public static final String DEVICE_NAME = "device_name";
	
	/** The Constant TOAST. */
	public static final String TOAST = "toast";

	/** The Constant EXTRA_UUID. */
	public static final String EXTRA_UUID  = "uuid";
	
	/** The Constant SPP_MODE. */
	public static final int SPP_MODE  = 0;
	
	/** The Constant SPP_UUID. */
	public static final UUID SPP_UUID  = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	
	/** The Constant REQUEST_ENABLE_BT. */
	public static final int REQUEST_ENABLE_BT  = 11;

	
	/** The Constant ColorRace1p30sec. */
	public static final int ColorRace1p30sec    = 0;
	
	/** The Constant ColorRace1p10pnt. */
	public static final int ColorRace1p10pnt    = 1;
	
	/** The Constant ColorRace2p10pnt. */
	public static final int ColorRace2p10pnt    = 2;
	
	/** The Constant ColorRace2p30sec. */
	public static final int ColorRace2p30sec    = 3;
	
	/** The Constant ColorRace3p10pnt. */
	public static final int ColorRace3p10pnt    = 4;
	
	/** The Constant ColorRace3p30sec. */
	public static final int ColorRace3p30sec    = 5;
	
	/** The Constant FinalCountDown. */
	public static final int FinalCountDown      = 6;
	
	/** The Constant FinalCountDwonSlow. */
	public static final int FinalCountDwonSlow  = 7;
	
	/** The Constant MemoryNumber. */
	public static final int MemoryNumber        = 8;
	
	/** The Constant SimonSays. */
	public static final int SimonSays           = 9;

	/**
	 * The Class User.
	 */
	public static class User{
		
		/** The instance. */
		static User instance = null;
		
		/**
		 * Instance.
		 *
		 * @return the user
		 */
		static User  instance(){
			if(instance == null){
				instance = new User();
			}
			return instance;
		}
		
		/** The fbid. */
		public static String fbid;
		
		/** The name_first. */
		public static String name_first;
		
		/** The name_last. */
		public static String name_last;
		
		/** The user_id. */
		public static int user_id;
		
		/** The total_score. */
		public static int total_score;
		
		/** The total_duration. */
		public static int total_duration;
		
		/** The num_achievments. */
		public static int num_achievments;
		
		/** The entity. */
		public static int entity;
	};

	/** The user. */
	public static User user;
	
	/** The Constant PERMISSIONS. */
	public static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	
	/** The Constant PENDING_PUBLISH_KEY. */
	public static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	
	/** The pending publish reauthorization. */
	public static boolean pendingPublishReauthorization = false;
	
	/** The Constant color. */
	public static final String color = "color";
	
	/**
	 * Sets the user.
	 *
	 * @param fbid the fbid
	 * @param name_first the name_first
	 * @param name_last the name_last
	 * @param user_id the user_id
	 * @param total_score the total_score
	 * @param total_duration the total_duration
	 * @param num_achievments the num_achievments
	 * @param entity the entity
	 */
	public static void setUser(String fbid, String name_first, String name_last,	
			int user_id, int total_score, int total_duration, 
			int num_achievments ,int entity){
		user  = User.instance();
		User.fbid = fbid;
		User.name_first =name_first;
		User.name_last =name_last;
		User.user_id=user_id;
		User.total_score =total_score;
		User.total_duration =total_duration;
		User.num_achievments = num_achievments;
		User.entity = entity;
	}

	/** The Constant gameNamesAll. */
	public final static String[] gameNamesAll = new String[]{
		"COLORRACE 1P 30SEC",
		"COLORRACE 1P 10PNT",
		"COLORRACE 2P 10PNT",
		"COLORRACE 2P 30SEC",
		"COLORRACE 3P 30SEC",
		"COLORRACE 3P 10PNT",
		"FINALCOUNTDOWN",
		"FINALCOUNTDOWN SLOW",	
		"MEMORYNUMBER",
		"SIMON SAYS"
	};

	/** The Constant gameNamesCurrent. */
	public final static String[] gameNamesCurrent = new String[]{
		"COLORRACE 1P 30SEC",
		"COLORRACE 2P 30SEC",
		"FINALCOUNTDOWN",
		"MEMORYNUMBER",
		"SIMON SAYS",
		"PAINT"
	};

	/** The Constant goalType. */
	public final static String[] goalType = new String[]{
		"play", "score"
	};

	/** The Constant profilName_array. */
	public final static  String[] profilName_array  = new String[]{
		"Stephanie Erika Papadaki",
		"Rabie Jradi",
		"Mitsuko Rizogaluko",
		"Michalis Uhu Hu Mathioudakis",
		"Pano Papadatos",
		"Finnur Smári Torfason",
		"Sofia Androulidaki",
		"Banny Doukos",
		"Collada Duck",
		"Eksw Ta Kommata"
	};

	/** The Constant fbids. */
	public final static String[] fbids  ={
		"501075781",
		"509475152",
		"584029580",
		"787650488",
		"817968359",
		"1021059219",
		"1179923499",
		"1818748461",
		"100000363377190",
		"100001002177770"
	};
	
	
	/** The Constant deviceNameArray. */
	public final static String[] deviceNameArray = {
		"Tiles Bluetooth"
	} ;

	/** The Constant deviceAddressArray. */
	public final static String[] deviceAddressArray ={
		"00:13:EF:12:16:20",
		"00:13:EF:00:0E:01"
	};
	
	/** The Constant bluetoothNotConnectedString. */
	public static final String bluetoothNotConnectedString        = "Bluetooth not connected";
	
	/** The Constant bluetoothScanningForDevicesString. */
	public static final String bluetoothScanningForDevicesString  = "Looking for devices";
	
	/** The Constant bluetoothConnectToDeviceString. */
	public static final String bluetoothConnectToDeviceString     = "Choose the device you want to connect from the dropdownlist";
	
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public static String getDate(){
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
		String formattedDate = df.format(new Date()); 
		return formattedDate;
	}

	/**
	 * Gets the list.
	 *
	 * @param participants the participants
	 * @param tag the tag
	 * @return the list
	 */
	public static ArrayList<String> getList(List<HashMap<String, String>> participants,String tag){
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0; i<participants.size(); i++){
			list.add(participants.get(i).get(tag));
		}
		return list;
	}

	/**
	 * Creates the post hash map.
	 *
	 * @param post_text the post_text
	 * @param name_first the name_first
	 * @param name_last the name_last
	 * @param fbid the fbid
	 * @return the hash map
	 */
	public static HashMap<String, String> createPostHashMap(String post_text, String name_first ,String name_last, String fbid){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put(Tags.post_text, post_text);
		data.put(Tags.profileName, name_first+" "+name_last);
		data.put(Tags.profileImg, Tags.facebookApi+fbid+Tags.fbPictureSmall);

		return data;
	}


}
