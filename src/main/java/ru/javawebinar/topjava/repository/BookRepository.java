package ru.javawebinar.topjava.repository;

import java.time.LocalDateTime;
import java.util.List;

import ru.javawebinar.topjava.model.Book;

public interface BookRepository {
	Book save(Book book);
	boolean delete(int id);
	Book get(int id);
	List<Book> getAll();
	List<Book> getBetween(LocalDateTime startDate, LocalDateTime endDate);
}