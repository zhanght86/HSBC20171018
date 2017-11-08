/*
 * <p>ClassName: OLDCode1UI </p>
 * <p>Description: OLDCode1UI类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @CreateDate：2005-01-26 11:24:08
 */
package com.sinosoft.lis.config;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LDCode1Schema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class OLDCode1UI {
private static Logger logger = Logger.getLogger(OLDCode1UI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	// 业务处理相关变量
	/** 全局数据 */
	private LDCode1Schema mLDCode1Schema = new LDCode1Schema();

	public OLDCode1UI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中

		OLDCode1BL tOLDCode1BL = new OLDCode1BL();

		// logger.debug("Start OLDCode1 UI Submit...");
		tOLDCode1BL.submitData(cInputData, mOperate);
		// logger.debug("End OLDCode1 UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tOLDCode1BL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tOLDCode1BL.mErrors);
			CError tError = new CError();
			tError.moduleName = "OLDCode1UI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("INSERT||MAIN")) {
			this.mResult.clear();
			this.mResult = tOLDCode1BL.getResult();
		}
		mInputData = null;
		return true;
	}

	public static void main(String[] args) {
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(this.mLDCode1Schema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCode1UI";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		boolean tReturn = false;
		// 此处增加一些校验代码
		tReturn = true;
		return tReturn;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		this.mLDCode1Schema.setSchema((LDCode1Schema) cInputData
				.getObjectByObjectName("LDCode1Schema", 0));
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}
}
