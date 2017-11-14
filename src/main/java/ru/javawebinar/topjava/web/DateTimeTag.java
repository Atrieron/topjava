package ru.javawebinar.topjava.web;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class DateTimeTag extends SimpleTagSupport {
	private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MM yyyy HH:mm");

	private LocalDateTime date;

	public DateTimeTag() {
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@Override
	public void doTag() throws JspException, IOException {
		try {
			getJspContext().getOut().write(dateTimeFormatter.format(date));
		} catch (Exception e) {
			e.printStackTrace();
			throw new SkipPageException("Exception in date " + date);
		}
	}

}