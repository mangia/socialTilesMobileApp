package dk.dtu.playware.socialtiles.datahandlers;

import java.util.ArrayList;
import java.util.HashMap;

import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupMembersEventsHandler.
 */
public class GroupMembersEventsHandler {
	
	/** The groups. */
	private ArrayList<HashMap<String,String>> groups =  new  ArrayList<HashMap<String,String>>();
	
	/** The users. */
	private ArrayList<ArrayList<HashMap<String,String>>> users =  new ArrayList<ArrayList<HashMap<String,String>>>();
	
	/**
	 * Gets the groups data.
	 *
	 * @return the groups data
	 */
	public ArrayList<HashMap<String,String>> getGroupsData() {
		return groups;
	}
	
	/**
	 * Sets the groups data.
	 *
	 * @param groups the groups
	 */
	public void setGroupsData(ArrayList<HashMap<String,String>> groups) {
		this.groups.addAll(groups);
	}
	
	/**
	 * Gets the groups data el.
	 *
	 * @param position the position
	 * @return the groups data el
	 */
	public HashMap<String,String> getGroupsDataEl(int position){
		return groups.get(position);
	}
	
	/**
	 * Gets the users data.
	 *
	 * @return the users data
	 */
	public ArrayList<ArrayList<HashMap<String,String>>> getUsersData() {
		return users;
	}
	
	/**
	 * Sets the users data.
	 *
	 * @param users the users
	 */
	public void setUsersData(ArrayList<ArrayList<HashMap<String,String>>> users) {
		this.users.addAll(users);
	}
	
	/**
	 * Gets the user data el.
	 *
	 * @param groupPosition the group position
	 * @param userPosition the user position
	 * @return the user data el
	 */
	public HashMap<String,String> getUserDataEl(int groupPosition, int userPosition){
		return users.get(groupPosition).get(userPosition);
	}
	
	/**
	 * Adds the group.
	 *
	 * @param group the group
	 * @return the int
	 */
	public int addGroup(HashMap<String, String> group){
		for(int i=0; i<groups.size();i++){
			if(group.get(Tags.group_id).equals(groups.get(i).get(Tags.group_id))){
				return i;
			}
		}
		groups.add(group);
		users.add(new ArrayList<HashMap<String,String>>());
		return groups.size()-1;
	}
	
	/**
	 * Gets the group pos.
	 *
	 * @param tag the tag
	 * @param value the value
	 * @return the group pos
	 */
	public int getGroupPos(String tag, String value){
		for(int i =0; i<groups.size();i++){
			if(groups.get(i).get(tag).equals(value)){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Adds the child.
	 *
	 * @param groupPos the group pos
	 * @param child the child
	 * @return the int
	 */
	public int addChild(int groupPos, HashMap<String, String> child){
		if(groupPos >= groups.size()){
			return -1;
		}
		
		users.get(groupPos).add(child);
		return users.get(groupPos).size()-1;
	}
	
}
