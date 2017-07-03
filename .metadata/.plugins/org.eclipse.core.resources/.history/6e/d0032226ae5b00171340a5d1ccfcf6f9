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
	/**
	 * @return the books
	 */
	public Book getBooks() {
		return books;
	}
	/**
	 * @param books the books to set
	 */
	public void setBooks(Book books) {
		this.books = books;
	}
	/**
	 * @return the branch
	 */
	public Library getBranch() {
		return branch;
	}
	/**
	 * @param branch the branch to set
	 */
	public void setBranch(Library branch) {
		this.branch = branch;
	}
	/**
	 * @return the borrower
	 */
	public Borrower getBorrower() {
		return borrower;
	}
	/**
	 * @param borrower the borrower to set
	 */
	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}
	/**
	 * @return the dateOut
	 */
	public Timestamp getDateOut() {
		return dateOut;
	}
	/**
	 * @param dateOut the dateOut to set
	 */
	public void setDateOut(Timestamp dateOut) {
		this.dateOut = dateOut;
	}
	/**
	 * @return the dueDate
	 */
	public Timestamp getDueDate() {
		return dueDate;
	}
	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}
	/**
	 * @return the dateIn
	 */
	public Timestamp getDateIn() {
		return dateIn;
	}
	/**
	 * @param dateIn the dateIn to set
	 */
	public void setDateIn(Timestamp dateIn) {
		this.dateIn = dateIn;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((books == null) ? 0 : books.hashCode());
		result = prime * result
				+ ((borrower == null) ? 0 : borrower.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((dateIn == null) ? 0 : dateIn.hashCode());
		result = prime * result + ((dateOut == null) ? 0 : dateOut.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookLoans other = (BookLoans) obj;
		if (books == null) {
			if (other.books != null)
				return false;
		} else if (!books.equals(other.books))
			return false;
		if (borrower == null) {
			if (other.borrower != null)
				return false;
		} else if (!borrower.equals(other.borrower))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (dateIn == null) {
			if (other.dateIn != null)
				return false;
		} else if (!dateIn.equals(other.dateIn))
			return false;
		if (dateOut == null) {
			if (other.dateOut != null)
				return false;
		} else if (!dateOut.equals(other.dateOut))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		return true;
	}
	
	

}
