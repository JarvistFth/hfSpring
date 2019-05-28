package com.example.hfspring.service.Impl;

import com.example.hfspring.Dao.RelicsMapper;
import com.example.hfspring.Dao.UsersMapper;
import com.example.hfspring.Model.Relics;
import com.example.hfspring.Model.ResponseCode;
import com.example.hfspring.Utils.ConstantUtils;
import com.example.hfspring.Utils.hfUtils;
import com.example.hfspring.demo.FabricStore;
import com.example.hfspring.demo.FabricUser;
import com.example.hfspring.fabric.FabricManager;
import com.example.hfspring.service.RelicsService;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
@Transactional
public class RelicsServiceImp implements RelicsService {

    @Autowired
    RelicsMapper relicsMapper = null;



    @Override
    public boolean putRelics(Relics relics) {
        try{
            relics.setOnsalestime(new Date());
            relics.setVerified(false);
            relicsMapper.insert(relics);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean deleteRelicsById(Integer id) {
        try{
            relicsMapper.deleteByPrimaryKey(id);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public Relics getRelicsById(Integer id) {
        return relicsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Relics> getAllRelics() {
        return relicsMapper.selectAll();
    }

    @Override
    public boolean updateRelics(Relics relics) {
        try{
            relicsMapper.updateByPrimaryKey(relics);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public List<Relics> getAllVerifiedRelics() {
        return relicsMapper.selectByVerified();
    }

    @Override
    public int updateRelicsVerify(Relics relics) {
        return relicsMapper.updateVerified(relics);
    }

    @Override
    public List<Relics> getAllNotVerified() {
        return relicsMapper.selectByNotVerified();
    }

    @Override
    public String deleteRelicsByName(Relics relics) {
        if(relicsMapper.isVerified(relics.getName()))
        {
            try{
                relicsMapper.deleteByName(relics.getName());
                return ConstantUtils.REQUEST_OK;
            }catch (Exception e){
                return ConstantUtils.REQUEST_ERROR;
            }
        }else{
            relicsMapper.deleteByName(relics.getName());
            return ConstantUtils.REQUEST_OK;
        }
    }

    @Override
    public String addRelics( Relics relics) {
        try{
            hfUtils.getManager().invoke(ConstantUtils.CC_INIT,relics.getId().toString());
            return ConstantUtils.REQUEST_OK;
        }catch (Exception e){
            return ConstantUtils.REQUEST_ERROR;
        }

    }

    @Override
    public String updatePhoto(Relics relics) {
       try{
           relicsMapper.updatePhoto(relics);
            return ConstantUtils.REQUEST_OK;
        }catch (Exception e){
            return ConstantUtils.REQUEST_ERROR;
        }
    }

    @Override
    public ResponseCode removeRelics(String name) {
        ResponseCode res = new ResponseCode();
        try{
            relicsMapper.deleteByName(name);
            res.setMsg("delete successfully");
            res.setCode("200");
        }catch (Exception e){
            res.setMsg(e.getMessage());
            res.setCode("400");
        }
        return res;
    }

    @Override
    public ResponseCode updateRelicsByName(Relics relics) {
        ResponseCode res = new ResponseCode();
        try{
            relicsMapper.updateByName(relics);
            res.setMsg("update successfully");
            res.setCode("200");
        }catch (Exception e){
            res.setMsg(e.getMessage());
            res.setCode("400");
        }
        return res;
    }




}
