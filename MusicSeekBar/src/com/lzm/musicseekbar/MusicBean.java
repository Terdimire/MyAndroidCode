package com.lzm.musicseekbar;

public class MusicBean {

	private String musicName;
	private String musicPath;
	private int image;
	public String getMusicName() {
		return musicName;
	}
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}
	public String getMusicPath() {
		return musicPath;
	}
	public void setMusicPath(String musicPath) {
		this.musicPath = musicPath;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public MusicBean(String musicName, String musicPath, int image) {
		super();
		this.musicName = musicName;
		this.musicPath = musicPath;
		this.image = image;
	}
	
}
