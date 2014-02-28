package dk.dtu.playware.socialtiles.datahandlers;

import java.util.ArrayList;
import java.util.HashMap;

import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class GoalsDataHandler.
 */
public class GoalsDataHandler {
	
	/** The goals. */
	private ArrayList<HashMap<String,String>> goals =  new  ArrayList<HashMap<String,String>>();
	
	/** The users. */
	private ArrayList<ArrayList<HashMap<String,String>>> users =  new ArrayList<ArrayList<HashMap<String,String>>>();
	
	/**
	 * Sets the goal data.
	 *
	 * @param temp the temp
	 */
	public void setGoalData(ArrayList<HashMap<String,String>>  temp) {
		goals.addAll(temp);
	}
	
	/**
	 * Sets the users data.
	 *
	 * @param temp the temp
	 */
	public void setUsersData(ArrayList<ArrayList<HashMap<String,String>>>  temp) {
		users.addAll(temp);
	}
	
	/**
	 * Gets the goal data.
	 *
	 * @return the goal data
	 */
	public ArrayList<HashMap<String,String>>  getGoalData() {
		return goals;
	}
	
	/**
	 * Gets the users data.
	 *
	 * @return the users data
	 */
	public ArrayList<ArrayList<HashMap<String, String>>>  getUsersData() {
		return users;
	}
	
	/**
	 * Gets the goal data el.
	 *
	 * @param position the position
	 * @return the goal data el
	 */
	public HashMap<String,String> getGoalDataEl(int position){
		return goals.get(position);
	}
	
	/**
	 * Gets the user data el.
	 *
	 * @param goalPosition the goal position
	 * @param userPosition the user position
	 * @return the user data el
	 */
	public HashMap<String,String> getUserDataEl(int goalPosition, int userPosition){
		return users.get(goalPosition).get(userPosition);
	}

	/**
	 * Adds the goal.
	 *
	 * @param i the i
	 * @param data the data
	 */
	public void addGoal(int i, HashMap<String,String> data) {
		// TODO Auto-generated method stub
		goals.add(i,data);
	}
	
	/**
	 * Adds the user.
	 *
	 * @param goalPosition the goal position
	 * @param i the i
	 * @param data the data
	 */
	public void addUser(int goalPosition, int i , HashMap<String,String> data){
		users.get(goalPosition).add(i, data);
	}

	/**
	 * Adds the group.
	 *
	 * @param goal the goal
	 * @return the int
	 */
	public int addGroup(HashMap<String, String> goal) {
		for(int i=0; i<goals.size();i++){
			if(goal.get(Tags.goal_id).equals(goals.get(i).get(Tags.goal_id))){
				return i;
			}
		}
		goals.add(goal);
		users.add(new ArrayList<HashMap<String,String>>());
		return goals.size()-1;
	}

	/**
	 * Adds the child.
	 *
	 * @param goalPos the goal pos
	 * @param user the user
	 * @return the int
	 */
	public int addChild(int goalPos, HashMap<String, String> user) {
		if(goalPos >= goals.size()){
			return -1;
		}
		
		users.get(goalPos).add(user);
		return users.get(goalPos).size()-1;
	}
}
