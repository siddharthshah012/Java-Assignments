package com.gcit.LibrarySystem.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Administrator {
	
	Scanner scanner  = new Scanner(System.in);
	BufferedReader reader = 
            new BufferedReader(new InputStreamReader(System.in));

	public void adminDisplay() throws IOException, SQLException{
		LibraryManagement lms = new LibraryManagement();
		System.out.println("Admin");
		System.out.println("1)	Add/Update/Delete Book and Author");
		System.out.println("2)	Add/Update/Delete Publishers");
		System.out.println("3)	Add/Update/Delete Library Branches");
		System.out.println("4)	Add/Update/Delete Borrowers");
		System.out.println("5)	Over-ride Due Date for a Book Loan");
		System.out.println("6)  Quit to previous menu");
		
		int option =0;
		
		option = scanner.nextInt();
		
		switch(option){
		
		case(1):bookandAuthor();
				break;
		case(2):publishers();
				break;
		case(3):libraryBranch();
				break;
		case(4):borrowers();
				break;
		case(5):overrideDueDate();
				break;
		case(6):lms.mainDisplay();
		default:
		}
		
		
	}
	
	public void bookandAuthor() throws IOException, SQLException{
		
		System.out.println("What operation do you wish to perform?");
		System.out.println("1) Add");
		System.out.println("2) Update");
		System.out.println("3) Delete");
		System.out.println("4) Quit to Previous");
		
		int option =0;
		option = scanner.nextInt();
		
		switch(option){
		
		case(1):addBookandAuthor();
				break;
		case(2):updateBookandAuthor();
				break;
		case(3):deleteBookandAuthor();
				break;
		case(4):adminDisplay();
				break;
			
		}
		
	}
	public void addBookandAuthor() throws IOException, SQLException{
	
		System.out.println("Do you wish to Associate author with the book?");
		System.out.println("1) Yes");
		System.out.println("2) No");
		System.out.println("3) Add Only Author Name");
		System.out.println("4) add only Book title");
		int acceptDecline;
		acceptDecline = scanner.nextInt();
		String query="";
		String query1="";
		String query2="";
		String query3="";
		Connection conn1 = JDBCConnect.getConnection();
		Statement statement = null;
		try {
			statement = conn1.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch(acceptDecline){
		
		case(1):System.out.println("Enter the Book name to add or enter N/A");
				String addBook = "";
				addBook = reader.readLine();
		
				System.out.println("Enter the Name of Author to add or enter N/A");
				String addAuthor="";
				addAuthor = reader.readLine();
				
				query = "INSERT INTO tbl_book(title) VALUES ('"+addBook+"');";
				
				query1 = "INSERT INTO tbl_author(authorName) Values ('"+addAuthor+"');";
				
				query2 = "Select bookId,authorId FROM tbl_book,tbl_author "
						+ "WHERE tbl_book.title='"+addBook+"' and tbl_author.authorName='"+addAuthor+"'"
						+ "order by bookId desc,authorId desc limit 1;";
				int rs;
				
				rs = statement.executeUpdate(query);
				conn1.commit();
				if (rs ==1){
					System.out.println("Executed successfully 1" );
				}
					
				int rs1 = statement.executeUpdate(query1);
				conn1.commit();
				int bookId = 0, authorId = 0;
				ResultSet rs2 = statement.executeQuery(query2);
				conn1.commit();
				System.out.println("Jai ho");
				while (rs2.next()){
					bookId = rs2.getInt("bookId");
					authorId=rs2.getInt("authorId");
				}
				System.out.println("jai ho 1");
				conn1.commit();
				conn1.close();
				
				Connection conn2 = JDBCConnect.getConnection();
				statement = conn2.createStatement();
					
				query3 ="INSERT INTO tbl_book_authors VALUES ("+bookId+","+authorId+");";
				int rs3 = statement.executeUpdate(query3);
				conn2.commit();
				if (rs3 ==1){
					System.out.println("Executed successfully 3");
				}
				conn2.commit();
				conn2.close();
				bookandAuthor();
				break;
	
		case(2):System.out.println("Enter the Book name to add or enter N/A");
				String addBook1 = "";
				addBook1 = reader.readLine();
		
				System.out.println("Enter the Name of Author to add or enter N/A");
				String addAuthor1="";
				addAuthor1 = reader.readLine();
				
				query = "INSERT INTO tbl_book(title) VALUES ('"+addBook1+"');";
				query1 ="INSERT INTO tbl_author(authorName) Values ('"+addAuthor1+"');";
				try {
					statement.executeUpdate(query);
					statement.executeUpdate(query1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					
					try {
						conn1.commit();
						conn1.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					bookandAuthor();
					
				}
				
				
				break;
				
				
		case(3):System.out.println("Enter the Name of Author to add or enter N/A");
				String addAuthor2="";
				addAuthor2 = reader.readLine();
				query1 ="INSERT INTO tbl_author(authorName) Values ('"+addAuthor2+"');";
				int ress3 = statement.executeUpdate(query1);
				if (ress3 ==1){
					System.out.println("Executed successfully");
				}
				conn1.commit();
				conn1.close();
				bookandAuthor();
				break;
				
		case(4):System.out.println("Enter the Book name to add or enter N/A");
				String addBook2 = "";
				addBook2 = reader.readLine();
				query = "INSERT INTO tbl_book(title) VALUES ('"+addBook2+"');";
				int rs4 = statement.executeUpdate(query);
				if (rs4 ==1){
					System.out.println("Executed successfully");
				}
				conn1.commit();
				conn1.close();
				bookandAuthor();
				break;
		case(5):conn1.commit();
				conn1.close();
				bookandAuthor();
				break;
		default:
		}
	}
	public void updateBookandAuthor() throws SQLException, IOException{	
		
		String query="";
		String query1="";
		String query2="";
		String query3="";
		
		Connection conn = JDBCConnect.getConnection();

		Statement statement;
		statement = conn.createStatement();
		
		System.out.println("Select BookID and Author ID that you want to update");
		System.out.println("1) Book ID only");
		System.out.println("2) Author Id only");
		System.out.println("3) Book ID and authorID");
		//System.out.println("4) Book ID and author ID together ");
		System.out.println("4) QUIT to previous");
		
		int option =scanner.nextInt();
		switch(option){
		case(1):System.out.println("Enter Book ID which you want to update");
				int bookId = 0;
				bookId = scanner.nextInt();
				
				System.out.println("Enter the new title to update");
				String newTitle = reader.readLine();
				
				
				query = "UPDATE tbl_book SET title='"+newTitle+"' WHERE bookId="+bookId+";";
				int rs1 = statement.executeUpdate(query);
				if (rs1 ==1){
					System.out.println("Executed successfully");
				}
				conn.commit();
				conn.close();
				break;
				
		
		case(2):System.out.println("Enter Book ID which you want to update");
				int authorId = 0;
				authorId = scanner.nextInt();
		
				System.out.println("Enter the new title to update");
				String newAuthorName = reader.readLine();
		
		
				query = "UPDATE tbl_author SET authorName='"+newAuthorName+"' WHERE authorId="+authorId+";";
				int rs2 = statement.executeUpdate(query);
				if (rs2 ==1){
					System.out.println("Executed successfully");
				}
				conn.commit();
				conn.close();
				break;
			
		case(3):System.out.println("Enter Book ID which you want to update");
				int bookId1 = 0;
				bookId1 = scanner.nextInt();
		
				System.out.println("Enter the new title to update");
				String newTitle1 = reader.readLine();
				
				System.out.println("Enter Author ID which you want to update");
				int authorId1 = 0;
				authorId1 = scanner.nextInt();
		
				System.out.println("Enter the new Author Name to update");
				String newAuthorName1 = reader.readLine();
		
		
				query = "UPDATE tbl_author,tbl_book SET title="+newTitle1+"'"
						+ ",authorName='"+newAuthorName1+"' WHERE bookId="+bookId1+" and authorId="+authorId1+";";
				int rs3 = statement.executeUpdate(query);
				if (rs3 ==1){
					System.out.println("Executed successfully");
				}
				conn.commit();
				conn.close();
				break;		
				
		case(4):conn.commit();
				conn.close();
				bookandAuthor();
				break;
		}
		
		
	}
	public void deleteBookandAuthor() throws SQLException, IOException{
		
		String query="";
		String query1="";
		String query2="";
		String query3="";
		
		Connection conn = JDBCConnect.getConnection();

		Statement statement;
		statement = conn.createStatement();
		
		System.out.println("Select BookID and Author ID that you want to Delete");
		System.out.println("1) Book ID only");
		System.out.println("2) Author Id only");
		System.out.println("3) Book ID and authorID");
		//System.out.println("4) Book ID and author ID together ");
		System.out.println("4) QUIT to previous");
		
		int option =scanner.nextInt();
		switch(option){
		case(1):System.out.println("Enter Book ID which you want to Delete");
				int bookId = 0;
				bookId = scanner.nextInt();
				
				query = "DELETE FROM tbl_book WHERE bookId="+bookId+";";
				int rs1 = statement.executeUpdate(query);
				if (rs1 == 1){
					System.out.println("Executed successfully");
				}
				conn.commit();
				conn.close();
				break;
				
		
		case(2):System.out.println("Enter Book ID which you want to Delete");
				int authorId = 0;
				authorId = scanner.nextInt();
		
				query = "DELETE FROM tbl_author WHERE authorId="+authorId+";";
				int rs2 = statement.executeUpdate(query);
				if (rs2 ==1){
					System.out.println("Executed successfully");
				}
				conn.commit();
				conn.close();
				break;
			
		case(3):System.out.println("Enter Book ID which you want to update");
				int bookId1 = 0;
				bookId1 = scanner.nextInt();
				
				System.out.println("Enter Author ID which you want to update");
				int authorId1 = 0;
				authorId1 = scanner.nextInt();		
		
				query = "DELETE FROM tbl_author,tbl_book WHERE bookId="+bookId1+" and authorId="+authorId1+";";
				int rs3 = statement.executeUpdate(query);
				if (rs3 ==1){
					System.out.println("Executed successfully");
				}
				conn.commit();
				conn.close();
				break;
				
		case(4):conn.commit();
				conn.close();
				bookandAuthor();
				break;
		}
	}
	
	public void publishers(){
		
		
		System.out.println("What operation do you wish to perform?");
		System.out.println("1) Add");
		System.out.println("2) Update");
		System.out.println("3) Delete");
		System.out.println("4) Quit to Previous");
		
		int option =0;
		option = scanner.nextInt();
		
		switch(option){
		
		case(1):
		case(2):
		case(3):
		case(4):
			
		}
	}
	public void libraryBranch(){
		
		System.out.println("What operation do you wish to perform?");
		System.out.println("1) Add");
		System.out.println("2) Update");
		System.out.println("3) Delete");
		System.out.println("4) Quit to Previous");
		
		int option =0;
		option = scanner.nextInt();
		
		switch(option){
		
		case(1):
		case(2):
		case(3):
		case(4):
			
		}
		
	}
	
	public void borrowers(){
		
		System.out.println("What operation do you wish to perform?");
		System.out.println("1) Add");
		System.out.println("2) Update");
		System.out.println("3) Delete");
		System.out.println("4) Quit to Previous");
		
		int option =0;
		option = scanner.nextInt();
		
		switch(option){
		
		case(1):
		case(2):
		case(3):
		case(4):
			
		}
		
	}
	
	public void overrideDueDate(){
		
	}
	
	

}
