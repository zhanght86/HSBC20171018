package com.sinosoft.lis.actuary.finder;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;

public abstract class AbstractrModifiedContFinder {
private static Logger logger = Logger.getLogger(AbstractrModifiedContFinder.class);

	public AbstractrModifiedContFinder() {
		super();
	}

	protected abstract SQLwithBindVariables getFindModifiedContsSql(String startdate, String enddate);

	public String[] findModifiedConts(Date start, Date end) {
		String startdate = new FDate().getString(start);
		String enddate = new FDate().getString(end);
		SQLwithBindVariables sql = getFindModifiedContsSql(startdate, enddate);
		String s = this.getClass().getName();
		s = s.substring(s.lastIndexOf(".") + 1);
		logger.debug(s + " begin at " + PubFun.getCurrentTime());
		SSRS tSSRS = new ExeSQL().execSQL(sql);
		String[] contnos = new String[tSSRS.MaxRow];
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			String contno = tSSRS.GetText(i, 1);
			contnos[i - 1] = contno;
		}
		logger.debug(s + " end at " + PubFun.getCurrentTime());
		return contnos;
	}

}
