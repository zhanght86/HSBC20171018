package com.sinosoft.easyscan5.entity;
// default package

import java.text.ParseException;
import java.util.Date;

import com.sinosoft.easyscan5.util.FDate;




/**
 * EsQcPages entity. @author MyEclipse Persistence Tools
 */

public class EsQcPages  implements java.io.Serializable {


    // Fields    

     private String pageId;
     private String docId;
     private Long pageNo;
     private String serverNo;
     private String pageName;
     private String pageSuffix;
     private String picPath;
     private String scanOperator;
     private Date scanDate;
     private String pageFlag;
     private String pageType;
     private Date createDate;
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
     private String fndockey;
     private String pageVersion;
    // Constructors

    public String getPageVersion() {
		return pageVersion;
	}

	public void setPageVersion(String pageVersion) {
		this.pageVersion = pageVersion;
	}

	public String getFndockey() {
		return fndockey;
	}

	public void setFndockey(String fndockey) {
		this.fndockey = fndockey;
	}

	/** default constructor */
    public EsQcPages() {
    }
    public EsQcPages(EsDocPages esDocPages) {
    	 this.pageId = esDocPages.getPageid() + "";
         this.docId = esDocPages.getDocid() + "";
         this.pageNo = esDocPages.getPagecode();
         this.serverNo = esDocPages.getHostname();
         this.pageName = esDocPages.getPagename();
         this.pageSuffix = esDocPages.getPagesuffix();
         this.picPath = esDocPages.getPicpath();
         this.scanOperator = esDocPages.getOperator();
         Date scanDate = esDocPages.getScandate();
         String scanTime = esDocPages.getScantime();
         this.pageType = esDocPages.getPagetype();
         try{
        	 if(scanDate != null && !"".equals(scanTime)){
        		 String tscanDate = FDate.formatDate(scanDate) + " " + scanTime;
        		 this.scanDate = FDate.formatYMDHMSToDate(tscanDate);
        	 }else{
        		 this.scanDate = scanDate;
        	 }
         }catch (ParseException e) {
        	 this.scanDate = scanDate;
		 }
         this.pageFlag = esDocPages.getPageflag();
         //createDate
         Date makeDate = esDocPages.getMakedate();
         String makeTime = esDocPages.getMaketime();
         try{
        	 if(makeDate != null && !"".equals(makeTime)){
        		 String tcreateDate= FDate.formatDate(makeDate) + " " + makeTime;
        		 this.createDate = FDate.formatYMDHMSToDate(tcreateDate);
        	 }else{
        		 this.createDate = makeDate;
        	 }
         }catch (ParseException e) {
        	 this.createDate = makeDate;
		 }
         //createDate
         Date modifyDate = esDocPages.getModifydate();
         String modifyTime = esDocPages.getModifytime();
         try{
        	 if(modifyDate != null && !"".equals(modifyTime)){
        		 String tupdateDate= FDate.formatDate(modifyDate) + " " + modifyTime;
        		 this.updateDate = FDate.formatYMDHMSToDate(tupdateDate);
        	 }else{
        		 this.updateDate = makeDate;
        	 }
         }catch (ParseException e) {
        	 this.updateDate = makeDate;
		 }
         this.p1 = esDocPages.getP1();
         this.p2 = esDocPages.getP2();
         this.p3 = esDocPages.getP3();
         this.p4 = esDocPages.getP4();
         this.p5 = esDocPages.getP5();
         this.p6 = esDocPages.getP6();
         this.p7 = esDocPages.getP7();
         this.p8 = esDocPages.getP8();
         this.p9 = esDocPages.getP9();
         this.fndockey = esDocPages.getFndockey();
    }
	/** minimal constructor */
    public EsQcPages(String pageId, String docId, Long pageNo, String serverNo, String pageName, String scanOperator, Date scanDate, Date createDate, Date updateDate,String fndockey) {
        this.pageId = pageId;
        this.docId = docId;
        this.pageNo = pageNo;
        this.serverNo = serverNo;
        this.pageName = pageName;
        this.scanOperator = scanOperator;
        this.scanDate = scanDate;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.fndockey = fndockey;
    }
    
    /** full constructor */
    public EsQcPages(String pageId, String docId, Long pageNo, String serverNo, String pageName, String pageSuffix, String picPath, String scanOperator, Date scanDate, String pageFlag, String pageType, Date createDate, Date updateDate, String p1, String p2, String p3, String p4, String p5, Date p6, Date p7, Long p8, Long p9,String fndockey,String pageVersion) {
        this.pageId = pageId;
        this.docId = docId;
        this.pageNo = pageNo;
        this.serverNo = serverNo;
        this.pageName = pageName;
        this.pageSuffix = pageSuffix;
        this.picPath = picPath;
        this.scanOperator = scanOperator;
        this.scanDate = scanDate;
        this.pageFlag = pageFlag;
        this.pageType = pageType;
        this.createDate = createDate;
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
        this.fndockey = fndockey;
        this.pageVersion = pageVersion;
    }

   
    // Property accessors

    public String getPageId() {
        return this.pageId;
    }
    
    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getDocId() {
        return this.docId;
    }
    
    public void setDocId(String docId) {
        this.docId = docId;
    }

    public Long getPageNo() {
        return this.pageNo;
    }
    
    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    public String getServerNo() {
        return this.serverNo;
    }
    
    public void setServerNo(String serverNo) {
        this.serverNo = serverNo;
    }

    public String getPageName() {
        return this.pageName;
    }
    
    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageSuffix() {
        return this.pageSuffix;
    }
    
    public void setPageSuffix(String pageSuffix) {
        this.pageSuffix = pageSuffix;
    }

    public String getPicPath() {
        return this.picPath;
    }
    
    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getScanOperator() {
        return this.scanOperator;
    }
    
    public void setScanOperator(String scanOperator) {
        this.scanOperator = scanOperator;
    }

    public Date getScanDate() {
        return this.scanDate;
    }
    
    public void setScanDate(Date scanDate) {
        this.scanDate = scanDate;
    }

    public String getPageFlag() {
        return this.pageFlag;
    }
    
    public void setPageFlag(String pageFlag) {
        this.pageFlag = pageFlag;
    }

    public String getPageType() {
        return this.pageType;
    }
    
    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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