package com.example.hfspring.service.Impl;

import com.example.hfspring.Dao.RelicsMapper;
import com.example.hfspring.Model.Relics;
import com.example.hfspring.service.RelicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelicsServiceImp implements RelicsService {

    @Autowired
    RelicsMapper relicsMapper = null;
    @Override
    public boolean addRelics(Relics relics) {
        try{
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
}
