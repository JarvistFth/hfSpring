package com.example.hfspring.Model;

public class UserVerify {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userVerify.id
     *
     * @mbg.generated Fri Mar 15 10:14:11 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userVerify.username
     *
     * @mbg.generated Fri Mar 15 10:14:11 CST 2019
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userVerify.password
     *
     * @mbg.generated Fri Mar 15 10:14:11 CST 2019
     */
    private String password;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userVerify
     *
     * @mbg.generated Fri Mar 15 10:14:11 CST 2019
     */
    public UserVerify(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userVerify
     *
     * @mbg.generated Fri Mar 15 10:14:11 CST 2019
     */
    public UserVerify() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userVerify.id
     *
     * @return the value of userVerify.id
     *
     * @mbg.generated Fri Mar 15 10:14:11 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userVerify.id
     *
     * @param id the value for userVerify.id
     *
     * @mbg.generated Fri Mar 15 10:14:11 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userVerify.username
     *
     * @return the value of userVerify.username
     *
     * @mbg.generated Fri Mar 15 10:14:11 CST 2019
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userVerify.username
     *
     * @param username the value for userVerify.username
     *
     * @mbg.generated Fri Mar 15 10:14:11 CST 2019
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userVerify.password
     *
     * @return the value of userVerify.password
     *
     * @mbg.generated Fri Mar 15 10:14:11 CST 2019
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userVerify.password
     *
     * @param password the value for userVerify.password
     *
     * @mbg.generated Fri Mar 15 10:14:11 CST 2019
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}