package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import com.sinosoft.lis.actuary.finder.IModifiedContFinder;
import com.sinosoft.lis.db.LCContModifyDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vdb.LCContModifyDBSet;
import com.sinosoft.lis.vschema.LDCodeSet;

public class LCContModifyTask extends TaskThread {
private static Logger logger = Logger.getLogger(LCContModifyTask.class);

	public boolean dealMain() {
		Date start = getStartDate();
		Date end = getEndDate();

		ArrayList finders = getFinders();

		// use a HashSet to cache all modified contno only once
		HashSet contnos = new HashSet();
		for (Iterator iterator = finders.iterator(); iterator.hasNext();) {
			IModifiedContFinder finder = (IModifiedContFinder) iterator.next();
			String[] cs = finder.findModifiedConts(start, end);
			for (int j = 0; j < cs.length; j++) {
				String c = cs[j];
				contnos.add(c);
			}
		}

		int n = contnos.size();
		logger.debug("Remain contno num " + n + " at "
				+ PubFun.getCurrentTime());
		LCContModifyDBSet tLCContModifyDBSetNew=new LCContModifyDBSet();
		LCContModifyDBSet tLCContModifyDBSetOld=new LCContModifyDBSet();
		for (Iterator iterator = contnos.iterator(); iterator.hasNext();) {
			n--;
			String contno = (String) iterator.next();
			LCContModifyDB tLCContModifyDB = new LCContModifyDB();
			tLCContModifyDB.setContNo(contno);
			// check if there's no record at all, insert one, otherwise just update it's modifytime.
			if (!tLCContModifyDB.getInfo()) {
				tLCContModifyDB.setMakeDate(PubFun.getCurrentDate());
				tLCContModifyDB.setMakeTime(PubFun.getCurrentTime());
				tLCContModifyDB.setModifyDate(PubFun.getCurrentDate());
				tLCContModifyDB.setModifyTime(PubFun.getCurrentTime());
				tLCContModifyDBSetNew.add(tLCContModifyDB.getSchema());
			} else {
				tLCContModifyDB.setModifyDate(PubFun.getCurrentDate());
				tLCContModifyDB.setModifyTime(PubFun.getCurrentTime());
				tLCContModifyDBSetOld.add(tLCContModifyDB.getSchema());
			}
			if (n % 10000 == 0) {
				if (!tLCContModifyDBSetNew.insert()) {
					this.mErrors.copyAllErrors(tLCContModifyDBSetNew.mErrors);
					logger.debug(tLCContModifyDBSetNew.mErrors
							.getErrContent());
					return false;
				}
				if (!tLCContModifyDBSetOld.update()) {
					this.mErrors.copyAllErrors(tLCContModifyDBSetOld.mErrors);
					logger.debug(tLCContModifyDBSetOld.mErrors
							.getErrContent());
					return false;
				}
				tLCContModifyDBSetNew.clear();
				tLCContModifyDBSetOld.clear();
				logger.debug("Remain contno num " + n + " at "
						+ PubFun.getCurrentTime());
			}
		}
		updateStartDate(end);
		return true;
	}

	private void updateStartDate(Date end) {
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("lastmodifycontdate");
		tLDSysVarDB.setSysVarValue(new FDate().getString(end));
		tLDSysVarDB.setRemark(PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
		tLDSysVarDB.delete();
		tLDSysVarDB.insert();
	}

	private Date getEndDate() {
		return new FDate().getDate(PubFun.getCurrentDate());
	}

	private Date getStartDate() {
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("lastmodifycontdate");
		Date startDate=null;
		if (tLDSysVarDB.getInfo()) {
			String d=tLDSysVarDB.getSysVarValue();
			startDate=new FDate().getDate(d);
		}
		if(startDate==null){
			startDate=new FDate().getDate(PubFun.calDate(PubFun.getCurrentDate(),-31,"D",PubFun.getCurrentDate()));
			logger.debug("lastmodifycontdate query error");
		}
		return startDate;
	}

	/**
	 * get lccont modify finders class description from ldcode
	 */
	private ArrayList getFinders() {
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCodeType("modifycont");
		LDCodeSet tLDCodeSet = tLDCodeDB.query();
		ArrayList finders = new ArrayList();
		for (int i = 1; i <= tLDCodeSet.size(); i++) {
			LDCodeSchema tLDCodeSchema = tLDCodeSet.get(i);
			String className = tLDCodeSchema.getCodeName();
			try {
				Class clazz = Class.forName(className);
				Object o = clazz.newInstance();
				if (o == null || !(o instanceof IModifiedContFinder))
					throw new RuntimeException(
							"can't construct IModifiedContFinder");
				IModifiedContFinder finder = (IModifiedContFinder) o;
				finders.add(finder);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return finders;
	}

}
