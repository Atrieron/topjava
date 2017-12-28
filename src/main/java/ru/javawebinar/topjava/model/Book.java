package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="books")
public class Book extends AbstractNamedEntity {
	@Column(name="print_date", nullable = false)
	@NotNull
	private LocalDateTime printYear;
	public LocalDateTime getPrintYear() {
		return printYear;
	}
	public void setPrintYear(LocalDateTime printYear) {
		this.printYear = printYear;
	}
	
	
}