package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
	private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
	private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
	private AtomicInteger counter = new AtomicInteger(0);

	public InMemoryMealRepositoryImpl() {
		MealsUtil.MEALS.forEach((elt) -> {
			elt.setId(counter.incrementAndGet());
			repository.put(elt.getId(), elt);
		});
	}

	@Override
	public Meal save(Meal meal) {
		if (meal.getUserId() != AuthorizedUser.id())
			return null;

		if (meal.isNew()) {
			meal.setId(counter.incrementAndGet());
		}
		repository.put(meal.getId(), meal);
		return meal;
	}

	@Override
	public boolean delete(int id) {
		if (!repository.containsKey(id) || repository.get(id).getUserId() != AuthorizedUser.id())
			return false;

		repository.remove(id);
		return true;
	}

	@Override
	public Meal get(int id) {
		if (!repository.containsKey(id) || repository.get(id).getUserId() != AuthorizedUser.id())
			return null;

		return repository.get(id);
	}

	@Override
	public List<Meal> getAll() {
		log.info("Requested all meal for " + AuthorizedUser.id());
		return repository.values().stream().filter(elt -> elt.getUserId() == AuthorizedUser.id())
				.sorted(new Comparator<Meal>() {
					@Override
					public int compare(Meal o1, Meal o2) {
						if (o1.getDateTime().isAfter(o2.getDateTime()))
							return 1;
						else if (o2.getDateTime().isAfter(o1.getDateTime()))
							return -1;
						return 0;
					}
				}).collect(Collectors.toList());
	}

	@Override
	public List<Meal> getInPeriod(LocalDate periodStart, LocalDate periodEnd, LocalTime timeStart, LocalTime timeEnd) {
		LocalDateTime ld = LocalDateTime.now();
		return repository.values().stream()
				.filter(elt -> elt.getUserId() == AuthorizedUser.id()
						&& DateTimeUtil.isBetween(elt.getDateTime().toLocalDate(), periodStart, periodEnd)
						&& DateTimeUtil.isBetween(elt.getDateTime().toLocalTime(), timeStart, timeEnd))
				.sorted(new Comparator<Meal>() {
					@Override
					public int compare(Meal o1, Meal o2) {
						if (o1.getDateTime().isAfter(o2.getDateTime()))
							return 1;
						else if (o2.getDateTime().isAfter(o1.getDateTime()))
							return -1;
						return 0;
					}
				}).collect(Collectors.toList());
	}
}
