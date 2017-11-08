package com.sinosoft.easyscan5.common.easyscanxml;

public class UpIndexReqXmlConstants extends ParentXmlConstants {

	/**
	 * 请求报文批次号节点
	 */
	public static final String XML_UPLOADBATCH = "uploadbatch";
	/**
	 * 请求报文批次号batchkey子节点
	 */
	public static final String XML_UPLOADBATCH_BATCHKEY = "uniqueid";
	/**
	 * 请求报文批次号channel子节点
	 */
	public static final String XML_UPLOADBATCH_CHANNEL = "channel";
	/**
	 * 请求报文组节点
	 */
	public static final String XML_GROUP = "group";
	/**
	 * 请求报文组节点caseno属性
	 */
	public static final String XML_GROUP_CASENO = "caseno";
	/**
	 * 请求报文组节点的子节点：单证节点
	 */
	public static final String XML_DOC = "doc";
	/**
	 * 请求报文组节点的子节点：单证节点doccode属性
	 */
	public static final String XML_DOC_DOCCODE = "doccode";
	/**
	 * 请求报文组节点的子节点：单证节点caseno属性
	 */
	public static final String XML_DOC_CASENO = "caseno";
	/**
	 * 请求报文组节点的子节点：单证节点printcode属性
	 */
	public static final String XML_DOC_PRINTCODE = "printcode";
	/**
	 * 请求报文组节点的子节点：单证节点busstype属性
	 */
	public static final String XML_DOC_BUSSTYPE = "busstype";
	/**
	 * 请求报文组节点的子节点：单证节点subtype属性
	 */
	public static final String XML_DOC_SUBTYPE = "subtype";
	
	/**
	 * 请求报文组节点的子节点：单证节点boxno属性
	 */
	public static final String XML_DOC_BOXNO = "boxno";
	/**
	 * 请求报文组节点的子节点：单证节点scantype属性
	 */
	public static final String XML_DOC_SCANTYPE = "scantype";
	/**
	 * 请求报文组节点的子节点：单证节点pagecount属性
	 */
	public static final String XML_DOC_PAGECOUNT = "pagecount";
	/**
	 * 请求报文组节点的子节点：单证节点scandate属性
	 */
	public static final String XML_DOC_SCANDATE = "scandate";
	/**
	 * 请求报文组节点的子节点：单证节点operator属性
	 */
	public static final String XML_DOC_SCANUSER = "scanuser";
	/**
	 * 请求报文组节点的子节点：单证节点scancom属性
	 */
	public static final String XML_DOC_SCANCOM = "scancom";
	/**
	 * 请求报文组节点的子节点：单证节点managecom属性
	 */
	public static final String XML_DOC_MANAGECOM = "managecom";
	
	public static final String XML_DOC_DOCREMARK = "docremark";
	/**
	 * 请求报文组节点的子节点：单证节点docflag属性
	 */
	public static final String XML_DOC_DOCFLAG = "docflag";
	/**
	 * 请求报文单证节点的子节点:页节点
	 */
	public static final String XML_PAGES = "pages";
	/**
	 * 请求报文页节点的子节点:page节点
	 */
	public static final String XML_PAGE = "page";
	/**
	 * 请求报文page节点的pagename属性
	 */
	public static final String XML_PAGE_PAGENAME = "pagename";
	/**
	 * 请求报文page节点的pagecode属性
	 */
	public static final String XML_PAGE_PAGECODE = "pagecode";
	/**
	 * 请求报文page节点的pagesuffix属性
	 */
	public static final String XML_PAGE_PAGESUFFIX = "pagesuffix";
	/**
	 * 请求报文page节点的boxno属性
	 */
	public static final String XML_PAGE_BOXNO = "boxno";
	/**
	 * 请求报文page节点的filepath属性
	 */
	public static final String XML_PAGE_FILEPATH = "filepath";
	/**
	 * 请求报文page节点的format属性
	 */
	public static final String XML_PAGE_FORMAT = "format";
	/**
	 * 请求报文page节点的pageurl属性
	 */
	public static final String XML_PAGE_PAGEURL = "pageurl";
	/**
	 * 请求报文page节点的filekey属性
	 */
	public static final String XML_PAGE_FILEKEY = "filekey";
	/**
	 * 请求报文page节点的scancom属性
	 */
	public static final String XML_PAGE_SCANCOM = "scancom";
	/**
	 * 请求报文page节点的operator属性
	 */
	public static final String XML_PAGE_OPERATOR = "scanuser";
	/**
	 * 请求报文page节点的scandate属性
	 */
	public static final String XML_PAGE_SCANDATE = "scandate";
	
	public static final String XML_PAGE_PAGEFILE = "pagefile";
	/**
	 * 请求报文page节点的子节点:marklayer节点
	 */
	public static final String XML_MARKLAYER = "marklayer";
	/**
	 * 请求报文marklayer节点的format属性
	 */
	public static final String XML_MARKLAYER_FORMAT = "format";
	/**
	 * 请求报文page节点的子节点:content节点
	 */
	public static final String XML_CONTENT = "content";
	/**
	 * 请求报文page节点的子节点:remark节点
	 */
	public static final String XML_REMARK = "remark";
	/**
	 * 请求报文单证节点的子节点:扩展属性节点
	 */
	public static final String XML_PROPS = "props";
	/**
	 * 请求报文单证子节点:扩展属性节点prop子节点
	 */
	public static final String XML_PROP = "prop";
	/**
	 * 请求报文单证子节点:扩展属性节点的prop子节点code属性
	 */
	public static final String XML_PROP_CODE = "code";
	/**
	 * 请求报文单证子节点:扩展属性节点的prop子节点value属性
	 */
	public static final String XML_PROP_VALUE = "value";
}