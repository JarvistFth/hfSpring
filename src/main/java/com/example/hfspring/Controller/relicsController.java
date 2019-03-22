package com.example.hfspring.Controller;

import com.example.hfspring.Model.Relics;
import com.example.hfspring.service.Impl.RelicsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/relics")
public class relicsController {

    @Autowired
    private RelicsServiceImp relicsServiceImp = null;

    @PostMapping("/addRelics")
    @ResponseBody
    public boolean addRelics(@RequestBody Relics relics){
        //set fabric client

        //execute chaincode

        return relicsServiceImp.addRelics(relics);
    }

    @PutMapping("/updateRelics")
    @ResponseBody
    public boolean updateRelics(@RequestBody Relics relics){
        return relicsServiceImp.updateRelics(relics);
    }


    @DeleteMapping("/deleteRelics/{id}")
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


}
