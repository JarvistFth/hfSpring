package com.example.hfspring.Controller;

import com.example.hfspring.Model.Users;
import com.example.hfspring.Utils.ConstantUtils;
import com.example.hfspring.service.Impl.UserServiceImp;
import io.netty.handler.codec.base64.Base64Decoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Base64;

@Controller
@RequestMapping("/users")
public class userController {

    @Autowired
    private UserServiceImp userServiceImp = null;

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

    @PutMapping("/updateAvatar/{username}/img64={img64}")
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

    @GetMapping("/getAvatar/{username}")
    public String getAvatar64(@PathVariable("username") String username){
        String filepath = ConstantUtils.AVATAR_PATH + "/" + username + ".jpg";
        File file = new File(filepath);
        if(!file.exists()){
            return ConstantUtils.REQUEST_ERROR + "file does not exist";
        }else{
            byte[] b = null;
            try{
                InputStream inputStream = new FileInputStream(filepath);
                b = new byte[inputStream.available()];
                inputStream.read(b);
                inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(b);
        }
    }

}
