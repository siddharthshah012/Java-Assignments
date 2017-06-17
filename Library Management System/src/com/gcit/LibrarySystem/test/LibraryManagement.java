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
	BufferedReader reader = 
            new BufferedReader(new InputStreamReader(System.in));
	
	public void mainDisplay() throws IOException, SQLException{
		
		int i = 0;
		
		System.out.println("Welcome to the GCIT Library Management System. Which category of a user are you");
		System.out.println("1) Librarian");
		System.out.println("2) Administrator");
		System.out.println("3) Borrower");
		System.out.println("4) Quit");
		
		i = scanner.nextInt();
		
		switch (i){
			
		case(1):libDisplay1();
				break;
			
		case(2):adminDisplay();
				break;
			
		case(3):borrowerDisplay();
				break;
				
		case(4):return;
		
		}
	}
	
	public void adminDisplay(){
		
		System.out.println("Admin");
	}
	public String libDisplay1() throws IOException, SQLException{
		int i=0; 
		System.out.println("Lib");
		System.out.println("Enter the Branch you Manage");
		System.out.println("Quit to previous menu");
		i = scanner.nextInt();
		
		switch(i){
		
		case(1):libDisplay2();
				break;
		case(2):mainDisplay();
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
			query = "select * from tbl_library_branch order by branchId DESC ";
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
		
		//listBranchNames.forEach(s -> System.out.println(s));
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
				System.out.println("Please enter a suitable number");
				libDisplay2();
			}
			
		}
		
	}
	
	
	public void libDisplay3(String s) throws IOException, SQLException{
		
		Connection conn = JDBCConnect.getConnection();
		String[] splitStr = s.split(",");
		ArrayList<String> listBookNames = new ArrayList<String>();
		
		System.out.println("Please enter the suitable number acc to your selection");
		System.out.println("1)	Update the details of the Library");
		System.out.println("2)	Add copies of Book to the Branch");
		System.out.println("3)	Quit to previous");
		int i=0;
		i = scanner.nextInt();
		
		String newBranchName;
		String newBranchAddress;
		switch(i){
		case(1):System.out.println("You have chosen to update the Branch with Branch Id: "+splitStr[0]+" and Branch Name: "+splitStr[1]);
				
				System.out.println("Please enter new branch name or enter N/A for no change:");
				newBranchName = reader.readLine();
				String notApp="N/A";
				if (newBranchName.equalsIgnoreCase(notApp)){
					newBranchName = splitStr[1];
				}
				System.out.println("Please enter new branch address or enter N/A for no change:");
				newBranchAddress = reader.readLine();
				if (newBranchAddress.equalsIgnoreCase(notApp)){
					newBranchAddress = splitStr[2];
				}
				String query ="";
				Statement stmt;
				
				try {
					stmt = conn.createStatement();
					query = "UPDATE tbl_library_branch SET branchName = '"+ newBranchName+"', branchAddress = '"+newBranchAddress+
							"' WHERE branchId = "+splitStr[0]+";";
					int rs = stmt.executeUpdate(query);
					if (rs == 1){
						
						System.out.println("Successfully Updated "+rs+" row");
					}
					else{
						System.out.println("Successfully Updated "+rs+" row");
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
				break;
				
		case(2):System.out.println("Pick the Book you want to add copies of, to your branch:");
				
				String query1 ="";
				Statement stmt1;
				
				try {
					stmt1 = conn.createStatement();
					query1 = "SELECT title,authorName from tbl_book_authors,tbl_book,tbl_author WHERE "
							+ "tbl_book.bookId=tbl_book_authors.bookId "
							+ "and tbl_author.authorId = tbl_book_authors.authorId;";
				
					ResultSet rs1 = stmt1.executeQuery(query1);
					while(rs1.next()){
					
						listBookNames.add(rs1.getString("title")+","+rs1.getString("authorName"));
					}
					//conn.commit();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for (int j = 0; j < listBookNames.size(); j++) {
					
					String[] splitStr1 = listBookNames.get(j).split(",");
					System.out.print((j+1)+") ");
					System.out.print(splitStr1[0]+",");
					System.out.println(splitStr1[1]);
				}
				
				System.out.println((listBookNames.size()+1)+") Quit to Previous Menu");
				
				
				System.out.println("Enter the number ");
				int selectedNumber=0;
				selectedNumber = scanner.nextInt();
				
				if (selectedNumber == (listBookNames.size()+1)){
					libDisplay3(s);
				}
				else{
					if (selectedNumber >=0 && selectedNumber <= listBookNames.size()){
						
						for (int k = 0; k < listBookNames.size(); k++) {
							//System.out.print(i+" ");
							if (selectedNumber == (k+1)){
								//libDisplay3(listBookNames.get(i));
								query1 = "Select noCtCopies from tbl_book_copies WHERE ";
								
								
							}	
						}
					}
					else{
						System.out.println("Please enter a suitable number");
						libDisplay3(s);
					}
					
				}
				
				
				
				
		
				
				break;
		case(3):break;
		default:
		}
		
		//System.out.println(s);
		
	}
	
	public void borrowerDisplay(){
		System.out.println("borrower");
		
	}

	public static void main(String[] args) throws IOException, SQLException {
		// TODO Auto-generated method stub
		LibraryManagement lm = new LibraryManagement();
		lm.mainDisplay();
		
		

	}

}
