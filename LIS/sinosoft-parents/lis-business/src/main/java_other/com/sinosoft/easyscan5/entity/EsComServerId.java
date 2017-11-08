package com.sinosoft.easyscan5.entity;
// default package



/**
 * EsComServerId entity. @author MyEclipse Persistence Tools
 */

public class EsComServerId  implements java.io.Serializable {


    // Fields    

     private String companyNo;
     private String cacheServerNo;


    // Constructors

    /** default constructor */
    public EsComServerId() {
    }

    
    /** full constructor */
    public EsComServerId(String companyNo, String cacheServerNo) {
        this.companyNo = companyNo;
        this.cacheServerNo = cacheServerNo;
    }

   
    // Property accessors

    public String getCompanyNo() {
        return this.companyNo;
    }
    
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public String getCacheServerNo() {
        return this.cacheServerNo;
    }
    
    public void setCacheServerNo(String cacheServerNo) {
        this.cacheServerNo = cacheServerNo;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof EsComServerId) ) return false;
		 EsComServerId castOther = ( EsComServerId ) other; 
         
		 return ( (this.getCompanyNo()==castOther.getCompanyNo()) || ( this.getCompanyNo()!=null && castOther.getCompanyNo()!=null && this.getCompanyNo().equals(castOther.getCompanyNo()) ) )
 && ( (this.getCacheServerNo()==castOther.getCacheServerNo()) || ( this.getCacheServerNo()!=null && castOther.getCacheServerNo()!=null && this.getCacheServerNo().equals(castOther.getCacheServerNo()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCompanyNo() == null ? 0 : this.getCompanyNo().hashCode() );
         result = 37 * result + ( getCacheServerNo() == null ? 0 : this.getCacheServerNo().hashCode() );
         return result;
   }   





}