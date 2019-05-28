package com.example.hfspring.Controller;


import com.alibaba.fastjson.JSONArray;
import com.example.hfspring.Model.*;
import com.example.hfspring.Utils.ConstantUtils;
import com.example.hfspring.fabric.FabricManager;
import com.example.hfspring.service.Impl.RelicsServiceImp;
import com.example.hfspring.service.Impl.TransactionServiceImp;
import com.example.hfspring.service.TransactionService;
import com.google.gson.JsonObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/invoke")
public class transactionController {
    //todo

    private static Log logger = LogFactory.getLog(transactionController.class);

    @Autowired
    private TransactionServiceImp transactionServiceImp;

    @Autowired
    private RelicsServiceImp relicsServiceImp;





    @PutMapping("/transfer/{id}")
    @ResponseBody
    public ResponseCode transfer(@PathVariable("id") String id, @RequestBody Transaction transaction){
        FabricManager manager = new FabricManager(transaction.getOwnerName(),transaction.getOrgName());
//        transactionServiceImp.initialize(transaction.getOwnerName(),transaction.getOrgName());
        ResponseCode responseCode = new ResponseCode();
        try{
            String ret = manager.invoke(ConstantUtils.CC_TRANSFER, id,transaction.getNewOwnerName());
            if(ret.isEmpty()) {
                responseCode.setCode("200");
                responseCode.setMsg("transfer successfully");
                relicsServiceImp.deleteRelicsById(Integer.parseInt(id));
            }
        }catch (Exception e){
            responseCode.setCode("400");
            responseCode.setMsg("error");
        }
        finally {
            manager.removeALL();
        }
        return responseCode;

    }

    @PostMapping("/query")
    @ResponseBody
    public List<TXMine> queryID(@RequestBody Transaction transaction){
        List<TXMine> list = new ArrayList<>();
        logger.info(transaction.getOwnerName() + "\n" +transaction.getOrgName());
        FabricManager manager = new FabricManager(transaction.getOwnerName(),transaction.getOrgName());
//        transactionServiceImp.initialize(transaction.getOwnerName(),transaction.getOrgName());
        try{
            String ret = manager.invoke(ConstantUtils.CC_QUERYBYOWNER, transaction.getOwnerName());
            list = JSONArray.parseArray(ret,TXMine.class);
            if(ret.isEmpty()) {

//                relicsServiceImp.deleteRelicsById(Integer.parseInt(id));
            }
            logger.info(ret);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }finally {
            manager.removeALL();
        }
        return list;
    }


    @PostMapping("/queryHistory/{id}")
    @ResponseBody
    public List<TXResponse> queryHistoryID(@PathVariable("id") String id, @RequestBody Transaction transaction){
        List<TXResponse> list = new ArrayList<>();
//        RetBody body = new RetBody();
        FabricManager manager = new FabricManager(transaction.getOwnerName(),transaction.getOrgName());
//        transactionServiceImp.initialize(transaction.getOwnerName(),transaction.getOrgName());
//        ResponseCode responseCode = new ResponseCode();
        try{
            String ret = manager.invoke(ConstantUtils.CC_QUERY_HISTORY, id);
            if(ret.isEmpty()) {

//                relicsServiceImp.deleteRelicsById(Integer.parseInt(id));
            }
            list = JSONArray.parseArray(ret,TXResponse.class);
//            for(TXResponse response:list){
//                body.setTxId(response.getTxId());
//                body.setTimeStamp(response.getTimestamp());
//                body.setId(response.getValue().getId());
//                body.setName(response.getValue().getName());
//                body.setOwnerName(response.getValue().getOwner());
//            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            manager.removeALL();
        }
        return list;
    }






}
