package com.example.hfspring.Controller;


import com.example.hfspring.Model.ResponseCode;
import com.example.hfspring.Model.Transaction;
import com.example.hfspring.fabric.FabricManager;
import com.example.hfspring.service.Impl.TransactionServiceImp;
import com.example.hfspring.service.TransactionService;
import com.google.gson.JsonObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/invoke")
public class transactionController {
    //todo

    private static Log logger = LogFactory.getLog(transactionController.class);

    @Autowired
    private TransactionServiceImp transactionServiceImp;





    @PutMapping("/transfer/{id}")
    @ResponseBody
    public ResponseCode transfer(@PathVariable("id") String id, @RequestBody Transaction transaction){
        transactionServiceImp.initialize(transaction.getOwnerName(),transaction.getOrgName());
        ResponseCode responseCode = new ResponseCode();
        String ret = transactionServiceImp.invokeTransaction(id,transaction.getNewOwnerName());
        if(ret.isEmpty()){
            responseCode.setCode("200");
            responseCode.setMsg("transfer successfully");
        }else{
            responseCode.setCode("400");
            responseCode.setMsg(ret);
        }
        return responseCode;
    }

    @GetMapping("/query/{id}")
    public ResponseCode queryID(@PathVariable("id") String id ,@RequestBody Transaction transaction){
        ResponseCode responseCode = new ResponseCode();
        transactionServiceImp.initialize(transaction.getOwnerName(),transaction.getOrgName());
        String ret = transactionServiceImp.queryRelics(id);
        if(ret.isEmpty()){
            responseCode.setCode("200");
            responseCode.setMsg("transfer successfully");
        }else{
            responseCode.setCode("400");
            responseCode.setMsg(ret);
        }
        return responseCode;
    }


    @GetMapping("/queryHistory/{id}")
    @ResponseBody
    public ResponseCode queryHistoryID(@PathVariable("id") String id, @RequestBody Transaction transaction){
        logger.info(transaction.getOrgName() + transaction.getOwnerName());
        ResponseCode responseCode = new ResponseCode();
        transactionServiceImp.initialize(transaction.getOwnerName(),transaction.getOrgName());
        String ret = transactionServiceImp.queryHistory(id);
        if(ret.isEmpty()){
            responseCode.setCode("200");
            responseCode.setMsg("transfer successfully");
        }else{
            responseCode.setCode("400");
            responseCode.setMsg(ret);
        }
        return responseCode;
    }






}
