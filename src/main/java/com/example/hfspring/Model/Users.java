package com.example.hfspring.Model;

import java.util.Date;

public class Users {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fabsystem..users.id
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fabsystem..users.name
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fabsystem..users.sex
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    private String sex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fabsystem..users.createtime
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    private Date createtime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fabsystem..users.organization
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    private String organization;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fabsystem..users.address
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    private String address;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fabsystem..users.telephone
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    private String telephone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fabsystem..users.password
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fabsystem..users.avatar
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    private String avatar;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fabsystem..users
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public Users(Integer id, String name, String sex, Date createtime, String organization, String address, String telephone, String password, String avatar) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.createtime = createtime;
        this.organization = organization;
        this.address = address;
        this.telephone = telephone;
        this.password = password;
        this.avatar = avatar;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fabsystem..users
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public Users() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fabsystem..users.id
     *
     * @return the value of fabsystem..users.id
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fabsystem..users.id
     *
     * @param id the value for fabsystem..users.id
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fabsystem..users.name
     *
     * @return the value of fabsystem..users.name
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fabsystem..users.name
     *
     * @param name the value for fabsystem..users.name
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fabsystem..users.sex
     *
     * @return the value of fabsystem..users.sex
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public String getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fabsystem..users.sex
     *
     * @param sex the value for fabsystem..users.sex
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fabsystem..users.createtime
     *
     * @return the value of fabsystem..users.createtime
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fabsystem..users.createtime
     *
     * @param createtime the value for fabsystem..users.createtime
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fabsystem..users.organization
     *
     * @return the value of fabsystem..users.organization
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fabsystem..users.organization
     *
     * @param organization the value for fabsystem..users.organization
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public void setOrganization(String organization) {
        this.organization = organization == null ? null : organization.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fabsystem..users.address
     *
     * @return the value of fabsystem..users.address
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fabsystem..users.address
     *
     * @param address the value for fabsystem..users.address
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fabsystem..users.telephone
     *
     * @return the value of fabsystem..users.telephone
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fabsystem..users.telephone
     *
     * @param telephone the value for fabsystem..users.telephone
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fabsystem..users.password
     *
     * @return the value of fabsystem..users.password
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fabsystem..users.password
     *
     * @param password the value for fabsystem..users.password
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fabsystem..users.avatar
     *
     * @return the value of fabsystem..users.avatar
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fabsystem..users.avatar
     *
     * @param avatar the value for fabsystem..users.avatar
     *
     * @mbg.generated Tue Mar 12 10:35:11 CST 2019
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    @Override
    public String toString() {
        return "User Message:{" +
                "userID = '" + id + '\'' +
                ", userName '" + name + '\'' +
                '}';
    }
}