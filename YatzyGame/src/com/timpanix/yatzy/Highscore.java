package com.timpanix.yatzy;

import java.io.*;

public class Highscore implements Serializable, Comparable<Highscore>{

	private static final long serialVersionUID = -3444983906494454323L;	// needed for Serialization
	private String name;
	private String date;
	private int score;
	
	public Highscore(String name, String date, int score){
		this.name = name;
		this.date = date;
		this.score = score;
	}
	
	// getters and setters
	public int getScore(){
		return this.score;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getDate(){
		return this.date;
	}

	/**
	 * this is the implemented method from the Comparable interface
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	
	@Override
	public int compareTo(Highscore score) {
		
		return score.getScore() - this.getScore();	// sort in descending order!!!! (most points first!)
	}
}
