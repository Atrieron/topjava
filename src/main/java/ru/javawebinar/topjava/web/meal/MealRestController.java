package ru.javawebinar.topjava.web.meal;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@Controller
public class MealRestController {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private MealService service;

	public Meal create(Meal meal) {
		log.info("create {}", meal);
		checkNew(meal);
		return service.create(meal);
	}

	public void delete(int id) throws NotFoundException {
		log.info("delete {}", id);
		service.delete(id);
	}

	public Meal get(int id) throws NotFoundException {
		log.info("get {}", id);
		return service.get(id);
	}

	public void update(Meal meal, int id) {
		log.info("update {} with id={}", meal, id);
		assureIdConsistent(meal, id);
		service.update(meal);
	}

	public List<MealWithExceed> getAll() {
		log.info("getAll");
		if (service == null)
			log.error("Meal service is null");
		return MealsUtil.getWithExceeded(service.getAll(), AuthorizedUser.getCaloriesPerDay());
	}

	public List<MealWithExceed> getInPeriod(LocalDate periodStart, LocalDate periodEnd, LocalTime timeStart,
			LocalTime timeEnd) {
		StringBuilder stringBuilder = new StringBuilder("Get in period");
		if (periodStart != null)
			stringBuilder.append(" after " + periodStart);
		if (periodEnd != null)
			stringBuilder.append(" before " + periodEnd);
		if (timeStart != null)
			stringBuilder.append(" after " + timeStart);
		if (timeEnd != null)
			stringBuilder.append(" before " + timeEnd);
		log.info(stringBuilder.toString());
		return MealsUtil.getWithExceeded(service.getInPeriod(periodStart == null ? LocalDate.MIN : periodStart,
				periodEnd == null ? LocalDate.MAX : periodEnd, timeStart == null ? LocalTime.MIN : timeStart,
				timeEnd == null ? LocalTime.MAX : timeEnd), AuthorizedUser.getCaloriesPerDay());
	}
}