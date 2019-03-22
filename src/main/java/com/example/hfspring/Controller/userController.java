package com.example.hfspring.Controller;

import com.example.hfspring.Model.Users;
import com.example.hfspring.service.Impl.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class userController {

    @Autowired
    private UserServiceImp userServiceImp = null;

    @PostMapping("/register")
    @ResponseBody
    public boolean insertUser(@RequestBody Users users){
        return userServiceImp.userRregister(users);
    }

    @PostMapping("/loginVerify")
    @ResponseBody
    public String loginVerify(String username, String password){
        Users users = new Users();
        users.setName(username);
        users.setPassword(password);
        boolean verify = userServiceImp.loginVerified(users);
        if(verify){
            return "sucess";
        }
        else{
            return "failure";
        }
    }

    @PutMapping("update")
    @ResponseBody
    public String updateUsers(Users users){
        if(userServiceImp.updateUsers(users)){
            return "update success";
        }
        else{
            return "update failure";
        }
    }
}
