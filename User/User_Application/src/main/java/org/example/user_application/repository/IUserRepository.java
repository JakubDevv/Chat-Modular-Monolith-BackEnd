package org.example.user_application.repository;

import aggregates.UserAggregate;
import models.Interest;
import models.User;

import java.util.List;

public interface IUserRepository {

    User getUserById(Long id);

    User getUserByUsername(String username);

    UserAggregate getUserAggregateByUserId(Long id);

    void save(UserAggregate user);

    List<Interest> getUserInterestsByUserId(Long id);

    void removeInterestById(Long id);

    Long addInterest(Long userId, String interest);

    List<User> getAllUsers();

    void save(User user);

    Long countAllGroup(Long userId);

    Long countAllCommonGroups(Long userId, Long userId2);
}
