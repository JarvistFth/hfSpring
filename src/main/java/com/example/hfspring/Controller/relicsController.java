package com.example.hfspring.Controller;

import com.example.hfspring.Model.Relics;
import com.example.hfspring.Model.ResponseCode;
import com.example.hfspring.Utils.ConstantUtils;
import com.example.hfspring.demo.FabricStore;
import com.example.hfspring.fabric.FabricManager;
import com.example.hfspring.service.Impl.RelicsServiceImp;
import com.example.hfspring.service.Impl.TransactionServiceImp;
import com.example.hfspring.service.TransactionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.sdk.ChaincodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.SimpleFormatter;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/relics")
@Transactional
public class relicsController {

    private static Log logger = LogFactory.getLog(relicsController.class);

    @Autowired
    private RelicsServiceImp relicsServiceImp = null;

    @Autowired
    private TransactionServiceImp transactionServiceImp = null;


    @PostMapping("/putRelics/{username}/{orgname}")
    @ResponseBody

    public ResponseCode putRelics(@PathVariable("username")String username,
                                  @PathVariable("orgname")String orgname,
                                  @RequestBody Relics relics){
        //insert C
        ResponseCode responseCode = new ResponseCode();

        relicsServiceImp.putRelics(relics);
        FabricManager manager = new FabricManager(username,orgname);
        try{
            String msg =  manager.invoke(ConstantUtils.CC_INIT,relics.getId().toString(),relics.getName(),relics.getPoster());
            responseCode.setCode("200");
            if(msg.isEmpty()){
                msg = "marble on chain successful";
            }
            responseCode.setMsg(msg);
            manager.removeALL();
        }catch (Exception e){
            responseCode.setCode("400");
            responseCode.setMsg("Error");
            e.getMessage();
        }
        return responseCode;
    }


    //U
    @PutMapping("/updateRelics")
    @ResponseBody
    public boolean updateRelics(@RequestBody Relics relics){
        return relicsServiceImp.updateRelics(relics);
    }

    //D
    @DeleteMapping("/removeRelics/{name}")
    @ResponseBody
    public ResponseCode deleteRelics(@PathVariable("name") String name){
        return relicsServiceImp.removeRelics(name);
    }
    //R
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

    @GetMapping("/getAllRelicsVerified")
    @ResponseBody
    public List<Relics> getAllVarified(){
        return relicsServiceImp.getAllVerifiedRelics();
    }


    //
    @PostMapping("/relicsPhoto/{relicsName}")
    public ResponseCode uploadRelicsPic(@PathVariable("relicsName") String relicsname, MultipartFile file){
        ResponseCode responseCode = new ResponseCode();

        if(file.isEmpty()){
            responseCode.setCode("400");
            responseCode.setMsg("file not exist！");
        }
        String filename = relicsname + file.getOriginalFilename();
        String filepath = ConstantUtils.PIC_PATH + "/" + filename;
        File desFile = new File(filepath);
        try{
            file.transferTo(desFile);
            Relics relics = new Relics();
            relics.setName(relicsname);
            relics.setPhoto(filename);
            relicsServiceImp.updatePhoto(relics);
            responseCode.setCode("200");
            responseCode.setMsg("file upload successful！");
        }catch (Exception e){
            responseCode.setCode("400");
            responseCode.setMsg(e.getMessage());
        }
        return responseCode;
    }




}
