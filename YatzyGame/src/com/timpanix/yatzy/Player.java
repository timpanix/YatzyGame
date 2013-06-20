package com.timpanix.yatzy;
import static com.timpanix.yatzy.Yatzy.getNumOfFields;

import java.io.Serializable;

public class Player implements Serializable{

	private static final long serialVersionUID = -8449759513718093273L;		// needed for serialization
	private String name;
	private int[] gameCard;		// stores all the scored values of the player
	private int noOfWins;
	
	// constructor
	public Player(){
		this.noOfWins = 0;
		gameCard = new int[getNumOfFields()];
		resetGameCard();
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getNoOfWins() {
		return noOfWins;
	}
	
	public void updateNoOfWins(){
		noOfWins++;
	}

	public int[] getGameCard(){
		return this.gameCard;
	}
	
	public int getGameCard(int index){
		return this.gameCard[index];
	}
	
	/**
	 * this method resets the game card (all values are set to - 1). 
	 * The default value is - 1 as the value 0 has a special meaning in yatzy 
	 * (it indicates that a particular field was crossed out)
	 */
	public void resetGameCard(){
		for(int i = 0; i < getGameCard().length; i++){
			this.gameCard[i] = -1;		// default value is -1 as 0 has a special meaning in yatzy
		}								// (0 means that the player crossed out a field)
		// special case: totals must have default value of 0, otherwise the sums are incorrect:
		this.gameCard[getNumOfFields() - 1] = 0;	// totals are at the last index
	}
	
	/**
	 * this method is used to update the game card.
	 * 
	 * @param: the index of the field to be updated, the value scored for that field
	 */
	public void updateGameCard(int index, int value){
		
		// if additional yatzy bonus was rolled
		if(index == 14){
			// check if it is the player's first additional yatzy bonus
			if(getGameCard()[index] == -1)
				this.gameCard[index] = value;	// if first add. bonus --> set value to 100
			else
				this.gameCard[index] += value;	// if not first add. bonus --> add 100 to current value
		// nothing special: normal update
		}else
			this.gameCard[index] = value;
		
		// calculate new total of the player
		this.gameCard[15] += value;
	}
}
