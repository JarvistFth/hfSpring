package com.example.hfspring.service.Impl;

import com.example.hfspring.Dao.UserVerifyMapper;
import com.example.hfspring.Dao.UsersMapper;
import com.example.hfspring.Model.UserVerify;
import com.example.hfspring.Model.Users;
import com.example.hfspring.service.UserService;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UserVerifyMapper userVerifyMapper;

    @Override
    public boolean userRregister(Users users) {
        String userName = users.getName();
        Users SQLusers = new Users();
        try{
            SQLusers = usersMapper.getUsersFromUsername(userName);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(SQLusers == null){
            usersMapper.insert(users);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public boolean loginVerified(Users users) {
        String password = users.getPassword();
        Users loginuser = usersMapper.getUsersFromUsername(users.getName());
        String varifiedPwd = loginuser.getPassword();
        if(password.equals(varifiedPwd)){
            return true;
        }
        return false;
    }

    @Override
    public Users getUsers(Long id) {
        Users users = usersMapper.selectByPrimaryKey(new Integer(id.toString()));
        return users;
    }

    @Override
    public Users getUsers(String username) {
        Users users = usersMapper.getUsersFromUsername(username);
        return users;
    }

    @Override
    public boolean updateUsers(Users users){
        return usersMapper.updateByUserName(users);
    }

    @Override
    public boolean updateUserPassword(UserVerify userVerify) {
        try{
            userVerifyMapper.updatePassword(userVerify);
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
