package com.example.hfspring.service;

import com.example.hfspring.Model.Relics;
import com.example.hfspring.Model.ResponseCode;

import javax.xml.ws.Response;
import java.util.List;

public interface RelicsService {
    //add delete select update
    boolean putRelics(Relics relics);

    boolean deleteRelicsById(Integer id);

    Relics getRelicsById(Integer id);

    List<Relics> getAllRelics();

    boolean updateRelics(Relics relics);

    List<Relics> getAllVerifiedRelics();

    int updateRelicsVerify(Relics relics);

    List<Relics> getAllNotVerified();

    String deleteRelicsByName(Relics relics);

    String addRelics(Relics relics);

    String updatePhoto(Relics relics);

    ResponseCode removeRelics(String name);

    ResponseCode updateRelicsByName(Relics relics);


}
