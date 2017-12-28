package ru.javawebinar.topjava.repository.datajpa;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ru.javawebinar.topjava.model.Book;
import ru.javawebinar.topjava.repository.BookRepository;

public class DataJpaBookRepository implements BookRepository {

	@Autowired
	private CrudBookRepository crudBookRepository;
	
	@Override
	public Book save(Book book) {
		return crudBookRepository.save(book);
	}

	@Override
	public boolean delete(int id) {		
		return crudBookRepository.delete(id)!=0;
	}

	@Override
	public Book get(int id) {
		return crudBookRepository.findById(id).orElse(null);
	}

	@Override
	public List<Book> getAll() {
		return crudBookRepository.findAll();
	}

	@Override
	public List<Book> getBetween(LocalDateTime startDate, LocalDateTime endDate) {
		return crudBookRepository.findAllByPrintYearBetween(startDate, endDate);
	}

}