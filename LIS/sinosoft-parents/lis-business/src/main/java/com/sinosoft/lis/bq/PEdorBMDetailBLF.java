package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:红利领取方式变更功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author pst
 * @version 1.0
 */
public class PEdorBMDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorBMDetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 封装将要提交数据 */
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPPolSet mLPPolSet = new LPPolSet();

	public PEdorBMDetailBLF() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mLPPolSet = (LPPolSet) mInputData.getObjectByObjectName("LPPolSet", 0);
		if (mGlobalInput == null || mGlobalInput.equals("")) {
			CError tError = new CError();
			tError.moduleName = "PEdorBMDetailBLF";
			tError.functionName = "checkData";
			tError.errorMessage = "GlobalInput数据为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mLPEdorItemSchema == null || mLPEdorItemSchema.equals("")) {
			CError tError = new CError();
			tError.moduleName = "PEdorBMDetailBLF";
			tError.functionName = "checkData";
			tError.errorMessage = "LPEdorItemSchema数据为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mLPPolSet == null || mLPPolSet.size() < 1) {
			CError tError = new CError();
			tError.moduleName = "PEdorBMDetailBLF";
			tError.functionName = "checkData";
			tError.errorMessage = "LPPolSchema数据为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!dealData()) {
			return false;
		}
		if (!pubSubmit()) {
			return false;
		}

		return true;
	}

	/**
	 * 提交本类的处理结果到数据库
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean pubSubmit() {
		// logger.debug("\t@> GrpEdorSDDetailBLF.pubSubmit() 开始");

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, mOperate)) {
			CError.buildErr(this, "保存提交的信息到数据库失败！");
			logger.debug("\t@> PEdorBMDetailBLF.pubSubmit() : PubSubmit.submitData() 失败！");
			return false;
		}
		// 垃圾处理
		tPubSubmit = null;

		// logger.debug("\t@> PEdorBMDetailBLF.pubSubmit() 成功");
		return true;
	} // function pubSubmit end

	/**
	 * 准备需要保存的数据 调用业务逻辑处理类PEdorGMDetailBL进行处理
	 */
	private boolean dealData() {

		PEdorBMDetailBL tPEdorBMDetailBL = new PEdorBMDetailBL();
		if (!tPEdorBMDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorBMDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorGMDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		VData tResult = tPEdorBMDetailBL.getResult();
		mMap.add((MMap) tResult.getObjectByObjectName("MMap", 0));
		mResult.add(mMap);

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回处理错误
	 * 
	 * @return: CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema
				.decode("6120060704000001|6020060704000002||BM|||000000004597|0000584430|210110000006596||2006-7-4|2006-7-4||||||0|0|0|0|||||||||||||||");
		LPPolSchema tLPPolSchema = new LPPolSchema();
		tLPPolSchema
				.decode("6020060704000002|BM|||000000004597|210110000006596||||||||||||||||||||||0|0|||||||||||||||0||0||0||0|||||0|0|0|0|0||0|0|0|0|0|0|0|0|0|0|0||||||0||||||||||||||1||||||||||||||||||||||||0|||||");
		LPDutySchema tLPDutySchema = new LPDutySchema();
		tLPPolSchema
				.decode("6020060704000002|BM|210110000006596||000000004597|0|0|0|0|0|0|0|0|0|0||0||||0||0||0||0||||0|||||||1||0|0|0||||||||||||0");

		VData tVData = new VData();
		tVData.addElement(tLPEdorItemSchema);
		tVData.addElement(tLPPolSchema);
		tVData.addElement(tLPDutySchema);
		tVData.addElement(tGlobalInput);

		PEdorBMDetailBLF tPEdorBMDetailBLF = new PEdorBMDetailBLF();
		tPEdorBMDetailBLF.submitData(tVData, "Detail");
	}
}
