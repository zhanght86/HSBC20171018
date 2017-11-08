package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保单质押银行贷款清偿BLF</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author Howie
 * @version 1.0
 */

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PEdorBDDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorBDDetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 封装将要提交数据 */
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperate;

	public PEdorBDDetailBLF() {
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
				tError.moduleName = "PEdorBDDetailBLF";
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
	 * 准备需要保存的数据 调用个人保全投保人信息变更业务逻辑处理类PEdorACBL进行处理
	 */
	private boolean dealData() {
		PEdorBDDetailBL tPEdorBDDetailBL = new PEdorBDDetailBL();
		if (!tPEdorBDDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorBDDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorBDDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tPEdorBDDetailBL.getResult();
		mMap.add((MMap) mResult.getObjectByObjectName("MMap", 0));
		mInputData.clear();
		mInputData.add(mMap);

		return true;
	}

	public static void main(String[] args) {
		PEdorBDDetailBLF tPEdorBDDetailBLF = new PEdorBDDetailBLF();

		// LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		// tLPEdorItemSchema.setEdorAcceptNo("86000000001594");
		// tLPEdorItemSchema.setContNo("230110000000465");
		// tLPEdorItemSchema.setEdorNo("410000000001298");
		// tLPEdorItemSchema.setEdorType("BD");
		// tLPEdorItemSchema.setInsuredNo("000000");
		// tLPEdorItemSchema.setPolNo("000000");

		GlobalInput tG = new GlobalInput();
		tG.Operator = "bq";
		tG.ManageCom = "86";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("EndPayType", "01");
		tTransferData.setNameAndValue("HaveEdorFlag", "0");

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorAcceptNo("6120050812000049");
		tLPEdorItemSchema.setEdorNo("6020050812000044");
		tLPEdorItemSchema.setEdorType("BD");
		tLPEdorItemSchema.setContNo("230110000002863");
		tLPEdorItemSchema.setInsuredNo("000000");
		tLPEdorItemSchema.setPolNo("000000");
		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tLPEdorItemSchema);
		tVData.add(tTransferData);
		boolean tag = tPEdorBDDetailBLF.submitData(tVData, "INSERT||MAIN");
		// boolean tag = tPEdorBDDetailBLF.submitData(tVData, "QUERY||MAIN");
		if (tag) {
			logger.debug("Successful");
		} else {
			logger.debug("Fail");
		}
	}
}
