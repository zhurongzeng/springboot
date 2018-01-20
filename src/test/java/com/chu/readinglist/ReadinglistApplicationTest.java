package com.chu.readinglist;

import com.chu.controller.ReadingListController;
import com.chu.po.Book;
import com.chu.po.User;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ReadinglistApplicationTest {

//    @Autowired
//    private ReadingListController controller;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    private MockMvc mockMvc;
//
//    @Before
//    public void setMockMvc() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .apply(springSecurity())
//                .build();
//    }
//
//    @Test
//    public void contextLoads() throws Exception {
//        Book book = new Book();
//        System.out.println(controller.addToReadingList("aaa", book));
//    }
//
//    @Test
//    public void homePage() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/readinglist/aaa"))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.view().name("readinglist/list"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("books"))
//                .andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.is(Matchers.hasItems())));
//    }
//
//    @Test
//    public void postBook() throws Exception {
//        mockMvc.perform(post("/readinglist/craig")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("title", "BOOK TITLE")
//                .param("author", "BOOK AUTHOR")
//                .param("isbn", "1234567890")
//                .param("description", "DESCRIPTION"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(header().string("Location", "/readinglist/craig"));
//        Book expectedBook = new Book();
//        expectedBook.setId(System.currentTimeMillis() + "");
//        expectedBook.setReader("craig");
//        expectedBook.setTitle("BOOK TITLE");
//        expectedBook.setAuthor("BOOK AUTHOR");
//        expectedBook.setIsbn("1234567890");
//        expectedBook.setDescription("DESCRIPTION");
//        mockMvc.perform(get("/readinglist/craig"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("readinglist/list"))
//                .andExpect(model().attributeExists("books"))
//                .andExpect(model().attribute("books", hasSize(1)))
//                .andExpect(model().attribute("books", contains(samePropertyValuesAs(expectedBook))));
//    }
//
//    @Test
//    public void homePage_unauthenticatedUser() throws Exception {
//        mockMvc.perform(get("/readinglist/aaa"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(header().string("Location", "http://localhost/login"));
//    }
//
//    @Test
//    @WithUserDetails("aaa")
//    public void homePage_authenticatedUser() throws Exception {
//        Reader expectedReader = new Reader();
//        expectedReader.setUsername("aaa");
//        expectedReader.setPassword("aaa");
//        expectedReader.setFullname("Zachary Chu");
//        mockMvc.perform(get("/readinglist/aaa"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("readingList"))
//                .andExpect(model().attribute("reader", samePropertyValuesAs(expectedReader)))
//                .andExpect(model().attribute("books", hasSize(0)));
//    }
}
