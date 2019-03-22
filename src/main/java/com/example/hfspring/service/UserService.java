package com.example.hfspring.service;

import com.example.hfspring.Model.UserVerify;
import com.example.hfspring.Model.Users;
import org.springframework.stereotype.Service;

public interface UserService {
    boolean userRregister(Users users);

    boolean loginVerified(Users users);

    Users getUsers(Long id);

    Users getUsers(String username);

    boolean updateUsers(Users users);

    boolean updateUserPassword(UserVerify userVerify);



}
