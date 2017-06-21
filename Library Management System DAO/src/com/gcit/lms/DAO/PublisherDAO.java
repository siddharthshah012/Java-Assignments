package com.gcit.lms.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//import com.gcit.lms.Entity.Book;
import com.gcit.lms.Entity.Publisher;

public class PublisherDAO extends BaseDAO{

	public PublisherDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public void addPublisher(Publisher publisher) throws SQLException{
		save("insert into tbl_publisher(publisherName,publisherAddress,publisherPhone) values (?)", new Object[] {publisher.getPublisherName(),publisher.getPublisherAddress(),publisher.getPublisherPhones()});
	}
	
	public void updatePublisher(Publisher publisher) throws SQLException {
		save("UPDATE tbl_publisher SET VALUES publisherName=? , publisherAddress=?, publisherPhone=? WHERE publisherId=?", new Object[]{publisher.getPublisherName(),publisher.getPublisherAddress(),publisher.getPublisherPhones(),publisher.getPublisherId()});
	}
	
	public void deletePublisher(Publisher publisher) throws SQLException {
		save("DELETE FROM tbl_publisher WHERE publisherId=?", new Object[]{publisher.getPublisherId()});
	}

	@Override
	public List<?> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> extractDataFirstLevel(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	

}
