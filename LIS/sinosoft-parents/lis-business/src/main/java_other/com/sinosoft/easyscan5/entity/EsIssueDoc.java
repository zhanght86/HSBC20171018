package com.sinosoft.easyscan5.entity;
// default package

import java.util.Date;


/**
 * EsIssueDoc entity. @author MyEclipse Persistence Tools
 */

public class EsIssueDoc  implements java.io.Serializable {


    // Fields    

     private String issueNo;
     private String issueType;
     private String issueDesc;
     private String issueStatus;
     private String manageCom;
     private String createUser;
     private Date createDate;
     private String updateUser;
     private Date updateDate;
     private String delFlag;
     private Date delDate;
     private String delUser;
     private String p1;
     private Date p2;
     private Long p3;


    // Constructors

    /** default constructor */
    public EsIssueDoc() {
    }

	/** minimal constructor */
    public EsIssueDoc(String issueNo, String issueType, String issueStatus) {
        this.issueNo = issueNo;
        this.issueType = issueType;
        this.issueStatus = issueStatus;
    }
    
    /** full constructor */
    public EsIssueDoc(String issueNo, String issueType, String issueDesc, String issueStatus, String manageCom, String createUser, Date createDate, String updateUser, Date updateDate, String delFlag, Date delDate, String delUser, String p1, Date p2, Long p3) {
        this.issueNo = issueNo;
        this.issueType = issueType;
        this.issueDesc = issueDesc;
        this.issueStatus = issueStatus;
        this.manageCom = manageCom;
        this.createUser = createUser;
        this.createDate = createDate;
        this.updateUser = updateUser;
        this.updateDate = updateDate;
        this.delFlag = delFlag;
        this.delDate = delDate;
        this.delUser = delUser;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

   
    // Property accessors

    public String getIssueNo() {
        return this.issueNo;
    }
    
    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo;
    }

    public String getIssueType() {
        return this.issueType;
    }
    
    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getIssueDesc() {
        return this.issueDesc;
    }
    
    public void setIssueDesc(String issueDesc) {
        this.issueDesc = issueDesc;
    }

    public String getIssueStatus() {
        return this.issueStatus;
    }
    
    public void setIssueStatus(String issueStatus) {
        this.issueStatus = issueStatus;
    }

    public String getManageCom() {
        return this.manageCom;
    }
    
    public void setManageCom(String manageCom) {
        this.manageCom = manageCom;
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

    public String getDelFlag() {
        return this.delFlag;
    }
    
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Date getDelDate() {
        return this.delDate;
    }
    
    public void setDelDate(Date delDate) {
        this.delDate = delDate;
    }

    public String getDelUser() {
        return this.delUser;
    }
    
    public void setDelUser(String delUser) {
        this.delUser = delUser;
    }

    public String getP1() {
        return this.p1;
    }
    
    public void setP1(String p1) {
        this.p1 = p1;
    }

    public Date getP2() {
        return this.p2;
    }
    
    public void setP2(Date p2) {
        this.p2 = p2;
    }

    public Long getP3() {
        return this.p3;
    }
    
    public void setP3(Long p3) {
        this.p3 = p3;
    }
   








}