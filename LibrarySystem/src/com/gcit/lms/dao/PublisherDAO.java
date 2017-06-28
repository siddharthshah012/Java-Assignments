package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO  {

	public PublisherDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub


		List<Publisher> publisher = new ArrayList<Publisher>();
		BookDAO bdao = new BookDAO(conn);
		
		while(rs.next()){
			Publisher b = new Publisher();
			b.setPublisherId(rs.getInt("publisherId"));
			b.setPublisherName(rs.getString("publisherName"));
			b.setPublisherAddress(rs.getString("publisherAddress"));
			b.setPublisherPhones(rs.getString("publisherPhone"));
			
			if ((b.getBooks() != null && b.getBooks().size() > 0)){
			for(Book bo: b.getBooks())
				b.setBooks((List<Book>) bdao.readFirstLevel("select * from tbl_book where bookId = ?)", new Object[] {bo.getBookId()}));
		   }
			publisher.add(b);
		}
		return publisher;
	}

	@Override
	public List<?> extractDataFirstLevel(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub

		List<Publisher> publisher = new ArrayList<Publisher>();
		
		while(rs.next()){
			Publisher b = new Publisher();
			b.setPublisherId(rs.getInt("publisherId"));
			b.setPublisherName(rs.getString("publisherName"));
			b.setPublisherAddress(rs.getString("publisherAddress"));
			b.setPublisherPhones(rs.getString("publisherPhone"));
			
			publisher.add(b);
		}
		return publisher;
		
	}

}
