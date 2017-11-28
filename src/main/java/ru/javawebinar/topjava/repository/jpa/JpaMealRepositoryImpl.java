package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public Meal save(Meal meal, int userId) {
		if(!meal.isNew()&&(meal.getUser()==null||meal.getUser().getId().intValue()!=userId)) {
			return null;
		}
		
		if (meal.isNew()) {	
			User ref = em.getReference(User.class, userId);
			meal.setUser(ref);
			em.persist(meal);
			return meal;
		} else {
			return em.merge(meal);
		}
	}

	@Override
	@Transactional
	public boolean delete(int id, int userId) {
		return em.createNamedQuery(Meal.DELETE).setParameter("id", id).setParameter("user_id", userId)
				.executeUpdate() != 0;
	}

	@Override
	public Meal get(int id, int userId) {
		List<Meal> meals = em.createNamedQuery(Meal.FIND_BY_ID, Meal.class).setParameter("id", id)
				.setParameter("user_id", userId).getResultList();
		return DataAccessUtils.singleResult(meals);
	}

	@Override
	public List<Meal> getAll(int userId) {
		return em.createNamedQuery(Meal.ALL_SORTED, Meal.class).setParameter("user_id", userId).getResultList();
	}

	@Override
	public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
		return em.createNamedQuery(Meal.FIND_BETWEEN, Meal.class).setParameter("user_id", userId)
				.setParameter("startTime", startDate).setParameter("endTime", endDate).getResultList();
	}
}