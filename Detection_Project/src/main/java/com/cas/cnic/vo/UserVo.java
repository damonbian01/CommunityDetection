package com.cas.cnic.vo;

/**
 * Created by ouxuan on 2017/5/22.
 */
public class UserVo {
    /**
     * 用户id
     */
    private int id;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户单位
     */
    private String userOrg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserOrg() {
        return userOrg;
    }

    public void setUserOrg(String userOrg) {
        this.userOrg = userOrg;
    }
}
