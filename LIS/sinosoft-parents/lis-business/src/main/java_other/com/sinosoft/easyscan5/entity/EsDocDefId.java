package com.sinosoft.easyscan5.entity;



/**
 * EsDocDefId entity. @author MyEclipse Persistence Tools
 */

public class EsDocDefId  implements java.io.Serializable {


    // Fields    

     private String bussType;
     private String subType;
     private String channel;

    // Constructors

    /** default constructor */
    public EsDocDefId() {
    }

    
    /** full constructor */
    public EsDocDefId(String bussType, String subType,String channel) {
        this.bussType = bussType;
        this.subType = subType;
        this.channel = channel;
    }

   
    // Property accessors

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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof EsDocDefId) ) return false;
		 EsDocDefId castOther = ( EsDocDefId ) other; 
         
		 return ( (this.getBussType()==castOther.getBussType()) || ( this.getBussType()!=null && castOther.getBussType()!=null && this.getBussType().equals(castOther.getBussType()) ) )
 && ( (this.getSubType()==castOther.getSubType()) || ( this.getSubType()!=null && castOther.getSubType()!=null && this.getSubType().equals(castOther.getSubType()) ) )
 && ( (this.getChannel()==castOther.getChannel()) || ( this.getChannel()!=null && castOther.getChannel()!=null && this.getChannel().equals(castOther.getChannel()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getBussType() == null ? 0 : this.getBussType().hashCode() );
         result = 37 * result + ( getSubType() == null ? 0 : this.getSubType().hashCode() );
         result = 37 * result + ( getChannel() == null ? 0 : this.getChannel().hashCode() );
         return result;
   }


public String getChannel() {
	return channel;
}


public void setChannel(String channel) {
	this.channel = channel;
}   





}