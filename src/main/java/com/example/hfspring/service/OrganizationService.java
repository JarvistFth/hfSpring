package com.example.hfspring.service;

import com.example.hfspring.Model.Organization;

import java.util.List;

public interface OrganizationService {

    boolean createOrg(Organization organization);

    boolean updateOrg(Organization organization);

    List<Organization> getAllOrgs();

    Organization getOrgsById(Integer id);

    Organization getOrgsByName(String name);

    boolean deleteOrg(Integer id);




}
