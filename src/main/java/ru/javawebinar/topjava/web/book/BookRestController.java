package ru.javawebinar.topjava.web.book;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ru.javawebinar.topjava.model.Book;

@RestController
@RequestMapping(BookRestController.URL)
public class BookRestController extends AbstractBookController {
	public static final String URL = "/rest/profile/books";

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Book> getAll() {
		return super.getAll();
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Book get(@PathVariable("id") int id) {
		return super.get(id);
	}

	@DeleteMapping(value = "/delete")
	public void delete(@PathVariable("id") int id) {
		super.delete(id);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(Book book, int id) {
		super.update(book, id);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Book> createWithLocation(@RequestBody Book book) {
		Book created = super.create(book);

		URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath().path(URL + "/{id}")
				.buildAndExpand(created.getId()).toUri();

		return ResponseEntity.created(uriOfNewResource).body(created);
	}
	
	@GetMapping("/filter")
	public List<Book> getBetween(@RequestParam(value = "startDate", required = false) LocalDate startDate, 
						@RequestParam(value = "startTime", required = false) LocalTime startTime, 
						@RequestParam(value = "endDate", required = false) LocalDate endDate, 
						@RequestParam(value = "endTime", required = false) LocalTime endTime){
		return super.getBetween(startDate, startTime, endDate, endTime);
	}
}