package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {
	@Autowired
	private UserService service;
	@Autowired
	private MealService mealService;

	@GetMapping("/")
	public String root() {
		return "index";
	}

	@GetMapping("/users")
	public String users(Model model) {
		model.addAttribute("users", service.getAll());
		return "users";
	}

	@PostMapping("/users")
	public String setUser(HttpServletRequest request) {
		int userId = Integer.valueOf(request.getParameter("userId"));
		AuthorizedUser.setId(userId);
		return "redirect:meals";
	}

	@GetMapping("/meals")
	public String meals(Model model) {
		model.addAttribute("meals",
				MealsUtil.getWithExceeded(mealService.getAll(AuthorizedUser.id()),
				AuthorizedUser.getCaloriesPerDay()));
		return "meals";
	}
	
	@PostMapping("/meals")
	public String mealPost(HttpServletRequest request) {
		Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (request.getParameter("id").isEmpty()) {
        	mealService.create(meal, AuthorizedUser.id());
        } else {
        	meal.setId(getId(request));
        	mealService.update(meal, AuthorizedUser.id());
        }
        
		return "redirect:meals";
	}
	
	@PostMapping("/meals/filter")
	public String mealsFilter(HttpServletRequest request) {
		LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        
        int userId = AuthorizedUser.id();

        List<Meal> mealsDateFiltered = mealService.getBetweenDates(
                startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                endDate != null ? endDate : DateTimeUtil.MAX_DATE, userId);        
        
        request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(mealsDateFiltered,
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()));        
		return "./meals";
	}
	
	@GetMapping("/meals/update/{id}")
	public String udpateMeal(@PathVariable("id") int id, Model model) {		 
		model.addAttribute("meal", mealService.get(id, AuthorizedUser.id()));		
		return "mealForm";
	}
	
	@GetMapping("/meals/create")
	public String createMeal(Model model) {		 
		model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));		
		return "mealForm";
	}
	
	@GetMapping("/meals/delete/{id}")
	public String deleteMeal(@PathVariable("id") int id, Model model) {		 
		mealService.delete(id, AuthorizedUser.id());		
		return "redirect:/meals";
	}
	
	private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}