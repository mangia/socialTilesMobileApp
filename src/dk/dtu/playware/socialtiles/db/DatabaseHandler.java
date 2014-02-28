package dk.dtu.playware.socialtiles.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import dk.dtu.playware.socialtiles.tags.Tags;

// TODO: Auto-generated Javadoc
/**
 * The Class DatabaseHandler.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
	
	/** The Constant DATABASE_VERSION. */
	private static final int DATABASE_VERSION = 1;
	// Database Name
	/** The Constant DATABASE_NAME. */
	private static final String DATABASE_NAME = "socialtilesdb";
	
	/** The Constant TABLE_FEEDBACKS. */
	private static final String TABLE_FEEDBACKS = "feedbacks";
	
	/** The Constant TABLE_USER. */
	private static final String TABLE_USER = "user";
	
	/** The Constant TABLE_HIGHSCORES. */
	private static final String TABLE_HIGHSCORES = "highscores";
	
	/** The Constant HIGHSCORE_ID. */
	private static final String HIGHSCORE_ID = "highscore_id";
	
	/** The Constant GAMENAME. */
	private static final String GAMENAME = "gamename";
	
	/** The Constant USER_ID. */
	private static final String USER_ID = "user_id";
	
	/** The Constant HIGHSCORE. */
	private static final String HIGHSCORE = "highscore";
	
	/** The Constant FEEDBACK_ID. */
	private static final String FEEDBACK_ID = "feedback_id";
	
	/** The Constant DATE_CREATED. */
	private static final String DATE_CREATED = "date_created";
	
	/** The Constant POINTS. */
	private static final String POINTS = "points";     
	
	/** The Constant MISS. */
	private static final String MISS   = "miss";
	
	/** The Constant DURATION. */
	private static final String DURATION = "duration";
	
	/** The Constant WINNER. */
	private static final String WINNER = "winner";
	
	/** The Constant LEVEL. */
	private static final String LEVEL  = "level";
	
	/** The Constant SIZE. */
	private static final String SIZE = "size";
	
	/** The Constant SCORE. */
	private static final String SCORE = "score";
	
	/** The Constant TIMESTAMP. */
	private static final String TIMESTAMP = "timestamp";
	
	/** The Constant NAME_FIRST. */
	private static final String NAME_FIRST = "";
	
	/** The Constant NAME_LAST. */
	private static final String NAME_LAST  = "";
	
	/** The Constant FBID. */
	private static final String FBID = "fbid";
	
	/** The Constant TOTAL_SCORE. */
	private static final String TOTAL_SCORE = "total_score";
	
	/** The Constant TOTAL_DURATION. */
	private static final String TOTAL_DURATION = "total_duration";
	
	/** The Constant NUM_ACHIEVMENTS. */
	private static final String NUM_ACHIEVMENTS = "num_achievments";
	
	/** The Constant IS_UPLOADED. */
	private static final String IS_UPLOADED = "is_uploaded";
	private static final String USER_ID_SERVER = "user_id_server";
	
	/**
	 * Instantiates a new database handler.
	 *
	 * @param context the context
	 */
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_HIGHSCORES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_HIGHSCORES + " ("+ 
				HIGHSCORE_ID 	+ " INTEGER PRIMARY KEY, " + 
				GAMENAME 		+ " TEXT, "+ 
				HIGHSCORE       + " INTEGER, "+
				USER_ID         + " INTEGER, "+
				IS_UPLOADED     + " INTEGER "+" ) ";
		Log.d("Social Tiles", CREATE_HIGHSCORES_TABLE);
		db.execSQL(CREATE_HIGHSCORES_TABLE );
		
		String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USER + "("+ 
				USER_ID 		+ " INTEGER PRIMARY KEY," + 
				NAME_FIRST 		+ " TEXT,"+ 
				NAME_LAST 		+ " TEXT,"+ 
				TOTAL_SCORE   	+ " INTEGER,"+
			    TOTAL_DURATION 	+ " INTEGER," + 
				NUM_ACHIEVMENTS + " INTEGER," + 
				IS_UPLOADED     + "INTEGER"+")";
		db.execSQL(CREATE_USERS_TABLE);
		
		String CREATE_FEEDBAKCS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FEEDBACKS + " ("+ 
				FEEDBACK_ID 	+ " INTEGER PRIMARY KEY," + 
				USER_ID         + " INTEGER, " +
				GAMENAME 		+ " TEXT, "+ 
				DATE_CREATED 	+ " TEXT, "+ 
				TIMESTAMP 		+ " TEXT, " +
				POINTS 			+ " INTEGER, " + 
				MISS 			+ " INTEGER, " + 
				DURATION 		+ " INTEGER, " + 
				WINNER 			+ " INTEGER, " + 
				LEVEL 			+ " INTEGER, " + 
				SIZE 			+ " INTEGER, " + 
				SCORE 			+ " INTEGER, " +
				IS_UPLOADED     + " INTEGER"+")";
		Log.d("Social Tiles", CREATE_FEEDBAKCS_TABLE);
		db.execSQL(CREATE_FEEDBAKCS_TABLE);
		
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACKS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGHSCORES);
		// Create tables again
		onCreate(db);
	}
	
	
	public long addFeedback(int user_id,String gamename, String date_created,String timestamp,  int points, int miss, int duration, int winner, int level, int size, int score){

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(USER_ID, user_id);
		values.put(GAMENAME, gamename);
		values.put(DATE_CREATED, date_created);
		values.put(TIMESTAMP, timestamp);
		values.put(POINTS, points);
		values.put(MISS, miss);
		values.put(DURATION, duration);
		values.put(WINNER, winner);
		values.put(LEVEL, level);
		values.put(SIZE, size);
		values.put(SCORE, score);
		values.put(IS_UPLOADED, 0);
		long feedback_id = db.insert(TABLE_FEEDBACKS, null, values);
		db.close();
		return feedback_id;
	}

	public Feedback getFeedback(int feedback_id){
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM " + TABLE_FEEDBACKS + " WHERE "
				+ FEEDBACK_ID + " = " + feedback_id;
		Log.d("Social Tiles v2", selectQuery);

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor != null)
			cursor.moveToFirst();
		//user_id, date_created, gamename, points, miss, duration , winner, level, size, score
		Feedback feedback = new Feedback(	
				Integer.parseInt(cursor.getString(0)), 	//feedback_id
				Integer.parseInt(cursor.getString(1)), 	//user_id
				cursor.getString(2), 					//game name
				cursor.getString(3),                    //date_created
				cursor.getString(4),
				Integer.parseInt(cursor.getString(5)),	//points 
				Integer.parseInt(cursor.getString(6)),  //miss
				Integer.parseInt(cursor.getString(7)),  //duration
				Integer.parseInt(cursor.getString(8)),  //winner
				Integer.parseInt(cursor.getString(9)),  //level
				Integer.parseInt(cursor.getString(10)),  //size
				Integer.parseInt(cursor.getString(11))   //score
				);

		// return contact
		cursor.close();
		db.close();

		return feedback;
	}

	public ArrayList<Feedback> getAllFeedbacks(int user_id){
		ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM " + TABLE_FEEDBACKS + " WHERE "
				+ USER_ID + " = " + user_id;
		Log.d("Social Tiles v2", selectQuery);

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Feedback feedback = new Feedback(	
						Integer.parseInt(cursor.getString(0)), 	//feedback_id
						Integer.parseInt(cursor.getString(1)), 	//user_id
						cursor.getString(2), 					//game name
						cursor.getString(3),                    //date_created
						cursor.getString(4),
						Integer.parseInt(cursor.getString(5)),	//points 
						Integer.parseInt(cursor.getString(6)),  //miss
						Integer.parseInt(cursor.getString(7)),  //duration
						Integer.parseInt(cursor.getString(8)),  //winner
						Integer.parseInt(cursor.getString(9)),  //level
						Integer.parseInt(cursor.getString(10)),  //size
						Integer.parseInt(cursor.getString(11))   //score
						);
				feedbacks.add(feedback);
			} while (cursor.moveToNext());
		}

		// return contact list
		cursor.close();
		db.close();
		return feedbacks;
	}

	public ArrayList<Feedback> getAllFeedbacksNotUploaded(){
		ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM " + TABLE_FEEDBACKS + " WHERE "
				+ IS_UPLOADED + " = " + 0;
		Log.d("Social Tiles v2", selectQuery);

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Feedback feedback = new Feedback(	
						Integer.parseInt(cursor.getString(0)), 	//feedback_id
						Integer.parseInt(cursor.getString(1)), 	//user_id
						cursor.getString(2), 					//game name
						cursor.getString(3),                    //date_created
						cursor.getString(4),
						Integer.parseInt(cursor.getString(5)),	//points 
						Integer.parseInt(cursor.getString(6)),  //miss
						Integer.parseInt(cursor.getString(7)),  //duration
						Integer.parseInt(cursor.getString(8)),  //winner
						Integer.parseInt(cursor.getString(9)),  //level
						Integer.parseInt(cursor.getString(10)),  //size
						Integer.parseInt(cursor.getString(11))   //score
						);
				feedbacks.add(feedback);
			} while (cursor.moveToNext());
		}

		// return contact list
		cursor.close();
		db.close();
		return feedbacks;
	}


	public long addHighscore(String gamename, int user_id, int highscore){
		SQLiteDatabase db;
		ArrayList<Highscore> currentHighscores = getAllHighscores(user_id);

		int size = currentHighscores.size();
		for(int i=0; i<size; i++){
			if(gamename.equals(currentHighscores.get(i).getGamename())){
				if(currentHighscores.get(i).getHighscore()<highscore){
					String strFilter = HIGHSCORE_ID+" = " +currentHighscores.get(i).getHighscore();
					ContentValues args = new ContentValues();
					args.put(HIGHSCORE, highscore);
					args.put(IS_UPLOADED, 0);
					db = this.getWritableDatabase();
					db.update(TABLE_HIGHSCORES, args, strFilter, null);
					db.close();
					return 0;
				}
				else{
					//db.close();
					return -1;
				}
			}
		}
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(USER_ID,user_id);
		values.put(GAMENAME, gamename);
		values.put(HIGHSCORE, highscore);
		values.put(IS_UPLOADED, 0);
		long highscore_id = db.insert(TABLE_HIGHSCORES, null, values);
		db.close();
		return highscore_id;
	}

	public Highscore getHighscore(int highscore_id){
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM " + TABLE_HIGHSCORES + " WHERE "
				+ HIGHSCORE_ID + " = " + highscore_id;
		Log.d("Social Tiles v2", selectQuery);

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor != null)
			cursor.moveToFirst();

		Highscore highscore =  new Highscore(
				Integer.parseInt(cursor.getString(0)), 
				cursor.getString(1), 
				Integer.parseInt(cursor.getString(2)), 
				Integer.parseInt(cursor.getString(3)), 
				Integer.parseInt(cursor.getString(4)));

		cursor.close();
		db.close();
		return highscore;
	}

	public ArrayList<Highscore> getAllHighscores(int user_id){
		ArrayList<Highscore> highscores = new ArrayList<Highscore>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM " + TABLE_HIGHSCORES + " WHERE "
				+ USER_ID + " = " + user_id;
		Log.d("Social Tiles v2", selectQuery);

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Highscore highscore =  new Highscore(
						Integer.parseInt(cursor.getString(0)), 
						cursor.getString(1), 
						Integer.parseInt(cursor.getString(2)), 
						Integer.parseInt(cursor.getString(3)), 
						Integer.parseInt(cursor.getString(4)));
				highscores.add(highscore);
			} while (cursor.moveToNext());
		}

		// return contact list
		cursor.close();
		db.close();
		return highscores;
	}

	public ArrayList<Highscore> getAllHighscoresNotUploaded(){
		ArrayList<Highscore> highscores = new ArrayList<Highscore>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM " + TABLE_HIGHSCORES + " WHERE "
				+ IS_UPLOADED + " = " + 0;;
				Log.d("Social Tiles v2", selectQuery);

				Cursor cursor = db.rawQuery(selectQuery, null);

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						Highscore highscore =  new Highscore(
								Integer.parseInt(cursor.getString(0)), 
								cursor.getString(1), 
								Integer.parseInt(cursor.getString(2)), 
								Integer.parseInt(cursor.getString(3)), 
								Integer.parseInt(cursor.getString(4)));
						highscores.add(highscore);
					} while (cursor.moveToNext());
				}

				// return contact list
				cursor.close();
				db.close();
				return highscores;

	}


	public long addUser(String first_name, String last_name, String fbid, int user_id_server, int total_score, int total_duration, int num_of_achievments){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(NAME_FIRST, first_name);
		values.put(NAME_LAST, last_name);
		values.put(FBID, fbid);
		values.put(USER_ID_SERVER, user_id_server);
		values.put(NUM_ACHIEVMENTS, num_of_achievments);
		values.put(FBID, "fbid");
		values.put(TOTAL_DURATION, total_duration);
		values.put(TOTAL_SCORE, total_score);
		values.put(IS_UPLOADED, 0);



		long user_id = db.insert(TABLE_USER, null, values);
		return user_id;
	}

	public User getUser(int user_id){
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
				+ USER_ID + " = " + user_id;
		Log.d("Social Tiles v2", selectQuery);

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor != null)
			cursor.moveToFirst();
		User user = new User(
				Integer.parseInt(cursor.getString(0)), 
				Integer.parseInt(cursor.getString(1)), 
				cursor.getString(2), 
				cursor.getString(3), 
				cursor.getString(4), 
				Integer.parseInt(cursor.getString(5)), 
				Integer.parseInt(cursor.getString(6)), 
				Integer.parseInt(cursor.getString(7)),
				Integer.parseInt(cursor.getString(8)));
		return user;
	}

	public ArrayList<User> getAllUsers(){
		ArrayList<User> users = new ArrayList<User>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM " + TABLE_USER;
		Log.d(Tags.debugTag, selectQuery);

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {

				for(int i=0 ; i<cursor.getColumnCount(); i++){
					Log.d(Tags.debugTag, cursor.getColumnName(i) +" "+cursor.getString(i));
				}

				User user = new User(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8));
				users.add(user);
				//				User user = new User(
				//						Integer.parseInt(cursor.getString(0)), 
				//						Integer.parseInt(cursor.getString(1)), 
				//						cursor.getString(2), 
				//						cursor.getString(3), 
				//						cursor.getString(4), 
				//						Integer.parseInt(cursor.getString(5)), 
				//						Integer.parseInt(cursor.getString(6)), 
				//						Integer.parseInt(cursor.getString(7)),
				//						Integer.parseInt(cursor.getString(8)));
				//				users.add(user);
			} while (cursor.moveToNext());

		}

		return users;
	}

	public ArrayList<User> getAllUsersNotUploaded(){
		ArrayList<User> users = new ArrayList<User>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM " + TABLE_USER;
		Log.d("Social Tiles v2", selectQuery);

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				User user = new User(
						Integer.parseInt(cursor.getString(0)), 
						Integer.parseInt(cursor.getString(1)), 
						cursor.getString(2), 
						cursor.getString(3), 
						cursor.getString(4), 
						Integer.parseInt(cursor.getString(5)), 
						Integer.parseInt(cursor.getString(6)), 
						Integer.parseInt(cursor.getString(7)),
						Integer.parseInt(cursor.getString(8)));
				users.add(user);
			} while (cursor.moveToNext());

		}

		return users;
	}
	
}
