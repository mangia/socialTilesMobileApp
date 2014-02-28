package dk.dtu.playware.socialtiles.db;

public class User {
	private String name_first;
	private String name_last;
	private String fbid;
	private int   	total_score;
	private int   	total_duration;
	private int   	num_achievements;
	private int 	user_id;
	private int 	user_id_server;
	private int   	is_uploaded;
	private int   selected;


	public User(int user_id, int user_id_server,String name_first, String name_last, String fbid, int total_score, 
			int total_duration, int num_achievments, int is_uploaded){
		this.name_first = name_first;
		this.name_last = name_last;
		this.fbid = fbid;
		this.total_score = total_score;
		this.total_duration = total_duration;
		this.num_achievements = num_achievments;
		this.user_id = user_id;
		this.user_id_server = user_id_server;
		this.setIsUploaded(is_uploaded);
		this.selected = 0;
	}


	public String getNameFirst() {
		return name_first;
	}
	public void setNameFirst(String name_first) {
		this.name_first = name_first;
	}

	public String getNameLast() {
		return name_last;
	}
	public void setNameLast(String name_last) {
		this.name_last = name_last;
	}

	public String getFbid() {
		return fbid;
	}
	public void setFbid(String fbid) {
		this.fbid = fbid;
	}

	public int getTotalScore() {
		return total_score;
	}
	public void setTotalScore(int total_score) {
		this.total_score = total_score;
	}

	public int getTotalDuration() {
		return total_duration;
	}
	public void setTotalDuration(int total_duration) {
		this.total_duration = total_duration;
	}

	public int getNumOfAchievements() {
		return num_achievements;
	}
	public void setNumOfAchievements(int num_achievements) {
		this.num_achievements = num_achievements;
	}

	public int getUserIdServer() {
		return user_id_server;
	}


	public void setUserIdServer(int user_id_server) {
		this.user_id_server = user_id_server;
	}



	public int getUserId() {
		return user_id;
	}


	public void setUserId(int user_id) {
		this.user_id = user_id;
	}


	public int getIsUploaded() {
		return is_uploaded;
	}


	public void setIsUploaded(int is_uploaded) {
		this.is_uploaded = is_uploaded;
	}


	public int getSelected() {
		return selected;
	}


	public void setSelected(int selected) {
		this.selected = selected;
	}

	public void toggleSelected(){
		if(this.selected ==1){
			this.selected = 0;
		}
		else{
			this.selected = 1;
		}
	}

}
