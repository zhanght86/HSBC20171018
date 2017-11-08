package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保单质押银行贷款BLF
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Howie
 * @version 1.0
 */

public class PEdorBLDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorBLDetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 封装将要提交数据 */
	private MMap mMap = new MMap();

	public PEdorBLDetailBLF() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		mInputData = (VData) cInputData.clone();

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		if (!mOperate.equals("QUERY||MAIN")) {
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mInputData, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "PEdorBLDetailBLF";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
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

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		PEdorBLDetailBL tPEdorBLDetailBL = new PEdorBLDetailBL();
		if (!tPEdorBLDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorBLDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorBLDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tPEdorBLDetailBL.getResult();
		mMap.add((MMap) mResult.getObjectByObjectName("MMap", 0));
		mInputData.clear();
		mInputData.add(mMap);
		return true;
	}

	public static void main(String[] args) {
		PEdorBLDetailBLF tPEdorBLDetailBLF = new PEdorBLDetailBLF();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

		tLPEdorItemSchema.setEdorAcceptNo("6120050804000054");
		tLPEdorItemSchema.setContNo("230110000003048");
		tLPEdorItemSchema.setEdorNo("6020050804000048");
		tLPEdorItemSchema.setEdorType("BL");
		tLPEdorItemSchema.setInsuredNo("000000");
		tLPEdorItemSchema.setPolNo("000000");

		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";
		tG.Operator = "bq";

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tLPEdorItemSchema);
		// boolean tag = tPEdorBLDetailBLF.submitData(tVData,"QUERY||MAIN");
		boolean tag = tPEdorBLDetailBLF.submitData(tVData, "INSERT||MAIN");
		if (tag) {
			logger.debug("Successful");
		} else {
			logger.debug("Fail");
		}
	}
}
