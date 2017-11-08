package com.sinosoft.easyscan5.common;

import com.sinosoft.easyscan5.entity.EsQcPages;

import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.easyscan5.util.FDate;


public class EsDocPagesPToEsQcPage {
	public static void esDocPagesPToEsQcPage(){
		
	}
	public static void esQcPageToEsDocPagesP(EsQcPages esQcPages,ES_DOC_PAGESSchema eSchema,String manageCom,
			String scanNo){
		eSchema.setDocID(esQcPages.getDocId());
		eSchema.setFNDocKey(esQcPages.getFndockey());
		eSchema.setHostName(esQcPages.getServerNo());
		eSchema.setMakeDate(FDate.formatDate(esQcPages.getScanDate()));
		String scanTime = FDate.formatDateTime(esQcPages.getScanDate());
		eSchema.setMakeTime(scanTime.substring(11));
		eSchema.setModifyDate(FDate.getCurrentDate());
		eSchema.setModifyTime(FDate.getCurrentTime());
		eSchema.setManageCom(manageCom);
		eSchema.setOperator(esQcPages.getScanOperator());
		eSchema.setPageCode(esQcPages.getPageNo().intValue());
		eSchema.setPageFlag(esQcPages.getPageFlag());
		eSchema.setPageID(esQcPages.getPageId());
		eSchema.setPageName(esQcPages.getPageName());
		eSchema.setPageSuffix(esQcPages.getPageSuffix());
		eSchema.setPageType(esQcPages.getPageType());
		eSchema.setPicPath(esQcPages.getPicPath());
		eSchema.setPicPathFTP("");
		/*eSchema.setScanDate(FDate.formatDate(esQcPages.getScanDate()));*/
		
		/*eSchema.setScanTime(scanTime.substring(11));*/
		eSchema.setScanNo(scanNo);
		eSchema.setFNDocKey("0");
	}
}
