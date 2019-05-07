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
        //insert C

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
        return relicsServiceImp.removeRelics(id);
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



    @PostMapping("/relicsPhoto/{relicsName}")
    public String uploadRelicsPic(@PathVariable("relicsName") String relicsname, MultipartFile file){
        if(file.isEmpty()){
            return "400";
        }
        String filename = relicsname + file.getOriginalFilename();
        String filepath = ConstantUtils.PIC_PATH + "/" + filename;
        File desFile = new File(filepath);
        try{
            file.transferTo(desFile);
            Relics relics = new Relics();
            relics.setName(relicsname);
            relics.setPhoto(filepath);
            relicsServiceImp.updatePhoto(relics);
            return "200";
        }catch (Exception e){
            return e.getMessage();
        }

    }




}
