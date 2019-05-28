package com.example.hfspring.Dao;

import com.example.hfspring.Model.Organization;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("OrganizationMapper")
public interface OrganizationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organization
     *
     * @mbg.generated Mon Mar 25 20:08:08 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organization
     *
     * @mbg.generated Mon Mar 25 20:08:08 CST 2019
     */
    int insert(Organization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organization
     *
     * @mbg.generated Mon Mar 25 20:08:08 CST 2019
     */
    Organization selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organization
     *
     * @mbg.generated Mon Mar 25 20:08:08 CST 2019
     */
    List<Organization> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organization
     *
     * @mbg.generated Mon Mar 25 20:08:08 CST 2019
     */
    int updateByPrimaryKey(Organization record);

    int updateByOrgName(Organization record);

    int deleteByOrgName(String name);

    Organization selectByOrgName(String name);
}