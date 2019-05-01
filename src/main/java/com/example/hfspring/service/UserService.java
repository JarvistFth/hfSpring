package com.example.hfspring.service;

import com.example.hfspring.Model.Users;

public interface UserService {
    String userRregister(Users users);

    boolean loginVerified(Users users);

    Users getUsers(Long id);

    Users getUsers(String username);

    int updateUsers(Users users);

    int updatePassword(Users users);


}
