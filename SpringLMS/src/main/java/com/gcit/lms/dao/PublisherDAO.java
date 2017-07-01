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

	
	public void addPublisher(Publisher publisher) throws SQLException {
		System.out.println("in dao " + publisher.getPublisherName());
		save("Insert into tbl_publisher(publisherName,publisherAddress,publisherPhone) values (?,?,?)",
				new Object[] { publisher.getPublisherName(),
						publisher.getPublisherAddress(),
						publisher.getPublisherPhones() });
	}

	public Integer addPublisherWithId(Publisher publisher) throws SQLException {
		return saveWithID(
				"Insert into tbl_publisher(publisherName,publisherAddress,publisherPhone) values (?,?,?)",
				new Object[] { publisher.getPublisherName(),
						publisher.getPublisherAddress(),
						publisher.getPublisherPhones() });
	}

	public void updatePublisher(Publisher publisher) throws SQLException {
		save("Update tbl_publisher set publisherName= ?, publisherAddress=?, publisherPhone=? where publisherId = ?",
				new Object[] { publisher.getPublisherName(),
						publisher.getPublisherAddress(),
						publisher.getPublisherPhones(),
						publisher.getPublisherId() });
	}

	public void deletePublisher(Publisher publisher) throws SQLException {
		save("delete from tbl_publisher where publisherId = ?",
				new Object[] { publisher.getPublisherId() });

	}
	
	
	@SuppressWarnings("unchecked")
	public List<Publisher> readAllPublisher(Integer pageNo) throws ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		return (List<Publisher>) read("select * from tbl_publisher", null);
	}
	
	@SuppressWarnings("unchecked")
	public List<Publisher> readAllPublishersByName(Integer pageNo,String searchString)
			throws SQLException {
		searchString = "%" + searchString + "%";
		setPageNo(pageNo);
		return (List<Publisher>) read(
				"select * from tbl_publisher where publisherName like ?",
				new Object[] { searchString });
	}
	
	public Integer getPublishersCount() throws SQLException{
		return getCount("select count(*) as COUNT from tbl_publisher", null);
	}

	@SuppressWarnings("unchecked")
	public Publisher readPublishersByPK(Integer publisherId)
			throws SQLException {
		List<Publisher> publishers = (List<Publisher>) read(
				"select * from tbl_publisher where publisherId=?",
				new Object[] { publisherId });
		if (publishers != null) {
			return publishers.get(0);
		}
		return null;

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
