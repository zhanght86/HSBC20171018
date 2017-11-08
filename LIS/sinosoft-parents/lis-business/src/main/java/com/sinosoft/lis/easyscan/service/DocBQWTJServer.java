package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.ES_DOC_RELATIONSchema;

public class DocBQWTJServer extends BaseService {
private static Logger logger = Logger.getLogger(DocBQWTJServer.class);

	protected boolean deal() {
		logger.debug("====DocBQBankService start====");
		MMap mMap = new MMap();

		// 扫描回销
		String tPrtSeq = mES_DOC_MAINSchema.getDocCode();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(tPrtSeq);
		if (!tLOPRTManagerDB.getInfo()) {
			throw new IllegalArgumentException("查不到通知书！");
		}

		if (!"1".equals(tLOPRTManagerDB.getStateFlag())) {
			throw new IllegalArgumentException("通知书状态错误:"
					+ tLOPRTManagerDB.getStateFlag());
		}

		if (!checkWTJNO(tLOPRTManagerDB.getCode())) {
			throw new IllegalArgumentException("通知书类型错误:"
					+ tLOPRTManagerDB.getCode());
		}

		if (tLOPRTManagerDB.getStandbyFlag1() == null
				|| tLOPRTManagerDB.getStandbyFlag1().equals("")) {
			throw new IllegalArgumentException("缺少保全受理号");
		}

		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(tLOPRTManagerDB.getStandbyFlag1());
		if (tLPEdorAppDB.getCount() == 0)
			throw new IllegalArgumentException("保全受理号查询错误："
					+ tLOPRTManagerDB.getStandbyFlag1());

		if (tLOPRTManagerDB.getCode().startsWith("BQ37")) {
			tLOPRTManagerDB.setStateFlag("2");
			mMap.put(tLOPRTManagerDB.getSchema(), "UPDATE");
		}

		ES_DOC_RELATIONSchema mES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
		mES_DOC_RELATIONSchema.setBussNo(tPrtSeq);
		mES_DOC_RELATIONSchema.setDocCode(tPrtSeq);
		mES_DOC_RELATIONSchema.setBussNoType("35");
		mES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSchema.getDocID());
		mES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSchema.getBussType());
		mES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSchema.getSubType());
		mES_DOC_RELATIONSchema.setRelaFlag("0");
		mMap.put(mES_DOC_RELATIONSchema.getSchema(), "DELETE&INSERT");

		mES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
		mES_DOC_RELATIONSchema.setBussNo(tLOPRTManagerDB.getStandbyFlag1());
		mES_DOC_RELATIONSchema.setDocCode(tLOPRTManagerDB.getStandbyFlag1());
		mES_DOC_RELATIONSchema.setBussNoType("31");
		mES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSchema.getDocID());
		mES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSchema.getBussType());
		mES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSchema.getSubType());
		mES_DOC_RELATIONSchema.setRelaFlag("0");
		mMap.put(mES_DOC_RELATIONSchema.getSchema(), "DELETE&INSERT");
		mResult.add(mMap);

		return true;
	}

	private boolean checkWTJNO(String code) {
		String[] validnos = new String[] { "BQ33", "BQ37" };
		for (int i = 0; i < validnos.length; i++) {
			if (validnos[i].equals(code))
				return true;
		}
		return false;
	}
}
