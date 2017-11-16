package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
	private ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
	private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

	private MealRestController mealRestController = appCtx.getBean(MealRestController.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");

		if (id != null) {
			Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
					LocalDateTime.parse(request.getParameter("dateTime")), request.getParameter("description"),
					Integer.valueOf(request.getParameter("calories")), AuthorizedUser.id());

			if (meal.isNew()) {
				log.info("Create {}", meal);
				mealRestController.create(meal);
			} else {
				log.info("Update {}", meal);
				mealRestController.update(meal, Integer.valueOf(id));
			}
			log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
			response.sendRedirect("meals");
			return;
		}

		String userId = request.getParameter("userId");
		if (userId != null) {
			log.info("Authorized " + userId);
			AuthorizedUser.setId(Integer.parseInt(userId));
			request.setAttribute("meals",
					mealRestController.getInPeriod(DateTimeUtil.parseLocalDateOrDefault(request.getParameter("startDate"), null),
							DateTimeUtil.parseLocalDateOrDefault(request.getParameter("endDate"),null), DateTimeUtil.parseLocalTimeOrDefault(request.getParameter("startTime"),null),
							DateTimeUtil.parseLocalTimeOrDefault(request.getParameter("endTime"),null)));
			request.setAttribute("currentId", AuthorizedUser.id());
			request.getRequestDispatcher("/meals.jsp").forward(request, response);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		switch (action == null ? "all" : action) {
		case "delete":
			int id = getId(request);
			log.info("Delete {}", id);
			mealRestController.delete(id);
			request.setAttribute("currentId", AuthorizedUser.id());
			response.sendRedirect("meals");
			break;
		case "create":
		case "update":
			final Meal meal = "create".equals(action)
					? new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, AuthorizedUser.id())
					: mealRestController.get(getId(request));
			request.setAttribute("meal", meal);
			request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
			break;
		case "all":
		default:
			log.info("getAll");
			if (mealRestController == null)
				log.error("Meal controller is null");
			request.setAttribute("meals", mealRestController.getAll());
			request.setAttribute("currentId", AuthorizedUser.id());
			request.getRequestDispatcher("/meals.jsp").forward(request, response);
			break;
		}
	}

	private int getId(HttpServletRequest request) {
		String paramId = Objects.requireNonNull(request.getParameter("id"));
		return Integer.valueOf(paramId);
	}
}
