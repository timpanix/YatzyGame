package com.timpanix.yatzy;
import static com.timpanix.yatzy.Yatzy.*;  // import all static methods
import java.util.Scanner;

public class YatzyManager {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		boolean firstGame = true;
		boolean OptionsAvailable = false;
		boolean playGame;
		boolean restarted = false;
		boolean lastRound = false;
		Scanner sc = new Scanner(System.in);
		int currentPlayerIndex = -1;
		int lastPlayerOfRound = -1;			// the player who plays last during one round of the game
		int round;
		
		while(true){	// runs until the player(s) decide to quit the game	
			// instantiate a new game
			Yatzy game = new Yatzy();
			playGame = false;	// this will be used for the main menu
			round = 0;
			
			if(firstGame)
				game.displayTitle();	// display the tile (only if first game)
			
			//--------------------------- run the main menu ----------------------------------------------------
			
			
			while(!playGame){		// run the main menu until the player choses to play a new game, restore an existing game or to quit
				// display the main menu and return the chosen option
				switch(game.displayMainMenu(sc)){
				// option 1: play a new game
				case 1:
					playGame = true;		// this breaks the menu display loop
					restarted = false;		// this makes sure that after a restarted game was finished, the game cards ARE reset for the next new game
					break;
				// option 2: continue an existing game that was saved
				case 2:
					System.out.print("Restoring the game ");					
					game.displayProgressBar();	// this is not necessary, but it looks nice :-)
					// if it didn't work
					int[] roundAndNextPlayer = game.restoreGame();
					if(roundAndNextPlayer[0] == -1){	// - 1 flags no success
						System.out.println("Please select another option from the menu underneath.");
						game.displayLine();
					}else{
						System.out.println("\nSuccess!");
						round = roundAndNextPlayer[0];
						currentPlayerIndex = roundAndNextPlayer[1];
						game.displayCard();
						playGame = true;
						firstGame = false;	// to avoid asking for player's names etc.
						restarted = true;	// to avoid asking for starting order
					}
					break;
				// option 3: display the Yatzy rules
				case 3:
					game.displayWhichRule(sc);	// which rule?
					System.out.print("\nPlease press C to close. ");
					while(! sc.next().toUpperCase().equals("C")){
						System.out.println("This is not an option. Please try again:");
					}
					game.displayLine();
					break;
				// option 4: display the high scores
				case 4:
					game.displayHighScores();
					System.out.print("\nPlease press C to close. ");
					while(! sc.next().toUpperCase().equals("C")){
						System.out.print("This is not an option. Please try again:");
					}
					game.displayLine();
					break;
				// option 5: quit the game
				case 5:
					game.exitGame(sc, round, currentPlayerIndex, false); 	// the 4th argument "false" means that the player will not be asked 
				}														 	// if he/she wants to save the current game 
																			// (this wouldn't make any sense as the game was finished anyway)
			}
			
			// --------------------establish names & number of players and who starts the game ------------------------
			
			// if this is the first game to be played
			if(firstGame){
				game.displayLine();
				// ask how many players
				game.setNumOfPlayers(sc);
				// ask for player's names
				game.setPlayers(sc);
			// if it's not the first game or a restarted game, reset the player's game cards
			}else if(!firstGame && !restarted){
				for(int i = 0; i < getNumOfPlayers(); i++)
					getPlayers()[i].resetGameCard();
				lastRound = false;	
			}
			// if there is more than one player involved in the game
			if(getNumOfPlayers() > 1){
				if(!restarted)	// if the game wasn't restarted
					currentPlayerIndex = game.whoStartsTheGame(sc);	// ask which player would like to start the game
			
				// establish which player will be the last one to play in each round
				// (this will be used to display the gamecard after each round)
				lastPlayerOfRound = currentPlayerIndex - 1;		// eg. if currentPlayer is at index 3, the last player will be at index 2
				// special case: if the player who starts the game is at index 0, the last player is at the last index, NOt at -1!
				if(lastPlayerOfRound == -1)
					lastPlayerOfRound = getNumOfPlayers() - 1;
			}else{		// only one player
				currentPlayerIndex = lastPlayerOfRound = 0;		// the one player is the current player and the last player of the round
			}
			
			// ------------------------ START OF THE GAME ---------------------------------
			
			while(round < getNumOfRounds()){		// game runs until all rounds are played
				
				// display round number
				System.out.println("\n----------------");
				System.out.print("| Round No. ");
				if(++round < 10)	// if only one digit, add extra space to make it look nice
					System.out.print(" ");
				System.out.print(round + " |\n");
				System.out.println("----------------\n");

				// if this is the last round
				if(round == getNumOfRounds())
					lastRound = true;	// set the boolean value to true (this will be used to do different things after the last round)
				// ----------------- one round -------------------------------
				for(int i = 0; i < getNumOfPlayers(); i++){		// loops until one round is played
					// display name of the current player (only if more than one player)
					if(getNumOfPlayers() > 1)
						System.out.println("Player: " + getPlayers()[currentPlayerIndex].getName() + "\n");
					// roll the dice (max. 3 times)
					OptionsAvailable = game.rollDice(sc, currentPlayerIndex);
					
					if(OptionsAvailable){		// if options are available (normal case)
						// player selects a possible combination and the points are "written to card"
						game.makeSelection(sc, currentPlayerIndex);
					}else{						// otherwise 
						// player needs to select a field to be crossed out (set to 0)
						game.crossOutField(sc, currentPlayerIndex);
					}	
				
					// if the end of the round has been reached (but NOT the last round)
					if(currentPlayerIndex == lastPlayerOfRound && !lastRound){
						System.out.println("End of Round " + round);
						System.out.println("---------------\n");
						game.displayCard();			// display the game card
						if(!game.continueTheGame(sc)){	// and ask if player(s) want(s) to quit the game
							//showSaveOption = true;		//  switch the save option on and
							game.exitGame(sc, round, currentPlayerIndex, true);	// true means that the player will be asked if he/she would like to save the game
						}
					}
					// ----------------- end of one round ------------------------	
					
					// get next player's index
					currentPlayerIndex = game.getNextPlayer(currentPlayerIndex);
				}
				
			// ------------------- END OF GAME --------------------------------------------------------------------
				
				if(lastRound){	// if at the end of the last round
					// display a little title
					System.out.println("\t\t\t T H E   E N D");
					System.out.println("\t\t\t -------------");
					// display the name of the winner and the amount of points scored (only if more than 1 player)
					if(getNumOfPlayers() > 1){
						int winnerIndex = game.displayWinner();	
						game.displaySummary(sc);				// display summary of the game (complete game card)
						game.displayStatistics(winnerIndex);	// display statistics (showing the no. of wins for each player)
					// if there is only one player
					}else
						game.displaySummary(sc);		// just display the complete gamecard
					
					// update the highscores (loop through all players)
					for(int i = 0; i < getNumOfPlayers(); i++)
						if(!game.updateHighScores(i))		// display error message if they couldn't be updated (otherwise display nothing)
							System.out.println("Highscores could not be updated. There was a problem");
					firstGame = false;
					System.out.print("\nWhat would you like to do next? \nPlease press any letter of number to go back to the main menu:");
					sc.next();
					game.displayDoubleLine();
				} // end of if
			}	// end of while loop (one game)
		}	// end of while(true) loop
	} // end of main method
}	// end of class
