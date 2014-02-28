package dk.dtu.playware.socialtiles.api;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerApi.
 */
public class ServerApi {


	/** The client. */
	private static AsyncHttpClient client = new AsyncHttpClient();
	
	/** The Constant BASE_URL.The server  base url */
	private static final String BASE_URL    = "http://shielded-wave-8148.herokuapp.com/api/";
	
	/**
	 * the rest constants are endings to the base url depending the api functions the program 
	 * has to use
	 */
	
	/** The Constant users. */
	public  static final String users              = "users.php";
	
	/** The Constant groups. */
	public  static final String groups             = "groups.php";
	
	/** The Constant events. */
	public  static final String events             = "events.php";
	
	/** The Constant eventParticipants. */
	public 	 static final String eventParticipants  = "event_participants.php";
	
	/** The Constant posts. */
	public  static final String posts              = "posts.php";
	
	/** The Constant goals. */
	public  static final String goals              = "goals.php";
	
	/** The Constant feedbacks. */
	public  static final String feedbacks          = "feedbacks.php";
	
	/** The Constant highscores. */
	public  static final String highscores         = "highscores.php";
	
	/** The Constant friends. */
	public  static final String friends            = "friends.php";
	
	/** The Constant groupMember. */
	public  static final String groupMember        = "group_members.php";
	
	
	/**
	 * Gets the absolute url.
	 *
	 * @param relativeUrl the relative url
	 * @return the absolute url
	 */
	private static String getAbsoluteUrl(String relativeUrl) {
		Log.d(Tags.debugTag,"absolute url : "+BASE_URL + relativeUrl);
		return BASE_URL + relativeUrl;
	}

	/**
	 * http post request.
	 *
	 * @param url the url
	 * @param params the params
	 * @param responseHandler the response handler
	 */
	public static void post(String url,RequestParams params, AsyncHttpResponseHandler responseHandler){
		Log.d(Tags.debugTag,"url is "+AsyncHttpClient.getUrlWithQueryString(url, params));
		client.post(getAbsoluteUrl(url),params, responseHandler);
	}

	/**
	 * http gets requests.
	 *
	 * @param url the url
	 * @param params the params
	 * @param responseHandler the response handler
	 */
	public static void get(String url,RequestParams params, AsyncHttpResponseHandler responseHandler){
		client.get(getAbsoluteUrl(url),params, responseHandler);
	}

	
	/**
	 * functions that creates the params for the different kind of requests
	 */
	
	/**
	 * Adds the user params.
	 *
	 * @param first_name the first_name
	 * @param last_name the last_name
	 * @param fbid the fbid
	 * @return the request params
	 */
	public static RequestParams addUserParams(String first_name, String last_name, String fbid){
		RequestParams params = new RequestParams();
		params.put(Tags.first_name, first_name);
		params.put(Tags.last_name, last_name);
		params.put(Tags.fbid, fbid);
		return params;
	}

	/**
	 * Adds the post.
	 *
	 * @param creator the creator
	 * @param posted_to the posted_to
	 * @param post_text the post_text
	 * @return the request params
	 */
	public static RequestParams addPost(int creator , int posted_to, String post_text){
		RequestParams params  = new RequestParams();
		params.put(Tags.creator, ""+creator);
		params.put(Tags.post_text, post_text);
		params.put(Tags.posted_to, ""+posted_to);
		params.put(Tags.post_date, Tags.getDate());
		return params;
	}

	/**
	 * Gets the posts.
	 *
	 * @param user_id the user_id
	 * @return the posts
	 */
	public static RequestParams getPosts(int user_id){
		RequestParams params  = new RequestParams();
		params.put(Tags.op, "user");
		params.put(Tags.user_id, ""+user_id);
		return params;
	}

	/**
	 * Gets the group posts.
	 *
	 * @param group_entity the group_entity
	 * @return the group posts
	 */
	public static RequestParams getGroupPosts(int group_entity) {
		RequestParams params = new RequestParams();
		params.put(Tags.op, "group");
		params.put(Tags.posted_to, ""+group_entity);
		Log.d(Tags.debugTag, "params are "+params);
		return params;
	}

	/**
	 * Gets the user posts.
	 *
	 * @param user_id the user_id
	 * @return the user posts
	 */
	public static RequestParams getUserPosts(int user_id) {
		RequestParams params = new RequestParams();
		params.put(Tags.op, "user");
		params.put(Tags.user_id, ""+user_id);
		Log.d(Tags.debugTag, "params are "+params);
		return params;
	}

	/**
	 * Gets the event posts.
	 *
	 * @param event_entity the event_entity
	 * @return the event posts
	 */
	public static RequestParams getEventPosts(int event_entity) {
		RequestParams params = new RequestParams();
		params.put(Tags.op, "event");
		params.put(Tags.posted_to, ""+event_entity);
		return params;
	}

	/**
	 * Gets the user group.
	 *
	 * @return the user group
	 */
	public static RequestParams getUserGroup() {
		RequestParams params = new RequestParams();
		params.put(Tags.op, "user_groups");
		params.put(Tags.user_id, ""+Tags.User.user_id);
		Log.d(Tags.debugTag,"params are  "+ params);
		return params;
	}
	
	/**
	 * Gets the user events.
	 *
	 * @return the user events
	 */
	public static RequestParams getUserEvents() {
		RequestParams params = new RequestParams();
		params.put(Tags.op, "user_events");
		params.put(Tags.user_id, ""+Tags.User.user_id);
		Log.d(Tags.debugTag,"params are  "+ params);
		return params;
	}

	/**
	 * Gets the group members.
	 *
	 * @param group_id the group_id
	 * @return the group members
	 */
	public static RequestParams getGroupMembers(int group_id) {
		RequestParams params = new RequestParams();
		params.put(Tags.group_id, ""+group_id);
		params.put(Tags.op, "user");
		Log.d(Tags.debugTag, "paramsa are "+ params);
		return params;
	}
	
	/**
	 * Gets the friends.
	 *
	 * @param user_id the user_id
	 * @return the friends
	 */
	public static RequestParams getFriends(int user_id) {
		RequestParams params = new RequestParams();
		params.put(Tags.user_id, ""+user_id);
		params.put(Tags.op, "friends");
		Log.d(Tags.debugTag,"params are "+params);
		return params;
	}

	/**
	 * Adds the group members.
	 *
	 * @param group_id the group_id
	 * @return the request params
	 */
	public static RequestParams addGroupMembers(String group_id){
		RequestParams params = new RequestParams();
		params.put(Tags.group_id, ""+group_id);
		params.put(Tags.op, "users");
		return params;
	}

	/**
	 * Adds the group.
	 *
	 * @param name the name
	 * @param description the description
	 * @return the request params
	 */
	public static RequestParams addGroup(String name, String description) {
		RequestParams params = new RequestParams();
		params.put(Tags.name, name);
		params.put(Tags.description, description);
		params.put(Tags.date_created, Tags.getDate());
		params.put(Tags.creator, ""+Tags.User.user_id);
		return params;
	}

	/**
	 * Adds the group members.
	 *
	 * @param group_id the group_id
	 * @param list the list
	 * @return the request params
	 */
	public static RequestParams addGroupMembers(String group_id, ArrayList<String> list) {
		RequestParams params = new RequestParams();
		JSONArray array = new JSONArray(list);
		params.put(Tags.op, "multiple");
		params.put(Tags.group_id, group_id);
		params.put(Tags.members, array.toString());
		Log.d(Tags.debugTag, "parameters are "+params);
		return params;
	}

	/**
	 * Adds the group member.
	 *
	 * @param group_id the group_id
	 * @param user_id the user_id
	 * @return the request params
	 */
	public static RequestParams addGroupMember(String group_id,String user_id) {
		RequestParams params = new RequestParams();
		params.put(Tags.op, "single");		
		params.put(Tags.group_id, group_id);
		params.put(Tags.user_id, user_id);
		return params;
	}

	/**
	 * Gets the members.
	 *
	 * @param event_id the event_id
	 * @return the members
	 */
	public static RequestParams getMembers(int event_id) {
		RequestParams params = new RequestParams();
		params.put(Tags.op, "single");		
		params.put(Tags.event_id, ""+event_id);
		params.put(Tags.op, "user");
		return params;
	}

	/**
	 * Adds the event.
	 *
	 * @param hm the hm
	 * @return the request params
	 */
	public static RequestParams addEvent(HashMap<String, String> hm) {
		RequestParams params = new RequestParams(hm);
		Log.d(Tags.debugTag,"parameters are: " + params);
		return params;
	}

	/**
	 * Adds the event members.
	 *
	 * @param event_id the event_id
	 * @param list the list
	 * @return the request params
	 */
	public static RequestParams addEventMembers(String event_id,
			ArrayList<String> list) {
		RequestParams params = new RequestParams();
		JSONArray array = new JSONArray(list);
		params.put(Tags.op, "multiple");
		params.put(Tags.event_id, event_id);
		params.put(Tags.participants, array.toString());
		Log.d(Tags.debugTag, "parameters are "+params);
		return params;
	}

}
