package com.example.hfspring.service;

import com.example.hfspring.Model.Relics;

import java.util.List;

public interface RelicsService {
    //add delete select update
    boolean addRelics(Relics relics);

    boolean deleteRelicsById(Integer id);

    Relics getRelicsById(Integer id);

    List<Relics> getAllRelics();

    boolean updateRelics(Relics relics);
}
