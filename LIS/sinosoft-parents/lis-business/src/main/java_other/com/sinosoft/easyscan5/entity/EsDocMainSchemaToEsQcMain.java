package com.sinosoft.easyscan5.entity;

import java.text.ParseException;
import java.util.Date;

import com.sinosoft.easyscan5.util.FDate;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;

public class EsDocMainSchemaToEsQcMain {
	public static void schemaToEsQcMain(ES_DOC_MAINSchema nSchema,EsQcMain esQcMain){
		esQcMain.setDocCode(nSchema.getDocCode());
		esQcMain.setBussType(nSchema.getBussType());
		esQcMain.setSubType(nSchema.getSubType());
		esQcMain.setNumPages(new Long((int)nSchema.getNumPages()));
		esQcMain.setDocId((int)nSchema.getDocID() + "");
		esQcMain.setDocFlag(nSchema.getDocFlag());
		esQcMain.setGroupNo(nSchema.getDocCode());
		String makeDate = nSchema.getMakeDate();
		String makeTime = nSchema.getMakeTime();
		String modifyDate = nSchema.getModifyDate();
		String modifyTime = nSchema.getModifyTime();
		try {
			Date scanDate = FDate.formatYMDHMSToDate(makeDate + " " + makeTime);
			Date modify =  FDate.formatYMDHMSToDate(modifyDate + " " + modifyTime);
			esQcMain.setCreateDate(scanDate);
			esQcMain.setScanDate(scanDate);
			esQcMain.setUpdateDate(modify);
		} catch (ParseException e) {
			esQcMain.setCreateDate(new Date());
			e.printStackTrace();
		}
		esQcMain.setManageCom(nSchema.getManageCom());
		esQcMain.setScanCom(nSchema.getManageCom());
		esQcMain.setScanOperator(nSchema.getScanOperator());
		esQcMain.setScanNo(nSchema.getScanNo());
	}
}
