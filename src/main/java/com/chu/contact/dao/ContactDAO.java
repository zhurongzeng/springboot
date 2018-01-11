package com.chu.contact.dao;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.chu.contact.po.Contact;

@Repository
public class ContactDAO {
	@Autowired
	private JdbcTemplate jdbc;

	public List<Contact> findAll() {
		return jdbc.query("select id, firstName, lastName, phoneNumber, emailAddress from contact order by lastName",
				new RowMapper<Contact>() {
					public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
						Contact contact = new Contact();
						contact.setId(rs.getLong(1));
						contact.setFirstName(rs.getString(2));
						contact.setLastName(rs.getString(3));
						contact.setPhoneNumber(rs.getString(4));
						contact.setEmailAddress(rs.getString(5));
						return contact;
					}
				});
	}

	public void save(Contact contact) {
		jdbc.update("insert into contact (id, firstName, lastName, phoneNumber, emailAddress) values (?, ?, ?, ?, ?)", System.currentTimeMillis(),
				contact.getFirstName(), contact.getLastName(), contact.getPhoneNumber(), contact.getEmailAddress());
	}
}