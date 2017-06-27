package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO {

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
	public List<Publisher> readAllPublishers() throws SQLException {
		return (List<Publisher>) read("select * from tbl_publisher", null);
	}

	@SuppressWarnings("unchecked")
	public List<Publisher> readAllPublishersByName(String searchString)
			throws SQLException {
		searchString = "%" + searchString + "%";
		return (List<Publisher>) read(
				"select * from tbl_publisher where publisherName like ?",
				new Object[] { searchString });
	}

	@SuppressWarnings("unchecked")
	public Publisher readPublishersById(Integer publisherId)
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
		List<Publisher> publishers = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while (rs.next()) {
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherAddress(rs.getString("publisherAddress"));
			p.setPublisherPhones(rs.getString("publisherPhone"));
			p.setBooks((List<Book>) bdao.readFirstLevel(
					"select * from tbl_book where pubId=?",
					new Object[] { p.getPublisherId() }));
			publishers.add(p);

		}
		return publishers;
	}

	public List<?> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<>();
		while (rs.next()) {
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherAddress(rs.getString("publisherAddress"));
			p.setPublisherPhones(rs.getString("publisherPhone"));
			publishers.add(p);
		}
		return publishers;
	}

}
