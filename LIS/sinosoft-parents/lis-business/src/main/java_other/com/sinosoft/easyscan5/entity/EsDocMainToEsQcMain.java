package com.sinosoft.easyscan5.entity;


public class EsDocMainToEsQcMain {
	public static void esDocMainToEsQcMain(EsDocMain esDocMain,EsQcMain esQcMain){
		esQcMain.setDocId(esDocMain.getDocid().toString());
		esQcMain.setDocCode(esDocMain.getDoccode());
		esQcMain.setGroupNo(esDocMain.getGroupNo());
		esQcMain.setPrintCode(esDocMain.getPrintcode());
		esQcMain.setBussType(esDocMain.getBusstype());
		esQcMain.setSubType(esDocMain.getSubtype());
		esQcMain.setScanNo(esDocMain.getScanno());
		esQcMain.setNumPages(esDocMain.getNumpages());
		esQcMain.setScanDate(esDocMain.getScanDate());
		esQcMain.setCreateDate(esDocMain.getCreateDate());
		esQcMain.setUpdateDate(esDocMain.getUpdateDate());
		esQcMain.setScanOperator(esDocMain.getScanoperator());
		esQcMain.setScanCom(esDocMain.getScanCom());
		esQcMain.setManageCom(esDocMain.getManagecom());
	}
}
