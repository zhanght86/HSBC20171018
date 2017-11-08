/*
 * <p>ClassName: OLDUWAddFeeBL </p>
 * <p>Description: OLDUWAddFeeBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @CreateDate：2005-01-20 09:41:35
 */
package com.sinosoft.lis.config;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDUWAddFeeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDUWAddFeeSchema;
import com.sinosoft.lis.vschema.LDUWAddFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class OLDUWAddFeeBL {
private static Logger logger = Logger.getLogger(OLDUWAddFeeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务处理相关变量 */
	private MMap map = new MMap();
	private LDUWAddFeeSchema mLDUWAddFeeSchema = new LDUWAddFeeSchema();
	private LDUWAddFeeSet mLDUWAddFeeSet = new LDUWAddFeeSet();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	public OLDUWAddFeeBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 进行业务处理
		if (!dealData(cOperate)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "OLDUWAddFeeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败OLDUWAddFeeBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		if (this.mOperate.equals("QUERY||MAIN")) {
			this.submitquery();
		} else {
			logger.debug("Start OLDUWAddFeeBL Submit...");
			PubSubmit tPubSubmit = new PubSubmit();

			if (!tPubSubmit.submitData(mInputData, cOperate)) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);

				CError tError = new CError();
				tError.moduleName = "LDPersonBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";

				this.mErrors.addOneError(tError);
				return false;
			}
			logger.debug("End OLDUWAddFeeBL Submit...");
		}
		mInputData = null;

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {
		boolean tReturn = false;
		String tNo = "";

		// if (!getInputData(cInputData))
		// return false ;
		logger.debug("After getinputdata");
		logger.debug(cOperate);
		// 处理个人信息数据
		// 添加纪录
		if (cOperate.equals("INSERT")) {

			map.put(mLDUWAddFeeSchema, "INSERT");
			tReturn = true;
		}
		;
		if (cOperate.equals("UPDATE||MAIN")) {
			map.put(mLDUWAddFeeSchema, "UPDATE");

			tReturn = true;
		}
		;
		if (cOperate.equals("DELETE||MAIN")) {
			map.put(mLDUWAddFeeSchema, "DELETE");
			// map.put("delete from LDUWAddFee where
			// ICDCode='"+mLDUWAddFeeSchema.getICDCode()+"'","DELETE");
			tReturn = true;
		}
		;

		return tReturn;

		// return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean updateData() {
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean deleteData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		this.mLDUWAddFeeSchema.setSchema((LDUWAddFeeSchema) cInputData
				.getObjectByObjectName("LDUWAddFeeSchema", 0));
		// this.mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean submitquery() {
		this.mResult.clear();
		LDUWAddFeeDB tLDUWAddFeeDB = new LDUWAddFeeDB();
		tLDUWAddFeeDB.setSchema(this.mLDUWAddFeeSchema);
		// 如果有需要处理的错误，则返回
		if (tLDUWAddFeeDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDUWAddFeeDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDUWAddFeeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	private boolean prepareOutputData() {
		try {

			this.mInputData.clear();
			this.mInputData.add(this.mLDUWAddFeeSchema);
			this.mInputData.add(map);
			mResult.clear();
			mResult.add(this.mLDUWAddFeeSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWAddFeeBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}
}
