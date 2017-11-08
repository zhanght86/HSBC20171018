package com.sinosoft.easyscan5.entity;
// default package

import java.util.Date;


/**
 * EsDocTypeExpPropertyHis entity. @author MyEclipse Persistence Tools
 */

public class EsDocTypeExpPropertyHis  implements java.io.Serializable {


    // Fields    

     private String id;
     private String bussType;
     private String subType;
     private String columnName;
     private String exppropertyId;
     private Long proVersion;
     private String createUser;
     private Date createDate;
     private String operUser;
     private Date operDate;
     private String channel;


    // Constructors

    public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	/** default constructor */
    public EsDocTypeExpPropertyHis() {
    }

	/** minimal constructor */
    public EsDocTypeExpPropertyHis(String id, String bussType, String subType, String columnName, String exppropertyId, String createUser, Date createDate, String operUser, Date operDate) {
        this.id = id;
        this.bussType = bussType;
        this.subType = subType;
        this.columnName = columnName;
        this.exppropertyId = exppropertyId;
        this.createUser = createUser;
        this.createDate = createDate;
        this.operUser = operUser;
        this.operDate = operDate;
    }
    
    /** full constructor */
    public EsDocTypeExpPropertyHis(String id, String bussType, String subType, 
    		String columnName, String exppropertyId, Long proVersion,
    		String createUser, Date createDate, String operUser, Date operDate,
    		String channel) {
        this.id = id;
        this.bussType = bussType;
        this.subType = subType;
        this.columnName = columnName;
        this.exppropertyId = exppropertyId;
        this.proVersion = proVersion;
        this.createUser = createUser;
        this.createDate = createDate;
        this.operUser = operUser;
        this.operDate = operDate;
        this.channel = channel;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getBussType() {
        return this.bussType;
    }
    
    public void setBussType(String bussType) {
        this.bussType = bussType;
    }

    public String getSubType() {
        return this.subType;
    }
    
    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getColumnName() {
        return this.columnName;
    }
    
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getExppropertyId() {
        return this.exppropertyId;
    }
    
    public void setExppropertyId(String exppropertyId) {
        this.exppropertyId = exppropertyId;
    }

    public Long getProVersion() {
        return this.proVersion;
    }
    
    public void setProVersion(Long proVersion) {
        this.proVersion = proVersion;
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

    public String getOperUser() {
        return this.operUser;
    }
    
    public void setOperUser(String operUser) {
        this.operUser = operUser;
    }

    public Date getOperDate() {
        return this.operDate;
    }
    
    public void setOperDate(Date operDate) {
        this.operDate = operDate;
    }
   








}