package com.sinosoft.easyscan5.common;

/**
 * xml节点名称，常量类
 * 
 * @author Administrator
 * 
 */
public class XmlConstants {
	
	/**获取中心配置**/
	public static final String CON_XML_ROOT = "DATA";
	public static final String CON_XML_CENTERSETTING = "CenterSetting";

	public static final String XML_NODE_BUSSTYPE = "BussType";
	public static final String XML_NODE_SUBTYPE = "SubType";
	
	public static final String XML_NODE_DEFCODE = "DefCode";
	public static final String XML_NODE_PROPFIELD = "PropField";	
	public static final String XML_NODE_CODELIST = "CodeList";
	public static final String XML_NODE_CODETYPE = "CodeType";
	public static final String XML_NODE_LIST = "List";

	public static final String XML_NODE_COMPANY = "Company";
	public static final String XML_NODE_MANAGECOM = "ManageCom";
	public static final String CON_XML_RET_NAME = "RETURN";

	public static final String XML_ATTR_CODE = "code";
	public static final String XML_ATTR_NAME = "name";
	public static final String XML_ATTR_PAPERTYPE = "papertype";
	public static final String XML_ATTR_CODEFLAG = "codeflag";
	public static final String XML_ATTR_NEWCASEFLAG = "newcaseflag";
	public static final String XML_ATTR_VERSION = "version";
	public static final String XML_ATTR_CODELEN = "codelen";
	public static final String XML_ATTR_UPLOADFILETYPE = "uploadfiletype";
	public static final String XML_ATTR_FILESAVETYPE = "filesavetype";// 0:黑白,1：灰度,2：彩色,空默认黑白。

	public static final String XML_ATTR_PROPCODE = "PropCode";
	public static final String XML_ATTR_PROPNAME = "PropName";
	public static final String XML_ATTR_FIELDORDER = "FieldOrder";
	public static final String XML_ATTR_BUSSTYPE = "BussType";
	public static final String XML_ATTR_SUBTYPE = "SubType";
	public static final String XML_ATTR_CTRLTYPE = "CtrlType";
	public static final String XML_ATTR_CTRLTOP = "CtrlTop";
	public static final String XML_ATTR_CTRLLEFT = "CtrlLeft";
	public static final String XML_ATTR_CTRLHEIGHT = "CtrlHeight";
	public static final String XML_ATTR_CTRLWIDTH = "CtrlWidth";
	public static final String XML_ATTR_CTRLVISIBLE = "CtrlVisible";
	public static final String XML_ATTR_CTRLTITLE = "CtrlTitle";
	public static final String XML_ATTR_CTRLFORM = "CtrlForm";
	public static final String XML_ATTR_LISTCODETYPE = "ListCodeType";
	public static final String XML_ATTR_CTRLDEFAULTVALUE = "CtrlDefaultValue";
	public static final String XML_ATTR_REMARK = "Remark";
	public static final String XML_ATTR_VALIDFLAG = "ValidFlag";

	public static final String XML_SIGN = "Sign";
	public static final String XML_SIGN_SIGNCONFIG = "SignConfig";
	public static final String XML_SIGN_BUSSTYPE = "SignBussType";
	public static final String XML_SIGN_SUBTYPE = "SignSubType";
	public static final String XML_SIGN_SIGNTYPE = "SignType";
	public static final String XML_SIGN_PAGECODE = "SignPageCode";
	public static final String XML_SIGN_TEMPLATEX = "SignTemplateX";
	public static final String XML_SIGN_TEMPLATEY = "SignTemplateY";
	public static final String XML_SIGN_X = "SignX";
	public static final String XML_SIGN_Y = "SignY";
	public static final String XML_SIGN_WIDTH = "SignWidth";
	public static final String XML_SIGN_HEIGHT = "SignHeight";
	public static final String XML_SIGN_SIGNTYPECONFIG = "SignTypeConfig";
	public static final String XML_SIGN_SIGNTYPENAME = "SignTypeName";

	public static final String XML_FOLLOWACT = "FollowAct";
	public static final String XML_FOLLOWACT_SIGNCONFIG = "FollowActConfig";
	public static final String XML_FOLLOWACT_BUSSTYPE = "FollowActBussType";
	public static final String XML_FOLLOWACT_SUBTYPE = "FollowActSubType";
	public static final String XML_FOLLOWACT_SIGNTYPE = "FollowActPropCode";
	public static final String XML_FOLLOWACT_PAGECODE = "FollowActPageCode";
	public static final String XML_FOLLOWACT_TEMPLATEX = "FollowActTemplateX";
	public static final String XML_FOLLOWACT_TEMPLATEY = "FollowActTemplateY";
	public static final String XML_FOLLOWACT_X = "FollowActX";
	public static final String XML_FOLLOWACT_Y = "FollowActY";
	public static final String XML_FOLLOWACT_WIDTH = "FollowActWidth";
	public static final String XML_FOLLOWACT_HEIGHT = "FollowActHeight";
	public static final String XML_SYSTEMCONFIG = "SystemConfig";
	public static final String XML_BARCODECONFIG = "BarCodeRecognizeSetting";
	public static final String XML_BATCHSCANCONFIG = "BatchScanSetting";
	/*扫描仪配置*/
    public static final String XML_NODE_SCANSETTINGLIST = "ScanSetting";
    public static final String XML_NODE_SCAN = "Scan";
    public static final String XML_NODE_SCANCODE = "DefSettingCode";
    public static final String XML_NODE_SCANNAME = "DefSettingName";
    public static final String XML_NODE_SCANPAGES = "Pages";
    public static final String XML_NODE_SCANDPI = "DPI";
    public static final String XML_NODE_SCANBITDEPTH = "BitDepth";
    public static final String XML_NODE_SCANPAGEMODE = "PagerMode";
    public static final String XML_NODE_SCANDUPLEX = "Duplex";
    public static final String XML_NODE_SCANBRIGHTNESS = "Brightness";
    public static final String XML_NODE_SCANCONTRAST = "Contrast";
    public static final String XML_NODE_SCANAUTOBRIGHT = "Autobright";
    public static final String XML_NODE_SCANAUTOMATICDESKEW = "Automaticdeskew";
    public static final String XML_NODE_SCANISBLACKEDGE = "IsBlackEdge";
    public static final String XML_NODE_SCANISBLANK = "IsBlank";
    public static final String XML_NODE_SCANBLANKVALUE = "BlankValue";

	/**获取箱号**/
	public static final String CON_XML_SCANNO = "ScanNo";
	public static final String CON_XML_SCANNO_COUNT = "COUNT";
	public static final String CON_XML_SCANNO_PAGECOUNT = "PAGECOUNT";    
	
	/**单证信息节点**/
	public static final String XML_CLIENT_DOCID = "DocID";
	public static final String XML_CLIENT_DOCCODE = "DocCode";
	public static final String XML_CLIENT_BUSSTYPE = "BussType";
	public static final String XML_CLIENT_SUBTYPE = "SubType";
	public static final String XML_CLIENT_NUMPAGES = "NumPages";
	public static final String XML_CLIENT_DOCFLAG = "DocFlag";
	public static final String XML_CLIENT_DOCREMARK = "DocRemark";
	public static final String XML_CLIENT_SCANOPERATOR = "ScanOperator";
	public static final String XML_CLIENT_SCANDATE = "MakeDate";
	public static final String XML_CLIENT_MANAGECOM = "ManageCom";
	public static final String XML_CLIENT_SCANNO = "ScanNo";
	public static final String XML_CLIENT_DOCPRIORITY = "Priority";
	public static final String XML_CLIENT_TEMPFLAG = "TempFlag";
	public static final String XML_CLIENT_DOCVERSION = "DocVersion";
	public static final String XML_CLIENT_UPLOADFILETYPE = "UploadFileType";
	
	public static final String XML_CLIENT_PRINTCODE = "PrintCode";
	public static final String XML_CLIENT_SCANTYPE = "ScanType";
	public static final String XML_CLIENT_MAKEDATE = "MakeDate";
	public static final String XML_CLIENT_MAKETIME = "MakeTime";
	public static final String XML_CLIENT_MODIFYDATE = "ModifyDate";
	public static final String XML_CLIENT_MODIFYTIME = "ModifyTime";
	public static final String XML_CLIENT_PAGEBARCODE = "Page_BarCode";
	public static final String XML_CLIENT_PICPATHFTP = "PicPathFtp";
	public static final String XML_CLIENT_FNDocKey = "FNDocKey";
	/**单证页信息节点**/
	public static final String XML_CLIENT_PAGEID = "PageID";
	public static final String XML_CLIENT_PAGENO = "PageCode";
	public static final String XML_CLIENT_SERVERNO = "HostName";
	public static final String XML_CLIENT_PAGENAME = "PageName";
	public static final String XML_CLIENT_PAGESUFFIX = "PageSuffix";
	public static final String XML_CLIENT_PICPATH = "PicPath";
	public static final String XML_CLIENT_PAGEFLAG = "PageFlag";
	public static final String XML_CLIENT_PAGETYPE = "PageType";
	public static final String XML_CLIENT_OPERATOR = "Operator";
	/**请求返回参数节点**/
	public static final String XML_CLIENT_RETURN_NUMBER = "NUMBER";
	public static final String XML_CLIENT_RETURN_MESSAGE = "MESSAGE";
	public static final String XML_CLIENT_PAGEURL = "Page_URL";
	/**扩展属性**/
	public static final String XML_CLIENT_PROPERTY = "Property";
	public static final String XML_CLIENT_EXPPROPVERSION = "ExpPropVersion";
	/**版本**/
	public static final String XML_PARAMVERSION = "PARAMVERSION";
	public static final String XML_SYS_COMPANY = "SYS_COMPANY";
	public static final String XML_ES_DOC_DEF = "ES_DOC_DEF";
	public static final String XML_ES_TWAIN_DEF = "ES_TWAIN_DEF";
	public static final String XML_ES_DOCTYPE_EXPPROPERTY = "ES_DOC_PROP";
	public static final String XML_EASYSCANXML = "EASYSCANXML";
	
	/**头部节点**/	
	public static final String CON_XML_HEAD = "HEAD";
	public static final String CON_XML_NAME = "NAME";
	public static final String CON_XML_VERSION = "VERSION";
	public static final String CON_XML_ACTIONCODE = "ACTIONCODE";
	/**标注节点**/
	public static final String XML_MARKLAYER = "MarkLayer";
}
