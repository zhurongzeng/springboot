package com.chu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/readinglist")
public class ReadingListController {
//    @Autowired
//    private ReadingListDAO readingListDAO;
//
//    @Value("${amazon.associateId}")
//    private String associateId;
//
//    @RequestMapping(value = "/{reader}", method = RequestMethod.GET)
//    public String readerBooks(@PathVariable("reader") String reader, Model model) {
//        List<Book> readingList = readingListDAO.findByReader(reader);
//        if (readingList != null) {
//            model.addAttribute("books", readingList);
//            model.addAttribute("amazonID", associateId);
//        }
//        return "readinglist/list";
//    }
//
//    @RequestMapping(value = "/{reader}", method = RequestMethod.POST)
//    public String addToReadingList(@PathVariable("reader") String reader, Book book) throws Exception {
//        book.setReader(reader);
//        readingListDAO.save(book);
//        return "redirect:/readinglist/{reader}";
//    }
}
