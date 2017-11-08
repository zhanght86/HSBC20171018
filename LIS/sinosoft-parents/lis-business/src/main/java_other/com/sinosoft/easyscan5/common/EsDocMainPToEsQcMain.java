package com.sinosoft.easyscan5.common;

import java.text.ParseException;
import java.util.Date;

import com.sinosoft.easyscan5.entity.EsQcMain;

import com.sinosoft.easyscan5.util.FDate;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
/**
 * Es_Doc_MainSchemaת转EsQcMain
 * @author DELL
 *
 */
public class EsDocMainPToEsQcMain {
	public static void esDocMainPToEsQcMain(Date scanDate,ES_DOC_MAINSchema eSchema,EsQcMain esQcMain){
		esQcMain.setDocId(String.valueOf((int)eSchema.getDocID()));
		esQcMain.setDocCode(eSchema.getDocCode().trim());
		esQcMain.setBussType(eSchema.getBussType());
		esQcMain.setSubType(eSchema.getSubType());
		esQcMain.setNumPages((long)eSchema.getNumPages());
		esQcMain.setScanCom(eSchema.getManageCom());
		esQcMain.setManageCom(eSchema.getManageCom());
		esQcMain.setScanNo(eSchema.getScanNo());
		esQcMain.setScanOperator(eSchema.getScanOperator());
		esQcMain.setDocFlag(eSchema.getDocFlag());
		esQcMain.setGroupNo(eSchema.getDocCode().trim());
		esQcMain.setPrintCode(eSchema.getPrintCode());
		esQcMain.setScanDate(scanDate);
//		esQcMain.setFu1(eSchema.getScanType());
		esQcMain.setDocRemark(eSchema.getDocRemark());
		String makeDate = eSchema.getMakeDate();
		String makeTime = eSchema.getMakeTime();
		String modifyDate = eSchema.getModifyDate();
		String modifyTime = eSchema.getModifyTime();
		Date createDate;
		Date updateDate;
		try {
			createDate = FDate.formatYMDHMSToDate(makeDate + " " + makeTime);
			updateDate = FDate.formatYMDHMSToDate(modifyDate + " " + modifyTime);
		} catch (ParseException e) {
			createDate = new Date();
			updateDate = new Date();
		}
		esQcMain.setCreateDate(createDate);
		esQcMain.setUpdateDate(updateDate);
	}
	public static void esQcMainToEsDocMainP(EsQcMain esQcMain,ES_DOC_MAINSchema eSchema){
		eSchema.setBussType(esQcMain.getBussType());
		eSchema.setSubType(esQcMain.getSubType());
		eSchema.setDocCode(esQcMain.getDocCode().trim());
		eSchema.setDocID(esQcMain.getDocId());
		eSchema.setDocFlag(esQcMain.getDocFlag());
		eSchema.setDocRemark(esQcMain.getDocRemark());
		eSchema.setMakeDate(FDate.getCurrentDate());
		eSchema.setMakeTime(FDate.getCurrentTime());
		eSchema.setModifyDate(FDate.getCurrentDate());
		eSchema.setModifyTime(FDate.getCurrentTime());
		eSchema.setManageCom(esQcMain.getManageCom());
		eSchema.setNumPages(esQcMain.getNumPages().intValue());
//		eSchema.setOperator(esQcMain.getScanOperator());
		eSchema.setPrintCode(esQcMain.getPrintCode());
		eSchema.setScanNo(esQcMain.getScanNo());
		eSchema.setScanOperator(esQcMain.getScanOperator());
//		eSchema.setScanType(esQcMain.getFu1());
	}
}
