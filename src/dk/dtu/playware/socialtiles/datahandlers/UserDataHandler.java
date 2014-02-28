package dk.dtu.playware.socialtiles.datahandlers;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDataHandler.
 */
public class UserDataHandler {
	
	/** The m list strings. */
	ArrayList<HashMap<String,String>> mListStrings = new ArrayList<HashMap<String,String>>();
	
	/**
	 * Sets the data.
	 *
	 * @param temp the temp
	 */
	public void setData(ArrayList<HashMap<String,String>> temp) {
		mListStrings.addAll(temp);
	}

	/**
	 * Gets the whole list.
	 *
	 * @return the data
	 */
	public ArrayList<HashMap<String,String>> getData() {
		//Log.d(Tags.debugTag, "data are " + mListStrings);
		return mListStrings;
	}

	/**
	 * Gets the element of the list at the specific position.
	 *
	 * @param position the position
	 * @return the data el
	 */
	public HashMap<String,String> getDataEl(int position){
		return mListStrings.get(position);
	}

	/**
	 * Adds a new entry at a specific place
	 *
	 * @param i the i
	 * @param entry the entry
	 */
	public void add(int i, HashMap<String,String> entry) {
		// TODO Auto-generated method stub
		mListStrings.add(i,entry);
		
	}
	
	/**
	 * Adds a new entry
	 *
	 * @param entry the entry
	 */
	public void add(HashMap<String,String> entry) {
		// TODO Auto-generated method stub
		//Log.d(Tags.debugTag, "entry is "+ entry);
		mListStrings.add(entry);
		
	}
	
	/**
	 * Gets a string array list with only a specific attribute of the data.
	 *
	 * @param tag the tag
	 * @return the attributes
	 */
	public ArrayList<String > getAttributes(String tag){
		ArrayList<String> attr = new ArrayList<String>();
		
		for(int i =0 ; i<mListStrings.size(); i++){
			attr.add(mListStrings.get(i).get(tag));
		}
		
		return attr;
	}
	
	/**
	 * Clear.
	 */
	public void clear(){
		mListStrings.clear();
	}
}
