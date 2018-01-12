package com.chu.readinglist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chu.readinglist.dao.ReadingListDAO;
import com.chu.readinglist.po.Book;

@Controller
@RequestMapping("/")
public class ReadingListController {
	@Autowired
	private ReadingListDAO readingListDAO;

	@RequestMapping(value = "/readinglist/{reader}", method = RequestMethod.GET)
	public String readerBooks(@PathVariable("reader") String reader, Model model) {
		List<Book> readingList = readingListDAO.findByReader(reader);
		if (readingList != null) {
			model.addAttribute("books", readingList);
		}
		return "readinglist/list";
	}

	@RequestMapping(value = "/readinglist/{reader}", method = RequestMethod.POST)
	public String addToReadingList(@PathVariable("reader") String reader, Book book) throws Exception{
		Book books = new Book();
		books.setAuthor("sss");
		readingListDAO.save(books);
		book.setReader(reader);
		readingListDAO.save(book);
		return "redirect:/readinglist/{reader}";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
}
