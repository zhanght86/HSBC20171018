package com.sinosoft.easyscan5.core.vo;

import java.util.List;
import java.util.Map;

import com.sinosoft.easyscan5.entity.EsDocDef;
import com.sinosoft.easyscan5.entity.EsQcMain;
import com.sinosoft.easyscan5.entity.EsQcPages;


public class EsDocAndPageVO {
	/**单证信息**/
	private EsQcMain esQcMain = new EsQcMain();
	/**单证页信息**/
	private List<EsQcPages> esQcPagesList;
	/**单证页makelayer信息**/
	private List esMarklayer;
	private Map<String,EsQcPages> esQcPagesMap;
	private List esDocPagesList;
	/**访问服务器地址**/
	private String[] strPage_URL;
	/**返回参数--NUMBER**/
	private String return_Number;
	/**返回参数--MESSAGE**/
	private String return_Message;
	private String uploadFileType;
	private String scanType;
	private EsDocDef esDocDef;

	public EsDocDef getEsDocDef() {
		return esDocDef;
	}
	public void setEsDocDef(EsDocDef esDocDef) {
		this.esDocDef = esDocDef;
	}
	public void setEsQcMain(EsQcMain esQcMain) {
		this.esQcMain = esQcMain;
	}
	public EsQcMain getEsQcMain() {
		return esQcMain;
	}
	public void setEsQcPagesList(List<EsQcPages> esQcPagesList) {
		this.esQcPagesList = esQcPagesList;
	}
	public List<EsQcPages> getEsQcPagesList() {
		return esQcPagesList;
	}
	public void setPage_URL(String[] page_URL) {
		strPage_URL = page_URL;
	}
	public String[] getPage_URL() {
		return strPage_URL;
	}
	public void setReturn_Message(String return_Message) {
		this.return_Message = return_Message;
	}
	public String getReturn_Message() {
		return return_Message;
	}
	public void setReturn_Number(String return_Number) {
		this.return_Number = return_Number;
	}
	public String getReturn_Number() {
		return return_Number;
	}
	public String getUploadFileType() {
		return uploadFileType;
	}
	public void setUploadFileType(String uploadFileType) {
		this.uploadFileType = uploadFileType;
	}
	public String getScanType() {
		return scanType;
	}
	public void setScanType(String scanType) {
		this.scanType = scanType;
	}
	public List getEsDocPagesList() {
		return esDocPagesList;
	}
	public void setEsDocPagesList(List esDocPagesList) {
		this.esDocPagesList = esDocPagesList;
	}
	public Map<String, EsQcPages> getEsQcPagesMap() {
		return esQcPagesMap;
	}
	public void setEsQcPagesMap(Map<String, EsQcPages> esQcPagesMap) {
		this.esQcPagesMap = esQcPagesMap;
	}

}
