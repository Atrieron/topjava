package ru.javawebinar.topjava.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoStubImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.io.IOException;
import java.time.LocalDateTime;

public class MealsServlet extends HttpServlet {
	private MealDao mealDao = MealDaoStubImpl.getInstance();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Meal meal = new Meal(LocalDateTime.parse(req.getParameter("dateTime")), req.getParameter("description"),
				Integer.parseInt(req.getParameter("calories")));

		if (req.getParameter("id") != null) {
			meal.setId(Integer.parseInt(req.getParameter("id")));
		}		
		mealDao.save(meal);
		
		resp.sendRedirect("meals");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String actionParameter = request.getParameter("action");
		if (actionParameter == null) {
			request.setAttribute("mealsList", MealsUtil.getWithExceeded(mealDao.getMeals(), 2000));
			request.getRequestDispatcher("/meals.jsp").forward(request, response);
		} else if (actionParameter.equals("remove")) {
			if (request.getParameter("id") != null) {
				Integer id = null;
				try {
					id = Integer.parseInt(request.getParameter("id"));
				} catch (NumberFormatException ex) {
				}

				if (id != null) {
					mealDao.removeById(id);
				}
			}
			response.sendRedirect("meals");
		} else if (actionParameter.equals("edit")) {
			Meal meal = null;
			if (request.getParameter("id") != null) {
				Integer id = null;
				try {
					id = Integer.parseInt(request.getParameter("id"));
				} catch (NumberFormatException ex) {
				}

				if (id != null) {
					meal = mealDao.getById(id);
				}
			}
			request.setAttribute("meal", meal);
			request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
		} else if (actionParameter.equals("add")) {
			request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
		}
	}
}