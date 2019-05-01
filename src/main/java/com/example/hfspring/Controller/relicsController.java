package com.example.hfspring.Controller;

import com.example.hfspring.Model.Relics;
import com.example.hfspring.Utils.ConstantUtils;
import com.example.hfspring.service.Impl.RelicsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

@Controller
@RequestMapping("/relics")
public class relicsController {

    @Autowired
    private RelicsServiceImp relicsServiceImp = null;

    @PostMapping("/putRelics")
    @ResponseBody
    public boolean putRelics(@RequestBody Relics relics){
        //set fabric client

        //execute chaincode

        return relicsServiceImp.putRelics(relics);
    }

    @PostMapping("/addRelics")
    @ResponseBody
    public String addRelics(@RequestBody Relics relics){
        return relicsServiceImp.addRelics(relics);
    }

    @PutMapping("/updateRelics")
    @ResponseBody
    public boolean updateRelics(@RequestBody Relics relics){
        return relicsServiceImp.updateRelics(relics);
    }


    @DeleteMapping("/removeRelics/{id}")
    @ResponseBody
    public boolean deleteRelics(@PathVariable("id") Integer id){
        return relicsServiceImp.deleteRelicsById(id);
    }

    @GetMapping("/getRelics/{id}")
    @ResponseBody
    public Relics getRelics(@PathVariable("id") Integer id){
        return relicsServiceImp.getRelicsById(id);
    }

    @GetMapping("/getAllRelics")
    @ResponseBody
    public List<Relics> getAllRelics(){
        return relicsServiceImp.getAllRelics();
    }

    @GetMapping("/getAllRelicsNotVerified")
    @ResponseBody
    public List<Relics> getAllNotVerified(){
        return  relicsServiceImp.getAllNotVerified();
    }

    @PostMapping("/uploadRelicsPic/{filename}")
    public String uploadPic(@PathVariable("filename")String filename, MultipartFile file){
        if(file.isEmpty()){
            return ConstantUtils.REQUEST_ERROR;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");
        String filepath = ConstantUtils.PIC_PATH + "/" + filename +"_" + dateFormat.format(new Date());
        File saveFile = new File(filepath);
        if(!saveFile.getParentFile().exists()){
            saveFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(saveFile);
            return ConstantUtils.REQUEST_OK;
        }catch (Exception e){
            return ConstantUtils.REQUEST_ERROR;
        }

    }




}
