package com.sinosoft.easyscan5.entity;


import com.sinosoft.easyscan5.util.FDate;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;

public class EntityConvertUtil {
	/**
	 * EsQcMainToEsDocMain
	 * @param esQcMain
	 * @param nDoc_MAINSchema
	 */
	public void esQcMainToEsDocMain(EsQcMain esQcMain,ES_DOC_MAINSchema nDoc_MAINSchema){
		nDoc_MAINSchema.setBussType(esQcMain.getBussType());
		nDoc_MAINSchema.setDocCode(esQcMain.getDocCode());
		nDoc_MAINSchema.setDocID(esQcMain.getDocId());
		nDoc_MAINSchema.setManageCom(esQcMain.getManageCom());
		nDoc_MAINSchema.setNumPages(esQcMain.getNumPages().intValue());
		nDoc_MAINSchema.setOperator(esQcMain.getScanOperator());
		nDoc_MAINSchema.setPrintCode(esQcMain.getPrintCode());
		nDoc_MAINSchema.setScanNo(esQcMain.getScanNo());
		nDoc_MAINSchema.setSubType(esQcMain.getSubType());
		nDoc_MAINSchema.setVersion(esQcMain.getDocVersion());
		nDoc_MAINSchema.setScanOperator(esQcMain.getScanOperator());
		try {
			if(esQcMain.getCreateDate() != null){
				String createDate = FDate.formatDateTime(esQcMain.getCreateDate());
				nDoc_MAINSchema.setModifyDate(createDate.substring(0,10));
				nDoc_MAINSchema.setModifyTime(createDate.substring(11));
			}else{
				nDoc_MAINSchema.setModifyDate(FDate.getCurrentDate());
				nDoc_MAINSchema.setModifyTime(FDate.getCurrentTime());
			}
		} catch (Exception e) {
			nDoc_MAINSchema.setModifyDate(FDate.getCurrentDate());
			nDoc_MAINSchema.setModifyTime(FDate.getCurrentTime());
		}
	}
}
