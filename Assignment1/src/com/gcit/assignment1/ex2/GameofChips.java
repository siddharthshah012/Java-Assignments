package com.gcit.assignment1.ex2;


import java.util.Scanner;

public class GameofChips {
	
	@SuppressWarnings("resource")
	public void gamebegins(int initialPile, String firstPlayer, String secondPlayer){
		Scanner scanner = new Scanner(System.in);
		
		int bit =0; // 0 for first player turn and 1 for second 
		int ChipsFirstPlayer = 0;
		int ChipsSecondPlayer =0;
		int ChipsTotalFir =0;
		int ChipsTotalSec =0;
		
		while (initialPile >0)
		{
			//System.out.println("value of initialpile at start of while" + initialPile);
			System.out.println(firstPlayer+ " has "+ ChipsTotalFir+" chips");
			System.out.println(secondPlayer+ " has " + ChipsTotalSec+" chips");
			
			if (bit ==0){
				System.out.println("It is your turn," + firstPlayer);
				
				System.out.println("There are "+initialPile +" chips remaining");
				if (initialPile == 1){
					System.out.println("How many will you take "+ ChipsFirstPlayer+"?");
					ChipsFirstPlayer = scanner.nextInt();
				}
				else{
					System.out.println("You may take any number of chips from 1 to "+initialPile/2 +".  How many will you take, "+ firstPlayer+"?");
					ChipsFirstPlayer = scanner.nextInt();
				}
				
				if (ChipsFirstPlayer == 0){
					System.out.println("Illegal move: you must take at least one chip.");
					ChipsFirstPlayer = scanner.nextInt();
				}
				else if (ChipsFirstPlayer > initialPile/2){
					if (initialPile ==1){
						ChipsTotalFir = ChipsTotalFir + ChipsFirstPlayer;
						initialPile = initialPile-ChipsFirstPlayer;
						bit = 1;
						break;
					}
					else 
					{
						System.out.println("Illegal move: you may not take more than "+initialPile/2 +" chips.");
						ChipsFirstPlayer = scanner.nextInt();
					}			
				}
				ChipsTotalFir = ChipsTotalFir + ChipsFirstPlayer;
				initialPile = initialPile-ChipsFirstPlayer;
				bit = 1;
			}
			else {
				System.out.println("It is your turn, " + secondPlayer);
				System.out.println("There are "+initialPile +" chips remaining");
				if (initialPile == 1){
					System.out.println("How many will you take "+ ChipsSecondPlayer+" ?");
					ChipsSecondPlayer = scanner.nextInt();
				}
				else{
					System.out.println("You may take any number of chips from 1 to "+initialPile/2 +".  How many will you take,"+ secondPlayer+"?");
					ChipsSecondPlayer = scanner.nextInt();
				}
				
				if (ChipsSecondPlayer == 0){
					System.out.println("Illegal move: you must take at least one chip.");
					ChipsSecondPlayer = scanner.nextInt();
				}
				if (ChipsSecondPlayer > initialPile/2){
					if (initialPile == 1){
						ChipsTotalSec = ChipsTotalSec + ChipsSecondPlayer;
						initialPile = initialPile - ChipsSecondPlayer;
						bit =0;
						break;
					}
					else{
						System.out.println("Illegal move: you may not take more than "+initialPile/2 +" chips.");
						ChipsSecondPlayer = scanner.nextInt();
					}
				}
				ChipsTotalSec = ChipsTotalSec + ChipsSecondPlayer;
				initialPile = initialPile - ChipsSecondPlayer;
				bit =0;
			}
		}
		
		if (initialPile ==0){
			System.out.println(firstPlayer+ " has " +ChipsTotalFir+" chips");
			System.out.println(secondPlayer+ " has " +ChipsTotalSec+" chips");
			
			if (ChipsTotalFir> ChipsTotalSec){
				System.out.println(firstPlayer+ " Wins!!");
			}else{
				System.out.println(secondPlayer+ " Wins!!");
			}
		}
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GameofChips gameofchips = new GameofChips();
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("What is the name of the first player?");
		String firstPlayer = scanner.next();
		
		System.out.println("What is the name of the Second player?");
		String secondPlayer = scanner.next();
		
		if (firstPlayer.equalsIgnoreCase(secondPlayer)){
			
			System.out.println("Both players cannot be named "+ secondPlayer+".  Enter a different name: ");
			secondPlayer = scanner.next();
		}
		//System.out.println(firstPlayer+ " "+ secondPlayer);
		
		System.out.println("How many chips does the initial pile contain ?");
		int initialPile = scanner.nextInt();
		
		if (initialPile <3){
			System.out.println("You have to start with at least 3 chips.  Choose another number: ");
			initialPile = scanner.nextInt();
		}
		//System.out.println(initialPile%2);
		
		if (initialPile%2 == 0){
			System.out.println("You have to start with an odd number of chips.  Choose another number: ");
			initialPile = scanner.nextInt();		
		}
		/*********Game begins*********/
		gameofchips.gamebegins(initialPile, firstPlayer, secondPlayer);
	
		boolean play = true;
		while (play){
			System.out.println("Play another game? (y/n)");
			String replay = scanner.next();
			
			if (replay.equalsIgnoreCase("y")){
				
				System.out.println("How many chips does the initial pile contain");
				initialPile = scanner.nextInt();
				
				if (initialPile <3){
					System.out.println("You have to start with at least 3 chips.  Choose another number: ");
					initialPile = scanner.nextInt();
				}
				
				if (initialPile%2 == 0){
					System.out.println("You have to start with an odd number of chips.  Choose another number: ");
					initialPile = scanner.nextInt();		
				}
				gameofchips.gamebegins(initialPile, firstPlayer, secondPlayer);
			}
			else if (replay.equalsIgnoreCase("n")){
				System.out.println("Have a great ahead!");
				play = false;
				return;
			}
			else {
				System.out.println("Please enter 'y' or 'n'");
			}
		}
		
	}
}
