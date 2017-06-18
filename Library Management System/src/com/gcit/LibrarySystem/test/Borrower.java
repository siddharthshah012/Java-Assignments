package com.gcit.LibrarySystem.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Borrower {
	
	Scanner scanner  = new Scanner(System.in);
	BufferedReader reader = 
            new BufferedReader(new InputStreamReader(System.in));
	
	public void borrowerDisplay() throws IOException, SQLException{
		LibraryManagement lms = new LibraryManagement();
		
		String name;
		String address;
		Long phone;
		
		System.out.println("borrower");
		System.out.println("Please enter your Card Number ");
		/*
		BigInteger check = new BigInteger("99999999999");
		String cardNumber = scanner.next();
		BigInteger cardNum = new BigInteger(cardNumber);*/
		
		long check = 99999999999L;
		long cardNumber = scanner.nextLong();
		if (cardNumber > check  || cardNumber < 0){
			System.out.println("Please Enter correct card number in the range of 1 to 99999999999");
			borrowerDisplay();
		}
		
		ArrayList<Long> returnListCardNo = new ArrayList<Long>(); 
		returnListCardNo = borrowerIds();
		boolean value = true;
		
		for (int i=0; i< returnListCardNo.size();i++){
			if (cardNumber == returnListCardNo.get(i)){
				//cardNumber = returnListCardNo.get(i);
				value= true;
			}
			else {
				value = false;
			}
		}
		
		if (value == true){
			
			System.out.println("PLease select a number corresponding to the option you want: ");
			System.out.println("1)	Check out a book");
			System.out.println("2)	Return a Book");
			System.out.println("3)	Quit to Previous ");
			
			int inputValue =0;
			inputValue = scanner.nextInt();
			
			switch(inputValue){
			
			case(1):checkOutBook();
					break;
			case(2):returnBook();
					break;
			case(3):lms.mainDisplay();
					break;
			default:borrowerDisplay();
			}
			
		}
		else {
			//borrowerDisplay();
			System.out.println("Do you want to become new member? Enter number: ");
			System.out.println("1) Yes");
			System.out.println("2) No/Entered Wrong Card number");
			int checkInput = scanner.nextInt();
			
			if (checkInput == 1){
				
				borrowerDisplay();
				/*
				System.out.println("Enter Name");
				name = reader.readLine();
				System.out.println("Enter Address");
				address = reader.readLine();
				System.out.println("Enter Phone");
				phone = scanner.nextLong();
				
				Connection conn =JDBCConnect.getConnection();
				String query ="";
				query = "INSERT INTO tbl_borrower VALUES ("+name+", "+address+", "+phone+");";
				Statement stsmt;
				stsmt = conn.createStatement();
				int result = stsmt.executeUpdate(query);
				if (result == 1){
					System.out.println("Welcome to the Library");
					borrowerDisplay();
					
				}
				else{
					System.out.println("Failed to execute");
				}
				conn.commit();
				conn.close();*/
				
			}
			else{
				lms.mainDisplay();
			}
			
		}
		
		
		
			
	}
	
	public void checkOutBook() throws SQLException, IOException{
		System.out.println("1");
		
		Connection conn =JDBCConnect.getConnection();
		ArrayList<String> listBranchNames = new ArrayList<String>();
		
		String query ="";
		String query1="";
		
		Statement stmt;
		
		stmt = conn.createStatement();
		query = "select * from tbl_library_branch order by branchId ";
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			
			listBranchNames.add(rs.getInt("branchId")+","+rs.getString("branchName")+","+ rs.getString("branchAddress"));
			
		}
		//conn.commit();
		
		for (int i = 0; i < listBranchNames.size(); i++) {
			
			String[] splitStr = listBranchNames.get(i).split(",");
			System.out.print((i+1)+") ");
			System.out.print(splitStr[1]+",");
			System.out.println(splitStr[2]);
		}
		System.out.println((listBranchNames.size()+1)+") Quit to Previous Menu");
		
		System.out.println("Enter the number ");
		int selectedNumber=0;
		selectedNumber = scanner.nextInt();
		
		String storeBranchList;
		
		if (selectedNumber == (listBranchNames.size()+1)){
			borrowerDisplay();
		}
		else{
			if (selectedNumber >=0 && selectedNumber <= listBranchNames.size()){
				
				for (int i = 0; i < listBranchNames.size(); i++) {
					//System.out.print(i+" ");
					if (selectedNumber == (i+1)){
						storeBranchList = listBranchNames.get(i);
					}	
				}
			}
			else{
				System.out.println("Please enter a suitable number");
				checkOutBook();
			}
			
		}
		
		
		
		
	}
	
	public ArrayList<Long> borrowerIds() throws SQLException{
		Connection conn =JDBCConnect.getConnection();
		
		ArrayList<Long> listCardNo = new ArrayList<Long>();
		
		String query ="";
		//String query1="";
		
		Statement stmt;
		
		stmt = conn.createStatement();
		query = "select cardNo from tbl_borrower order by cardNo ";
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			
			listCardNo.add(rs.getLong("cardNo"));
			
		}
		conn.commit();
		conn.close();
		
		return listCardNo;
	}
	
	public String bookCopies(){
		
		return null;
	}
	
	public void returnBook(){
		System.out.println("2");
	}
	

}
