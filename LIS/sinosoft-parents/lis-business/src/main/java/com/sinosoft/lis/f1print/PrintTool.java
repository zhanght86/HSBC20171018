package com.sinosoft.lis.f1print;


import org.dom4j.Element;

import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.encrypt.des.DesEncrypt;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.print.func.XMLPrintTagName;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.XmlExportNew;
import org.apache.log4j.Logger;
/**
 * 存放打印引擎的通用工具
 * 
 * @author sinosoft
 * 
 */
public class PrintTool {
	private static Logger logger = Logger.getLogger(PrintTool.class);

	/**
	 * 生成barcode
	 * 
	 * @param xmlExportNew 数据存储类
	 * @param prtNo 不加密的流水号等
	 */
	public static void setBarCode(XmlExportNew xmlExport, String prtSeq) {
		setBarCode(xmlExport, prtSeq, "", "");
	}

	/**
	 * 生成barcode
	 * 
	 * @param xmlExport 要添加barcode的xmlexportnew对象
	 * @param prtSeq code，一般是流水号，不加密
	 * @param otherContent 要加密的内容，在二维码中显示，将和之前的code以分号隔开
	 */
	public static void setBarCode(XmlExportNew xmlExport, String prtSeq,
			String otherContent) {
		setBarCode(xmlExport, prtSeq, otherContent, "");
	}

	/**
	 * @param xmlExport
	 * @param prtSeq
	 * @param otherContent
	 * @param type
	 */
	public static void setBarCode(XmlExportNew xmlExport, String prtSeq,
			String otherContent, String type) {
		Element tElement = xmlExport.getDocument().getRootElement().element(
				"BarCodeInfo");
		if (tElement == null) {
			initBarCode(xmlExport, prtSeq, otherContent, "");
		} else {
			setBarCodeByElement(xmlExport, tElement, prtSeq, otherContent, type);
		}
	}

	/**
	 * 此xmlexport对象第一次添加barcode
	 * 
	 * @param xmlExport
	 * @param prtSeq
	 *            流水号
	 * @param otherContent
	 *            未加密内容
	 * @param type
	 *            类型，留用
	 */
	private static void initBarCode(XmlExportNew xmlExport, String prtSeq,
			String otherContent, String type) {
		ListTable tListTable = new ListTable();
		String[] tTitle = new String[5];
		tTitle[0] = "Code";
		tTitle[1] = "Param";
		tTitle[2] = "Name";
		tTitle[3] = "Type";
		tTitle[4] = "Content";
		tListTable.setName("BarCodeInfo");

		String[] StrCont = new String[5];
		StrCont[0] = prtSeq;
		StrCont[1] = "BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10";// "BarHeight=300&amp;BarRation=3&amp;BarWidth=300&amp;BgColor=FFFFFF&amp;ForeColor=000000&amp;XMargin=10&amp;YMargin=10";
		StrCont[2] = "BarCode,2";
		StrCont[3] = type;
		StrCont[4] = getEncode(otherContent);
		tListTable.add(StrCont);

		xmlExport.addListTable(tListTable, tTitle, "BarCode");
	}

	/**
	 * 添加条形码到指定节点下，非第一次添加
	 * 
	 * @param xmlExport
	 * @param element
	 * @param prtSeq
	 * @param otherContent
	 * @param type
	 */
	private static void setBarCodeByElement(XmlExportNew xmlExport,
			Element element, String prtSeq, String otherContent, String type) {
		String[] tTitle = new String[5];
		tTitle[0] = "Code";
		tTitle[1] = "Param";
		tTitle[2] = "Name";
		tTitle[3] = "Type";
		tTitle[4] = "Content";

		String[] StrCont = new String[5];
		StrCont[0] = prtSeq;
		StrCont[1] = "BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10";// "BarHeight=300&amp;BarRation=3&amp;BarWidth=300&amp;BgColor=FFFFFF&amp;ForeColor=000000&amp;XMargin=10&amp;YMargin=10";
		StrCont[2] = "BarCode,2";
		StrCont[3] = type;
		StrCont[4] = getEncode(otherContent);

		xmlExport.addListTableToElement(element, StrCont, tTitle, "BarCode");
	}

	/**
	 * 将输入字符串加密
	 * 
	 * @param otherContent
	 * @return
	 */
	private static String getEncode(String otherContent) {
		if ("".equalsIgnoreCase(otherContent) || otherContent == null)
			return "";
		DesEncrypt tDesEncrypt = new DesEncrypt();
		otherContent = ";" + tDesEncrypt.strEnc(otherContent);
		return otherContent;
	}

	/**
	 * 对以封号隔开的二维码数据进行解密，返回字符串数组，第一个为未加密的流水号，第二个为已解密的内容
	 * 
	 * @param mixContent 二维码数据
	 * @return
	 */
	public static String[] getBarCodeDecode(String mixContent) {
		if (mixContent != null) {
			String[] barCodeArray = mixContent.split(";");
			if (barCodeArray.length == 2) {
				barCodeArray[1] = getDecode(barCodeArray[1]);
			}
			return barCodeArray;
		}
		return null;
	}

	/**
	 * 将输入字符串解码
	 * 
	 * @param encodeContent
	 * @return
	 */
	public static String getDecode(String encodeContent) {
		if ("".equalsIgnoreCase(encodeContent) || encodeContent == null)
			return "";
		DesEncrypt tDesEncrypt = new DesEncrypt();
		return tDesEncrypt.strDec(encodeContent);
	}

	/**
	 * 获得客户（投保人或者被保人）的语言
	 * 
	 * @param customerNo 客户号，在表LDPerson
	 */
	public static String getCustomerLanguage(String customerNo) {
		LDPersonDB tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(customerNo);
		tLDPersonDB.getInfo();
		String uLanguage = tLDPersonDB.getLanguage();
		return uLanguage;
	}

	/**
	 * 获得系统的语言
	 * @param sysLanguage 前台通过Local对象获得的system语言，在国际版中使用，目前默认返回zh
	 * @return
	 */
	public static String getSysLanguage(String sysLanguage) {
		String uLanguage = "zh";
		return uLanguage;
	}
	
	/**
	 * 根据客户（投保人或者被保人）的语言判定该打印何种语言的模板
	 * 
	 * @param xmlExport
	 * @param customerNo
	 */
	public static void setCustomerLanguage(XmlExportNew xmlExport, String customerNo) {
		String uLanguage = getCustomerLanguage(customerNo);
		if (uLanguage != null && !"".equals(uLanguage)) {
			xmlExport.setUserLanguage(uLanguage);
		}
	}

	/**
	 * 根据姓名，性别，语言来修改称呼
	 * 
	 * @param name
	 * @param gender
	 * @param language
	 * @return
	 */
	public static String getHead(String name, String gender, String language) {
		if (gender != null && gender.equals("0"))
			name = getHeadMale(name, language);
		else
			name = getHeadFemale(name, language);
		return name;
	}

	private static String getHeadMale(String name, String language) {
		if ("en".equalsIgnoreCase(language))
			name = "Mr." + name;
		else
			name = name + "先生";
		return name;
	}

	private static String getHeadFemale(String name, String language) {
		if ("en".equalsIgnoreCase(language))
			name = "Ms." + name;
		else
			name = name + "女士";
		return name;
	}
	
	/**根据中文名获得标签的英文名
	 * @param chineseName
	 * @return
	 */
	public static String getTagName(String chineseName){
		return XMLPrintTagName.getName(chineseName);
	}
	
	public static void main(String[] args){
		String[] content = PrintTool.getBarCodeDecode("0300002542;D4990051D60A72161C926CD86A8FC42A60AFC9C20CFF59D5EDFD5D1E2DF9470A527EF7263B745832D4990051D60A7216");
		logger.debug(content[0]);
		logger.debug(content[1]);
	}
	/**
	 * 获得系统的语言
	 * @param sysLanguage 前台通过Local对象获得的system语言，在国际版中使用，目前默认返回zh
	 * @return
	 */
	public static String getSysLanguage(GlobalInput globalInput) {
		String uLanguage = "zh";
		//uLanguage = globalInput.locale.getLanguage();//国际版中选择相应语言
		return uLanguage;
	}
	/**
	 * 设置系统语言
	 * 
	 * @param xmlExport
	 * @param globalInput 全局数据
	 */
	public static void setSysLanguage(XmlExportNew xmlExport, GlobalInput globalInput) {
		String uLanguage = getSysLanguage(globalInput);
		if (uLanguage != null && !"".equals(uLanguage)) {
			xmlExport.setSysLanguage(uLanguage);
		}
	}
}
