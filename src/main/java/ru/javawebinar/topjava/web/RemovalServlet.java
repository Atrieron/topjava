package ru.javawebinar.topjava.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoStubImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import java.io.IOException;

public class RemovalServlet extends HttpServlet {
	private MealDao mealDao = MealDaoStubImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		request.setAttribute("mealsList", MealsUtil.getWithExceeded(mealDao.getMeals(),2000));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
	}
}