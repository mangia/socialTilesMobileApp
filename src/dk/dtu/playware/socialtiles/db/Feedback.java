package dk.dtu.playware.socialtiles.db;

public class Feedback {
	private int feedback_id ;
	private int user_id;
	private String date_created;
	private String gamename;
	private int points;
	private int miss;
	private int duration;
	private int winner;
	private int level;
	private int size;
	private int score;
	private int is_uploaded;
	private String timestamp;
	
	public Feedback(int feedback_id,int user_id, String gamename, String date_created, String timestamp,  int points, int miss, int duration , int winner, int level, int size, int score){
		this.feedback_id =  feedback_id;
		this.user_id = user_id;
		this.date_created = date_created;
		this.timestamp = timestamp;
		this.gamename = gamename;
		this.points = points;
		this.miss= miss;
		this.duration = duration;
		this.winner= winner;
		this.level= level;
		this.size = size;
		this.score= score;
		this.is_uploaded = 0;
	}
	
	public int getFeedbackId() {
		return feedback_id;
	}
	public void setFeedbackId(int feedback_id) {
		this.feedback_id = feedback_id;
	}
	
	public int getUserId() {
		return user_id;
	}
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	
	public String getDateCreated() {
		return date_created;
	}
	public void setDateCreated(String date_created) {
		this.date_created = date_created;
	}
	
	public String getGamename() {
		return gamename;
	}
	public void setGamename(String gamename) {
		this.gamename = gamename;
	}
	
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	public int getMiss() {
		return miss;
	}
	public void setMiss(int miss) {
		this.miss = miss;
	}
	
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getWinner() {
		return winner;
	}
	public void setWinner(int winner) {
		this.winner = winner;
	}
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getIsUploaded() {
		return is_uploaded;
	}
	public void setIsUploaded(int is_uploaded) {
		this.is_uploaded = is_uploaded;
	}
}
