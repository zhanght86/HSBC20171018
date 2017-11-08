package com.sinosoft.easyscan5.entity;
// default package

import java.util.Date;


/**
 * EsDocPages entity. @author MyEclipse Persistence Tools
 */

public class EsDocPages  implements java.io.Serializable {


    // Fields    

     private Long pageid;
     private Long docid;
     private Long pagecode;
     private String hostname;
     private String pagename;
     private String pagesuffix;
     private String picpathftp;
     private String pageflag;
     private String picpath;
     private String pagetype;
     private String managecom;
     private String operator;
     private Date makedate;
     private String maketime;
     private Date modifydate;
     private String modifytime;
     private String scanno;
     private String fndockey;
     private Date scandate;
     private String scantime;
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
    public EsDocPages() {
    }

	/** minimal constructor */
    public EsDocPages(Long pageid, Long docid, Long pagecode, String hostname, String pagename, String scanno) {
        this.pageid = pageid;
        this.docid = docid;
        this.pagecode = pagecode;
        this.hostname = hostname;
        this.pagename = pagename;
        this.scanno = scanno;
    }
    
    /** full constructor */
    public EsDocPages(Long pageid, Long docid, Long pagecode, String hostname, String pagename, String pagesuffix, String picpathftp, String pageflag, String picpath, String pagetype, String managecom, String operator, Date makedate, String maketime, Date modifydate, String modifytime, String scanno, String fndockey, Date scandate, String scantime, String p1, String p2, String p3, String p4, String p5, Date p6, Date p7, Long p8, Long p9) {
        this.pageid = pageid;
        this.docid = docid;
        this.pagecode = pagecode;
        this.hostname = hostname;
        this.pagename = pagename;
        this.pagesuffix = pagesuffix;
        this.picpathftp = picpathftp;
        this.pageflag = pageflag;
        this.picpath = picpath;
        this.pagetype = pagetype;
        this.managecom = managecom;
        this.operator = operator;
        this.makedate = makedate;
        this.maketime = maketime;
        this.modifydate = modifydate;
        this.modifytime = modifytime;
        this.scanno = scanno;
        this.fndockey = fndockey;
        this.scandate = scandate;
        this.scantime = scantime;
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

    public Long getPageid() {
        return this.pageid;
    }
    
    public void setPageid(Long pageid) {
        this.pageid = pageid;
    }

    public Long getDocid() {
        return this.docid;
    }
    
    public void setDocid(Long docid) {
        this.docid = docid;
    }

    public Long getPagecode() {
        return this.pagecode;
    }
    
    public void setPagecode(Long pagecode) {
        this.pagecode = pagecode;
    }

    public String getHostname() {
        return this.hostname;
    }
    
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPagename() {
        return this.pagename;
    }
    
    public void setPagename(String pagename) {
        this.pagename = pagename;
    }

    public String getPagesuffix() {
        return this.pagesuffix;
    }
    
    public void setPagesuffix(String pagesuffix) {
        this.pagesuffix = pagesuffix;
    }

    public String getPicpathftp() {
        return this.picpathftp;
    }
    
    public void setPicpathftp(String picpathftp) {
        this.picpathftp = picpathftp;
    }

    public String getPageflag() {
        return this.pageflag;
    }
    
    public void setPageflag(String pageflag) {
        this.pageflag = pageflag;
    }

    public String getPicpath() {
        return this.picpath;
    }
    
    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    public String getPagetype() {
        return this.pagetype;
    }
    
    public void setPagetype(String pagetype) {
        this.pagetype = pagetype;
    }

    public String getManagecom() {
        return this.managecom;
    }
    
    public void setManagecom(String managecom) {
        this.managecom = managecom;
    }

    public String getOperator() {
        return this.operator;
    }
    
    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getMakedate() {
        return this.makedate;
    }
    
    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public String getMaketime() {
        return this.maketime;
    }
    
    public void setMaketime(String maketime) {
        this.maketime = maketime;
    }

    public Date getModifydate() {
        return this.modifydate;
    }
    
    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    public String getModifytime() {
        return this.modifytime;
    }
    
    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }

    public String getScanno() {
        return this.scanno;
    }
    
    public void setScanno(String scanno) {
        this.scanno = scanno;
    }

    public String getFndockey() {
        return this.fndockey;
    }
    
    public void setFndockey(String fndockey) {
        this.fndockey = fndockey;
    }

    public Date getScandate() {
        return this.scandate;
    }
    
    public void setScandate(Date scandate) {
        this.scandate = scandate;
    }

    public String getScantime() {
        return this.scantime;
    }
    
    public void setScantime(String scantime) {
        this.scantime = scantime;
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