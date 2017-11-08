package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
import com.sinosoft.service.BusinessService;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 理赔条形码打印，根据“赔案号《外部传入》”打印出相应的条形码
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author yuejw
 * @version 1.0
 */
public class BarCodePrintBL implements PrintService,BusinessService {
private static Logger logger = Logger.getLogger(BarCodePrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	// 向后台提交
	private VData mInputData = new VData();
	// 返回前台的数据包
	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();
	private String mDocCode;
	private String mSubType;


	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		if(mDocCode!=null && !mDocCode.equals("") && !mDocCode.equals("null")){
		// 准备所有要打印的数据
		if (!getBarCodePrintData()) {
			CError.buildErr(this, "单证号分隔页打印错误!");
			return false;
		}
		}else if(mSubType!=null && !mSubType.equals("") && !mSubType.equals("null")){
			// 准备所有要打印的数据
			if (!getBarCodeTypePrintData()) {
				CError.buildErr(this, "类型分隔页打印错误!");
				return false;
			}
		}else{
			CError.buildErr(this, "Arg error!");
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		mInputData = cInputData;
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mDocCode = (String) mTransferData.getValueByName("DocCode");
		mSubType = (String) mTransferData.getValueByName("SubType");
		return true;
	}

	/**
	 * 返回处理信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误信息
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 打印处理
	 * 
	 * @return boolean
	 */
	private boolean getBarCodePrintData() {
		// 接收条码的参数
		String strCodeParam = "BarHeight=20&BarRation=2&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10";

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport txmlexport = new XmlExport(); // 新建一个XmlExport的实例
		txmlexport.createDocument("BarCodePrint.vts", "");
		texttag.add("BarCode1", mDocCode);
		texttag.add("BarCodeParam1", strCodeParam);
		// 下面两个条形码不一定非要是CLM999，可以写成另外的
		texttag.add("BarCode2", " ");
		texttag.add("BarCodeParam2", strCodeParam);
		texttag.add("BarCode3", " ");
		texttag.add("BarCodeParam3", strCodeParam);
		if (texttag.size() > 0) {
			txmlexport.addTextTag(texttag);
		}
		// xmlexport.outputDocumentToFile("d:\\", "testHZM"); //输出xml文档到文件
		/* ##################### 后台调试部分，生成临时文件############################ */
		// String strTemplatePath="E:/lis/ui/f1print/NCLtemplate/";
		// String strVFPathName=strTemplatePath+"DDDD.vts";
		// CombineVts tcombineVts = null;
		// tcombineVts = new CombineVts(txmlexport.getInputStream(), strTemplatePath);
		// ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		// tcombineVts.output(dataStream);
		// AccessVtsFile.saveToFile(dataStream, strVFPathName);
		mResult.clear();
		mResult.addElement(txmlexport);
		logger.debug("xmlexport=" + txmlexport);
		return true;
	}

	private boolean getBarCodeTypePrintData() {
		// 接收条码的参数
		String strCodeParam = "BarHeight=20&BarRation=2&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10";
		
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport txmlexport = new XmlExport(); // 新建一个XmlExport的实例
		String sql="select subtypename from es_doc_def where subtype='"+"?mSubType?"+"'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("mSubType",mSubType);
		String subtypename=new ExeSQL().getOneValue(sqlbv1);
		txmlexport.createDocument("BarCodeTypePrint.vts", "");
		texttag.add("BarCode1", mSubType);
		texttag.add("BarCodeParam1", strCodeParam);
		// 下面两个条形码不一定非要是CLM999，可以写成另外的
		texttag.add("BarCode2", " ");
		texttag.add("BarCodeParam2", strCodeParam);
		texttag.add("BarCode3", " ");
		texttag.add("BarCodeParam3", strCodeParam);
		texttag.add("BarCode4", " ");
		texttag.add("BarCodeParam4", strCodeParam);
		texttag.add("subtypename", subtypename);
		if (texttag.size() > 0) {
			txmlexport.addTextTag(texttag);
		}
		// xmlexport.outputDocumentToFile("d:\\", "testHZM"); //输出xml文档到文件
		/* ##################### 后台调试部分，生成临时文件############################ */
		// String strTemplatePath="E:/lis/ui/f1print/NCLtemplate/";
		// String strVFPathName=strTemplatePath+"DDDD.vts";
		// CombineVts tcombineVts = null;
		// tcombineVts = new CombineVts(txmlexport.getInputStream(), strTemplatePath);
		// ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		// tcombineVts.output(dataStream);
		// AccessVtsFile.saveToFile(dataStream, strVFPathName);
		mResult.clear();
		mResult.addElement(txmlexport);
		return true;
	}

}
