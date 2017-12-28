package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;

import ru.javawebinar.topjava.service.BookService;
import ru.javawebinar.topjava.web.book.BookRestController;

public class BookRestControllerTest {
	private static final String REST_URL = BookRestController.URL + '/';
	
	@Autowired
	BookService service;
}