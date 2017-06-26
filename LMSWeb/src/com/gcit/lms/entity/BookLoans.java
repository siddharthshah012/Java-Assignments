package com.gcit.lms.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class BookLoans implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -591623965461921994L;
	private Book books;
	private Library branch;
	private Borrower borrower;
	private Timestamp dateOut;
	private Timestamp dueDate;
	private Timestamp dateIn;
	

}
