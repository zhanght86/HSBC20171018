package com.sinosoft.easyscan5.entity;
// default package

import java.util.Date;


/**
 * EsComServer entity. @author MyEclipse Persistence Tools
 */

public class EsComServer  implements java.io.Serializable {


    // Fields    

     private EsComServerId id;
     private String remark;
     private String createUser;
     private Date createDate;
     private String updateUser;
     private Date updateDate;
     private String p1;
     private String p2;
     private String p3;
     private String p4;
     private String p5;
     private Date p6;
     private Date p7;
     private Long p8;
     private Long p9;


    // Constructors

    /** default constructor */
    public EsComServer() {
    }

	/** minimal constructor */
    public EsComServer(EsComServerId id,  String createUser, Date createDate, String updateUser, Date updateDate) {
        this.id = id;
        this.createUser = createUser;
        this.createDate = createDate;
        this.updateUser = updateUser;
        this.updateDate = updateDate;
    }
    
    /** full constructor */
    public EsComServer(EsComServerId id,  String remark,  String createUser, Date createDate, String updateUser, Date updateDate, String p1, String p2, String p3, String p4, String p5, Date p6, Date p7, Long p8, Long p9) {
        this.id = id;
        this.remark = remark;
        this.createUser = createUser;
        this.createDate = createDate;
        this.updateUser = updateUser;
        this.updateDate = updateDate;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;
        this.p6 = p6;
        this.p7 = p7;
        this.p8 = p8;
        this.p9 = p9;
    }

   
    // Property accessors

    public EsComServerId getId() {
        return this.id;
    }
    
    public void setId(EsComServerId id) {
        this.id = id;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateUser() {
        return this.createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }
    
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getP1() {
        return this.p1;
    }
    
    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return this.p2;
    }
    
    public void setP2(String p2) {
        this.p2 = p2;
    }

    public String getP3() {
        return this.p3;
    }
    
    public void setP3(String p3) {
        this.p3 = p3;
    }

    public String getP4() {
        return this.p4;
    }
    
    public void setP4(String p4) {
        this.p4 = p4;
    }

    public String getP5() {
        return this.p5;
    }
    
    public void setP5(String p5) {
        this.p5 = p5;
    }

    public Date getP6() {
        return this.p6;
    }
    
    public void setP6(Date p6) {
        this.p6 = p6;
    }

    public Date getP7() {
        return this.p7;
    }
    
    public void setP7(Date p7) {
        this.p7 = p7;
    }

    public Long getP8() {
        return this.p8;
    }
    
    public void setP8(Long p8) {
        this.p8 = p8;
    }

    public Long getP9() {
        return this.p9;
    }
    
    public void setP9(Long p9) {
        this.p9 = p9;
    }
   








}