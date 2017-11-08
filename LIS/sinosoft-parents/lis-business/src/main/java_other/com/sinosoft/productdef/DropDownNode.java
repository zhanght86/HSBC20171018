

package com.sinosoft.productdef;

import java.io.Serializable;
import java.util.List;

public class DropDownNode implements Serializable{
	public String eName;
	public String cName;
	public List idValue;
	public List tValue;
	
	public String getCName() {
		return cName;
	}
	
	public void setCName(String name) {
		cName = name;
	}   
	
	public String getEName() {
		return eName;
	}
	
	public void setEName(String name) {
		eName = name;
	}
	
	public List getIdValue() {
		return idValue;
	}
	
	public void setIdValue(List idValue) {
		this.idValue = idValue;
	}
	
	public List getTValue() {
		return tValue;
	}
	
	public void setTValue(List value) {
		tValue = value;
	}
	
	public DropDownNode(String name, String name2, List idValue, List value) {
		super();
		eName = name;
		cName = name2;
		this.idValue = idValue;
		tValue = value;
	}
	
	public DropDownNode(){
		
	}
}
