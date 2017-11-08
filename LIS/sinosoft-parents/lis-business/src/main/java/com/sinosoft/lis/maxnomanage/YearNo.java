package com.sinosoft.lis.maxnomanage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.apache.log4j.Logger;

public class YearNo extends MaxNoElement {
	public YearNo(String tType, Hashtable tProps, Hashtable tOtherValue) {
		super(tType, tProps, tOtherValue);
	}

	private static Logger logger = Logger.getLogger(YearNo.class);

	private CreateMaxNoImp mCreateMaxNoImp;

	public String CreateMaxNo() {
		Hashtable props = this.getProps();
		String cNoType = this.getType();
		Hashtable others = this.getOthers();
		String tTempNo = "";
		String tPattern = props.get("Format") == null ? "yyyyMM"
				: (String) props.get("Format");
		SimpleDateFormat df = new SimpleDateFormat(tPattern);
		Date today = new Date();
		tTempNo = df.format(today);

		return tTempNo;
	}

	@Override
	public String CreatePrviewMaxNo() {
		// TODO Auto-generated method stub
		return CreateMaxNo();
	}
}
