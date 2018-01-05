package com.chu.readinglist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/readinglist")
public class ReadingListController {
	@Autowired
	private ReadingListDAO readingListDAO;

	@RequestMapping(value = "/{reader}", method = RequestMethod.GET)
	public String readerBooks(@PathVariable("reader") String reader, Model model) {
		List<Book> readingList = readingListDAO.findByReader(reader);
		if (readingList != null) {
			model.addAttribute("books", readingList);
		}
		return "readinglist/list";
	}

	@RequestMapping(value = "/{reader}", method = RequestMethod.POST)
	public String addToReadingList(@PathVariable("reader") String reader, Book book) {
		book.setReader(reader);
		readingListDAO.save(book);
		return "redirect:/readinglist/{reader}";
	}
}
