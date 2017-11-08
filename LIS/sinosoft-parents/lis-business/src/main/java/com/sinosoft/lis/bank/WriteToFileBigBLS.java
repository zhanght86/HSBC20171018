package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.text.DecimalFormat;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.sinosoft.lis.db.LDBankDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LYSendToBankSchema;
import com.sinosoft.lis.vdb.LYBankLogDBSet;
import com.sinosoft.lis.vschema.LYBankLogSet;
import com.sinosoft.lis.vschema.LYSendToBankSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * 此类主要功能是为BL中的数据写xml并调用模板解析成送银行文件
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 银行数据转换到文件模块
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 1.0
 */

public class WriteToFileBigBLS {
private static Logger logger = Logger.getLogger(WriteToFileBigBLS.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	// 业务数据
	private LYSendToBankSet inLYSendToBankSet = new LYSendToBankSet();
	private LYBankLogSet inLYBankLogSet = new LYBankLogSet();
	private TransferData inTransferData = new TransferData();

	private String fileName = "";
	private String filePath = "";
	private String bankCode = "";

	private String mCountMoney = "";
	private PrintWriter out = null;
	private String mSerialnoCount = "";
	private String mDealType = "";

	public WriteToFileBigBLS() {
	}

	/**
	 * ===================初始化数据,等到调用类传来的参数以便处理 =======================
	 * 
	 * @param:其中
	 * @return: boolean
	 */
	public boolean setInputData(TransferData tTransferData,
			GlobalInput inGlobalInput) {
		try {
			fileName = (String) tTransferData.getValueByName("fileName")
					+ ".xml";
			filePath = fileName.substring(0, fileName.lastIndexOf("/"));
			bankCode = (String) tTransferData.getValueByName("bankCode");
			logger.debug("filePath: " + filePath + " | bankCode: "
					+ bankCode);

		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WriteToFileBigBLS";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/** **得到一个批次的总的金额 ** */
	public void setSerialnoMoney(String tMoney) {
		try {
			this.mCountMoney = (new DecimalFormat("0.00")).format(Double
					.valueOf(tMoney));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** **得到一个批次的总的条数** */
	public void setSerialnoNum(String tCount) {
		this.mSerialnoCount = tCount;
	}

	/** **得到此次处理的文件的扣划类型** */
	public void setDealType(String tDealType) {
		this.mDealType = tDealType;
	}

	/**
	 * =======================下面是调用java.IO的类来完成生成.xml=============
	 * 因为是增量的生成,增量只对于数据体来说的.所以用三个方法,分别生成文件头和文件体和 文件尾
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean WriterXMLHead() {

		try {

			logger.debug("Start creating file ......");
			logger.debug("File Name : " + fileName);

			out = new PrintWriter(new FileWriter(new File(fileName)), true);

			out.println("<?xml version=\"1.0\" encoding=\"gb2312\"?>");
			out
					.println("<?xml-stylesheet type=\"text/xsl\" href=\"BankDataFormat.xsl\"?>");
			out.println("");

			// 生成文件头信息
			out.println("<!--");
			out.println(" * <p>FileName: " + fileName + " </p>");
			out.println(" * <p>Description: 业务系统数据转银行系统文件 </p>");
			out.println(" * <p>Copyright: Copyright (c) 2002</p>");
			out.println(" * <p>Company: sinosoft </p>");
			out.println(" * @Author：Minim's WriteToFileBigBLS");
			out.println(" * @CreateDate：" + PubFun.getCurrentDate());
			out.println("-->");
			out.println("");

			out.println("<BANKDATA>");
			out.println("<content>");
			out.print("<CountNum>");
			out.print(mSerialnoCount);
			out.println("</CountNum>");
			out.print("<SysTime>");
			out.print(PubFun.getCurrentDate());
			out.println("</SysTime>");
			out.print("<DealTypeT>");
			out.print(mDealType);
			out.println("</DealTypeT>");
			// 添加管理机构的代码，四级机构
			out.print("<ManageCom>");
			out.print(inLYSendToBankSet.get(1).getComCode());
			out.println("</ManageCom>");

			out.print("<CountPayMoney>");
			out.print(mCountMoney);
			out.println("</CountPayMoney>");
			out.print("</content>");

		} catch (Exception e) {
			logger.debug("Create File Error!");
			return false;
		}

		return true;
	}

	/**
	 * 此方法做xml文件体
	 * 
	 * @return String
	 */
	public void WriterXMLBody(LYSendToBankSchema tLYSendToBankSchema) {
		Class c = tLYSendToBankSchema.getClass();
		Field f[] = c.getDeclaredFields();

		out.println("  <ROW>");

		for (int elementsNum = 0; elementsNum < f.length; elementsNum++) {
			out.print("    <" + f[elementsNum].getName() + ">");

			if (f[elementsNum].getName().equals("PayMoney")) {
				out.print((new DecimalFormat("0.00")).format(Double
						.parseDouble(tLYSendToBankSchema.getV(f[elementsNum]
								.getName()))));
			} else {
				out.print(tLYSendToBankSchema.getV(f[elementsNum].getName()));
			}

			out.println("</" + f[elementsNum].getName() + ">");
		}

		out.println("  </ROW>");
		out.println("");

		// out.println(tLYSendToBankSchema.encode());

	}

	/**
	 * 写完xml文件关闭文件流的输出
	 * 
	 * @return void
	 */
	public void WriterXMLEnd() {
		out.println("</BANKDATA>");

		out.println("");
		out.println("<!-- Create BankFile Success! -->");
		out.close();

	}

	/*
	 * ===================开始调用xml文件和xsl解析出需要的字符串==========================
	 * 获取xsl文件路径 @return
	 */
	public String getXslPath() {
		LDBankDB tLDBankDB = new LDBankDB();

		tLDBankDB.setBankCode(bankCode);
		tLDBankDB.getInfo();
		// if(inLYSendToBankSet.get(1).getDealType().equals("S"))
		// {
		return tLDBankDB.getAgentPaySendF();
		// }
		// else
		// {
		// return tLDBankDB.getAgentGetSendF();
		// }
	}

	/**
	 * Simple sample code to show how to run the XSL processor from the API.
	 */
	public boolean xmlTransform() {
		// Have the XSLTProcessorFactory obtain a interface to a
		// new XSLTProcessor object.
		try {

			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			String tSql = "select bankselfcode from ldbank  where bankcode = '"
					+ "?bankcode?" + "'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tSql);
			sqlbv1.put("bankcode", bankCode);
			tSSRS = tExeSQL.execSQL(sqlbv1);
			logger.debug("=================================================");
			logger.debug("=================================================");
			logger.debug("=================================================");
			logger.debug("=================================================");
			logger.debug("=================================================");
			logger.debug("=================================================");
			logger.debug(tSql);

			boolean tCreateFile = false;
			if (tSSRS.GetText(1, 1) != null
					&& !tSSRS.GetText(1, 1).equals("null")
					&& !tSSRS.GetText(1, 1).equals("")
					&& tSSRS.GetText(1, 1).equals("Y")) {
				tCreateFile = true;
				logger.debug("+++++++++++++++++生成了头文件++++++++++++++++++++++++++");

			}

			if (tCreateFile) {
				int i = 0;
				String xslPath = getXslPath();

				xslPath = xslPath.substring(0, xslPath.length() - 4) + "b.xsl";

				logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				logger.debug("xslPath:" + xslPath);

				File fSource = new File(fileName);
				File fStyle = new File(xslPath);

				Source source = new StreamSource(fSource);
				Result result = new StreamResult(new FileOutputStream(fileName
						.substring(0, fileName.lastIndexOf("."))
						+ "b.z"));
				Source style = new StreamSource(fStyle);

				// Create the Transformer
				TransformerFactory transFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transFactory.newTransformer(style);

				// Transform the Document
				transformer.transform(source, result);
			}
			String xslPath = getXslPath();
			logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			logger.debug("xslPath:" + xslPath);

			File fSource = new File(fileName);
			File fStyle = new File(xslPath);

			Source source = new StreamSource(fSource);
			Result result = new StreamResult(new FileOutputStream(fileName
					.substring(0, fileName.lastIndexOf("."))
					+ ".z"));
			Source style = new StreamSource(fStyle);

			// Create the Transformer
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer(style);

			// Transform the Document
			transformer.transform(source, result);
			logger.debug("======================================================");
			logger.debug("======================================================");
			logger.debug("======================================================");
			logger.debug("======================================================");
			logger.debug("======================================================");
			logger.debug("======================================================");
			logger.debug("======================================================");

			logger.debug("======================================================");
			logger.debug("======================================================");
			logger.debug("======================================================");
			logger.debug("======================================================");

			logger.debug("Transform Success!");
		} catch (Exception e) {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WriteToFileBigBLS";
			tError.functionName = "SimpleTransform";
			tError.errorMessage = "Xml处理失败,无法转换!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * =======================需要将生成的文件名称插入后台库中===================== 保存操作
	 * 
	 * @param mInputData
	 * @return
	 */
	private boolean save(VData mInputData) {
		int i;
		boolean tReturn = true;
		logger.debug("Start Save...");

		// 建立数据库连接
		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WriteToFileBigBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			// 开始事务，锁表
			conn.setAutoCommit(false);

			// 记录银行操作日志
			LYBankLogDBSet tLYBankLogDBSet = new LYBankLogDBSet(conn);
			tLYBankLogDBSet.set(inLYBankLogSet);
			if (!tLYBankLogDBSet.update()) {
				try {
					conn.rollback();
				} catch (Exception e) {
				}
				conn.close();
				logger.debug("LYBankLog Insert Failed");
				return false;
			}

			conn.commit();
			conn.close();
			logger.debug("End Committed");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WriteToFileBigBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
			} catch (Exception e) {
			}
			tReturn = false;
		}
		return tReturn;
	}

	public static void main(String[] args) {
		WriteToFileBigBLS WriteToFileBigBLS1 = new WriteToFileBigBLS();
	}
}
