package ru.javawebinar.topjava.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import ru.javawebinar.topjava.model.Book;
import ru.javawebinar.topjava.util.exception.NotFoundException;

public interface BookService {
	Book get(int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    default List<Book> getBetweenDates(LocalDate startDate, LocalDate endDate) {
        return getBetweenDateTimes(LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX));
    }

    List<Book> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Book> getAll();

    Book update(Book book) throws NotFoundException;

    Book create(Book book);
}