package ru.javawebinar.topjava.web.book;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ru.javawebinar.topjava.model.Book;
import ru.javawebinar.topjava.service.BookService;
import ru.javawebinar.topjava.util.DateTimeUtil;

public class AbstractBookController {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	BookService service;

	public List<Book> getAll() {
		log.info("getAll");
		return service.getAll();
	}

	public Book get(int id) {
		log.info("get {}", id);
		return service.get(id);
	}

	public void delete(int id) {
		log.info("delete {}", id);
		service.delete(id);
	}

	public void update(Book book, int id) {
		log.info("update {} with id={}", book, id);
		assureIdConsistent(book, id);
		service.update(book);
	}

	public Book create(Book book) {
		return service.create(book);
	}

	public List<Book> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		return service.getBetweenDateTimes(startDateTime, endDateTime);
	}

	public List<Book> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
		List<Book> choseByDate = getBetween(LocalDateTime.of(startDate, LocalTime.MIN),
				LocalDateTime.of(endDate, LocalTime.MAX));
		return choseByDate.stream()
				.filter(t -> DateTimeUtil.isBetween(t.getPrintYear().toLocalTime(), startTime, endTime))
				.collect(Collectors.toList());
	}
}