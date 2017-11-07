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

public class EditorServlet extends HttpServlet {
	private MealDao mealDao = MealDaoStubImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
	}
}