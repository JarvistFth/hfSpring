package com.example.hfspring.Controller;


import com.example.hfspring.Model.Transaction;
import com.example.hfspring.service.Impl.TransactionServiceImp;
import com.example.hfspring.service.TransactionService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/invoke")
public class transactionController {
    //todo
    @Autowired
    private TransactionServiceImp transactionServiceImp;


    @PutMapping("/transfer/{id}")
    public void transfer(@PathVariable("id") String id, @RequestBody Transaction transaction){
        transactionServiceImp.initialize(transaction.getOwnerName(),transaction.getOrgName());
        transactionServiceImp.queryHistory(id);
    }





}
