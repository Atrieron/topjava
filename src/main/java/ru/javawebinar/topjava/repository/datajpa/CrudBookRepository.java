package ru.javawebinar.topjava.repository.datajpa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.javawebinar.topjava.model.Book;

public interface CrudBookRepository extends JpaRepository<Book,Integer> {
	Optional<Book> findById(int id);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM books b where b.id=:id")
	int delete(@Param("id") int id);
	
	List<Book> findAllByPrintYearBetween(LocalDateTime dateStart, LocalDateTime dateEnd);
}