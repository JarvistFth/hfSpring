package com.example.hfspring.service.Impl;

import com.example.hfspring.Dao.OrganizationMapper;
import com.example.hfspring.Model.Organization;
import com.example.hfspring.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgServiceImp implements OrganizationService {

    @Autowired
    OrganizationMapper organizationMapper = null;

    @Override
    public boolean createOrg(Organization organization) {
        String userName = organization.getOrgname();
        Organization sqlOrg = new Organization();
        try{
            sqlOrg = organizationMapper.getOrgFromOrgName(userName);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(sqlOrg == null){
            organizationMapper.insert(organization);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public boolean updateOrg(Organization organization) {
        try{
            organizationMapper.updateByPrimaryKey(organization);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Organization> getAllOrgs() {
        return organizationMapper.selectAll();
    }

    @Override
    public Organization getOrgsById(Integer id) {
        return organizationMapper.selectByPrimaryKey(id);
    }

    @Override
    public Organization getOrgsByName(String name) {
        return null;
    }

    @Override
    public boolean deleteOrg(Integer id) {
        try{
            organizationMapper.deleteByPrimaryKey(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
