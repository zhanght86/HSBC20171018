package com.sinosoft.lis.maxnomanage;

import java.util.Hashtable;

public abstract class MaxNoElement {

    private CreateMaxNo mCreateMaxNo;
    
    private String NoType;
    private Hashtable props;
    private Hashtable others;
    
    public MaxNoElement(String tType,Hashtable tProps,Hashtable tOtherValue)
    {
    	setType(tType);
    	setProps(tProps);
    	setOthers(tOtherValue);
    }
    
    public CreateMaxNo getCreateMaxNo() {
        return mCreateMaxNo;
    }

    public void setCreateMaxNo() {
        this.mCreateMaxNo = new CreateMaxNoImp();
    }
    
    public void setType(String type) {
        this.NoType = type;
    }
    
    public String getType() {
        return this.NoType;
    }
    
    public void setOthers(Hashtable others) {
        this.others = others;
    }
    
    public Hashtable getOthers() {
        return this.others;
    }
    
    public void setProps(Hashtable props) {
        this.props = props;
    }
    
    public Hashtable getProps() {
        return this.props;
    }
    
    public abstract String CreateMaxNo();
    public abstract String CreatePrviewMaxNo();
}
