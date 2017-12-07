package ru.javawebinar.topjava.service.datajpa;

import static ru.javawebinar.topjava.UserTestData.USER_WITH_MEALS;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles("datajpa")
public class DataJPAUserServiceTest extends UserServiceTest {
	@Test
    public void getWithMeals() throws Exception {
        User user = service.getWithMeals(USER_ID);
        assertThat(user).isEqualToIgnoringGivenFields(USER_WITH_MEALS, "registered", "roles");
    }
}