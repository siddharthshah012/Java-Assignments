package com.gcit.lms;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.service.AdminService;
import com.gcit.lms.service.BorrowerService;
import com.gcit.lms.service.LibrarianService;


@Configuration
public class LMSConfig {
	
	public String driver = "com.mysql.jdbc.Driver";
	public String url = "jdbc:mysql://localhost/library";
	public String username = "root";
	public String password = "siddharth";
	
	@Bean
	public BasicDataSource datasource(){
		
		BasicDataSource datasource = new BasicDataSource();
		
		datasource.setDriverClassName(driver);
		datasource.setUrl(url);
		datasource.setUsername(username);
		datasource.setPassword(password);
		
		return datasource;
	}
	
	@Bean
	public JdbcTemplate template(){
		
		return new JdbcTemplate(datasource());
	}
	
	@Bean
	public AdminService adminService(){
	
		return new AdminService();	
	}
	
	@Bean
	public AuthorDAO authordao(){
		
		return new AuthorDAO();
	}
	
	@Bean
	public BookDAO bookdao(){
		
		return new BookDAO();
	}
	
	@Bean
	public PublisherDAO publisherDAO(){
		return new PublisherDAO();
	}
	
	@Bean
	public BorrowerDAO borrowerDAO(){
		return new BorrowerDAO();
	}
	
	@Bean
	public LibraryBranchDAO branchDAO(){
		return new LibraryBranchDAO();
	}
	
	@Bean
	public GenreDAO genreDAO(){
		return new GenreDAO();
	}
	
	@Bean
	public BookLoansDAO bookLoansDAO(){
		return new BookLoansDAO();
	}
	
	@Bean
	public BookCopiesDAO bookCopiesDAO(){
		return new BookCopiesDAO();
	}
	
	@Bean

	public BorrowerService borrowerService(){
		return new BorrowerService();
	}

	@Bean
	public LibrarianService librarianService(){
		return new LibrarianService();
	}

}
