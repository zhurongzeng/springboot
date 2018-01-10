package com.chu.contact.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chu.contact.dao.ContactDAO;
import com.chu.contact.po.Contact;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/contact")
public class ContactController {
	@Autowired
	private ContactDAO contactDAO;

	@RequestMapping(method = RequestMethod.GET)
	public String home(Map<String, Object> model) {
		log.info("********** 查询列表······ **********");
		List<Contact> contacts = contactDAO.findAll();
		model.put("contacts", contacts);
		return "contact/home";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submit(Contact contact) {
		log.info("********** 保存数据······ **********");
		contactDAO.save(contact);
		return "redirect:/contact";
	}
}
