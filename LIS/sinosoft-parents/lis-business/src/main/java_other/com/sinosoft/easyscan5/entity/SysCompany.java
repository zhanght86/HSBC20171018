package com.sinosoft.easyscan5.entity;
// default package

import java.util.Date;


/**
 * SysCompany entity. @author MyEclipse Persistence Tools
 */

public class SysCompany  implements java.io.Serializable {


    // Fields    

     private String id;
     private String companyNo;
     private String companyName;
     private String shortName;
     private String supercomNo;
     private String supercomName;
     private String address;
     private String companyState;
     private String delFlag;
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
    public SysCompany() {
    }

	/** minimal constructor */
    public SysCompany(String id, String companyNo, String companyName, String supercomNo, String supercomName, String companyState, String delFlag, String createUser, Date createDate, String updateUser, Date updateDate) {
        this.id = id;
        this.companyNo = companyNo;
        this.companyName = companyName;
        this.supercomNo = supercomNo;
        this.supercomName = supercomName;
        this.companyState = companyState;
        this.delFlag = delFlag;
        this.createUser = createUser;
        this.createDate = createDate;
        this.updateUser = updateUser;
        this.updateDate = updateDate;
    }
    
    /** full constructor */
    public SysCompany(String id, String companyNo, String companyName, String shortName, String supercomNo, String supercomName, String address, String companyState, String delFlag, String createUser, Date createDate, String updateUser, Date updateDate, String p1, String p2, String p3, String p4, String p5, Date p6, Date p7, Long p8, Long p9) {
        this.id = id;
        this.companyNo = companyNo;
        this.companyName = companyName;
        this.shortName = shortName;
        this.supercomNo = supercomNo;
        this.supercomName = supercomName;
        this.address = address;
        this.companyState = companyState;
        this.delFlag = delFlag;
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

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyNo() {
        return this.companyNo;
    }
    
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public String getCompanyName() {
        return this.companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getShortName() {
        return this.shortName;
    }
    
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSupercomNo() {
        return this.supercomNo;
    }
    
    public void setSupercomNo(String supercomNo) {
        this.supercomNo = supercomNo;
    }

    public String getSupercomName() {
        return this.supercomName;
    }
    
    public void setSupercomName(String supercomName) {
        this.supercomName = supercomName;
    }

    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyState() {
        return this.companyState;
    }
    
    public void setCompanyState(String companyState) {
        this.companyState = companyState;
    }

    public String getDelFlag() {
        return this.delFlag;
    }
    
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
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