package com.example.hfspring.Controller;

import com.example.hfspring.Model.Relics;
import com.example.hfspring.Model.ResponseCode;
import com.example.hfspring.Model.UserInfo;
import com.example.hfspring.Model.Users;
import com.example.hfspring.Utils.ConstantUtils;
import com.example.hfspring.service.Impl.RelicsServiceImp;
import com.example.hfspring.service.Impl.UserServiceImp;
import io.netty.handler.codec.base64.Base64Decoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;

@RestController
@RequestMapping("/users")
public class userController {

    @Autowired
    private UserServiceImp userServiceImp = null;

    @Autowired
    private RelicsServiceImp relicsServiceImp = null;

    @PostMapping("/register")
    @ResponseBody
    public String insertUser(@RequestBody Users users){
        return userServiceImp.userRregister(users);
    }

    @PostMapping("/loginVerify")
    @ResponseBody
    public String loginVerify(@RequestBody Users loginUsers){
        boolean verify = userServiceImp.loginVerified(loginUsers);
        if(verify){
            return "success";
        }
        else{
            return "failure";
        }
    }

    @PutMapping("/update")
    @ResponseBody
    public int updateUsers(Users users){
        return userServiceImp.updateUsers(users);
    }

    @PostMapping("/avatar/{username}")
    public String uploadAvatar(@PathVariable("username") String username, MultipartFile file){
        if(file.isEmpty()){
            return "400";
        }
        String filename = username + file.getOriginalFilename();
        String filepath = ConstantUtils.AVATAR_PATH + "/" + filename;
        File desFile = new File(filepath);
        try{
            file.transferTo(desFile);
            Users users = new Users();
            users.setName(username);
            users.setAvatar(filepath);
            userServiceImp.updateUsers(users);
            return "200";
        }catch (Exception e){
            return e.getMessage();
        }

    }

    @PutMapping("/avatar/{username}/img64={img64}")
    public String genAvatar(@PathVariable("img64") String img64,@PathVariable("username") String username){
        if(img64 == null){
            return "string is null";
        }
        Base64.Decoder decoder = Base64.getDecoder();
        try{
            byte[] b = decoder.decode(img64);
            for(int i=0;i<b.length;i++){
                b[i] += 256;
            }
            String filePath = ConstantUtils.AVATAR_PATH + "/" + username + ".jpg";
            OutputStream out = new FileOutputStream(filePath);
            out.write(b);
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ConstantUtils.REQUEST_OK;
    }

    @GetMapping("/details/{username}")
    @ResponseBody
    public UserInfo getUserDetails(@PathVariable("username")String username){

        Users users = new Users();
        users = userServiceImp.getUsers(username);
        UserInfo userinfo = new UserInfo(users.getName(),users.getSex(),
                users.getCreatetime().toString(),
                users.getOrganization(),users.getAddress(),
                users.getTelephone(),users.getAvatar(), users.getBalance());
       return userinfo;
    }





}
