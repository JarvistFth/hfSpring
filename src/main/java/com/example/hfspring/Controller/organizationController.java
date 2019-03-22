package com.example.hfspring.Controller;

import com.example.hfspring.Model.Organization;
import com.example.hfspring.service.Impl.OrgServiceImp;
import com.example.hfspring.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/organizations")
public class organizationController {

    @Autowired
    private OrgServiceImp orgServiceImp = null;

    @PostMapping("/register")
    @ResponseBody
    public boolean addOrg(@RequestBody Organization organization){
        return orgServiceImp.createOrg(organization);
    }

    @PutMapping("/modify")
    @ResponseBody
    public boolean modifyOrg(@RequestBody Organization organization){
        return orgServiceImp.updateOrg(organization);
    }

    @DeleteMapping("/deleteOrg/{id}")
    @ResponseBody
    public boolean deleteOrg(@PathVariable("id") Integer id){
        return orgServiceImp.deleteOrg(id);
    }

    @GetMapping("organization/{id}")
    @ResponseBody
    public Organization getOrg(@PathVariable("id") Integer id){
        return orgServiceImp.getOrgsById(id);
    }

    @GetMapping("organization/all")
    @ResponseBody
    public List<Organization> getallOrg(){
        return orgServiceImp.getAllOrgs();
    }

}
