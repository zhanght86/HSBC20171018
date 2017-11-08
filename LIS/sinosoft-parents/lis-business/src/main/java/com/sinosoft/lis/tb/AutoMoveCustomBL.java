package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Hashtable;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 扫描件申请
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

public class AutoMoveCustomBL {
private static Logger logger = Logger.getLogger(AutoMoveCustomBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	Hashtable mHashtable = new Hashtable();

	public AutoMoveCustomBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT||MAIN"和"INSERT||DETAIL"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("---End getInputData---");

		// 进行业务处理
		if (!dealData())
			return false;
		logger.debug("---End dealData---");

		// 申请投保单处理
		if (mOperate.equals("INSERT||MAIN")) {
			// 准备往后台的数据
			// if (!prepareOutputData()) return false;
			// logger.debug("---End prepareOutputData---");
			//
			// logger.debug("Start AutoMoveCustom BLS Submit...");
			// AutoMoveCustomBLS tAutoMoveCustomBLS = new AutoMoveCustomBLS();
			// if(tAutoMoveCustomBLS.submitData(mInputData, cOperate) == false)
			// {
			// // @@错误处理
			// this.mErrors.copyAllErrors(tAutoMoveCustomBLS.mErrors);
			// mResult.clear();
			// return false;
			// }
			// logger.debug("End AutoMoveCustom BLS Submit...");
			//
			// //如果有需要处理的错误，则返回
			// if (tAutoMoveCustomBLS.mErrors .needDealError()) {
			// this.mErrors.copyAllErrors(tAutoMoveCustomBLS.mErrors ) ;
			// }
		}
		// 查询
		else if (mOperate.equals("QUERY||MAIN")) {
		}

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mHashtable = (Hashtable) mInputData.getObjectByObjectName(
					"Hashtable", 0);
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "AutoMoveCustomBL";
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
		int i;

		try {
			// 申请投保单处理
			if (mOperate.equals("INSERT||MAIN")) {
				writeToFile((String) mHashtable.get("type") + ".js");
			}
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "TempFeeWithdrawBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 创建文件
	 * 
	 * @param fileName
	 * @return
	 */
	private PrintWriter mkFile(String fileName) {
		try {
			String filePath = "c:\\\\" + fileName;
			PrintWriter out = new PrintWriter(
					new FileWriter(new File(filePath)), true);
			logger.debug("filePath:" + fileName);
			return out;
		} catch (Exception e) {
			try {
				String filePath = "c:\\" + fileName;
				PrintWriter out = new PrintWriter(new FileWriter(new File(
						fileName)), true);
				logger.debug("filePath:" + fileName);
				return out;
			} catch (Exception e2) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * 生成文件
	 * 
	 * @param fileName
	 * @return
	 */
	private boolean writeToFile(String fileName) {
		int i;
		PrintWriter out = null;

		try {
			logger.debug("Start creating file ......");
			logger.debug("File Name : " + fileName);

			out = mkFile(fileName);

			// 生成文件头信息
			out.println("/**");
			out.println(" * <p>FileName: " + fileName + " </p>");
			out.println(" * <p>Description: 文件 </p>");
			out.println(" * <p>Copyright: Copyright (c) 2002</p>");
			out.println(" * <p>Company: sinosoft </p>");
			out.println(" * @Author：Minim");
			out.println(" * @CreateDate：" + PubFun.getCurrentDate());
			out.println(" **/");
			out.println("");

			out.println("function " + (String) mHashtable.get("type") + "() {");
			out.println("  objName = this.name;");
			out.println("  if (goToLock) {");
			out.println("    objName = goToLock;");
			out.println("    goToLock = false;");
			out.println("  }");
			out.println("");
			out.println("  try { hiddenPosition(); } catch(e) {}");
			out.println("");

			String value = (String) mHashtable.get("autoMove");
			String[] arrValue = PubFun.split(value, "|");
			for (int j = 0; j < arrValue.length; j++) {
				out.println("  " + arrValue[j]);
				logger.debug(arrValue[j]);
			}

			out.println("");
			out.println("}");
			logger.debug("<!-- Create File Success! -->");
			out.close();
		} catch (Exception e) {
			logger.debug("Create File Error!");
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

	public static void main(String[] args) {
		AutoMoveCustomBL scanApplyBL1 = new AutoMoveCustomBL();
	}
}
