package dk.dtu.playware.socialtiles.datahandlers;

import java.util.ArrayList;
import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class FeedsDataHandler.
 */
public class FeedsDataHandler {



	/** The m list strings. */
	ArrayList<HashMap<String, String>> mListStrings = new ArrayList<HashMap<String, String>>();
	
	/**
	 * Instantiates a new feeds data handler.
	 */
	public FeedsDataHandler(){
		
	}
	
	/**
	 * Sets the data.
	 *
	 * @param temp the temp
	 */
	public void setData(ArrayList<HashMap<String, String>> temp) {
		mListStrings.addAll(temp);
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public ArrayList<HashMap<String, String>> getData() {
		return mListStrings;
	}

	/**
	 * Gets the data el.
	 *
	 * @param position the position
	 * @return the data el
	 */
	public HashMap<String, String> getDataEl(int position){
		return mListStrings.get(position);
	}

	/**
	 * Adds the.
	 *
	 * @param i the i
	 * @param data the data
	 */
	public void add(int i, HashMap<String, String> data) {
		// TODO Auto-generated method stub
		mListStrings.add(i,data);
	}


}
