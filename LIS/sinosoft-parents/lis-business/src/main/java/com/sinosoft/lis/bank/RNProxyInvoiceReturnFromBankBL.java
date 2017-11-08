package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sinosoft.lis.db.LDBankDB;
import com.sinosoft.lis.db.LYBankBillDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * 
 * <p>
 * Description: 银行文件转换到数据模块
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author guanwei
 * 
 * @version 1.0
 */

public class RNProxyInvoiceReturnFromBankBL {
private static Logger logger = Logger.getLogger(RNProxyInvoiceReturnFromBankBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 传出数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	// 业务数据
	private TransferData inTransferData = new TransferData();
	private GlobalInput inGlobalInput = new GlobalInput();
	private String fileName = "";
	private String bankCode = "";
	private Document dataDoc = null;
	private Document resultDoc = null;

	// 做为统一提交insert into LYBankUpload
	private Connection coon = null;
	private PreparedStatement prst = null;

	// //更新字段 LYBankBill.BankPrintCount
	// private PreparedStatement Updateprst = null;

	public RNProxyInvoiceReturnFromBankBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"READ"和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

		return true;
	}

	/**
	 * =======下面的主要方法是要得到基本数据和开始统一调用其它方法完成逻辑设计 ===== 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			inTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			inGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ReadFromFileBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			// 获取银行发票返回文件数据
			if (mOperate.equals("READ")) {
				// 获取返回文件名称
				fileName = (String) inTransferData.getValueByName("fileName");
				fileName = fileName.replace('\\', '/');
				bankCode = (String) inTransferData.getValueByName("bankCode");
				logger.debug("bankCode in inTransferData" + bankCode);

				// 获取银行返回数据信息并开始处理, return boolean
				if (!readBankFile(fileName)) {
					return false;
				}
			}
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ReadFromFileBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * ===================进行相应的方法去处理的每个方法 ===================
	 * =============下面的方法主要用来解析,并在解析时将数据导入相应的库中======== 创建一个xml文档对象DOM
	 * 
	 * @return
	 */
	private Document buildDocument() {
		try {
			// Create the document builder
			DocumentBuilderFactory dbfactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docbuilder = dbfactory.newDocumentBuilder();

			// Create the new document(s)
			return docbuilder.newDocument();
		} catch (Exception e) {
			logger.debug("Problem creating document: " + e.getMessage());
			return null;
		}
	}

	/**
	 * 获取xsl文件路径
	 * 
	 * @return
	 */
	public String getXslPath() throws Exception {
		LDBankDB tLDBankDB = new LDBankDB();
		tLDBankDB.setBankCode(bankCode);
		logger.debug("银行编码bankCode:" + bankCode);
		if (!tLDBankDB.getInfo()) {
			throw new Exception("获取银行XSL描述信息失败！");
		}
		logger.debug(tLDBankDB.getChkReceiveF());
		return tLDBankDB.getChkReceiveF();
	}

	/**
	 * Simple sample code to show how to run the XSL processor from the API.
	 */
	public boolean xmlTransform() {
		logger.debug("Processing xmlTransform");
		try {
			String xslPath = getXslPath();
			logger.debug("xslPath:" + xslPath);

			File fStyle = new File(xslPath);
			logger.debug("fStyle:" + fStyle.toString());
			Source source = new DOMSource(dataDoc);
			logger.debug("source:" + source);
			Result result = new DOMResult(resultDoc);
			logger.debug("result:" + result);
			Source style = new StreamSource(fStyle);
			logger.debug("style:" + style.toString());

			// Create the Transformer
			TransformerFactory transFactory = TransformerFactory.newInstance();
			logger.debug("transFactory:" + transFactory);
			Transformer transformer = transFactory.newTransformer(style);
			logger.debug("transformer:" + transformer);

			// Transform the Document
			transformer.transform(source, result);
			logger.debug("Transform Success!");
		} catch (Exception e) {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ReadFromFileBLS";
			tError.functionName = "SimpleTransform";
			tError.errorMessage = "Xml处理失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 将每条读取的数据进行库操作 ,
	 * 
	 * @param tLYReturnFromBankSet
	 * @return
	 */
	private boolean xmlToDatabase() {
		logger.debug("Processing xmlToDatabase");
		try {
			/**
			 * +++++++++++++++++Insert Into LyBankUpLoad++++++++++++++
			 * 当银行返回文件时将根据serialno插入一条记录 String tSql
			 */
			int m = 0;
			int t = 0;
			String tSql = " insert into LYBankUpload (SerialNo,BankCode,UploadDate,"
					+ " Col1,Col2,Col3,LongCol1,Col13,Col14,Col15,"
					+ " Operator,MakeDate,MakeTime) values "
					+ " (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			logger.debug("插入的Sql为:" + tSql);

			/**
			 * +++++++++++++++++update LYBankBill++++++++++++++
			 * 当银行返回文件时将根据SerialNo更新相应记录 String tBillSql
			 */
			String tCurrentDate = PubFun.getCurrentDate();
			logger.debug("CurrentDate in xmlToDatabase:" + tCurrentDate);
			String tCurrentTime = PubFun.getCurrentTime();
			logger.debug("CurrentTime in xmlToDatabase:" + tCurrentTime);
			// String tBillSql =
			// "update LYBankBill set BankPrintCount = 1 , ReturnFromBankDate = '" +
			// tCurrentDate + "' , ModifyDate = '" + tCurrentDate +
			// "' , ModifyTime = '" + tCurrentTime + "'" +
			// " where SerialNo = ? ";
			coon = DBConnPool.getConnection();
			prst = coon.prepareStatement(tSql);
			// Updateprst = coon.prepareStatement(tBillSql);
			displayDocument(resultDoc);
			// logger.debug("更新的Sql为:" + tBillSql);

			// get all rows
			NodeList rows = resultDoc.getDocumentElement().getChildNodes();
			logger.debug("rows" + rows.toString());

			int i;
			for (i = 0; i < rows.getLength(); i++) {
				// For each row, get the row element and name
				Element thisRow = (Element) rows.item(i);
				// Get the columns for thisRow
				NodeList columns = thisRow.getChildNodes();
				String tBankAccNo = "";
				String tAccName = "";
				String tPayMoney = "";
				String tBankNetworkCode = "";
				String tBankSerialNo = "";
				String tPrintDate = "";
				String tPrintDetail = "";
				String tSerialNo = "";
				String tUploadDate = PubFun.getCurrentDate();
				String tOperator = inGlobalInput.Operator;
				for (int j = 0; j < columns.getLength(); j++) {
					Element thisColumn = (Element) columns.item(j);

					String colName = thisColumn.getNodeName();
					String colValue = thisColumn.getFirstChild().getNodeValue();
					logger.debug(colName + colValue);
					// 银行帐号
					if (colName.equalsIgnoreCase("BankAccNo")) {
						tBankAccNo = colValue;
						logger.debug("银行帐号tBankAccNo:" + tBankAccNo);
					}
					// 帐户名
					if (colName.equalsIgnoreCase("AccName")) {
						tAccName = colValue;
						logger.debug("账户姓名tAccName:" + tAccName);
					}
					// 金额
					if (colName.equalsIgnoreCase("PayMoney")) {
						tPayMoney = colValue;
						logger.debug("缴费金额tPayMoney:" + tPayMoney);
					}
					// 流水号
					if (colName.equalsIgnoreCase("SerialNo")) {
						tSerialNo = colValue;
						logger.debug("流水号tSerialNo:" + tSerialNo);
					}
					// 打印明细
					if (colName.equalsIgnoreCase("PrintDetail")) {
						tPrintDetail = colValue;
						logger.debug("打印明细tPrintDetail:" + tPrintDetail);
					}
					// 打印日期
					if (colName.equalsIgnoreCase("PrintDate")) {
						tPrintDate = colValue;
						logger.debug("打印日期tPrintDate：" + tPrintDate);
					}
					// 打印网点
					if (colName.equalsIgnoreCase("BankNetworkCode")) {
						tBankNetworkCode = colValue;
						logger.debug("银行网点tBankNetworkCode:"
								+ tBankNetworkCode);
					}
					// 中间业务流水号
					if (colName.equalsIgnoreCase("BankSerialNo")) {
						tBankSerialNo = colValue;
					}
				}
				LYBankBillDB tLYBankBillDB = new LYBankBillDB();
				tLYBankBillDB.setSerialNo(tSerialNo);
				tLYBankBillDB.setBankCode(bankCode);
				if (!tLYBankBillDB.getInfo()) {
					logger.debug(tSerialNo + "在文件发盘表中没有数据");
					t++;
					continue;
				}
				prst.setString(1, tSerialNo); // 流水号
				prst.setString(2, bankCode); // 银行代码
				prst.setString(3, tUploadDate); // 上传日期
				prst.setString(4, tBankAccNo); // 银行帐号
				prst.setString(5, tAccName); // 账户姓名
				prst.setString(6, tPayMoney); // 金额
				prst.setString(7, tPrintDetail); // 打印明细
				prst.setString(8, tPrintDate); // 打印日期
				prst.setString(9, tBankNetworkCode); // 打印网点
				prst.setString(10, tBankSerialNo); // 中间业务流水号
				prst.setString(11, tOperator); // 操作员
				prst.setString(12, tCurrentDate); // 入机日期
				prst.setString(13, tCurrentTime); // 入机时间
				// Updateprst.setString(1, tSerialNo); //根据流水号更新LYBankBill
				prst.execute();
				// Updateprst.execute();
				coon.commit();
				m++;
			}
			logger.debug("======文件中共有" + i + "条记录======");
			logger.debug("其中有" + t + "条在文件发盘表LYBankBill表中没有记录");
			logger.debug("共向LYBankUpload表中插入了" + m
					+ "条记录,并更新了LYBankBill表中的" + m + "条记录");
			// Updateprst.close();
			prst.close();
			coon.close();
			logger.debug("prst.close");
		} catch (Exception e) {
			e.printStackTrace();
			// @@错误处理
			try {
				// Updateprst.close();
				prst.close();
				coon.close();
			} catch (Exception eSql) {
				eSql.printStackTrace();
			}
			CError tError = new CError();
			tError.moduleName = "RNProxyInvoicReturnFromBankBL";
			tError.functionName = "xmlToDatabase";
			tError.errorMessage = "Xml转入数据库处理失败!" + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 读取银行返回文件
	 * 
	 * @param fileName
	 * @return
	 */
	private boolean readBankFile(String fileName) {
		// Declare the document
		dataDoc = buildDocument();
		logger.debug("============1:dataDoc.toString()="
				+ dataDoc.toString());
		resultDoc = buildDocument();
		logger.debug("============2:resultDoc.toString()"
				+ resultDoc.toString());

		try {
			// 读入文件到BUFFER中以提高处理效率 这样的调用是为了解决汉字编码问题.
			logger.debug("======================================3 go into try");
			// InputStream ins = new FileInputStream(fileName);
			logger.debug(fileName);
			// logger.debug("======================4:ins.toString() = " +
			// ins.toString());
			// BufferedReader in = new BufferedReader(new InputStreamReader(ins));
			BufferedReader in = new BufferedReader(new FileReader(fileName));

			// BufferedReader in = new BufferedReader(
			// new FileReader(fileName));
			// //将所有文本以行为单位读入到VECTOR中
			String strLine = "";

			// 创建根标签
			Element dataRoot = dataDoc.createElement("BANKDATA");
			logger.debug("====================== 5:create Element dataRoot");
			// String tStr = in.readLine();
			// Byte[] tByte = tStr.getBytes("ISO","GBK");

			// 循环获取每一行
			while (true) {
				strLine = in.readLine();
				// logger.debug("======================6:" +
				// new String(in.readLine().getBytes(
				// "UTF-8"), "UTF-8"));
				logger.debug("=================================6");
				if (strLine == null) {
					logger.debug("strLine == null");
					break;
				}
				// strLine = strLine.trim();
				// 去掉空行
				if (strLine.trim().length() < 3) {
					logger.debug("strLine.trim().length() < 3");
					continue;
				}
				logger.debug(strLine);

				// Create the element to hold the row
				Element rowEl = dataDoc.createElement("ROW");
				logger.debug("======================7:create Element rowEl="
								+ rowEl.toString());
				Element columnEl = dataDoc.createElement("COLUMN");
				logger.debug("======================8:create Element columnEl="
								+ columnEl.toString());
				columnEl.appendChild(dataDoc.createTextNode(strLine));
				logger.debug("======================9:columnEl.appendChild(dataDoc.createTextNode(strLine));"
								+ columnEl.toString());
				rowEl.appendChild(columnEl);
				logger.debug("======================10：rowEl.appendChild(columnEl);");

				// Add the row element to the root
				dataRoot.appendChild(rowEl);
				logger.debug("======================11：dataRoot.appendChild(rowEl);");
			}
			// Add the root to the document
			dataDoc.appendChild(dataRoot);
			logger.debug("======================12:dataDoc.appendChild(dataRoot);");
			NodeList tables = dataDoc.getDocumentElement().getChildNodes();
			logger.debug("======================13:tables.getLength():"
					+ tables.getLength());
			displayDocument(dataDoc);

			// 转换xml
			if (!xmlTransform()) {
				logger.debug("xmlTransform出错，xml样式表转换失败。");
				throw new Exception("xmlTransform出错，xml样式表转换失败。");
			}

			// 将数据存入数据库
			if (!xmlToDatabase()) {
				logger.debug("xmlToDatabase出错，xml信息导入数据库失败");
				throw new Exception("xmlToDatabase出错，xml信息导入数据库失败");
			}
			logger.debug("======================14：in.close()");
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RNProxyInvoiceReturnFromBankBL";
			tError.functionName = "readBankFile";
			tError.errorMessage = "读取银行返回文件失败!" + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public static int num = 0;

	public void displayDocument(Node d) {
		num += 2;
		if (d.hasChildNodes()) {
			NodeList nl = d.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				Node n = nl.item(i);
				for (int j = 0; j < num; j++) {
					logger.debug(" ");
				}
				if (n.getNodeValue() == null) {
					logger.debug("<" + n.getNodeName() + ">");
				} else {
					logger.debug(n.getNodeValue());
				}
				displayDocument(n);
				num -= 2;
				if (n.getNodeValue() == null) {
					for (int j = 0; j < num; j++) {
						logger.debug(" ");
					}
					logger.debug("</" + n.getNodeName() + ">");
				}
			}
		}
	}
}
