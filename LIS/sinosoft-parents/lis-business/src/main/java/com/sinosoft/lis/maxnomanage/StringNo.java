package com.sinosoft.lis.maxnomanage;

import java.util.Hashtable;

import org.apache.log4j.Logger;

public class StringNo extends MaxNoElement {
	public StringNo(String tType, Hashtable tProps, Hashtable tOtherValue) {
		super(tType, tProps, tOtherValue);
	}

	private static Logger logger = Logger.getLogger(StringNo.class);

	private CreateMaxNoImp mCreateMaxNoImp;

	public String CreateMaxNo() {
		// 字符串只显示Content描述的内容
		Hashtable props = this.getProps();
		String cNoType = this.getType();
		String tTempNo = props.get("Content") == null ? "" : (String) props
				.get("Content");
		return tTempNo;
	}

	@Override
	public String CreatePrviewMaxNo() {
		// TODO Auto-generated method stub
		return CreateMaxNo();
	}
}
