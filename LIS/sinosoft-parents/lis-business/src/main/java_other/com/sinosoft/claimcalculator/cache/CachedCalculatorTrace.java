/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.claimcalculator.cache;
import org.apache.log4j.Logger;

import java.util.Hashtable;

import com.sinosoft.lis.db.LCalculatorTraceDB;
import com.sinosoft.lis.schema.LCalculatorTraceSchema;
import com.sinosoft.lis.vschema.LCalculatorTraceSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;

public class CachedCalculatorTrace {
private static Logger logger = Logger.getLogger(CachedCalculatorTrace.class);
	// @Field
	public CErrors mErrors = new CErrors(); 

	
	private Hashtable m_hashLCalculatorTrace = new Hashtable();

	private static String NOFOUND = "NOFOUND";
	private static CachedCalculatorTrace m_cct = null;

	// @Constructor
	private CachedCalculatorTrace() {
	}

	public static CachedCalculatorTrace getInstance() {
		if (m_cct == null) {
			m_cct = new CachedCalculatorTrace();
		}

		m_cct.mErrors.clearErrors();

		return m_cct;
	}

	public void refresh() {
		m_hashLCalculatorTrace.clear();
	}


	public void remove(String tClmNo) {
		m_hashLCalculatorTrace.remove(tClmNo);
	}


	public boolean addTrace2Cache(LCalculatorTraceSchema tLCalculatorTraceSchema){

		Hashtable hash = m_hashLCalculatorTrace;
		String tClmNo = tLCalculatorTraceSchema.getClmNo();

		Object obj = hash.get(tClmNo);

		LCalculatorTraceSet tLCalculatorTraceSet = new LCalculatorTraceSet();
		if (obj != null) {
			if (obj instanceof String) {
				return false;
			} else {
				tLCalculatorTraceSet = (LCalculatorTraceSet) obj;
				hash.remove(tClmNo);
			}
		}
		tLCalculatorTraceSet.add(tLCalculatorTraceSchema);
		hash.put(tClmNo, tLCalculatorTraceSet);
		
		return true;
	}

	public LCalculatorTraceSet findTraceByClmNo(String strClmNo) {

		Hashtable hash = m_hashLCalculatorTrace;

		Object obj = hash.get(strClmNo);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (LCalculatorTraceSet) obj;
			}
		} else {
			return null;
		}
	}

	public LCalculatorTraceSet findTraceByClmNoClone(String strClmNo) {
		LCalculatorTraceSet tLCalculatorTraceSet = findTraceByClmNo(strClmNo);

		if (tLCalculatorTraceSet == null) {
			return tLCalculatorTraceSet;
		} else {
			LCalculatorTraceSet newLCalculatorTraceSet = new LCalculatorTraceSet();
			for (int nIndex = 0; nIndex < tLCalculatorTraceSet.size(); nIndex++) {
				newLCalculatorTraceSet.add(tLCalculatorTraceSet.get(nIndex + 1));
			}
			return newLCalculatorTraceSet;
		}
	}

	public LCalculatorTraceSet findTrace(String strClmNo , String strCalculatorCode) {
		LCalculatorTraceSet tLCalculatorTraceSet = findTraceByClmNo(strClmNo);

		if (tLCalculatorTraceSet == null) {
			return null;
		}

		LCalculatorTraceSet retLCalculatorTraceSet = new LCalculatorTraceSet();

		for (int nIndex = 0; nIndex < tLCalculatorTraceSet.size(); nIndex++) {
			LCalculatorTraceSchema tLCalculatorTraceSchema = tLCalculatorTraceSet.get(nIndex + 1);
			if (StrTool.cTrim(tLCalculatorTraceSchema.getCalculatorCode()).equals(
					strCalculatorCode)) {
				retLCalculatorTraceSet.add(tLCalculatorTraceSchema);
			}
		}

		return retLCalculatorTraceSet;
	}


}
