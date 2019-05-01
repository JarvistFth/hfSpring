package com.example.hfspring.Controller;


import com.example.hfspring.Model.Transaction;
import com.example.hfspring.service.Impl.TransactionServiceImp;
import com.example.hfspring.service.TransactionService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/invoke")
public class transactionController {
    //todo
    @Autowired
    private TransactionServiceImp transactionServiceImp;


    @PutMapping("/transfer/{id}")
    @ResponseBody
    public String transfer(@PathVariable("id") String id, @RequestBody Transaction transaction){
        transactionServiceImp.initialize(transaction.getOwnerName(),transaction.getOrgName());
        return transactionServiceImp.invokeTransaction(id,transaction.getNewOwnerName());
    }

    @GetMapping("/query/{id}")
    public String queryID(@PathVariable("id") String id ,@RequestBody Transaction transaction){
        transactionServiceImp.initialize(transaction.getOwnerName(),transaction.getOrgName());
        return transactionServiceImp.queryRelics(id);
    }


    @GetMapping("/queryHistory/{id}")
    public String queryHistoryID(@PathVariable("id") String id,@RequestBody Transaction transaction){
        transactionServiceImp.initialize(transaction.getOwnerName(),transaction.getOrgName());
        return transactionServiceImp.queryHistory(id);
    }

    @GetMapping("/getHistory/{id}")
    public String getHistory(@PathVariable("id") String id){
        return "\"[{\\\"TxId\\\":\\\"ab946614141489b0ebff929ef9c649d51262f20ea55cd37068f3107644f10a2b\\\", " +
                "\\\"Value\\\":{\\\"docType\\\":\\\"relic\\\",\\\"id\\\":\\\"2015212153\\\",\\\"name\\\":\\\"moder\\\",\\\"owner\\\":\\\"jar\\\"}, " +
                "\\\"Timestamp\\\":\\\"2019-04-24 02:46:54.103043035 +0000 UTC\\\", \\\"IsDelete\\\":\\\"false\\\"}," +
                "{\\\"TxId\\\":\\\"55b514ee29c1133fce95dbf9dc47ffa9f5ad0d54e6f19108b7d600235b59e5de\\\", " +
                "\\\"Value\\\":{\\\"docType\\\":\\\"relic\\\",\\\"id\\\":\\\"2015212153\\\",\\\"name\\\":\\\"moder\\\",\\\"owner\\\":\\\"huahua\\\"}, " +
                "\\\"Timestamp\\\":\\\"2019-04-24 02:47:11.971340754 +0000 UTC\\\", \\\"IsDelete\\\":\\\"false\\\"}," +
                "{\\\"TxId\\\":\\\"f67817dd01bbbb1ada4fa39e4fad2aa7fd2dac1b0025e6874f769f0dd018c515\\\", " +
                "\\\"Value\\\":{\\\"docType\\\":\\\"relic\\\",\\\"id\\\":\\\"2015212153\\\",\\\"name\\\":\\\"moder\\\",\\\"owner\\\":\\\"jjw\\\"}, " +
                "\\\"Timestamp\\\":\\\"2019-04-24 02:47:20.799888433 +0000 UTC\\\", \\\"IsDelete\\\":\\\"false\\\"}]\" \n";
    }





}
