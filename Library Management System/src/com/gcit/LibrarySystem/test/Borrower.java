package com.gcit.LibrarySystem.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
//import java.util.Calendar;
import java.util.Scanner;

public class Borrower {
	
	Scanner scanner  = new Scanner(System.in);
	BufferedReader reader = 
            new BufferedReader(new InputStreamReader(System.in));
	
	public void borrowerDisplay() throws IOException, SQLException{
		LibraryManagement lms = new LibraryManagement();
		/*
		String name;
		String address;
		Long phone;*/
		
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
		//returnListCardNo.forEach(s-> System.out.println(s));
		for (int i=0; i< returnListCardNo.size();i++){
			System.out.println(returnListCardNo.get(i));
		}
		
		for (int i=0; i< returnListCardNo.size();i++){
			
			if (cardNumber == returnListCardNo.get(i)){
				//cardNumber = returnListCardNo.get(i);
				//System.out.println("true");
				value= true;
				break;
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
			
			case(1):checkOutBook(cardNumber);
					break;
			case(2):returnBook(cardNumber);
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
	
	public void checkOutBook(long cardNumber) throws SQLException, IOException{
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
		conn.commit();
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
		
		String storeBranchList = null;
		
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
				checkOutBook(cardNumber);
			}
		}
		
		String[] splitstr = storeBranchList.split(",");
		
		query1 ="Select bk.bookId,title, authorName "+
				"from tbl_book_authors as ba "+
				"join tbl_book as bk on bk.bookId=ba.bookId "+
				"join tbl_author as au on au.authorId=ba.authorId "+
				"Where bk.bookId IN "+
				"(select bc.bookId from tbl_book_copies as bc where bc.branchId ="+splitstr[0]+")";
		
		ResultSet rs1 = stmt.executeQuery(query1);
		conn.commit();
		ArrayList <String> storeAvailBook = new ArrayList<String>();
		
		while (rs1.next()){
			storeAvailBook.add(rs1.getInt("bookId")+" "+rs1.getString("title")+" "+rs1.getString("authorName"));
		}
		for (int a =0;a <storeAvailBook.size();a++){
			System.out.println((a+1)+") " + storeAvailBook.get(a));
		}
		
		System.out.println("SELECT AND ENTER THE BOOK ID FROM THE LIST:");
		int selectedBookId = scanner.nextInt();
		int id=0;
		for (int b=0; b<storeAvailBook.size();b++){
			String[] splitstr1 = storeAvailBook.get(b).split(" ");
			if (selectedBookId == Integer.parseInt(splitstr1[0])){
				id = selectedBookId; 
				
			}
		}
		
		query="INSERT INTO tbl_book_loans VALUES "
				+ "("+id+","+splitstr[0]+","+cardNumber+",NOW(),NOW()+interval 1 week, null);";
		int rs3 = stmt.executeUpdate(query);
		
		if (rs3 ==1){System.out.println("copy updated");}
		conn.commit();
		query ="UPDATE tbl_book_copies SET noOfCopies = (noOfCopies -1) "
				+ "WHERE bookId="+id+" and branchId= "+splitstr[0] +" and noOfCopies > 0;";
		
		int rs4 = stmt.executeUpdate(query);
		if (rs4 ==1){System.out.println(" number of Copies updated ");}
		
		conn.commit();
		conn.close();
		borrowerDisplay();
		
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
	
	public void returnBook(long cardNumber) throws SQLException, IOException{
		
		Connection conn =JDBCConnect.getConnection();
		ArrayList<String> listBranchNames = new ArrayList<String>();
		
		String query ="";
		//String query1="";
		
		Statement stmt;
		
		stmt = conn.createStatement();
		query = "select * from tbl_library_branch order by branchId ";
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			listBranchNames.add(rs.getInt("branchId")+","+rs.getString("branchName")+","+ rs.getString("branchAddress"));
		}
		conn.commit();
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
		
		String storeBranchList = null;
		
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
				checkOutBook(cardNumber);
			}
		}
		
		String[] splitstr = storeBranchList.split(",");
		
		query=  "select bl.bookId,bk.title,au.authorName,bl.dateOut "+ 
				"from tbl_book_authors as ba "+
				"join tbl_book as bk on bk.bookId=ba.bookId "+
				"join tbl_book_loans as bl on bl.bookId=ba.bookId "+
				"join tbl_author as au on au.authorId=ba.authorId "+
				"Where bl.branchId="+splitstr[0] +" and bl.cardNo="+ cardNumber+"";
		
		ResultSet rs2 = stmt.executeQuery(query);
		conn.commit();
		ArrayList<Integer> storeBookId = new ArrayList<Integer>();
		ArrayList<Timestamp> storeDateOut = new ArrayList<Timestamp>();
		while (rs2.next()){
			storeBookId.add(rs2.getInt("bookId"));
			storeDateOut.add(rs2.getTimestamp("dateOut"));
		}
		
		for(int x = 0; x< storeDateOut.size();x++){
			
			System.out.println((x+1)+") "+ storeDateOut.get(x));
		}
		
		System.out.println("SELECT AND ENTER THE NUMBER FOR THE SELECTION");
		int selectNumber = scanner.nextInt();
		int varBookId = 0;
		Timestamp varStore = null;
		int x=0;
		while(x<storeDateOut.size()){
			System.out.println(storeDateOut.size());
			if (selectNumber == (x+1)){
				System.out.println(selectNumber +" "+ (x+1));
				varBookId =storeBookId.get(x);
				varStore= storeDateOut.get(x);	
			}
			x++;
		}
		System.out.println("out"+ varStore);
		conn.commit();
		conn.close();
		
		Connection conn1= JDBCConnect.getConnection();
		Statement state;
		state = conn1.createStatement();
		String queryUpdate="UPDATE tbl_book_loans SET dateIn = NOW() WHERE dateOut='"+varStore+"';";
		int rs4 = state.executeUpdate(queryUpdate);
		if (rs4 ==1 ){
			System.out.println("Updated bookLoans Successfully");
		}
		conn1.commit();
		String queryUpdateBookCopies = 	"UPDATE tbl_book_copies "+
										"SET noOfCopies = (noOfCopies + 1) " +
										"WHERE bookId ="+varBookId+" and branchId="+splitstr[0]+"; ";
		int rs5 = state.executeUpdate(queryUpdateBookCopies);
		if (rs5== 1){
			
		}
		conn1.commit();
		conn1.close();
		
	}
	

}
