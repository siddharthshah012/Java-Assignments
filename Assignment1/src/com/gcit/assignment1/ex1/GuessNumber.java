package com.gcit.assignment1.ex1;

import java.util.Random;
import java.util.Scanner;

public class GuessNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int MaxRandNumber;
		int MinRandNumber;
		int userinput;
		
		@SuppressWarnings("resource")
		Scanner scanner= new Scanner(System.in);
		
		Random randomNumber = new Random();
		int n = randomNumber.nextInt(1000) +1;
		
		//System.out.println(n);
		MaxRandNumber = n+10;
		MinRandNumber = n-10;
		
		int count =1;
		
		while (count <= 5){
		
			System.out.println("Please guess a number between 1 to 1000");
			userinput = scanner.nextInt();
			
			if (userinput >= MinRandNumber && userinput <= MaxRandNumber  ){
				System.out.println("YOu have guess it correctly");
				System.out.println("The correct number is :" + n );
				return;
			}
			else if (count < 5){
				System.out.println("You have "+ (5-count)+ " more chances to guess the number");
			}
			count++;
			
		}
		
		System.out.println("Sorry");
		return;
		
	}

}
