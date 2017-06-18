package com.gcit.LibrarySystem.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
					
				@SuppressWarnings("unused")
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
	
	public void publishers() throws IOException, SQLException{
		
		
		System.out.println("What operation do you wish to perform?");
		System.out.println("1) Add");
		System.out.println("2) Update");
		System.out.println("3) Delete");
		System.out.println("4) Quit to Previous");
		
		int option =0;
		option = scanner.nextInt();
		
		switch(option){
		
		case(1):addpublisher();
				break;
		case(2):updatePublisher();
				break;
		case(3):deletePublisher();
				break;
		case(4):adminDisplay();
				break;
			default:adminDisplay();
			
		}
	}
	
	private void addpublisher() throws IOException, SQLException{
		System.out.println("Enter the publisher Name");
		String pubName = reader.readLine();
		System.out.println("Enter the address of Publisher");
		String pubAddress = reader.readLine();
		System.out.println("Enter the Phone number of Publisher");
		String pubPhoneNumber = reader.readLine();
		String query ="";
		
		Connection conn = JDBCConnect.getConnection();
		Statement stmt;
		stmt = conn.createStatement();
		query = "INSERT INTO tbl_publisher (publisherName,publisherAddress,publisherPhone) "
				+ "VALUES ('"+pubName+"','"+pubAddress+"','"+pubPhoneNumber+"');";
		
		int rs = stmt.executeUpdate(query);
		if (rs ==1){System.out.println("Publisher table inserted");}
		conn.commit();
		conn.close();
		System.out.println("Do you want to associate with any book?");
		System.out.println("1) Yes");
		System.out.println("2) N0");
		
		ArrayList<String> storeBookTable = new ArrayList<String>();
		int answer = scanner.nextInt();
		if (answer == 1){
			
			Connection conn1 = JDBCConnect.getConnection();
			Statement statement ;
			statement = conn1.createStatement();
			query = "SELECT * FROM tbl_book;";
			ResultSet rs1 = statement.executeQuery(query);
			while(rs1.next()){
				storeBookTable.add(rs1.getInt("bookId")+" "+rs1.getString("title"));
			}
			conn1.commit();
			for (int i=0; i < storeBookTable.size();i++){
				System.out.println((i+1)+") "+storeBookTable.get(i));
			}
			System.out.println("Enter accordingly book id or book title");
			System.out.println("Enter The book Title");
			String bookTitle = reader.readLine();
			
			query ="SELECT publisherId from tbl_publisher WHERE publisherName ='"+pubName+"' order by publisherId desc;";
			ResultSet rs2 = statement.executeQuery(query);
			int pubId=0;
			while(rs2.next()){
				pubId =rs2.getInt("publisherId");
			}
			int count =0;
			for (int j=0; j<storeBookTable.size();j++){
				String[] splitstr = storeBookTable.get(j).split(" ");
				
				if (bookTitle.equals(splitstr[1])){
					
					query = "UPDATE tbl_book SET pubId = "+pubId+" WHERE title ='"+bookTitle+"'; " ;
					
					int rs3 = statement.executeUpdate(query);
					if (rs3 ==1){
						count++;
					}
				}
			}
			System.out.println(count+" rows were updated");
			conn1.commit();
			conn1.close();
		}
		else {
			publishers();
		}
		publishers();
		
	}
	private void updatePublisher() throws SQLException, IOException{
		String query ="";
		ArrayList<String> storePublisher= new ArrayList<String> ();
 		Connection conn = JDBCConnect.getConnection();
		Statement stmt;
		stmt = conn.createStatement();
		query = "SELECT * FROM tbl_publisher";
		
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			storePublisher.add(rs.getInt("publisherId")+" "+rs.getString("publisherName")
					+" "+rs.getString("publisherAddress")+" "+rs.getString("publisherPhone"));
		}
		
		//int i;
		//storePublisher.forEach(s ->System.out.println(") "+s));
		for (int j =0; j <storePublisher.size();j++){
			System.out.println((j+1)+") "+ storePublisher.get(j));
		}
		
		System.out.println("SELECT the Publisher id that you want to update");
		String pubSelectedId = scanner.next();
		
		System.out.println("Enter the New Name of Publisher or N/A");
		String newName = reader.readLine();
		System.out.println("Enter the new address of publisher or N/A");
		String newAddress = reader.readLine();
		System.out.println("Enter the New phone of Publisher or N/A");
		String NewPhone = reader.readLine();
		
		for (int i=0; i < storePublisher.size();i++){
			String[] splitstr = storePublisher.get(i).split(" "); 
			if (pubSelectedId.equals(splitstr[0])){
				
				query = "UPDATE tbl_publisher SET publisherName='"+newName+"'"
						+ ", publisherAddress='"+newAddress+"',publisherPhone='"+NewPhone+"' WHERE publisherId="+pubSelectedId+";";
				
				int rs1 = stmt.executeUpdate(query);
				if (rs1 == 1){
					System.out.println("Publisher UPdated Successfully");
				}
			}
		}
		
		conn.commit();
		conn.close();
		publishers();
	}
	private void deletePublisher() throws SQLException, IOException{
		
		String query ="";
		ArrayList<String> storePublisher= new ArrayList<String> ();
 		Connection conn = JDBCConnect.getConnection();
		Statement stmt;
		stmt = conn.createStatement();
		query = "SELECT * FROM tbl_publisher";
		
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			storePublisher.add(rs.getInt("publisherId")+" "+rs.getString("publisherName")
					+" "+rs.getString("publisherAddress")+" "+rs.getString("publisherPhone"));
		}
		
		//int i;
		//storePublisher.forEach(s ->System.out.println(") "+s));
		for (int j =0; j <storePublisher.size();j++){
			System.out.println((j+1)+") "+ storePublisher.get(j));
		}
		System.out.println("SELECT the Publisher id that you want to Delete");
		String pubSelectedId = scanner.next();
		
		for (int i=0; i < storePublisher.size();i++){
			String[] splitstr = storePublisher.get(i).split(" "); 
			if (pubSelectedId.equals(splitstr[0])){
				
				query = "DELETE FROM tbl_publisher WHERE publisherId="+pubSelectedId+";";
				
				int rs1 = stmt.executeUpdate(query);
				if (rs1 == 1){
					System.out.println("Publisher Deleted Successfully");
				}
			}
		}
		
		conn.commit();
		conn.close();
		publishers();
	}
	public void libraryBranch() throws IOException, SQLException{
		
		System.out.println("What operation do you wish to perform?");
		System.out.println("1) Add");
		System.out.println("2) Update");
		System.out.println("3) Delete");
		System.out.println("4) Quit to Previous");
		
		int option =0;
		option = scanner.nextInt();
		
		switch(option){
		
		case(1):addLibrary();
				break;
		case(2):updateLibrary();
				break;
		case(3):deleteLibrary();
				break;
		case(4):adminDisplay();
				break;
		}
		
	}
	
	
	private void addLibrary() throws IOException, SQLException{
		
		System.out.println("Enter the Branch Name that you want to add");
		String branchName = reader.readLine();
		System.out.println("Enter the address of branch that you want to add");
		String branchAddress = reader.readLine();
		
		String query="";
		Statement stmt;
		Connection conn = JDBCConnect.getConnection();
		
		stmt = conn.createStatement();
		
		query = "INSERT INTO tbl_library_branch(branchName, branchAddress) VALUES ('"+branchName+"','"+branchAddress+"');";
		int rs = stmt.executeUpdate(query);
		if (rs ==1){System.out.println("New Branch added");}
		
		conn.commit();
		conn.close();
		libraryBranch();
	}
	
	private void updateLibrary() throws SQLException, IOException{
		
		String query ="";
		ArrayList<String> storeLibrary= new ArrayList<String> ();
 		Connection conn = JDBCConnect.getConnection();
		Statement stmt;
		stmt = conn.createStatement();
		query = "SELECT * FROM tbl_library_branch";
		
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			storeLibrary.add(rs.getInt("branchId")+" "+rs.getString("branchName")
					+" "+rs.getString("branchAddress"));
		}
		
		//int i;
		//storePublisher.forEach(s ->System.out.println(") "+s));
		for (int j =0; j <storeLibrary.size();j++){
			System.out.println((j+1)+") "+ storeLibrary.get(j));
		}
		
		System.out.println("SELECT the Branch id that you want to update");
		String libBranchId = scanner.next();
		
		System.out.println("Enter the New Name of Library Branch or N/A");
		String newName = reader.readLine();
		System.out.println("Enter the new address of Library Branch or N/A");
		String newAddress = reader.readLine();
		
		for (int i=0; i < storeLibrary.size();i++){
			String[] splitstr = storeLibrary.get(i).split(" "); 
			if (libBranchId.equals(splitstr[0])){
				
				query = "UPDATE tbl_library_branch SET branchName='"+newName+"'"
						+ ", branchAddress='"+newAddress+"' WHERE branchId="+libBranchId+";";
				
				int rs1 = stmt.executeUpdate(query);
				if (rs1 == 1){
					System.out.println("Library Branch UPdated Successfully");
				}
			}
		}
		
		conn.commit();
		conn.close();
		libraryBranch();
		
	}
	
	private void deleteLibrary() throws SQLException, IOException{
		
		String query ="";
		ArrayList<String> storeLibrary= new ArrayList<String> ();
 		Connection conn = JDBCConnect.getConnection();
		Statement stmt;
		stmt = conn.createStatement();
		query = "SELECT * FROM tbl_library_branch";
		
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			storeLibrary.add(rs.getInt("branchId")+" "+rs.getString("branchName")
					+" "+rs.getString("branchAddress"));
		}
		
		//int i;
		//storePublisher.forEach(s ->System.out.println(") "+s));
		for (int j =0; j <storeLibrary.size();j++){
			System.out.println((j+1)+") "+ storeLibrary.get(j));
		}
		
		System.out.println("SELECT the Branch id that you want to Delete");
		String libBranchId = scanner.next();
		
		for (int i=0; i < storeLibrary.size();i++){
			String[] splitstr = storeLibrary.get(i).split(" "); 
			if (libBranchId.equals(splitstr[0])){
				
				query = "DELETE FROM tbl_library_branch WHERE branchId="+libBranchId+";";
				
				int rs1 = stmt.executeUpdate(query);
				if (rs1 == 1){
					System.out.println("Library Branch Deleted Successfully");
				}
			}
		}
		
		conn.commit();
		conn.close();
		libraryBranch();
	}
	
	public void borrowers() throws IOException, SQLException{
		
		System.out.println("What operation do you wish to perform?");
		System.out.println("1) Add");
		System.out.println("2) Update");
		System.out.println("3) Delete");
		System.out.println("4) Quit to Previous");
		
		int option =0;
		option = scanner.nextInt();
		
		switch(option){
		
		case(1):addBorrower();
		break;
		case(2):updateBorrower();
				break;
		case(3):deleteBorrower();
				break;
		case(4):adminDisplay();
			
		}
		
	}
	
	private void addBorrower() throws IOException, SQLException{
		System.out.println("Enter the publisher Name");
		String name = reader.readLine();
		System.out.println("Enter the address of Publisher");
		String address = reader.readLine();
		System.out.println("Enter the Phone number of Publisher");
		String phoneNumber = reader.readLine();
		String query ="";
		
		Connection conn = JDBCConnect.getConnection();
		Statement stmt;
		stmt = conn.createStatement();
		query = "INSERT INTO tbl_borrower (name,address,phone) "
				+ "VALUES ('"+name+"','"+address+"','"+phoneNumber+"');";
		
		int rs = stmt.executeUpdate(query);
		if (rs ==1){System.out.println("Publisher table inserted");}
		conn.commit();
		conn.close();
		borrowers();
	}
	private void updateBorrower() throws SQLException, IOException{
		
		String query ="";
		ArrayList<String> storeborrower= new ArrayList<String> ();
 		Connection conn = JDBCConnect.getConnection();
		Statement stmt;
		stmt = conn.createStatement();
		query = "SELECT * FROM tbl_borrower";
		
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			storeborrower.add(rs.getInt("cardNo")+" "+rs.getString("name")
					+" "+rs.getString("address")+" "+rs.getString("phone"));
		}
		
	
		for (int j =0; j <storeborrower.size();j++){
			System.out.println((j+1)+") "+ storeborrower.get(j));
		}
		
		System.out.println("SELECT the Branch id that you want to update");
		String cardNo = scanner.next();
		
		System.out.println("Enter the New Name or N/A");
		String newName = reader.readLine();
		System.out.println("Enter the new address or N/A");
		String newAddress = reader.readLine();
		System.out.println("Enter the new Phone NUmber or N/A");
		String newPhone = reader.readLine();
		
		for (int i=0; i < storeborrower.size();i++){
			String[] splitstr = storeborrower.get(i).split(" "); 
			if (cardNo.equals(splitstr[0])){
				
				query = "UPDATE tbl_borrower SET name='"+newName+"'"
						+ ", address='"+newAddress+"', phone ='"+newPhone+"' WHERE cardNo="+cardNo+";";
				
				int rs1 = stmt.executeUpdate(query);
				if (rs1 == 1){
					System.out.println("Borrower UPdated Successfully");
				}
			}
		}
		
		conn.commit();
		conn.close();
		libraryBranch();
		
	}
	private void deleteBorrower() throws SQLException, IOException{
		
		String query ="";
		ArrayList<String> storeborrower= new ArrayList<String> ();
 		Connection conn = JDBCConnect.getConnection();
		Statement stmt;
		stmt = conn.createStatement();
		query = "SELECT * FROM tbl_borrower";
		
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			storeborrower.add(rs.getInt("cardNo")+" "+rs.getString("name")
					+" "+rs.getString("address")+" "+rs.getString("phone"));
		}
		
		//int i;
		//storePublisher.forEach(s ->System.out.println(") "+s));
		for (int j =0; j <storeborrower.size();j++){
			System.out.println((j+1)+") "+ storeborrower.get(j));
		}
		
		System.out.println("SELECT the Cardno that you want to Delete");
		String cardNo = scanner.next();
		
		for (int i=0; i < storeborrower.size();i++){
			String[] splitstr = storeborrower.get(i).split(" "); 
			if (cardNo.equals(splitstr[0])){
				
				query = "DELETE FROM tbl_borrower  WHERE cardNo="+cardNo+";";
				
				int rs1 = stmt.executeUpdate(query);
				if (rs1 == 1){
					System.out.println("Borrower Deleted Successfully");
				}
			}
		}
		
		conn.commit();
		conn.close();
		libraryBranch();
		
	}
	
	
	public void overrideDueDate() throws SQLException{
		
		String query="";
		Connection conn = JDBCConnect.getConnection();
		ArrayList<String> storeBookLoan = new ArrayList<String> ();
		Statement statement;
		
		statement= conn.createStatement();
		
		query ="select * from tbl_book_loans WHERE dueDate> curdate() and dateOut is not null;";
		
		ResultSet rs = statement.executeQuery(query);
		while(rs.next()){
			storeBookLoan.add(rs.getInt("bookId")+" "+rs.getInt("branchId")+" "+rs.getInt("cardNo")+" "+
								rs.getDate("dateOut")+" "+rs.getDate("dueDate")+" "+rs.getDate("dateIn"));			
		}
		
		for (int i =0; i < storeBookLoan.size(); i++){
			System.out.println((i+1)+") "+storeBookLoan.get(i));
		}
		
		System.out.println("Please Enter folllowing details to override due date");
		System.out.println("Enter Book id:");
		String bookId = scanner.next();
		System.out.println("Enter Branch id:");
		String branchId = scanner.next();
		System.out.println("Enter Card no: ");
		String cardNo = scanner.next();
		
		System.out.println("Enter to which date you want to extend");
		int extendDay = scanner.nextInt();
		if (extendDay < 0 && extendDay> 30){overrideDueDate();}
		
		for (int i =0; i < storeBookLoan.size(); i++){
			//System.out.println((i+1)+") "+storeBookLoan.get(i));
			String[] splitstr = storeBookLoan.get(i).split(" ");
			if (splitstr[0].equals(bookId) && splitstr[1].equals(branchId) && splitstr[2].equals(cardNo)){
				
				query ="UPDATE tbl_book_loans SET dueDate=ADDDATE(dueDate,"+ extendDay+")"
						+ " WHERE bookId ="+bookId+" and branchId="+branchId+" and cardNo="+ cardNo+";";
				
				int rs2 = statement.executeUpdate(query);
				if (rs2 ==1){System.out.println("Updated succesfully");}
				
			}
		}
		conn.commit();
		conn.close();
	}
	
	

}
