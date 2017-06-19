package com.gcit.LibrarySystem.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import java.util.List;
import java.util.Scanner;

public class LibraryManagement {
	
	Scanner scanner  = new Scanner(System.in);
	Borrower borrower = new Borrower();
	Administrator administrator = new Administrator(); 
	BufferedReader reader = 
            new BufferedReader(new InputStreamReader(System.in));
	
	public void mainDisplay() throws IOException, SQLException{
		int i = 0;
		
		System.out.println("WELCOME TO THE GCIT LIBRARY MANAGEMENT SYSTEM!!!");
		System.out.println("WHICH CATEGORY USER ARE YOU ?");
		System.out.println("1) LIBRARIAN");
		System.out.println("2) ADMINISTRATOR");
		System.out.println("3) BORROWER");
		System.out.println("4) QUIT");
		
		i = scanner.nextInt();
		while (i>0 && i< 5){
			switch (i){
			case(1):libDisplay1();
					break;
			case(2):administrator.adminDisplay();
					break;
			case(3):borrower.borrowerDisplay();
					break;	
			case(4):System.out.println("HAVE A GREAT DAY AHEAD AND VISIT US BACK SOON!!!!!");
					System.exit(0);
			default:mainDisplay();
					break;
			}
		}
		System.out.println("Please Enter a valid Number");
		mainDisplay();
	}
	
	
	public String libDisplay1() throws IOException, SQLException{
		int i=0; 
		System.out.println("Enter the suitable associated number to the option you want to select ");
		System.out.println("1) Enter the Branch you Manage");
		System.out.println("2) Quit to previous menu");
		i = scanner.nextInt();
		
		switch(i){
		
		case(1):libDisplay2();
				break;
		case(2):mainDisplay();
				break;
			default:mainDisplay();
					break;
		}
		return null;
	}
	
	
	public void libDisplay2() throws IOException, SQLException {
		
		Connection conn =JDBCConnect.getConnection();
		ArrayList<String> listBranchNames = new ArrayList<String>();
		String query = "";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			query = "select * from tbl_library_branch order by branchId ";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				listBranchNames.add(rs.getInt("branchId")+","+rs.getString("branchName")+","+ rs.getString("branchAddress"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		for (int i = 0; i < listBranchNames.size(); i++) {
			
			String[] splitStr = listBranchNames.get(i).split(",");
			System.out.print((i+1)+") ");
			System.out.print(splitStr[1]+",");
			System.out.println(splitStr[2]);
		}
		System.out.println((listBranchNames.size()+1)+") QUIT TO PREVIOUS MENU");
		
		System.out.println("SELECT AND ENTER THE NUMBER TO PROCEED");
		int selectedNumber=0;
		selectedNumber = scanner.nextInt();
		
		if (selectedNumber == (listBranchNames.size()+1)){
			libDisplay1();
		}
		else{
			if (selectedNumber >=0 && selectedNumber <= listBranchNames.size()){
				
				for (int i = 0; i < listBranchNames.size(); i++) {
					//System.out.print(i+" ");
					if (selectedNumber == (i+1)){
						libDisplay3(listBranchNames.get(i));
					}	
				}
			}
			else{
				System.out.println("PLEASE ENTER A SUITABLE NUMBER");
				libDisplay2();
			}
			
		}
		
	}
	
	
	public void libDisplay3(String s) throws IOException, SQLException{
		
		Connection conn = JDBCConnect.getConnection();
		String[] splitStr = s.split(",");
		ArrayList<String> listBookNames = new ArrayList<String>();
		
		System.out.println("SELECT AND ENTER A NUMBER TO PROCEED");
		System.out.println("1)	UPDATE THE DETAILS OF THE LIBRARY");
		System.out.println("2)	ADD COPIES OF BOOK TO THE LIBRARY");
		System.out.println("3)	QUIT TO PREVIOUS MENU");
		int i=0;
		i = scanner.nextInt();
		
		String newBranchName;
		String newBranchAddress;
		switch(i){
		case(1):System.out.println("YOU HAVE CHOSEN TO UPDATE BRANCH WITH BRANCH ID: "+splitStr[0]+" AND BRANCH NAME: "+splitStr[1]);
				
				
				
				String queryName ="";
				String queryAddress="";
				Statement stmt;
				
				try {
					System.out.println("PLEASE ENTER A NEW BRANCH NAME OR TYPE N/A");
					newBranchName = reader.readLine();
					String notApp="N/A";
					if (newBranchName.equalsIgnoreCase(notApp)){
						newBranchName = splitStr[1];
					}
					stmt = conn.createStatement();
					queryName = "UPDATE tbl_library_branch SET branchName = '"+ newBranchName+"' WHERE branchId = "+splitStr[0]+";";
					int rs = stmt.executeUpdate(queryName);
					
					
					System.out.println("DO YOU WISH TO QUIT TO CANCEL OPERATION PRESS 1 FOR YES AND 2 FOR NO");
					int checkYesNo = scanner.nextInt();
					
					if (checkYesNo == 1){
						conn.close();
						libDisplay3(s);
					}
					
					System.out.println("PLEASE ENTER A NEW BRANCH ADDRESS OR TYPE N/A");
					newBranchAddress = reader.readLine();
					if (newBranchAddress.equalsIgnoreCase(notApp)){
						newBranchAddress = splitStr[2];
					}
					
					queryAddress = "UPDATE tbl_library_branch SET branchAddress='"+newBranchAddress+"' WHERE branchId="+splitStr[0]+";";
					
					int rsAddress = stmt.executeUpdate(queryAddress);
					
					System.out.println("DO YOU WISH TO QUIT TO CANCEL OPERATION PRESS 1 FOR YES AND 2 FOR NO");
					int checkYesNo1 = scanner.nextInt();
					
					if (checkYesNo1 == 1){
						conn.close();
						libDisplay3(s);
					}
					if (rsAddress==1 && rs == 1 ){
						System.out.println("Successfully Updated "+rsAddress+" row");
					}
					
					conn.commit();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	finally{
					try {
						conn.close();
						libDisplay3(s);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				libDisplay3(s);
				break;
				
		case(2):System.out.println("PICK THE BOOK, YOU WANT TO ADD COPIES OF");
				
				String query1 ="";
				Statement stmt1 = null;
				
				try {
					stmt1 = conn.createStatement();
					query1 = "SELECT tbl_book.bookId,title,authorName from tbl_book_authors,tbl_book,tbl_author WHERE "
							+ "tbl_book.bookId=tbl_book_authors.bookId "
							+ "and tbl_author.authorId = tbl_book_authors.authorId;";
				
					ResultSet rs1 = stmt1.executeQuery(query1);
					while(rs1.next()){
						listBookNames.add(rs1.getInt("bookId")+","+rs1.getString("title")+","+rs1.getString("authorName"));
					}
					//conn.commit();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for (int j = 0; j < listBookNames.size(); j++) {
					
					String[] splitStr1 = listBookNames.get(j).split(",");
					System.out.print((j+1)+") ");
					//System.out.print(splitStr1[0]+",");
					System.out.print(splitStr1[1]+",");
					System.out.println(splitStr1[2]);
				}
				
				System.out.println((listBookNames.size()+1)+") QUIT TO PREVIOUS MENU");
				
				
				System.out.println("SELECT AND ENTER THE NUMBER");
				int selectedNumber=0;
				selectedNumber = scanner.nextInt();
				//ArrayList<Integer> selectedStringCount = new ArrayList<Integer>();
				int selectedStringCount = 0;
				
				if (selectedNumber == (listBookNames.size()+1)){
					libDisplay2();
				}
				else{
					if (selectedNumber >=0 && selectedNumber <= listBookNames.size()){
						
						for (int k = 0; k < listBookNames.size(); k++) {
							//System.out.print(i+" ");
							if (selectedNumber == (k+1)){
								//storeSelectedString= listBookNames.get(k);
								String[] storeSplitStr = listBookNames.get(k).split(",");
								query1 = "Select noOfCopies from tbl_book_copies WHERE branchId ="
										+ splitStr[0]+" and bookId ="+ storeSplitStr[0]+";";
								ResultSet rs1 = stmt1.executeQuery(query1);
								while(rs1.next()){
									selectedStringCount= (rs1.getInt("noOfCopies"));
								}
								System.out.println("Existing number of copies: "+selectedStringCount);
								
								System.out.println("Enter the new number of copies: ");
								int newNumCopies = scanner.nextInt();
								System.out.println(newNumCopies +" Values of new num");
								
								if (selectedStringCount == 0){
									String query2="";							
									query2 ="INSERT tbl_book_copies values ("+storeSplitStr[0]+","+splitStr[0]+","+ newNumCopies+");";
									int rs2 =stmt1.executeUpdate(query2);
									if (rs2 == 1){	
										System.out.println("Successfully Updated "+rs2+" row");
									}
									else{
										System.out.println("Unsuccessful in Updating "+rs2+" row");
									}
									conn.commit();
									conn.close();
									libDisplay3(s);
									
								}else{
									int addNum = selectedStringCount+newNumCopies;
									String query2="";
									query2 = "UPDATE tbl_book_copies SET noOfCopies="+ addNum+
											" WHERE bookId ="+ storeSplitStr[0]+" and branchId="+splitStr[0]+";";
									int rs2 = stmt1.executeUpdate(query2);
									
									if (rs2 == 1){
										
										System.out.println("Successfully Updated "+rs2+" row else");
									}
									else{
										System.out.println("Unsuccessful in Updating "+rs2+" row");
									}
									conn.commit();
									conn.close();
									libDisplay3(s);
									
								}
							} 		
						}	
					}
					else{
						System.out.println("PLEASE ENTER A SUITABLE NUMBER");
						libDisplay3(s);
					}
				}
				break;
		
		case(3):libDisplay2();
				break;
		default:
		}	
	}
	public static void main(String[] args) throws IOException, SQLException {
		// TODO Auto-generated method stub
		LibraryManagement lm = new LibraryManagement();
		lm.mainDisplay();
		
		
	}

}
