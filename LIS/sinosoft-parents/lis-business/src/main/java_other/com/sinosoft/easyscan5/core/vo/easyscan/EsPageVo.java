package com.sinosoft.easyscan5.core.vo.easyscan;

import java.util.List;

import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;



public class EsPageVo {
	private String docId;
	private String pageId;
	private String pageCode;
	private String pageName;
	private String pageSuffix;
	private String pageFlag;
	private String scanNo;
	private String pageUrl;
	private String fileKey;
	private String scanCom;
	private String scanUser;
	private String scanDate;
	private String createDate;
	private String updateDate;
	private String format;
	private String markLayer;
	private String content;
	private String remark;
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getPageCode() {
		return pageCode;
	}
	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}
	public String getPageSuffix() {
		return pageSuffix;
	}
	public void setPageSuffix(String pageSuffix) {
		this.pageSuffix = pageSuffix;
	}
	public String getPageFlag() {
		return pageFlag;
	}
	public void setPageFlag(String pageFlag) {
		this.pageFlag = pageFlag;
	}
	public String getScanNo() {
		return scanNo;
	}
	public void setScanNo(String scanNo) {
		this.scanNo = scanNo;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getFileKey() {
		return fileKey;
	}
	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}
	public String getScanCom() {
		return scanCom;
	}
	public void setScanCom(String scanCom) {
		this.scanCom = scanCom;
	}
	public String getScanUser() {
		return scanUser;
	}
	public void setScanUser(String scanUser) {
		this.scanUser = scanUser;
	}
	public String getScanDate() {
		return scanDate;
	}
	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getMarkLayer() {
		return markLayer;
	}
	public void setMarkLayer(String markLayer) {
		this.markLayer = markLayer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static void qcDocAndPageToPageVoList(ES_DOC_MAINSchema esQcMain,
			ES_DOC_PAGESSet nES_DOC_PAGESSet,String[] strPage_URL,
			List<EsPageVo> esPageVoList) throws Exception{
		for(int i = 1;i <= nES_DOC_PAGESSet.size();i++){
			ES_DOC_PAGESSchema esQcPages = nES_DOC_PAGESSet.get(i);
			EsPageVo esPageVo = new EsPageVo();
			esPageVo.setDocId(String.valueOf(esQcPages.getDocID()));
			esPageVo.setPageId(String.valueOf(esQcPages.getPageID()));
			esPageVo.setPageCode((int)esQcPages.getPageCode() + "");
			esPageVo.setPageName(esQcPages.getPageName());
			esPageVo.setPageSuffix(esQcPages.getPageSuffix());
			esPageVo.setScanNo(esQcMain.getScanNo());
			esPageVo.setPageUrl(strPage_URL[i-1]);
			esPageVo.setFileKey(esQcPages.getFNDocKey());
			esPageVo.setScanCom(esQcMain.getManageCom());
			esPageVo.setScanUser(esQcPages.getOperator());
			String scanDate = esQcPages.getMakeDate();
			String scanTime = esQcPages.getMakeTime();
			if(scanTime.length() > 8){
				esPageVo.setScanDate(scanDate + " " + scanTime.substring(0,8));
			}else{
				esPageVo.setScanDate(scanDate + " " + scanTime);
			}
			String makeDate = esQcPages.getMakeDate();
			String makeTime = esQcPages.getMakeTime();
			if(makeTime.length() > 8){
				esPageVo.setCreateDate(makeDate + " " + makeTime.substring(0,8));
			}else{
				esPageVo.setScanDate(makeDate + " " + makeTime);
			}
			String modifyDate = esQcPages.getModifyDate();
			String modifyTime = esQcPages.getModifyTime();
			if(modifyTime.length() > 8){
				esPageVo.setUpdateDate(modifyDate + " " + modifyTime.substring(0,8));
			}else{
				esPageVo.setUpdateDate(modifyDate + " " + modifyTime);
			}
//			setMarkLayer(esMarkLayerList, esPageVo, esQcPages.getPageID());
			esPageVo.setContent("");
			esPageVoList.add(esPageVo);
		}
		
	}
	public static void mainDocAndPageToPageVoList(ES_DOC_MAINSchema esDocMain,
			ES_DOC_PAGESSet nES_DOC_PAGESSet,String[] strPage_URL,List esMarkLayerList,
			List<EsPageVo> esPageVoList) throws Exception{
		for(int i = 1;i <= nES_DOC_PAGESSet.size();i++){
			ES_DOC_PAGESSchema esDocPages = nES_DOC_PAGESSet.get(i);
			EsPageVo esPageVo = new EsPageVo();
			esPageVo.setDocId((int)esDocPages.getDocID() + "");
			esPageVo.setPageId((int)esDocPages.getPageID() + "");
			esPageVo.setPageCode((int)esDocPages.getPageCode() + "");
			esPageVo.setPageName(esDocPages.getPageName());
			esPageVo.setPageSuffix(esDocPages.getPageSuffix());
			esPageVo.setScanNo(esDocMain.getScanNo());
			esPageVo.setPageUrl(strPage_URL[i-1]);
			esPageVo.setFileKey(esDocPages.getFNDocKey());
			esPageVo.setScanCom(esDocMain.getManageCom());
			esPageVo.setScanUser(esDocMain.getScanOperator());
			String scanDate = esDocPages.getMakeDate();
			String scanTime = esDocPages.getMakeTime();
			if(scanTime.length() > 8){
				esPageVo.setScanDate(scanDate + " " + scanTime.substring(0,8));
			}else{
				esPageVo.setScanDate(scanDate + " " + scanTime);
			}
			String makeDate = esDocMain.getMakeDate();
			String makeTime = esDocMain.getMakeTime();
			if(makeTime.length() > 8){
				esPageVo.setCreateDate(makeDate + " " + makeTime.substring(0,8));
			}else{
				esPageVo.setScanDate(makeDate + " " + makeTime);
			}
			String modifyDate = esDocMain.getModifyDate();
			String modifyTime = esDocMain.getModifyTime();
			if(modifyTime.length() > 8){
				esPageVo.setUpdateDate(modifyDate + " " + modifyTime.substring(0,8));
			}else{
				esPageVo.setUpdateDate(modifyDate + " " + modifyTime);
			}
			setMarkLayer(esMarkLayerList, esPageVo, esDocPages.getPageID() + "");
			esPageVo.setContent("");
			esPageVoList.add(esPageVo);
		}
	}
	private static void setMarkLayer(List esMarkLayerList,EsPageVo esPageVo,String pageId) throws Exception{
//		if(esMarkLayerList != null && esMarkLayerList.size() > 0){
//			for(int j = 0;j < esMarkLayerList.size();j++){
//				EsMarklayer esMarklayer = (EsMarklayer) esMarkLayerList.get(j);
//				String tpageId = esMarklayer.getPageId();
//				if(tpageId.equals(pageId)){
//					esPageVo.setFormat(esMarklayer.getFormat());
//					Blob blob = esMarklayer.getMarklayer(); 
//					byte[] inbyte=null;   
//					if(blob != null) {   
//					  inbyte = blob.getBytes(1, (int) blob.length());   
//					  String str =new String (inbyte);   
//					  esPageVo.setMarkLayer(str);
//					}   
//				}
//			}
//		}
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public static void main(String[] args) {
		System.out.println("23:33:33.322".substring(0,8));
	}
}
