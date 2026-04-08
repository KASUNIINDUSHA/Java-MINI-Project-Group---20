package com.java_lms_group_20.Service;

import com.java_lms_group_20.Model.User;
import com.java_lms_group_20.Repository.LoginRepository;
import java.util.Optional;

public class LoginService {

    private final LoginRepository repository = new LoginRepository();

    public User authenticate(String username, String password) throws Exception {

        Optional<User> userOpt = repository.findByUsername(username);

        if (userOpt.isEmpty()) {
            throw new Exception("User not found!");
        }

        User user = userOpt.get();

        if (!user.getPassword().equals(password)) {
            throw new Exception("Invalid password!");
        }

        return user;
    }
}