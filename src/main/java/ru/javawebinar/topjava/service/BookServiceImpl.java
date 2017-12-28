package ru.javawebinar.topjava.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import ru.javawebinar.topjava.model.Book;
import ru.javawebinar.topjava.repository.BookRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class BookServiceImpl implements BookService {
	private final BookRepository repository;	

	@Autowired
	public BookServiceImpl(BookRepository repository) {
		this.repository = repository;
	}

	@Override
	public Book get(int id) throws NotFoundException {
		return checkNotFoundWithId(repository.get(id),id);
	}

	@Override
	public void delete(int id) throws NotFoundException {
		checkNotFoundWithId(repository.delete(id),id);		
	}

	@Override
	public List<Book> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		Assert.notNull(startDateTime, "startDateTime must not be null");
        Assert.notNull(endDateTime, "endDateTime  must not be null");
		return repository.getBetween(startDateTime, endDateTime);
	}

	@Override
	public List<Book> getAll() {
		return repository.getAll();
	}

	@Override
	public Book update(Book book) throws NotFoundException {
		return checkNotFoundWithId(repository.save(book),book.getId());
	}

	@Override
	public Book create(Book book) {
		Assert.notNull(book, "book must not be null");
		return repository.save(book);
	}
}
