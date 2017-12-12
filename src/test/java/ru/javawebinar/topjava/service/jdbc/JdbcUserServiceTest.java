package ru.javawebinar.topjava.service.jdbc;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.assertMatch;

import java.util.EnumSet;
import java.util.Set;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {
	@Test
	public void changeRoles() {
		Set<Role> roles = EnumSet.of(Role.ROLE_ADMIN);
		User updated = new User(USER);
        updated.setRoles(roles);
        service.update(updated);
        assertMatch(service.get(USER_ID), updated);
	}
}