package com.sinosoft.lis.maxnomanage;

import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubFun;

public class ComNo extends MaxNoElement {
	public ComNo(String tType, Hashtable tProps, Hashtable tOtherValue) {
		super(tType, tProps, tOtherValue);
	}

	private static Logger logger = Logger.getLogger(ComNo.class);

	private CreateMaxNoImp mCreateMaxNoImp;

	public String CreateMaxNo() {
		Hashtable props = this.getProps();
		String cNoType = this.getType();
		Hashtable others = this.getOthers();
		String tCover = props.get("Cover") == null ? "0" : (String) props
				.get("Cover");// 不足位补充内容
		String tSubStr = props.get("SubStr") == null ? "0-6" : (String) props
				.get("SubStr");// 显示位置,默认为0-6
		String tTempNo = "";
		String[] tSubstrs = tSubStr.split("-");
		String tComNo = others.get("ManageCom") == null ? "0000"
				: (String) others.get("ManageCom");
		if(tSubstrs.length!=2)
		{
			return tComNo;
		}
		
		if (tComNo.length() > Integer.parseInt(tSubstrs[1])) {
			tTempNo = tComNo.substring( Integer.parseInt(tSubstrs[0]),  Integer.parseInt(tSubstrs[1]));
		} else {
			tTempNo = PubFun.LCh(tComNo, tCover,  Integer.parseInt(tSubstrs[1]));
		}

		return tTempNo;
	}

	@Override
	public String CreatePrviewMaxNo() {
		// TODO Auto-generated method stub
		return CreateMaxNo();
	}
}
