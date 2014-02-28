package dk.dtu.playware.socialtiles.db;

public class Highscore {
	private String gamename;
	private int   highscore;
	private int  user_id;
	private int is_uploaded;
	private int highscore_id;
	


	public Highscore(int highscore_id,String gamename, int highscore, int user_id, int is_uploaded){
		this.gamename = gamename;
		this.highscore = highscore;
		this.user_id= user_id;
		this.setIsUploaded(is_uploaded);
		this.setHighscoreId(highscore_id);
	}
	
	public String getGamename() {
		return gamename;
	}
	public void setGamename(String gamename) {
		this.gamename = gamename;
	}
	
	public int getHighscore() {
		return highscore;
	}
	public void setHighscore(int highscore) {
		this.highscore = highscore;
	}

	public int getIsUploaded() {
		return is_uploaded;
	}

	public void setIsUploaded(int is_uploaded) {
		this.is_uploaded = is_uploaded;
	}
	
	public int getUserId() {
		return user_id;
	}

	public void setUserId(int user_id) {
		this.user_id = user_id;
	}

	public int getHighscoreId() {
		return highscore_id;
	}

	public void setHighscoreId(int highscore_id) {
		this.highscore_id = highscore_id;
	}
}
