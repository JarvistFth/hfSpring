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
    public ResponseCode insertUser(@RequestBody Users users){
        return userServiceImp.userRregister(users);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseCode loginVerify(@RequestBody Users loginUsers){
        boolean verify = userServiceImp.loginVerified(loginUsers);
        ResponseCode responseCode = new ResponseCode();
        if(verify){
            responseCode.setCode("200");
            responseCode.setMsg("登录成功");
        }
        else{
            responseCode.setCode("400");
            responseCode.setMsg("用户名密码错误");
        }
        return responseCode;
    }

    @PutMapping("/update")
    @ResponseBody
    public int updateUsers(Users users){
        return userServiceImp.updateUsers(users);
    }

    @PostMapping("/avatar/{username}")
    public ResponseCode uploadAvatar(@PathVariable("username") String username, MultipartFile file){
        ResponseCode responseCode = new ResponseCode();
        if(file.isEmpty()){
            responseCode.setMsg("file is not exist");
            responseCode.setCode("400");
        }
        String filename = username + file.getOriginalFilename();
        String filepath = ConstantUtils.AVATAR_PATH + "/" + filename;
        File desFile = new File(filepath);
        try{
            file.transferTo(desFile);
            Users users = new Users();
            users.setName(username);
            users.setAvatar(filename);
            userServiceImp.updateAvatar(users);
            responseCode.setMsg("avatar save successfully");
            responseCode.setCode("200");
        }catch (Exception e){
            e.printStackTrace();
            responseCode.setMsg(e.getMessage());
            responseCode.setCode("400");
        }
        return responseCode;
    }

//    @PutMapping("/avatar/{username}/img64={img64}")
//    public String genAvatar(@PathVariable("img64") String img64,@PathVariable("username") String username){
//        if(img64 == null){
//            return "string is null";
//        }
//        Base64.Decoder decoder = Base64.getDecoder();
//        try{
//            byte[] b = decoder.decode(img64);
//            for(int i=0;i<b.length;i++){
//                b[i] += 256;
//            }
//            String filePath = ConstantUtils.AVATAR_PATH + "/" + username + ".jpg";
//            OutputStream out = new FileOutputStream(filePath);
//            out.write(b);
//            out.flush();
//            out.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return ConstantUtils.REQUEST_OK;
//    }

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
