package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BOMStruct {
private static Logger logger = Logger.getLogger(BOMStruct.class);
	
	protected BOMStruct fatherBOMStruct;
	protected String bomName;
	protected Object objBom; 
	protected List listChildren = new ArrayList() ; //一般情况下只有一个类对象存在，如果多于一个，则表明有同层次的关系存在，如何处理？？
	
	public BOMStruct(String bomName,Object objBom){
		this.bomName = bomName;
		this.objBom  = objBom;
	}

	public String getBomName() {
		return bomName;
	}

	public void setBomName(String bomName) {
		this.bomName = bomName;
	}

	public BOMStruct getFatherBOMStruct() {
		return fatherBOMStruct;
	}

	public void setFatherBOMStruct(BOMStruct fatherBOMStruct) {
		this.fatherBOMStruct = fatherBOMStruct;
	}

	public Object getObjBom() {
		return objBom;
	}

	public void setObjBom(Object objBom) {
		this.objBom = objBom;
	}

	public List getListChildren() {
		return listChildren;
	}
	
	public void addChild(BOMStruct bomStruct){
		listChildren.add(bomStruct);
	}
	
	
}
