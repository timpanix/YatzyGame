package com.timpanix.yatzy;


public interface Pausable{

	public boolean saveGameState(Player[] players, int round, int currentPlayer);
	public int[] restoreGame();
}


