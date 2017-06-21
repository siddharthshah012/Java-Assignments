package com.gcit.lms.Entity;

import java.sql.Timestamp;
import java.util.List;

public class BookLoans {
	
	private Timestamp dateOut;
	private Timestamp dateIn;
	private Timestamp dueDate;
	private List<Integer> cardNo;
	private List<Integer> bookId;
	private List<Integer> branchId;
	
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
	

}
